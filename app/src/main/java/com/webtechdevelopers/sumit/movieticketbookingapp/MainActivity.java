package com.webtechdevelopers.sumit.movieticketbookingapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.webtechdevelopers.sumit.movieticketbookingapp.fragments.MainFragment;
import com.webtechdevelopers.sumit.movieticketbookingapp.fragments.MovieDetailsFragment;
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.OnFragmentInteractionListener;

public class MainActivity extends AppCompatActivity implements OnFragmentInteractionListener {
    int backCount=0;
    int last_fragment_id=R.layout.fragment_main;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    public void onFragmentInteractionResult(int fragmentId, Bundle bundle) {
        MovieDetailsFragment movieDetailsFragment;
        switch (fragmentId){
            case R.layout.fragment_now_playing:
                last_fragment_id=R.layout.fragment_now_playing;
                movieDetailsFragment=MovieDetailsFragment.newInstance(bundle);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_container,movieDetailsFragment)
                        .addToBackStack("MovieDetailsFragment")
                        .commit();

                break;
            case R.layout.fragment_top_rated:
                last_fragment_id=R.layout.fragment_top_rated;
                movieDetailsFragment = MovieDetailsFragment.newInstance(bundle);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_container,movieDetailsFragment)
                        .addToBackStack("MovieDetailsFragment")
                        .commit();

                break;
            case R.layout.fragment_upcoming:
                last_fragment_id=R.layout.fragment_upcoming;
                movieDetailsFragment=MovieDetailsFragment.newInstance(bundle);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_container,movieDetailsFragment)
                        .addToBackStack("MovieDetailsFragment")
                        .commit();

                break;
            default:
                last_fragment_id=R.layout.fragment_main;
                Log.e("CASE_ERROR","Invalid fragmentId ID:"+fragmentId);
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
}
