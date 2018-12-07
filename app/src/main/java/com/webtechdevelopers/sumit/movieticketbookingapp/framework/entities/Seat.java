package com.webtechdevelopers.sumit.movieticketbookingapp.framework.entities;

import java.io.Serializable;

public class Seat implements Serializable {
    private int seat_no=0;
    private int row_no=0;
    private int column_no=0;
    private int price=0;
    private int visiblity;
    private boolean isBooked =false;

    public Seat() {
    }

    public Seat(int seat_no, int row_no, int column_no, int price, int visiblity, boolean isBooked) {
        this.seat_no = seat_no;
        this.row_no = row_no;
        this.column_no = column_no;
        this.price = price;
        this.visiblity = visiblity;
        this.isBooked = isBooked;
    }

    public int getSeat_no() {
        return seat_no;
    }

    public int getRow_no() {
        return row_no;
    }

    public int getColumn_no() {
        return column_no;
    }

    public int getPrice() {
        return price;
    }

    public int getVisiblity() {
        return visiblity;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public Seat setSeat_no(int seat_no) {
        this.seat_no = seat_no;
        return this;
    }

    public Seat setRow_no(int row_no) {
        this.row_no = row_no;
        return this;
    }

    public Seat setColumn_no(int column_no) {
        this.column_no = column_no;
        return this;
    }

    public Seat setPrice(int price) {
        this.price = price;
        return this;
    }

    public Seat setVisiblity(int visiblity) {
        this.visiblity = visiblity;
        return this;
    }

    public Seat setBooked(boolean booked) {
        this.isBooked = booked;
        return this;
    }

    @Override
    public String toString() {
        return "Seat{" +
                "seat_no=" + seat_no +
                ", row_no=" + row_no +
                ", column_no=" + column_no +
                ", price=" + price +
                ", visibility=" + visiblity +
                ", isBooked=" + isBooked +
                '}';
    }
}
