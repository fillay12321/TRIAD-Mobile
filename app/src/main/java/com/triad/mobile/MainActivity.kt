package com.triad.mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.triad.mobile.ui.navigation.NavigationHost
import com.triad.mobile.ui.screens.splash.SplashScreenCompatImpl
import com.triad.mobile.ui.theme.TriadTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

/**
 * Главная активность приложения TRIAD
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    
    private var splashScreenVisible by mutableStateOf(true)
    
    override fun onCreate(savedInstanceState: Bundle?) {
        // Установка splash screen с совместимым решением
        val splashScreen = SplashScreenCompatImpl.installSplashScreen(this)
        SplashScreenCompatImpl.setKeepOnScreenCondition(splashScreen) {
            splashScreenVisible
        }
        
        super.onCreate(savedInstanceState)
        
        setContent {
            val navController = rememberNavController()
            
            // Небольшая задержка перед скрытием splash screen
            LaunchedEffect(key1 = true) {
                delay(1000) // 1 секунда задержки
                splashScreenVisible = false
            }
            
            TriadTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavigationHost(navController = navController)
                }
            }
        }
    }
} 