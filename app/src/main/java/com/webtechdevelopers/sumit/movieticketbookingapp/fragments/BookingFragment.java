package com.webtechdevelopers.sumit.movieticketbookingapp.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.webtechdevelopers.sumit.movieticketbookingapp.R;
import com.webtechdevelopers.sumit.movieticketbookingapp.SeatLayoutGenerator;
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.OnFragmentInteractionListener;
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.OnSeatClickActionListener;
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.SeatBookingRecyclerAdapter;
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.entities.Movie;
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.entities.Seat;
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.entities.Show;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class BookingFragment extends Fragment {
    private Movie movie;
    private Show show;

    private int seatPrice =200;
    private int maxSeatCount =350;
    private int maxSeatRowCount =10;
    private int maxSeatColumnCount =25;
    private int seatMaxSelectable=3;

    private RecyclerView seatRecyclerView;
    private ArrayList<Seat> seatArrayList;

    private TextView textCalculation;
    private TextView movieName;
    public BookingFragment() {
        // Required empty public constructor
    }

    public static BookingFragment newInstance(Bundle bundle) {
        BookingFragment fragment = new BookingFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            movie= (Movie) getArguments().getSerializable("movie");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_booking, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        show=new Show();
        show.setMovie(movie);

        seatRecyclerView =view.findViewById(R.id.seatRecyclerView);
        textCalculation=view.findViewById(R.id.textCalculation);
        movieName=view.findViewById(R.id.movieName);
        movieName.setText(""+movie.getOriginal_title());
        seatArrayList=new ArrayList<>();

        SeatLayoutGenerator seatLayoutGenerator=new SeatLayoutGenerator(maxSeatCount, maxSeatRowCount, maxSeatColumnCount,seatPrice);

        seatLayoutGenerator.removeRow(4);
        seatLayoutGenerator.removeRow(9);
        seatLayoutGenerator.removeColumn(5);
        seatLayoutGenerator.removeColumn(6);
        seatLayoutGenerator.removeColumn(19);
        seatLayoutGenerator.removeColumn(20);

        seatArrayList=seatLayoutGenerator.getSeatArrayList();
        /*
        for(int i=1;i<351;i++){
            if(((i-1)%maxSeatColumnCount)==5||((i-1)%maxSeatColumnCount)==6||((i-1)%maxSeatColumnCount)==19||((i-1)%maxSeatColumnCount)==20 ||(i/maxSeatColumnCount)==4||(i/maxSeatColumnCount)==9){
                seatArrayList.add(new Seat().setVisiblity(View.GONE));
            }else{
                seatArrayList.add(new Seat(i-1,i/maxSeatColumnCount,(i-1)%maxSeatColumnCount+1, seatPrice,View.VISIBLE,false));
            }
        }
        */

        SeatBookingRecyclerAdapter seatBookingAdapter =new SeatBookingRecyclerAdapter(seatArrayList,seatMaxSelectable, maxSeatColumnCount, new OnSeatClickActionListener() {
            @Override
            public void onSeatSelected(Seat seat) {
                ArrayList<Seat> seats=show.getSeats();
                seats.add(seat);
                show.setSeats(seats);
                textCalculation.setText(""+show.getSeatCount()+"X"+seatPrice+"="+(show.getSeatCount()*seatPrice)+"INR");
                Log.i("SeatAction","onSeatSelected: "+show.getSeats().toString());
            }

            @Override
            public void onSeatDeselected(Seat seat) {
                ArrayList<Seat> seats=show.getSeats();
                seats.remove(seat);
                show.setSeats(seats);
                textCalculation.setText(""+show.getSeatCount()+"X"+seatPrice+"="+(show.getSeatCount()*seatPrice)+"INR");
                Log.i("SeatAction","onSeatDeselected"+show.getSeats().toString());
            }
        });

        seatRecyclerView.setHasFixedSize(true);
        //seatRecyclerView.getRecycledViewPool().setMaxRecycledViews(0,0);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(view.getContext(), maxSeatColumnCount);
        seatRecyclerView.setLayoutManager(gridLayoutManager);
        seatRecyclerView.setAdapter(seatBookingAdapter);

        view.findViewById(R.id.bookButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle=new Bundle();
                bundle.putSerializable("show",show);
                ((OnFragmentInteractionListener)getActivity()).onFragmentInteractionResult("payment",bundle);
            }
        });
    }
}
