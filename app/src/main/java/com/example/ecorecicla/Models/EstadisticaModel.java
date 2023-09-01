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



    public void setArrProductosReciclados(ProductoReciclajeModel productosReciclados) {
        this.arrProductosReciclados.add(productosReciclados);
    }
}
