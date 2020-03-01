package com.example.getsumgame.data;

import android.os.AsyncTask;

import com.example.getsumgame.models.GameListItem;
import com.example.getsumgame.models.StreamerListItem;
import com.example.getsumgame.utils.NetworkUtils;
import com.example.getsumgame.utils.TwitchUtils;

import java.io.IOException;
import java.util.ArrayList;

public class GameAsyncTask extends AsyncTask<String, Void, String> {

    private Callback mCallback;
    private int type;

    public interface Callback{
        void resultReceived(ArrayList<GameListItem> gameResults);
        void streamerReceived(ArrayList<StreamerListItem> streamerResults);
    }

    public GameAsyncTask(Callback callback){
        mCallback=callback;
    }

    @Override
    protected String doInBackground(String... params) {
        String id = params[0];
        String request=params[1];
        String requestType=params[2];
        String gameJSON = "ABC";

        if(requestType.equals("Get_Top_Game")){
            type=1;
            try {
                gameJSON = NetworkUtils.doHTTPGet(id,request);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(requestType.equals("Get_Stream_info")){
            type=2;
            try {
                gameJSON = NetworkUtils.doHTTPGet(id,request);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return gameJSON;
    }

    @Override
    protected void onPostExecute(String s) {
        if(type==1) {
            ArrayList<GameListItem> results = TwitchUtils.parseGameJson(s);
//            Log.d("123", Integer.toString(results.get(0).id));
            mCallback.resultReceived(results);
        }
        else if(type==2){
            ArrayList<StreamerListItem> results=TwitchUtils.parseStreamerJson(s);
            mCallback.streamerReceived(results);
        }
    }
}
