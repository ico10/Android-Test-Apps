package com.sfa.android.concentration3.score

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class GameWinViewModelFactory(private val attempts: Int) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GameWinViewModel::class.java)) {
            return GameWinViewModel(attempts) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}