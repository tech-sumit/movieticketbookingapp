package com.webtechdevelopers.sumit.movieticketbookingapp;

import android.util.Log;
import android.view.View;

import com.webtechdevelopers.sumit.movieticketbookingapp.framework.entities.Seat;

import java.util.ArrayList;
import java.util.List;

public class SeatLayoutGenerator {
    private int maxSeatCount=0;
    private int maxRowCount=0;
    private int maxColumnCount=0;
    private ArrayList<Seat> seatArrayList;
    private List seatRowList;
    private int price=0;

    public SeatLayoutGenerator(int maxSeatCount, int maxRowCount, int maxColumnCount,int price) {
        this.maxSeatCount = maxSeatCount;
        this.maxRowCount = maxRowCount;
        this.maxColumnCount = maxColumnCount;
        seatArrayList=new ArrayList<>();
        seatRowList=new ArrayList<>();
        this.price=price;

        for(int i=0;i<maxSeatCount;i++){
            seatArrayList.add(new Seat()
                    .setSeat_no(i%maxColumnCount)
                    .setBooked(false)
                    .setVisiblity(View.VISIBLE)
                    .setRow_no(i/maxColumnCount)
                    .setColumn_no(i%maxColumnCount)
                    .setPrice(price));
        }

        for(int i=0;i<maxRowCount;i++){
            seatRowList.add((char)(i+65));
        }
    }

    public void removeRow(int row){
        int start=row*maxColumnCount;
        Log.i("removeColumnRow","start: "+start+"\nrow: "+row+"\ncolumn count: "+maxColumnCount);
        for(int i=start;i<(start+maxColumnCount);i++){
            seatArrayList.add(i,seatArrayList.get(i).setVisiblity(View.INVISIBLE));
            Log.i("removeColumnRow","removeRow seat removed "+seatArrayList.get(i).toString());
        }
    }

    public void removeColumn(int column){
        int count=column;
        while (count<=maxSeatCount){
            seatArrayList.add(count,seatArrayList.get(count).setVisiblity(View.INVISIBLE));
            Log.i("removeColumnRow","removeColumn seat removed "+seatArrayList.get(count).toString());
            count+=maxColumnCount;
        }
        /*
        for(int i=0;i<maxColumnCount;i++){
            if(i/maxSeatCount==column){
                seatArrayList.add(i,seatArrayList.get(i).setVisiblity(View.INVISIBLE));
                Log.i("removeColumnRow","removeColumn seat removed "+seatArrayList.get(i).toString());
            }
        }
        */
    }

    public int getMaxSeatCount() {
        return maxSeatCount;
    }

    public int getMaxRowCount() {
        return maxRowCount;
    }

    public int getMaxColumnCount() {
        return maxColumnCount;
    }

    public ArrayList<Seat> getSeatArrayList() {
        return seatArrayList;
    }

    public List getSeatRowList() {
        return seatRowList;
    }

    @Override
    public String toString() {
        return "SeatLayoutGenerator{" +
                "maxSeatCount=" + maxSeatCount +
                ", maxRowCount=" + maxRowCount +
                ", maxColumnCount=" + maxColumnCount +
                ", seatArrayList=" + seatArrayList +
                '}';
    }
}
