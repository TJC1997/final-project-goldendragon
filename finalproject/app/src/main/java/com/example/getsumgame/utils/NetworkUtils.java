package com.example.getsumgame.utils;

import android.util.Log;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NetworkUtils {

    private static final OkHttpClient mHTTPClient = new OkHttpClient();

    public static String doHTTPGet(String client,String requestUrl) throws IOException {
        Request request = new Request.Builder()
                .addHeader("Client-ID",client)
                .url(requestUrl)
                .build();
        Log.d("123",request.toString());
        Response response = mHTTPClient.newCall(request).execute();
        Log.d("123",response.toString());
        try {
            return response.body().string();
        } finally {
            response.close();
        }
    }

}
