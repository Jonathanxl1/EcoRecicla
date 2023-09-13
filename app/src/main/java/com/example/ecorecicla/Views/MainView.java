package com.example.ecorecicla.Views;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Column;
import com.anychart.enums.Anchor;
import com.anychart.enums.HoverMode;
import com.anychart.enums.Position;
import com.anychart.enums.TooltipPositionMode;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.ecorecicla.Constants.TypeProductsConstants;
import com.example.ecorecicla.Controllers.DataAdministrator;
import com.example.ecorecicla.Models.EstadisticaModel;
import com.example.ecorecicla.R;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.JsonArray;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainView extends AppCompatActivity {
    private FloatingActionButton addProduct;
    private BottomAppBar bottomAppBar;
    private Toolbar topBar;

    private Button btnRightConsejo,btnLeftConsejo, btnDetallesEstadisticas;
    private TextView txvConsejos;

    private Integer positionConsejo = 0;
    private final Gson gson = new Gson();
    private JsonArray jsonArray;

    private AnyChartView chartAll;

    SharedPreferences preferences;

    private Integer userIdRef;

    private EstadisticaModel estadisticaModel;

    private DataAdministrator dataAdministrator;

    private TypeProductsConstants constAgua, constEnergia, constPapel, constPlastico;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal_view);
        init();

        setChartAllProductsRecycled();


        bottomBarConfig();

        setConsejosView();

        setRandomConsejo();

        btnDetallesEstadisticas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detailEstadistics = new Intent(getApplicationContext(), EstadisticasAllView.class);
                startActivity(detailEstadistics);
            }
        });

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

    private void setChartAllProductsRecycled() {
        Cartesian cartesian = AnyChart.column();

        List<DataEntry> data = new ArrayList<>();
        data.add(new ValueDataEntry(constAgua.getTypeProducto(),getTotalProductsByType(constAgua) ));
        data.add(new ValueDataEntry(constPapel.getTypeProducto(), getTotalProductsByType(constPapel)));
        data.add(new ValueDataEntry(constEnergia.getTypeProducto(), getTotalProductsByType(constEnergia)));
        data.add(new ValueDataEntry(constPlastico.getTypeProducto(), getTotalProductsByType(constPlastico)));


        Column column = cartesian.column(data);

        column.tooltip()
                .titleFormat("{%X}")
                .position(Position.CENTER_BOTTOM)
                .anchor(Anchor.CENTER_BOTTOM)
                .offsetX(0d)
                .offsetY(5d)
                .format("{%Value}{groupsSeparator: }");

        cartesian.animation(true);
        cartesian.title("Productos registrados total");

        cartesian.yScale().minimum(0d);

        cartesian.yAxis(0).labels().format("{%Value}{groupsSeparator: }");

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
        cartesian.interactivity().hoverMode(HoverMode.BY_X);

        cartesian.xAxis(0).title("Elementos");
        cartesian.yAxis(0).title("Cantidad");

        chartAll.setChart(cartesian);
    }

    private void setConsejosView() {
        try {
            InputStream inputStream = getResources().openRawResource(R.raw.consejos);

            String jsonContent = readJsonFromInputStream(inputStream);

            jsonArray = gson.fromJson(jsonContent, JsonArray.class);
        }catch (Resources.NotFoundException e){
            e.printStackTrace();
        }
    }

    

    private void init() {
        addProduct = findViewById(R.id.addProduct);
        bottomAppBar = findViewById(R.id.bottomAppBar);
        topBar = findViewById(R.id.topAppBar);
        btnLeftConsejo = findViewById(R.id.leftConsejo);
        btnRightConsejo = findViewById(R.id.rightConsejo);
        txvConsejos = findViewById(R.id.txvConsejos);
        btnDetallesEstadisticas = findViewById(R.id.detailEstadistics);
        chartAll = findViewById(R.id.chartAll);
        chartAll.setProgressBar(findViewById(R.id.progressBar));

        preferences = getApplicationContext().getSharedPreferences("dataUser",MODE_PRIVATE);
        userIdRef = preferences.getInt("userIdRef",-1);

        dataAdministrator = new DataAdministrator(estadisticaModel,getApplicationContext());

        constAgua = TypeProductsConstants.AGUA;
        constPapel = TypeProductsConstants.PAPEL;
        constPlastico = TypeProductsConstants.PLASTICO;
        constEnergia = TypeProductsConstants.ENERGIA;

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

    private Double getTotalProductsByType(TypeProductsConstants typeProductsConstants){
        try {
            return dataAdministrator.getEstadisticaModel(userIdRef).getMapTotalProductsByType().get(typeProductsConstants);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}