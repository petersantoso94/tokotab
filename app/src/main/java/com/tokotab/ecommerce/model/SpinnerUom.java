package com.tokotab.ecommerce.model;

/**
 * Created by dwikadarmawan on 6/29/16.
 */
public class SpinnerUom {

    private String InternalID,UomID,RegularPrice,BussinessPrice;
    public static final String ARG_INTERNAL_ID = "uom_internal";
    public static final String ARG_UOM_ID = "uom_id";

    public SpinnerUom(String internalID, String uomID, String regularPrice, String bussinessPrice) {
        InternalID = internalID;
        UomID = uomID;
        RegularPrice = regularPrice;
        BussinessPrice = bussinessPrice;
    }

    public String getInternalID() {
        return InternalID;
    }

    public void setInternalID(String internalID) {
        InternalID = internalID;
    }

    public String getUomID() {
        return UomID;
    }

    public void setUomID(String uomID) {
        UomID = uomID;
    }

    public String getRegularPrice() {
        return RegularPrice;
    }

    public void setRegularPrice(String regularPrice) {
        RegularPrice = regularPrice;
    }

    public String getBussinessPrice() {
        return BussinessPrice;
    }

    public void setBussinessPrice(String bussinessPrice) {
        BussinessPrice = bussinessPrice;
    }
}
