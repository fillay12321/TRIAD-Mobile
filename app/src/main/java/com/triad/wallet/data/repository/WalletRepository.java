package com.triad.wallet.data.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.triad.wallet.data.api.ApiClient;
import com.triad.wallet.data.api.TriadApiService;
import com.triad.wallet.data.model.Transaction;
import com.triad.wallet.data.model.WalletInfo;

import java.math.BigDecimal;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Репозиторий для работы с кошельком
 */
public class WalletRepository {
    private static final String TAG = "WalletRepository";
    private static final String PREF_NAME = "wallet_prefs";
    private static final String KEY_WALLET_ADDRESS = "wallet_address";
    
    private final Context context;
    private final TriadApiService apiService;
    private final SharedPreferences preferences;
    
    /**
     * Конструктор репозитория
     * @param context контекст приложения
     */
    public WalletRepository(Context context) {
        this.context = context;
        this.apiService = ApiClient.getApiService();
        this.preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }
    
    /**
     * Получить информацию о кошельке
     * @param callback колбэк с результатом
     */
    public void getWalletInfo(WalletCallback<WalletInfo> callback) {
        String address = getSavedWalletAddress();
        if (address == null || address.isEmpty()) {
            callback.onFailure("Кошелек не найден");
            return;
        }
        
        apiService.getWalletBalance(address).enqueue(new Callback<WalletInfo>() {
            @Override
            public void onResponse(Call<WalletInfo> call, Response<WalletInfo> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailure("Ошибка загрузки информации о кошельке");
                }
            }
            
            @Override
            public void onFailure(Call<WalletInfo> call, Throwable t) {
                Log.e(TAG, "Ошибка API: ", t);
                callback.onFailure("Ошибка сети: " + t.getMessage());
            }
        });
    }
    
    /**
     * Получить транзакции кошелька
     * @param limit лимит транзакций
     * @param offset смещение
     * @param callback колбэк с результатом
     */
    public void getTransactions(int limit, int offset, WalletCallback<List<Transaction>> callback) {
        String address = getSavedWalletAddress();
        if (address == null || address.isEmpty()) {
            callback.onFailure("Кошелек не найден");
            return;
        }
        
        apiService.getAddressTransactions(address, limit, offset).enqueue(new Callback<List<Transaction>>() {
            @Override
            public void onResponse(Call<List<Transaction>> call, Response<List<Transaction>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailure("Ошибка загрузки транзакций");
                }
            }
            
            @Override
            public void onFailure(Call<List<Transaction>> call, Throwable t) {
                Log.e(TAG, "Ошибка API: ", t);
                callback.onFailure("Ошибка сети: " + t.getMessage());
            }
        });
    }
    
    /**
     * Отправить транзакцию
     * @param toAddress адрес получателя
     * @param amount сумма
     * @param callback колбэк с результатом
     */
    public void sendTransaction(String toAddress, BigDecimal amount, WalletCallback<Transaction> callback) {
        String fromAddress = getSavedWalletAddress();
        if (fromAddress == null || fromAddress.isEmpty()) {
            callback.onFailure("Кошелек не найден");
            return;
        }
        
        // Создаем объект транзакции
        Transaction transaction = new Transaction(
                Transaction.TransactionType.TRANSFER,
                fromAddress,
                toAddress,
                amount,
                BigDecimal.valueOf(0.01), // Фиксированная комиссия как пример
                System.currentTimeMillis(), // Используем текущее время как nonce (это упрощение)
                "" // Без дополнительных данных
        );
        
        // Отправляем транзакцию через API
        apiService.sendTransaction(transaction).enqueue(new Callback<Transaction>() {
            @Override
            public void onResponse(Call<Transaction> call, Response<Transaction> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailure("Ошибка отправки транзакции");
                }
            }
            
            @Override
            public void onFailure(Call<Transaction> call, Throwable t) {
                Log.e(TAG, "Ошибка API: ", t);
                callback.onFailure("Ошибка сети: " + t.getMessage());
            }
        });
    }
    
    /**
     * Создать новый кошелек
     * @param callback колбэк с результатом
     */
    public void createWallet(WalletCallback<WalletInfo> callback) {
        apiService.createWallet().enqueue(new Callback<WalletInfo>() {
            @Override
            public void onResponse(Call<WalletInfo> call, Response<WalletInfo> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Сохраняем адрес кошелька
                    saveWalletAddress(response.body().getAddress());
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailure("Ошибка создания кошелька");
                }
            }
            
            @Override
            public void onFailure(Call<WalletInfo> call, Throwable t) {
                Log.e(TAG, "Ошибка API: ", t);
                callback.onFailure("Ошибка сети: " + t.getMessage());
            }
        });
    }
    
    /**
     * Импортировать кошелек
     * @param privateKey приватный ключ или мнемоническая фраза
     * @param callback колбэк с результатом
     */
    public void importWallet(String privateKey, WalletCallback<WalletInfo> callback) {
        apiService.importWallet(privateKey).enqueue(new Callback<WalletInfo>() {
            @Override
            public void onResponse(Call<WalletInfo> call, Response<WalletInfo> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Сохраняем адрес кошелька
                    saveWalletAddress(response.body().getAddress());
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailure("Ошибка импорта кошелька");
                }
            }
            
            @Override
            public void onFailure(Call<WalletInfo> call, Throwable t) {
                Log.e(TAG, "Ошибка API: ", t);
                callback.onFailure("Ошибка сети: " + t.getMessage());
            }
        });
    }
    
    /**
     * Сохранить адрес кошелька
     * @param address адрес кошелька
     */
    private void saveWalletAddress(String address) {
        preferences.edit().putString(KEY_WALLET_ADDRESS, address).apply();
    }
    
    /**
     * Получить сохраненный адрес кошелька
     * @return адрес кошелька или null, если не найден
     */
    public String getSavedWalletAddress() {
        return preferences.getString(KEY_WALLET_ADDRESS, null);
    }
    
    /**
     * Проверить, есть ли сохраненный кошелек
     * @return true, если кошелек сохранен
     */
    public boolean hasWallet() {
        return getSavedWalletAddress() != null;
    }
    
    /**
     * Интерфейс для колбэков репозитория
     * @param <T> тип данных результата
     */
    public interface WalletCallback<T> {
        void onSuccess(T result);
        void onFailure(String error);
    }
} 