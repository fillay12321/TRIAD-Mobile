package com.triad.mobile.di

import android.content.Context
import com.triad.mobile.data.source.local.AppDatabase
import com.triad.mobile.data.source.local.WalletDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt модуль для предоставления компонентов базы данных
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    
    /**
     * Предоставляет экземпляр базы данных
     */
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }
    
    /**
     * Предоставляет DAO для работы с кошельками
     */
    @Singleton
    @Provides
    fun provideWalletDao(database: AppDatabase): WalletDao {
        return database.walletDao()
    }
} 