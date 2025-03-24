package com.triad.mobile.di

import com.triad.mobile.domain.crypto.CryptoManager
import com.triad.mobile.domain.crypto.CryptoManagerImpl
import com.triad.mobile.domain.crypto.WalletGenerator
import com.triad.mobile.domain.crypto.WalletGeneratorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt модуль для предоставления криптографических компонентов
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class CryptoModule {
    
    /**
     * Предоставляет реализацию CryptoManager
     */
    @Singleton
    @Binds
    abstract fun bindCryptoManager(impl: CryptoManagerImpl): CryptoManager
    
    /**
     * Предоставляет реализацию WalletGenerator
     */
    @Singleton
    @Binds
    abstract fun bindWalletGenerator(impl: WalletGeneratorImpl): WalletGenerator
} 