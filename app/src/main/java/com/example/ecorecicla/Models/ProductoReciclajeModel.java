package com.example.ecorecicla.Models;

import com.example.ecorecicla.Constants.TypeProductsConstants;

import java.time.LocalDate;

public class ProductoReciclajeModel {

    private TypeProductsConstants typeProduct ;
    private Double quantity;


    private int year,month,day;

    public ProductoReciclajeModel(TypeProductsConstants typeProduct, Double quantity, LocalDate date) {
        this.typeProduct = typeProduct;
        this.quantity = quantity;
        this.year = date.getYear();
        this.month = date.getMonthValue();
        this.day = date.getDayOfMonth();
    }


    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public String getTypeProduct() {
        return typeProduct.toString();
    }


    public Double getQuantity() {
        return quantity;
    }

}
