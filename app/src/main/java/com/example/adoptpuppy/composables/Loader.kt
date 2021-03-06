package com.example.adoptpuppy.composables


import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieAnimationSpec
import com.example.adoptpuppy.R

@Composable
fun Loader(
    modifier: Modifier = Modifier
) {
    val animationSpec = remember { LottieAnimationSpec.RawRes(R.raw.loading) }
    LottieAnimation(
        animationSpec,
        modifier = Modifier
            .fillMaxSize()
            .then(modifier)
    )
}