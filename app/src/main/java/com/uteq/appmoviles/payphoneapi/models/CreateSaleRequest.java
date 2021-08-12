package com.uteq.appmoviles.payphoneapi.models;

public class CreateSaleRequest {
    private String phoneNumber;
    private String countryCode;
    private int amount;
    private int amountWithTax;
    private int amountWithoutTax;
    private int tax;
    private String clientTransactionId;

    public CreateSaleRequest(String phoneNumber, String countryCode, int amount, int amountWithTax, int amountWithoutTax, int tax, String clientTransactionId) {
        this.phoneNumber = phoneNumber;
        this.countryCode = countryCode;
        this.amount = amount;
        this.amountWithTax = amountWithTax;
        this.amountWithoutTax = amountWithoutTax;
        this.tax = tax;
        this.clientTransactionId = clientTransactionId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getAmountWithTax() {
        return amountWithTax;
    }

    public void setAmountWithTax(int amountWithTax) {
        this.amountWithTax = amountWithTax;
    }

    public int getTax() {
        return tax;
    }

    public void setTax(int tax) {
        this.tax = tax;
    }

    public String getClientTransactionId() {
        return clientTransactionId;
    }

    public void setClientTransactionId(String clientTransactionId) {
        this.clientTransactionId = clientTransactionId;
    }

    public int getAmountWithoutTax() {
        return amountWithoutTax;
    }

    public void setAmountWithoutTax(int amountWithoutTax) {
        this.amountWithoutTax = amountWithoutTax;
    }
}
