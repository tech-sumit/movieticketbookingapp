
package com.webtechdevelopers.sumit.movieticketbookingapp.framework.entities;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

class Genre implements Serializable {

    @SerializedName("id")
    private Integer id;
    @SerializedName("name")
    private String name;


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

}
