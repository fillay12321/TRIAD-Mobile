package com.triad.mobile.data.repository

import kotlinx.coroutines.flow.Flow

/**
 * Репозиторий для хранения и управления пользовательскими настройками приложения
 */
interface UserPreferencesRepository {
    
    /**
     * Получает статус прохождения онбординга
     * 
     * @return true, если пользователь завершил онбординг
     */
    suspend fun isOnboardingCompleted(): Boolean
    
    /**
     * Устанавливает статус прохождения онбординга
     * 
     * @param completed true, если пользователь завершил онбординг
     */
    suspend fun setOnboardingCompleted(completed: Boolean)
    
    /**
     * Получает идентификатор текущего активного кошелька
     * 
     * @return адрес кошелька или null, если активный кошелек не выбран
     */
    suspend fun getCurrentWalletAddress(): String?
    
    /**
     * Устанавливает идентификатор текущего активного кошелька
     * 
     * @param address адрес кошелька
     */
    suspend fun setCurrentWalletAddress(address: String)
    
    /**
     * Получает выбранную пользователем валюту для отображения
     * 
     * @return код валюты (например, USD, EUR, RUB)
     */
    suspend fun getPreferredCurrency(): String
    
    /**
     * Устанавливает предпочитаемую пользователем валюту
     * 
     * @param currencyCode код валюты (например, USD, EUR, RUB)
     */
    suspend fun setPreferredCurrency(currencyCode: String)
    
    /**
     * Получает настройку использования биометрической аутентификации
     * 
     * @return true, если биометрическая аутентификация включена
     */
    suspend fun isBiometricEnabled(): Boolean
    
    /**
     * Включает или отключает биометрическую аутентификацию
     * 
     * @param enabled true для включения биометрической аутентификации
     */
    suspend fun setBiometricEnabled(enabled: Boolean)
    
    /**
     * Получает настройку темы приложения
     * 
     * @return ThemeMode (LIGHT, DARK или SYSTEM)
     */
    suspend fun getThemeMode(): ThemeMode
    
    /**
     * Устанавливает предпочитаемую тему приложения
     * 
     * @param themeMode ThemeMode (LIGHT, DARK или SYSTEM)
     */
    suspend fun setThemeMode(themeMode: ThemeMode)
    
    /**
     * Получает интервал автоблокировки приложения в минутах
     * 
     * @return интервал в минутах или 0, если автоблокировка отключена
     */
    suspend fun getAutoLockTimeout(): Int
    
    /**
     * Устанавливает интервал автоблокировки приложения
     * 
     * @param timeoutMinutes интервал в минутах или 0 для отключения
     */
    suspend fun setAutoLockTimeout(timeoutMinutes: Int)
    
    /**
     * Наблюдает за изменениями настроек темы
     * 
     * @return Flow с текущим режимом темы
     */
    fun observeThemeMode(): Flow<ThemeMode>
}

/**
 * Перечисление режимов темы приложения
 */
enum class ThemeMode {
    LIGHT,
    DARK,
    SYSTEM
} 