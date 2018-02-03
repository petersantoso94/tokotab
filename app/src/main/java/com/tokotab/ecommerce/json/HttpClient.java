package com.tokotab.ecommerce.json;

import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by DIDLPSANTOSO on 5/11/2016.
 */
public class HttpClient {
    public InputStream getData(String target){
        HttpURLConnection connection = null;
        InputStream inputStream = null;

        try {
            connection = (HttpURLConnection) (new URL(target)).openConnection();
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.connect();
            //read the response
            inputStream = connection.getInputStream();
            return inputStream;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String sendJSON(String JsonDATA, String target){
        String JsonResponse = null;
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        try {
            URL url = new URL(target);
            urlConnection =(HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            // is output buffer writter
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            urlConnection.setRequestProperty("X-Requested-With", "XMLHttpRequest");
            //urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.connect();
//set headers and method
            Log.d("data","isi data = "+JsonDATA);
            OutputStream writer = new BufferedOutputStream(urlConnection.getOutputStream());
            writer.write(JsonDATA.getBytes());
            writer.flush();
// json data
            writer.close();
            InputStream inputStream = urlConnection.getInputStream();
//input stream
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String inputLine;
            while ((inputLine = reader.readLine()) != null)
                buffer.append(inputLine + "\n");
            if (buffer.length() == 0) {
                // Stream was empty. No point in parsing.
                return null;
            }
            JsonResponse = buffer.toString();
            Log.d("data",JsonResponse);
//response data
            //send to post execute
            urlConnection.disconnect();
            return JsonResponse;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public InputStream sendJSONstream(String JsonDATA, String target){
        String JsonResponse = null;
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        try {
            URL url = new URL(target);
            urlConnection =(HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            // is output buffer writter
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            urlConnection.setRequestProperty("X-Requested-With", "XMLHttpRequest");
            //urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.connect();
//set headers and method
            Log.d("data","isi data = "+JsonDATA);
            OutputStream writer = new BufferedOutputStream(urlConnection.getOutputStream());
            writer.write(JsonDATA.getBytes());
            writer.flush();
// json data
            writer.close();
            InputStream inputStream = urlConnection.getInputStream();
            return inputStream;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
