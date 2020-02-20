package com.example.getsumgame.utils;

public class TwitchUtils {
    private final static String CLIENT_ID = "du9dzneemr0xtqld8w7n9ysw2dn3zn";
    private final static String Get_Top_Game="https://api.twitch.tv/helix/games/top";

    public static String getClientId() {
        return CLIENT_ID;
    }

    public static String getGet_Top_Game() {
        return Get_Top_Game;
    }
}
