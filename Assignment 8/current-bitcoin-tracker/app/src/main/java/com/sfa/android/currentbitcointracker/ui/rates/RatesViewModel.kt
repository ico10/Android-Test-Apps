package com.sfa.android.currentbitcointracker.ui.rates

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sfa.android.currentbitcointracker.network.BitcoinApi
import com.sfa.android.currentbitcointracker.network.BitcoinDatabaseDao
import com.sfa.android.currentbitcointracker.network.DatabaseCurrency
import com.sfa.android.currentbitcointracker.network.MyCurrency
import kotlinx.coroutines.*
import java.lang.Exception

class RatesViewModel(private val database: BitcoinDatabaseDao) : ViewModel() {

    private val _time = MutableLiveData<String>()
    val time: LiveData<String>
        get() = _time

    private var _usd = MutableLiveData<MyCurrency>()
    val usd: LiveData<MyCurrency>
        get() = _usd

    private var _eur = MutableLiveData<MyCurrency>()
    val eur: LiveData<MyCurrency>
        get() = _eur

    private var _gbp = MutableLiveData<MyCurrency>()
    val gbp: LiveData<MyCurrency>
        get() = _gbp

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        getBitcoinIndex()
    }

    fun onAddToArchivePressed(newCurrency: DatabaseCurrency) {
        viewModelScope.launch {
            insert(newCurrency)
        }
    }

    fun getCurrencyPressed(code: String): DatabaseCurrency {
        val currentDatabaseCurrency = DatabaseCurrency()
        _time.value?.let { currentDatabaseCurrency.setCurrentTime(it) }
        when(code){
            CurrencyCode.USD.code -> {
                usd.value?.let { currentDatabaseCurrency.setRate(it.rate) }
                usd.value?.let { currentDatabaseCurrency.setCode(it.code) }
            }
            CurrencyCode.EUR.code -> {
                eur.value?.let { currentDatabaseCurrency.setRate(it.rate) }
                eur.value?.let { currentDatabaseCurrency.setCode(it.code) }
            }
            CurrencyCode.GBP.code -> {
                gbp.value?.let { currentDatabaseCurrency.setRate(it.rate) }
                gbp.value?.let { currentDatabaseCurrency.setCode(it.code) }
            }
        }


        return currentDatabaseCurrency
    }

    private fun getBitcoinIndex() {
        coroutineScope.launch {
            val getPriceIndexDeferred = BitcoinApi.retrofitService.getPriceIndexAsync()
            try {
                val result = getPriceIndexDeferred.await()
                _time.value = result.time.updated
                _usd.value = result.bpi.USD
                _eur.value = result.bpi.EUR
                _gbp.value = result.bpi.GBP
            } catch (e: Exception) {
                _time.value = ""
                Log.d(TAG, API_ERROR_MESSAGE)
            }
        }
    }

    private suspend fun insert(databaseCurrency: DatabaseCurrency) {
        withContext(Dispatchers.IO) {
            database.insert(databaseCurrency)
        }
    }

    fun refreshBitcoinIndex() {
        getBitcoinIndex()
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    companion object{
        private const val TAG = "RatesViewModel"
        private const val API_ERROR_MESSAGE = "Api error"
    }
}
