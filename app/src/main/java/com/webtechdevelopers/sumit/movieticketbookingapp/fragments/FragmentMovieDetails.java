package com.webtechdevelopers.sumit.movieticketbookingapp.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.postprocessors.IterativeBoxBlurPostProcessor;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.webtechdevelopers.sumit.movieticketbookingapp.R;
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.Constants;
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.OnFragmentInteractionListener;
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.entities.DetailedMovie;
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.entities.Movie;
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.entities.SpokenLanguage;
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.entities.Video;
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.network.ApiConnector;
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.network.JSONPacketParser;
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.network.OnApiResultRecived;

import java.util.ArrayList;


public class FragmentMovieDetails extends Fragment {
    private Movie movie;
    private DetailedMovie detailedMovie;
    private String type="";

    private CardView movieDetailsCard;

    private TextView textOverview;
    private TextView textAdult;
    private TextView textReleseDate;
    private TextView textProductionCountries;
    private TextView textTagline;
    private TextView textSpokenLanguages;
    private TextView textVoteAverage;
    private TextView textBudget;
    private TextView textHomepage;
    private TextView textRuntime;
    private TextView textProductionCompanies;
    private TextView textTrailersTag;
    private TextView textTrailers;

    private ProgressBar movieDetailsProgress;

    public FragmentMovieDetails() {
    }

    public static FragmentMovieDetails newInstance(Bundle bundle) {
        FragmentMovieDetails fragment = new FragmentMovieDetails();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            movie= (Movie) getArguments().getSerializable("movie");
            type= getArguments().getString("type");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Fresco.initialize(view.getContext());
        Uri uri = Uri.parse(Constants.IMAGE_URL+movie.getPoster_path());

        SimpleDraweeView movieDetailImage = view.findViewById(R.id.movieDetailImage);
        SimpleDraweeView movieDetailBackground = view.findViewById(R.id.movieDetailBackground);
        TextView movieDetailName = view.findViewById(R.id.movieDetailName);
        TextView movieDetailGenre = view.findViewById(R.id.movieDetailType);
        Button bookMovieNow = view.findViewById(R.id.bookMovieButton);

        movieDetailsCard=view.findViewById(R.id.movieDetailsCard);
        movieDetailsProgress=view.findViewById(R.id.movieDetailsProgress);

        Log.i("TYPE",""+type);
        if(!type.equals("upcoming")){
            bookMovieNow.setVisibility(View.VISIBLE);
            bookMovieNow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle=new Bundle();
                    bundle.putSerializable("movie",movie);
                    ((OnFragmentInteractionListener)getActivity()).onFragmentInteractionResult("booking",bundle);
                }
            });
        }else{
            bookMovieNow.setVisibility(View.GONE);
        }

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
        textProductionCompanies=view.findViewById(R.id.textProductionCompanies);
        textTrailersTag=view.findViewById(R.id.textTrailersTag);
        textTrailers=view.findViewById(R.id.textTrailers);

        final ApiConnector apiConnector=new ApiConnector(view.getContext());
        apiConnector.getMovieDetails(movie.getId(), new OnApiResultRecived() {
            @Override
            public void onResult(String response) {
                Log.i("Response Data","Response:\n"+response);
                detailedMovie=JSONPacketParser.getDetailMovie(response);
                apiConnector.getVideos(movie.getId(), new OnApiResultRecived() {
                    @Override
                    public void onResult(String response) {
                        Log.i("ApiConnector","Response: "+response);
                        final ArrayList<Video> videoList=JSONPacketParser.getVideos(response);
                        Log.i("ApiConnector","Videos size: "+videoList.size()+"\nVideos:"+videoList);
                        if(videoList.size()>0){
                            textTrailers.setText(videoList.get(0).getName());
                            textTrailers.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse(Constants.VIDEO_URL+videoList.get(0).getKey())));
                                }
                            });
                        }else{
                            textTrailersTag.setVisibility(View.GONE);
                            textTrailers.setVisibility(View.GONE);
                        }
                        movieDetailsCard.setVisibility(View.VISIBLE);
                        movieDetailsProgress.setVisibility(View.GONE);

                        textOverview.setText(detailedMovie.getOverview());
                        if(detailedMovie.getAdult()){
                            textAdult.setText(getString(R.string.age_warning));
                        }else{
                            textAdult.setVisibility(View.GONE);
                        }
                        String textReleseDateText="Released on "+detailedMovie.getReleaseDate();
                        textReleseDate.setText(textReleseDateText);

                        String countries="Production Countries: ";
                        for(int i=0;i<detailedMovie.getProductionCountries().size();i++){
                            countries+="\n"+detailedMovie.getProductionCountries().get(i).getName();
                        }
                        textProductionCountries.setText(countries);

                        if(detailedMovie.getTagline().equals("")){
                            textTagline.setVisibility(View.GONE);
                        }else{
                            String textTaglineText="Tagline: "+detailedMovie.getTagline();
                            textTagline.setText(textTaglineText);
                        }
                        String textSpokenLanguagesText="Languages: ";
                        for(SpokenLanguage spokenLanguage:detailedMovie.getSpokenLanguages()){
                            textSpokenLanguagesText+=", "+spokenLanguage.getName();
                        }
                        textSpokenLanguages.setText(textSpokenLanguagesText);
                        String textVoteAverageText="Rating: "+detailedMovie.getVoteAverage();
                        textVoteAverage.setText(textVoteAverageText);
                        if(detailedMovie.getBudget()<=0){

                            textBudget.setText(getString(R.string.budget_not_available));
                        }else{
                            String textBudgetText="Budget: $"+detailedMovie.getBudget();
                            textBudget.setText(textBudgetText);
                        }
                        Log.i("textHomepage",""+detailedMovie.getHomepage());
                        if(detailedMovie.getHomepage()!=null&&!detailedMovie.getHomepage().equals("null")) {
                            LinearLayout layoutHomepage = view.findViewById(R.id.layoutHomepage);
                            layoutHomepage.setVisibility(View.VISIBLE);
                            String textHomepageText = "" + detailedMovie.getHomepage();
                            textHomepage.setText(textHomepageText);
                            textHomepage.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(detailedMovie.getHomepage())));
                                }
                            });
                        }
                        String textRuntimeText="Duration: "+detailedMovie.getRuntime()+" minutes";
                        textRuntime.setText(textRuntimeText);
                        if(detailedMovie.getProductionCompanies().size()>0){
                            String companies="Production Companies: ";
                            for(int i=0;i<detailedMovie.getProductionCompanies().size();i++){
                                companies+="\n"+detailedMovie.getProductionCompanies().get(i).getName();
                            }
                            textProductionCompanies.setText(companies);
                        }else{
                            textProductionCompanies.setVisibility(View.GONE);
                        }
                    }
                });

            }
        });
    }
}
