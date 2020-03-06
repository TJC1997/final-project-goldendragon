package com.example.getsumgame;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.getsumgame.data.SavedReposRepository;
import com.example.getsumgame.models.GameInfo;

import java.util.List;

public class SavedReposViewModel extends AndroidViewModel {
    private SavedReposRepository mRepository;

    public SavedReposViewModel(Application application) {
        super(application);
        mRepository = new SavedReposRepository(application);
    }

    public void insertSavedRepo(GameInfo repo) {
        mRepository.insertSavedRepo(repo);
    }

    public void deleteSavedRepo(GameInfo repo) {
        mRepository.deleteSavedRepo(repo);
    }

    public LiveData<List<GameInfo>> getAllRepos() {
        return mRepository.getAllRepos();
    }
}
