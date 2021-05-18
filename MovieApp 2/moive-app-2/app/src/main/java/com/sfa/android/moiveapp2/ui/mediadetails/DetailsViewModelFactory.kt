package com.sfa.android.moiveapp2.ui.mediadetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sfa.android.moiveapp2.network.Media

class DetailsViewModelFactory(
    private val media: Media,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailsViewModel::class.java)) {
            return DetailsViewModel(media) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
