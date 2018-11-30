package com.webtechdevelopers.sumit.movieticketbookingapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.webtechdevelopers.sumit.movieticketbookingapp.R;
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
    private int seatMaxRowCount=10;
    private int seatMaxColumnCount=25;

    private RecyclerView seatRecyclerView;
    private ArrayList<Seat> seatArrayList;

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

        seatRecyclerView=view.findViewById(R.id.seatRecyclerView);

        seatArrayList=new ArrayList<>();
        for(int i=1;i<250;i++){
            seatArrayList.add(new Seat(i,i/seatMaxColumnCount,i%seatMaxColumnCount, seatPrice));
        }

        SeatBookingRecyclerAdapter seatBookingRecyclerAdapter =new SeatBookingRecyclerAdapter(seatArrayList, new OnSeatClickActionListener() {
            @Override
            public void onSeatSelected(Seat seat) {
                ArrayList<Seat> seats=show.getSeats();
                seats.add(seat);
                show.setSeats(seats);
            }

            @Override
            public void onSeatDeselected(Seat seat) {
                ArrayList<Seat> seats=show.getSeats();
                seats.remove(seat);
                show.setSeats(seats);
            }
        });
        seatRecyclerView.setHasFixedSize(true);
        seatRecyclerView.setAdapter(seatBookingRecyclerAdapter);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(view.getContext(),seatMaxColumnCount);
        seatRecyclerView.setLayoutManager(gridLayoutManager);

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
