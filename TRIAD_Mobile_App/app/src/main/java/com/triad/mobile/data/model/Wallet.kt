package com.triad.mobile.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal
import java.util.Date

/**
 * Модель данных кошелька для хранения в базе данных
 */
@Entity(tableName = "wallets")
data class Wallet(
    /**
     * Уникальный идентификатор кошелька
     */
    @PrimaryKey
    val id: String,
    
    /**
     * Имя кошелька, заданное пользователем
     */
    val name: String,
    
    /**
     * Адрес кошелька в блокчейне TRIAD
     */
    val address: String,
    
    /**
     * Зашифрованный приватный ключ
     */
    val encryptedPrivateKey: String,
    
    /**
     * Зашифрованная мнемоническая фраза (если создавалась)
     */
    val encryptedMnemonic: String? = null,
    
    /**
     * Публичный ключ
     */
    val publicKey: String,
    
    /**
     * Баланс кошелька
     */
    val balance: BigDecimal = BigDecimal.ZERO,
    
    /**
     * Активен ли кошелек в данный момент (основной кошелек)
     */
    val isActive: Boolean = false,
    
    /**
     * Дата создания кошелька
     */
    val createdAt: Date = Date(),
    
    /**
     * Дата последнего обновления кошелька
     */
    val updatedAt: Date = Date()
) 