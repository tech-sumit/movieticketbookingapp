package com.webtechdevelopers.sumit.movieticketbookingapp.framework.entities;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Video implements Serializable{

    @SerializedName("key")
    private final String key;

    private Video(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public static Video fromSerializable(String json){
        return new Gson().fromJson(json,Video.class);
    }

}
