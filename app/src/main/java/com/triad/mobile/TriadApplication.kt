package com.triad.mobile

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import java.security.Security
import org.bouncycastle.jce.provider.BouncyCastleProvider

/**
 * Основной класс приложения TRIAD
 */
@HiltAndroidApp
class TriadApplication : Application() {
    
    override fun onCreate() {
        super.onCreate()
        
        // Инициализация BouncyCastle для криптографических операций
        setupBouncyCastle()
        
        // Настройка логирования
        setupTimber()
    }
    
    /**
     * Настраивает криптографический провайдер BouncyCastle
     */
    private fun setupBouncyCastle() {
        // Добавление BouncyCastle как провайдера безопасности, если еще не добавлен
        if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
            Security.addProvider(BouncyCastleProvider())
        }
    }
    
    /**
     * Настраивает логирование через Timber
     */
    private fun setupTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            // В релизной версии можно использовать собственную реализацию
            // для отправки логов на сервер или другой обработки
            // Например: Timber.plant(CrashReportingTree())
        }
    }
} 