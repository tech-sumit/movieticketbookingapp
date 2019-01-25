
package com.webtechdevelopers.sumit.movieticketbookingapp.framework.entities;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ProductionCountry implements Serializable
{

    @SerializedName("iso_3166_1")
    private String iso31661;
    @SerializedName("name")
    private String name;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ProductionCountry() {
    }

    /**
     * 
     * @param iso31661
     * @param name
     */
    public ProductionCountry(String iso31661, String name) {
        super();
        this.iso31661 = iso31661;
        this.name = name;
    }

    public String getIso31661() {
        return iso31661;
    }

    public void setIso31661(String iso31661) {
        this.iso31661 = iso31661;
    }

    public ProductionCountry withIso31661(String iso31661) {
        this.iso31661 = iso31661;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProductionCountry withName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String toString() {
        return "ProductionCountry{" +
                "iso31661='" + iso31661 + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
