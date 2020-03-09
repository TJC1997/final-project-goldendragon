package com.example.getsumgame.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "repos")
public class SavedInfo {
    @PrimaryKey
    @NonNull
    public String gameID;
    public String gameName;
    public int index;
}
