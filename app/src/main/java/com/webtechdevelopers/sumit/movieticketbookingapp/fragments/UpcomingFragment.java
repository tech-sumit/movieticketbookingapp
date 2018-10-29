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

public class UpcomingFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private RecyclerView upcomingMovies;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private String mParam1;

    public UpcomingFragment() {
    }

    public static UpcomingFragment newInstance(String param1) {
        UpcomingFragment fragment = new UpcomingFragment();
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        upcomingMovies =view.findViewById(R.id.upcoming_movies);
        //GET Data from server
        MovieItemRecyclerAdapter movieItemRecyclerAdapter=new MovieItemRecyclerAdapter(null, new OnItemSelectedListener() {
            @Override
            public void onItemSelected(Movie movie) {
                //TODO: display movie details and booking screen using movie item.
            }
        });
        upcomingMovies.setHasFixedSize(true);
        upcomingMovies.setAdapter(movieItemRecyclerAdapter);
        // Implement listener upcomingMovies
    }
}
