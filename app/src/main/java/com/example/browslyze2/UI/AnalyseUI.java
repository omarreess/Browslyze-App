package com.example.browslyze2.UI;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.browslyze2.Logic.Authentication;
import com.example.browslyze2.Logic.PieCharting;
import com.example.browslyze2.Logic.RealtimeDb;
import com.example.browslyze2.R;
import com.github.mikephil.charting.charts.PieChart;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;


public class AnalyseUI extends Fragment {

    @BindView(R.id.analyse_sign_out_button)
    Button signOut;
    @BindView(R.id.analyse_imageuser)
    CircleImageView userImage;
    @BindView(R.id.analyse_username)
    TextView userName;
    @BindView(R.id.analyse_usermail)
    TextView userMail;
    @BindView(R.id.analyse_pieChart)
    PieChart pieChart;
    PieCharting pieCharting ;
    Authentication authentication ;
    RealtimeDb db ;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authentication = new Authentication();
        db=new RealtimeDb();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_analyse, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //getting hashmap from pervious fragemnt and setting the chart
        Bundle bundle = this.getArguments();
        pieCharting = new PieCharting((HashMap)bundle.getSerializable("HashMap"),pieChart );
        //to set user info
        db.setProfileImage(authentication.userImg() , userImage);
        userName.setText(authentication.userName());
        userMail.setText(authentication.userMail());


    }

    @OnClick(R.id.analyse_sign_out_button)
    public void signout() {
        authentication.signOut();
         Navigation.findNavController(Objects.requireNonNull(getView())).navigate(R.id.action_analyse_to_authentication);

    }

}
