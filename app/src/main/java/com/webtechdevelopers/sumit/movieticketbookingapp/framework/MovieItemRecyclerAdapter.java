package com.webtechdevelopers.sumit.movieticketbookingapp.framework;

import android.net.Uri;
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

import java.util.ArrayList;
import java.util.Arrays;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
//        movieItemHolder.movieBackground.setImageURI(backgroundUri);
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(backgroundUri)
                .setPostprocessor(new IterativeBoxBlurPostProcessor(20))
                .build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(movieItemHolder.movieBackground.getController())
                .build();
        movieItemHolder.movieBackground.setController(controller);


        movieItemHolder.movieName.setText(movie.getTitle());
        movieItemHolder.movieDuration.setText(""+movie.getVote_average());

        movieItemHolder.movieType.setText(movie.getGenres());
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
        public TextView movieType;
        public TextView movieDuration;

        public View itemView;

        public MovieItemHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView=itemView;
            movieImage=itemView.findViewById(R.id.movieImage);
            movieBackground=itemView.findViewById(R.id.movieBackground);
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