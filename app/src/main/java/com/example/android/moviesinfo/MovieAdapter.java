package com.example.android.moviesinfo;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

/**
 * Created by Anurag10 on 4/13/2017.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private List<MovieList> listPosterLinks;
    static int temp = 0;
    private final Context context;

    public MovieAdapter(List<MovieList> listPosterLinks, Context context){
        this.listPosterLinks = listPosterLinks;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_card,parent,false);
        return new ViewHolder(v);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final MovieList listPosterLink = listPosterLinks.get(position);
        Glide.with(context)
                .load(listPosterLink.getPosterLink())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .override(200,300)
                .centerCrop()
                .into(holder.imageView);
        //holder.textView.setText("Hello Boss");
    }

    @Override
    public int getItemCount() {
        return listPosterLinks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView imageView;
        public TextView textView;
        public ViewHolder(View itemView){
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.moviePoster);
            textView = (TextView) itemView.findViewById(R.id.textView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    temp++;
                    final MovieList listPosterLink = listPosterLinks.get(getAdapterPosition());
                    //Log.v("New Links",listPosterLink.getPosterLink());
                    Intent i = new Intent(v.getContext(), MovieInformation.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    i.putExtra("userRating", Integer.toString(listPosterLink.getUserRating()));
                    i.putExtra("title",listPosterLink.getTitle());
                    i.putExtra("thumbnail",listPosterLink.getThumbnail());
                    i.putExtra("plotSynopsis",listPosterLink.getPlotSynopsis());
                    i.putExtra("releaseDate",listPosterLink.getReleaseDate());
                    context.startActivity(i);
                }
            });
        }
    }

}