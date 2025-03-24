package com.triad.mobile.ui.screens.wallet.create

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
 * Состояние UI для экрана создания кошелька
 */
data class CreateWalletUiState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String? = null,
    val wallet: Wallet? = null
)

/**
 * ViewModel для экрана создания кошелька
 */
@HiltViewModel
class CreateWalletViewModel @Inject constructor(
    private val walletRepository: WalletRepository
) : ViewModel() {
    
    // Состояние UI
    var uiState by mutableStateOf(CreateWalletUiState())
        private set
    
    /**
     * Создает новый кошелек
     * 
     * @param name Название кошелька
     * @param password Пароль для шифрования ключей
     */
    suspend fun createWallet(name: String, password: String) {
        uiState = uiState.copy(
            isLoading = true,
            error = null,
            isSuccess = false,
            wallet = null
        )
        
        try {
            val wallet = walletRepository.createWallet(name, password)
            Timber.d("Кошелек успешно создан: $name, адрес: ${wallet.address}")
            
            uiState = uiState.copy(
                isLoading = false,
                isSuccess = true,
                wallet = wallet
            )
        } catch (e: Exception) {
            Timber.e(e, "Ошибка при создании кошелька")
            
            val errorMessage = when {
                e.message?.contains("already exists", ignoreCase = true) == true -> 
                    "Кошелек с таким адресом уже существует"
                else -> "Ошибка при создании кошелька: ${e.message}"
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