package com.omaressam.browslyze2.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.omaressam.browslyze2.R;

import butterknife.ButterKnife;

public class MainScreen extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);
        ButterKnife.bind(this);



    }
}
