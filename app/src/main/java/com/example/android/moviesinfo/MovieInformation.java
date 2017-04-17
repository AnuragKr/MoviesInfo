package com.example.android.moviesinfo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

/**
 * Created by Anurag10 on 4/17/2017.
 */

public class MovieInformation extends AppCompatActivity {
    //Creating a List of Booking History
    private String posterLink, title, thumbnail, plotSynopsis, releaseDate, userRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_information);
        Intent intent = getIntent();
        Bundle bd = intent.getExtras();
        if (bd != null) {
            title = (String) bd.get("title");
            thumbnail = (String) bd.get("thumbnail");
            plotSynopsis = (String) bd.get("plotSynopsis");
            releaseDate = (String) bd.get("releaseDate");
            userRating = (String) bd.get("userRating");

        }
        try {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            toolbar.setNavigationIcon(R.drawable.arrow_left);
            getSupportActionBar().setTitle("Movies Info");
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
            });
        } catch (Throwable e) {
            e.printStackTrace();
        }
        Button titleButton = (Button) findViewById(R.id.setTitle);
        titleButton.setText(title);
        TextView userRatingText = (TextView) findViewById(R.id.userRating);
        TextView releaseDateText = (TextView) findViewById(R.id.releaseDate);
        TextView plotSynopsisText = (TextView) findViewById(R.id.plotSynopsis);
        ImageView thumbnailImage = (ImageView) findViewById(R.id.thumbnailImage);
        Button favouriteButton = (Button) findViewById(R.id.favouriteMovie);
        Glide.with(getApplicationContext())
                .load(thumbnail)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .override(200, 300)
                .centerCrop()
                .into(thumbnailImage);
        userRatingText.setText(userRating + "/" + "10");
        releaseDateText.setText(releaseDate);
        plotSynopsisText.setText(plotSynopsis);
        favouriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Marked As Favourite For You", Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_movie_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.top_rated:
                Intent intent = new Intent(getApplicationContext(), TopRatedMovies.class);
                startActivity(intent);
                return true;
            case R.id.popular:
                Intent intentPop = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intentPop);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
