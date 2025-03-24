package com.triad.wallet.data.model;

import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Модель данных транзакции в блокчейне TRIAD
 */
public class Transaction {
    
    public enum TransactionStatus {
        PENDING,
        CONFIRMED,
        FAILED
    }
    
    public enum TransactionType {
        TRANSFER,
        TOKEN_TRANSFER,
        CONTRACT_EXECUTION,
        CONTRACT_DEPLOYMENT,
        STAKING,
        VALIDATOR_REGISTRATION,
        INTERCHAIN
    }
    
    @SerializedName("hash")
    private String hash;
    
    @SerializedName("type")
    private TransactionType type;
    
    @SerializedName("from")
    private String from;
    
    @SerializedName("to")
    private String to;
    
    @SerializedName("amount")
    private BigDecimal amount;
    
    @SerializedName("fee")
    private BigDecimal fee;
    
    @SerializedName("timestamp")
    private Date timestamp;
    
    @SerializedName("blockHash")
    private String blockHash;
    
    @SerializedName("blockHeight")
    private long blockHeight;
    
    @SerializedName("nonce")
    private long nonce;
    
    @SerializedName("signature")
    private String signature;
    
    @SerializedName("status")
    private TransactionStatus status;
    
    @SerializedName("data")
    private String data;
    
    @SerializedName("token")
    private String token;
    
    @SerializedName("confirmations")
    private int confirmations;

    // Конструктор по умолчанию (нужен для Gson)
    public Transaction() {
    }
    
    // Конструктор для создания новой транзакции
    public Transaction(TransactionType type, String from, String to, BigDecimal amount, 
                      BigDecimal fee, long nonce, String data) {
        this.type = type;
        this.from = from;
        this.to = to;
        this.amount = amount;
        this.fee = fee;
        this.nonce = nonce;
        this.data = data;
        this.timestamp = new Date();
        this.status = TransactionStatus.PENDING;
    }

    // Геттеры и сеттеры

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getBlockHash() {
        return blockHash;
    }

    public void setBlockHash(String blockHash) {
        this.blockHash = blockHash;
    }

    public long getBlockHeight() {
        return blockHeight;
    }

    public void setBlockHeight(long blockHeight) {
        this.blockHeight = blockHeight;
    }

    public long getNonce() {
        return nonce;
    }

    public void setNonce(long nonce) {
        this.nonce = nonce;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getConfirmations() {
        return confirmations;
    }

    public void setConfirmations(int confirmations) {
        this.confirmations = confirmations;
    }
} 