package com.webtechdevelopers.sumit.movieticketbookingapp.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.webtechdevelopers.sumit.movieticketbookingapp.framework.OnFragmentInteractionListener;
import com.webtechdevelopers.sumit.movieticketbookingapp.R;
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.entities.Movie;
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.MovieItemRecyclerAdapter;
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.OnItemSelectedListener;
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.network.ApiConnector;
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.network.JSONPacketParser;
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.network.OnApiResultRecived;

import java.util.ArrayList;


public class FragmentUpcoming extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private RecyclerView upcomingMovies;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Movie> movieArrayList;

    private String mParam1;

    public FragmentUpcoming() {
    }

    public static FragmentUpcoming newInstance(String param1) {
        FragmentUpcoming fragment = new FragmentUpcoming();
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
        return inflater.inflate(R.layout.fragment_upcoming, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        upcomingMovies =view.findViewById(R.id.upcoming_movies);
        ApiConnector apiConnector=new ApiConnector(view.getContext());
        apiConnector.getUpcomingMovies(1, new OnApiResultRecived() {
            @Override
            public void onResult(String response) {
                Log.i("Response Data","Response:\n"+response);
                movieArrayList=JSONPacketParser.getMovies(response);

                final MovieItemRecyclerAdapter movieItemRecyclerAdapter=new MovieItemRecyclerAdapter(movieArrayList, new OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(Movie movie) {
                        //TODO: display movie details and booking screen using movie item.
                        Bundle bundle=new Bundle();
                        bundle.putSerializable("movie",movie);
                        bundle.putString("type","upcoming");
                        ((OnFragmentInteractionListener)getActivity()).onFragmentInteractionResult("upcoming",bundle);
                    }
                });
                upcomingMovies.setHasFixedSize(true);
                upcomingMovies.setAdapter(movieItemRecyclerAdapter);
                LinearLayoutManager linearLayoutManager= new LinearLayoutManager(view.getContext());
                linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
                upcomingMovies.setLayoutManager(linearLayoutManager);
            }
        });
    }
}