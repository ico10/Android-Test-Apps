package com.sfa.android.moiveapp2.ui.lists.medialists

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ListOfItemsFactory(
    private val listId: Long,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListOfItemsViewModel::class.java)) {
            return ListOfItemsViewModel(listId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}