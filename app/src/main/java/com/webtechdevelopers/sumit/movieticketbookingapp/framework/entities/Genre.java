
package com.webtechdevelopers.sumit.movieticketbookingapp.framework.entities;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Genre implements Serializable
{

    @SerializedName("id")
    private Integer id;
    @SerializedName("name")
    private String name;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Genre() {
    }

    /**
     * 
     * @param id
     * @param name
     */
    public Genre(Integer id, String name) {
        super();
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Genre withId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Genre withName(String name) {
        this.name = name;
        return this;
    }


}
