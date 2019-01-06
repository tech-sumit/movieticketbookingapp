package com.webtechdevelopers.sumit.movieticketbookingapp.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
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
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.network.OnApiResultRecived;

import java.util.ArrayList;

public class FragmentMain extends Fragment {
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
        if(container!=null){
            Fresco.initialize(container.getContext());
        }else {
            getActivity().finish();
        }
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //getActivity().getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

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
                String latestMovieDurationText=""+movie.getVote_average();
                latestMovieDuration.setText(latestMovieDurationText);
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
                    SimpleDraweeView profilePic=dialog.findViewById(R.id.profile_pic);
                    SharedPreferences sharedPreferences=view.getContext().getSharedPreferences("login_pref",Context.MODE_PRIVATE);
                    String profilePicURL=sharedPreferences.getString(Constants.PROFILE_PIC,"");
                    profilePic.setImageURI(Uri.parse(profilePicURL));
                    TextView profileName=dialog.findViewById(R.id.profile_name);
                    profileName.setText(sharedPreferences.getString(Constants.NAME,""));
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
                            getActivity()
                                    .getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(
                                            R.id.replaceableConstraintLayout,
                                            new FragmentOrders())
                                    .addToBackStack("FragmentOrders")
                                    .commit();
                        }
                    });
                    LinearLayout aboutUs=dialog.findViewById(R.id.nav_about_us);
                    aboutUs.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.cancel();
                            getActivity()
                                    .getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(
                                            R.id.replaceableConstraintLayout,
                                            new FragmentAboutUs())
                                    .addToBackStack("FragmentAboutUs")
                                    .commit();
                        }
                    });
                    LinearLayout signOut=dialog.findViewById(R.id.nav_signout);
                    signOut.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            GoogleSignInOptions googleSignInOptions= new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                                    .requestEmail()
                                    .requestProfile()
                                    .build();
                            GoogleSignInClient googleSignInClient= GoogleSignIn.getClient(view.getContext(), googleSignInOptions);
                            googleSignInClient.signOut()
                                    .addOnCompleteListener(getActivity(), new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            dialog.cancel();
                                            SharedPreferences sharedPreferences1=view.getContext().getSharedPreferences(Constants.LOGIN_PREF,Context.MODE_PRIVATE);
                                            SharedPreferences.Editor editor=sharedPreferences1.edit();
                                            editor.clear();
                                            editor.apply();
                                            ((OnFragmentInteractionListener)getActivity()).onFragmentInteractionResult("login_fragment",null);
                                        }
                                    });
                        }
                    });
                }

            }
        });
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
                    return new FragmentTopRated();
                case 1:
                    return new FragmentNowPlaying();
                case 2:
                    return new FragmentUpcoming();
                default:
                    Log.e("FragmentMain","Default case in onTabSelected");

            }
            return new FragmentTopRated();
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}
