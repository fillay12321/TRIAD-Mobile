package com.triad.wallet.ui.wallet;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.triad.wallet.TriadWalletApplication;
import com.triad.wallet.data.model.WalletInfo;
import com.triad.wallet.data.repository.WalletRepository;

/**
 * ViewModel для экрана кошелька
 */
public class WalletViewModel extends AndroidViewModel {
    
    private final WalletRepository walletRepository;
    
    // LiveData для информации о кошельке
    private final MutableLiveData<WalletInfo> walletInfo = new MutableLiveData<>();
    
    // LiveData для ошибок
    private final MutableLiveData<String> error = new MutableLiveData<>();
    
    // Флаг загрузки
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    
    public WalletViewModel(@NonNull Application application) {
        super(application);
        
        // Получаем репозиторий из приложения
        this.walletRepository = ((TriadWalletApplication) application).getWalletRepository();
    }
    
    /**
     * Получить LiveData с информацией о кошельке
     */
    public LiveData<WalletInfo> getWalletInfo() {
        return walletInfo;
    }
    
    /**
     * Получить LiveData с ошибкой
     */
    public LiveData<String> getError() {
        return error;
    }
    
    /**
     * Получить LiveData с состоянием загрузки
     */
    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }
    
    /**
     * Загрузить информацию о кошельке
     */
    public void loadWalletInfo() {
        isLoading.setValue(true);
        
        // В реальном приложении здесь будет асинхронный запрос к репозиторию
        // walletRepository.getWalletInfo(...)
        
        // Временно используем моковые данные
        try {
            // Эмулируем задержку сети
            Thread.sleep(500);
            
            // Создаем моковые данные
            WalletInfo mockWalletInfo = new WalletInfo();
            mockWalletInfo.setAddress("TRIADxxxx1234567890abcdefghijklmnopqrst");
            mockWalletInfo.setBalance(java.math.BigDecimal.valueOf(123.45));
            
            // Обновляем LiveData
            walletInfo.postValue(mockWalletInfo);
            error.postValue(null); // Сбрасываем ошибку
        } catch (Exception e) {
            error.postValue("Ошибка загрузки данных: " + e.getMessage());
        } finally {
            isLoading.postValue(false);
        }
    }
    
    /**
     * Отправить транзакцию
     */
    public void sendTransaction(String toAddress, java.math.BigDecimal amount) {
        // В реальном приложении здесь будет логика отправки транзакции
        // walletRepository.sendTransaction(...)
    }
} 