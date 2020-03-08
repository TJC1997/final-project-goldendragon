package com.example.getsumgame.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.getsumgame.models.GameInfo;

import java.util.List;

@Dao
public interface SavedReposDao {
    @Insert
    void insert(GameInfo repo);

    @Delete
    void delete(GameInfo repo);

    @Query("SELECT * FROM repos")
    LiveData<List<GameInfo>> getAllRepos();
}
