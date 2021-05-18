package com.sfa.android.moiveapp2.ui.popular

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sfa.android.moiveapp2.BuildConfig
import com.sfa.android.moiveapp2.network.Media
import com.sfa.android.moiveapp2.network.MovieApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class PopularMediaViewModel: ViewModel() {

    private val _movies = MutableLiveData<List<Media>>()
    val movies: LiveData<List<Media>>
        get() = _movies

    private val _series = MutableLiveData<List<Media>>()
    val series: LiveData<List<Media>>
        get() = _series

    private val _navigateToSelectedMedia = MutableLiveData<Media?>()
    val navigateToSelectedMedia: LiveData<Media?>
        get() = _navigateToSelectedMedia

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main )

    init {
        getPopularMovies()
        getPopularSeries()
    }

    fun setCurrentlyClickedMedia(media: Media) {
        _navigateToSelectedMedia.value = media
    }

    fun resetCurrentlyClickedMedia() {
        _navigateToSelectedMedia.value = null
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

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun ratingSort() {
        _movies.value = _movies.value?.sortedBy { it.voteAverage }
        _series.value = _series.value?.sortedBy { it.voteAverage }
    }

    fun releaseDateSort() {
        _movies.value = _movies.value?.sortedBy { it.getMediaReleaseDate() }
        _series.value = _series.value?.sortedBy { it.getMediaReleaseDate() }
    }

    fun titleSort() {
        _movies.value = _movies.value?.sortedBy { it.getMediaTitle() }
        _series.value = _series.value?.sortedBy { it.getMediaTitle() }
    }
}
