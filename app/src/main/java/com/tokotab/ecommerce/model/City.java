package com.tokotab.ecommerce.model;

/**
 * Created by DIDLPSANTOSO on 8/11/2016.
 */
public class City {
    private String CityName, InternalID, ProvinceInternalID;
    public static final String ARG_INTERNAL_ID = "internalCity";
    public static final String ARG_PROVINCE_INTERNAL_ID = "provinceInternalCity";
    public static final String ARG_CITY_NAME = "CityName";

    public City(String cityName, String internalID, String provinceInternalID) {
        CityName = cityName;
        InternalID = internalID;
        ProvinceInternalID = provinceInternalID;
    }

    public String getProvinceInternalID() {
        return ProvinceInternalID;
    }

    public void setProvinceInternalID(String provinceInternalID) {
        ProvinceInternalID = provinceInternalID;
    }

    public String getCityName() {
        return CityName;
    }

    public void setCityName(String cityName) {
        CityName = cityName;
    }

    public String getInternalID() {
        return InternalID;
    }

    public void setInternalID(String internalID) {
        InternalID = internalID;
    }
}
