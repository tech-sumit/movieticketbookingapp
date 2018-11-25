package com.webtechdevelopers.sumit.movieticketbookingapp.fragments;

import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.webtechdevelopers.sumit.movieticketbookingapp.framework.OnFragmentInteractionListener;
import com.webtechdevelopers.sumit.movieticketbookingapp.R;
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.Constants;
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.entities.Movie;
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.network.ApiConnector;
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.network.JSONPacketParser;
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.network.OnApiResultRecived;

import java.util.ArrayList;

public class MainFragment extends Fragment implements OnFragmentInteractionListener {
    private static final String ARG_PARAM1 = "param1";
    private String mParam1;
    private SimpleDraweeView latestMovieImage;
    private TextView latestMovieName;
    private TextView latestMovieType;
    private TextView latestMovieDuration;

    public MainFragment() {
    }

    public static MainFragment newInstance(String param1) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Fresco.initialize(container.getContext());
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TabLayout tabLayout = view.findViewById(R.id.tabLayout);

        ViewPager mViewPager = view.findViewById(R.id.viewPager);
        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager());
        mViewPager.setAdapter(mSectionsPagerAdapter);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        latestMovieImage=view.findViewById(R.id.latestMovieImage);
        latestMovieName=view.findViewById(R.id.latestMovieName);
        latestMovieType=view.findViewById(R.id.latestMovieType);
        latestMovieDuration=view.findViewById(R.id.latestMovieDuration);
        ApiConnector apiConnector=new ApiConnector(view.getContext());
        apiConnector.getPopularMovies(1,new OnApiResultRecived() {
            @Override
            public void onResult(String response) {
                Log.i("Response Data","Response:\n"+response);
                ArrayList<Movie> movies=JSONPacketParser.getMovies(response);
                Movie movie=movies.get(0);
                Log.i("Movie","Data: "+movie.toString());
                Uri uri = Uri.parse(Constants.IMAGE_URL+movie.getPoster_path());
                latestMovieImage.setImageURI(uri);
                latestMovieName.setText(movie.getTitle());
                latestMovieDuration.setText(""+movie.getVote_average());
                latestMovieType.setText(movie.getGenres());
            }
        });
    }

    @Override
    public void onFragmentInteractionResult(int fragmentId, Bundle bundle) {
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Log.i("MainFragment","Position:"+position);
            switch (position){
                case 1:
                    TopRatedFragment topRatedFragment = TopRatedFragment.newInstance("");
                    if(topRatedFragment ==null){
                    }
                    return topRatedFragment;
                case 2:
                    NowPlayingFragment nowPlayingFragment = NowPlayingFragment.newInstance("");
                    if(nowPlayingFragment ==null){
                    }
                    return nowPlayingFragment;
                case 3:
                    UpcomingFragment upcomingFragment = UpcomingFragment.newInstance("");
                    if(upcomingFragment ==null){
                    }
                    return upcomingFragment;
                default:
                    Log.e("MainFragment","Default case in onTabSelected");

            }
            return TopRatedFragment.newInstance("");
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}
