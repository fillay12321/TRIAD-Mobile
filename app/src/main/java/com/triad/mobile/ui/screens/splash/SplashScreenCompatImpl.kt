package com.triad.mobile.ui.screens.splash

import android.app.Activity
import android.os.Build
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

/**
 * Класс-обертка для работы с SplashScreen API
 * Обеспечивает совместимость с разными версиями Android
 */
class SplashScreenCompatImpl {
    companion object {
        /**
         * Устанавливает splash screen для активности
         * @param activity Активность, где нужно установить splash screen
         * @return SplashScreen объект или null на более ранних версиях Android
         */
        fun installSplashScreen(activity: Activity): SplashScreen? {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                activity.installSplashScreen()
            } else {
                null
            }
        }

        /**
         * Устанавливает задержку для отображения splash screen
         * @param splashScreen SplashScreen объект
         * @param keepOnScreenCondition условие для удержания splash screen на экране
         */
        fun setKeepOnScreenCondition(splashScreen: SplashScreen?, keepOnScreenCondition: () -> Boolean) {
            splashScreen?.setKeepOnScreenCondition(keepOnScreenCondition)
        }
    }
} 