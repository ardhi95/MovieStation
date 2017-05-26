package com.example.nabella.moviestation.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.nabella.moviestation.R;
import com.example.nabella.moviestation.seats.SeatsActivity;


public class MoviesFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movies, container, false);
        // Inflate the layout for this fragment
        Button btn = (Button)rootView.findViewById(R.id.btn_keseats);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), SeatsActivity.class);
                startActivity(i);
            }
        });
        return rootView;
    }

}
