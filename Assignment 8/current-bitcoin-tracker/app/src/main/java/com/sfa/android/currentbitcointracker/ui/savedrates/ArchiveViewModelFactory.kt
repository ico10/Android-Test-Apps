package com.sfa.android.currentbitcointracker.ui.savedrates

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sfa.android.currentbitcointracker.network.BitcoinDatabaseDao

class ArchiveViewModelFactory(
    private val dataSource: BitcoinDatabaseDao
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ArchiveViewModel::class.java)) {
            return ArchiveViewModel(dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
