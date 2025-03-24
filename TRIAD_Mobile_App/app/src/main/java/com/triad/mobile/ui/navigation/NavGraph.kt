package com.triad.mobile.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.triad.mobile.ui.screens.home.HomeScreen
import com.triad.mobile.ui.screens.onboarding.OnboardingScreen
import com.triad.mobile.ui.screens.wallet.WalletScreen
import com.triad.mobile.ui.screens.send.SendScreen
import com.triad.mobile.ui.screens.receive.ReceiveScreen
import com.triad.mobile.ui.screens.settings.SettingsScreen
import com.triad.mobile.ui.screens.explorer.ExplorerScreen
import com.triad.mobile.ui.screens.staking.StakingScreen
import com.triad.mobile.ui.screens.auth.CreateWalletScreen
import com.triad.mobile.ui.screens.auth.ImportWalletScreen
import com.triad.mobile.ui.screens.splash.SplashScreen
import com.triad.mobile.ui.screens.backup.BackupWalletScreen
import com.triad.mobile.ui.screens.transactions.TransactionDetailsScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
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
        
        composable(Screen.CreateWallet.route) {
            CreateWalletScreen(
                navigateToBackup = { walletId ->
                    navController.navigate(Screen.BackupWallet.createRoute(walletId))
                },
                navigateBack = { navController.popBackStack() }
            )
        }
        
        composable(Screen.ImportWallet.route) {
            ImportWalletScreen(
                navigateToHome = { 
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Onboarding.route) { inclusive = true }
                    }
                },
                navigateBack = { navController.popBackStack() }
            )
        }
        
        composable(
            route = Screen.BackupWallet.route,
            arguments = Screen.BackupWallet.arguments
        ) {
            val walletId = it.arguments?.getString(Screen.ARG_WALLET_ID) ?: ""
            BackupWalletScreen(
                walletId = walletId,
                navigateToHome = { 
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Onboarding.route) { inclusive = true }
                    }
                },
                navigateBack = { navController.popBackStack() }
            )
        }
        
        composable(Screen.Home.route) {
            HomeScreen(
                navigateToWallet = { navController.navigate(Screen.Wallet.route) },
                navigateToExplorer = { navController.navigate(Screen.Explorer.route) },
                navigateToStaking = { navController.navigate(Screen.Staking.route) },
                navigateToSettings = { navController.navigate(Screen.Settings.route) }
            )
        }
        
        composable(Screen.Wallet.route) {
            WalletScreen(
                navigateToSend = { navController.navigate(Screen.Send.route) },
                navigateToReceive = { navController.navigate(Screen.Receive.route) },
                navigateToTransactionDetails = { txId ->
                    navController.navigate(Screen.TransactionDetails.createRoute(txId))
                },
                navigateBack = { navController.popBackStack() }
            )
        }
        
        composable(Screen.Send.route) {
            SendScreen(
                navigateBack = { navController.popBackStack() },
                onTransactionSent = { navController.popBackStack() }
            )
        }
        
        composable(Screen.Receive.route) {
            ReceiveScreen(
                navigateBack = { navController.popBackStack() }
            )
        }
        
        composable(
            route = Screen.TransactionDetails.route,
            arguments = Screen.TransactionDetails.arguments
        ) {
            val txId = it.arguments?.getString(Screen.ARG_TX_ID) ?: ""
            TransactionDetailsScreen(
                transactionId = txId,
                navigateBack = { navController.popBackStack() }
            )
        }
        
        composable(Screen.Explorer.route) {
            ExplorerScreen(
                navigateToBlockDetails = { blockId ->
                    // navController.navigate(Screen.BlockDetails.createRoute(blockId))
                },
                navigateToTransactionDetails = { txId ->
                    navController.navigate(Screen.TransactionDetails.createRoute(txId))
                },
                navigateBack = { navController.popBackStack() }
            )
        }
        
        composable(Screen.Staking.route) {
            StakingScreen(
                navigateBack = { navController.popBackStack() }
            )
        }
        
        composable(Screen.Settings.route) {
            SettingsScreen(
                navigateToBackup = { walletId ->
                    navController.navigate(Screen.BackupWallet.createRoute(walletId))
                },
                navigateBack = { navController.popBackStack() },
                navigateToOnboarding = {
                    navController.navigate(Screen.Onboarding.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                }
            )
        }
    }
} 