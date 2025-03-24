package com.triad.mobile.data.repository

import com.triad.mobile.data.model.Transaction
import com.triad.mobile.data.model.Wallet
import com.triad.mobile.data.source.local.WalletDao
import com.triad.mobile.domain.crypto.CryptoManager
import com.triad.mobile.domain.crypto.WalletGenerator
import com.triad.mobile.domain.crypto.WalletKeys
import kotlinx.coroutines.flow.Flow
import timber.log.Timber
import java.math.BigDecimal
import java.util.Date
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Интерфейс репозитория для работы с кошельками
 */
interface WalletRepository {
    /**
     * Создает новый кошелек на основе мнемонической фразы
     */
    suspend fun createWallet(name: String, password: String): Wallet
    
    /**
     * Импортирует кошелек из мнемонической фразы
     */
    suspend fun importWalletFromMnemonic(name: String, mnemonic: String, password: String): Wallet
    
    /**
     * Импортирует кошелек из приватного ключа
     */
    suspend fun importWalletFromPrivateKey(name: String, privateKey: String, password: String): Wallet
    
    /**
     * Получает активный кошелек
     */
    suspend fun getActiveWallet(): Wallet?
    
    /**
     * Получает кошелек по идентификатору
     */
    suspend fun getWalletById(walletId: String): Wallet?
    
    /**
     * Получает кошелек по адресу
     */
    suspend fun getWalletByAddress(address: String): Wallet?
    
    /**
     * Получает все кошельки пользователя
     */
    suspend fun getAllWallets(): List<Wallet>
    
    /**
     * Наблюдает за всеми кошельками
     */
    fun observeAllWallets(): Flow<List<Wallet>>
    
    /**
     * Проверяет, есть ли активный кошелек
     */
    suspend fun hasActiveWallet(): Boolean
    
    /**
     * Устанавливает активный кошелек
     */
    suspend fun setActiveWallet(walletId: String): Boolean
    
    /**
     * Обновляет баланс кошелька
     */
    suspend fun updateWalletBalance(address: String, balance: BigDecimal)
    
    /**
     * Расшифровывает приватный ключ кошелька
     */
    fun decryptPrivateKey(wallet: Wallet, password: String): String
    
    /**
     * Расшифровывает мнемоническую фразу кошелька
     */
    fun decryptMnemonic(wallet: Wallet, password: String): String?
    
    /**
     * Удаляет кошелек
     */
    suspend fun deleteWallet(walletId: String): Boolean
}

/**
 * Реализация репозитория для работы с кошельками
 */
