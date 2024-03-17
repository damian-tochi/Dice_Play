package com.example.diceplay.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import com.example.diceplay.ui.shape.DiceShape
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource


@OptIn(ExperimentalResourceApi::class)
@Composable
fun DiceSpinner (
    modifier: Modifier = Modifier,
    rotationDegrees: Float = 0f,  isSpinning: Boolean = false,  computerTurn: Boolean = false) {
    Box(modifier = modifier
            .aspectRatio(1.0f)
            .clip(DiceShape())
            .background(if (isSpinning) { Color(0xFF1E9C99) } else { Color.Transparent })) {

        if (isSpinning) {
            Image(
                modifier = Modifier
                    .fillMaxSize(0.6f)
                    .align(Alignment.Center)
                    .rotate(rotationDegrees)
                    .aspectRatio(1.0f),
                painter = painterResource("dice-faces_spin.png"),
                contentDescription = "dice-spinning"
            )
        }
    }

}