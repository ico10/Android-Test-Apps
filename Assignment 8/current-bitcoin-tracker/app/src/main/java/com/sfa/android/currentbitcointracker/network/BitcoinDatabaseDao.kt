package com.sfa.android.currentbitcointracker.network

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface BitcoinDatabaseDao {
    @Insert
    suspend fun insert(databaseCurrency: DatabaseCurrency)

    @Update
    suspend fun update(databaseCurrency: DatabaseCurrency)

    @Query("DELETE FROM bitcoin_currency_table WHERE id = :key")
    suspend fun delete(key: Long)

    @Query("DELETE FROM bitcoin_currency_table")
    suspend fun clear()

    @Query("SELECT * FROM bitcoin_currency_table ORDER BY id DESC")
    fun getAllCurrencies(): List<DatabaseCurrency>
}
