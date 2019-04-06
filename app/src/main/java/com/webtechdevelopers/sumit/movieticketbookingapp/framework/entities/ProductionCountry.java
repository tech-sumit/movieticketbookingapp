
package com.webtechdevelopers.sumit.movieticketbookingapp.framework.entities;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

class ProductionCountry implements Serializable
{

    @SerializedName("name")
    private String name;


    public void setName(String name) {
        this.name = name;
    }

    @NonNull
    @Override
    public String toString() {
        return "ProductionCountry{" +
                ", name='" + name + '\'' +
                '}';
    }
}
