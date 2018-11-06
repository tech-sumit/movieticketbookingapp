package com.webtechdevelopers.sumit.movieticketbookingapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.webtechdevelopers.sumit.movieticketbookingapp.fragments.MainFragment;
import com.webtechdevelopers.sumit.movieticketbookingapp.fragments.MovieDetailsFragment;
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.Movie;

public class MainActivity extends AppCompatActivity implements OnFragmentInteractionListener{
    int backCount=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_container,MainFragment.newInstance(""))
                .commitNow();
    }

    @Override
    public void onFragmentInteractionResult(int fragmentId, Bundle bundle) {
        switch (fragmentId){
            case R.layout.fragment_now_playing:
                MovieDetailsFragment movieDetailsFragment=MovieDetailsFragment.newInstance(bundle);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_container,movieDetailsFragment)
                        .addToBackStack("MovieDetailsFragment")
                        .commit();

                break;
            default:
                Log.e("CASE_ERROR","Invalid fragmentId ID:"+fragmentId);
        }
    }

    @Override
    public void onBackPressed() {
        backCount++;

        if(backCount>1){
            super.onBackPressed();
        }else {
            Toast.makeText(MainActivity.this,"Press again to close",Toast.LENGTH_SHORT).show();
        }
    }
}
