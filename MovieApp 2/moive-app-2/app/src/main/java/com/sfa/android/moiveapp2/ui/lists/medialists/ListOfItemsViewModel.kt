package com.sfa.android.moiveapp2.ui.lists.medialists

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sfa.android.moiveapp2.BuildConfig
import com.sfa.android.moiveapp2.network.DeleteList
import com.sfa.android.moiveapp2.network.Media
import com.sfa.android.moiveapp2.network.userlistmodels.MediaListItem
import com.sfa.android.moiveapp2.network.userlistmodels.MediaListItems
import com.sfa.android.moiveapp2.network.MovieApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ListOfItemsViewModel(val listId: Long) : ViewModel() {

    private val _media = MutableLiveData<List<Media>?>()
    val media: LiveData<List<Media>?>
        get() = _media

    private val _success = MutableLiveData<DeleteList>()
    val success: LiveData<DeleteList>
        get() = _success

    private val _deleteListSuccess = MutableLiveData<DeleteList>()
    val deleteListSuccess: LiveData<DeleteList>
        get() = _deleteListSuccess

    private val _currentMediaToDelete = MutableLiveData<List<MediaListItem>>()
    val currentMediaToDelete: LiveData<List<MediaListItem>>
        get() = _currentMediaToDelete

    private val _navigateToAddMedia = MutableLiveData<Long>()
    val navigateToAddMedia: LiveData<Long>
        get() = _navigateToAddMedia

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main )

    init {
        getSingleMediaList()
    }

    fun addMediaToListPressed() {
        _navigateToAddMedia.value = listId
    }

    fun addMediaToListPressedCompleted() {
        _navigateToAddMedia.value = DEFAULT_ID
    }

    fun addItemToList(mediaListItem: MediaListItem){
        _currentMediaToDelete.value = listOf(mediaListItem)
    }

    fun deleteListPressed(){
        deleteList(listId)
    }

    fun getSingleMediaList() {
        coroutineScope.launch {
            val getSingleMediaListDeferred = MovieApi.retrofitService.getSingleMediaListAsync(listId, BuildConfig.MOVIE_DB_API)
            try {
                val listResult =  getSingleMediaListDeferred.await()
                _media.value = listResult.results
            } catch (e: Exception) {
                _media.value = ArrayList()
            }
        }
    }

    fun deleteMediaItemsFromList(list: List<MediaListItem>) {
        coroutineScope.launch {
            val deleteMediaItemsFromListDeferred = MovieApi.retrofitService.deleteMediaItemsFromListAsync(listId, MediaListItems(list), BuildConfig.MOVIE_DB_API)
            try {
                val result = deleteMediaItemsFromListDeferred.await()
                _success.value = result
            } catch (e: Exception) {
                _success.value = DeleteList(false)
            }
        }
    }

    private fun deleteList(id: Long) {
        coroutineScope.launch {
            val deleteListDeferred = MovieApi.retrofitService.deleteListAsync(id)
            try {
                val result = deleteListDeferred.await()
                _deleteListSuccess.value = result
            } catch (e: Exception) {
                _deleteListSuccess.value = DeleteList(false)
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
