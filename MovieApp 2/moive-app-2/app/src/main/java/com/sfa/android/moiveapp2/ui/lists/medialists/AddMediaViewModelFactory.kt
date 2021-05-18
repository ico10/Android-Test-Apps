package com.sfa.android.moiveapp2.ui.lists.medialists

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class AddMediaViewModelFactory(
    private val listId: Long,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddMediaToListViewModel::class.java)) {
            return AddMediaToListViewModel(listId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}