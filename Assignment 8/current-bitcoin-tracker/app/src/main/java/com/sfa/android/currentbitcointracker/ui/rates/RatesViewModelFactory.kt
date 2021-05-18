package com.sfa.android.currentbitcointracker.ui.rates

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sfa.android.currentbitcointracker.network.BitcoinDatabaseDao

class RatesViewModelFactory(
    private val dataSource: BitcoinDatabaseDao
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RatesViewModel::class.java)) {
            return RatesViewModel(dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
