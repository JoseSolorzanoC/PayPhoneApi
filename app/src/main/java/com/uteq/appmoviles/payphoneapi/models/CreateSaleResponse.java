package com.uteq.appmoviles.payphoneapi.models;

public class CreateSaleResponse {
    private String transactionId;

    public CreateSaleResponse(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
}
