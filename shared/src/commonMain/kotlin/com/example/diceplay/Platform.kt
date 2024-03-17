package com.example.diceplay

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform