package com.sfa.android.concentration4.gamewin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class GameWinViewModelFactory(
    private val attempts: Int
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GameWinViewModel::class.java)) {
            return GameWinViewModel(attempts) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
