package com.example.browslyze;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class Main2Activity extends AppCompatActivity {
Button signout ;
TextView name , email ;
    FirebaseAuth mAuth;
    FirebaseUser user;
    CircleImageView circleImageView;
    HashMap<String , Long> newHash;
static int  counter ;
    //for pie chart
    PieChart pieChart;
    PieData pieData;
    PieDataSet pieDataSet;
    ArrayList pieEntries;
    ArrayList PieEntryLabels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        circleImageView = findViewById(R.id.profile_image2);
        name =findViewById(R.id.textView3);
        email =findViewById(R.id.textView4);
         mAuth = FirebaseAuth.getInstance();
counter = 1;
        Intent intent = getIntent();
     newHash = (HashMap<String, Long>) intent.getSerializableExtra("hashMap");


        setProfileImage ();


        signout = findViewById(R.id.sign_out_button);
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent i = new Intent(getApplicationContext(), Auth.class);
                startActivity(i);
            }
        });
        //Pie Graph

        pieChart = findViewById(R.id.pieChart);
        getEntries();
        pieDataSet = new PieDataSet(pieEntries, "OMMMMMMM");
        pieData = new PieData(pieDataSet);
        pieData.setValueFormatter(new PercentFormatter());

        pieChart.setData(pieData);

        pieDataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        pieDataSet.setSliceSpace(2f);
        pieDataSet.setValueTextColor(Color.WHITE);
        pieDataSet.setValueTextSize(10f);
        pieDataSet.setSliceSpace(5f);

    }



    //Setting profile Data
    void setProfileImage (){

         Picasso.get()
                .load(  mAuth.getCurrentUser().getPhotoUrl().toString())
                .placeholder(R.drawable.profile)
                .error(R.drawable.profile)
                .into(circleImageView);
         name.setText( mAuth.getCurrentUser().getDisplayName().toString());
        email.setText( mAuth.getCurrentUser().getEmail().toString());


     }
//For pie chart

    private void getEntries() {
        pieEntries = new ArrayList<>();

        for (Map.Entry<String, Long> entry : newHash.entrySet()) {
            if (newHash.get(entry.getKey()) != 0) {
                pieEntries.add(new PieEntry(((float) newHash.get(entry.getKey())), entry.getKey().toString()));
            }
        }
    }

        }