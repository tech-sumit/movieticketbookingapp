package com.webtechdevelopers.sumit.movieticketbookingapp.framework.entities;

import android.support.annotation.NonNull;

import java.io.Serializable;

public class Seat implements Serializable {
    private String seat_no="";
    private int row_no=0;
    private int column_no=0;
    private int price=0;
    private int visibility;
    private boolean isBooked =false;

    public Seat() { }

    public Seat(String  seat_no, int row_no, int column_no, int price, int visibility, boolean isBooked) {
        this.seat_no = seat_no;
        this.row_no = row_no;
        this.column_no = column_no;
        this.price = price;
        this.visibility = visibility;
        this.isBooked = isBooked;
    }

    public String getSeat_no() {
        return seat_no;
    }

    public int getPrice() {
        return price;
    }

    public int getVisibility() {
        return visibility;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public void setSeat_no(String seat_no) {
        this.seat_no = seat_no;
    }

    @NonNull
    public Seat setVisibility(int visibility) {
        this.visibility = visibility;
        return this;
    }

    @NonNull
    @Override
    public String toString() {
        return "Seat{" +
                "seat_no=" + seat_no +
                ", row_no=" + row_no +
                ", column_no=" + column_no +
                ", price=" + price +
                ", visibility=" + visibility +
                ", isBooked=" + isBooked +
                '}';
    }
}
