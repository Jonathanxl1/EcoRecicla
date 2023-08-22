package com.example.ecorecicla.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.example.ecorecicla.R;

public class MapView extends AppCompatActivity {

    private WebView mapView;

    private Toolbar topBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_view);
        init();
        topBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
     private void init(){
        topBar = findViewById(R.id.topAppBar);
        mapView = findViewById(R.id.mapView);
        WebSettings webSettings = mapView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mapView.loadUrl("https://mapareciclajeapp.netlify.app");
     }
}