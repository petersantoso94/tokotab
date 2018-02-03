package com.tokotab.ecommerce.model;

/**
 * Created by dwikadarmawan on 6/21/16.
 */
public class ListCategory {
    private String InternalID,CategoryID,CategoryName,Level,ParentInternalID,CategoryPicture1,
            CategoryPicture2,CategoryPicture3,CategoryPicture4,Jumlah;

    public ListCategory(String internalID, String categoryID,
                        String categoryName, String level, String parentInternalID,
                        String categoryPicture1, String categoryPicture2, String categoryPicture3,
                        String categoryPicture4, String jumlah) {
        InternalID = internalID;
        CategoryID = categoryID;
        CategoryName = categoryName;
        Level = level;
        ParentInternalID = parentInternalID;
        CategoryPicture1 = categoryPicture1;
        CategoryPicture2 = categoryPicture2;
        CategoryPicture3 = categoryPicture3;
        CategoryPicture4 = categoryPicture4;
        Jumlah = jumlah;
    }

    public String getJumlah() {
        return Jumlah;
    }

    public void setJumlah(String jumlah) {
        Jumlah = jumlah;
    }

    public String getInternalID() {
        return InternalID;
    }

    public void setInternalID(String internalID) {
        InternalID = internalID;
    }

    public String getCategoryID() {
        return CategoryID;
    }

    public void setCategoryID(String categoryID) {
        CategoryID = categoryID;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public String getLevel() {
        return Level;
    }

    public void setLevel(String level) {
        Level = level;
    }

    public String getParentInternalID() {
        return ParentInternalID;
    }

    public void setParentInternalID(String parentInternalID) {
        ParentInternalID = parentInternalID;
    }

    public String getCategoryPicture1() {
        return CategoryPicture1;
    }

    public void setCategoryPicture1(String categoryPicture1) {
        CategoryPicture1 = categoryPicture1;
    }

    public String getCategoryPicture2() {
        return CategoryPicture2;
    }

    public void setCategoryPicture2(String categoryPicture2) {
        CategoryPicture2 = categoryPicture2;
    }

    public String getCategoryPicture3() {
        return CategoryPicture3;
    }

    public void setCategoryPicture3(String categoryPicture3) {
        CategoryPicture3 = categoryPicture3;
    }

    public String getCategoryPicture4() {
        return CategoryPicture4;
    }

    public void setCategoryPicture4(String categoryPicture4) {
        CategoryPicture4 = categoryPicture4;
    }
}
