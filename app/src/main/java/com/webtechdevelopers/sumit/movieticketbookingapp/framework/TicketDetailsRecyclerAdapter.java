package com.webtechdevelopers.sumit.movieticketbookingapp.framework;

import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.webtechdevelopers.sumit.movieticketbookingapp.R;
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.entities.Movie;
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.entities.Show;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TicketDetailsRecyclerAdapter extends RecyclerView.Adapter<TicketDetailsRecyclerAdapter.TicketHolder> {

    private ArrayList<Show> shows;
    private Show show;
    private OnShowSelectedListener onShowSelectedListener;
    public TicketDetailsRecyclerAdapter(ArrayList<Show> shows,OnShowSelectedListener onShowSelectedListener){
        this.shows=shows;
        this.onShowSelectedListener=onShowSelectedListener;
    }

    @NonNull
    @Override
    public TicketHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Fresco.initialize(parent.getContext());
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_ticket_item,parent,false);
        return new TicketHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TicketHolder holder, int position) {
        show=shows.get(position);
        Movie movie=show.getMovie();
        holder.bind(show,onShowSelectedListener);
        holder.ticketMovieImage.setImageURI(Uri.parse(Constants.IMAGE_URL+movie.getPoster_path()));
        holder.ticketPaymentID.setText(show.getPaymentData().getOrderId());
        holder.ticketMovieName.setText(movie.getOriginal_title());
        holder.ticketSeats.setText(""+show.getSeatCount());
        holder.ticketMoneyPaid.setText(""+(show.getSeatCount()*show.getSeats().get(0).getPrice()));
        Log.i("MovieDetails","\nData: "+movie.toString());
    }

    @Override
    public void onViewRecycled(@NonNull TicketHolder holder) {
        super.onViewRecycled(holder);
        Movie movie=show.getMovie();
        holder.bind(show,onShowSelectedListener);
        holder.ticketMovieImage.setImageURI(Uri.parse(Constants.IMAGE_URL+movie.getPoster_path()));
        holder.ticketPaymentID.setText(show.getPaymentData().getOrderId());
        holder.ticketMovieName.setText(movie.getOriginal_title());
        holder.ticketSeats.setText(""+show.getSeatCount());
        holder.ticketMoneyPaid.setText(""+(show.getSeatCount()*show.getSeats().get(0).getPrice()));
        Log.i("MovieDetails","\nData: "+movie.toString());
    }

    @Override
    public int getItemCount() {
        return shows.size();
    }

    public class TicketHolder extends RecyclerView.ViewHolder{
        public SimpleDraweeView ticketMovieImage;
        public TextView ticketPaymentID;
        public TextView ticketMovieName;
        public TextView ticketSeats;
        public TextView ticketMoneyPaid;
        public TicketHolder(@NonNull View itemView) {
            super(itemView);
            ticketMovieImage=itemView.findViewById(R.id.ticketMovieImage);
            ticketPaymentID=itemView.findViewById(R.id.ticketPaymentID);
            ticketMovieName=itemView.findViewById(R.id.ticketMovieName);
            ticketSeats=itemView.findViewById(R.id.ticketSeats);
            ticketMoneyPaid=itemView.findViewById(R.id.ticketMoneyPaid);
        }
        public void bind(final Show show, final OnShowSelectedListener onShowSelectedListener){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onShowSelectedListener.onShowSelected(show);
                }
            });
        }
    }
}