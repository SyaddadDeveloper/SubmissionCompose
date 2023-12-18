package com.example.submissioncompose.model

data class Game(
    val id: String,
    val gameName: String,
    val desc: String,
    val price: String,
    val developer: String,
    val bannerUrl: String,
    val releaseDate: String,
    val gameLink: String
)
