package com.webtechdevelopers.sumit.movieticketbookingapp.framework;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.postprocessors.IterativeBoxBlurPostProcessor;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.webtechdevelopers.sumit.movieticketbookingapp.R;
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.entities.Movie;
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.entities.Seat;
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.entities.Show;

import java.util.ArrayList;

@SuppressWarnings("ALL")
public class TicketDetailsRecyclerAdapter extends RecyclerView.Adapter<TicketDetailsRecyclerAdapter.TicketHolder> {

    private final ArrayList<Show> shows;
    private Show show;
    private final OnShowSelectedListener onShowSelectedListener;
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
        Uri backgroundUri = Uri.parse(Constants.IMAGE_URL+movie.getBackdrop_path());
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(backgroundUri)
                .setPostprocessor(new IterativeBoxBlurPostProcessor(20))
                .build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(holder.ticketBackground.getController())
                .build();
        holder.ticketBackground.setController(controller);
        if(show.getPaymentData().getOrderId()!=null){
            holder.ticketPaymentID.setText(show.getPaymentData().getOrderId());
        }
        holder.ticketMovieName.setText(movie.getOriginal_title());
        ArrayList<Seat> seats=show.getSeats();
        StringBuilder bookedSeats= new StringBuilder();
        for(Seat seat: seats){
            bookedSeats.append(seat.getSeat_no()).append(" ");
        }
        String ticketSeatsText="Seats: "+bookedSeats;
        holder.ticketSeats.setText(ticketSeatsText);
        String ticketMoneyPaidText=""+(show.getSeatCount()*show.getSeats().get(0).getPrice()+" INR");
        holder.ticketMoneyPaid.setText(ticketMoneyPaidText);
        Log.i("MovieDetails","\nData: "+movie.toString());
    }

    @Override
    public void onViewRecycled(@NonNull TicketHolder holder) {
        super.onViewRecycled(holder);
        Movie movie=show.getMovie();
        holder.bind(show,onShowSelectedListener);
        holder.ticketMovieImage.setImageURI(Uri.parse(Constants.IMAGE_URL+movie.getPoster_path()));
        Uri backgroundUri = Uri.parse(Constants.IMAGE_URL+movie.getBackdrop_path());
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(backgroundUri)
                .setPostprocessor(new IterativeBoxBlurPostProcessor(20))
                .build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(holder.ticketBackground.getController())
                .build();
        holder.ticketBackground.setController(controller);
        holder.ticketPaymentID.setText(show.getPaymentData().getOrderId());
        holder.ticketMovieName.setText(movie.getOriginal_title());
        ArrayList<Seat> seats=show.getSeats();
        StringBuilder bookedSeats= new StringBuilder();
        for(Seat seat: seats){
            bookedSeats.append(seat.getSeat_no()).append(" ");
        }
        String ticketSeatsText="Seats: "+bookedSeats;
        holder.ticketSeats.setText(ticketSeatsText);
        String ticketMoneyPaidText=""+(show.getSeatCount()*show.getSeats().get(0).getPrice()+" INR");
        holder.ticketMoneyPaid.setText(ticketMoneyPaidText);
        Log.i("MovieDetails","\nData: "+movie.toString());
    }

    @Override
    public int getItemCount() {
        return shows.size();
    }

    class TicketHolder extends RecyclerView.ViewHolder{
        final SimpleDraweeView ticketMovieImage;
        final SimpleDraweeView ticketBackground;
        final TextView ticketPaymentID;
        final TextView ticketMovieName;
        final TextView ticketSeats;
        final TextView ticketMoneyPaid;
        TicketHolder(@NonNull View itemView) {
            super(itemView);
            ticketMovieImage=itemView.findViewById(R.id.ticketMovieImage);
            ticketBackground=itemView.findViewById(R.id.ticketBackground);
            ticketPaymentID=itemView.findViewById(R.id.ticketPaymentID);
            ticketMovieName=itemView.findViewById(R.id.ticketMovieName);
            ticketSeats=itemView.findViewById(R.id.ticketSeats);
            ticketMoneyPaid=itemView.findViewById(R.id.ticketMoneyPaid);
        }
        void bind(final Show show, @NonNull final OnShowSelectedListener onShowSelectedListener){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onShowSelectedListener.onShowSelected(show);
                }
            });
        }
    }
}