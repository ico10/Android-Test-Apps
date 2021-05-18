package com.sfa.android.movieapp.movie_descriptions

import androidx.annotation.DrawableRes
import androidx.lifecycle.ViewModel

class MovieDetailsViewModel(
    @DrawableRes imageDrawable: Int,
    title: String,
    releaseYear: String,
    shortDescription: String,
    genre: String,
    longDescription: String

) : ViewModel() {

    val modelImageDrawable = imageDrawable
    val modelTitle = title
    val modelReleaseYear = releaseYear
    val modelShortDescription = shortDescription
    val modelGenre = genre
    val modelLongDescription = longDescription
}