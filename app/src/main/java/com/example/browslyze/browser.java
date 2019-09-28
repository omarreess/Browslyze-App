package com.example.browslyze;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;


public class browser extends Fragment {
View view ;
WebView web ;
    WebViewClient vc;
String firstUrl,longUrl ;

     public browser(String firstUrl) {
 this.firstUrl =firstUrl;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        view =inflater.inflate(R.layout.fragment_browser, container, false);
       web= view.findViewById(R.id.web);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
          vc = new WebViewClient();
        web.setWebViewClient(vc);
        web.getSettings().setJavaScriptEnabled(true);
        web.setInitialScale(1);

        web.getSettings().setLoadWithOverviewMode(true);
        web.getSettings().setUseWideViewPort(true);

        web.getSettings().setSupportZoom(true);
        web.getSettings().setBuiltInZoomControls(true);
        web.getSettings().setDisplayZoomControls(false);

        web.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        web.setScrollbarFadingEnabled(false);
         //WV Client to load site in app without opening externally
       changeUrl(firstUrl);

    }
    String changeUrl (String url ){


        longUrl ="http://"+url;
        web.loadUrl(longUrl);
    return web.getUrl().toString();}


     void backBtn ()
     {
         if (web.canGoBack()) {
             web.goBack();
         } else {
             Toast.makeText(getContext(), "This is your First WebPage ", Toast.LENGTH_SHORT).show();
         }
      }

}