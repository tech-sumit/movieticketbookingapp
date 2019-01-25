package com.webtechdevelopers.sumit.movieticketbookingapp.framework.network;

import com.webtechdevelopers.sumit.movieticketbookingapp.framework.entities.DetailedMovie;
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.entities.Movie;
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.entities.Video;

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
                Movie movie=Movie.fromSerializable(jsonMovie.toString());
                movie.setGenre_ids(movie.getGenre_ids());
                movies.add(movie);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return movies;
    }

    public static Movie getMovie(String jsonString){
        Movie movieResult = null;
        try {
            JSONObject jsonMovie=new JSONObject(jsonString);

            movieResult=Movie.fromSerializable(jsonMovie.toString());
            movieResult.setGenre_ids(movieResult.getGenre_ids());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return movieResult;
    }

    public static ArrayList<Video> getVideos(String jsonString){
        ArrayList<Video> videos=new ArrayList<>();
        try {
            JSONObject jsonObject=new JSONObject(jsonString);
            JSONArray results=jsonObject.getJSONArray("results");
            for(int i=0;i<results.length();i++){
                JSONObject jsonMovie=results.getJSONObject(i);
                videos.add(Video.fromSerializable(jsonMovie.toString()));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return videos;
    }

    public static DetailedMovie getDetailMovie(String jsonString){
        DetailedMovie movie= null;
        try {
            JSONObject jsonMovie=new JSONObject(jsonString);
            movie=DetailedMovie.fromSerializable(jsonMovie.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return movie;
    }
}
