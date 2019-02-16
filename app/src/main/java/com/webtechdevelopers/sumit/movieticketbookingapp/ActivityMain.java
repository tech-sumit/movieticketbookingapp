package com.webtechdevelopers.sumit.movieticketbookingapp;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.razorpay.Checkout;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultWithDataListener;
import com.webtechdevelopers.sumit.movieticketbookingapp.fragments.FragmentBooking;
import com.webtechdevelopers.sumit.movieticketbookingapp.fragments.FragmentLogin;
import com.webtechdevelopers.sumit.movieticketbookingapp.fragments.FragmentMain;
import com.webtechdevelopers.sumit.movieticketbookingapp.fragments.FragmentMovieDetails;
import com.webtechdevelopers.sumit.movieticketbookingapp.fragments.FragmentOrders;
import com.webtechdevelopers.sumit.movieticketbookingapp.fragments.FragmentPayment;
import com.webtechdevelopers.sumit.movieticketbookingapp.fragments.FragmentTicketDetails;
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.Constants;
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.OnFragmentInteractionListener;
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.PersistentDataStorage;
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.entities.Show;

public class ActivityMain extends AppCompatActivity implements OnFragmentInteractionListener,PaymentResultWithDataListener{
    private int backCount=0;
    private boolean isDoubleClickAllowed=true;
    private Show show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Checkout.preload(this);
        //setTheme(R.style.AppTheme_NoActionBar_Dark);
        setContentView(R.layout.layout_splash_screen);
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ){
            ActivityCompat.requestPermissions(this,
                    new String[]{
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE}, 43);
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setContentView(R.layout.activity_main);
                SharedPreferences sharedPreferences=getSharedPreferences(Constants.LOGIN_PREF,MODE_PRIVATE);
                if(sharedPreferences.getBoolean(Constants.LOGIN,false)){
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_container,new FragmentMain())
                            .commitNow();
                }else{
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_container,new FragmentLogin())
                            .commitNowAllowingStateLoss();
                }
            }
        },2000);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onFragmentInteractionResult(String fragmentName, Bundle bundle) {
        backCount=0;
        FragmentMovieDetails fragmentMovieDetails;
        switch (fragmentName){
            case "login_fragment":
                isDoubleClickAllowed=true;
                FragmentLogin fragmentLogin=new FragmentLogin();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_container, fragmentLogin)
                        .commit();
                break;
            case "main_fragment":
                isDoubleClickAllowed=true;
                FragmentMain fragmentMain =new FragmentMain();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_container, fragmentMain)
                        .commitNowAllowingStateLoss();
                break;
            case "movie_details":
                isDoubleClickAllowed=false;
                fragmentMovieDetails =FragmentMovieDetails.newInstance(bundle);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_container, fragmentMovieDetails)
                        .addToBackStack("FragmentMovieDetails")
                        .commit();
                break;
            case "booking":
                isDoubleClickAllowed=false;
                FragmentBooking fragmentBooking =FragmentBooking.newInstance(bundle);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_container, fragmentBooking)
                        .addToBackStack("FragmentBooking")
                        .commit();
                break;
            case "payment":
                isDoubleClickAllowed=false;
                FragmentPayment fragmentPayment =FragmentPayment.newInstance(bundle);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_container, fragmentPayment)
                        .commit();
                break;
            case "orders":
                isDoubleClickAllowed=false;
                FragmentOrders fragmentOrders=new FragmentOrders();
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.main_container, fragmentOrders)
                        .addToBackStack("FragmentOrders")
                        .commit();
            case "ticket_details":
                isDoubleClickAllowed=false;
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_container, FragmentTicketDetails.newInstance(bundle))
                        .addToBackStack("FragmentTicketDetails")
                        .commit();
                break;
            default:
                isDoubleClickAllowed=false;
                Log.e("CASE_ERROR","Invalid fragmentId ID:"+fragmentName);
        }
    }

    @Override
    public void onBackPressed() {

        if(isDoubleClickAllowed){
            backCount++;
            if(backCount>1){
                finish();
            }else {
                Toast.makeText(ActivityMain.this,"Press again to close",Toast.LENGTH_SHORT).show();
            }
        }else{
            super.onBackPressed();
            backCount=0;
        }
    }

    @Override
    public void onPaymentSuccess(String s, PaymentData paymentData) {
        Log.i("PAYMENT_RESULT",""+s);
        try{
            PersistentDataStorage persistentDataStorage =new PersistentDataStorage(this);
            show.setPaymentData(paymentData);
            persistentDataStorage.addShow(show);
            Toast.makeText(this,"Payment Successful",Toast.LENGTH_SHORT).show();
            isDoubleClickAllowed=true;
            onFragmentInteractionResult("main_fragment",null);
        }catch (Exception e){
            e.printStackTrace();
        }
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
