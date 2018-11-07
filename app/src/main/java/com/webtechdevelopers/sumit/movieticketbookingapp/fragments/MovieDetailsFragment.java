package com.webtechdevelopers.sumit.movieticketbookingapp.fragments;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.Constants;
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.Movie;

public class MovieDetailsFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private Movie movie;

    public SimpleDraweeView movieDetailImage;
    public SimpleDraweeView movieDetailBackground;
    public TextView movieDetailName;
    public TextView movieDetailGenre;

    public MovieDetailsFragment() {
    }

    public static MovieDetailsFragment newInstance(Bundle bundle) {
        MovieDetailsFragment fragment = new MovieDetailsFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            movie= (Movie) getArguments().getSerializable("movie");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Uri uri = Uri.parse(Constants.IMAGE_URL+movie.getPoster_path());

        movieDetailImage =view.findViewById(R.id.movieDetailImage);
        movieDetailBackground =view.findViewById(R.id.movieDetailBackground);
        movieDetailName =view.findViewById(R.id.movieDetailName);
        movieDetailGenre =view.findViewById(R.id.movieDetailType);

        movieDetailImage.setImageURI(uri);
        Uri backgroundUri = Uri.parse(Constants.IMAGE_URL+movie.getBackdrop_path());
//        movieItemHolder.movieDetailBackground.setImageURI(backgroundUri);
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(backgroundUri)
                .setPostprocessor(new IterativeBoxBlurPostProcessor(20))
                .build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(movieDetailBackground.getController())
                .build();
        movieDetailBackground.setController(controller);

        movieDetailName.setText(movie.getTitle());

        movieDetailGenre.setText(movie.getGenres());

    }
}
