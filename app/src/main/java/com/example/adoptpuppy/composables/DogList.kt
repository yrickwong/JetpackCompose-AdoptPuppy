package com.example.adoptpuppy.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.adoptpuppy.Dog

@Composable
fun DogList(dogList: List<Dog>, navigateToPuppyDetails: (Dog) -> Unit) {
    LazyColumn(Modifier.fillMaxWidth()) {
        items(dogList) { dog ->
            DogCardItem(
                dog = dog,
                navigateToPuppyDetails = navigateToPuppyDetails
            )
        }
    }
}