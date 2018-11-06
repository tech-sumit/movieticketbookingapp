package com.webtechdevelopers.sumit.movieticketbookingapp.framework.network;

import com.webtechdevelopers.sumit.movieticketbookingapp.framework.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JSONPacketParser {
    public static ArrayList<Movie> getMovies(String jsonString){
        ArrayList<Movie> movies=new ArrayList<>();
        try {
            JSONObject jsonObject=new JSONObject(jsonString);

            JSONArray results=jsonObject.getJSONArray("results");
            for(int i=0;i<results.length();i++){
                JSONObject jsonMovie=results.getJSONObject(i);
                Movie movie=new Movie(
                        jsonMovie.getInt("vote_count"),
                        jsonMovie.getInt("id"),
                        jsonMovie.getBoolean("video"),
                        jsonMovie.getDouble("vote_average"),
                        jsonMovie.getString("title"),
                        jsonMovie.getDouble("popularity"),
                        jsonMovie.getString("poster_path"),
                        jsonMovie.getString("original_language"),
                        jsonMovie.getString("original_title"),
                        new int[]{1,2},
                        jsonMovie.getString("backdrop_path"),
                        jsonMovie.getBoolean("adult"),
                        jsonMovie.getString("overview"),
                        jsonMovie.getString("release_date"));
                JSONArray genreArray=jsonMovie.getJSONArray("genre_ids");
                int genre[]=new int[genreArray.length()];
                for(int j=0;j<genreArray.length();j++){
                    genre[j]=genreArray.getInt(j);
                }
                movie.setGenre_ids(genre);
                movies.add(movie);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  movies;
    }

    public static ArrayList<String> getVideos(String jsonString){
        ArrayList<String> videos=new ArrayList<>();
        try {
            JSONObject jsonObject=new JSONObject(jsonString);
            JSONArray results=jsonObject.getJSONArray("results");
            for(int i=0;i<results.length();i++){
                JSONObject jsonMovie=results.getJSONObject(i);
                videos.add(jsonMovie.getString("key"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return videos;
    }
}
