package com.example.browslyze2.UI;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.browslyze2.R;

import java.util.Objects;

import butterknife.ButterKnife;


public class SplashUI extends Fragment {



    public SplashUI() {

                    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_splash, container, false);
        ButterKnife.bind(this, view);

         return view;
        //ButterKnife.bind(this, view); we dont need it
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        int SPLASH_TIME = 1500 ;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Navigation.findNavController(Objects.requireNonNull(getView())).navigate(R.id.action_splash_to_authentication);

             }
        }, SPLASH_TIME);




        }
}
