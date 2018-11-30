package com.webtechdevelopers.sumit.movieticketbookingapp.fragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import java.io.Serializable;
import java.util.ArrayList;

//TODO: Create 3 fragments for the menu content
public class TopRatedFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private RecyclerView topRatedMovies;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Movie> movieArrayList;

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
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        topRatedMovies=view.findViewById(R.id.top_rated_movies);

        ApiConnector apiConnector=new ApiConnector(view.getContext());
        apiConnector.getTopRatedMovies(1, new OnApiResultRecived() {
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
                        bundle.putString("type","top_rated");
                        ((OnFragmentInteractionListener)getActivity()).onFragmentInteractionResult("top_rated",bundle);
                    }
                });
                topRatedMovies.setHasFixedSize(true);
                topRatedMovies.setAdapter(movieItemRecyclerAdapter);
                LinearLayoutManager linearLayoutManager= new LinearLayoutManager(view.getContext());
                linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
                topRatedMovies.setLayoutManager(linearLayoutManager);
            }
        });
    }
}
