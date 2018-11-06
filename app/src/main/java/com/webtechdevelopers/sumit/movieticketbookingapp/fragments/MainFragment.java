package com.webtechdevelopers.sumit.movieticketbookingapp.fragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.webtechdevelopers.sumit.movieticketbookingapp.OnFragmentInteractionListener;
import com.webtechdevelopers.sumit.movieticketbookingapp.R;
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.Movie;

import java.util.ArrayList;

public class MainFragment extends Fragment implements OnFragmentInteractionListener {
    private static final String ARG_PARAM1 = "param1";
    private TabLayout tabLayout;
    private ViewPager mViewPager;
    private ViewPager mCardPager;

    private String mParam1;
    private NowPlayingFragment nowPlayingFragment;
    private TopRatedFragment topRatedFragment;
    private UpcomingFragment upcomingFragment;


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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tabLayout=view.findViewById(R.id.tabLayout);

        mCardPager= view.findViewById(R.id.cardPager);
        SectionsPagerAdapter mSectionsCardPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager());
        mCardPager.setAdapter(mSectionsCardPagerAdapter );

        mViewPager = view.findViewById(R.id.viewPager);
        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager());
        mViewPager.setAdapter(mSectionsPagerAdapter);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
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
                    topRatedFragment=TopRatedFragment.newInstance("");
                    if(topRatedFragment==null){
                    }
                    return topRatedFragment;
                case 2:
                    nowPlayingFragment=NowPlayingFragment.newInstance("");
                    if(nowPlayingFragment==null){
                    }
                    return nowPlayingFragment;
                case 3:
                    upcomingFragment=UpcomingFragment.newInstance("");
                    if(upcomingFragment==null){
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

    public class CardPagerAdapter extends FragmentPagerAdapter {
        //TODO: Show the current active movie in the card layout
        private ArrayList<Movie> movies;
        public CardPagerAdapter(FragmentManager fm, ArrayList<Movie> movies) {
            super(fm);
            this.movies=movies;
        }

        @Override
        public Fragment getItem(int position) {
            Log.i("MainFragment","Position:"+position);
            return TopRatedFragment.newInstance("");
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}
