package com.sfa.android.currentbitcointracker.ui.savedrates

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sfa.android.currentbitcointracker.network.BitcoinDatabaseDao
import com.sfa.android.currentbitcointracker.network.DatabaseCurrency
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ArchiveViewModel(private val database: BitcoinDatabaseDao): ViewModel() {

    private val _archivedCurrencies = MutableLiveData<List<DatabaseCurrency>>()
    val archivedCurrencies: LiveData<List<DatabaseCurrency>>
        get() = _archivedCurrencies

    private var viewModelJob = Job()

    init {
        getAllArchivedCurrencies()
    }

    fun onDeleteButtonPressed(databaseCurrency: DatabaseCurrency) {
        viewModelScope.launch {
            deleteCurrency(databaseCurrency.id)
            getAllArchivedCurrencies()
        }
    }

    fun deleteAllRecords() {
        viewModelScope.launch {
            clearAll()
            getAllArchivedCurrencies()
        }
    }

    private suspend fun clearAll() {
        withContext(Dispatchers.IO){
            database.clear()
        }
    }

    private suspend fun deleteCurrency(key: Long){
        withContext(Dispatchers.IO){
            database.delete(key)
        }
    }

    private fun getAllArchivedCurrencies() {
        viewModelScope.launch {
            val current: List<DatabaseCurrency>
            withContext(Dispatchers.IO) {
                current = database.getAllCurrencies()
            }
            _archivedCurrencies.value = current
        }
    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
