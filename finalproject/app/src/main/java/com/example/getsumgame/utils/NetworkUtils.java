package com.example.getsumgame.utils;

import android.util.Log;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NetworkUtils {

    private static final String TAG = "123";
    private static final OkHttpClient mHTTPClient = new OkHttpClient();

    public static String doHTTPGet(String client,String requestUrl) throws IOException {
        Request request = new Request.Builder()
                .addHeader("Client-ID",client)
                .url(requestUrl)
                .build();
        Log.d(TAG,request.toString());
        Response response = mHTTPClient.newCall(request).execute();
        try {
            return response.body().string();
        } finally {
            response.close();
        }
    }

    // Throwing Exception bad!!!
    public static String doSimpleHTTPGet(String requestUrl) {
        Request request = new Request.Builder()
                .url(requestUrl)
                .build();

        String res = null;
        Response response = null;

        try{
            response = mHTTPClient.newCall(request).execute();
            res = response.body().string();
        } catch (IOException e){
            Log.e(TAG, e.toString());
            Log.e(TAG, "Assuming empty String");
            res = "";
        } catch(NullPointerException e){
            Log.e(TAG, "Response body did not have a string :\\");
            Log.e(TAG, e.toString());
            Log.e(TAG, "Assuming empty String");
            res = "";
        }finally{
            if (response != null) {
                response.close();
            }
        }

        return res;
    }

}
