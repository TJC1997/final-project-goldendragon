package com.example.getsumgame.data;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.getsumgame.models.StreamerListItem;

import java.util.ArrayList;

public class SingleGameRepository {
    private static final String TAG = SingleGameRepository.class.getName();

    // Live Data for streamers data
    private MutableLiveData<ArrayList<StreamerListItem>> liveStreamerList = null;
    private ArrayList<StreamerListItem> mStreamerList = null;

    // Live data for status of repo
    private MutableLiveData<Status> singleGameStatus = null;

    public SingleGameRepository(){
        liveStreamerList = new MutableLiveData<>();
        mStreamerList = new ArrayList<>();
        singleGameStatus = new MutableLiveData<>();
        singleGameStatus.setValue(Status.EMPTY);
    }

    public void push(ArrayList<StreamerListItem> newList){
        if(this.singleGameStatus.getValue() != Status.LOADING) {
            if (newList != null && !newList.isEmpty()) {
                mStreamerList = newList;
                liveStreamerList.setValue(newList);
                singleGameStatus.setValue(Status.SUCCESS);
            }
        }
    }

    public void pull(String query){
        Log.d(TAG, "The Repository does not know how to pull yet.");
        // Adding this comment here so JetBrains doesn't single line my function.
    }

    public LiveData<ArrayList<StreamerListItem>> getLiveStreamerList(){
        return this.liveStreamerList;
    }

    public LiveData<Status> getStatus(){
        return this.singleGameStatus;
    }


}
