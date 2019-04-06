package com.webtechdevelopers.sumit.movieticketbookingapp.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.webtechdevelopers.sumit.movieticketbookingapp.R;
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.OnShowSelectedListener;
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.PersistentDataStorage;
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.TicketDetailsRecyclerAdapter;
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.entities.Show;

import java.util.ArrayList;

public class DialogOrders extends Dialog {
    private final View view;

    public DialogOrders(@NonNull Context context) {
        super(context,R.style.AppTheme_NoActionBar_Dark);
        view=View.inflate(context,R.layout.layout_orders, null);
        setContentView(view);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FloatingActionButton backButton = findViewById(R.id.backFab);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });
        PersistentDataStorage persistentDataStorage =new PersistentDataStorage(view.getContext());
        ArrayList<Show> shows= persistentDataStorage.getShows();
        if(shows.size()>0){
            TicketDetailsRecyclerAdapter ticketDetailsRecyclerAdapter=new TicketDetailsRecyclerAdapter(shows, new OnShowSelectedListener() {
                @Override
                public void onShowSelected(@NonNull Show show) {
                    Log.i("OnShowSelectedListener","Show Selected: \n"+show.getSerializable());
                    Bundle bundle=new Bundle();
                    bundle.putSerializable("show",show);
                    bundle.putString("payment_id",show.getPaymentData().getPaymentId());
                    bundle.putString("name",show.getMovie().getOriginal_title());
                    bundle.putString("venue",show.getVenue());
                    bundle.putString("time",show.getTime());
                    DialogTicketDetails dialogTicketDetails =new DialogTicketDetails(view.getContext(),bundle);
                    dialogTicketDetails.show();
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
