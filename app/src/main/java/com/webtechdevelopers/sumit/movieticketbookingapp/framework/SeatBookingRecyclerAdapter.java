package com.webtechdevelopers.sumit.movieticketbookingapp.framework;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.webtechdevelopers.sumit.movieticketbookingapp.R;
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.entities.Seat;

import java.text.MessageFormat;
import java.util.ArrayList;


public class SeatBookingRecyclerAdapter extends RecyclerView.Adapter<SeatBookingRecyclerAdapter.SeatHolder> {

    private final ArrayList<Seat> seatArrayList;
    private final int seatMaxSelectable;
    private int seatSelectedCount = 0;
    private final OnSeatClickActionListener onSeatClickActionListener;
    private Seat seat;
    public SeatBookingRecyclerAdapter(ArrayList<Seat> seatArrayList, int setMaxSelectable, OnSeatClickActionListener onSeatClickActionListener){
        this.seatArrayList =seatArrayList;
        this.seatMaxSelectable=setMaxSelectable;
        this.onSeatClickActionListener =onSeatClickActionListener;
    }

    @NonNull
    @Override
    public SeatHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_seat,viewGroup,false);
        return new SeatHolder(view,seatMaxSelectable);
    }

    @Override
    public void onBindViewHolder(@NonNull SeatHolder seatHolder, int i) {
        seat= seatArrayList.get(i);

        if(seat.getVisibility()==View.VISIBLE){
            seatHolder.seatLayout.setVisibility(seat.getVisibility());
            seatHolder.bind(seat, onSeatClickActionListener);
            seat.setSeat_no(""+seat.getSeat_no());
            seatHolder.seatNumber.setText(seat.getSeat_no());
            if(seat.isBooked()){
                seatHolder.seatImage.setBackgroundColor(Color.GRAY);
            }
            Log.i("MovieDetails","\nData: "+seat.toString());
        }else {
            seatHolder.seatLayout.setVisibility(seat.getVisibility());
        }
    }

    @Override
    public int getItemCount() {
        return seatArrayList.size();
    }

    @Override
    public void onViewRecycled(@NonNull SeatHolder seatHolder) {
        super.onViewRecycled(seatHolder);
        if (seat.getVisibility() == View.VISIBLE) {
            seatHolder.seatLayout.setVisibility(seat.getVisibility());
            seatHolder.bind(seat, onSeatClickActionListener);
            seat.setSeat_no(""+seat.getSeat_no());
            seatHolder.seatNumber.setText(seat.getSeat_no());
            if (seat.isBooked()) {
                seatHolder.seatImage.setBackgroundColor(Color.GRAY);
            }
            Log.i("MovieDetails", "\nData: " + seat.toString());
        } else {
            seatHolder.seatLayout.setVisibility(seat.getVisibility());
        }
    }
    class SeatHolder extends RecyclerView.ViewHolder {
        final ImageView seatImage;
        final TextView seatNumber;
        private final int seatMaxSelectable;
        @NonNull
        final View itemView;
        final RelativeLayout seatLayout;
        private boolean isSelected = false;

        SeatHolder(@NonNull View itemView, int setMaxSelectable) {
            super(itemView);
            this.itemView = itemView;
            this.seatMaxSelectable = setMaxSelectable;
            seatImage = itemView.findViewById(R.id.seatImage);
            seatNumber = itemView.findViewById(R.id.seatNumber);
            seatLayout = itemView.findViewById(R.id.seatLayout);
        }

        void bind(@NonNull final Seat seat, @NonNull final OnSeatClickActionListener onSeatClickActionListener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!seat.isBooked()) {
                        if (!isSelected) {
                            if (seatSelectedCount < seatMaxSelectable) {
                                isSelected = true;
                                seatSelectedCount++;
                                seatImage.setBackgroundColor(Color.GREEN);
                                Log.i("OnSeatClickAction", "isBookedL:" + seat.isBooked()
                                        + "\nisSelected:" + isSelected
                                        + "\nseatMaxSelectable:" + seatMaxSelectable
                                        + "\nseatSelectedCount:" + seatSelectedCount);
                                onSeatClickActionListener.onSeatSelected(seat);
                            } else {
                                Toast.makeText(itemView.getContext(),
                                        MessageFormat
                                                .format(
                                                        "Only {0} seats can be booked at a time",
                                                        seatMaxSelectable)
                                        , Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            isSelected = false;
                            seatSelectedCount--;
                            seatImage.setBackgroundColor(Color.WHITE);
                            Log.i("OnSeatClickAction", "isBookedL:" + seat.isBooked()
                                    + "\nisSelected:" + isSelected
                                    + "\nseatMaxSelectable:" + seatMaxSelectable
                                    + "\nseatSelectedCount:" + seatSelectedCount);
                            onSeatClickActionListener.onSeatDeselected(seat);
                        }
                    }
                }
            });
        }
    }
}
