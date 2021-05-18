package com.sfa.android.movieapp.movie_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MovieListViewModel: ViewModel() {

    private val _eventAddMoviePressed = MutableLiveData<Boolean>()
    val eventAddMoviePressed: LiveData<Boolean>
        get() = _eventAddMoviePressed

    private val _isMoviePopulated = MutableLiveData<Boolean>()
    val isMoviePopulated: LiveData<Boolean>
        get() = _isMoviePopulated

    private val _isSorted = MutableLiveData<Boolean>()
    val isSorted: LiveData<Boolean>
        get() = _isSorted

    private val _isFilteredBy = MutableLiveData<String>()
    val isFilteredBy: LiveData<String>
        get() = _isFilteredBy

    init {
        _eventAddMoviePressed.value = false
        _isMoviePopulated.value = false
        _isSorted.value = false
        _isFilteredBy.value = NO_FILTER
    }

    fun addMovieClick() {
        _eventAddMoviePressed.value = true
    }

    fun addMovieClickEnd() {
        _eventAddMoviePressed.value = false
    }

    fun clickOnMovie() {
        _isMoviePopulated.value = true
    }

    fun clickOnMovieEnd() {
        _isMoviePopulated.value = false
    }

    fun actionSort() {
        _isSorted.value = true
    }

    fun actionSortEnd() {
        _isSorted.value = false
    }

    fun addFilter(title: String) {
        _isFilteredBy.value = title
    }

    companion object{
        private const val NO_FILTER = "None"
    }
}
