package com.example.getsumgame.data;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.getsumgame.models.GameInfo;
import com.example.getsumgame.models.SavedInfo;

import java.util.List;

public class SavedReposRepository {
    private SavedReposDao mDAO;

    public SavedReposRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        mDAO = db.savedReposDao();
    }

    public void insertSavedRepo(SavedInfo repo) {
        new InsertAsyncTask(mDAO).execute(repo);
    }

    public void deleteSavedRepo(SavedInfo repo) {
        new DeleteAsyncTask(mDAO).execute(repo);
    }

    public LiveData<List<SavedInfo>> getAllRepos() {
        return mDAO.getAllRepos();
    }

    private static class InsertAsyncTask extends AsyncTask<SavedInfo, Void, Void> {
        private SavedReposDao mAsyncTaskDAO;
        InsertAsyncTask(SavedReposDao dao) {
            mAsyncTaskDAO = dao;
        }

        @Override
        protected Void doInBackground(SavedInfo... gameRepos) {
            mAsyncTaskDAO.insert(gameRepos[0]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<SavedInfo, Void, Void> {
        private SavedReposDao mAsyncTaskDAO;
        DeleteAsyncTask(SavedReposDao dao) {
            mAsyncTaskDAO = dao;
        }

        @Override
        protected Void doInBackground(SavedInfo... gameRepos) {
            mAsyncTaskDAO.delete(gameRepos[0]);
            return null;
        }
    }
}
