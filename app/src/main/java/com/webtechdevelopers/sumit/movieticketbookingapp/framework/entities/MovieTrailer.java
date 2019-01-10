
package com.webtechdevelopers.sumit.movieticketbookingapp.framework.entities;

import android.os.Parcel;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MovieTrailer implements Serializable{

    @SerializedName("id")
    private int id;
    @SerializedName("videos")
    private List<Video> videos = new ArrayList<>();

    protected MovieTrailer(Parcel in) {
        this.id = ((int) in.readValue((int.class.getClassLoader())));
        in.readList(this.videos, (Video.class.getClassLoader()));
    }

    public MovieTrailer() {
    }

    public MovieTrailer(int id, List<Video> videos) {
        this.id = id;
        this.videos = videos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public MovieTrailer withId(int id) {
        this.id = id;
        return this;
    }

    public List<Video> getVideos() {
        return videos;
    }

    public void setVideos(List<Video> videos) {
        this.videos = videos;
    }

    public MovieTrailer withResults(List<Video> videos) {
        this.videos = videos;
        return this;
    }
    public String getSerializable(){
        return new Gson().toJson(this);
    }

    public static MovieTrailer fromSerializable(String json){
        return new Gson().fromJson(json,MovieTrailer.class);
    }

    @Override
    public String toString() {
        return "MovieTrailer{" +
                "id=" + id +
                ", videos=" + videos +
                '}';
    }
}
