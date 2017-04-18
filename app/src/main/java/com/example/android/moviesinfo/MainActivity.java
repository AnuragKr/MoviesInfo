package com.example.android.moviesinfo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //Creating a List of Booking History
    private List<MovieList> listPosterLink;
    private String title, thumbnail, plotSynopsis, releaseDate;
    private int userRating;
    //Creating Views
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private String errorMessage = null, message = null;
    private ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Initializing
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        //Toolbar
        try {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Pop Movies");
        } catch (Throwable e) {
            e.printStackTrace();
        }
        //Initializing our Poster list
        listPosterLink = new ArrayList<>();
        //Function For Loading Information From Link
        loadImagePoster();
    }

    private void loadImagePoster() {
        final ProgressDialog pd = new ProgressDialog(MainActivity.this);
        pd.setMessage("Loading...");
        pd.show();
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("api.themoviedb.org")
                .appendPath("3")
                .appendPath("movie")
                .appendPath("popular")
                .appendQueryParameter("api_key", "0bd70c8e2eb34a54d4609a2dac80f818");
        String movieUrl = builder.build().toString();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, movieUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                pd.dismiss();
                parseData(response);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (volleyError instanceof NetworkError) {
                    iv = (ImageView) findViewById(R.id.page_not_found);
                    errorMessage = "Cannot connect to Internet...Please check your connection!";
                    Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            pd.dismiss();
                            iv.setImageResource(R.drawable.page_found);
                            Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();
                        }
                    }, 5000);
                } else if (volleyError instanceof ServerError) {
                    errorMessage = "The server could not be found. Please try again after some time!!";
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            pd.dismiss();
                            Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();
                        }
                    }, 5000);
                } else if (volleyError instanceof AuthFailureError) {
                    errorMessage = "Cannot connect to Internet...Please check your connection!";
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            pd.dismiss();
                            Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();
                        }
                    }, 5000);
                } else if (volleyError instanceof ParseError) {
                    errorMessage = "Parsing error! Please try again after some time!!";
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            pd.dismiss();
                            Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();
                        }
                    }, 5000);
                } else if (volleyError instanceof NoConnectionError) {
                    errorMessage = "Cannot connect to Internet...Please check your connection!";
                    Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            pd.dismiss();
                            Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();
                        }
                    }, 5000);
                } else if (volleyError instanceof TimeoutError) {

                    errorMessage = "Connection TimeOut! Please check your internet connection.";
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            pd.dismiss();
                            Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();
                        }
                    }, 5000);
                }
            }
        });
        //jsonObjectRequest.setShouldCache(false);
        MySingleton.getInstance(MainActivity.this).addToRequestque(jsonObjectRequest);
    }

    //This method will parse json data
    private void parseData(JSONObject json) {

        try {
            //Getting json
            JSONArray json_array = json.getJSONArray("results");
            for (int i = 0; i < json_array.length(); i++) {
                JSONObject jsonObject = json_array.getJSONObject(i);
                Uri.Builder builder = new Uri.Builder();
                builder.scheme("https")
                        .authority("image.tmdb.org")
                        .appendPath("t")
                        .appendPath("p")
                        .appendPath("w500")
                        .appendPath(jsonObject.getString("poster_path").substring(1));
                String posterURL = builder.build().toString();
                //Extracting Information
                plotSynopsis = jsonObject.getString("overview");
                title = jsonObject.getString("original_title");
                releaseDate = jsonObject.getString("release_date");
                userRating = jsonObject.getInt("vote_average");
                Uri.Builder builderThumbnail = new Uri.Builder();
                builderThumbnail.scheme("https")
                        .authority("image.tmdb.org")
                        .appendPath("t")
                        .appendPath("p")
                        .appendPath("w500")
                        .appendPath(jsonObject.getString("backdrop_path").substring(1));
                thumbnail = builderThumbnail.build().toString();
                MovieList listMoviePosterLinks = new MovieList(posterURL, title, thumbnail, plotSynopsis, releaseDate, userRating);
                listPosterLink.add(listMoviePosterLinks);
            }
            //initializing our adapter
            adapter = new MovieAdapter(listPosterLink, getApplicationContext());
            //Adding adapter to recyclerview
            recyclerView.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_item, menu);
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
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}