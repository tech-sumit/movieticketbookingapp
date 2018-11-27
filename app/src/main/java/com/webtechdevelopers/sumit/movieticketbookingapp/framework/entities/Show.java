package com.webtechdevelopers.sumit.movieticketbookingapp.framework.entities;

import java.io.Serializable;
import java.util.Arrays;

public class Show implements Serializable {
    private Movie movie;
    private String venue = "";
    private String time = "";
    private int seatCount;
    private int[] seats;

    public Show() {
    }

    public Show(Movie movie, String venue, int seatCount, int[] seats) {
        this.movie = movie;
        this.venue = venue;
        this.seatCount = seatCount;
        this.seats = seats;
    }

    public Movie getMovie() {
        return movie;
    }

    public String getVenue() {
        return venue;
    }

    public int getSeatCount() {
        return seatCount;
    }

    public int[] getSeats() {
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

    public void setSeatCount(int seatCount) {
        this.seatCount = seatCount;
    }

    public void setSeats(int[] seats) {
        this.seats = seats;
        seatCount = seats.length;
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
                ", seatCount=" + seatCount +
                ", seats=" + Arrays.toString(seats) +
                '}';
    }
}
