package com.webtechdevelopers.sumit.movieticketbookingapp.framework.entities;

import android.support.annotation.NonNull;

import java.util.Objects;

@SuppressWarnings("ALL")
public class ProductionCountry {
    private String iso_3166_1;
    private String name;

    public ProductionCountry(String iso_3166_1, String name) {
        this.iso_3166_1 = iso_3166_1;
        this.name = name;
    }

    public String getIso_3166_1() {
        return iso_3166_1;
    }

    public void setIso_3166_1(String iso_3166_1) {
        this.iso_3166_1 = iso_3166_1;
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
                "iso_3166_1=" + iso_3166_1 +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductionCountry)) return false;
        ProductionCountry that = (ProductionCountry) o;
        return Objects.equals(getIso_3166_1(), that.getIso_3166_1()) &&
                getName().equals(that.getName());
    }
}
