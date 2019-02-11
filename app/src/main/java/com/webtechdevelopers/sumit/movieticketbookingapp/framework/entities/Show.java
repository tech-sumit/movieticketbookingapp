package com.webtechdevelopers.sumit.movieticketbookingapp.framework.entities;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.razorpay.PaymentData;

import java.io.Serializable;
import java.util.ArrayList;

public class Show implements Serializable {

    @SerializedName("movie")
    private Movie movie;
    @SerializedName("venue")
    private String venue = "";
    @SerializedName("time")
    private String time = "";
    @SerializedName("seats")
    private ArrayList<Seat> seats;
    @SerializedName("payment_data")
    private PaymentData paymentData;

    public Show() {
        seats=new ArrayList<>();
    }

    public Show(Movie movie, String venue, ArrayList<Seat> seats) {
        this.movie = movie;
        this.venue = venue;
        this.seats = seats;
    }

    public Movie getMovie() {
        return movie;
    }

    public String getVenue() {
        return venue;
    }

    public int getSeatCount() {
        return seats.size();
    }

    public ArrayList<Seat> getSeats() {
        return seats;
    }

    public String getTime() {
        return time;
    }

    public PaymentData getPaymentData() {
        return paymentData;
    }

    public String getSerializable(){
        return new Gson().toJson(this);
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public void setSeats(ArrayList<Seat> seats) {
        this.seats = seats;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setPaymentData(PaymentData paymentData) {
        this.paymentData = paymentData;
    }

    public static Show fromSerializable(String json){
        return new Gson().fromJson(json,Show.class);
    }

    @Override
    public String toString() {
        return "Show{" +
                "movie=" + movie +
                ", venue='" + venue + '\'' +
                ", time='" + time + '\'' +
                ", seats=" + seats +
                ", paymentData=" + paymentData +
                '}';
    }
}
