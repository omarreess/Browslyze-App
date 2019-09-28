package com.example.browslyze;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
EditText search_url;
ImageView backBtn ;
static HashMap hash;
private FirebaseAuth mAuth;
FirebaseUser user;
DatabaseReference myRef;
  private InterstitialAd mInterstitialAd;
    // Write a message to the database

 CircleImageView circleImageView ;
    FragmentManager fragmentManager ;
    Boolean first_instance;
    FragmentTransaction fragmentTransaction;
    browser frag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //AD


        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-6808778866074990/2535244401");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        circleImageView = findViewById(R.id.profile_image);
        first_instance = true;
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        myRef = database.getReference(user.getUid()).child("catogry");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(first_instance) {
                    hash = (HashMap) dataSnapshot.getValue();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        setProfileImage();
        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 Intent i = new Intent(getApplicationContext(), Main2Activity.class);
                i.putExtra("hashMap", hash);


                startActivity(i);

            }
        });

        backBtn =findViewById(R.id.imageView);
        search_url=findViewById(R.id.editText);

        search_url.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String url = s.toString().trim();
                if (url.endsWith(".com")) {
                    // Doing with url any thing
                    if (first_instance) {
                        mInterstitialAd.show();
                        first_instance = false;
                        fragmentManager = getSupportFragmentManager();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        frag = new browser(url);
                        fragmentTransaction.add(R.id.frame, frag);
                        fragmentTransaction.commit();
                        //Here Api
//                        ArrayList<String> arrayList = new ArrayList< >();
//                        arrayList.add("Adult");
//
//                         classifying(arrayList);
                        gettingType(url);
                    } else {
                        String hint = frag.changeUrl(url);
                        search_url.setHint(hint);
                        gettingType(url);
                        //here Api
//                        ArrayList<String> arrayList = new ArrayList< >();
//                        arrayList.add("Adult");
//
//                        classifying(arrayList);
                    }
                }
            }

        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(frag==null)
                {
                    Toast.makeText(MainActivity.this, "Please type a url", Toast.LENGTH_SHORT).show();}
                 else{frag.backBtn();}
            }
        });
    }


// Profile Image

    void setProfileImage (){
    Intent i = getIntent();
    Picasso.get()
            .load(i.getStringExtra("Image"))
            .placeholder(R.drawable.profile)
            .error(R.drawable.profile)
            .into(circleImageView);

    }

 // To setting Db , detrmine data returned from api (update Hashmap , db )

    void classifying (ArrayList<String> arr)
    {
        for(int i =0 ; i< ((int) arr.size()); i++ )
        {
            String type = arr.get(i).toString();
             long type_counter =  (Long) hash.get(type);

            type_counter ++;
            hash.put(type ,type_counter);
            myRef.child(type).setValue(type_counter);



        }

    }


    //Retrofit method
    void gettingType (String url)
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()) //Here we are using the GsonConverterFactory to directly convert json data to object
                .build();
         Api api = retrofit.create(Api.class);
         Call<GetType> call = api.getType(url);
         call.enqueue(new Callback<GetType>() {
             @Override
             public void onResponse(Call<GetType> call, Response<GetType> response) {
                ArrayList<String> arrayList = (ArrayList<String>) response.body().getCategories();
                  classifying(arrayList);
             }

             @Override
             public void onFailure(Call<GetType> call, Throwable t) {

             }
         });



    }

}



