package com.webtechdevelopers.sumit.movieticketbookingapp;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import com.webtechdevelopers.sumit.movieticketbookingapp.fragments.BookingFragment;
import com.webtechdevelopers.sumit.movieticketbookingapp.fragments.MainFragment;
import com.webtechdevelopers.sumit.movieticketbookingapp.fragments.MovieDetailsFragment;
import com.webtechdevelopers.sumit.movieticketbookingapp.fragments.PaymentFragment;
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.OnFragmentInteractionListener;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements OnFragmentInteractionListener ,PaymentResultListener {
    int backCount=0;
    int last_fragment_id=R.layout.fragment_main;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Checkout.preload(this);
        setContentView(R.layout.layout_splash_screen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setContentView(R.layout.activity_main);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_container,MainFragment.newInstance(""))
                        .commitNow();
            }
        },3000);
    }

    @Override
    public void onFragmentInteractionResult(String fragmentName, Bundle bundle) {
        MovieDetailsFragment movieDetailsFragment;
        switch (fragmentName){
            case "now_playing":
                last_fragment_id=R.layout.fragment_now_playing;
                movieDetailsFragment=MovieDetailsFragment.newInstance(bundle);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_container,movieDetailsFragment)
                        .addToBackStack("MovieDetailsFragment")
                        .commit();

                break;
            case "top_rated":
                last_fragment_id=R.layout.fragment_top_rated;
                movieDetailsFragment = MovieDetailsFragment.newInstance(bundle);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_container,movieDetailsFragment)
                        .addToBackStack("MovieDetailsFragment")
                        .commit();
                break;
            case "upcoming":
                last_fragment_id=R.layout.fragment_upcoming;
                movieDetailsFragment=MovieDetailsFragment.newInstance(bundle);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_container,movieDetailsFragment)
                        .addToBackStack("MovieDetailsFragment")
                        .commit();
                break;
            case "booking":
                last_fragment_id=R.layout.fragment_upcoming;
                BookingFragment bookingFragment=BookingFragment.newInstance(bundle);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_container,bookingFragment)
                        .addToBackStack("BookingFragment")
                        .commit();
                break;
            case "payment":
                last_fragment_id=R.layout.fragment_upcoming;
                PaymentFragment paymentFragment=PaymentFragment.newInstance(bundle);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_container,paymentFragment)
                        .addToBackStack("PaymentFragment")
                        .commit();
                break;
            default:
                last_fragment_id=R.layout.fragment_main;
                Log.e("CASE_ERROR","Invalid fragmentId ID:"+fragmentName);
        }
    }

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(last_fragment_id==R.layout.fragment_main){
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.main_menu, menu);
            return true;
        }else{
            recreate();
            return false;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.signout:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    */

    @Override
    public void onBackPressed() {
        if(last_fragment_id==R.layout.fragment_main){
            backCount++;
            if(backCount>1){
                super.onBackPressed();
            }else {
                Toast.makeText(MainActivity.this,"Press again to close",Toast.LENGTH_SHORT).show();
            }
        }else{
            super.onBackPressed();
            backCount=0;
            last_fragment_id=R.layout.fragment_main;
        }
    }

    @Override
    public void onPaymentSuccess(String s) {
        Log.i("PAYMENT_RESULT",""+s);
        Toast.makeText(this,"Data:"+s,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPaymentError(int i, String s) {
        Log.i("PAYMENT_RESULT","i: "+i+"\n"+s);
        Toast.makeText(this,"Data:i" +i+" s"+s,Toast.LENGTH_SHORT).show();
    }
}
