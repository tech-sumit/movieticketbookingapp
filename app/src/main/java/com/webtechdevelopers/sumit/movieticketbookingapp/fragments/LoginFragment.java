package com.webtechdevelopers.sumit.movieticketbookingapp.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.webtechdevelopers.sumit.movieticketbookingapp.framework.OnFragmentInteractionListener;
import com.webtechdevelopers.sumit.movieticketbookingapp.R;
public class LoginFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private OnFragmentInteractionListener onFragmentInteractionListener=null;

    private String mParam1;

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance(String param1,OnFragmentInteractionListener onFragmentInteractionListener) {
        LoginFragment fragment = new LoginFragment();
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
        return inflater.inflate(R.layout.fragment_login, container, false);
    }
}
