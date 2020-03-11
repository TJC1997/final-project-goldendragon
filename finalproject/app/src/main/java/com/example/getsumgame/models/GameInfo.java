package com.example.getsumgame.models;
import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

public class GameInfo implements Serializable {
    public int Game_id;
    public String Game_name;
    public int streamer_count;
    public int view_number;
    public String language;
}
