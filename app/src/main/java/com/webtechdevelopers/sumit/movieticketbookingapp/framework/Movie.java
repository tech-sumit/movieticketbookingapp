package com.webtechdevelopers.sumit.movieticketbookingapp.framework;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Movie {
    private String primaryInfo="";
    private String alternativeTitles="";
    private String cast="";
    private String crew="";
    private ArrayList<String> images;
    private String plotKeywords="";
    private String releaseInformation="";
    private ArrayList<String> trailers;
    private String translations="";
    private String similarMovies ="";
    private String reviews="";
    private String belongsToLists="";
    private String changes="";

    public Movie(){

    }
    public Movie(String primaryInfo, String alternativeTitles, String cast, String crew, ArrayList<String> images, String plotKeywords, String releaseInformation, ArrayList<String> trailers, String translations, String similarMovies, String reviews, String belongsToLists, String changes) {
        this.primaryInfo = primaryInfo;
        this.alternativeTitles = alternativeTitles;
        this.cast = cast;
        this.crew = crew;
        this.images = images;
        this.plotKeywords = plotKeywords;
        this.releaseInformation = releaseInformation;
        this.trailers = trailers;
        this.translations = translations;
        this.similarMovies = similarMovies;
        this.reviews = reviews;
        this.belongsToLists = belongsToLists;
        this.changes = changes;
    }

    public String getPrimaryInfo() {
        return primaryInfo;
    }

    public String getAlternativeTitles() {
        return alternativeTitles;
    }

    public String getCast() {
        return cast;
    }

    public String getCrew() {
        return crew;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public String getPlotKeywords() {
        return plotKeywords;
    }

    public String getReleaseInformation() {
        return releaseInformation;
    }

    public ArrayList<String> getTrailers() {
        return trailers;
    }

    public String getTranslations() {
        return translations;
    }

    public String getSimilarMovies() {
        return similarMovies;
    }

    public String getReviews() {
        return reviews;
    }

    public String getBelongsToLists() {
        return belongsToLists;
    }

    public String getChanges() {
        return changes;
    }

    public void setPrimaryInfo(String primaryInfo) {
        this.primaryInfo = primaryInfo;
    }

    public void setAlternativeTitles(String alternativeTitles) {
        this.alternativeTitles = alternativeTitles;
    }

    public void setCast(String cast) {
        this.cast = cast;
    }

    public void setCrew(String crew) {
        this.crew = crew;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    public void setPlotKeywords(String plotKeywords) {
        this.plotKeywords = plotKeywords;
    }

    public void setReleaseInformation(String releaseInformation) {
        this.releaseInformation = releaseInformation;
    }

    public void setTrailers(ArrayList<String> trailers) {
        this.trailers = trailers;
    }

    public void setTranslations(String translations) {
        this.translations = translations;
    }

    public void setSimilarMovies(String similarMovies) {
        this.similarMovies = similarMovies;
    }

    public void setReviews(String reviews) {
        this.reviews = reviews;
    }

    public void setBelongsToLists(String belongsToLists) {
        this.belongsToLists = belongsToLists;
    }

    public void setChanges(String changes) {
        this.changes = changes;
    }

    @NonNull
    @Override
    public String toString() {
        JSONObject jsonObject=new JSONObject();

        try {
            jsonObject.put("primaryInfo",getPrimaryInfo());
            jsonObject.put("alternativeTitles",getAlternativeTitles());
            jsonObject.put("cast",getCast());
            jsonObject.put("crew",getCrew());
            jsonObject.put("images",getImages().toString());
            jsonObject.put("plotKeywords",getPlotKeywords());
            jsonObject.put("releaseInformation",getReleaseInformation());
            jsonObject.put("trailers",getTrailers().toString());
            jsonObject.put("translation",getTranslations());
            jsonObject.put("similarMovies",getSimilarMovies());
            jsonObject.put("reviews",getReviews());
            jsonObject.put("belongsToLists",getBelongsToLists());
            jsonObject.put("changes",getChanges());
            } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }
}
