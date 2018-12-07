package com.webtechdevelopers.sumit.movieticketbookingapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.razorpay.Checkout;
import com.razorpay.ExternalWalletListener;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultWithDataListener;
import com.webtechdevelopers.sumit.movieticketbookingapp.R;
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.entities.Movie;
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.entities.Show;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class PaymentFragment extends Fragment{
    private TextView calcualationText;
    private Button payButton;
    private Show show;
    private float price;
    public PaymentFragment() {}

    public static PaymentFragment newInstance(Bundle bundle) {
        PaymentFragment fragment = new PaymentFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            show= (Show) getArguments().getSerializable("show");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_payment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        calcualationText=view.findViewById(R.id.calculationText);
        payButton=view.findViewById(R.id.payButton);
        Movie movie=show.getMovie();
        price=show.getSeatCount()*show.getSeats().get(0).getPrice();
        String calculation=""+movie.getOriginal_title();
        calculation+="\n"+show.getVenue()
                +"\n"+show.getSeatCount()+" X "+ show.getSeats().get(0).getPrice()+" = "+price+"INR";
        calcualationText.setText(calculation);
        payButton.setText("Pay "+price+"INR");
        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPayment();
            }
        });
    }

    private void startPayment(){
        Checkout checkout=new Checkout();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("name","Movie Ticket Booking App");
            jsonObject.put("description","Ticket count");
            jsonObject.put("currency","INR");
            jsonObject.put("amount",""+(price*100));
            checkout.open(getActivity(),jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Checkout.handleActivityResult(getActivity(), 0, 0, null, new PaymentResultWithDataListener() {
            @Override
            public void onPaymentSuccess(String s, PaymentData paymentData) {

            }

            @Override
            public void onPaymentError(int i, String s, PaymentData paymentData) {

            }
        }, new ExternalWalletListener() {
            @Override
            public void onExternalWalletSelected(String s, PaymentData paymentData) {

            }
        });
    }
}
