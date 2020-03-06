package com.example.getsumgame.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.getsumgame.models.GameInfo;

@Database(entities = {GameInfo.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract SavedReposDao savedReposDao();
    private static volatile AppDatabase INSTANCE;

    static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            "repos_db"
                    ).build();
                }
            }
        }
        return INSTANCE;
    }
}
