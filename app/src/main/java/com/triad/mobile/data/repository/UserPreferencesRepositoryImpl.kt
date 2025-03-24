package com.triad.mobile.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

// Расширение для Context для доступа к DataStore
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "triad_preferences")

/**
 * Реализация репозитория пользовательских настроек с использованием DataStore
 */
@Singleton
class UserPreferencesRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : UserPreferencesRepository {
    
    companion object {
        // Ключи для доступа к настройкам
        private val ONBOARDING_COMPLETED = booleanPreferencesKey("onboarding_completed")
        private val CURRENT_WALLET_ADDRESS = stringPreferencesKey("current_wallet_address")
        private val PREFERRED_CURRENCY = stringPreferencesKey("preferred_currency")
        private val BIOMETRIC_ENABLED = booleanPreferencesKey("biometric_enabled")
        private val THEME_MODE = stringPreferencesKey("theme_mode")
        private val AUTO_LOCK_TIMEOUT = intPreferencesKey("auto_lock_timeout")
        
        // Значения по умолчанию
        private const val DEFAULT_CURRENCY = "USD"
        private const val DEFAULT_AUTO_LOCK_TIMEOUT = 5 // 5 минут
    }
    
    override suspend fun isOnboardingCompleted(): Boolean {
        return context.dataStore.data.map { preferences ->
            preferences[ONBOARDING_COMPLETED] ?: false
        }.collect { it }
    }
    
    override suspend fun setOnboardingCompleted(completed: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[ONBOARDING_COMPLETED] = completed
        }
    }
    
    override suspend fun getCurrentWalletAddress(): String? {
        return context.dataStore.data.map { preferences ->
            preferences[CURRENT_WALLET_ADDRESS]
        }.collect { it }
    }
    
    override suspend fun setCurrentWalletAddress(address: String) {
        context.dataStore.edit { preferences ->
            preferences[CURRENT_WALLET_ADDRESS] = address
        }
    }
    
    override suspend fun getPreferredCurrency(): String {
        return context.dataStore.data.map { preferences ->
            preferences[PREFERRED_CURRENCY] ?: DEFAULT_CURRENCY
        }.collect { it }
    }
    
    override suspend fun setPreferredCurrency(currencyCode: String) {
        context.dataStore.edit { preferences ->
            preferences[PREFERRED_CURRENCY] = currencyCode
        }
    }
    
    override suspend fun isBiometricEnabled(): Boolean {
        return context.dataStore.data.map { preferences ->
            preferences[BIOMETRIC_ENABLED] ?: false
        }.collect { it }
    }
    
    override suspend fun setBiometricEnabled(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[BIOMETRIC_ENABLED] = enabled
        }
    }
    
    override suspend fun getThemeMode(): ThemeMode {
        val themeModeString = context.dataStore.data.map { preferences ->
            preferences[THEME_MODE] ?: ThemeMode.SYSTEM.name
        }.collect { it }
        
        return try {
            ThemeMode.valueOf(themeModeString)
        } catch (e: IllegalArgumentException) {
            ThemeMode.SYSTEM
        }
    }
    
    override suspend fun setThemeMode(themeMode: ThemeMode) {
        context.dataStore.edit { preferences ->
            preferences[THEME_MODE] = themeMode.name
        }
    }
    
    override suspend fun getAutoLockTimeout(): Int {
        return context.dataStore.data.map { preferences ->
            preferences[AUTO_LOCK_TIMEOUT] ?: DEFAULT_AUTO_LOCK_TIMEOUT
        }.collect { it }
    }
    
    override suspend fun setAutoLockTimeout(timeoutMinutes: Int) {
        context.dataStore.edit { preferences ->
            preferences[AUTO_LOCK_TIMEOUT] = timeoutMinutes
        }
    }
    
    override fun observeThemeMode(): Flow<ThemeMode> {
        return context.dataStore.data.map { preferences ->
            val themeModeString = preferences[THEME_MODE] ?: ThemeMode.SYSTEM.name
            try {
                ThemeMode.valueOf(themeModeString)
            } catch (e: IllegalArgumentException) {
                ThemeMode.SYSTEM
            }
        }
    }
} 