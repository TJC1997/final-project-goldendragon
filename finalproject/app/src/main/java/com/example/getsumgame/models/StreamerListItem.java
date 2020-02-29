package com.example.getsumgame.models;

import java.io.Serializable;

public class StreamerListItem implements Serializable {
    public long user_id;
    public long game_id;
    public int viewer_count;
    public String user_name;
    public String started_at;
    public String thumbnail_url;

}
