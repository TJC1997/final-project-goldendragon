package com.example.getsumgame.models;

import java.io.Serializable;
import java.util.ArrayList;

public class CoupRatingResult implements Serializable {
    public String title;

    // Poorly Formatted Date... Seems to be related to locale :\
    public String releaseDate;

    public String description;
    public ArrayList<String> genre;
    public String image;
    public int score;
    public String developer;
    public ArrayList<String> publisher;
    public String rating;
    public ArrayList<String> alsoAvailableOn;

}
