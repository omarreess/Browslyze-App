package com.example.browslyze2.Logic;


import android.app.Activity;
import android.content.Context;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class Authentication  {

    public FirebaseAuth mAuth = FirebaseAuth.getInstance();
    public FirebaseUser user;
    RealtimeDb db = new RealtimeDb();






    public  Boolean  isExist ()
    {
        if((mAuth.getCurrentUser()!= null))
        { return true;}
        else return false ;
    }
    public void   signIn (GoogleSignInAccount account , Context context)
    {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);

        mAuth.signInWithCredential(credential).addOnCompleteListener((Activity) context, task -> {
            if (task.isSuccessful()) {
                // Sign in success, update UI with the signed-in user's information
                user = mAuth.getCurrentUser();
                db.initialize(user.getUid());






            }
        });

     }

    public void   signOut ()
    {
        mAuth.signOut();
    }
    // user info
    public String  userUid ()
    {
        return mAuth.getCurrentUser().getUid();
    }
    public String  userImg ()
    {
        return mAuth.getCurrentUser().getPhotoUrl().toString();
    }
    public String  userMail ()
    {
        return mAuth.getCurrentUser().getEmail() ;
    }

    public String  userName ()
    {
        return mAuth.getCurrentUser().getDisplayName() ;
    }


}

