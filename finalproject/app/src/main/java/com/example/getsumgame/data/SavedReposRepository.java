package com.example.getsumgame.data;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.getsumgame.models.GameInfo;

import java.util.List;

public class SavedReposRepository {
    private SavedReposDao mDAO;

    public SavedReposRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        mDAO = db.savedReposDao();
    }

    public void insertSavedRepo(GameInfo repo) {
        new InsertAsyncTask(mDAO).execute(repo);
    }

    public void deleteSavedRepo(GameInfo repo) {
        new DeleteAsyncTask(mDAO).execute(repo);
    }

    public LiveData<List<GameInfo>> getAllRepos() {
        return mDAO.getAllRepos();
    }

    private static class InsertAsyncTask extends AsyncTask<GameInfo, Void, Void> {
        private SavedReposDao mAsyncTaskDAO;
        InsertAsyncTask(SavedReposDao dao) {
            mAsyncTaskDAO = dao;
        }

        @Override
        protected Void doInBackground(GameInfo... gameRepos) {
            mAsyncTaskDAO.insert(gameRepos[0]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<GameInfo, Void, Void> {
        private SavedReposDao mAsyncTaskDAO;
        DeleteAsyncTask(SavedReposDao dao) {
            mAsyncTaskDAO = dao;
        }

        @Override
        protected Void doInBackground(GameInfo... gameRepos) {
            mAsyncTaskDAO.delete(gameRepos[0]);
            return null;
        }
    }
}
