package com.example.ecorecicla.Models;


import java.util.ArrayList;

public class EstadisticaModel {

    private int userIdRef;


    private ArrayList <Double> promedioProductos = new ArrayList<>();




    private ArrayList<ProductoReciclajeModel> arrProductosReciclados = new ArrayList<>();



    public EstadisticaModel(int primaryKey) {
        this.userIdRef = primaryKey;
    }


    public int getPrimaryKey() {
        return userIdRef;
    }

    public ArrayList<ProductoReciclajeModel> getArrProductosReciclados() {
        return arrProductosReciclados;
    }

    public Double getPromedioProductos() {
        int counter = 0;
        Double quantityPromedio = 0.0;
        for(ProductoReciclajeModel producto : arrProductosReciclados){
            counter++;
            quantityPromedio += producto.getQuantity().doubleValue();
        }
        if(counter != 0.0){
            return quantityPromedio/ (double) counter;
        }

        return 0.0;

    }

    public void setArrProductosReciclados(ProductoReciclajeModel productosReciclados) {
        this.arrProductosReciclados.add(productosReciclados);
    }
}
