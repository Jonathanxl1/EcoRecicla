package com.example.ecorecicla.Views;

import com.anychart.APIlib;
import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Column;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.Anchor;
import com.anychart.enums.MarkerType;
import com.anychart.enums.TooltipPositionMode;
import com.anychart.graphics.vector.Stroke;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ScrollView;
import android.widget.Toast;


import com.example.ecorecicla.Constants.TypeProductsConstants;
import com.example.ecorecicla.Controllers.DataAdministrator;
import com.example.ecorecicla.Models.EstadisticaModel;
import com.example.ecorecicla.Models.ProductoReciclajeModel;
import com.example.ecorecicla.R;
import com.google.android.material.textfield.TextInputLayout;

import java.io.FileNotFoundException;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.EnumMap;
import java.util.List;

public class EstadisticasAllView extends AppCompatActivity {

    private EstadisticaModel estadisticaModel;
    private SharedPreferences preferences;
    private Integer userIdref;

    private DataAdministrator dataAdministrator;

    private TypeProductsConstants AGUA,PAPEL, ENERGIA,PLASTICO;

    private AutoCompleteTextView atvSelectMonth;
    final Calendar c = Calendar.getInstance();

    private Month selectedMonth = Month.of(c.get(Calendar.MONTH));

    private AnyChartView chartAgua,chartEnergia,chartPlastico,chartPapel;

    private ScrollView scrollCharts;

    private TextInputLayout inputMonth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estadisticas_all);

        chartAgua = findViewById(R.id.chartAgua);
        chartEnergia = findViewById(R.id.chartEnergia);
        chartPapel = findViewById(R.id.chartPapel);
        chartPlastico = findViewById(R.id.chartPlastico);


        scrollCharts = findViewById(R.id.scrollCharts);
        atvSelectMonth = findViewById(R.id.selectMonth);
        inputMonth = findViewById(R.id.inputMonth);

        AGUA = TypeProductsConstants.AGUA;
        PAPEL = TypeProductsConstants.PAPEL;
        ENERGIA = TypeProductsConstants.ENERGIA;
        PLASTICO = TypeProductsConstants.PLASTICO;




        preferences = getApplicationContext().getSharedPreferences("dataUser",MODE_PRIVATE);
        userIdref = preferences.getInt("userIdRef",-1);
        dataAdministrator = new DataAdministrator(estadisticaModel,getApplicationContext());



        try {
            estadisticaModel = dataAdministrator.getEstadisticaModel(userIdref);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }


        Boolean existsValuesMonths = estadisticaModel.getMesesDisponibles().size() > 0;

        ArrayList arrMonths = new ArrayList<>();

        if(existsValuesMonths){
            scrollCharts.setVisibility(View.VISIBLE);
            inputMonth.setEnabled(true);
            arrMonths.addAll(estadisticaModel.getMesesDisponibles());
            ArrayAdapter adaterMonths = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item,arrMonths);
            atvSelectMonth.setAdapter(adaterMonths);

        }else{
            inputMonth.setEnabled(false);
        }





        atvSelectMonth.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        selectedMonth = (Month) parent.getItemAtPosition(position);
                        putChartByMonth(selectedMonth);

            }
        }
        );



    }

    private void putChartByMonth(Month monthSelected) {

       for(TypeProductsConstants type : TypeProductsConstants.values()){
            setChartByType(type,monthSelected);
        }


    }

    private void setChartByType(TypeProductsConstants type, Month monthType) {
            AnyChartView anyChartView ;
            String colorStroke = "";
            switch (type) {
                case AGUA: {
                    anyChartView =  chartAgua;
                    colorStroke = "blue";
                    APIlib.getInstance().setActiveAnyChartView(anyChartView);

                    break;
                }
                case PAPEL: {
                    anyChartView = chartPapel;
                    colorStroke = "red";
                    APIlib.getInstance().setActiveAnyChartView(anyChartView);

                    break;
                }
                case ENERGIA: {
                    anyChartView = chartEnergia;
                    colorStroke = "orange";
                    APIlib.getInstance().setActiveAnyChartView(anyChartView);

                    break;
                }
                case PLASTICO: {
                    anyChartView = chartPlastico;
                    colorStroke = "yellow";
                    APIlib.getInstance().setActiveAnyChartView(anyChartView);

                    break;
                }

                default:
                    anyChartView = chartAgua;
                    colorStroke = "black";
                    APIlib.getInstance().setActiveAnyChartView(anyChartView);


            }

            Cartesian cartesian = AnyChart.column();
            cartesian.animation(true);

            cartesian.padding(10d, 20d, 5d, 20d);

            cartesian.crosshair().enabled(true);
            cartesian.crosshair()
                    .yLabel(true)
                    // TODO ystroke
                    .yStroke((Stroke) null, null, null, (String) null, (String) null);

            cartesian.tooltip().positionMode(TooltipPositionMode.POINT);

            cartesian.title("Estadistica segun el Mes");

            cartesian.yAxis(0).title("Cantidad");
            cartesian.xAxis(0).labels().padding(5d, 5d, 5d, 5d);


            List<DataEntry> seriesData = new ArrayList<>();

            ArrayList<ProductoReciclajeModel> arrP = getArrMapProductsFilteredBy(monthType).get(type);

            for (ProductoReciclajeModel p : arrP) {
                seriesData.add(new CustomDataEntry(p.getDay(), p.getQuantity()));
            }


            final Set set = Set.instantiate();
            set.data(seriesData);

            Mapping seriesMapping = set.mapAs("{ x: 'x', value: 'value' }");


            Column serie = cartesian.column(seriesMapping);
            serie.name(type.getTypeProducto().toLowerCase());
            serie.hovered().markers().enabled(true);
            serie.hovered().markers()
                    .type(MarkerType.CIRCLE)
                    .size(4d);
            serie.tooltip()
                    .position("right")
                    .anchor(Anchor.LEFT_CENTER)
                    .offsetX(5d)
                    .offsetY(5d);
            serie.stroke(colorStroke);

            cartesian.legend().enabled(true);
            cartesian.legend().fontSize(13d);
            cartesian.legend().padding(0d, 0d, 10d, 0d);

            anyChartView.setChart(cartesian);




    }

    private class CustomDataEntry extends ValueDataEntry {

        CustomDataEntry(int x, Double value) {
            super(x, value);

        }

    }

    private EnumMap<TypeProductsConstants,ArrayList<ProductoReciclajeModel>> getArrMapProductsFilteredBy(Month monthFilter){
        return estadisticaModel.getMapArrProductsTypeByMonth(monthFilter);
    }


}
