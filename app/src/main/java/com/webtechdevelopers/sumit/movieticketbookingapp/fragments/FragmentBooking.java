package com.webtechdevelopers.sumit.movieticketbookingapp.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.webtechdevelopers.sumit.movieticketbookingapp.R;
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.OnFragmentInteractionListener;
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.OnSeatClickActionListener;
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.SeatBookingRecyclerAdapter;
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.entities.Movie;
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.entities.Seat;
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.entities.Show;

import java.util.ArrayList;

public class FragmentBooking extends Fragment {
    private Movie movie;
    private Show show;

    private int seatPrice =200;
    private int maxSeatCount =336;
    private int maxSeatRowCount =10;
    private int maxSeatColumnCount =24;
    private int seatMaxSelectable=3;

    private RecyclerView seatRecyclerView;
    private ArrayList<Seat> seatArrayList;

    private TextView textCalculation;
    private TextView movieName;
    public FragmentBooking() {
        // Required empty public constructor
    }

    public static FragmentBooking newInstance(Bundle bundle) {
        FragmentBooking fragment = new FragmentBooking();
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
        final Spinner showTiming=view.findViewById(R.id.showTiming);
        showTiming.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                show.setTime(showTiming.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                show.setTime("");
            }
        });
        final Spinner showVenue=view.findViewById(R.id.showVenue);
        showVenue.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                show.setVenue(showVenue.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                show.setVenue("");
            }
        });

        //Generating static theater layout
        for(int i=0;i<336;i++){
            int columnNo=(i)%maxSeatColumnCount;
            int rowNo=i/maxSeatColumnCount;
            char c= (char) (rowNo+65);
            if(columnNo==4||columnNo==5||columnNo==18||columnNo==19 ||rowNo==4||rowNo==9){
                seatArrayList.add(new Seat().setVisiblity(View.GONE));
            }else{
                seatArrayList.add(new Seat(""+c+(columnNo+1),i/maxSeatColumnCount,(i-1)%maxSeatColumnCount+1, seatPrice,View.VISIBLE,false));
            }
        }

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
                if(show.getSeats().size()>0){
                    Bundle bundle=new Bundle();
                    bundle.putSerializable("show",show);
                    ((OnFragmentInteractionListener)getActivity()).onFragmentInteractionResult("payment",bundle);
                }else{
                    Toast.makeText(view.getContext(),"No seats selected",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
