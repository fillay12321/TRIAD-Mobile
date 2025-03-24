package com.triad.mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.triad.mobile.ui.navigation.NavigationHost
import com.triad.mobile.ui.theme.TriadTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

/**
 * Главная активность приложения TRIAD
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        // Установка splash screen (требует зависимость и настройку темы)
        // installSplashScreen()
        
        super.onCreate(savedInstanceState)
        
        setContent {
            val navController = rememberNavController()
            
            // Небольшая задержка перед отображением UI для имитации splash screen, 
            // если настоящий splash еще не настроен
            LaunchedEffect(key1 = true) {
                delay(300)
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