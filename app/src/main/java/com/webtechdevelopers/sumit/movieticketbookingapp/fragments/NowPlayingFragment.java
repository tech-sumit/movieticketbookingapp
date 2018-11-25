package com.webtechdevelopers.sumit.movieticketbookingapp.fragments;

import android.os.Bundle;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class NowPlayingFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private String mParam1;
    private RecyclerView nowPlayingMovies;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Movie> movieArrayList;

    public NowPlayingFragment() {
    }

    public static NowPlayingFragment newInstance(String param1) {
        NowPlayingFragment fragment = new NowPlayingFragment();
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
        return inflater.inflate(R.layout.fragment_now_playing, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        nowPlayingMovies=view.findViewById(R.id.now_playing_movies);
        ApiConnector apiConnector=new ApiConnector(view.getContext());
        apiConnector.getNowPlayingMovies(1, new OnApiResultRecived() {
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
                        ((OnFragmentInteractionListener)getActivity()).onFragmentInteractionResult(R.layout.fragment_now_playing,bundle);
                    }
                });
                nowPlayingMovies.setHasFixedSize(true);
                nowPlayingMovies.setAdapter(movieItemRecyclerAdapter);
                LinearLayoutManager linearLayoutManager= new LinearLayoutManager(view.getContext());
                linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
                nowPlayingMovies.setLayoutManager(linearLayoutManager);

            }
        });
    }
}
