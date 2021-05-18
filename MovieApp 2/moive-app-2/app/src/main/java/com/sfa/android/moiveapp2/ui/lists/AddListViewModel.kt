package com.sfa.android.moiveapp2.ui.lists

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sfa.android.moiveapp2.BuildConfig
import com.sfa.android.moiveapp2.network.CreateList
import com.sfa.android.moiveapp2.network.DeleteList
import com.sfa.android.moiveapp2.network.MovieApi
import com.sfa.android.moiveapp2.network.userlistmodels.SubmitableMediaList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class AddListViewModel: ViewModel() {

    private val _success = MutableLiveData<CreateList>()
    val success: LiveData<CreateList>
        get() = _success

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main )

    fun pressCreateButton(text: String) {
        val currentList = SubmitableMediaList(text)
        createList(currentList)
    }

    private fun createList(mediaList: SubmitableMediaList) {
        coroutineScope.launch {
            val createListDeferred = MovieApi.retrofitService.createListAsync(mediaList, BuildConfig.MOVIE_DB_API)
            try {
                val result = createListDeferred.await()
                _success.value = result
            } catch (e: Exception) {
                _success.value = CreateList(DEFAULT_ID, false)
            }
        }
    }

    fun resetSuccess() {
        _success.value = CreateList(DEFAULT_ID, false)
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    companion object{
        const val DEFAULT_ID = 0L
    }
}
