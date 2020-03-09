package com.example.getsumgame;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.getsumgame.data.SavedReposRepository;
import com.example.getsumgame.models.GameInfo;
import com.example.getsumgame.models.SavedInfo;

import java.util.List;

public class SavedReposViewModel extends AndroidViewModel {
    private SavedReposRepository mRepository;

    public SavedReposViewModel(Application application) {
        super(application);
        mRepository = new SavedReposRepository(application);
    }

    public void insertSavedRepo(SavedInfo repo) {
        mRepository.insertSavedRepo(repo);
    }

    public void deleteSavedRepo(SavedInfo repo) {
        mRepository.deleteSavedRepo(repo);
    }

    public LiveData<List<SavedInfo>> getAllRepos() {
        return mRepository.getAllRepos();
    }
}
