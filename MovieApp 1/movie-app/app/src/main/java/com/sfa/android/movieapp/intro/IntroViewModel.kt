package com.sfa.android.movieapp.intro

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class IntroViewModel : ViewModel() {

    private val _showButtonPressed = MutableLiveData<Boolean>()
    val showButtonPressed: LiveData<Boolean>
        get() = _showButtonPressed

    private val _addMovieButtonPressed = MutableLiveData<Boolean>()
    val addMovieButtonPressed: LiveData<Boolean>
        get() = _addMovieButtonPressed

    init {
        _showButtonPressed.value = false
        _addMovieButtonPressed.value = false
    }

    fun pressShowButton() {
        _showButtonPressed.value = true
    }

    fun pressShowButtonEnd() {
        _showButtonPressed.value = false
    }

    fun pressAddMovieButton() {
        _addMovieButtonPressed.value = true
    }

    fun pressAddMovieButtonEnd() {
        _addMovieButtonPressed.value = false
    }
}
