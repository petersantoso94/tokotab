package com.tokotab.ecommerce.model;

/**
 * Created by DIDLPSANTOSO on 6/20/2016.
 */
public class User {
    String InternalID, MemberID, MemberEmail, MemberName, MemberPhone, MemberAddress, MemberType;
    public static final String INTERNAL_ID = "InternalID";
    public static final String MEMBER_ID = "MemberID";
    public static final String MEMBER_EMAIL = "MemberEmail";
    public static final String MEMBER_NAME = "MemberName";
    public static final String MEMBER_PHONE = "MemberPhone";
    public static final String MEMBER_ADDRESS = "MemberAddress";
    public static final String MEMBER_TYPE = "MemberType";
    public static final String MEMBER_PASS = "MemberPass";
    public User(String internalID, String memberID, String memberEmail, String memberName, String memberPhone, String memberAddress, String memberType) {
        InternalID = internalID;
        MemberID = memberID;
        MemberEmail = memberEmail;
        MemberName = memberName;
        MemberPhone = memberPhone;
        MemberAddress = memberAddress;
        MemberType = memberType;
    }

    public User() {
    }

    public String getInternalID() {
        return InternalID;
    }

    public void setInternalID(String internalID) {
        InternalID = internalID;
    }

    public String getMemberID() {
        return MemberID;
    }

    public void setMemberID(String memberID) {
        MemberID = memberID;
    }

    public String getMemberEmail() {
        return MemberEmail;
    }

    public void setMemberEmail(String memberEmail) {
        MemberEmail = memberEmail;
    }

    public String getMemberName() {
        return MemberName;
    }

    public void setMemberName(String memberName) {
        MemberName = memberName;
    }

    public String getMemberPhone() {
        return MemberPhone;
    }

    public void setMemberPhone(String memberPhone) {
        MemberPhone = memberPhone;
    }

    public String getMemberAddress() {
        return MemberAddress;
    }

    public void setMemberAddress(String memberAddress) {
        MemberAddress = memberAddress;
    }

    public String getMemberType() {
        return MemberType;
    }

    public void setMemberType(String memberType) {
        MemberType = memberType;
    }
}
