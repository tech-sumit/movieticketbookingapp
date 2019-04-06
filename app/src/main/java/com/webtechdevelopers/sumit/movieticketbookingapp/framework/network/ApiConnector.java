package com.webtechdevelopers.sumit.movieticketbookingapp.framework.network;

import android.content.Context;
import android.support.annotation.NonNull;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.Constants;

public class ApiConnector {
    private final Context context;

    public ApiConnector(Context context){
        this.context=context;
    }

    public void getNowPlayingMovies(int page, @NonNull final OnAdiResultReceived onAdiResultReceived){
        Volley.newRequestQueue(context).add(new StringRequest(Request.Method.GET,
                Constants.BASE_URL+"/now_playing?page="+page+"&api_key="+Constants.API_KEY,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        onAdiResultReceived.onResult(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(@NonNull VolleyError error) {
                        error.printStackTrace();
                    }
                }));
    }

    public void getPopularMovies(int page, @NonNull final OnAdiResultReceived onAdiResultReceived){
        Volley.newRequestQueue(context).add(new StringRequest(Request.Method.GET,
                Constants.BASE_URL+"/popular?page="+page+"&api_key="+Constants.API_KEY,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        onAdiResultReceived.onResult(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(@NonNull VolleyError error) {
                        error.printStackTrace();
                    }
                }));
    }

    public void getTopRatedMovies(int page, @NonNull final OnAdiResultReceived onAdiResultReceived){
        Volley.newRequestQueue(context).add(new StringRequest(Request.Method.GET,
                Constants.BASE_URL+"/top_rated?page="+page+"&api_key="+Constants.API_KEY,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        onAdiResultReceived.onResult(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(@NonNull VolleyError error) {
                        error.printStackTrace();
                    }
                }));
    }

    public void getUpcomingMovies(int page, @NonNull final OnAdiResultReceived onAdiResultReceived){
        Volley.newRequestQueue(context).add(new StringRequest(Request.Method.GET,
                Constants.BASE_URL+"/upcoming?page="+page+"&api_key="+Constants.API_KEY,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        onAdiResultReceived.onResult(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(@NonNull VolleyError error) {
                        error.printStackTrace();
                    }
                }));
    }

    public void getVideos(int id, @NonNull final OnAdiResultReceived onAdiResultReceived){
        Volley.newRequestQueue(context).add(new StringRequest(Request.Method.GET,
                Constants.BASE_URL+"/"+id+"/videos?api_key="+Constants.API_KEY,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        onAdiResultReceived.onResult(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(@NonNull VolleyError error) {
                        error.printStackTrace();
                    }
                }));
    }

    public void getMovieDetails(int id, @NonNull final OnAdiResultReceived onAdiResultReceived) {
        Volley.newRequestQueue(context).add(new StringRequest(Request.Method.GET,
                Constants.BASE_URL + "/" + id + "?api_key=" + Constants.API_KEY,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        onAdiResultReceived.onResult(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(@NonNull VolleyError error) {
                        error.printStackTrace();
                    }
                }));
    }
}