@Singleton
class WalletRepositoryImpl @Inject constructor(
    private val walletDao: WalletDao,
    private val walletGenerator: WalletGenerator,
    private val cryptoManager: CryptoManager
) : WalletRepository {
    
    override suspend fun createWallet(name: String, password: String): Wallet {
        try {
            val mnemonicPair = walletGenerator.generateMnemonic()
            val keys = walletGenerator.generateKeysFromMnemonic(mnemonicPair.mnemonic)
            
            val encryptedPrivateKey = cryptoManager.encrypt(keys.privateKey, password)
            val encryptedMnemonic = cryptoManager.encrypt(mnemonicPair.mnemonic, password)
            
            val walletId = UUID.randomUUID().toString()
            val wallet = Wallet(
                id = walletId,
                name = name,
                address = keys.address,
                encryptedPrivateKey = encryptedPrivateKey,
                encryptedMnemonic = encryptedMnemonic,
                publicKey = keys.publicKey,
                isActive = true,
                createdAt = Date(),
                updatedAt = Date()
            )
            
            walletDao.insertWallet(wallet)
            walletDao.setActiveWallet(walletId)
            
            Timber.d("Создан новый кошелек: ${wallet.address}")
            return wallet
        } catch (e: Exception) {
            Timber.e(e, "Ошибка при создании кошелька")
            throw e
        }
    }
    
    override suspend fun importWalletFromMnemonic(name: String, mnemonic: String, password: String): Wallet {
        try {
            val keys = walletGenerator.generateKeysFromMnemonic(mnemonic)
            
            // Проверка, не существует ли уже кошелек с таким адресом
            if (walletDao.walletExistsByAddress(keys.address)) {
                throw IllegalStateException("Кошелек с адресом ${keys.address} уже существует")
            }
            
            val encryptedPrivateKey = cryptoManager.encrypt(keys.privateKey, password)
            val encryptedMnemonic = cryptoManager.encrypt(mnemonic, password)
            
            val walletId = UUID.randomUUID().toString()
            val wallet = Wallet(
                id = walletId,
                name = name,
                address = keys.address,
                encryptedPrivateKey = encryptedPrivateKey,
                encryptedMnemonic = encryptedMnemonic,
                publicKey = keys.publicKey,
                isActive = true,
                createdAt = Date(),
                updatedAt = Date()
            )
            
            walletDao.insertWallet(wallet)
            walletDao.setActiveWallet(walletId)
            
            Timber.d("Импортирован кошелек из мнемонической фразы: ${wallet.address}")
            return wallet
        } catch (e: Exception) {
            Timber.e(e, "Ошибка при импорте кошелька из мнемонической фразы")
            throw e
        }
    }
    
    override suspend fun importWalletFromPrivateKey(name: String, privateKey: String, password: String): Wallet {
        try {
            val keys = walletGenerator.generateKeysFromPrivateKey(privateKey)
            
            // Проверка, не существует ли уже кошелек с таким адресом
            if (walletDao.walletExistsByAddress(keys.address)) {
                throw IllegalStateException("Кошелек с адресом ${keys.address} уже существует")
            }
            
            val encryptedPrivateKey = cryptoManager.encrypt(keys.privateKey, password)
            
            val walletId = UUID.randomUUID().toString()
            val wallet = Wallet(
                id = walletId,
                name = name,
                address = keys.address,
                encryptedPrivateKey = encryptedPrivateKey,
                publicKey = keys.publicKey,
                isActive = true,
                createdAt = Date(),
                updatedAt = Date()
            )
            
            walletDao.insertWallet(wallet)
            walletDao.setActiveWallet(walletId)
            
            Timber.d("Импортирован кошелек из приватного ключа: ${wallet.address}")
            return wallet
        } catch (e: Exception) {
            Timber.e(e, "Ошибка при импорте кошелька из приватного ключа")
            throw e
        }
    }
    
    override suspend fun getActiveWallet(): Wallet? {
        return walletDao.getActiveWallet()
    }
    
    override suspend fun getWalletById(walletId: String): Wallet? {
        return walletDao.getWalletById(walletId)
    }
    
    override suspend fun getWalletByAddress(address: String): Wallet? {
        return walletDao.getWalletByAddress(address)
    }
    
    override suspend fun getAllWallets(): List<Wallet> {
        return walletDao.getAllWallets()
    }
    
    override fun observeAllWallets(): Flow<List<Wallet>> {
        return walletDao.observeAllWallets()
    }
    
    override suspend fun hasActiveWallet(): Boolean {
        return walletDao.hasActiveWallet()
    }
    
    override suspend fun setActiveWallet(walletId: String): Boolean {
        return try {
            walletDao.setActiveWallet(walletId)
            Timber.d("Установлен активный кошелек: $walletId")
            true
        } catch (e: Exception) {
            Timber.e(e, "Ошибка при установке активного кошелька")
            false
        }
    }
    
    override suspend fun updateWalletBalance(address: String, balance: BigDecimal) {
        walletDao.updateWalletBalance(address, balance, Date())
        Timber.d("Обновлен баланс кошелька $address: $balance")
    }
    
    override fun decryptPrivateKey(wallet: Wallet, password: String): String {
        return try {
            cryptoManager.decrypt(wallet.encryptedPrivateKey, password)
        } catch (e: Exception) {
            Timber.e(e, "Ошибка при расшифровке приватного ключа")
            throw IllegalArgumentException("Неверный пароль или поврежденные данные")
        }
    }
    
    override fun decryptMnemonic(wallet: Wallet, password: String): String? {
        return try {
            wallet.encryptedMnemonic?.let { cryptoManager.decrypt(it, password) }
        } catch (e: Exception) {
            Timber.e(e, "Ошибка при расшифровке мнемонической фразы")
            null
        }
    }
    
    override suspend fun deleteWallet(walletId: String): Boolean {
        return try {
            val wallet = walletDao.getWalletById(walletId) ?: return false
            walletDao.deleteWallet(wallet)
            Timber.d("Удален кошелек: ${wallet.address}")
            true
        } catch (e: Exception) {
            Timber.e(e, "Ошибка при удалении кошелька")
            false
        }
    }
} 