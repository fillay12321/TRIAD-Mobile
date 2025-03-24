package com.triad.wallet.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

/**
 * Модель данных блока в блокчейне TRIAD
 */
public class Block {
    @SerializedName("hash")
    private String hash;
    
    @SerializedName("previousHash")
    private String previousHash;
    
    @SerializedName("height")
    private long height;
    
    @SerializedName("timestamp")
    private Date timestamp;
    
    @SerializedName("transactions")
    private List<Transaction> transactions;
    
    @SerializedName("validator")
    private String validator;
    
    @SerializedName("signature")
    private String signature;
    
    @SerializedName("merkleRoot")
    private String merkleRoot;
    
    @SerializedName("size")
    private int size;
    
    @SerializedName("difficulty")
    private double difficulty;
    
    @SerializedName("txCount")
    private int transactionCount;
    
    @SerializedName("consensusType")
    private String consensusType;

    // Конструктор по умолчанию (нужен для Gson)
    public Block() {
    }

    // Геттеры и сеттеры

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public void setPreviousHash(String previousHash) {
        this.previousHash = previousHash;
    }

    public long getHeight() {
        return height;
    }

    public void setHeight(long height) {
        this.height = height;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public String getValidator() {
        return validator;
    }

    public void setValidator(String validator) {
        this.validator = validator;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getMerkleRoot() {
        return merkleRoot;
    }

    public void setMerkleRoot(String merkleRoot) {
        this.merkleRoot = merkleRoot;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public double getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(double difficulty) {
        this.difficulty = difficulty;
    }

    public int getTransactionCount() {
        return transactionCount;
    }

    public void setTransactionCount(int transactionCount) {
        this.transactionCount = transactionCount;
    }

    public String getConsensusType() {
        return consensusType;
    }

    public void setConsensusType(String consensusType) {
        this.consensusType = consensusType;
    }
} 