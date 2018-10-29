package com.webtechdevelopers.sumit.movieticketbookingapp.fragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.webtechdevelopers.sumit.movieticketbookingapp.R;
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.Movie;
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.MovieItemRecyclerAdapter;
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.OnItemSelectedListener;

public class NowPlayingFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private String mParam1;
    private RecyclerView nowPlayingMovies;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        nowPlayingMovies=view.findViewById(R.id.now_playing_movies);
        //GET Data from server
        MovieItemRecyclerAdapter movieItemRecyclerAdapter=new MovieItemRecyclerAdapter(null, new OnItemSelectedListener() {
            @Override
            public void onItemSelected(Movie movie) {
                //TODO: display movie details and booking screen using movie item.
            }
        });
        nowPlayingMovies.setHasFixedSize(true);
        nowPlayingMovies.setAdapter(movieItemRecyclerAdapter);
        // Implement listener topRatedMovies
    }
}
