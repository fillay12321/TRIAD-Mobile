package com.triad.mobile.ui.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class Screen(val route: String) {
    object Splash : Screen("splash_screen")
    object Onboarding : Screen("onboarding_screen")
    object CreateWallet : Screen("create_wallet_screen")
    object ImportWallet : Screen("import_wallet_screen")
    object Home : Screen("home_screen")
    object Wallet : Screen("wallet_screen")
    object Send : Screen("send_screen")
    object Receive : Screen("receive_screen")
    object Explorer : Screen("explorer_screen")
    object Staking : Screen("staking_screen")
    object Settings : Screen("settings_screen")
    
    object BackupWallet : Screen("backup_wallet_screen/{$ARG_WALLET_ID}") {
        val arguments = listOf(
            navArgument(ARG_WALLET_ID) { type = NavType.StringType }
        )
        
        fun createRoute(walletId: String): String {
            return "backup_wallet_screen/$walletId"
        }
    }
    
    object TransactionDetails : Screen("transaction_details_screen/{$ARG_TX_ID}") {
        val arguments = listOf(
            navArgument(ARG_TX_ID) { type = NavType.StringType }
        )
        
        fun createRoute(txId: String): String {
            return "transaction_details_screen/$txId"
        }
    }
    
    object BlockDetails : Screen("block_details_screen/{$ARG_BLOCK_ID}") {
        val arguments = listOf(
            navArgument(ARG_BLOCK_ID) { type = NavType.StringType }
        )
        
        fun createRoute(blockId: String): String {
            return "block_details_screen/$blockId"
        }
    }
    
    companion object {
        const val ARG_WALLET_ID = "walletId"
        const val ARG_TX_ID = "txId"
        const val ARG_BLOCK_ID = "blockId"
    }
} 