package com.tokotab.ecommerce.model;

/**
 * Created by DIDLPSANTOSO on 8/11/2016.
 */
public class Bank {
    private String BankName, InternalID, BankNumber,BankPersonName;
    public static final String ARG_INTERNAL_ID = "internalBank";
    public static final String ARG_BANK_NAME = "namaBank";
    public static final String ARG_BANK_NUMBER = "BankNumber";
    public static final String ARG_BANK_PERSON_NAME = "BankPersonName";

    public Bank(String bankName, String internalID, String bankNumber, String bankPersonName) {
        BankName = bankName;
        InternalID = internalID;
        BankNumber = bankNumber;
        BankPersonName = bankPersonName;
    }

    public String getBankName() {
        return BankName;
    }

    public void setBankName(String bankName) {
        BankName = bankName;
    }

    public String getInternalID() {
        return InternalID;
    }

    public void setInternalID(String internalID) {
        InternalID = internalID;
    }

    public String getBankNumber() {
        return BankNumber;
    }

    public void setBankNumber(String bankNumber) {
        BankNumber = bankNumber;
    }

    public String getBankPersonName() {
        return BankPersonName;
    }

    public void setBankPersonName(String bankPersonName) {
        BankPersonName = bankPersonName;
    }
}
