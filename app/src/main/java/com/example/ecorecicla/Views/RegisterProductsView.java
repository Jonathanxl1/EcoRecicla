package com.example.ecorecicla.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;


import com.example.ecorecicla.Constants.TypeProductsConstants;
import com.example.ecorecicla.Models.EstadisticaModel;
import com.example.ecorecicla.Models.ProductoReciclajeModel;
import com.example.ecorecicla.R;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.textfield.TextInputLayout;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class RegisterProductsView extends AppCompatActivity {
    private ArrayList<TypeProductsConstants> tipoProductos;
    private ArrayList<String> arrMeses;
    private TextInputLayout selectProducto,selectMes,txtCantidad;
    private ArrayAdapter listProductos,listMeses;

    private ProductoReciclajeModel productoReciclajeModel;

    private EstadisticaModel estadisticaModel;


    private Button btnRegistrarProducto;

    private BottomAppBar bottomAppBar;

    private File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_products_view);
        inicializar();
        ((AutoCompleteTextView) selectProducto.getEditText()).setAdapter(listProductos);
        ((AutoCompleteTextView) selectMes.getEditText()).setAdapter(listMeses);
        estadisticaModel = new EstadisticaModel(0);


        btnRegistrarProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mes = selectMes.getEditText().getText().toString();
                String producto =  selectProducto.getEditText().getText().toString();
                Double cantidad = Double.parseDouble(txtCantidad.getEditText().getText().toString());
               // productoReciclajeModel = new ProductoReciclajeModel(producto,cantidad,mes);



                estadisticaModel.setArrProductosReciclados(productoReciclajeModel);

                Toast.makeText(getApplicationContext(),String.valueOf(estadisticaModel.getPromedioProductos()),Toast.LENGTH_LONG).show();

                /*Intent paginaPrincipal = new Intent(getApplicationContext(), MainView.class);
                startActivity(paginaPrincipal);*/
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
        selectProducto = findViewById(R.id.selectProduct);
        selectMes = findViewById(R.id.selectDate);
        txtCantidad = findViewById(R.id.quantity);
        btnRegistrarProducto = findViewById(R.id.btnRegistrarProducto);
        bottomAppBar = findViewById(R.id.bottomAppBar);

        tipoProductos = new ArrayList<>();
        tipoProductos.addAll(Arrays.asList(TypeProductsConstants.values()));

        arrMeses = new ArrayList<String>();
        String[] stringsArrayMeses = {"Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre"};
        arrMeses.addAll(Arrays.asList(stringsArrayMeses));

        listProductos = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,tipoProductos);
        listProductos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        listMeses = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,arrMeses);
        listMeses.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


    }
}