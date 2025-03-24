package com.triad.mobile.data.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.triad.mobile.data.model.Wallet
import com.triad.mobile.data.source.local.converters.DateConverter
import com.triad.mobile.data.source.local.converters.DecimalConverter

/**
 * Главная база данных приложения TRIAD
 */
@Database(
    entities = [Wallet::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(DateConverter::class, DecimalConverter::class)
abstract class AppDatabase : RoomDatabase() {
    
    /**
     * Доступ к DAO для работы с кошельками
     */
    abstract fun walletDao(): WalletDao
    
    companion object {
        private const val DATABASE_NAME = "triad_db"
        
        @Volatile
        private var INSTANCE: AppDatabase? = null
        
        /**
         * Создает или возвращает существующий экземпляр базы данных
         */
        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }
        }
        
        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                DATABASE_NAME
            )
                .fallbackToDestructiveMigration()
                .build()
        }
    }
} 