package com.example.getsumgame.models;
import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "repos")
public class GameInfo implements Serializable {
    @PrimaryKey
    @NonNull
    public int Game_id;
    public String Game_name;
    public int streamer_count;
    public int view_number;
}
