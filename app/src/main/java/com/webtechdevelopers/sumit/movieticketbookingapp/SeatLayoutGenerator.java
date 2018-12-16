package com.webtechdevelopers.sumit.movieticketbookingapp;

import android.util.Log;
import android.view.View;

import com.webtechdevelopers.sumit.movieticketbookingapp.framework.entities.Seat;

import java.util.ArrayList;
import java.util.List;
//TODO: Solve the bugs in dynamic layout generator. This facilitates fast layout creation
public class SeatLayoutGenerator {
    private int maxSeatCount=0;
    private int maxRowCount=0;
    private int maxColumnCount=0;
    private ArrayList<Seat> seatArrayList;
    private List seatRowList;

    public SeatLayoutGenerator(int maxSeatCount, int maxRowCount, int maxColumnCount,int price) {
        this.maxSeatCount = maxSeatCount;
        this.maxRowCount = maxRowCount;
        this.maxColumnCount = maxColumnCount;
        seatArrayList=new ArrayList<>();
        seatRowList=new ArrayList<>();
        int price1 = price;

        for(int i=0;i<maxSeatCount;i++){
            int columnNo=(i)%maxColumnCount;
            char c=(char)((i/maxColumnCount)+65);
            seatArrayList.add(new Seat()
                    .setSeat_no(""+c+ (columnNo+1))
                    .setBooked(false)
                    .setVisiblity(View.VISIBLE)
                    .setRow_no(i/maxColumnCount)
                    .setColumn_no((i-1)%maxColumnCount+1)
                    .setPrice(price));
        }

        for(int i=0;i<maxRowCount;i++){
            seatRowList.add((char)(i+65));
        }
    }

    public void removeRow(int row){
        for(int i=0;i<maxSeatCount;i++){
            int rowNo=i/maxColumnCount;
            if(rowNo==row){
                seatArrayList.add(i+1,seatArrayList.get(i).setVisiblity(View.INVISIBLE));
                Log.i("removeColumnRow","removeRow seat removed "+seatArrayList.get(i).toString());
            }
        }
    }

    public void removeColumn(int column){
        for(int i=0;i<maxSeatCount;i++){
            int columnNo=(i)%maxColumnCount;
            if(columnNo==column){
                seatArrayList.add(i,seatArrayList.get(i).setVisiblity(View.INVISIBLE));
                Log.i("removeColumnRow","removeColumn seat removed "+seatArrayList.get(i).toString());
            }
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
