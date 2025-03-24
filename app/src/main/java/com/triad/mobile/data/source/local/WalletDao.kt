package com.triad.mobile.data.source.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.triad.mobile.data.model.Wallet
import kotlinx.coroutines.flow.Flow

/**
 * DAO для работы с кошельками в базе данных
 */
@Dao
interface WalletDao {
    
    /**
     * Получает все кошельки пользователя
     */
    @Query("SELECT * FROM wallets ORDER BY createdAt DESC")
    suspend fun getAllWallets(): List<Wallet>
    
    /**
     * Получает все кошельки как Flow для наблюдения за изменениями
     */
    @Query("SELECT * FROM wallets ORDER BY createdAt DESC")
    fun observeAllWallets(): Flow<List<Wallet>>
    
    /**
     * Получает кошелек по идентификатору
     */
    @Query("SELECT * FROM wallets WHERE id = :walletId")
    suspend fun getWalletById(walletId: String): Wallet?
    
    /**
     * Получает активный кошелек
     */
    @Query("SELECT * FROM wallets WHERE isActive = 1 LIMIT 1")
    suspend fun getActiveWallet(): Wallet?
    
    /**
     * Получает кошелек по адресу
     */
    @Query("SELECT * FROM wallets WHERE address = :address")
    suspend fun getWalletByAddress(address: String): Wallet?
    
    /**
     * Проверяет, существует ли кошелек с указанным адресом
     */
    @Query("SELECT EXISTS(SELECT 1 FROM wallets WHERE address = :address)")
    suspend fun walletExistsByAddress(address: String): Boolean
    
    /**
     * Проверяет, существует ли активный кошелек
     */
    @Query("SELECT EXISTS(SELECT 1 FROM wallets WHERE isActive = 1)")
    suspend fun hasActiveWallet(): Boolean
    
    /**
     * Сохраняет новый кошелек
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWallet(wallet: Wallet): Long
    
    /**
     * Обновляет существующий кошелек
     */
    @Update
    suspend fun updateWallet(wallet: Wallet)
    
    /**
     * Удаляет кошелек
     */
    @Delete
    suspend fun deleteWallet(wallet: Wallet)
    
    /**
     * Сбрасывает флаг активности у всех кошельков, кроме указанного
     */
    @Query("UPDATE wallets SET isActive = 0 WHERE id != :walletId")
    suspend fun deactivateOtherWallets(walletId: String)
    
    /**
     * Устанавливает активный кошелек и сбрасывает активность всех остальных
     */
    @Transaction
    suspend fun setActiveWallet(walletId: String) {
        deactivateOtherWallets(walletId)
        
        val wallet = getWalletById(walletId)
        wallet?.let {
            updateWallet(it.copy(isActive = true))
        }
    }
    
    /**
     * Обновляет баланс кошелька
     */
    @Query("UPDATE wallets SET balance = :balance, updatedAt = :updateTime WHERE address = :address")
    suspend fun updateWalletBalance(address: String, balance: java.math.BigDecimal, updateTime: java.util.Date)
} 