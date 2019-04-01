
package com.webtechdevelopers.sumit.movieticketbookingapp.framework.entities;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ProductionCountry implements Serializable
{

    @SerializedName("iso_3166_1")
    private String iso31661;
    @SerializedName("name")
    private String name;

    public ProductionCountry(String iso31661, String name) {
        super();
        this.iso31661 = iso31661;
        this.name = name;
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
        return "ProductionCountry{" +
                "iso31661='" + iso31661 + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
