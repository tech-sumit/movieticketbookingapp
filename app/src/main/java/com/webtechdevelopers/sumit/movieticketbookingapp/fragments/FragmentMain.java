package com.webtechdevelopers.sumit.movieticketbookingapp.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.webtechdevelopers.sumit.movieticketbookingapp.R;
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.Constants;
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.OnFragmentInteractionListener;
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.entities.Movie;
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.network.ApiConnector;
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.network.JSONPacketParser;
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.network.OnApiResultRecived;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class FragmentMain extends Fragment{
    private SimpleDraweeView latestMovieImage;
    private TextView latestMovieName;
    private TextView latestMovieType;
    private TextView latestMovieDuration;

    public FragmentMain() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Fresco.initialize(container.getContext());
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setHasOptionsMenu(true);

        final CoordinatorLayout mainCoordinatorLayout=view.findViewById(R.id.mainCoordinatorLayout);
        final FrameLayout replacableFrameLayout=view.findViewById(R.id.replacableFrameLayout);

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

        FloatingActionButton menuFab=view.findViewById(R.id.menuFab);
        menuFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(view.getContext());
                View dialogView=View.inflate(view.getContext(),R.layout.layout_navigation_dialog,null);
                builder.setView(dialogView);
                final AlertDialog dialog=builder.create();
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog.show();
                if(dialog.isShowing()){
                    LinearLayout theatre=dialog.findViewById(R.id.nav_theatre);
                    theatre.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.cancel();
                            ((OnFragmentInteractionListener)getActivity()).onFragmentInteractionResult("main_fragment",null);
                        }
                    });
                    LinearLayout orders=dialog.findViewById(R.id.nav_booking);
                    orders.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.cancel();
                            ((OnFragmentInteractionListener)getActivity()).onFragmentInteractionResult("orders",null);
                        }
                    });
                    LinearLayout setting=dialog.findViewById(R.id.nav_settings);
                    setting.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //TODO:  Add settings
                        }
                    });
                    LinearLayout aboutUs=dialog.findViewById(R.id.nav_about_us);
                    aboutUs.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.cancel();
                            mainCoordinatorLayout.setVisibility(View.GONE);
                            replacableFrameLayout.setVisibility(View.VISIBLE);
                            getActivity()
                                    .getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(
                                            R.id.replacableFrameLayout,
                                            new FragmentAboutUs())
                                    .addToBackStack("FragmentAboutUs")
                                    .commit();
                        }
                    });
                    LinearLayout signOut=dialog.findViewById(R.id.nav_signout);
                    signOut.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });
                }

            }
        });
        /*
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ActionBar actionbar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
        */
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.nav_menu,menu);
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Log.i("FragmentMain","Position:"+position);
            switch (position){
                case 0:
                    FragmentTopRated fragmentTopRated = FragmentTopRated.newInstance("");
                    if(fragmentTopRated ==null){
                    }
                    return fragmentTopRated;
                case 1:
                    FragmentNowPlaying fragmentNowPlaying = FragmentNowPlaying.newInstance("");
                    if(fragmentNowPlaying ==null){
                    }
                    return fragmentNowPlaying;
                case 2:
                    FragmentUpcoming fragmentUpcoming = FragmentUpcoming.newInstance("");
                    if(fragmentUpcoming ==null){
                    }
                    return fragmentUpcoming;
                default:
                    Log.e("FragmentMain","Default case in onTabSelected");

            }
            return FragmentTopRated.newInstance("");
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}
