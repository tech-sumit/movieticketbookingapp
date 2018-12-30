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
import android.widget.Toast;

import com.webtechdevelopers.sumit.movieticketbookingapp.R;
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.OnShowSelectedListener;
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.PersistantDataStorage;
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.TicketDetailsRecyclerAdapter;
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.entities.Show;

import java.util.ArrayList;

public class FragmentOrders extends Fragment {
    public FragmentOrders() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_orders, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        PersistantDataStorage persistantDataStorage=new PersistantDataStorage(view.getContext());
        ArrayList<Show> shows=persistantDataStorage.getShows();
        if(shows.size()>0){
            TicketDetailsRecyclerAdapter ticketDetailsRecyclerAdapter=new TicketDetailsRecyclerAdapter(shows, new OnShowSelectedListener() {
                @Override
                public void onShowSelected(Show show) {
                    Log.i("OnShowSelectedListener","Show Selected: \n"+show.getSerializable());
                }
            });
            RecyclerView ticketBookingRecyclerView=view.findViewById(R.id.ticketBookingRecyclerView);
            ticketBookingRecyclerView.setHasFixedSize(true);
            ticketBookingRecyclerView.setAdapter(ticketDetailsRecyclerAdapter);
            LinearLayoutManager linearLayoutManager= new LinearLayoutManager(view.getContext());
            linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
            ticketBookingRecyclerView.setLayoutManager(linearLayoutManager);
        }else{
            Toast.makeText(view.getContext(),"No tickets booked till date", Toast.LENGTH_SHORT).show();
        }
    }
}
