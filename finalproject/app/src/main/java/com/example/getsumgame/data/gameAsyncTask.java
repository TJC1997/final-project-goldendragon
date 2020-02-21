package com.example.getsumgame.data;

import android.os.AsyncTask;
import android.util.Log;

import com.example.getsumgame.utils.NetworkUtils;
import com.example.getsumgame.utils.TwitchUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class gameAsyncTask extends AsyncTask<String, Void, String> {

    private Callback mCallback;

    public interface Callback{
        void resultReceived(List<GameListItem> forecastItems);
    }

    public gameAsyncTask(Callback callback){
        mCallback=callback;
    }

    @Override
    protected String doInBackground(String... params) {
        String id = params[0];
        String request=params[1];
        String gameJSON = "ABC";
        try {
            gameJSON = NetworkUtils.doHTTPGet(id,request);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return gameJSON;
    }

    @Override
    protected void onPostExecute(String s) {
//            TextView test= findViewById(R.id.test);
//            Log.d("123",s);
//            test.setText(s);
        ArrayList<GameListItem> results= TwitchUtils.parseGameJson(s);
        Log.d("123",Integer.toString(results.get(0).id));
        mCallback.resultReceived(results);
    }
}
