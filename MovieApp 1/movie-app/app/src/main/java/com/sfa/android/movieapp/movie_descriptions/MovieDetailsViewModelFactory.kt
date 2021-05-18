package com.sfa.android.movieapp.movie_descriptions

import androidx.annotation.DrawableRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MovieDetailsViewModelFactory(
    @DrawableRes private val imageDrawable: Int,
    private val title: String,
    private val releaseYear: String,
    private val shortDescription: String,
    private val genre: String,
    private val longDescription: String
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieDetailsViewModel::class.java)) {
            return MovieDetailsViewModel(
                imageDrawable,
                title,
                releaseYear,
                shortDescription,
                genre,
                longDescription
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
