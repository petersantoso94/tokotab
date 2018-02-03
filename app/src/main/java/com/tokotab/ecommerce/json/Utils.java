package com.tokotab.ecommerce.json;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by DIDLPSANTOSO on 5/11/2016.
 */
public class Utils {
    public static final String BASE_URL = "http://home.jualangadget.com/loadBarangPeter";
    public static JSONObject getObject(String tagName, JSONObject jsonObject) throws JSONException {
        JSONObject jObj = jsonObject.getJSONObject(tagName);
        return jObj;
    }
    public static String getString(String tagName, JSONObject jsonObject) throws JSONException{
        return jsonObject.getString(tagName);
    }
    public static float getFloat(String tagName, JSONObject jsonObject) throws JSONException{
        return (float) jsonObject.getDouble(tagName);
    }
    public static int getInt(String tagName, JSONObject jsonObject) throws JSONException{
        return jsonObject.getInt(tagName);
    }
}
