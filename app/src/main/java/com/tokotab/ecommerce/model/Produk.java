package com.tokotab.ecommerce.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;

/**
 * Created by dwikadarmawan on 6/17/16.
 */
public class Produk {
    private String InternalID,ProductID,ProductName, ProductPrice, Status, FakePrice,
            ProductDescription, ProductPicture1, ProductPicture2, ProductPicture3,ProductPicture4,
            ProductType, TopProduct, NewProduct, CategoryInternalID,ProductColor ;
    public static final String ARG_INTERNAL_ID = "internal";
    public static final String ARG_PRODUCT_ID = "id";
    public static final String ARG_PRODUCT_NAME = "name";
    public static final String ARG_PRODUCT_PRICE = "price";
    public static final String ARG_STATUS = "stat";
    public static final String ARG_FAKE_PRICE = "fake";
    public static final String ARG_PRODUCT_DESC = "desc";
    public static final String ARG_PP_1 = "pp1";
    public static final String ARG_PP_2 = "pp2";
    public static final String ARG_PP_3 = "pp3";
    public static final String ARG_PP_4 = "pp4";
    public static final String ARG_PRODUCT_TYPE = "type";
    public static final String ARG_PRODUCT_TOP = "top";
    public static final String ARG_PRODUCT_NEW = "new";
    public static final String ARG_PRODUCT_CATEGORY = "category";
    public static final String ARG_PRODUCT_COLOR = "color";

    public Produk() {
    }

    public Produk(String internalID, String productID, String productName, String productPrice, String status, String fakePrice, String productDescription, String productPicture1, String productPicture2, String productPicture3, String productPicture4, String productType, String topProduct, String newProduct, String categoryInternalID, String productColor) {
        InternalID = internalID;
        ProductID = productID;
        ProductName = productName;
        ProductPrice = productPrice;
        Status = status;
        FakePrice = fakePrice;
        ProductDescription = productDescription;
        ProductPicture1 = productPicture1;
        ProductPicture2 = productPicture2;
        ProductPicture3 = productPicture3;
        ProductPicture4 = productPicture4;
        ProductType = productType;
        TopProduct = topProduct;
        NewProduct = newProduct;
        CategoryInternalID = categoryInternalID;
        ProductColor = productColor;
    }

    public String getProductColor() {
        return ProductColor;
    }

    public void setProductColor(String productColor) {
        ProductColor = productColor;
    }

    public String getProductPrice() {
        return ProductPrice;
    }

    public void setProductPrice(String productPrice) {
        ProductPrice = productPrice;
    }

    public String getInternalID() {
        return InternalID;
    }

    public void setInternalID(String internalID) {
        InternalID = internalID;
    }

    public String getProductID() {
        return ProductID;
    }

    public void setProductID(String productID) {
        ProductID = productID;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getFakePrice() {
        return FakePrice;
    }

    public void setFakePrice(String fakePrice) {
        FakePrice = fakePrice;
    }

    public String getProductDescription() {
        return ProductDescription;
    }

    public void setProductDescription(String productDescription) {
        ProductDescription = productDescription;
    }

    public String getProductPicture1() {
        return ProductPicture1;
    }

    public void setProductPicture1(String productPicture1) {
        ProductPicture1 = productPicture1;
    }

    public String getProductPicture2() {
        return ProductPicture2;
    }

    public void setProductPicture2(String productPicture2) {
        ProductPicture2 = productPicture2;
    }

    public String getProductPicture3() {
        return ProductPicture3;
    }

    public void setProductPicture3(String productPicture3) {
        ProductPicture3 = productPicture3;
    }

    public String getProductPicture4() {
        return ProductPicture4;
    }

    public void setProductPicture4(String productPicture4) {
        ProductPicture4 = productPicture4;
    }

    public String getProductType() {
        return ProductType;
    }

    public void setProductType(String productType) {
        ProductType = productType;
    }

    public String getTopProduct() {
        return TopProduct;
    }

    public void setTopProduct(String topProduct) {
        TopProduct = topProduct;
    }

    public String getNewProduct() {
        return NewProduct;
    }

    public void setNewProduct(String newProduct) {
        NewProduct = newProduct;
    }

    public String getCategoryInternalID() {
        return CategoryInternalID;
    }

    public void setCategoryInternalID(String categoryInternalID) {
        CategoryInternalID = categoryInternalID;
    }

    public void setImage(ImageView imgvView){
        String img=getProductPicture1().replaceAll(" ", "%20");
        new DownloadImageTask(imgvView)
                .execute("http://home.stationeryone.com/uploaded_img/200x200/"+img);
    }
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
