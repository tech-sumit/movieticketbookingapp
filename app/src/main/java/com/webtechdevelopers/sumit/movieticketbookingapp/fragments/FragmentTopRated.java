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

import com.webtechdevelopers.sumit.movieticketbookingapp.R;
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.MovieItemRecyclerAdapter;
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.OnFragmentInteractionListener;
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.OnItemSelectedListener;
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.entities.Movie;
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.network.ApiConnector;
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.network.JSONPacketParser;
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.network.OnAdiResultReceived;

import java.util.ArrayList;

//TODO: Create 3 fragments for the menu content
public class FragmentTopRated extends Fragment {
    private RecyclerView topRatedMovies;
    private ArrayList<Movie> movieArrayList;

    public FragmentTopRated() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_top_rated, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        topRatedMovies=view.findViewById(R.id.top_rated_movies);

        ApiConnector apiConnector=new ApiConnector(view.getContext());
        apiConnector.getTopRatedMovies(1, new OnAdiResultReceived() {
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
                        if(getActivity()!=null)
                            ((OnFragmentInteractionListener)getActivity()).onFragmentInteractionResult("movie_details",bundle);
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
