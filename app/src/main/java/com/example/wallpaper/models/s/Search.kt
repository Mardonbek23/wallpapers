package com.example.wallpaper.models.s

data class Search(
    val results: List<Result>,
    val total: Int,
    val total_pages: Int
)