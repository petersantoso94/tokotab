package com.tokotab.ecommerce.model;

/**
 * Created by dwikadarmawan on 7/1/16.
 */
public class TransaksiDetail {
    private String ProductName,Price,Subtotal,UOM,Qty,ColorName,ColorHexa,SalesInternalID;

    public TransaksiDetail(String productName, String price, String subtotal, String UOM, String qty, String colorName, String colorHexa, String salesInternalID) {
        ProductName = productName;
        Price = price;
        Subtotal = subtotal;
        this.UOM = UOM;
        Qty = qty;
        ColorName = colorName;
        ColorHexa = colorHexa;
        SalesInternalID = salesInternalID;
    }

    public String getSalesInternalID() {
        return SalesInternalID;
    }

    public void setSalesInternalID(String salesInternalID) {
        SalesInternalID = salesInternalID;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getSubtotal() {
        return Subtotal;
    }

    public void setSubtotal(String subtotal) {
        Subtotal = subtotal;
    }

    public String getUOM() {
        return UOM;
    }

    public void setUOM(String UOM) {
        this.UOM = UOM;
    }

    public String getQty() {
        return Qty;
    }

    public void setQty(String qty) {
        Qty = qty;
    }

    public String getColorName() {
        return ColorName;
    }

    public void setColorName(String colorName) {
        ColorName = colorName;
    }

    public String getColorHexa() {
        return ColorHexa;
    }

    public void setColorHexa(String colorHexa) {
        ColorHexa = colorHexa;
    }
}
