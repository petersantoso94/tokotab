package com.tokotab.ecommerce.json;

import android.util.JsonReader;
import android.util.Log;

import com.tokotab.ecommerce.model.Bank;
import com.tokotab.ecommerce.model.City;
import com.tokotab.ecommerce.model.ListCategory;
import com.tokotab.ecommerce.model.Produk;
import com.tokotab.ecommerce.model.Province;
import com.tokotab.ecommerce.model.SliderImage;
import com.tokotab.ecommerce.model.SpinnerUom;
import com.tokotab.ecommerce.model.Transaksi;
import com.tokotab.ecommerce.model.TransaksiDetail;
import com.tokotab.ecommerce.model.User;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DIDLPSANTOSO on 5/11/2016.
 */
public class JsonParser {
    public static List<Produk> getData(InputStream data) {
        try {
            JsonReader reader = new JsonReader(new InputStreamReader(data, "UTF-8"));
            try {
                return readMessageArray(reader);
            } finally {
                reader.close();
            }
        } catch (IOException e) {

        }
        return null;
    }
    public static List<Province> getDataProvince(InputStream data) {
        try {
            JsonReader reader = new JsonReader(new InputStreamReader(data, "UTF-8"));
            try {
                return readMessageArrayProvince(reader);
            } finally {
                reader.close();
            }
        } catch (IOException e) {

        }
        return null;
    }
    public static List<Bank> getDataBank(InputStream data) {
        try {
            JsonReader reader = new JsonReader(new InputStreamReader(data, "UTF-8"));
            try {
                return readMessageArrayBank(reader);
            } finally {
                reader.close();
            }
        } catch (IOException e) {

        }
        return null;
    }
    public static List<City> getDataCity(InputStream data) {
        try {
            JsonReader reader = new JsonReader(new InputStreamReader(data, "UTF-8"));
            try {
                return readMessageArrayCity(reader);
            } finally {
                reader.close();
            }
        } catch (IOException e) {

        }
        return null;
    }
    public static List<SliderImage> getDataSlider(InputStream data) {
        try {
            JsonReader reader = new JsonReader(new InputStreamReader(data, "UTF-8"));
            try {
                return readMessageArraySlider(reader);
            } finally {
                reader.close();
            }
        } catch (IOException e) {

        }
        return null;
    }

    public static List<ListCategory> getDataCategory(InputStream data) {
        try {
            JsonReader reader = new JsonReader(new InputStreamReader(data, "UTF-8"));
            try {
                return readMessageArrayCategory(reader);
            } finally {
                reader.close();
            }
        } catch (IOException e) {

        }
        return null;
    }

    public static List<Transaksi> getDataTransaksi(InputStream data) {
        try {
            JsonReader reader = new JsonReader(new InputStreamReader(data, "UTF-8"));
            try {
                return readMessageArrayTransaksi(reader);
            } finally {
                reader.close();
            }
        } catch (IOException e) {

        }
        return null;
    }

    public static List<TransaksiDetail> getDataTransaksiDetail(InputStream data) {
        try {
            JsonReader reader = new JsonReader(new InputStreamReader(data, "UTF-8"));
            try {
                return readMessageArrayTransaksiDetail(reader);
            } finally {
                reader.close();
            }
        } catch (IOException e) {

        }
        return null;
    }

    public static List<String> getDataUom(InputStream data) {
        try {
            JsonReader reader = new JsonReader(new InputStreamReader(data, "UTF-8"));
            try {
                return readMessageArrayUom(reader);
            } finally {
                reader.close();
            }
        } catch (IOException e) {

        }
        return null;
    }

    public static User getDataUser(InputStream data) {
        try {
            JsonReader reader = new JsonReader(new InputStreamReader(data, "UTF-8"));
            try {
                return readMessageUser(reader);
            } finally {
                reader.close();
            }
        } catch (IOException e) {

        }
        return null;
    }

    public static List<Produk> readMessageArray(JsonReader reader) {
        List<Produk> inv = new ArrayList<>();
        try {
            reader.beginArray();
            while (reader.hasNext()) {
                inv.add(readMessage(reader));
            }
            reader.endArray();
            return inv;
        } catch (IOException e) {

        }
        return null;
    }

    public static List<Province> readMessageArrayProvince(JsonReader reader) {
        List<Province> inv = new ArrayList<>();
        try {
            reader.beginArray();
            while (reader.hasNext()) {
                inv.add(readMessageProvince(reader));
            }
            reader.endArray();
            return inv;
        } catch (IOException e) {

        }
        return null;
    }

