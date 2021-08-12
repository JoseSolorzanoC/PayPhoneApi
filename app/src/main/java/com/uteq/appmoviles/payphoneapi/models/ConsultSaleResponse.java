package com.uteq.appmoviles.payphoneapi.models;

public class ConsultSaleResponse {
    private int amount;
    private String clientTransactionId;
    private String transactionStatus;
    private long transactionId;
    private String document;
    private String storeName;

    public ConsultSaleResponse(int amount, String clientTransactionId, String transactionStatus, long transactionId, String document, String storeName) {
        this.amount = amount;
        this.clientTransactionId = clientTransactionId;
        this.transactionStatus = transactionStatus;
        this.transactionId = transactionId;
        this.document = document;
        this.storeName = storeName;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getClientTransactionId() {
        return clientTransactionId;
    }

    public void setClientTransactionId(String clientTransactionId) {
        this.clientTransactionId = clientTransactionId;
    }

    public String getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
    }

}
