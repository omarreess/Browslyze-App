package com.example.browslyze2.Logic;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewParent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.browslyze2.R;

public class Browser {


    View view ;
    WebView web ;
    WebViewClient vc;
    String firstUrl,longUrl ;


    @SuppressLint("SetJavaScriptEnabled")
    public Browser(WebView webviewBinded) {
        this.web = webviewBinded;
        vc = new WebViewClient();
        web.setWebViewClient(vc);
        web.getSettings().setJavaScriptEnabled(true);
        web.setInitialScale(1);
        web.getSettings().setDomStorageEnabled(true);

        web.getSettings().setLoadWithOverviewMode(true);
        web.getSettings().setUseWideViewPort(true);


        web.getSettings().setLoadWithOverviewMode(true);
        web.getSettings().setUseWideViewPort(true);

        web.getSettings().setSupportZoom(true);
        web.getSettings().setBuiltInZoomControls(true);
        web.getSettings().setDisplayZoomControls(false);

        web.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        web.setScrollbarFadingEnabled(false);
    }

    public void backBtn (Context context)
    {if (web.canGoBack())
    {
        web.goBack();
    }
    else {

        Toast.makeText(context, "This is your First WebPage ", Toast.LENGTH_SHORT).show();

    }
    }





    public String surf (String url)
    {
         longUrl ="http://"+url;
        web.loadUrl(longUrl);



        return web.getUrl();
          }
    }




