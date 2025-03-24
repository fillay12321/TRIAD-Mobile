package com.triad.mobile.data.source.local.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "triad_preferences")

/**
 * Класс для работы с пользовательскими настройками
 */
@Singleton
class UserPreferences @Inject constructor(
    @ApplicationContext private val context: Context
) {
    
    private val dataStore = context.dataStore
    
    /**
     * Ключи для хранения настроек
     */
    companion object {
        private val ONBOARDING_COMPLETED = booleanPreferencesKey("onboarding_completed")
        private val ACTIVE_WALLET_ID = stringPreferencesKey("active_wallet_id")
        private val THEME_MODE = stringPreferencesKey("theme_mode")
        private val BIOMETRIC_AUTH_ENABLED = booleanPreferencesKey("biometric_auth_enabled")
    }
    
    /**
     * Получает статус прохождения онбординга
     */
    suspend fun isOnboardingCompleted(): Boolean {
        return try {
            dataStore.data.first()[ONBOARDING_COMPLETED] ?: false
        } catch (e: Exception) {
            Timber.e(e, "Ошибка при получении статуса онбординга")
            false
        }
    }
    
    /**
     * Устанавливает статус прохождения онбординга
     */
    suspend fun setOnboardingCompleted(completed: Boolean) {
        dataStore.edit { preferences ->
            preferences[ONBOARDING_COMPLETED] = completed
        }
    }
    
    /**
     * Получает идентификатор активного кошелька
     */
    suspend fun getActiveWalletId(): String? {
        return try {
            dataStore.data.first()[ACTIVE_WALLET_ID]
        } catch (e: Exception) {
            Timber.e(e, "Ошибка при получении ID активного кошелька")
            null
        }
    }
    
    /**
     * Устанавливает идентификатор активного кошелька
     */
    suspend fun setActiveWalletId(walletId: String?) {
        dataStore.edit { preferences ->
            if (walletId != null) {
                preferences[ACTIVE_WALLET_ID] = walletId
            } else {
                preferences.remove(ACTIVE_WALLET_ID)
            }
        }
    }
    
    /**
     * Следит за изменениями идентификатора активного кошелька
     */
    fun observeActiveWalletId(): Flow<String?> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    Timber.e(exception, "Ошибка при чтении настроек")
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                preferences[ACTIVE_WALLET_ID]
            }
    }
    
    /**
     * Получает выбранный режим темы
     */
    suspend fun getThemeMode(): String {
        return try {
            dataStore.data.first()[THEME_MODE] ?: "system"
        } catch (e: Exception) {
            Timber.e(e, "Ошибка при получении режима темы")
            "system"
        }
    }
    
    /**
     * Устанавливает режим темы
     */
    suspend fun setThemeMode(mode: String) {
        dataStore.edit { preferences ->
            preferences[THEME_MODE] = mode
        }
    }
    
    /**
     * Проверяет, включена ли биометрическая аутентификация
     */
    suspend fun isBiometricAuthEnabled(): Boolean {
        return try {
            dataStore.data.first()[BIOMETRIC_AUTH_ENABLED] ?: false
        } catch (e: Exception) {
            Timber.e(e, "Ошибка при получении статуса биометрической аутентификации")
            false
        }
    }
    
    /**
     * Включает или отключает биометрическую аутентификацию
     */
    suspend fun setBiometricAuthEnabled(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[BIOMETRIC_AUTH_ENABLED] = enabled
        }
    }
    
    private fun emptyPreferences() = Preferences.EMPTY
} 