package com.example.browslyze2.UI;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.browslyze2.Logic.Authentication;
import com.example.browslyze2.Logic.Browser;
 import com.example.browslyze2.Logic.CheckInternet;
import com.example.browslyze2.Logic.RealtimeDb;
import com.example.browslyze2.NetworkLayer.ApiRetro;
import com.example.browslyze2.NetworkLayer.GetType;
import com.example.browslyze2.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class BrowserUI extends Fragment {
    @BindView(R.id.browser_url)
    EditText searchUrl;
    @BindView(R.id.browser_back)
    ImageView backBtn;
    @BindView(R.id.browser_userimage)
    CircleImageView userImage;
    @BindView(R.id.browser_webview)
    WebView webView ;
    @BindView(R.id.browser_root)
    ConstraintLayout view;
    CheckInternet checkInternet  ;
     Browser browser;
    RealtimeDb db ;
     Authentication auth ;
    static  HashMap hash ;
    Boolean first_Time;
    private InterstitialAd mInterstitialAd;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //for ad
        MobileAds.initialize(getContext(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mInterstitialAd = new InterstitialAd(getContext());
        mInterstitialAd.setAdUnitId("ca-app-pub-6808778866074990/1856003736");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_browser, container, false);
        ButterKnife.bind(this, view);
        first_Time =true;
        auth = new Authentication();
        if (auth.userUid() == null)
        {

        }
        else {
            FirebaseDatabase database = FirebaseDatabase.getInstance();

            DatabaseReference myRef = database.getReference(auth.userUid()).child("catogry");

            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                   //
                        hash = (HashMap) dataSnapshot.getValue();
                        first_Time = false;
                    //}
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

          browser = new Browser(webView);
         db=new RealtimeDb();
        //to set user image
        db.setProfileImage(auth.userImg(),userImage);
        //For Broadcast
        checkInternet = new CheckInternet(view);
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        filter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        getActivity().registerReceiver(checkInternet, filter);
         // . . .
    }

    @OnClick(R.id.browser_userimage)
    public void setUserImage() {
        Bundle bundle=new Bundle();
        bundle.putSerializable("HashMap" , hash);
        Navigation.findNavController(Objects.requireNonNull(getView())).navigate(R.id.action_browser_to_analyse , bundle);

    }

     @OnClick(R.id.browser_back)
    public void Back() {
  browser.backBtn(getActivity());
            }



    @OnTextChanged(value = R.id.browser_url,
            callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
     public void search(Editable editable) {
         String url = editable.toString().trim();
        if (url.endsWith(".com") || url.endsWith(".net"))
        {
             String newUrl = browser.surf(url);
            mInterstitialAd.show();
            try {
                 gettingType(newUrl);

            }
            catch (Exception e){
                Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
            }

        }
      }







    void gettingType (String url)
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiRetro.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()) //Here we are using the GsonConverterFactory to directly convert json data to object
                .build();
        ApiRetro api = retrofit.create(ApiRetro.class);
        Call<GetType> call = api.getType(url);

            call.enqueue(new Callback<GetType>() {
                @Override
                public void onResponse(Call<GetType> call, Response<GetType> response) {
              //here is a try & catch block cause of trial api
          try {
              ArrayList<String> arrayList = (ArrayList<String>) response.body().getCategories();
               setData(auth.userUid() ,arrayList);
               }
          catch (Exception e){}




                }

                @Override
                public void onFailure(Call<GetType> call, Throwable t) {

                }
            });
        }



       void setData (String uid , ArrayList<String> arr)
        {
            FirebaseDatabase fb3 = FirebaseDatabase.getInstance();
             DatabaseReference myRef3  =    fb3.getReference(uid).child("catogry");


            for(int i =0 ; i< ((int) arr.size()); i++ )
            {

                String type = arr.get(i).toString();
                long type_counter =  (Long) hash.get(type);

                type_counter ++;
                hash.put(type ,type_counter);
                 myRef3.child(type).setValue(type_counter);



            }
        }




}
