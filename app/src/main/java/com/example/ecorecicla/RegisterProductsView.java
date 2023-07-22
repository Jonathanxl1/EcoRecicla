package com.example.ecorecicla;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;


import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class RegisterProductsView extends AppCompatActivity {
    private ArrayList<String> tipoProductos;
    private TextInputLayout selectProducto;
    private ArrayAdapter listProductos;

    private Button btnRegistrarProducto;

    private BottomAppBar bottomAppBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_products_view);
        inicializar();
        ((AutoCompleteTextView) selectProducto.getEditText()).setAdapter(listProductos);
        btnRegistrarProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent paginaPrincipal = new Intent(getApplicationContext(), MainView.class);
                startActivity(paginaPrincipal);
            }
        });

        bottomAppBar.setOnClickListener(new View.OnClickListener() {
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


    }

    private void inicializar(){
        tipoProductos = new ArrayList<>();
        tipoProductos.add("Agua");
        tipoProductos.add("Vidrio");
        tipoProductos.add("Plastico");
        tipoProductos.add("Papel");
        listProductos = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,tipoProductos);
        listProductos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectProducto = findViewById(R.id.selectProduct);
        btnRegistrarProducto = findViewById(R.id.btnRegistrarProducto);
        bottomAppBar = findViewById(R.id.bottomAppBar);


    }
}