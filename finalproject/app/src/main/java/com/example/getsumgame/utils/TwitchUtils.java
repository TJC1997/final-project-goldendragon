package com.example.getsumgame.utils;

import com.example.getsumgame.models.GameListItem;
import com.example.getsumgame.models.GameListResult;
import com.example.getsumgame.models.StreamerListItem;
import com.example.getsumgame.models.StreamerListResult;
import com.google.gson.Gson;

import java.util.ArrayList;

public class TwitchUtils {
    private final static String CLIENT_ID = "du9dzneemr0xtqld8w7n9ysw2dn3zn";
    private final static String Get_Top_Game="https://api.twitch.tv/helix/games/top";

    public static String getClientId() {
        return CLIENT_ID;
    }

    public static String getGet_Top_Game() {
        return Get_Top_Game;
    }



    public static ArrayList<GameListItem>parseGameJson(String gameJson){
        Gson gson = new Gson();
        GameListResult gameResults=gson.fromJson(gameJson,GameListResult.class);
        if (gameResults!=null && gameResults.data!=null){
            return gameResults.data;
        }
        else{
            return null;
        }
    }

    public static ArrayList<StreamerListItem>parseStreamerJson(String StreamerJson){
        Gson gson = new Gson();
        StreamerListResult gameResults=gson.fromJson(StreamerJson,StreamerListResult.class);
        if (gameResults!=null && gameResults.data!=null){
            return gameResults.data;
        }
        else{
            return null;
        }
    }
}
