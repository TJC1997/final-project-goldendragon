package com.example.getsumgame.data;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.getsumgame.models.CoupRatingResult;
import com.example.getsumgame.models.CoupResult;
import com.example.getsumgame.models.StreamerListItem;
import com.example.getsumgame.utils.CoupUtils;

import java.util.ArrayList;

public class ChickenCoupRepository implements CoupAsyncTask.Callback {
    private static final String TAG = ChickenCoupRepository.class.getName();

    // Live Data for streamers data
    private MutableLiveData<CoupResult> coupResult = null;

    // Live data for status of repo
    private MutableLiveData<Status> repoStatus = null;

    // Cacheing Method to improve upon later. Does not repeat queries
    private String prev_query;

    public ChickenCoupRepository(){
        this.coupResult = new MutableLiveData<>();
        this.repoStatus = new MutableLiveData<>();
        this.repoStatus.setValue(Status.EMPTY);
    }

    public void push(CoupResult newVal){
        if(this.repoStatus.getValue() != Status.LOADING) {
            if (newVal != null && newVal.result != null) {
                this.coupResult.setValue(newVal);
                this.repoStatus.setValue(Status.SUCCESS);
                this.prev_query = CoupUtils.site + newVal.query;
            }
        }
    }

    public void pull(String gameName){
        if(this.repoStatus.getValue() != Status.LOADING) {

            // Ugh computing this twice... Maybe I should change the CoupAsyncTask Interface
            String query = CoupUtils.getQuery(gameName);

            if (this.prev_query == null || !this.prev_query.equals(query)) {
                this.prev_query = query;
                this.repoStatus.setValue(Status.LOADING);
                new CoupAsyncTask(this).execute(gameName);
            }

        }
    }

    public LiveData<CoupResult> getCoupResult(){
        return this.coupResult;
    }

    public LiveData<Status> getStatus(){
        return this.repoStatus;
    }

    @Override
    public void result(CoupResult result) {
        if(result == null){
            this.repoStatus.postValue(Status.ERROR);
        }else if (result.result == null){
            this.repoStatus.postValue(Status.BAD);
        }else{
            this.coupResult.postValue(result);
            this.repoStatus.postValue(Status.SUCCESS);
        }
    }
}
