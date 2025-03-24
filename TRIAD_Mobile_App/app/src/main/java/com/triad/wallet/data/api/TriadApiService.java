package com.triad.wallet.data.api;

import com.triad.wallet.data.model.Block;
import com.triad.wallet.data.model.Transaction;
import com.triad.wallet.data.model.WalletInfo;
import com.triad.wallet.data.model.NetworkStatus;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Интерфейс для взаимодействия с API блокчейна TRIAD
 */
public interface TriadApiService {

    /**
     * Получить информацию о статусе сети
     */
    @GET("api/status")
    Call<NetworkStatus> getNetworkStatus();

    /**
     * Получить последние блоки
     * @param limit количество блоков для получения
     */
    @GET("api/blocks/latest")
    Call<List<Block>> getLatestBlocks(@Query("limit") int limit);

    /**
     * Получить детали конкретного блока
     * @param blockHash хеш блока
     */
    @GET("api/blocks/{blockHash}")
    Call<Block> getBlockByHash(@Path("blockHash") String blockHash);

    /**
     * Получить блок по высоте
     * @param height высота блока
     */
    @GET("api/blocks/height/{height}")
    Call<Block> getBlockByHeight(@Path("height") long height);

    /**
     * Получить информацию о транзакции
     * @param txHash хеш транзакции
     */
    @GET("api/transactions/{txHash}")
    Call<Transaction> getTransaction(@Path("txHash") String txHash);

    /**
     * Получить список транзакций для адреса
     * @param address адрес кошелька
     * @param limit максимальное количество транзакций
     * @param offset смещение для пагинации
     */
    @GET("api/wallet/{address}/transactions")
    Call<List<Transaction>> getAddressTransactions(
            @Path("address") String address,
            @Query("limit") int limit,
            @Query("offset") int offset
    );

    /**
     * Получить баланс кошелька
     * @param address адрес кошелька
     */
    @GET("api/wallet/{address}/balance")
    Call<WalletInfo> getWalletBalance(@Path("address") String address);

    /**
     * Отправить новую транзакцию
     * @param transaction объект транзакции
     */
    @POST("api/transactions")
    Call<Transaction> sendTransaction(@Body Transaction transaction);

    /**
     * Создать новый кошелек
     */
    @GET("api/wallet/create")
    Call<WalletInfo> createWallet();

    /**
     * Импортировать существующий кошелек
     * @param privateKey приватный ключ или мнемоническая фраза
     */
    @POST("api/wallet/import")
    Call<WalletInfo> importWallet(@Body String privateKey);
} 