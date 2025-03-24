package com.triad.mobile.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.triad.mobile.ui.screens.home.HomeScreen
import com.triad.mobile.ui.screens.onboarding.OnboardingScreen
import com.triad.mobile.ui.screens.splash.SplashScreen
import com.triad.mobile.ui.screens.wallet.create.CreateWalletScreen
import com.triad.mobile.ui.screens.wallet.import.ImportWalletScreen
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Основные экраны навигации
 */
sealed class Screen(val route: String) {
    data object Splash : Screen("splash")
    data object Onboarding : Screen("onboarding")
    data object Home : Screen("home")
    data object CreateWallet : Screen("create_wallet")
    data object ImportWallet : Screen("import_wallet")
    data object WalletDetails : Screen("wallet_details/{walletId}") {
        fun createRoute(walletId: String) = "wallet_details/$walletId"
    }
    data object SendTransaction : Screen("send_transaction")
    data object ReceiveTransaction : Screen("receive_transaction")
    data object Settings : Screen("settings")
}

/**
 * Основной хост навигации для приложения
 */
@Composable
fun NavigationHost(navController: NavHostController) {
    val coroutineScope = rememberCoroutineScope()
    
    // Настроим начальный экран - Splash
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        // Экран заставки
        composable(Screen.Splash.route) {
            SplashScreen(
                navigateToOnboarding = {
                    navController.navigate(Screen.Onboarding.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                },
                navigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            )
        }
        
        // Экран онбординга
        composable(Screen.Onboarding.route) {
            OnboardingScreen(
                navigateToCreateWallet = {
                    navController.navigate(Screen.CreateWallet.route)
                },
                navigateToImportWallet = {
                    navController.navigate(Screen.ImportWallet.route)
                }
            )
        }
        
        // Экран создания кошелька
        composable(Screen.CreateWallet.route) {
            // Временная заглушка
            CreateWalletScreen(
                onWalletCreated = { walletId ->
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Onboarding.route) { inclusive = true }
                    }
                },
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        
        // Экран импорта кошелька
        composable(Screen.ImportWallet.route) {
            // Временная заглушка
            ImportWalletScreen(
                onWalletImported = { walletId ->
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Onboarding.route) { inclusive = true }
                    }
                },
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        
        // Главный экран
        composable(Screen.Home.route) {
            // Временная заглушка
            HomeScreen(
                navigateToSend = {
                    navController.navigate(Screen.SendTransaction.route)
                },
                navigateToReceive = {
                    navController.navigate(Screen.ReceiveTransaction.route)
                },
                navigateToSettings = {
                    navController.navigate(Screen.Settings.route)
                },
                navigateToWalletDetails = { walletId ->
                    navController.navigate(Screen.WalletDetails.createRoute(walletId))
                }
            )
        }
        
        // Здесь можно добавить другие экраны по мере необходимости
    }
} 