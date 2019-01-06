package com.webtechdevelopers.sumit.movieticketbookingapp.framework;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.webtechdevelopers.sumit.movieticketbookingapp.framework.entities.Show;

import java.util.ArrayList;

public class PersistentDataStorage {
    private SharedPreferences sharedPreferences;

    public PersistentDataStorage(Context context){
        String pref = "ticket_data";
        sharedPreferences=context.getSharedPreferences(pref,Context.MODE_PRIVATE);
    }

    //We are adding one show each time. So cannot store arraylist of shows tobe stored in sharedPreferences
    public void addShow(Show show){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        int orderCount=sharedPreferences.getInt("order_count",0);
        String json=show.getSerializable();
        editor.putString(""+(orderCount+1),json);
        editor.putInt("order_count",(orderCount+1));
        editor.apply();
        Log.i("PersistentDataStorage","Show Saved: "+json);
    }

    //Similarly we have to read one by one by specifying their keys.
    //But we want to show all the show data in single fragment so we are reading them one by one and adding to ArrayList to pass them to recyclerview.
    public ArrayList<Show> getShows(){
        int orderCount=sharedPreferences.getInt("order_count",0);
        ArrayList<Show> shows=new ArrayList<>();
        if(orderCount>0){
            for(int i=1;i<=orderCount;i++){
                String jsonString=sharedPreferences.getString(""+i,"No bookings done yet");
                shows.add(Show.fromSerializable(jsonString));
            }
        }
        Log.i("PersistentDataStorage","Show Data: "+shows);
        return shows;
    }
}
