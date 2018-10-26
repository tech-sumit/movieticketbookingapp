package com.webtechdevelopers.sumit.movieticketbookingapp.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.webtechdevelopers.sumit.movieticketbookingapp.OnFragmentInteractionListener;
import com.webtechdevelopers.sumit.movieticketbookingapp.R;

public class NowPlayingFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private String mParam1;
    private OnFragmentInteractionListener onFragmentInteractionListener;

    public NowPlayingFragment() {
    }

    public static NowPlayingFragment newInstance(String param1, OnFragmentInteractionListener onFragmentInteractionListener) {
        NowPlayingFragment fragment = new NowPlayingFragment();
        fragment.onFragmentInteractionListener=onFragmentInteractionListener;
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_now_playing, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
