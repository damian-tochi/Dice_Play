package com.example.diceplay.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import com.example.diceplay.ui.shape.DiceShape
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource


@OptIn(ExperimentalResourceApi::class)
@Composable
fun ImageContent (modifier: Modifier = Modifier, isRemoved: Boolean = false, indexes: List<Int>) {

    val allImageParts: Array<String> =
        arrayOf(
            "d-1.png",
            "h-2.png",
            "h-3.png",
            "h-4.png",
            "h-5.png",
            "h-6.png"
        )

    val blankParts: Array<String> =
        arrayOf(
            "d-1-b.png",
            "h-2-b.png",
            "h-3-b.png",
            "h-4-b.png",
            "h-5-b.png",
            "h-6-b.png"
        )

    val selectedIndexes: ArrayList<Int> = arrayListOf()
    selectedIndexes.addAll(indexes)

    Box(modifier = modifier.aspectRatio(1.0f)) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()) {
            Image(
                modifier = Modifier.fillMaxWidth(1f),
                painter = painterResource(if (selectedIndexes.contains(1)) { allImageParts[0] } else { blankParts[0]} ),
                contentDescription = "dice-spinning"
            )

            Row(modifier = Modifier.fillMaxWidth(1f).align(Alignment.CenterHorizontally).wrapContentHeight()) {
                Image(
                    modifier = Modifier.weight(0.712f, false),
                    painter = painterResource(if (selectedIndexes.contains(2)) { allImageParts[3] } else { blankParts[3] }),
                    contentDescription = "dice-spinning"
                )
                Image(
                    modifier = Modifier.wrapContentHeight().weight(1f, true),
                    painter = painterResource(if (selectedIndexes.contains(3)) { allImageParts[1] } else { blankParts[1] }),
                    contentDescription = "dice-spinning"
                )
                Image(
                    modifier = Modifier.weight(0.74f, true),
                    painter = painterResource(if (selectedIndexes.contains(4)) { allImageParts[2] } else { blankParts[2] }),
                    contentDescription = "dice-spinning"
                )
            }

            Row(modifier = Modifier.fillMaxWidth(1f).align(Alignment.CenterHorizontally)) {
                Spacer(modifier = Modifier.weight(0.4f))
                Image(
                    modifier = Modifier.wrapContentHeight().wrapContentWidth(),
                    painter = painterResource(if (selectedIndexes.contains(5)) { allImageParts[4] } else { blankParts[4]} ),
                    contentDescription = "dice-spinning"
                )
                Image(
                    modifier = Modifier.wrapContentHeight().wrapContentWidth(),
                    painter = painterResource(if (selectedIndexes.contains(6)) { allImageParts[5] } else { blankParts[5]} ),
                    contentDescription = "dice-spinning"
                )
                Spacer(modifier = Modifier.weight(0.4f))

            }
            Spacer(modifier = Modifier.weight(1f))
        }



    }
}