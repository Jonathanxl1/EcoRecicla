package com.example.ecorecicla.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.ecorecicla.Controllers.DataAdministrator;
import com.example.ecorecicla.Models.EstadisticaModel;
import com.example.ecorecicla.R;

import java.io.FileNotFoundException;

public class EstadisticasAllView extends AppCompatActivity {

    private EstadisticaModel estadisticaModel;
    private SharedPreferences preferences;
    private Integer userIdref;

    private DataAdministrator dataAdministrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estadisticas_all);
        preferences = getApplicationContext().getSharedPreferences("dataUser",MODE_PRIVATE);
        userIdref = preferences.getInt("userIdRef",-1);
        dataAdministrator = new DataAdministrator(estadisticaModel,getApplicationContext());
        try {
            estadisticaModel = dataAdministrator.getEstadisticaModel(userIdref);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}