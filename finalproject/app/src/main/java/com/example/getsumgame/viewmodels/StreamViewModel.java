package com.example.getsumgame.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.getsumgame.data.ChickenCoupRepository;
import com.example.getsumgame.data.SingleGameRepository;
import com.example.getsumgame.data.Status;
import com.example.getsumgame.models.CoupResult;
import com.example.getsumgame.models.StreamerListItem;

import java.util.ArrayList;

public class StreamViewModel extends ViewModel {


    /**************** STREAM REPO FIELDS *************************/
    private LiveData<ArrayList<StreamerListItem>> streamData = null;
    private SingleGameRepository mStreamRepo = null;
    private LiveData<Status> streamRepoStatus = null;



    /**************** COUP REPO FIELDS *************************/
    private LiveData<CoupResult> coupData = null;
    private ChickenCoupRepository mCoupRepo = null;
    private LiveData<Status> coupRepoStatus = null;



    /**************** Constructor *************************/
    public StreamViewModel(){
        this.mStreamRepo = new SingleGameRepository();
        this.streamData = this.mStreamRepo.getLiveStreamerList();
        this.streamRepoStatus = this.mStreamRepo.getStatus();

        this.mCoupRepo = new ChickenCoupRepository();
        this.coupData = this.mCoupRepo.getCoupResult();
        this.coupRepoStatus = this.mCoupRepo.getStatus();
    }



    /**************** STREAM REPO METHODS *************************/
    public LiveData<ArrayList<StreamerListItem>> getStreamData(){
        return this.streamData;
    }

    public LiveData<Status> getStreamRepoStatus(){
        return this.streamRepoStatus;
    }

    public void streamDataPush(ArrayList<StreamerListItem> temp){
        this.mStreamRepo.push(temp);
    }

    public void streamDataPull(String query){
        this.mStreamRepo.pull(query);
    }



    /**************** COUP REPO METHODS *************************/
    public LiveData<CoupResult> getCoupData(){
        return this.coupData;
    }

    public LiveData<Status> getCoupRepoStatus(){
        return this.coupRepoStatus;
    }

    public void coupDataPush(CoupResult newVal){
        this.mCoupRepo.push(newVal);
    }

    public void coupDataPull(String gameName){
        this.mCoupRepo.pull(gameName);
    }
}
