package com.webtechdevelopers.sumit.movieticketbookingapp.framework.entities;

import java.io.Serializable;
import java.util.ArrayList;

public class Show implements Serializable {
    private Movie movie;
    private String venue = "";
    private String time = "";
    private ArrayList<Seat> seats;

    public Show() {
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

    @Override
    public String toString() {
        return "Show{" +
                "movie=" + movie +
                ", venue='" + venue + '\'' +
                ", time='" + time + '\'' +
                ", seats=" + seats +
                '}';
    }
}
