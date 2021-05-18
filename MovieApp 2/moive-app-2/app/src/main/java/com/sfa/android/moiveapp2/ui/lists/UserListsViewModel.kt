package com.sfa.android.moiveapp2.ui.lists

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sfa.android.moiveapp2.BuildConfig
import com.sfa.android.moiveapp2.network.DeleteList
import com.sfa.android.moiveapp2.network.userlistmodels.MediaList
import com.sfa.android.moiveapp2.network.MovieApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class UserListsViewModel: ViewModel() {

    private val _mediaLists = MutableLiveData<List<MediaList>>()
    val mediaLists: LiveData<List<MediaList>>
        get() = _mediaLists

    private val _success = MutableLiveData<DeleteList>()
    val success: LiveData<DeleteList>
        get() = _success

    private val _navigateToMediaListItems = MutableLiveData<Long>()
    val navigateToMediaListItems: LiveData<Long>
        get() = _navigateToMediaListItems

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main )

    init {
        getAllMediaLists()
    }

    fun onMediaListPressed(id: Long){
        _navigateToMediaListItems.value = id
    }

    fun onMediaListPressedCompleted(){
        _navigateToMediaListItems.value = DEFAULT_ID
    }

    fun getAllMediaLists() {
        coroutineScope.launch {
            val getMediaListsDeferred = MovieApi.retrofitService.getAllMediaListsAsync(BuildConfig.MOVIE_DB_AID)
            try {
                val listResult =  getMediaListsDeferred.await()
                _mediaLists.value = listResult.results
            } catch (e: Exception) {
                _mediaLists.value = ArrayList()
            }
        }
    }

    fun deleteList(id: Long) {
        coroutineScope.launch {
            val deleteListDeferred = MovieApi.retrofitService.deleteListAsync(id)
            try {
                val result = deleteListDeferred.await()
                _success.value = result
            } catch (e: Exception) {
                _success.value = DeleteList(false)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    companion object{
        const val DEFAULT_ID = 0L
    }
}
