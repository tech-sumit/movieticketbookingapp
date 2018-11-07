package com.webtechdevelopers.sumit.movieticketbookingapp.framework;

import com.webtechdevelopers.sumit.movieticketbookingapp.framework.network.GenreParser;

import java.io.Serializable;
import java.util.Arrays;

public class Movie implements Serializable {

    private int vote_count=0;
    private int id=0;
    private boolean video=false;
    private double vote_average=0;
    private String title="";
    private double popularity=0;
    private String poster_path="";
    private String original_language="";
    private String original_title="";
    private int genre_ids[];
    private String genres[];
    private String backdrop_path="";
    private boolean adult=false;
    private String overview="";
    private String release_date="";

    public Movie(){

    }

    public Movie(int vote_count, int id, boolean video, double vote_average, String title, double popularity, String poster_path, String original_language, String original_title, int[] genre_ids, String backdrop_path, boolean adult, String overview, String release_date) {
        this.vote_count = vote_count;
        this.id = id;
        this.video = video;
        this.vote_average = vote_average;
        this.title = title;
        this.popularity = popularity;
        this.poster_path = poster_path;
        this.original_language = original_language;
        this.original_title = original_title;
        this.genre_ids = genre_ids;

        GenreParser genreParser=new GenreParser();
        genres=new String[genre_ids.length];
        for(int i=0;i<genre_ids.length;i++){
            genres[i]=genreParser.getGenre(genre_ids[i]);
        }

        this.backdrop_path = backdrop_path;
        this.adult = adult;
        this.overview = overview;
        this.release_date = release_date;
    }

    public int getVote_count() {
        return vote_count;
    }

    public Movie setVote_count(int vote_count) {
        this.vote_count = vote_count;
        return this;
    }

    public int getId() {
        return id;
    }

    public Movie setId(int id) {
        this.id = id;
        return this;
    }

    public boolean isVideo() {
        return video;
    }

    public Movie setVideo(boolean video) {
        this.video = video;
        return this;
    }

    public double getVote_average() {
        return vote_average;
    }

    public Movie setVote_average(int vote_average) {
        this.vote_average = vote_average;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Movie setTitle(String title) {
        this.title = title;
        return this;
    }

    public double getPopularity() {
        return popularity;
    }

    public Movie setPopularity(float popularity) {
        this.popularity = popularity;
        return this;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public Movie setPoster_path(String poster_path) {
        this.poster_path = poster_path;
        return this;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public Movie setOriginal_language(String original_language) {
        this.original_language = original_language;
        return this;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public Movie setOriginal_title(String original_title) {
        this.original_title = original_title;
        return this;
    }

    public int[] getGenre_ids() {
        return genre_ids;
    }

    public Movie setGenre_ids(int[] genre_ids) {
        this.genre_ids = genre_ids;
        GenreParser genreParser=new GenreParser();
        genres=new String[genre_ids.length];
        for(int i=0;i<genre_ids.length;i++){
            this.genres[i]=genreParser.getGenre(genre_ids[i]);
        }
        return this;
    }

    public String getGenres() {
        String genre=genres[0];
        for(int i=1;i<genres.length;i++){
            genre+=","+genres[i];
        }
        return genre;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public Movie setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
        return this;
    }

    public boolean isAdult() {
        return adult;
    }

    public Movie setAdult(boolean adult) {
        this.adult = adult;
        return this;
    }

    public String getOverview() {
        return overview;
    }

    public Movie setOverview(String overview) {
        this.overview = overview;
        return this;
    }

    public String getRelease_date() {
        return release_date;
    }

    public Movie setRelease_date(String release_date) {
        this.release_date = release_date;
        return this;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "vote_count=" + vote_count +
                ", id=" + id +
                ", video=" + video +
                ", vote_average=" + vote_average +
                ", title='" + title + '\'' +
                ", popularity=" + popularity +
                ", poster_path='" + poster_path + '\'' +
                ", original_language='" + original_language + '\'' +
                ", original_title='" + original_title + '\'' +
                ", genre_ids=" + Arrays.toString(genre_ids) +
                ", genres=" + Arrays.toString(genres) +
                ", backdrop_path='" + backdrop_path + '\'' +
                ", adult=" + adult +
                ", overview='" + overview + '\'' +
                ", release_date='" + release_date + '\'' +
                '}';
    }
}
