package com.webtechdevelopers.sumit.movieticketbookingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.webtechdevelopers.sumit.movieticketbookingapp.fragments.MainFragment;

public class MainActivity extends AppCompatActivity implements OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_container,MainFragment.newInstance("",this))
                .commitNow();
    }

    @Override
    public void onFragmentInteractionResult(int fragmentId, Bundle bundle) {
        switch (fragmentId){

            default:
                Log.e("CASE_ERROR","Invalid fragmentId ID:"+fragmentId);
        }
    }
}
