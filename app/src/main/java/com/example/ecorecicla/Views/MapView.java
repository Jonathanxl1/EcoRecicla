package com.example.ecorecicla.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.example.ecorecicla.R;

public class MapView extends AppCompatActivity {

    private WebView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_view);
        init();
    }
     private void init(){
        mapView = findViewById(R.id.mapView);
        WebSettings webSettings = mapView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mapView.loadUrl("https://mapareciclajeapp.netlify.app");
     }
}