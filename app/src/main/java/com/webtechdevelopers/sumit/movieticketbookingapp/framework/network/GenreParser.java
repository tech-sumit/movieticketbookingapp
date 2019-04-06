package com.webtechdevelopers.sumit.movieticketbookingapp.framework.network;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.HashMap;
//This class is created to translate the genre ids sent by server as genre type
//If not used then we have to make an api call to TheMovieDB Server to get genre string associated to id passed.
public class GenreParser {
    @NonNull
    private final HashMap<Integer, String > genreMap;
    @SuppressLint("UseSparseArrays")
    public GenreParser() {
        genreMap=new HashMap<>();
        genreMap.put(28,"Action");
        genreMap.put(12,"Adventure");
        genreMap.put(16,"Animation");
        genreMap.put(35,"Comedy");
        genreMap.put(80,"Crime");
        genreMap.put(99,"Documentary");
        genreMap.put(18,"Drama");
        genreMap.put(10751,"Family");
        genreMap.put(14,"Fantasy");
        genreMap.put(36,"History");
        genreMap.put(27,"Horror");
        genreMap.put(10402,"Music");
        genreMap.put(9648,"Mystery");
        genreMap.put(10749,"Romance");
        genreMap.put(878,"Science Fiction");
        genreMap.put(10770,"TV MovieResult");
        genreMap.put(53,"Thriller");
        genreMap.put(10752,"War");
        genreMap.put(37,"Western");
    }

    @Nullable
    public String getGenre(int id){
        return genreMap.get(id);
    }

    @NonNull
    @Override
    public String toString() {
        return "GenreParser{" +
                "genreMap=" + genreMap.toString() +
                '}';
    }
}
