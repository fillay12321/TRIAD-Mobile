package com.triad.mobile.di

import com.triad.mobile.data.repository.UserPreferencesRepository
import com.triad.mobile.data.repository.UserPreferencesRepositoryImpl
import com.triad.mobile.data.repository.WalletRepository
import com.triad.mobile.data.repository.WalletRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt модуль для предоставления репозиториев в приложении
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    
    /**
     * Связывает реализацию репозитория пользовательских настроек с его интерфейсом
     */
    @Binds
    @Singleton
    abstract fun bindUserPreferencesRepository(
        impl: UserPreferencesRepositoryImpl
    ): UserPreferencesRepository
    
    /**
     * Предоставляет реализацию WalletRepository
     */
    @Singleton
    @Binds
    abstract fun bindWalletRepository(impl: WalletRepositoryImpl): WalletRepository
} 