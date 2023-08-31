package com.example.ecorecicla.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.ecorecicla.R;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.JsonArray;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainView extends AppCompatActivity {
    private FloatingActionButton addProduct;
    private BottomAppBar bottomAppBar;
    private Toolbar topBar;

    private Button btnRightConsejo,btnLeftConsejo;
    private TextView txvConsejos;

    private Integer positionConsejo = 0;
    private Gson gson = new Gson();
    private JsonArray jsonArray;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal_view);
        init();
        bottomBarConfig();

        setConsejosView();

        setRandomConsejo();

        btnRightConsejo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                positionConsejo++;
                if (positionConsejo >= jsonArray.size()) {
                    positionConsejo = 0;
                }
                setConsejoByPosition(positionConsejo);
            }
        });

        btnLeftConsejo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                positionConsejo--;
                if (positionConsejo < 0) {
                    positionConsejo = jsonArray.size() - 1;
                }
                setConsejoByPosition(positionConsejo);
            }
        });


    }

    private void setConsejosView() {
        InputStream inputStream = getResources().openRawResource(R.raw.consejos);

        String jsonContent = readJsonFromInputStream(inputStream);

        jsonArray = gson.fromJson(jsonContent, JsonArray.class);
    }

    private void init() {
        addProduct = findViewById(R.id.addProduct);
        bottomAppBar = findViewById(R.id.bottomAppBar);
        topBar = findViewById(R.id.topAppBar);
        btnLeftConsejo = findViewById(R.id.leftConsejo);
        btnRightConsejo = findViewById(R.id.rightConsejo);
        txvConsejos = findViewById(R.id.txvConsejos);
    }
    private void bottomBarConfig() {
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



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveTaskToBack(true);
    }


    private void setConsejoByPosition(int position) {
        String mensaje = jsonArray.get(position).getAsJsonObject().get("consejo").getAsString();
        txvConsejos.setText("\"" + mensaje + "\"");
    }

    private String readJsonFromInputStream(InputStream inputStream) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    private void setRandomConsejo() {
        positionConsejo = (int) Math.round(Math.random() * (jsonArray.size() - 1));
        setConsejoByPosition(positionConsejo);
    }

}