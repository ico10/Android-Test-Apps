package com.sfa.android.movieapp.create_movie

import androidx.annotation.DrawableRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CreateMovieViewModelFactory(
    private val isForEdit: Boolean,
    @DrawableRes private val movieTag: Int,
    private val movieTitle: String,
    private val movieYear: String
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CreateMovieViewModel::class.java)) {
            return CreateMovieViewModel(isForEdit, movieTag, movieTitle, movieYear) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
