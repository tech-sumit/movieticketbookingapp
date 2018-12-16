package com.webtechdevelopers.sumit.movieticketbookingapp.framework;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.webtechdevelopers.sumit.movieticketbookingapp.R;
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.entities.Seat;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SeatBookingRecyclerAdapter extends RecyclerView.Adapter<SeatBookingRecyclerAdapter.SeatHolder> {

    private ArrayList<Seat> seatArrayList;
    private int seatMaxSelectable=0;
    private int columnCount=0;
    private int seatSelectedCount = 0;
    private int columnIndex=0;
    private OnSeatClickActionListener onSeatClickActionListener;
    private Seat seat;
    public SeatBookingRecyclerAdapter(ArrayList<Seat> seatArrayList,int setMaxSelectable,int columnCount, OnSeatClickActionListener onSeatClickActionListener){
        this.seatArrayList =seatArrayList;
        this.seatMaxSelectable=setMaxSelectable;
        this.columnCount=columnCount;
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

        if(seat.getVisiblity()==View.VISIBLE){
            seatHolder.seatLayout.setVisibility(seat.getVisiblity());
            seatHolder.bind(seat, onSeatClickActionListener);
            seat.setSeat_no(""+seat.getSeat_no());
            seatHolder.seatNumber.setText(seat.getSeat_no());
            if(seat.isBooked()){
                seatHolder.seatImage.setBackgroundColor(Color.GRAY);
            }
            Log.i("MovieDetails","\nData: "+seat.toString());
        }else {
            seatHolder.seatLayout.setVisibility(seat.getVisiblity());
        }

        /*
        if(!(seat.getColumn_no() ==0)){
        }else{
            seatHolder.seatLayout.setVisibility(View.VISIBLE);
            seatHolder.seatImage.setVisibility(View.INVISIBLE);
            seatHolder.seatNumber.setText(""+((char)(seat.getRow_no()+65)));
        }
        */
    }

    @Override
    public int getItemCount() {
        return seatArrayList.size();
    }

    @Override
    public void onViewRecycled(@NonNull SeatHolder seatHolder) {
        super.onViewRecycled(seatHolder);
        if (seat.getVisiblity() == View.VISIBLE) {
            seatHolder.seatLayout.setVisibility(seat.getVisiblity());
            seatHolder.bind(seat, onSeatClickActionListener);
            seat.setSeat_no(""+seat.getSeat_no());
            seatHolder.seatNumber.setText(seat.getSeat_no());
            if (seat.isBooked()) {
                seatHolder.seatImage.setBackgroundColor(Color.GRAY);
            }
            Log.i("MovieDetails", "\nData: " + seat.toString());
        } else {
            seatHolder.seatLayout.setVisibility(seat.getVisiblity());
        }

        /*
        if (seat != null) {
            if (!(seat.getColumn_no() == 0)) {
            } else {
                seatHolder.seatLayout.setVisibility(View.VISIBLE);
                seatHolder.seatImage.setVisibility(View.INVISIBLE);
                seatHolder.seatNumber.setText(""+((char)(seat.getRow_no()+65)));
            }
        }
        */
    }
    class SeatHolder extends RecyclerView.ViewHolder {
        public ImageView seatImage;
        public TextView seatNumber;
        private int seatMaxSelectable;
        public View itemView;
        public RelativeLayout seatLayout;
        private boolean isSelected = false;

        public SeatHolder(@NonNull View itemView, int setMaxSelectable) {
            super(itemView);
            this.itemView = itemView;
            this.seatMaxSelectable = setMaxSelectable;
            seatImage = itemView.findViewById(R.id.seatImage);
            seatNumber = itemView.findViewById(R.id.seatNumber);
            seatLayout = itemView.findViewById(R.id.seatLayout);
        }

        public void bind(final Seat seat, final OnSeatClickActionListener onSeatClickActionListener) {
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
                                Toast.makeText(itemView.getContext(), "Only " + seatMaxSelectable + " seats can be booked at a time", Toast.LENGTH_SHORT).show();
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
