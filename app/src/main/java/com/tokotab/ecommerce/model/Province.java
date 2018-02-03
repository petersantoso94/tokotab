package com.tokotab.ecommerce.model;

/**
 * Created by DIDLPSANTOSO on 8/11/2016.
 */
public class Province {
    private String ProvinceName, InternalID, Multiplier;
    public static final String ARG_INTERNAL_ID = "internalProvince";
    public static final String ARG_PROVINCE_NAME = "ProvinceName";
    public static final String ARG_MULTIPLIER = "Multiplier";
    public Province(String provinceName, String internalID, String multiplier) {
        ProvinceName = provinceName;
        InternalID = internalID;
        Multiplier = multiplier;
    }

    public String getMultiplier() {
        return Multiplier;
    }

    public void setMultiplier(String multiplier) {
        Multiplier = multiplier;
    }

    public String getProvinceName() {
        return ProvinceName;
    }

    public void setProvinceName(String provinceName) {
        ProvinceName = provinceName;
    }

    public String getInternalID() {
        return InternalID;
    }

    public void setInternalID(String internalID) {
        InternalID = internalID;
    }
}