    public static List<Bank> readMessageArrayBank(JsonReader reader) {
        List<Bank> inv = new ArrayList<>();
        try {
            reader.beginArray();
            while (reader.hasNext()) {
                inv.add(readMessageBank(reader));
            }
            reader.endArray();
            return inv;
        } catch (IOException e) {

        }
        return null;
    }

    public static List<City> readMessageArrayCity(JsonReader reader) {
        List<City> inv = new ArrayList<>();
        try {
            reader.beginArray();
            while (reader.hasNext()) {
                inv.add(readMessageCity(reader));
            }
            reader.endArray();
            return inv;
        } catch (IOException e) {

        }
        return null;
    }

    public static List<Transaksi> readMessageArrayTransaksi(JsonReader reader) {
        List<Transaksi> transaksis = new ArrayList<>();
        try {
            reader.beginArray();
            while (reader.hasNext()) {
                transaksis.add(readMessageTransaksi(reader));
            }
            reader.endArray();
            return transaksis;
        } catch (IOException e) {

        }
        return null;
    }

    public static List<TransaksiDetail> readMessageArrayTransaksiDetail(JsonReader reader) {
        List<TransaksiDetail> transaksiDetails = new ArrayList<>();
        try {
            reader.beginArray();
            while (reader.hasNext()) {
                transaksiDetails.add(readMessageTransaksiDetail(reader));
            }
            reader.endArray();
            return transaksiDetails;
        } catch (IOException e) {

        }
        return null;
    }

    public static List<String> readMessageArrayUom(JsonReader reader) {
        List<String> uom = new ArrayList<>();
        try {
            reader.beginArray();
            while (reader.hasNext()) {
                uom.add(readMessageUom(reader));
            }
            reader.endArray();
            return uom;
        } catch (IOException e) {

        }
        return null;
    }

    public static List<ListCategory> readMessageArrayCategory(JsonReader reader) {
        List<ListCategory> categories = new ArrayList<>();
        try {
            reader.beginArray();
            while (reader.hasNext()) {
                categories.add(readMessageCategory(reader));
            }
            reader.endArray();
            return categories;
        } catch (IOException e) {

        }
        return null;
    }

    public static List<SliderImage> readMessageArraySlider(JsonReader reader) {
        List<SliderImage> slider = new ArrayList<>();
        try {
            reader.beginArray();
            while (reader.hasNext()) {
                slider.add(readMessageSlider(reader));
            }
            reader.endArray();
            return slider;
        } catch (IOException e) {

        }
        return null;
    }

    public static Produk readMessage(JsonReader reader) {
        String internalID = null, productID = null, productName = null, productPrice = null, status = null,
                fakePrice = null, productDescription = null, productPicture1 = null, productPicture2 = null,
                productPicture3 = null, productPicture4 = null, productType = null, topProduct = null, newProduct = null, categoryInternalID = null, productColor = null;
        try {
            reader.beginObject();
            while (reader.hasNext()) {
                String temp = reader.nextName();
                if (temp.equals("ProductName")) {
                    productName = reader.nextString();
                } else if (temp.equals("Price")) {
                    productPrice = reader.nextString();
                } else if (temp.equals("Color")) {
                    productColor = reader.nextString();
                } else if (temp.equals("ProductPicture1")) {
                    productPicture1 = reader.nextString();
                } else if (temp.equals("ProductPicture2")) {
                    productPicture2 = reader.nextString();
                } else if (temp.equals("ProductPicture3")) {
                    productPicture3 = reader.nextString();
                } else if (temp.equals("ProductPicture4")) {
                    productPicture4 = reader.nextString();
                } else if (temp.equals("InternalID")) {
                    internalID = reader.nextString();
                } else if (temp.equals("ProductID")) {
                    productID = reader.nextString();
                } else if (temp.equals("Status")) {
                    status = reader.nextString();
                } else if (temp.equals("FakePrice")) {
                    fakePrice = reader.nextString();
                } else if (temp.equals("ProductDescription")) {
                    productDescription = reader.nextString();
                } else if (temp.equals("ProductType")) {
                    productType = reader.nextString();
                } else if (temp.equals("TopProduct")) {
                    topProduct = reader.nextString();
                } else if (temp.equals("NewProduct")) {
                    newProduct = reader.nextString();
                } else if (temp.equals("CategoryInternalID")) {
                    categoryInternalID = reader.nextString();
                } else {
                    reader.skipValue();
                }
            }
            reader.endObject();
            return new Produk(internalID, productID, productName, productPrice, status, fakePrice,
                    productDescription, productPicture1, productPicture2, productPicture3, productPicture4,
                    productType, topProduct, newProduct, categoryInternalID, productColor);
        } catch (IOException e) {

        }
        return null;
    }

