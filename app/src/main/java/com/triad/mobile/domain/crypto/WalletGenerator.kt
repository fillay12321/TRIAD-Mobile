package com.triad.mobile.domain.crypto

/**
 * Интерфейс для операций по генерации криптовалютных кошельков
 */
interface WalletGenerator {
    
    /**
     * Генерирует новую мнемоническую фразу
     * 
     * @return Пара с мнемонической фразой и энтропией
     */
    fun generateMnemonic(): MnemonicPair
    
    /**
     * Генерирует ключи из мнемонической фразы
     * 
     * @param mnemonic Мнемоническая фраза
     * @return Пара ключей
     */
    fun generateKeysFromMnemonic(mnemonic: String): WalletKeys
    
    /**
     * Генерирует ключи из приватного ключа
     * 
     * @param privateKey Приватный ключ в строковом формате
     * @return Пара ключей
     */
    fun generateKeysFromPrivateKey(privateKey: String): WalletKeys
}

/**
 * Пара мнемонической фразы и энтропии
 */
data class MnemonicPair(
    val mnemonic: String,
    val entropy: ByteArray
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        
        other as MnemonicPair
        
        if (mnemonic != other.mnemonic) return false
        if (!entropy.contentEquals(other.entropy)) return false
        
        return true
    }
    
    override fun hashCode(): Int {
        var result = mnemonic.hashCode()
        result = 31 * result + entropy.contentHashCode()
        return result
    }
}

/**
 * Ключи кошелька
 */
data class WalletKeys(
    val privateKey: String,
    val publicKey: String,
    val address: String
) 