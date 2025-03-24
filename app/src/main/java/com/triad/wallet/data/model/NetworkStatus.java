package com.triad.wallet.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Модель данных статуса сети блокчейна
 */
public class NetworkStatus {
    
    @SerializedName("height")
    private long blockHeight;
    
    @SerializedName("nodes")
    private int activeNodes;
    
    @SerializedName("txPool")
    private int transactionsInPool;
    
    @SerializedName("tps")
    private double transactionsPerSecond;
    
    @SerializedName("difficulty")
    private double difficulty;
    
    @SerializedName("version")
    private String nodeVersion;
    
    @SerializedName("consensusType")
    private String consensusType;
    
    @SerializedName("syncStatus")
    private String syncStatus;
    
    @SerializedName("uptime")
    private long uptime;
    
    // Конструктор по умолчанию
    public NetworkStatus() {
    }
    
    // Геттеры и сеттеры
    public long getBlockHeight() {
        return blockHeight;
    }
    
    public void setBlockHeight(long blockHeight) {
        this.blockHeight = blockHeight;
    }
    
    public int getActiveNodes() {
        return activeNodes;
    }
    
    public void setActiveNodes(int activeNodes) {
        this.activeNodes = activeNodes;
    }
    
    public int getTransactionsInPool() {
        return transactionsInPool;
    }
    
    public void setTransactionsInPool(int transactionsInPool) {
        this.transactionsInPool = transactionsInPool;
    }
    
    public double getTransactionsPerSecond() {
        return transactionsPerSecond;
    }
    
    public void setTransactionsPerSecond(double transactionsPerSecond) {
        this.transactionsPerSecond = transactionsPerSecond;
    }
    
    public double getDifficulty() {
        return difficulty;
    }
    
    public void setDifficulty(double difficulty) {
        this.difficulty = difficulty;
    }
    
    public String getNodeVersion() {
        return nodeVersion;
    }
    
    public void setNodeVersion(String nodeVersion) {
        this.nodeVersion = nodeVersion;
    }
    
    public String getConsensusType() {
        return consensusType;
    }
    
    public void setConsensusType(String consensusType) {
        this.consensusType = consensusType;
    }
    
    public String getSyncStatus() {
        return syncStatus;
    }
    
    public void setSyncStatus(String syncStatus) {
        this.syncStatus = syncStatus;
    }
    
    public long getUptime() {
        return uptime;
    }
    
    public void setUptime(long uptime) {
        this.uptime = uptime;
    }
} 