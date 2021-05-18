package com.sfa.android.movieapp.create_movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sfa.android.movieapp.DrawableList

class CreateMovieViewModel(
    fromEdit: Boolean,
    movieTag: Int,
    movieTitle: String,
    movieYear: String
) : ViewModel() {

    private val _eventMoviePosterPressed = MutableLiveData<Boolean>()
    val eventMoviePosterPressed: LiveData<Boolean>
        get() = _eventMoviePosterPressed

    private val _eventCreateMovieButtonPressed = MutableLiveData<Boolean>()
    val eventCreateMovieButtonPressed: LiveData<Boolean>
        get() = _eventCreateMovieButtonPressed

    var imageValid = false
    var titleValid = false
    var yearValid = false
    var shortDescriptionValid = false
    var genreValid = false
    var longDescriptionValid = false


    var currentImageResource = 0
    val isFromEdit = fromEdit
    val editMovieTag = movieTag
    val editMovieTitle = movieTitle
    val editMovieYear = movieYear

    private var listOfImages = DrawableList.listOfDrawables.toMutableList()

    init {
        _eventMoviePosterPressed.value = false
        _eventCreateMovieButtonPressed.value = false
        addImageToList()
    }

    private fun addImageToList() {
        listOfImages = DrawableList.listOfDrawables.toMutableList()
    }

    fun pressMoviePoster() {
        _eventMoviePosterPressed.value = true
    }

    fun pressMoviePosterEnd() {
        _eventMoviePosterPressed.value = false
    }

    fun pressCreateMovieButton() {
        if (imageValid && titleValid && yearValid && shortDescriptionValid && genreValid && longDescriptionValid) {
            _eventCreateMovieButtonPressed.value = true
        }
    }

    fun pressCreateMovieButtonEnd() {
        _eventCreateMovieButtonPressed.value = false
    }

    fun getDrawableImage(): Int {
        currentImageResource = if (listOfImages.isEmpty()) {
            addImageToList()
            listOfImages.removeAt(0)
        } else {
            listOfImages.removeAt(0)
        }
        return currentImageResource
    }
}
