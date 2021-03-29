package com.example.adoptpuppy

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.adoptpuppy.ui.theme.PictureAppTheme
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.navigate
import com.airbnb.mvrx.compose.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.Uninitialized
import com.airbnb.mvrx.compose.mavericksActivityViewModel
import com.example.adoptpuppy.composables.DogList
import com.example.adoptpuppy.composables.Loader
import com.example.adoptpuppy.ui.theme.purple500
import com.example.adoptpuppy.ui.theme.typography
import dev.chrisbanes.accompanist.coil.CoilImage

private const val TAG = "DogActivity"

class DogActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PictureAppTheme {
                val navController = rememberNavController()
                NavHost(navController, startDestination = "home") {
                    composable("home") {
                        DogListHomeScreen(navController)
                    }
                    composable("dog/{id}") { backStackEntry ->
                        val dogId = backStackEntry.arguments?.getString("id")
                        DogDetailScreen(dogId = dogId?.toLong()) {
                            navController.popBackStack()
                        }
                    }
                }
            }
        }
    }

}

@Composable
fun DogListHomeScreen(navController: NavController) {
    val viewModel: DogViewModel = mavericksActivityViewModel()
    val dogList by viewModel.collectAsState(DogState::dogs)
    Surface(color = MaterialTheme.colors.background) {
        Box(modifier = Modifier.fillMaxSize().background(color = purple500) ) {
            DogList(
                dogList = dogList().orEmpty(),
            ) {
                navController.navigate("dog/${it.id}")
            }
            if (dogList is Uninitialized || dogList is Loading) {
                Loader(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}

@Composable
fun DogDetailScreen(dogId: Long?, navigateBack: () -> Unit) {
    val viewModel: DogViewModel = mavericksActivityViewModel()
    val dogList by viewModel.collectAsState(DogState::dogs)
    val dog: Dog =
        dogList()?.firstOrNull { it.id == dogId } ?: error("Cannot find dog with id $dogId")
    Scaffold(
        content = {
            Surface(color = MaterialTheme.colors.background, modifier = Modifier.fillMaxHeight()) {
                Box {
                    Column {
                        CoilImage(
                            data = dog.imageUrl,
                            contentDescription = dog.description,
                            modifier = Modifier
                                .height(360.dp)
                                .background(color = Color.Gray)
                                .fillMaxWidth(),
                            contentScale = ContentScale.Crop,
                            fadeIn = true,
                            loading = {
                                Box(Modifier.matchParentSize()) {
                                    CircularProgressIndicator(Modifier.align(Alignment.Center))
                                }
                            }
                        )
                        Text(
                            modifier = Modifier.padding(top = 12.dp, start = 12.dp),
                            text = dog.name,
                            style = typography.h6,
                        )
                        Text(
                            modifier = Modifier.padding(top = 4.dp, start = 12.dp),
                            text = dog.breeds,
                            style = typography.h5,
                        )
                        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                            Text(
                                modifier = Modifier.padding(top = 4.dp, start = 12.dp),
                                text = dog.description,
                                style = TextStyle(fontSize = 28.sp)
                            )
                        }
                    }
                    Button(
                        onClick = {
                            navigateBack()
                        }, modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(12.dp)
                            .fillMaxWidth()
                    ) {
                        Text(text = "I love this dog!", style = TextStyle(fontSize = 18.sp))
                    }
                }
            }
        }
    )
}




