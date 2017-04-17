package com.example.android.moviesinfo;

/**
 * Created by Anurag10 on 4/13/2017.
 */

public class MovieList {
    private String posterLink,title,thumbnail,plotSynopsis,releaseDate;
    private int userRating;

    public MovieList(String posterLink,String title,String thumbnail,String plotSynopsis,String releaseDate,int userRating){
        this.posterLink = posterLink;
        this.title = title;
        this.thumbnail = thumbnail;
        this.releaseDate = releaseDate;
        this.userRating = userRating;
        this.plotSynopsis = plotSynopsis;
    }

    public String getPosterLink(){
        return posterLink;
    }

    public String getTitle(){
        return title;
    }

    public String getThumbnail(){
        return thumbnail;
    }

    public String getReleaseDate(){
        return releaseDate;
    }

    public String getPlotSynopsis(){
        return plotSynopsis;
    }

    public Integer getUserRating(){
        return userRating;
    }
}
