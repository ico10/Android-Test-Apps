package com.sfa.android.moiveapp2.ui.mediadetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sfa.android.moiveapp2.BuildConfig
import com.sfa.android.moiveapp2.network.*
import com.sfa.android.moiveapp2.network.moviedetailsmodel.Review
import com.sfa.android.moiveapp2.ui.popular.MediaType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class DetailsViewModel(val media: Media) : ViewModel() {

    private val _currentMedia = MutableLiveData<Media>()
    val currentMedia: LiveData<Media>
        get() = _currentMedia

    private val _currentMediaReviews = MutableLiveData<List<Review>>()
    val currentMediaReviews: LiveData<List<Review>>
        get() = _currentMediaReviews

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main )

    init {
        if (media.getMediaType() == MediaType.MOVIE.type){
            getMovie()
            getMovieReviews()
        } else {
            getSeries()
            getSeriesReviews()
        }
    }

    private fun getMovie() {
        coroutineScope.launch {
            val getMovieDeferred = MovieApi.retrofitService.getMovieAsync(media.id, BuildConfig.MOVIE_DB_API, VIDEOS_QUERY)
            try {
                val result = getMovieDeferred.await()
                _currentMedia.value = result
            } catch (e: Exception) {

            }
        }
    }

    private fun getSeries() {
        coroutineScope.launch {
            val getSeriesDeferred = MovieApi.retrofitService.getSeriesAsync(media.id, BuildConfig.MOVIE_DB_API, VIDEOS_QUERY)
            try {
                val result = getSeriesDeferred.await()
                _currentMedia.value = result
            } catch (e: Exception) {

            }
        }
    }

    private fun getMovieReviews() {
        coroutineScope.launch {
            val getMovieReviewsDeferred = MovieApi.retrofitService.getMovieReviewsAsync(media.id, BuildConfig.MOVIE_DB_API)
            try {
                val result = getMovieReviewsDeferred.await()
                _currentMediaReviews.value = result.results
            } catch (e: Exception) {

            }
        }
    }

    private fun getSeriesReviews() {
        coroutineScope.launch {
            val getSeriesReviewsDeferred = MovieApi.retrofitService.getSeriesReviewsAsync(media.id, BuildConfig.MOVIE_DB_API)
            try {
                val result = getSeriesReviewsDeferred.await()
                _currentMediaReviews.value = result.results
            } catch (e: Exception) {

            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    companion object{
        const val VIDEOS_QUERY = "videos"
    }
}
