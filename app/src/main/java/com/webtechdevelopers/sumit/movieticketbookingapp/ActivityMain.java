package com.webtechdevelopers.sumit.movieticketbookingapp;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.razorpay.Checkout;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultListener;
import com.razorpay.PaymentResultWithDataListener;
import com.webtechdevelopers.sumit.movieticketbookingapp.fragments.FragmentBooking;
import com.webtechdevelopers.sumit.movieticketbookingapp.fragments.FragmentMain;
import com.webtechdevelopers.sumit.movieticketbookingapp.fragments.FragmentMovieDetails;
import com.webtechdevelopers.sumit.movieticketbookingapp.fragments.FragmentOrders;
import com.webtechdevelopers.sumit.movieticketbookingapp.fragments.FragmentPayment;
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.OnFragmentInteractionListener;
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.PersistantDataStorage;
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.entities.Show;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityMain extends AppCompatActivity implements OnFragmentInteractionListener,PaymentResultWithDataListener{
    private int backCount=0;
    private int last_fragment_id=R.layout.fragment_main;
    private Show show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Checkout.preload(this);
        //setTheme(R.style.AppTheme_NoActionBar_Dark);
        setContentView(R.layout.layout_splash_screen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setContentView(R.layout.activity_main);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_container,new FragmentMain())
                        .commitNow();
            }
        },2000);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onFragmentInteractionResult(String fragmentName, Bundle bundle) {
        FragmentMovieDetails fragmentMovieDetails;
        switch (fragmentName){
            case "main_fragment":
                last_fragment_id=R.layout.fragment_main;
                FragmentMain fragmentMain =new FragmentMain();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_container, fragmentMain)
                        .commit();
                break;
            case "now_playing":
                last_fragment_id=R.layout.fragment_now_playing;
                fragmentMovieDetails =FragmentMovieDetails.newInstance(bundle);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_container, fragmentMovieDetails)
                        .addToBackStack("FragmentMovieDetails")
                        .commit();
                break;
            case "top_rated":
                last_fragment_id=R.layout.fragment_top_rated;
                fragmentMovieDetails = FragmentMovieDetails.newInstance(bundle);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_container, fragmentMovieDetails)
                        .addToBackStack("FragmentMovieDetails")
                        .commit();
                break;
            case "upcoming":
                last_fragment_id=R.layout.fragment_upcoming;
                fragmentMovieDetails =FragmentMovieDetails.newInstance(bundle);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_container, fragmentMovieDetails)
                        .addToBackStack("FragmentMovieDetails")
                        .commit();
                break;
            case "booking":
                last_fragment_id=R.layout.fragment_booking;
                FragmentBooking fragmentBooking =FragmentBooking.newInstance(bundle);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_container, fragmentBooking)
                        .addToBackStack("FragmentBooking")
                        .commit();
                break;
            case "payment":
                last_fragment_id=R.layout.fragment_payment;
                FragmentPayment fragmentPayment =FragmentPayment.newInstance(bundle);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_container, fragmentPayment)
                        .addToBackStack("FragmentPayment")
                        .commit();
                break;
            case "orders":
                last_fragment_id=R.layout.fragment_payment;
                FragmentOrders fragmentOrders=new FragmentOrders();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_container, fragmentOrders)
                        .addToBackStack("FragmentOrders")
                        .commit();
                break;
            default:
                last_fragment_id=R.layout.fragment_main;
                Log.e("CASE_ERROR","Invalid fragmentId ID:"+fragmentName);
        }
    }

    @Override
    public void onBackPressed() {
        if(last_fragment_id==R.layout.fragment_main){
            backCount++;
            last_fragment_id=R.layout.fragment_main;
            if(backCount>1){
                super.onBackPressed();
            }else {
                Toast.makeText(ActivityMain.this,"Press again to close",Toast.LENGTH_SHORT).show();
            }
        }else{
            super.onBackPressed();
            backCount=0;
            last_fragment_id=R.layout.fragment_main;
        }
    }

    @Override
    public void onPaymentSuccess(String s, PaymentData paymentData) {
        Log.i("PAYMENT_RESULT",""+s);
        PersistantDataStorage persistantDataStorage=new PersistantDataStorage(this);
        show.setPaymentData(paymentData);
        persistantDataStorage.addShow(show);
        Toast.makeText(this,"Payment Successful",Toast.LENGTH_SHORT).show();
        //TODO: Save order details into database. Set booked seats as already booked status in booing fragment
    }

    @Override
    public void onPaymentError(int i, String s, PaymentData paymentData) {
        Log.i("PAYMENT_RESULT","i: "+i+"\n"+s);
        switch (i){
            case Checkout.PAYMENT_CANCELED:
                Toast.makeText(this,"Payment Canceled",Toast.LENGTH_SHORT).show();
                break;
            case Checkout.NETWORK_ERROR:
                Toast.makeText(this,"Network Filed",Toast.LENGTH_SHORT).show();
                break;
            case Checkout.INVALID_OPTIONS:
                Toast.makeText(this,"Invalid Options",Toast.LENGTH_SHORT).show();
                break;
            case Checkout.TLS_ERROR:
                Toast.makeText(this,"TLS Error",Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(this,"Unknown error, Try again later",Toast.LENGTH_SHORT).show();
        }
    }
    public void setShow(Show show){
        this.show=show;
    }
}
