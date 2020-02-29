package com.example.getsumgame.data;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.getsumgame.models.GameInfo;
import com.example.getsumgame.models.GameListItem;
import com.example.getsumgame.models.StreamerListItem;

import java.util.ArrayList;
import java.util.List;

public class GamesRepository implements GameAsyncTask.Callback {
    private MutableLiveData<List<GameInfo>> mGameInfo;
    private List<GameInfo> tempGameInfoList;
    private MutableLiveData<List<GameListItem>> mGameResult;
    private MutableLiveData<Status> mLoadingStatus;
    private String client_id;
    private String Get_Stream_info;
    private GameListItem currentGame;
    private int count;
    private int goal;
    private int oneTime;

    // Game Streams to Send to Detail Activity
    private MutableLiveData<ArrayList<ArrayList<StreamerListItem>>> mGameStreams;
    private ArrayList<ArrayList<StreamerListItem>> tempGameStreams;


    public GamesRepository(){
        mGameInfo=new MutableLiveData<>();
        tempGameInfoList=new ArrayList<>();
        mGameResult=new MutableLiveData<>();
        mGameResult.setValue(null);
        mLoadingStatus=new MutableLiveData<>();
        mLoadingStatus.setValue(Status.SUCCESS);
        Get_Stream_info="https://api.twitch.tv/helix/streams";
        currentGame=null;
        oneTime=1;

        mGameStreams = new MutableLiveData<>();
    }

    public MutableLiveData<Status> getmLoadingStatus() {
        return mLoadingStatus;
    }

    public MutableLiveData<List<GameListItem>> getmGameResult() {
        return mGameResult;
    }

    public MutableLiveData<List<GameInfo>> getmGameInfo() {
        return mGameInfo;
    }

    public MutableLiveData<ArrayList<ArrayList<StreamerListItem>>> getMGameStreams(){
        return this.mGameStreams;
    }

    @Override
    public void resultReceived(ArrayList<GameListItem> gameResult) {
        if(oneTime==0){
            return;
        }
        if(gameResult!=null && !gameResult.isEmpty()){
            goal=gameResult.size();
        }
        mGameResult.setValue(gameResult);
        if(mGameResult!=null){
            count=0;
            mLoadingStatus.setValue(Status.SUCCESS);
            tempGameInfoList.clear();
//            tempGameInfoList=new ArrayList<>();
            Log.d("Start","DONE with game result");
            if(gameResult!=null){
                for (GameListItem game:gameResult) {
                    loadGameInfo(client_id,game.id);
                }
            }
            oneTime--;
            Log.d("DONE","DONE with game result");
        }
        else{
            mLoadingStatus.setValue(Status.ERROR);
        }
    }

    public void streamerReceived(ArrayList<StreamerListItem> streamerResults){
        if(mGameResult!=null &&streamerResults!=null){
            mLoadingStatus.setValue(Status.SUCCESS);
            currentGame=mGameResult.getValue().get(count);
            GameInfo currentGameInfo= new GameInfo();
            currentGameInfo.Game_id=currentGame.id;
            currentGameInfo.Game_name=currentGame.name;
            currentGameInfo.streamer_count=streamerResults.size();
            int view_count=0;
            for (StreamerListItem stream:streamerResults) {
                view_count+=stream.viewer_count;
            }
            currentGameInfo.view_number=view_count;
            tempGameInfoList.add(currentGameInfo);
//            mGameInfo.setValue(tempGameInfoList);
            Log.d("LENGTH:",Integer.toString( tempGameInfoList.size()));


            // Add The Stream Details to a list for later
            this.tempGameStreams.add(streamerResults);
            this.mGameStreams.setValue(this.tempGameStreams);

            count++;
            if(count==goal){
                mGameInfo.setValue(tempGameInfoList);
            }
        }
        else{
            mLoadingStatus.setValue(Status.ERROR);
        }
    }

    public void loadGameResults(String CLIENT_ID,String Get_Top_Game){
        if(oneTime==0){
            return;
        }
        client_id= CLIENT_ID;
//        mGameResult.setValue(null);
        mLoadingStatus.setValue(Status.LOADING);
        new GameAsyncTask(this).execute(CLIENT_ID,Get_Top_Game,"Get_Top_Game");

    }

    public void loadGameInfo(String CLIENT_ID,int game_id){
        String param="?game_id="+Integer.toString(game_id);
        String url=Get_Stream_info+param;
        new GameAsyncTask(this).execute(CLIENT_ID,url,"Get_Stream_info");
    }
}
