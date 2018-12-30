package com.webtechdevelopers.sumit.movieticketbookingapp.framework;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.webtechdevelopers.sumit.movieticketbookingapp.framework.entities.Show;

import java.util.ArrayList;

public class PersistantDataStorage {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Context context;
    private String pref="ticket_data";
    public PersistantDataStorage(Context context){
        this.context=context;
        sharedPreferences=context.getSharedPreferences(pref,Context.MODE_PRIVATE);
    }

    public void addShow(Show show){
        editor=sharedPreferences.edit();
        int orderCount=sharedPreferences.getInt("order_count",0);
        String json=show.getSerializable();
        editor.putString(""+(orderCount+1),json);
        editor.putInt("order_count",(orderCount+1));
        editor.apply();
        Log.i("PersistantDataStorage","Show Saved: "+json);
    }

    public ArrayList<Show> getShows(){
        int orderCount=sharedPreferences.getInt("order_count",0);
        ArrayList<Show> shows=new ArrayList<>();
        if(orderCount>0){
            for(int i=1;i<=orderCount;i++){
                String jsonString=sharedPreferences.getString(""+i,"No bookings done yet");
                shows.add(Show.fromSerializable(jsonString));
            }
        }
        Log.i("PersistantDataStorage","Show Data: "+shows);
        return shows;
    }

}
