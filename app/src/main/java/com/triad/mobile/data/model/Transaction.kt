package com.triad.mobile.data.model

import java.math.BigDecimal

/**
 * Представляет транзакцию в блокчейне TRIAD
 */
data class Transaction(
    /**
     * Уникальный идентификатор транзакции (хеш)
     */
    val id: String,
    
    /**
     * Адрес отправителя
     */
    val sender: String,
    
    /**
     * Адрес получателя
     */
    val recipient: String,
    
    /**
     * Сумма транзакции
     */
    val amount: BigDecimal,
    
    /**
     * Комиссия транзакции
     */
    val fee: BigDecimal,
    
    /**
     * Отметка времени создания транзакции (в миллисекундах)
     */
    val timestamp: Long,
    
    /**
     * Высота блока, в котором была включена транзакция (null, если не включена)
     */
    val blockHeight: Long?,
    
    /**
     * Статус транзакции
     */
    val status: TransactionStatus,
    
    /**
     * Данные транзакции (если это смарт-контракт или другие данные)
     */
    val data: String?,
    
    /**
     * Тип транзакции
     */
    val type: TransactionType,
    
    /**
     * Количество подтверждений транзакции
     */
    val confirmations: Int,
    
    /**
     * Публичный ключ отправителя
     */
    val senderPublicKey: String,
    
    /**
     * Подпись транзакции
     */
    val signature: String,
    
    /**
     * ID ассета (для токенов)
     */
    val asset: String = "TRD",
    
    /**
     * Заметка пользователя к транзакции (локальное хранение)
     */
    val note: String? = null
)

/**
 * Статусы транзакции
 */
enum class TransactionStatus {
    /**
     * Транзакция создана, но еще не отправлена в сеть
     */
    CREATED,
    
    /**
     * Транзакция отправлена в сеть и ожидает включения в блок
     */
    PENDING,
    
    /**
     * Транзакция включена в блок
     */
    CONFIRMED,
    
    /**
     * Транзакция не может быть обработана из-за ошибки
     */
    FAILED,
    
    /**
     * Транзакция отклонена из-за двойного расходования или других причин
     */
    REJECTED
}

/**
 * Типы транзакций
 */
enum class TransactionType {
    /**
     * Перевод средств между адресами
     */
    TRANSFER,
    
    /**
     * Стейкинг средств
     */
    STAKE,
    
    /**
     * Снятие средств со стейкинга
     */
    UNSTAKE,
    
    /**
     * Получение наград за стейкинг
     */
    CLAIM_REWARDS,
    
    /**
     * Взаимодействие со смарт-контрактом
     */
    CONTRACT_CALL,
    
    /**
     * Плата за транзакцию в блоке (майнерам)
     */
    FEE,
    
    /**
     * Другой тип транзакции
     */
    OTHER
} 