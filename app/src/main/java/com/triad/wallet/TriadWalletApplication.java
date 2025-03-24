package com.triad.wallet;

import android.app.Application;
import android.util.Log;

import com.triad.wallet.data.repository.WalletRepository;

/**
 * Основной класс приложения TRIAD Wallet.
 * Используется для инициализации глобальных компонентов приложения.
 */
public class TriadWalletApplication extends Application {
    private static final String TAG = "TriadWalletApp";

    // Одиночный экземпляр приложения
    private static TriadWalletApplication instance;

    // Репозиторий кошелька
    private WalletRepository walletRepository;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "Инициализация приложения TRIAD Wallet");
        
        // Сохраняем экземпляр приложения
        instance = this;
        
        // Инициализируем компоненты приложения
        initializeComponents();
    }

    /**
     * Инициализация основных компонентов приложения
     */
    private void initializeComponents() {
        // Инициализация репозитория кошелька
        walletRepository = new WalletRepository(this);
        
        // Здесь будут добавлены другие инициализации в будущем
    }

    /**
     * Получить экземпляр приложения
     * @return экземпляр TriadWalletApplication
     */
    public static TriadWalletApplication getInstance() {
        return instance;
    }

    /**
     * Получить репозиторий кошелька
     * @return экземпляр WalletRepository
     */
    public WalletRepository getWalletRepository() {
        return walletRepository;
    }
} 