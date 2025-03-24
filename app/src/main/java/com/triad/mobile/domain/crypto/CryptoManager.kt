package com.triad.mobile.domain.crypto

/**
 * Интерфейс для криптографических операций
 */
interface CryptoManager {
    
    /**
     * Шифрует строку с использованием пароля
     * 
     * @param data Данные для шифрования
     * @param password Пароль для шифрования
     * @return Зашифрованная строка
     */
    fun encrypt(data: String, password: String): String
    
    /**
     * Расшифровывает строку с использованием пароля
     * 
     * @param encryptedData Зашифрованные данные
     * @param password Пароль для расшифровки
     * @return Расшифрованная строка
     */
    fun decrypt(encryptedData: String, password: String): String
} 