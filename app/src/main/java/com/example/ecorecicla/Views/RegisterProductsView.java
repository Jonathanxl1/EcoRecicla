package com.example.ecorecicla.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;


import com.example.ecorecicla.Constants.TypeProductsConstants;
import com.example.ecorecicla.Controllers.DataAdministrator;
import com.example.ecorecicla.Models.EstadisticaModel;
import com.example.ecorecicla.Models.ProductoReciclajeModel;
import com.example.ecorecicla.R;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class RegisterProductsView extends AppCompatActivity {
    private ArrayList<TypeProductsConstants> tipoProductos;
    private TextInputEditText selectDate,txtCantidad;

    private AutoCompleteTextView selectProducto;
    private ArrayAdapter listProductos;

    private ProductoReciclajeModel productoReciclajeModel;

    private EstadisticaModel estadisticaModel;


    private Button btnRegistrarProducto;

    private BottomAppBar bottomAppBar;

    private File file;

     final Calendar c  = Calendar.getInstance();

    private int year ;
    private int month ;
    private int day ;

    private LocalDate localDate;

    private int positionItem;

    SharedPreferences preferences;
    Integer userIdRef;

    DataAdministrator estadisticasAdministrador ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_products_view);
        inicializar();
        validateBtn();

        selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDatePicker();
                validateBtn();
            }
        });
        selectProducto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                positionItem = position;
                validateBtn();
            }
        });

        estadisticaModel = new EstadisticaModel(0);


        txtCantidad.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validateBtn();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });




        btnRegistrarProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Double cantidad = Double.parseDouble(txtCantidad.getText().toString());
                productoReciclajeModel = new ProductoReciclajeModel(TypeProductsConstants.values()[positionItem],cantidad, localDate);

                estadisticasAdministrador.saveProductModel(productoReciclajeModel,userIdRef);
                resetFields();

                Toast.makeText(getApplicationContext(),"Product saved successfully", Toast.LENGTH_LONG).show();

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
        year =  c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        estadisticasAdministrador = new DataAdministrator(estadisticaModel,getApplicationContext());

        preferences = getApplicationContext().getSharedPreferences("dataUser",MODE_PRIVATE);
        userIdRef = preferences.getInt("userIdRef",-1);

        selectProducto = findViewById(R.id.selectProduct);
        selectDate = findViewById(R.id.selectDate);
        txtCantidad = findViewById(R.id.quantity);
        btnRegistrarProducto = findViewById(R.id.btnRegistrarProducto);
        bottomAppBar = findViewById(R.id.bottomAppBar);
        txtCantidad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtCantidad.setFocusable(true);
            }
        });


        tipoProductos = new ArrayList<>();
        tipoProductos.addAll(Arrays.asList(TypeProductsConstants.values()));


        listProductos = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,tipoProductos);
        listProductos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        selectProducto.setAdapter(listProductos);


    }

    public void setDate(LocalDate df,int year,int month,int day){
        this.localDate = df;
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public void validateBtn(){
        Boolean emptyDate = selectDate.getText().toString().isEmpty();
        Boolean emptyCantidad = txtCantidad.getText().toString().isEmpty();
        Boolean emptyProducto = selectProducto.getText().toString().isEmpty();
        Boolean lessCantidadToZero = emptyCantidad ? true : Double.parseDouble(txtCantidad.getText().toString()) < 1;



        if(emptyDate || emptyCantidad || emptyProducto || lessCantidadToZero){
            btnRegistrarProducto.setEnabled(false);

        }else{
            btnRegistrarProducto.setEnabled(true);
        }
    }

    private void openDatePicker(){


        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                LocalDate df = LocalDate.of(year,month,dayOfMonth);

                setDate(df,year,month+1,dayOfMonth);

                LocalDate dfText = LocalDate.of(year,month+1,dayOfMonth);

                selectDate.setText(dfText.toString());
                validateBtn();
            }
        }, year, month, day);

        dialog.show();
    }

    private void resetFields(){
        selectDate.setText(null);
        txtCantidad.setText(null);
        selectProducto.setText(null);

        txtCantidad.clearFocus();


    }
}