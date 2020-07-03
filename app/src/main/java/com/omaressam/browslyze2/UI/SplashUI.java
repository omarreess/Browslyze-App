package com.omaressam.browslyze2.UI;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.omaressam.browslyze2.R;

import java.util.Objects;

import butterknife.ButterKnife;


public class SplashUI extends Fragment {







    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_splash, container, false);
        ButterKnife.bind(this, view);
       getActivity().getWindow().setStatusBarColor(getResources().getColor(R.color.black_grey));
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
