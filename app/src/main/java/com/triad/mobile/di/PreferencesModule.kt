package com.triad.mobile.di

import android.content.Context
import com.triad.mobile.data.source.local.preferences.UserPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt модуль для предоставления доступа к пользовательским настройкам
 */
@Module
@InstallIn(SingletonComponent::class)
object PreferencesModule {
    
    /**
     * Предоставляет экземпляр UserPreferences
     */
    @Singleton
    @Provides
    fun provideUserPreferences(@ApplicationContext context: Context): UserPreferences {
        return UserPreferences(context)
    }
} 