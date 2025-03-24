package com.triad.mobile.ui.screens.wallet.import

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.triad.mobile.data.model.Wallet
import com.triad.mobile.data.repository.WalletRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * Состояние UI для экрана импорта кошелька
 */
data class ImportWalletUiState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String? = null,
    val wallet: Wallet? = null
)

/**
 * ViewModel для экрана импорта кошелька
 */
@HiltViewModel
class ImportWalletViewModel @Inject constructor(
    private val walletRepository: WalletRepository
) : ViewModel() {
    
    // Состояние UI
    var uiState by mutableStateOf(ImportWalletUiState())
        private set
    
    /**
     * Импортирует кошелек из мнемонической фразы
     * 
     * @param name Название кошелька
     * @param mnemonic Мнемоническая фраза
     * @param password Пароль для шифрования ключей
     */
    suspend fun importWalletFromMnemonic(name: String, mnemonic: String, password: String) {
        uiState = uiState.copy(
            isLoading = true,
            error = null,
            isSuccess = false,
            wallet = null
        )
        
        try {
            val wallet = walletRepository.importWalletFromMnemonic(name, mnemonic, password)
            Timber.d("Кошелек успешно импортирован из мнемонической фразы: $name, адрес: ${wallet.address}")
            
            uiState = uiState.copy(
                isLoading = false,
                isSuccess = true,
                wallet = wallet
            )
        } catch (e: Exception) {
            Timber.e(e, "Ошибка при импорте кошелька из мнемонической фразы")
            
            val errorMessage = when {
                e.message?.contains("already exists", ignoreCase = true) == true -> 
                    "Кошелек с таким адресом уже существует"
                e.message?.contains("invalid mnemonic", ignoreCase = true) == true -> 
                    "Недействительная мнемоническая фраза"
                else -> "Ошибка при импорте кошелька: ${e.message}"
            }
            
            uiState = uiState.copy(
                isLoading = false,
                error = errorMessage
            )
        }
    }
    
    /**
     * Импортирует кошелек из приватного ключа
     * 
     * @param name Название кошелька
     * @param privateKey Приватный ключ
     * @param password Пароль для шифрования ключей
     */
    suspend fun importWalletFromPrivateKey(name: String, privateKey: String, password: String) {
        uiState = uiState.copy(
            isLoading = true,
            error = null,
            isSuccess = false,
            wallet = null
        )
        
        try {
            val wallet = walletRepository.importWalletFromPrivateKey(name, privateKey, password)
            Timber.d("Кошелек успешно импортирован из приватного ключа: $name, адрес: ${wallet.address}")
            
            uiState = uiState.copy(
                isLoading = false,
                isSuccess = true,
                wallet = wallet
            )
        } catch (e: Exception) {
            Timber.e(e, "Ошибка при импорте кошелька из приватного ключа")
            
            val errorMessage = when {
                e.message?.contains("already exists", ignoreCase = true) == true -> 
                    "Кошелек с таким адресом уже существует"
                e.message?.contains("invalid private key", ignoreCase = true) == true -> 
                    "Недействительный приватный ключ"
                else -> "Ошибка при импорте кошелька: ${e.message}"
            }
            
            uiState = uiState.copy(
                isLoading = false,
                error = errorMessage
            )
        }
    }
    
    /**
     * Очищает сообщение об ошибке
     */
    fun clearError() {
        uiState = uiState.copy(error = null)
    }
} 