package com.example.getsumgame.utils;

import com.example.getsumgame.data.GameListItem;
import com.example.getsumgame.data.GameListResult;
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
}
