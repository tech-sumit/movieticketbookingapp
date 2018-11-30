package com.webtechdevelopers.sumit.movieticketbookingapp.framework;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.webtechdevelopers.sumit.movieticketbookingapp.R;
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.entities.Seat;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SeatBookingRecyclerAdapter extends RecyclerView.Adapter<SeatBookingRecyclerAdapter.SeatHolder> {

    private ArrayList<Seat> seatArrayList;
    private OnSeatClickActionListener onSeatClickActionListener;
    public SeatBookingRecyclerAdapter(ArrayList<Seat> seatArrayList, OnSeatClickActionListener onSeatClickActionListener){
        this.seatArrayList =seatArrayList;
        this.onSeatClickActionListener =onSeatClickActionListener;
    }

    @NonNull
    @Override
    public SeatHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Fresco.initialize(viewGroup.getContext());
        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_seat,viewGroup,false);
        return new SeatHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SeatHolder seatHolder, int i) {
        Seat seat= seatArrayList.get(i);
        seatHolder.bind(seat, onSeatClickActionListener);
        seatHolder.seatNumber.setText(""+ seatArrayList.get(i).getColumn_no());
        Log.i("MovieDetails","\nData: "+seat.toString());
    }

    @Override
    public int getItemCount() {
        return seatArrayList.size();
    }

    public static class SeatHolder extends RecyclerView.ViewHolder {
        public ImageView seatImage;
        public TextView seatNumber;
        public View itemView;
        private boolean isSelected=false;

        public SeatHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView=itemView;
            seatImage=itemView.findViewById(R.id.movieImage);
            seatNumber=itemView.findViewById(R.id.movieDuration);
        }

        public void bind(final Seat seat, final OnSeatClickActionListener onSeatClickActionListener){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!isSelected){
                        onSeatClickActionListener.onSeatSelected(seat);
                    }else {
                        onSeatClickActionListener.onSeatDeselected(seat);
                    }
                }
            });
        }
    }
}
