package com.example.browslyze2.UI;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.browslyze2.Logic.Authentication;
import com.example.browslyze2.Logic.CheckInternet;
import com.example.browslyze2.Logic.RealtimeDb;
import com.example.browslyze2.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static androidx.core.app.ActivityCompat.startActivityForResult;


public class AuthenticationUI extends Fragment {
    @BindView(R.id.auth_signinbtn)
      SignInButton googleSignInButton;
    @BindView(R.id.auth_root)
    ConstraintLayout root;
    CheckInternet checkInternet  ;
    Authentication auth ;
    RealtimeDb db ;
    public GoogleSignInClient mGoogleSignInClient;
GoogleSignInOptions gso;
    FirebaseAuth mAuth2;





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth2 = FirebaseAuth.getInstance();
        auth = new Authentication();
        db = new RealtimeDb() ;
          gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("535061932316-v6uareaoaca4asa7q8kg7i3d1htuhhq0.apps.googleusercontent.com")
                .requestEmail()
                .build();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_authentication, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(auth.isExist()) {     Navigation.findNavController(Objects.requireNonNull(getView())).navigate(R.id.action_authentication_to_browser );

        }

        else {  //For Broadcast
            checkInternet = new CheckInternet(root);
            IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
            filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
            filter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
            Objects.requireNonNull(getActivity()).registerReceiver(checkInternet, filter);
            // . . .
            }



     }

    //when user press on google signin button
    @OnClick (R.id.auth_signinbtn)
    public void signin() {

        mGoogleSignInClient = GoogleSignIn.getClient(Objects.requireNonNull(getContext()), gso);
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, 9001);




    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 9001) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);

                 //auth.signIn(account, getContext());
                signIn(account);

                  //    Navigation.findNavController(Objects.requireNonNull(getView())).navigate(R.id.action_authentication_to_browser );



            } catch (ApiException e) {
                e.printStackTrace();
            }
        }

}
    public void   signIn (GoogleSignInAccount account  )
    {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);

        mAuth2.signInWithCredential(credential).addOnCompleteListener(getActivity(), task -> {
            if (task.isSuccessful()) {
                // Sign in success, update UI with the signed-in user's information
                    ;
                    if(mAuth2.getCurrentUser().getUid() == null)
                    {Toast.makeText(getContext(), "user id is null", Toast.LENGTH_SHORT).show();}
                    else{ db.initialize(mAuth2.getCurrentUser().getUid());
                        Navigation.findNavController(Objects.requireNonNull(getView())).navigate(R.id.action_authentication_to_browser );}








            }
        });

     }

}
