package com.sfa.android.moiveapp2.ui.lists.medialists

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sfa.android.moiveapp2.BuildConfig
import com.sfa.android.moiveapp2.network.Media
import com.sfa.android.moiveapp2.network.userlistmodels.MediaListItem
import com.sfa.android.moiveapp2.network.userlistmodels.MediaListItems
import com.sfa.android.moiveapp2.network.MovieApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class AddMediaToListViewModel(val listId: Long) : ViewModel() {

    private val _movies = MutableLiveData<List<Media>>()
    val movies: LiveData<List<Media>>
        get() = _movies

    private val _series = MutableLiveData<List<Media>>()
    val series: LiveData<List<Media>>
        get() = _series

    private val _currentMediaToAdd = MutableLiveData<List<MediaListItem>>()
    val currentMediaToAdd: LiveData<List<MediaListItem>>
        get() = _currentMediaToAdd

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main )

    init {
        getPopularMovies()
        getPopularSeries()
    }

    fun addItemToList(mediaListItem: MediaListItem){
        _currentMediaToAdd.value = listOf(mediaListItem)
    }

    private fun getPopularMovies() {
        coroutineScope.launch {
            val getMoviesDeferred = MovieApi.retrofitService.getPopularMoviesAsync(BuildConfig.MOVIE_DB_API)
            try {
                val listResult =  getMoviesDeferred.await()
                _movies.value = listResult.results
            } catch (e: Exception) {
                _movies.value = ArrayList()
            }
        }
    }

    private fun getPopularSeries() {
        coroutineScope.launch {
            val getSeriesDeferred = MovieApi.retrofitService.getPopularSeriesAsync(BuildConfig.MOVIE_DB_API)
            try {
                val listResult =  getSeriesDeferred.await()
                _series.value = listResult.results
            } catch (e: Exception) {
                _series.value = ArrayList()
            }
        }
    }

    fun addMediaItemsToList(list: List<MediaListItem>) {
        coroutineScope.launch {
            val addMediaItemsToListDeferred = MovieApi.retrofitService.addMediaItemsToListAsync(listId, MediaListItems(list), BuildConfig.MOVIE_DB_API)
            try {
                addMediaItemsToListDeferred.await()
            } catch (e: Exception) {
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}