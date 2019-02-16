package com.webtechdevelopers.sumit.movieticketbookingapp.framework.entities;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.network.GenreParser;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class Movie implements Serializable {

    @SerializedName("vote_count")
    private int vote_count=0;
    @SerializedName("id")
    private int id=0;
    @SerializedName("video")
    private boolean video=false;
    @SerializedName("vote_average")
    private double vote_average=0;
    @SerializedName("title")
    private String title="";
    @SerializedName("popularity")
    private double popularity=0;
    @SerializedName("poster_path")
    private String poster_path="";
    @SerializedName("original_language")
    private String original_language="";
    @SerializedName("original_title")
    private String original_title="";
    private int genre_ids[];
    private String genres[];
    @SerializedName("backdrop_path")
    private String backdrop_path="";
    @SerializedName("adult")
    private boolean adult=false;
    @SerializedName("overview")
    private String overview="";
    @SerializedName("release_date")
    private String release_date="";
    @SerializedName("belongs_to_collection")
    private String belongs_to_collection="";
    @SerializedName("budget")
    private int budget=0;
    @SerializedName("homepage")
    private String homepage="";
    @SerializedName("imdb_id")
    private String imdb_id="";
    private ArrayList<ProductionCompany> companies;
    private ArrayList<ProductionCountry> countries;
    @SerializedName("revenue")
    private int revenue=0;
    @SerializedName("runtime")
    private int runtime=0;
    @SerializedName("spoken_languages")
    private String spoken_languages[];
    @SerializedName("status")
    private String status="";
    @SerializedName("tag_line")
    private String tag_line="";

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
        setGenre_ids(genre_ids);
        this.backdrop_path = backdrop_path;
        this.adult = adult;
        this.overview = overview;
        this.release_date = release_date;
        this.belongs_to_collection = "";
        this.budget = 0;
        this.homepage ="";
        this.imdb_id = "";
        this.companies =new ArrayList<>();
        this.countries = new ArrayList<>();
        this.revenue = 0;
        this.runtime = 0;
        this.spoken_languages = new String[1];
        this.status = "";
        this.tag_line = "";
    }

    public Movie(int vote_count, int id, boolean video, double vote_average, String title, double popularity, String poster_path, String original_language, String original_title, int[] genre_ids, String backdrop_path, boolean adult, String overview, String release_date, String belongs_to_collection, int budget, String homepage, String imdb_id, ArrayList<ProductionCompany> companies, ArrayList<ProductionCountry> countries, int revenue, int runtime, String[] spoken_languages, String status, String tag_line) {
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
        setGenre_ids(genre_ids);
        this.backdrop_path = backdrop_path;
        this.adult = adult;
        this.overview = overview;
        this.release_date = release_date;
        this.belongs_to_collection = belongs_to_collection;
        this.budget = budget;
        this.homepage = homepage;
        this.imdb_id = imdb_id;
        this.companies = companies;
        this.countries = countries;
        this.revenue = revenue;
        this.runtime = runtime;
        this.spoken_languages = spoken_languages;
        this.status = status;
        this.tag_line = tag_line;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
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

    public void setGenres(String[] genres) {
        this.genres = genres;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public void setBelongs_to_collection(String belongs_to_collection) {
        this.belongs_to_collection = belongs_to_collection;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public void setImdb_id(String imdb_id) {
        this.imdb_id = imdb_id;
    }

    public void setCompanies(ArrayList<ProductionCompany> companies) {
        this.companies = companies;
    }

    public void setCountries(ArrayList<ProductionCountry> countries) {
        this.countries = countries;
    }

    public void setRevenue(int revenue) {
        this.revenue = revenue;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public void setSpoken_languages(String[] spoken_languages) {
        this.spoken_languages = spoken_languages;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTag_line(String tag_line) {
        this.tag_line = tag_line;
    }

    public int getVote_count() {
        return vote_count;
    }

    public int getId() {
        return id;
    }

    public boolean isVideo() {
        return video;
    }

    public double getVote_average() {
        return vote_average;
    }

    public String getTitle() {
        return title;
    }

    public double getPopularity() {
        return popularity;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public int[] getGenre_ids() {
        return genre_ids;
    }

    public String getGenres() {
        if(genres!=null){
            if(genres.length>0){
                String genre=genres[0];
                for(int i=1;i<genres.length;i++){
                    genre+=", "+genres[i];
                }
                return genre;
            }
        }
        return "N/A";
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public boolean isAdult() {
        return adult;
    }

    public String getOverview() {
        return overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public String getBelongs_to_collection() {
        return belongs_to_collection;
    }

    public int getBudget() {
        return budget;
    }

    public String getHomepage() {
        return homepage;
    }

    public String getImdb_id() {
        return imdb_id;
    }

    public ArrayList<ProductionCompany> getCompanies() {
        return companies;
    }

    public String getCountries() {
        String country="";
        if(countries.size()>0){
            country=countries.get(0).getName();
            for(int i=1;i<countries.size();i++){
                country+=", "+countries.get(i).getName();
            }
        }
        return country;
    }

    public int getRevenue() {
        return revenue;
    }

    public int getRuntime() {
        return runtime;
    }

    public String getSpoken_languages() {
        String languages="";
        if(spoken_languages.length>0){
            languages=spoken_languages[0];
            for(int i=1;i<spoken_languages.length;i++){
                languages+=", "+spoken_languages[i];
            }
        }
        return languages;
    }

    public String getStatus() {
        return status;
    }

    public String getTag_line() {
        return tag_line;
    }

    public String getSerializable(){
        return new Gson().toJson(this);
    }

    public static Movie fromSerializable(String json){
        return new Gson().fromJson(json,Movie.class);
    }

    @Override
     public String toString() {
        return "MovieResult{" +
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
                ", belongs_to_collection='" + belongs_to_collection + '\'' +
                ", budget=" + budget +
                ", homepage='" + homepage + '\'' +
                ", imdb_id='" + imdb_id + '\'' +
                ", companies=" + companies +
                ", countries=" + countries +
                ", revenue=" + revenue +
                ", runtime=" + runtime +
                ", spoken_languages=" + Arrays.toString(spoken_languages) +
                ", status='" + status + '\'' +
                ", tag_line='" + tag_line + '\'' +
                '}';
    }
}
