package com.sfa.android.movieapp.model

import androidx.annotation.DrawableRes

data class Movie(
    @DrawableRes val imageDrawable: Int,
    val title: String,
    val year: String,
    var shortDescription: String,
    var genre: String,
    var longDescription: String
)