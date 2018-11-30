package com.webtechdevelopers.sumit.movieticketbookingapp.framework.entities;

import java.io.Serializable;

public class Seat implements Serializable {
    private int seat_no=0;
    private int row_no=0;
    private int column_no=0;
    private int price=0;

    public Seat() {
    }

    public Seat(int seat_no, int row_no, int column_no, int price) {
        this.seat_no = seat_no;
        this.row_no = row_no;
        this.column_no = column_no;
        this.price = price;
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

    public void setSeat_no(int seat_no) {
        this.seat_no = seat_no;
    }

    public void setRow_no(int row_no) {
        this.row_no = row_no;
    }

    public void setColumn_no(int column_no) {
        this.column_no = column_no;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Seat{" +
                "seat_no=" + seat_no +
                ", row_no=" + row_no +
                ", column_no=" + column_no +
                ", price=" + price +
                '}';
    }
}
