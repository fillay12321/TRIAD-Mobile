package com.triad.wallet.data.model;

import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

/**
 * Модель данных с информацией о кошельке
 */
public class WalletInfo {
    
    @SerializedName("address")
    private String address;
    
    @SerializedName("balance")
    private BigDecimal balance;
    
    @SerializedName("publicKey")
    private String publicKey;
    
    @SerializedName("nonce")
    private long nonce;
    
    @SerializedName("lastTxHash")
    private String lastTransactionHash;
    
    @SerializedName("name")
    private String name;
    
    // Конструктор по умолчанию (нужен для Gson)
    public WalletInfo() {
        this.balance = BigDecimal.ZERO;
        this.nonce = 0;
    }
    
    // Конструктор с основными параметрами
    public WalletInfo(String address, BigDecimal balance) {
        this.address = address;
        this.balance = balance;
        this.nonce = 0;
    }
    
    // Геттеры и сеттеры
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public BigDecimal getBalance() {
        return balance;
    }
    
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
    
    public String getPublicKey() {
        return publicKey;
    }
    
    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }
    
    public long getNonce() {
        return nonce;
    }
    
    public void setNonce(long nonce) {
        this.nonce = nonce;
    }
    
    public String getLastTransactionHash() {
        return lastTransactionHash;
    }
    
    public void setLastTransactionHash(String lastTransactionHash) {
        this.lastTransactionHash = lastTransactionHash;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
} 