package com.triad.mobile.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.triad.mobile.ui.screens.onboarding.OnboardingScreen
import com.triad.mobile.ui.screens.splash.SplashScreen

/**
 * Основные маршруты приложения
 */
sealed class AppDestination(val route: String) {
    data object Splash : AppDestination("splash")
    data object Onboarding : AppDestination("onboarding")
    data object Home : AppDestination("home")
    data object CreateWallet : AppDestination("create_wallet")
    data object ImportWallet : AppDestination("import_wallet")
}

/**
 * Основная навигация приложения
 */
@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController(),
    startDestination: String = AppDestination.Splash.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        // Экран заставки
        splashRoute(
            navigateToOnboarding = {
                navController.navigate(AppDestination.Onboarding.route) {
                    popUpTo(AppDestination.Splash.route) { inclusive = true }
                }
            },
            navigateToHome = {
                navController.navigate(AppDestination.Home.route) {
                    popUpTo(AppDestination.Splash.route) { inclusive = true }
                }
            }
        )
        
        // Экран онбординга
        onboardingRoute(
            navigateToCreateWallet = {
                navController.navigate(AppDestination.CreateWallet.route)
            },
            navigateToImportWallet = {
                navController.navigate(AppDestination.ImportWallet.route)
            }
        )
        
        // Остальные экраны будут добавлены позже
    }
}

/**
 * Маршрут для экрана заставки
 */
private fun NavGraphBuilder.splashRoute(
    navigateToOnboarding: () -> Unit,
    navigateToHome: () -> Unit
) {
    composable(route = AppDestination.Splash.route) {
        SplashScreen(
            navigateToOnboarding = navigateToOnboarding,
            navigateToHome = navigateToHome
        )
    }
}

/**
 * Маршрут для экрана онбординга
 */
private fun NavGraphBuilder.onboardingRoute(
    navigateToCreateWallet: () -> Unit,
    navigateToImportWallet: () -> Unit
) {
    composable(route = AppDestination.Onboarding.route) {
        OnboardingScreen(
            navigateToCreateWallet = navigateToCreateWallet,
            navigateToImportWallet = navigateToImportWallet
        )
    }
} 