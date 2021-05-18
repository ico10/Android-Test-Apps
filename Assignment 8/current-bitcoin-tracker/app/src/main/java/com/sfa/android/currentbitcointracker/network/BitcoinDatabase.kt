package com.sfa.android.currentbitcointracker.network

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DatabaseCurrency::class], version = 1, exportSchema = false)
abstract class BitcoinDatabase : RoomDatabase() {

    abstract val bitcoinDatabaseDao: BitcoinDatabaseDao

    companion object {

        @Volatile
        private var INSTANCE: BitcoinDatabase? = null

        fun getInstance(context: Context): BitcoinDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        BitcoinDatabase::class.java,
                        "sleep_history_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
