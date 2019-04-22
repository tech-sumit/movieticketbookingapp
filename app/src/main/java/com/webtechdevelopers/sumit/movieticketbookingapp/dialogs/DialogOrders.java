package com.webtechdevelopers.sumit.movieticketbookingapp.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.webtechdevelopers.sumit.movieticketbookingapp.R;
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.Constants;
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.OnShowSelectedListener;
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.PersistentDataStorage;
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.SerializationUtils;
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.TicketDetailsRecyclerAdapter;
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.entities.Show;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

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

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference(Constants.BOOKINGS);
        String id=getContext().getSharedPreferences(Constants.LOGIN_PREF,MODE_PRIVATE).getString(Constants.ID,"");
        assert id != null;
        if(id.equals("")){
            Log.i("DialogOrders","Empty Email at onPaymentSuccess id:"+id);
            return;
        }

        Query bookings= databaseReference.child(id);
        bookings.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.d("DialogOrders", "onChildAdded:" + dataSnapshot.getKey());
                ArrayList<Show> shows=new ArrayList<>();
                for(int i=0;i<dataSnapshot.getChildrenCount();i++){
                    shows.add((Show) SerializationUtils.deserialize((String) dataSnapshot.getValue()));
                }
                Log.i("DialogOrders","onChildAdded shows: "+shows.toString());
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                ArrayList<Show> shows=new ArrayList<>();
                for(int i=0;i<dataSnapshot.getChildrenCount();i++){
                    shows.add(dataSnapshot.getValue(Show.class));
                }
                Log.i("DialogOrders","onChildChanged shows: "+shows.toString());
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Show> shows=new ArrayList<>();
                for(int i=0;i<dataSnapshot.getChildrenCount();i++){
                    shows.add(dataSnapshot.getValue(Show.class));
                }
                Log.i("DialogOrders","onChildRemoved shows: "+shows.toString());
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                ArrayList<Show> shows=new ArrayList<>();
                for(int i=0;i<dataSnapshot.getChildrenCount();i++){
                    shows.add(dataSnapshot.getValue(Show.class));
                }
                Log.i("DialogOrders","onChildMoved shows: "+shows.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("DialogOrders", "onCancelled", databaseError.toException());
                Toast.makeText(DialogOrders.this.getContext(), "Failed to load comments.",
                        Toast.LENGTH_SHORT).show();
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
