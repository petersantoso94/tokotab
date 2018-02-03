package com.tokotab.ecommerce.model;

/**
 * Created by dwikadarmawan on 7/1/16.
 */
public class Transaksi {

    private String noPo, tanggal, status, grandTotal, InternalID;
    public static final String ARG_PROVINSI = "provinsi_pengiriman";
    public static final String ARG_KOTA = "kota_pengiriman";
    public static final String ARG_ALAMAT = "alamat_pengiriman";
    public static final String ARG_GRAND_TOTAL = "grand_total";
    public static final String ARG_DISKON = "diskon_cart";
    public static final String ARG_SHIPPING = "shipping_cart";
    public static final String ARG_SALES_HEADER = "sales_header";
    public Transaksi(String noPo, String tanggal, String status, String grandTotal, String internalID) {
        this.noPo = noPo;
        this.tanggal = tanggal;
        this.status = status;
        this.grandTotal = grandTotal;
        InternalID = internalID;
    }

    public String getInternalID() {
        return InternalID;
    }

    public void setInternalID(String internalID) {
        InternalID = internalID;
    }

    public String getNoPo() {
        return noPo;
    }

    public void setNoPo(String noPo) {
        this.noPo = noPo;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(String grandTotal) {
        this.grandTotal = grandTotal;
    }
}
