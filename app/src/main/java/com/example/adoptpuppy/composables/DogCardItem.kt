package com.example.adoptpuppy.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.example.adoptpuppy.Dog
import com.example.adoptpuppy.ui.theme.typography
import dev.chrisbanes.accompanist.coil.CoilImage

@Composable
fun DogCardItem(dog: Dog, navigateToPuppyDetails: (Dog) -> Unit) {
    Card(
        elevation = 4.dp,
        modifier = Modifier
            .padding(16.dp)
            .clickable { navigateToPuppyDetails(dog) }
    ) {
        Column {
            CoilImage(
                data = dog.imageUrl,
                contentDescription = dog.description,
                modifier = Modifier
                    .height(240.dp)
                    .background(color = Color.Gray)
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop,
                fadeIn = true,
                loading = {
                    CircularProgressIndicator(Modifier.align(Alignment.Center))
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
                style = typography.body2,
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}