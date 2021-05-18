package com.sfa.android.currentbitcointracker.network

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bitcoin_currency_table")
data class DatabaseCurrency(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,

    @ColumnInfo(name = "current_time")
    private var currentTime: String? ="",

    @ColumnInfo(name = "code")
    private var code: String? ="",

    @ColumnInfo(name = "rate")
    private var rate: String?="",
) {
    fun setCurrentTime(time: String){
        currentTime = time
    }

    fun setCode(code: String){
        this.code = code
    }

    fun setRate(rate: String){
        this.rate = rate
    }

    fun getCurrentTime(): String? {
        return currentTime
    }

    fun getCode(): String? {
        return code
    }

    fun getRate(): String? {
        return rate
    }
}