    public static ListCategory readMessageCategory(JsonReader reader) {
        String internalID = null, categoryID = null, categoryName = null, level = null, parentInternalID = null,
                categoryPicture1 = null, categoryPicture2 = null,
                categoryPicture3 = null, categoryPicture4 = null, jumlah = null;
        try {
            reader.beginObject();
            while (reader.hasNext()) {
                String temp = reader.nextName();
                if (temp.equals("CategoryName")) {
                    categoryName = reader.nextString();
                } else if (temp.equals("Level")) {
                    level = reader.nextString();
                } else if (temp.equals("CategoryPicture1")) {
                    categoryPicture1 = reader.nextString();
                } else if (temp.equals("CategoryPicture2")) {
                    categoryPicture2 = reader.nextString();
                } else if (temp.equals("CategoryPicture3")) {
                    categoryPicture3 = reader.nextString();
                } else if (temp.equals("CategoryPicture4")) {
                    categoryPicture4 = reader.nextString();
                } else if (temp.equals("InternalID")) {
                    internalID = reader.nextString();
                } else if (temp.equals("CategoryID")) {
                    categoryID = reader.nextString();
                } else if (temp.equals("ParentInternalID")) {
                    parentInternalID = reader.nextString();
                } else if (temp.equals("Jumlah")) {
                    jumlah = reader.nextString();
                } else {
                    reader.skipValue();
                }
            }
            reader.endObject();
            return new ListCategory(internalID, categoryID, categoryName, level,
                    parentInternalID, categoryPicture1,
                    categoryPicture2, categoryPicture3, categoryPicture4, jumlah);
        } catch (IOException e) {

        }
        return null;
    }

    public static User readMessageUser(JsonReader reader) {
        String InternalID = null, MemberID = null, MemberEmail = null, MemberName = null, MemberPhone = null, MemberAddress = null, MemberType = null;
        try {
            reader.beginObject();
            while (reader.hasNext()) {
                String temp = reader.nextName();
                if (temp.equals("InternalID")) {
                    InternalID = reader.nextString();
                } else if (temp.equals("MemberID")) {
                    MemberID = reader.nextString();
                } else if (temp.equals("MemberEmail")) {
                    MemberEmail = reader.nextString();
                } else if (temp.equals("MemberName")) {
                    MemberName = reader.nextString();
                } else if (temp.equals("MemberPhone")) {
                    MemberPhone = reader.nextString();
                } else if (temp.equals("MemberAddress")) {
                    MemberAddress = reader.nextString();
                } else if (temp.equals("MemberType")) {
                    MemberType = reader.nextString();
                } else {
                    reader.skipValue();
                }
            }
            reader.endObject();
            return new User(InternalID, MemberID, MemberEmail, MemberName, MemberPhone, MemberAddress, MemberType);
        } catch (IOException e) {

        }
        return null;
    }

    public static String readMessageUom(JsonReader reader) {
        String Isi = null;
        try {
            reader.beginObject();
            while (reader.hasNext()) {
                String temp = reader.nextName();
                if (temp.equals("tampText")) {
                    Isi = reader.nextString();
                } else {
                    reader.skipValue();
                }
            }
            reader.endObject();
            return Isi;
        } catch (IOException e) {

        }
        return null;
    }

    public static SliderImage readMessageSlider(JsonReader reader) {
        String internalID = null, sliderName = null, sliderPicture = null, sliderLink = null;
        try {
            reader.beginObject();
            while (reader.hasNext()) {
                String temp = reader.nextName();
                if (temp.equals("InternalID")) {
                    internalID = reader.nextString();
                } else if (temp.equals("SliderName")) {
                    sliderName = reader.nextString();
                } else if (temp.equals("SliderPicture")) {
                    sliderPicture = reader.nextString();
                } else if (temp.equals("SliderLink")) {
                    sliderLink = reader.nextString();
                } else {
                    reader.skipValue();
                }
            }
            reader.endObject();
            return new SliderImage(internalID, sliderName, sliderPicture, sliderLink);
        } catch (IOException e) {

        }
        return null;
    }

