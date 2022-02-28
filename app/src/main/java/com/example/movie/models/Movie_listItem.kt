package com.example.movie.models

data class Movie_listItem(
    val CAST: String,
    val CATEGORIES: List<CategoryItem>,
    val COVER: String,
    val DESCRIPTION: String,
    val NAME: String,
    val id: Int,
    val ratings: List<Rating>,
    val reviews: List<Review>,
    val rating:Double
)