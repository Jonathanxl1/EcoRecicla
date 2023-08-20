package com.example.ecorecicla.Views;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.ecorecicla.R;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainView extends AppCompatActivity {
    private FloatingActionButton addProduct;
    private BottomAppBar bottomAppBar;
    private Toolbar topBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal_view);
        addProduct = findViewById(R.id.addProduct);
        bottomAppBar = findViewById(R.id.bottomAppBar);
        topBar = findViewById(R.id.topAppBar);
        topBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId() == R.id.mapViewOption){
                    Intent mapView = new Intent(getApplicationContext(), MapView.class);
                    startActivity(mapView);
                    return true;
                }
                return false;
            }
        });



        bottomAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pageNews = new Intent(getApplicationContext(),NewsViews.class);
                startActivity(pageNews);
            }
        });

        bottomAppBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int option = item.getItemId();
                if (option == R.id.profileButton) {
                    Intent profile = new Intent(getApplicationContext(), ProfileView.class);
                    startActivity(profile);
                    return true;
                }else{
                    return false;
                }
            }
        });




        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registrarProducto = new Intent(getApplicationContext(), RegisterProductsView.class);
                startActivity(registrarProducto);
            }
        });
    }
}