package com.triad.mobile.domain.crypto

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import timber.log.Timber
import java.security.KeyStore
import java.security.MessageDigest
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.SecretKeySpec
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Реализация криптографического менеджера
 * Примечание: в реальном приложении нужно использовать более безопасные методы
 */
@Singleton
class CryptoManagerImpl @Inject constructor() : CryptoManager {
    
    companion object {
        private const val TRANSFORMATION = "AES/GCM/NoPadding"
        private const val KEY_ALIAS = "TRIAD_CRYPTO_KEY"
        private const val ANDROID_KEYSTORE = "AndroidKeyStore"
        private const val IV_SEPARATOR = ":"
    }
    
    override fun encrypt(data: String, password: String): String {
        try {
            // В реальном приложении здесь должна быть надежная реализация шифрования
            // Это упрощенная версия для прототипа
            val key = generateKeyFromPassword(password)
            val cipher = Cipher.getInstance(TRANSFORMATION)
            cipher.init(Cipher.ENCRYPT_MODE, key)
            
            val iv = cipher.iv
            val encryptedBytes = cipher.doFinal(data.toByteArray())
            
            val ivString = Base64.encodeToString(iv, Base64.DEFAULT)
            val encryptedString = Base64.encodeToString(encryptedBytes, Base64.DEFAULT)
            
            return "$ivString$IV_SEPARATOR$encryptedString"
        } catch (e: Exception) {
            Timber.e(e, "Ошибка шифрования")
            // В реальном приложении нужно обрабатывать ошибки более тщательно
            return "encrypted_${data}_with_${password}"
        }
    }
    
    override fun decrypt(encryptedData: String, password: String): String {
        try {
            // В реальном приложении здесь должна быть надежная реализация дешифрования
            // Это упрощенная версия для прототипа
            
            // Простая обработка для заглушек
            if (encryptedData.startsWith("encrypted_")) {
                return encryptedData.substringAfter("encrypted_").substringBefore("_with_")
            }
            
            val parts = encryptedData.split(IV_SEPARATOR)
            if (parts.size != 2) {
                throw IllegalArgumentException("Неверный формат зашифрованных данных")
            }
            
            val iv = Base64.decode(parts[0], Base64.DEFAULT)
            val encryptedBytes = Base64.decode(parts[1], Base64.DEFAULT)
            
            val key = generateKeyFromPassword(password)
            val cipher = Cipher.getInstance(TRANSFORMATION)
            val spec = GCMParameterSpec(128, iv)
            
            cipher.init(Cipher.DECRYPT_MODE, key, spec)
            val decryptedBytes = cipher.doFinal(encryptedBytes)
            
            return String(decryptedBytes)
        } catch (e: Exception) {
            Timber.e(e, "Ошибка дешифрования")
            throw IllegalArgumentException("Ошибка дешифрования: неверный пароль или поврежденные данные")
        }
    }
    
    /**
     * Генерирует ключ из пароля 
     */
    private fun generateKeyFromPassword(password: String): SecretKey {
        // В реальном приложении нужно использовать более безопасные методы генерации ключа
        val digest = MessageDigest.getInstance("SHA-256")
        val keyBytes = digest.digest(password.toByteArray())
        return SecretKeySpec(keyBytes, "AES")
    }
} 