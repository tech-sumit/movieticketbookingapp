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
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.entities.Movie;
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.network.ApiConnector;
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.network.JSONPacketParser;
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.network.OnApiResultRecived;


public class FragmentMovieDetails extends Fragment {
    private Movie movie;
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

        ApiConnector apiConnector=new ApiConnector(view.getContext());
        apiConnector.getMovieDetails(movie.getId(), new OnApiResultRecived() {
            @Override
            public void onResult(String response) {
                Log.i("Response Data","Response:\n"+response);

                movieDetailsCard.setVisibility(View.VISIBLE);
                movieDetailsProgress.setVisibility(View.GONE);

                movie=JSONPacketParser.getDetailMovie(response);
                textOverview.setText(movie.getOverview());
                if(movie.isAdult()){
                    textAdult.setText(getString(R.string.age_warning));
                }else{
                    textAdult.setVisibility(View.GONE);
                }
                String textReleseDateText="Released on "+movie.getRelease_date();
                textReleseDate.setText(textReleseDateText);

                String countries="Production Countries: "+movie.getCountries();
                textProductionCountries.setText(countries);

                if(movie.getTag_line().equals("")){
                    textTagline.setVisibility(View.GONE);
                }else{
                    String textTaglineText="Tagline: "+movie.getTag_line();
                    textTagline.setText(textTaglineText);
                }
                String textSpokenLanguagesText="Languages: "+movie.getSpoken_languages();
                textSpokenLanguages.setText(textSpokenLanguagesText);
                String textVoteAverageText="Rating: "+movie.getVote_average();
                textVoteAverage.setText(textVoteAverageText);
                if(movie.getBudget()<=0){

                    textBudget.setText(getString(R.string.budget_not_available));
                }else{
                    String textBudgetText="Budget: $"+movie.getBudget();
                    textBudget.setText(textBudgetText);
                }

                if(movie.getHomepage().equals("null")){
                    textHomepage.setVisibility(View.GONE);
                    TextView textWebsiteTag=view.findViewById(R.id.textWebsiteTag);
                    textWebsiteTag.setText(getString(R.string.website_not_available));
                }else{
                    String textHomepageText=""+movie.getHomepage();
                    textHomepage.setText(textHomepageText);
                    textHomepage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse(movie.getHomepage())));
                        }
                    });
                }
                String textRuntimeText="Duration: "+movie.getRuntime()+" minutes";
                textRuntime.setText(textRuntimeText);
                if(movie.getCompanies().size()>0){
                    String companies="Production Companies: \n"+movie.getCompanies().get(0).getName();
                    for(int i=1;i<movie.getCompanies().size();i++){
                        companies+="\n"+movie.getCompanies().get(i).getName();
                    }
                    textProductionCompanies.setText(companies);
                }else{
                    textProductionCompanies.setVisibility(View.GONE);
                }
            }
        });
    }
}
