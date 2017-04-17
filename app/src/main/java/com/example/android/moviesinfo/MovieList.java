package com.example.android.moviesinfo;

/**
 * Created by Anurag10 on 4/13/2017.
 */

public class MovieList {
    private String posterLink;

    public MovieList(String posterLink){
        this.posterLink = posterLink;
    }

    public String getPosterLink(){
        return posterLink;
    }
}
