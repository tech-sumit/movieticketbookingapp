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

//TODO: Create 3 fragments for the menu content
public class TopRatedFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private RecyclerView topRatedMovies;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private String mParam1;

    public TopRatedFragment() {
    }

    public static TopRatedFragment newInstance(String param1) {
        TopRatedFragment fragment = new TopRatedFragment();
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_top_rated, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        topRatedMovies=view.findViewById(R.id.top_rated_movies);
        //GET Data from server
        MovieItemRecyclerAdapter movieItemRecyclerAdapter=new MovieItemRecyclerAdapter(null, new OnItemSelectedListener() {
            @Override
            public void onItemSelected(Movie movie) {
                //TODO: display movie details and booking screen using movie item.
            }
        });
        topRatedMovies.setHasFixedSize(true);
        topRatedMovies.setAdapter(movieItemRecyclerAdapter);
        // Implement listener topRatedMovies
    }
}
