package com.triad.mobile.ui.screens.splash

import androidx.lifecycle.ViewModel
import com.triad.mobile.data.repository.WalletRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

/**
 * ViewModel для экрана загрузки
 */
@HiltViewModel
class SplashViewModel @Inject constructor(
    private val walletRepository: WalletRepository
) : ViewModel() {
    
    /**
     * Проверяет, есть ли у пользователя созданный кошелек
     * 
     * @return true если кошелек существует, иначе false
     */
    suspend fun hasWallet(): Boolean = withContext(Dispatchers.IO) {
        try {
            val hasActiveWallet = walletRepository.hasActiveWallet()
            Timber.d("Проверка наличия кошелька: $hasActiveWallet")
            hasActiveWallet
        } catch (e: Exception) {
            Timber.e(e, "Ошибка при проверке наличия кошелька")
            false
        }
    }
} 