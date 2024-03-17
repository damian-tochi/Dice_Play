package com.example.diceplay



import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseInBounce
import androidx.compose.animation.core.EaseInCirc
import androidx.compose.animation.core.EaseInOutQuint
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.diceplay.ui.DiceSpinAnimate
import com.example.diceplay.ui.ImageContent
import com.example.diceplay.ui.shape.DiceShape
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import kotlin.random.Random


@OptIn(ExperimentalResourceApi::class)
@Composable
fun App() {

    val imageHolder: Array<String> =
        arrayOf(
            "dice-six-faces-one.png",
            "dice-six-faces-two.png",
            "dice-six-faces-three.png",
            "dice-six-faces-four.png",
            "dice-six-faces-five.png",
            "dice-six-faces-six.png",
            "good-fish.png",
            "evil-shark.png",
            "con-shark.png",
            "dice-faces_spin.png"
        )

    val imageIndex: MutableState<Int> = remember {
        mutableStateOf(9)
    }

    val playerIndex: MutableState<Int> = remember {
        mutableStateOf(9)
    }

    val computerIndex: MutableState<Int> = remember {
        mutableStateOf(9)
    }

    var spinningState by remember { mutableStateOf(false) }

    var computerPlaying by remember { mutableStateOf(false) }

    var computerDeciding by remember { mutableStateOf(false) }

    var cardPicked by remember { mutableStateOf(false) }

    var computerTurn by remember { mutableStateOf(false) }

    var playerShark by remember { mutableStateOf(false) }

    var computerShark by remember { mutableStateOf(false) }

    val computerChoices: Array<String> =
        arrayOf(
            "NONE",
            "One",
            "two",
            "two",
            "one",
            "one",
            "two"
        )

    var computerChosen by remember { mutableStateOf(false) }

    val computerChoiceIndex: MutableState<Int> = remember {
        mutableStateOf(0)
    }

    var clearCard by remember { mutableStateOf(false) }

    val turn: MutableState<Int> = remember {
        mutableStateOf(0)
    }

    val computerCards: ArrayList<Int> = remember {
        arrayListOf()
    }

    val playerCards: ArrayList<Int> = remember {
        arrayListOf()
    }

    val transition = rememberInfiniteTransition(label = "")
    val transitionTwo = rememberInfiniteTransition(label = "")
    val cardMoveTansition = rememberInfiniteTransition(label = "")

    val animatedProgress by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(20500, easing = EaseInCirc),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    val topDownAnimatedProgress by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2500, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = ""
    )

    val brush2 = Brush.verticalGradient(
        colors = listOf(Color(0xffb20811),
            Color(0x4Fb20811)),
            startY = 0f,
            endY = 1000f * animatedProgress
        )


    val brushHigh = Brush.verticalGradient(
        colors = listOf(Color(0x4050C882),
            Color(0x00FFFFFF)),
        startY = 0f,
        endY = 200f
    )

    val brushTransparent = Brush.verticalGradient(
        colors = listOf(Color(0x00FFFFFF),
            Color(0x00FFFFFF)),
        startY = 0f,
        endY = 1000f
    )

    val dice = Brush.verticalGradient(
        colors = listOf(Color(0xFF3CDCDC),
            Color(0xFF40C882)),
        startY = 0f,
        endY = 500f * animatedProgress
    )

    val brush = Brush.linearGradient(
        colors = listOf(Color(0xFF282828),
            Color(0xFF3C3C3C)),
        start = Offset(0f, Float.POSITIVE_INFINITY),
        end = Offset(Float.POSITIVE_INFINITY, 500f * animatedProgress)
    )

    val cardPositionState = cardMoveTansition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 2500,
                delayMillis = 1000,
                easing = LinearEasing
            )
        )
    )

    var computerWin = false
    var playerWin = false

    //Con shark takes 1, all or you might get lucky and he gives you one

    MaterialTheme {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize().background(brush)) {

            Row(modifier = Modifier.weight(1f)) {

                //Player Sequence
                Column(
                    modifier = Modifier.weight(1f).background(if (!computerTurn) { brushHigh } else { brushTransparent }),
                    verticalArrangement = Arrangement.spacedBy(5.dp)) {

                    Row(
                        modifier = Modifier.fillMaxWidth(1f).height(40.dp).background(Color.Gray).padding(5.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.CenterVertically) {

                        Image(
                            painter = painterResource(imageHolder[1]),
                            null,
                            modifier = Modifier.fillMaxSize(0.2f).aspectRatio(1.0f)
                        )

                        Text(
                            "Player Name",
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color.White
                        )

                        Box(Modifier.size(30.dp).padding(1.dp)) {
                            Button(
                                onClick = { },
                                modifier = Modifier.size(30.dp),
                                shape = RoundedCornerShape(10.dp),
                                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent)
                            ) {
                                Icon(
                                    painter = painterResource("dice-six-faces-one.png"),
                                    contentDescription = "",
                                    modifier = Modifier.size(30.dp),
                                    tint = Color.White
                                )
                            }

                        }

                    }

                    Text("",
                        fontSize = 12.sp,
                        color = Color.Blue,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )

                    Text("0", fontSize = 10.sp, color = Color.Black)

                    ImageContent(Modifier,false, playerCards)

                    Spacer(modifier = Modifier.weight(1f))

                    if (turn.value == 2 && !clearCard && !playerShark) {
                        Row(modifier = Modifier.fillMaxWidth(1f).height(80.dp)
                            .background(Color.Transparent).padding(5.dp),
                            horizontalArrangement = Arrangement.spacedBy(1.dp),
                            verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                painter = painterResource(
                                    if (imageIndex.value == 6) {
                                        "good-fish-card.png"
                                    } else if (imageIndex.value == 8) {
                                        "con-shark-card.png"
                                    } else {
                                        "dice-cover-card.jpeg"
                                    }
                                ),
                                null,
                                modifier = Modifier.weight(1f).clickable {
                                    val value = Random.nextInt(1, 3)
                                    if (turn.value == 2) {
                                        when (playerIndex.value) {
                                            0 -> {
                                                if (value == 2) {
                                                    if (playerCards.size < 1) {
                                                        playerCards.add(1)
                                                    } else {
                                                        if (!playerCards.contains(1)) {
                                                            playerCards.add(1)
                                                        }
                                                    }
                                                }

                                            }
                                            1 -> {
                                                if (value == 2) {
                                                    if (playerCards.size < 1) {
                                                        playerCards.add(2)
                                                    } else {
                                                        if (!playerCards.contains(2)) {
                                                            playerCards.add(2)
                                                        }
                                                    }
                                                }

                                            }
                                            2 -> {
                                                if (value == 2) {
                                                    if (playerCards.size < 1) {
                                                        playerCards.add(3)
                                                    } else {
                                                        if (!playerCards.contains(3)) {
                                                            playerCards.add(3)
                                                        }
                                                    }
                                                }

                                            }
                                            3 -> {
                                                if (value == 2) {
                                                    if (playerCards.size < 1) {
                                                        playerCards.add(4)
                                                    } else {
                                                        if (!playerCards.contains(4)) {
                                                            playerCards.add(4)
                                                        }
                                                    }
                                                }

                                            }
                                            4 -> {
                                                if (value == 2) {
                                                    if (playerCards.size < 1) {
                                                        playerCards.add(5)
                                                    } else {
                                                        if (!playerCards.contains(5)) {
                                                            playerCards.add(5)
                                                        }
                                                    }
                                                }

                                            }
                                            5 -> {
                                                if (value == 2) {
                                                    if (playerCards.size < 1) {
                                                        playerCards.add(6)
                                                    } else {
                                                        if (!playerCards.contains(6)) {
                                                            playerCards.add(6)
                                                        }
                                                    }
                                                }

                                            }
                                            6 -> {
                                                val txt = "Good Fish, Lucky!"
                                            }
                                            7 -> {
                                                if (playerCards.size > 0) {
                                                    playerCards.clear()
                                                }
                                            }
                                            8 -> {
                                                val txt = "Try Your Luck, Con Shark here"
                                            }
                                            9 -> {}
                                            else -> {
                                                playerCards.clear()
                                            }
                                        }
                                    }

                                    cardPicked = true
                                    computerTurn = true
                                    computerPlaying = true
                                }
                            )

                            Image(
                                painter = painterResource(
                                    if (imageIndex.value == 6) {
                                        "good-fish-card.png"
                                    } else if (imageIndex.value == 8) {
                                        "con-shark-card.png"
                                    } else {
                                        "dice-cover-card.jpeg"
                                    }
                                ),
                                null,
                                modifier = Modifier.weight(1f).clickable {
                                    val value = Random.nextInt(1, 3)
                                    if (turn.value == 2) {
                                        when (playerIndex.value) {
                                            0 -> {
                                                if (value == 2) {
                                                    if (playerCards.size < 1) {
                                                        playerCards.add(1)
                                                    } else {
                                                        if (!playerCards.contains(1)) {
                                                            playerCards.add(1)
                                                        }
                                                    }
                                                }

                                            }
                                            1 -> {
                                                if (value == 2) {
                                                    if (playerCards.size < 1) {
                                                        playerCards.add(2)
                                                    } else {
                                                        if (!playerCards.contains(2)) {
                                                            playerCards.add(2)
                                                        }
                                                    }
                                                }

                                            }
                                            2 -> {
                                                if (value == 2) {
                                                    if (playerCards.size < 1) {
                                                        playerCards.add(3)
                                                    } else {
                                                        if (!playerCards.contains(3)) {
                                                            playerCards.add(3)
                                                        }
                                                    }
                                                }

                                            }
                                            3 -> {
                                                if (value == 2) {
                                                    if (playerCards.size < 1) {
                                                        playerCards.add(4)
                                                    } else {
                                                        if (!playerCards.contains(4)) {
                                                            playerCards.add(4)
                                                        }
                                                    }
                                                }

                                            }
                                            4 -> {
                                                if (value == 2) {
                                                    if (playerCards.size < 1) {
                                                        playerCards.add(5)
                                                    } else {
                                                        if (!playerCards.contains(5)) {
                                                            playerCards.add(5)
                                                        }
                                                    }
                                                }

                                            }
                                            5 -> {
                                                if (value == 2) {
                                                    if (playerCards.size < 1) {
                                                        playerCards.add(6)
                                                    } else {
                                                        if (!playerCards.contains(6)) {
                                                            playerCards.add(6)
                                                        }
                                                    }
                                                }

                                            }
                                            6 -> {
                                                val txt = "Good Fish, Lucky!"
                                                if (value == 2 || value == 3) {
                                                    if (playerCards.size < 1) {
                                                        val ranPos = Random.nextInt(1, 6)
                                                        playerCards.add(ranPos)
                                                    } else {
                                                        for (i in 0 until 6) {
                                                            if (!playerCards.contains(i + 1)) {
                                                                playerCards.add(i + 1)
                                                                break
                                                            }
                                                        }
                                                    }
                                                } else {
                                                    val ranPos = Random.nextInt(1, 6)
                                                    playerCards.add(ranPos)
                                                }
                                            }
                                            7 -> {
                                                if (playerCards.size > 0) {
                                                    playerCards.clear()
                                                }
                                            }
                                            8 -> {
                                                val txt = "Try Your Luck, Con Shark here"
                                                if (value == 2) {
                                                    if (playerCards.size < 1) {
                                                        val ranPos = Random.nextInt(1, 6)
                                                        playerCards.add(ranPos)
                                                    } else {
                                                        for (i in 0 until 6) {
                                                            if (!playerCards.contains(i + 1)) {
                                                                playerCards.add(i + 1)
                                                                break
                                                            }
                                                        }
                                                    }
                                                } else if (value == 3) {
                                                    if (playerCards.size > 0) {
                                                        val ranPos = Random.nextInt(1, 2)
                                                        if (ranPos == 1) {
                                                            playerShark = true
                                                        } else {
                                                            for (i in 0 until 6) {
                                                                if (playerCards.contains(i + 1)) {
                                                                    playerCards.remove(i + 1)
                                                                    break
                                                                }
                                                            }
                                                        }
                                                    } else {
                                                        val ranPos = Random.nextInt(1, 4)
                                                        playerCards.add(ranPos)
                                                    }
                                                } else {
                                                    val ranPos = Random.nextInt(1, 6)
                                                    playerCards.add(ranPos)
                                                }

                                            }

                                            9 -> {}
                                            else -> {
                                                playerCards.clear()
                                            }
                                        }
                                    }
                                    cardPicked = true
                                    computerTurn = true
                                    computerPlaying = true
                                }

                            )
                        }

//                        Icon(
//                            painter = VectorPainter(resource("shark.xml")),
//                            contentDescription = "Localized description",
//                            modifier = Modifier
//                                .size(100.dp),
//                            tint = Color.Green,
//                        )

                        Text(
                            when (imageIndex.value) {
                                0 -> {"Nice! Pick a card"}
                                1 -> {"Nice! Pick a card"}
                                2 -> {"Nice! Pick a card"}
                                3 -> {"Nice! Pick a card"}
                                4 -> {"Nice! Pick a card"}
                                5 -> {"Nice! Pick a card"}
                                6 -> {"Good Fish, Lucky!"}
                                7 -> {"Evil Shark, Oops!"}
                                8 -> {"Careful, Con Shark here"}
                                else -> {"0"}
                            },
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            fontSize = 12.sp,
                            color = Color.Blue,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )

                    }

                }

                //Computer Sequence
                Column(modifier = Modifier.weight(1f).background(if (computerTurn) { brushHigh } else { brushTransparent }),
                    verticalArrangement = Arrangement.spacedBy(5.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(1f).height(40.dp)
                            .background(Color.LightGray).padding(5.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(imageHolder[0]),
                            null,
                            modifier = Modifier.fillMaxSize(0.2f).aspectRatio(1.0f)
                        )

                        Text(
                            "Computer",
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color.White
                        )

                    }

                    val value = Random.nextInt(1, 3)
                    if (turn.value == 1) {
                        when (computerIndex.value) {
                            0 -> {
                                if (computerCards.size < 1) {
                                    computerCards.add(1)
                                } else {
                                    if (!computerCards.contains(1)) {
                                        computerCards.add(1)
                                    }
                                }
                            }
                            1 -> {
                                if (computerCards.size < 1) {
                                    computerCards.add(2)
                                } else {
                                    if (!computerCards.contains(2)) {
                                        computerCards.add(2)
                                    }
                                }
                            }
                            2 -> {
                                if (computerCards.size < 1) {
                                    computerCards.add(3)
                                } else {
                                    if (!computerCards.contains(3)) {
                                        computerCards.add(3)
                                    }
                                }
                            }
                            3 -> {
                                if (computerCards.size < 1) {
                                    computerCards.add(4)
                                } else {
                                    if (!computerCards.contains(4)) {
                                        computerCards.add(4)
                                    }
                                }
                            }
                            4 -> {
                                if (computerCards.size < 1) {
                                    computerCards.add(5)
                                } else {
                                    if (!computerCards.contains(5)) {
                                        computerCards.add(5)
                                    }
                                }
                            }
                            5 -> {
                                if (computerCards.size < 1) {
                                    computerCards.add(6)
                                } else {
                                    if (!computerCards.contains(6)) {
                                        computerCards.add(6)
                                    }
                                }
                            }
                            6 -> {
                                val txt = "Good Fish, Lucky!"
                                if (value == 2 || value == 3) {
                                    if (computerCards.size < 1) {
                                        val ranPos = Random.nextInt(1, 6)
                                        computerCards.add(ranPos)
                                    } else {
                                        for (i in 0 until 6) {
                                            if (!computerCards.contains(i + 1)) {
                                                computerCards.add(i + 1)
                                                break
                                            }
                                        }
                                    }
                                } else {
                                    val ranPos = Random.nextInt(1, 6)
                                    computerCards.add(ranPos)
                                }
                            }
                            7 -> {
                                if (computerCards.size > 0) {
                                    computerCards.clear()
                                }
                            }
                            8 -> {
                                val txt = "Try Your Luck, Con Shark here"
                                if (value == 2) {
                                    if (computerCards.size < 1) {
                                        val ranPos = Random.nextInt(1, 6)
                                        computerCards.add(ranPos)
                                    } else {
                                        for (i in 0 until 6) {
                                            if (!computerCards.contains(i + 1)) {
                                                computerCards.add(i + 1)
                                                break
                                            }
                                        }
                                    }
                                } else if (value == 3) {
                                    if (computerCards.size > 0) {
                                        val ranPos = Random.nextInt(1, 2)
                                        if (ranPos == 1) {
                                            computerShark = true
                                        } else {
                                            for (i in 0 until 6) {
                                                if (computerCards.contains(i + 1)) {
                                                    computerCards.remove(i + 1)
                                                    break
                                                }
                                            }
                                        }
                                    } else {
                                        val ranPos = Random.nextInt(1, 4)
                                        computerCards.add(ranPos)
                                    }
                                } else {
                                    val ranPos = Random.nextInt(1, 6)
                                    computerCards.add(ranPos)
                                }

                            }
                            9 -> {}
                            else -> {
                                computerCards.clear()
                            }
                        }
                    }

                    Text(
                        "",
                        fontSize = 12.sp,
                        color = Color.Blue,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text("Computer", fontSize = 10.sp, color = Color.Black)

                    ImageContent(Modifier,false, computerCards)

                    Spacer(modifier = Modifier.weight(1f))

                    if (turn.value == 1 && !clearCard && !computerShark) {
                        Row(modifier = Modifier.fillMaxWidth(1f).height(80.dp)
                            .background(Color.Transparent).padding(5.dp),
                            horizontalArrangement = Arrangement.spacedBy(1.dp),
                            verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                painter = painterResource(
                                    if (imageIndex.value == 6) {
                                        "good-fish-card.png"
                                    } else if (imageIndex.value == 8) {
                                        "con-shark-card.png"
                                    } else {
                                        "dice-cover-card.jpeg"
                                    }
                                ),
                                null,
                                modifier = Modifier.weight(1f)
                            )

                            Image(
                                painter = painterResource(
                                    if (imageIndex.value == 6) {
                                        "good-fish-card.png"
                                    } else if (imageIndex.value == 8) {
                                        "con-shark-card.png"
                                    } else {
                                        "dice-cover-card.jpeg"
                                    }
                                ),
                                null,
                                modifier = Modifier.weight(1f)
                            )
                        }
                        Text(
                            when (imageIndex.value) {
                                0 -> {"Nice! Pick a card"}
                                1 -> {"Nice! Pick a card"}
                                2 -> {"Nice! Pick a card"}
                                3 -> {"Nice! Pick a card"}
                                4 -> {"Nice! Pick a card"}
                                5 -> {"Nice! Pick a card"}
                                6 -> {"Good Fish, Lucky!"}
                                7 -> {"Evil Shark, Oops!"}
                                8 -> {"Careful, Con Shark here"}
                                else -> {"0"}
                            },
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            fontSize = 12.sp,
                            color = Color.Blue,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }

            if (!spinningState) {
                Box(Modifier
                    .fillMaxSize(0.24f).aspectRatio(1.0f).clip(DiceShape())
                    .background(if (!computerTurn) {  if (turn.value != 2) { dice } else { brushTransparent } } else { brush2 })) {
                    Image(
                        modifier = Modifier
                            .fillMaxSize(0.6f)
                            .align(Alignment.Center)
                            .clickable {
                                if (!computerTurn && turn.value != 2) {
                                    turn.value = 2
                                    spinningState = true
                                }
                            },
                        painter = painterResource(if (clearCard) { imageHolder[9]} else {imageHolder[imageIndex.value]}),
                        contentDescription = "dice-spinning"
                    )
                }

            } else {
                DiceSpinAnimate(modifier = Modifier.fillMaxSize(0.23f).aspectRatio(1.0f), spinningState, computerTurn)
            }


        }

        if (spinningState) {
            clearCard = true
            computerChosen = false
            computerIndex.value = 9
            playerIndex.value = 9
                LaunchedEffect(Unit) {
                delay(1000)
                spinningState = false
                imageIndex.value = Random.nextInt(0, 8)
                computerChoiceIndex.value = Random.nextInt(0,6)
                clearCard = false
                if (computerTurn) {
                    computerDeciding = true
                    computerIndex.value = imageIndex.value
                    if (computerIndex.value == 7) {
                        computerShark = true
                    }
                } else {
                    playerIndex.value = imageIndex.value
                    if (playerIndex.value == 7) {
                        playerShark = true
                    }
                }
            }
        }

        if (computerDeciding) {
            LaunchedEffect(Unit) {
                delay(1000)
                computerChosen = true
            }
        }

        if (computerChosen) {
            if (computerPlaying) {
                if (computerTurn) {
                    val choice = computerChoices[computerChoiceIndex.value]
                    LaunchedEffect(Unit) {
                        delay(1000)
                        if (computerCards.size > 5) {
                            computerWin = true
                        } else {
                            turn.value = 1
                            clearCard = true
                            computerPlaying = false
                            computerChosen = false
                            computerTurn = false
                            computerDeciding = false
                        }
                    }
                }
            }
        }

        if (computerPlaying) {
            LaunchedEffect(Unit) {
                delay(1000)
                turn.value = 1
                spinningState = true
                cardPicked = false
            }
        }

        if (cardPicked) {
            clearCard = true
            if (!computerTurn) {
                if (playerCards.size > 5) {
                    playerWin = true
                } else {
                    turn.value = 2
                    cardPicked = false
                    spinningState = true
                }
            }
        }

        if (playerShark) {
            LaunchedEffect(Unit) {
                delay(900)
                playerCards.clear()
                playerShark = false
                cardPicked = true
                computerTurn = true
                computerPlaying = true
            }
        }

//        if (computerShark) {
//            LaunchedEffect(Unit) {
//                delay(700)
//                playerCards.clear()
//                playerShark = false
//                cardPicked = true
//                computerTurn = true
//                computerPlaying = true
//            }
//        }

        if (computerWin) {

        }

        if (playerWin) {

        }


        co.touchlab.kermit.Logger.i { "$cardPicked"}
        co.touchlab.kermit.Logger.i ("TAG", null, "$computerTurn")

    }

}
