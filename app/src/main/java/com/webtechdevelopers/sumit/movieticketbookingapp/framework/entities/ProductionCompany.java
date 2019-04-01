package com.webtechdevelopers.sumit.movieticketbookingapp.framework.entities;

import android.support.annotation.NonNull;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductionCompany implements Serializable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("logo_path")
    @Expose
    private Object logoPath;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("origin_country")
    @Expose
    private String originCountry;

    public ProductionCompany(Integer id, Object logoPath, String name, String originCountry) {
        super();
        this.id = id;
        this.logoPath = logoPath;
        this.name = name;
        this.originCountry = originCountry;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NonNull
    @Override
    public String toString() {
        return "ProductionCompany{" +
                "id=" + id +
                ", logoPath=" + logoPath +
                ", name='" + name + '\'' +
                ", originCountry='" + originCountry + '\'' +
                '}';
    }
}
