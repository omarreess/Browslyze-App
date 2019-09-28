package com.example.browslyze;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Auth extends AppCompatActivity {
    private SignInButton googleSignInButton;
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;
    FirebaseUser user;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        googleSignInButton = findViewById(R.id.sign_in_button);

        mAuth = FirebaseAuth.getInstance();
        //mAuth.signOut();

       if((mAuth.getCurrentUser()!= null))
       {              PassToSecActivity(mAuth.getCurrentUser());}

       else { GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
               .requestIdToken("535061932316-v6uareaoaca4asa7q8kg7i3d1htuhhq0.apps.googleusercontent.com")
               .requestEmail()
               .build();
           mGoogleSignInClient = GoogleSignIn.getClient(this, gso);



           googleSignInButton.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                   startActivityForResult(signInIntent, 9001);
               }
           });
       }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 9001) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {

            }
        } }
        private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {

            AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
            mAuth.signInWithCredential(credential)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                   user = mAuth.getCurrentUser();

                                 MakeDb(user.getUid());
                                PassToSecActivity(user);

                            } else {  }
                                                    }
                    });
                                                                 }


                     void PassToSecActivity (FirebaseUser u )
                     { Intent i = new Intent(getApplicationContext(), MainActivity.class);
                         i.putExtra("Name", u.getDisplayName());
                         i.putExtra("Email", u.getEmail());
                         i.putExtra("Image", u.getPhotoUrl().toString());
                          i.putExtra("Uid" , u.getUid());
                         startActivity(i);
                     finish();}


                     void MakeDb (String uid)
                     {
                         FirebaseDatabase database = FirebaseDatabase.getInstance();
                           myRef = database.getReference();
                         myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                             @Override
                             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                 if(dataSnapshot.child(user.getUid()).exists()) {

                                 } else
                                 {   myRef.child(user.getUid()).child("catogry").child("Arts and Entertainment").setValue(0);
                                     myRef.child(user.getUid()).child("catogry").child("Autos and Vehicles").setValue(0);
                                     myRef.child(user.getUid()).child("catogry").child("Beauty and Fitness").setValue(0);
                                     myRef.child(user.getUid()).child("catogry").child("Books and Literature").setValue(0);
                                     myRef.child(user.getUid()).child("catogry").child("Business and Industry").setValue(0);
                                     myRef.child(user.getUid()).child("catogry").child("Career and Education").setValue(0);
                                     myRef.child(user.getUid()).child("catogry").child("Computer and electronics").setValue(0);
                                     myRef.child(user.getUid()).child("catogry").child("Finance").setValue(0);
                                     myRef.child(user.getUid()).child("catogry").child("Food and Drink").setValue(0);
                                     myRef.child(user.getUid()).child("catogry").child("Gambling").setValue(0);
                                     myRef.child(user.getUid()).child("catogry").child("Games").setValue(0);
                                     myRef.child(user.getUid()).child("catogry").child("Health").setValue(0);
                                     myRef.child(user.getUid()).child("catogry").child("Home and Garden").setValue(0);
                                     myRef.child(user.getUid()).child("catogry").child("Internet and Telecom").setValue(0);
                                     myRef.child(user.getUid()).child("catogry").child("Law and Government").setValue(0);
                                     myRef.child(user.getUid()).child("catogry").child("News and Media").setValue(0);
                                     myRef.child(user.getUid()).child("catogry").child("People and Society").setValue(0);
                                     myRef.child(user.getUid()).child("catogry").child("Pets and Animals").setValue(0);
                                     myRef.child(user.getUid()).child("catogry").child("Recreation and Hobbies").setValue(0);
                                     myRef.child(user.getUid()).child("catogry").child("Reference").setValue(0);
                                     myRef.child(user.getUid()).child("catogry").child("Science").setValue(0);
                                     myRef.child(user.getUid()).child("catogry").child("Shopping").setValue(0);
                                     myRef.child(user.getUid()).child("catogry").child("Sports").setValue(0);
                                     myRef.child(user.getUid()).child("catogry").child("Travel").setValue(0);
                                     myRef.child(user.getUid()).child("catogry").child("Adult").setValue(0);}
                             }

                             @Override
                             public void onCancelled(@NonNull DatabaseError databaseError) {

                             }
                         });






                      }
}


























