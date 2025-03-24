package com.triad.mobile.data.model

/**
 * Представляет блок в блокчейне TRIAD
 */
data class Block(
    /**
     * Хеш блока
     */
    val hash: String,
    
    /**
     * Высота блока
     */
    val height: Long,
    
    /**
     * Хеш предыдущего блока
     */
    val previousHash: String,
    
    /**
     * Отметка времени создания блока (в миллисекундах)
     */
    val timestamp: Long,
    
    /**
     * Количество транзакций в блоке
     */
    val transactionCount: Int,
    
    /**
     * Размер блока в байтах
     */
    val size: Int,
    
    /**
     * Nonce блока
     */
    val nonce: Long,
    
    /**
     * Сложность блока
     */
    val difficulty: Long,
    
    /**
     * Адрес майнера, который создал блок
     */
    val miner: String,
    
    /**
     * Суммарная комиссия всех транзакций в блоке
     */
    val totalFees: String,
    
    /**
     * Хеш корня дерева Меркле транзакций
     */
    val merkleRoot: String,
    
    /**
     * Версия блока
     */
    val version: Int,
    
    /**
     * Список хешей транзакций в блоке
     */
    val transactionHashes: List<String>,
    
    /**
     * Дополнительные данные в блоке
     */
    val extraData: String?
) 