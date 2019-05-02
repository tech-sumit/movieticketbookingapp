package com.webtechdevelopers.sumit.movieticketbookingapp.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.webtechdevelopers.sumit.movieticketbookingapp.R;
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.Constants;
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.OnFragmentInteractionListener;
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.entities.Movie;
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.network.ApiConnector;
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.network.JSONPacketParser;
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.network.OnAdiResultReceived;

import java.util.ArrayList;

public class FragmentMain extends Fragment {
    private SimpleDraweeView latestMovieImage;
    private TextView latestMovieName;
    private TextView latestMovieType;
    private TextView latestMovieDuration;
    @NonNull
    private String lastFragment = "";
    private ImageView starMovieIcon;

    public FragmentMain() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        if (container != null) {
            Fresco.initialize(container.getContext());
        } else {
            if (getActivity() != null)
                getActivity().finish();
        }
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TabLayout tabLayout = view.findViewById(R.id.tabLayout);

        ViewPager mViewPager = view.findViewById(R.id.viewPager);
        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager());
        mViewPager.setAdapter(mSectionsPagerAdapter);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        latestMovieImage = view.findViewById(R.id.latestMovieImage);
        latestMovieName = view.findViewById(R.id.latestMovieName);
        latestMovieType = view.findViewById(R.id.latestMovieType);
        latestMovieDuration = view.findViewById(R.id.latestMovieDuration);
        starMovieIcon = view.findViewById(R.id.starImageIcon);
        ConstraintLayout constraintLayout=view.findViewById(R.id.fragment_container);
        lastFragment = "main_fragment";

        ApiConnector apiConnector = new ApiConnector(view.getContext());
        apiConnector.getPopularMovies(1, new OnAdiResultReceived() {
            @Override
            public void onResult(String response) {
                Log.i("Response Data", "Response:\n" + response);
                ArrayList<Movie> movies = JSONPacketParser.getMovies(response);
                Movie movie = movies.get(0);
                Log.i("MovieResult", "Data: " + movie.toString());
                Uri uri = Uri.parse(Constants.IMAGE_URL + movie.getPoster_path());
                starMovieIcon.setVisibility(View.VISIBLE);
                latestMovieImage.setImageURI(uri);
                latestMovieName.setText(movie.getTitle());
                String latestMovieDurationText = "" + movie.getVote_average();
                latestMovieDuration.setText(latestMovieDurationText);
                latestMovieType.setText(movie.getGenres());
            }
        });

    }

    class SectionsPagerAdapter extends FragmentPagerAdapter {

        SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            Log.i("FragmentMain", "Position:" + position);
            switch (position) {
                case 0:
                    return new FragmentTopRated();
                case 1:
                    return new FragmentNowPlaying();
                case 2:
                    return new FragmentUpcoming();
                default:
                    Log.e("FragmentMain", "Default case in onTabSelected");

            }
            return new FragmentTopRated();
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}
