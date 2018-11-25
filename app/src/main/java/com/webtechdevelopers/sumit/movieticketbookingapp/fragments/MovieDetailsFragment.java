package com.webtechdevelopers.sumit.movieticketbookingapp.fragments;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.Constants;
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.MovieItemRecyclerAdapter;
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.OnFragmentInteractionListener;
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.OnItemSelectedListener;
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.entities.Movie;
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.network.ApiConnector;
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.network.JSONPacketParser;
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.network.OnApiResultRecived;

import java.util.ArrayList;

public class MovieDetailsFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private Movie movie;

    public SimpleDraweeView movieDetailImage;
    public SimpleDraweeView movieDetailBackground;
    public TextView movieDetailName;
    public TextView movieDetailGenre;

    TextView textOverview;
    TextView textAdult;
    TextView textReleseDate;
    TextView textProductionCountries;
    TextView textTagline;
    TextView textSpokenLanguages;
    TextView textVoteAverage;
    TextView textBudget;
    TextView textHomepage;
    TextView textRuntime;

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

        textOverview=view.findViewById(R.id.textOverview);
        textReleseDate=view.findViewById(R.id.textReleseDate);
        textAdult=view.findViewById(R.id.textAdult);
        textProductionCountries=view.findViewById(R.id.textProductionCountries);
        textTagline=view.findViewById(R.id.textTagline);
        textSpokenLanguages=view.findViewById(R.id.textSpokenLanguages);
        textVoteAverage=view.findViewById(R.id.textVoteAverage);
        textBudget=view.findViewById(R.id.textBudget);
        textHomepage=view.findViewById(R.id.textHomepage);
        textRuntime=view.findViewById(R.id.textRuntime);

        ApiConnector apiConnector=new ApiConnector(view.getContext());
        apiConnector.getMovieDetails(movie.getId(), new OnApiResultRecived() {
            @Override
            public void onResult(String response) {
                Log.i("Response Data","Response:\n"+response);
                movie=JSONPacketParser.getDetailMovie(response);
                textOverview.setText(movie.getOverview());
                if(movie.isAdult()){
                    textAdult.setText("Note: 18+ Age warning");
                }else{
                    textAdult.setVisibility(View.GONE);
                }
                textReleseDate.setText("Released on "+movie.getRelease_date());

                String countries=movie.getCountries();
                textProductionCountries.setText("Production Countries: "+countries);

                if(movie.getTag_line().equals("")){
                    textTagline.setVisibility(View.GONE);
                }else{
                    textTagline.setText("Tagline: "+movie.getTag_line());
                }
                textSpokenLanguages.setText("Languages: "+movie.getSpoken_languages());
                textVoteAverage.setText("Rating: "+movie.getVote_average());
                textBudget.setText("Budget: "+movie.getBudget());
                textHomepage.setText("Website: "+movie.getHomepage());
                textRuntime.setText("Duration: "+movie.getRuntime()+" minutes");
            }
        });
    }
}
