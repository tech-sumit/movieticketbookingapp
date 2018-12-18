package com.webtechdevelopers.sumit.movieticketbookingapp.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.razorpay.Checkout;
import com.razorpay.ExternalWalletListener;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultWithDataListener;
import com.webtechdevelopers.sumit.movieticketbookingapp.ActivityMain;
import com.webtechdevelopers.sumit.movieticketbookingapp.R;
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.PersistantDataStorage;
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.entities.Movie;
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.entities.Show;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentPayment extends Fragment{
    private TextView calcualationText;
    private Button payButton;
    private Show show;
    private float price;
    public FragmentPayment() {}

    public static FragmentPayment newInstance(Bundle bundle) {
        FragmentPayment fragment = new FragmentPayment();
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
        String calculation="Movie: "+movie.getOriginal_title();
        calculation+="\nVenue: "+show.getVenue()
                +"\nTotal Seats: "+show.getSeatCount()
                +"\nSeat rate: "+ show.getSeats().get(0).getPrice()+" INR"
                +"\nTotal: "+price+" INR";
        calcualationText.setText(calculation);
        payButton.setText("Pay "+price+"INR");
        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPayment(view);
            }
        });
    }

    private void startPayment(final View view){
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

        ((ActivityMain)getActivity()).setShow(show);

        Checkout.handleActivityResult(getActivity(), 0, 0, null, (PaymentResultWithDataListener) getActivity(), new ExternalWalletListener() {
            @Override
            public void onExternalWalletSelected(String s, PaymentData paymentData) {

            }
        });

        /*
        Checkout.handleActivityResult(getActivity(), 0, 0, null, new PaymentResultWithDataListener() {
            @Override
            public void onPaymentSuccess(String s, PaymentData paymentData) {
                Log.i("PAYMENT_RESULT",""+s);
                PersistantDataStorage persistantDataStorage=new PersistantDataStorage(view.getContext());
                show.setPaymentData(paymentData);
                persistantDataStorage.addShow(show);
                Toast.makeText(view.getContext(),"Payment Successful",Toast.LENGTH_SHORT).show();
                //TODO: Save order details into database. Set booked seats as already booked status in booing fragment
            }

            @Override
            public void onPaymentError(int i, String s, PaymentData paymentData) {
                Log.i("PAYMENT_RESULT","i: "+i+"\n"+s);
                switch (i){
                    case Checkout.PAYMENT_CANCELED:
                        Toast.makeText(view.getContext(),"Payment Canceled",Toast.LENGTH_SHORT).show();
                        break;
                    case Checkout.NETWORK_ERROR:
                        Toast.makeText(view.getContext(),"Network Filed",Toast.LENGTH_SHORT).show();
                        break;
                    case Checkout.INVALID_OPTIONS:
                        Toast.makeText(view.getContext(),"Invalid Options",Toast.LENGTH_SHORT).show();
                        break;
                    case Checkout.TLS_ERROR:
                        Toast.makeText(view.getContext(),"TLS Error",Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(view.getContext(),"Unknown error, Try again later",Toast.LENGTH_SHORT).show();
                }
            }
        }, new ExternalWalletListener() {
            @Override
            public void onExternalWalletSelected(String s, PaymentData paymentData) {

            }
        });

         */
    }
}
