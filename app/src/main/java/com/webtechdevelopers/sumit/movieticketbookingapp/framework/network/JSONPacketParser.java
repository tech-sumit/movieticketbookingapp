package com.webtechdevelopers.sumit.movieticketbookingapp.framework.network;

import com.webtechdevelopers.sumit.movieticketbookingapp.framework.entities.Movie;
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.entities.ProductionCompany;
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.entities.ProductionCountry;

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

    public static Movie getMovie(String jsonString){
        Movie movie = null;
        try {
            JSONObject jsonMovie=new JSONObject(jsonString);

            movie=new Movie(
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
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return movie;
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

    public static Movie getDetailMovie(String jsonString){
        Movie movie = null;
        try {
            JSONObject jsonMovie=new JSONObject(jsonString);
            ArrayList<ProductionCompany> companies=new ArrayList<>();
            JSONArray jsonCompanies=new JSONArray(jsonMovie.getString("production_companies"));

            for(int i=0;i<jsonCompanies.length();i++){
                JSONObject company=jsonCompanies.getJSONObject(i);
                companies.add(new ProductionCompany(company.getInt("id"),company.getString("logo_path"),company.getString("name"),company.getString("origin_country")));
            }

            ArrayList<ProductionCountry> countries=new ArrayList<>();
            JSONArray jsonCountries=new JSONArray(jsonMovie.getString("production_countries"));
            for(int i=0;i<jsonCountries.length();i++){
                JSONObject country=jsonCountries.getJSONObject(i);
                countries.add(new ProductionCountry(country.getString("iso_3166_1"),country.getString("name")));
            }

            JSONArray jsonSpokenLanguages=new JSONArray(jsonMovie.getString("spoken_languages"));
            String spoken_languages[]=new String [jsonSpokenLanguages.length()];
            for(int i=0;i<jsonSpokenLanguages.length();i++){
                JSONObject language=jsonSpokenLanguages.getJSONObject(i);
                spoken_languages[i]= "" + language.getString("name");
            }

            movie=new Movie(
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
                    jsonMovie.getString("release_date"),
                    jsonMovie.getString("belongs_to_collection"),
                    jsonMovie.getInt("budget"),
                    jsonMovie.getString("homepage"),
                    jsonMovie.getString("imdb_id"),
                    companies,
                    countries,
                    jsonMovie.getInt("revenue"),
                    jsonMovie.getInt("runtime"),
                    spoken_languages,
                    jsonMovie.getString("status"),
                    jsonMovie.getString("tagline"));
/*
            JSONArray genreArray=jsonMovie.getJSONArray("genres");
            int genre[]=new int[genreArray.length()];
            for(int j=0;j<genreArray.length();j++){
                genre[j]=genreArray.getInt(j);
            }
            movie.setGenre_ids(genre);
*/
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return movie;
    }
}
