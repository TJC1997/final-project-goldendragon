package com.example.getsumgame.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.getsumgame.models.GameInfo;
import com.example.getsumgame.data.Status;
import com.example.getsumgame.data.GamesRepository;
import com.example.getsumgame.models.StreamerListItem;

import java.util.ArrayList;
import java.util.List;

public class GameViewModel extends ViewModel {
    private GamesRepository mRepo;
    private LiveData<List<GameInfo>> mGameInfo;
//    private LiveData<List<GameListItem>> mGameResult;
    private LiveData<Status> mLoadingStatus;
    private LiveData<ArrayList<ArrayList<StreamerListItem>>> mStreamsList;


    public GameViewModel(){
        mRepo = new GamesRepository();
//        mGameResult=mRepo.getmGameResult();
        mGameInfo=mRepo.getmGameInfo();
        mLoadingStatus=mRepo.getmLoadingStatus();
        this.mStreamsList = mRepo.getMGameStreams();
    }

    public void loadGameResults(String CLIENT_ID,String Get_Top_Game){
        mRepo.loadGameResults(CLIENT_ID,Get_Top_Game);
    }

    public LiveData<Status> getmLoadingStatus() {
        return mLoadingStatus;
    }

    public LiveData<List<GameInfo>> getmGameInfo() {
        return mGameInfo;
    }
    //    public LiveData<List<GameListItem>> getmGameResult() {
//        return mGameResult;
//    }

    public LiveData<ArrayList<ArrayList<StreamerListItem>>> getStreamers(){
        return this.mStreamsList;
    }

    public void setLanguagePreference(String languagePreference) {
        this.mRepo.setLanguagePreference(languagePreference);
    }
}

