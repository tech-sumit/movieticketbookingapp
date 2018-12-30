package com.webtechdevelopers.sumit.movieticketbookingapp.framework;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.postprocessors.IterativeBoxBlurPostProcessor;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.webtechdevelopers.sumit.movieticketbookingapp.R;
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.entities.Movie;

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
        Fresco.initialize(viewGroup.getContext());
        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_movie_item,viewGroup,false);
        return new MovieItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieItemHolder movieItemHolder, int i) {
        Movie movie=movieArrayList.get(i);
        movieItemHolder.bind(movie,onItemSelectedListener);

        Uri uri = Uri.parse(Constants.IMAGE_URL+movie.getPoster_path());
        movieItemHolder.movieImage.setImageURI(uri);

        Uri backgroundUri = Uri.parse(Constants.IMAGE_URL+movie.getBackdrop_path());
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(backgroundUri)
                .setPostprocessor(new IterativeBoxBlurPostProcessor(20))
                .build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(movieItemHolder.movieBackground.getController())
                .build();
        movieItemHolder.movieBackground.setController(controller);


        movieItemHolder.movieName.setText(movie.getTitle());
        movieItemHolder.movieRating.setText(""+movie.getVote_average());

        movieItemHolder.movieGenre.setText(movie.getGenres());
        Log.i("MovieDetails","\nData: "+movie.toString());
    }

    @Override
    public int getItemCount() {
        return movieArrayList.size();
    }

    public static class MovieItemHolder extends RecyclerView.ViewHolder {
        public SimpleDraweeView movieImage;
        public SimpleDraweeView movieBackground;
        public TextView movieName;
        public TextView movieGenre;
        public TextView movieRating;

        public View itemView;

        public MovieItemHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView=itemView;
            movieImage=itemView.findViewById(R.id.movieImage);
            movieBackground=itemView.findViewById(R.id.movieBackground);
            movieName=itemView.findViewById(R.id.movieName);
            movieGenre =itemView.findViewById(R.id.movieType);
            movieRating =itemView.findViewById(R.id.movieDuration);
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
