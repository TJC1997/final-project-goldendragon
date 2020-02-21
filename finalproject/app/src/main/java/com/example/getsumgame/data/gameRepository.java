package com.example.getsumgame.data;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class gameRepository implements gameAsyncTask.Callback {
    private MutableLiveData<List<GameListItem>> mGameResult;
    private MutableLiveData<Status> mLoadingStatus;
    public gameRepository(){
        mGameResult=new MutableLiveData<>();
        mGameResult.setValue(null);
        mLoadingStatus=new MutableLiveData<>();
        mLoadingStatus.setValue(Status.SUCCESS);
    }

    public MutableLiveData<Status> getmLoadingStatus() {
        return mLoadingStatus;
    }

    public MutableLiveData<List<GameListItem>> getmGameResult() {
        return mGameResult;
    }

    @Override
    public void resultReceived(List<GameListItem> forecastItems) {
        mGameResult.setValue(forecastItems);
        if(mGameResult!=null){
            mLoadingStatus.setValue(Status.SUCCESS);
        }
        else{
            mLoadingStatus.setValue(Status.ERROR);
        }
    }

    public void loadGameResults(String CLIENT_ID,String Get_Top_Game){
        mGameResult.setValue(null);
        mLoadingStatus.setValue(Status.LOADING);
        new gameAsyncTask(this).execute(CLIENT_ID,Get_Top_Game);
    }
}
