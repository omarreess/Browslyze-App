package com.omaressam.browslyze2.Logic;

import androidx.annotation.NonNull;

import com.omaressam.browslyze2.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class RealtimeDb {


   public  static HashMap hash  ;

 // for checking if node exists or not to initialize it
public void initialize (String uid )
  {   FirebaseDatabase database = FirebaseDatabase.getInstance();
          DatabaseReference myRef = database.getReference();
     myRef.addListenerForSingleValueEvent(new ValueEventListener() {
         @Override
         public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
             if(dataSnapshot.child(uid).exists()) {

             } else
             {   myRef.child(uid).child("catogry").child("Arts and Entertainment").setValue(0);
                 myRef.child(uid).child("catogry").child("Autos and Vehicles").setValue(0);
                 myRef.child(uid).child("catogry").child("Beauty and Fitness").setValue(0);
                 myRef.child(uid).child("catogry").child("Books and Literature").setValue(0);
                 myRef.child(uid).child("catogry").child("Business and Industry").setValue(0);
                 myRef.child(uid).child("catogry").child("Career and Education").setValue(0);
                 myRef.child(uid).child("catogry").child("Computer and electronics").setValue(0);
                 myRef.child(uid).child("catogry").child("Finance").setValue(0);
                 myRef.child(uid).child("catogry").child("Food and Drink").setValue(0);
                 myRef.child(uid).child("catogry").child("Gambling").setValue(0);
                 myRef.child(uid).child("catogry").child("Games").setValue(0);
                 myRef.child(uid).child("catogry").child("Health").setValue(0);
                 myRef.child(uid).child("catogry").child("Home and Garden").setValue(0);
                 myRef.child(uid).child("catogry").child("Internet and Telecom").setValue(0);
                 myRef.child(uid).child("catogry").child("Law and Government").setValue(0);
                 myRef.child(uid).child("catogry").child("News and Media").setValue(0);
                 myRef.child(uid).child("catogry").child("People and Society").setValue(0);
                 myRef.child(uid).child("catogry").child("Pets and Animals").setValue(0);
                 myRef.child(uid).child("catogry").child("Recreation and Hobbies").setValue(0);
                 myRef.child(uid).child("catogry").child("Reference").setValue(0);
                 myRef.child(uid).child("catogry").child("Science").setValue(0);
                 myRef.child(uid).child("catogry").child("Shopping").setValue(0);
                 myRef.child(uid).child("catogry").child("Sports").setValue(0);
                 myRef.child(uid).child("catogry").child("Travel").setValue(0);
                 myRef.child(uid).child("catogry").child("Adult").setValue(0);}
         }

         @Override
         public void onCancelled(@NonNull DatabaseError databaseError) {

         }
     });



 }

    public void setData (String uid , ArrayList<String> arr , HashMap newhash)
    {    FirebaseDatabase fb3 = FirebaseDatabase.getInstance();
          DatabaseReference myRef3 = fb3.getReference();
        if (hash == null)
        {hash =getData(uid);}
        for(int i =0 ; i< ((int) arr.size()); i++ )
        {
            String type = arr.get(i).toString();
            long type_counter =  (Long) newhash.get(type);

            type_counter ++;
          newhash.put(type ,type_counter);
            myRef3 = fb3.getReference(uid).child("catogry");
            myRef3.child(type).setValue(type_counter);



        }
         hash = getData(uid);

    }

     public HashMap getData (String uid )
     {
          DatabaseReference myRef2 =   FirebaseDatabase.getInstance().getReference(uid).child("catogry");



            myRef2.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                      hash = (HashMap) dataSnapshot.getValue();


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
         return hash;


    }
     public void setProfileImage (String url , CircleImageView userImage)
    {
     Picasso.get()
             .load(url)
             .placeholder(R.drawable.profile)
             .error(R.drawable.profile)
             .into(userImage);
 }




 }
