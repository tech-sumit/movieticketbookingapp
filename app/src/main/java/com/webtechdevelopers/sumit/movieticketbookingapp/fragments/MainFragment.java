package com.webtechdevelopers.sumit.movieticketbookingapp.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.webtechdevelopers.sumit.movieticketbookingapp.OnFragmentInteractionListener;
import com.webtechdevelopers.sumit.movieticketbookingapp.R;

public class MainFragment extends Fragment implements OnFragmentInteractionListener {
    private static final String ARG_PARAM1 = "param1";
    private TabLayout tabLayout;
    private ViewPager mViewPager;
    private OnFragmentInteractionListener onFragmentInteractionListener=null;

    private String mParam1;


    public MainFragment() {
    }

    public static MainFragment newInstance(String param1,OnFragmentInteractionListener onFragmentInteractionListener) {
        MainFragment fragment = new MainFragment();
        fragment.onFragmentInteractionListener=onFragmentInteractionListener;
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
                    return TopRatedFragment.newInstance("",MainFragment.this);
                case 2:
                    return NowPlayingFragment.newInstance("",MainFragment.this);
                case 3:
                    return UpcomingFragment.newInstance("",MainFragment.this);
                default:
                    Log.e("MainFragment","Default case in onTabSelected");

            }
            return TopRatedFragment.newInstance("",MainFragment.this);
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}
