package com.webtechdevelopers.sumit.movieticketbookingapp.framework;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.squareup.picasso.Picasso;
import com.webtechdevelopers.sumit.movieticketbookingapp.R;

import java.util.ArrayList;

public class MovieItemRecyclerAdapter extends RecyclerView.Adapter<MovieItemRecyclerAdapter.MovieItemHolder> {

    private ArrayList<Movie> movieArrayList;
    private OnItemSelectedListener onItemSelectedListener;
    public MovieItemRecyclerAdapter(ArrayList<Movie> movieArrayList,OnItemSelectedListener onItemSelectedListener){
        this.movieArrayList=movieArrayList;
        this.onItemSelectedListener=onItemSelectedListener;
    }

    @NonNull
    @Override
    public MovieItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_movie_item,viewGroup,false);
        return new MovieItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieItemHolder movieItemHolder, int i) {
        movieItemHolder.bind(movieArrayList.get(i),onItemSelectedListener);
        Movie movie=movieArrayList.get(i);

        Uri uri = Uri.parse(movie.getImages().get(0));
        movieItemHolder.movieImage.setImageURI(uri);

        movieItemHolder.movieName.setText(movie.getPrimaryInfo());
        movieItemHolder.movieDuration.setText(movie.getReleaseInformation());
        movieItemHolder.movieType.setText(movie.getBelongsToLists());
        Log.i("MovieDetails","\nData: "+movie.toString());
    }

    @Override
    public int getItemCount() {
        return movieArrayList.size();
    }

    public static class MovieItemHolder extends RecyclerView.ViewHolder {
        public SimpleDraweeView movieImage;
        public TextView movieName;
        public TextView movieType;
        public TextView movieDuration;

        public View itemView;

        public MovieItemHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView=itemView;
            movieImage=itemView.findViewById(R.id.movieImage);
            movieName=itemView.findViewById(R.id.movieName);
            movieType=itemView.findViewById(R.id.movieType);
            movieDuration=itemView.findViewById(R.id.movieDuration);
        }
        public void bind(final Movie movie, final OnItemSelectedListener onItemSelectedListener){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemSelectedListener.onItemSelected(movie);
                }
            });
        }
    }
}
