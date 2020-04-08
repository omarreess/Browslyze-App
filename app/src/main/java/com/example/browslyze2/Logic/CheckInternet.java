package com.example.browslyze2.Logic;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.snackbar.Snackbar;

public class CheckInternet extends BroadcastReceiver {
    ConstraintLayout view;


    public CheckInternet(ConstraintLayout view) {
        this.view=view;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {



                // Make and display Snackbar
                Snackbar.make( view, "Wifi enabled", Snackbar.LENGTH_SHORT)
                        .show();


            }
            else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {

                Snackbar.make( view, "Mobile data enabled", Snackbar.LENGTH_SHORT)
                        .show();

            }
        }
        else {
            Snackbar.make( view, "No internet is available", Snackbar.LENGTH_SHORT)
                    .show();

        }

    }
}