    public static Bank readMessageBank(JsonReader reader) {
        String internalID = null, bankName = null, bankNumber = null, bankPersonName = null;
        try {
            reader.beginObject();
            while (reader.hasNext()) {
                String temp = reader.nextName();
                if (temp.equals("InternalID")) {
                    internalID = reader.nextString();
                } else if (temp.equals("BankName")) {
                    bankName = reader.nextString();
                } else if (temp.equals("BankNumber")) {
                    bankNumber = reader.nextString();
                } else if (temp.equals("BankPersonName")) {
                    bankPersonName = reader.nextString();
                } else {
                    reader.skipValue();
                }
            }
            reader.endObject();
            return new Bank( bankName,  internalID,  bankNumber,  bankPersonName);
        } catch (IOException e) {

        }
        return null;
    }

    public static Province readMessageProvince(JsonReader reader) {
        String internalID = null, provinceName = null,multiplier=null;
        try {
            reader.beginObject();
            while (reader.hasNext()) {
                String temp = reader.nextName();
                if (temp.equals("InternalID")) {
                    internalID = reader.nextString();
                } else if (temp.equals("ProvinceName")) {
                    provinceName = reader.nextString();
                } else if (temp.equals("Multiplier")) {
                    multiplier = reader.nextString();
                } else {
                    reader.skipValue();
                }
            }
            reader.endObject();
            return new Province(provinceName, internalID,multiplier);
        } catch (IOException e) {

        }
        return null;
    }

    public static City readMessageCity(JsonReader reader) {
        String internalID = null, cityName = null,provinceInternalID=null;
        try {
            reader.beginObject();
            while (reader.hasNext()) {
                String temp = reader.nextName();
                if (temp.equals("InternalID")) {
                    internalID = reader.nextString();
                } else if (temp.equals("CityName")) {
                    cityName = reader.nextString();
                }else if (temp.equals("ProvinceInternalID")) {
                    provinceInternalID = reader.nextString();
                } else {
                    reader.skipValue();
                }
            }
            reader.endObject();
            return new City( cityName,  internalID,  provinceInternalID);
        } catch (IOException e) {

        }
        return null;
    }

    public static Transaksi readMessageTransaksi(JsonReader reader) {
        String noPo = null, tanggal = null, status = null, grandTotal = null, InternalID = null;
        try {
            reader.beginObject();
            while (reader.hasNext()) {
                String temp = reader.nextName();
                if (temp.equals("SalesID")) {
                    noPo = reader.nextString();
                } else if (temp.equals("SalesDate")) {
                    tanggal = reader.nextString();
                } else if (temp.equals("Status")) {
                    status = reader.nextString();
                } else if (temp.equals("GrandTotal")) {
                    grandTotal = reader.nextString();
                } else if (temp.equals("InternalID")) {
                    InternalID = reader.nextString();
                } else {
                    reader.skipValue();
                }
            }
            reader.endObject();
            //Log.d("objek:",noPo+" "+tanggal);
            return new Transaksi(noPo, tanggal, status, grandTotal, InternalID);
        } catch (IOException e) {

        }
        return null;
    }

    public static TransaksiDetail readMessageTransaksiDetail(JsonReader reader) {
        String productName = null, price = null, subtotal = null, UOM = null, qty = null, colorName = null, colorHexa = null,salesInternalID=null;
        try {
            reader.beginObject();
            while (reader.hasNext()) {
                String temp = reader.nextName();
                if (temp.equals("ProductName")) {
                    productName = reader.nextString();
                } else if (temp.equals("Price")) {
                    price = reader.nextString();
                } else if (temp.equals("SubTotal")) {
                    subtotal = reader.nextString();
                } else if (temp.equals("UomID")) {
                    UOM = reader.nextString();
                } else if (temp.equals("Qty")) {
                    qty = reader.nextString();
                } else if (temp.equals("ColorID2")) {
                    colorName = reader.nextString();
                } else if (temp.equals("Color2")) {
                    colorHexa = reader.nextString();
                }else if (temp.equals("SalesInternalID")) {
                    salesInternalID = reader.nextString();
                } else {
                    reader.skipValue();
                }

            }
            Log.d("objek:", salesInternalID+" "+productName + " " + price + " " + subtotal + " " + UOM + " " + qty + " " + colorName + " " + colorHexa);
            reader.endObject();
            return new TransaksiDetail(productName, price, subtotal, UOM, qty, colorName, colorHexa, salesInternalID);
        } catch (IOException e) {

        }
        return null;
    }
}
