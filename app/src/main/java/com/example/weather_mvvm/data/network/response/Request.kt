package com.example.weather_mvvm.data.network.response


data class Request(
    val language: String,
    val query: String,
    val type: String,
    val unit: String
)