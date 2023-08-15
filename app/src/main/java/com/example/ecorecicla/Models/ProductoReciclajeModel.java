package com.example.ecorecicla.Models;

import com.example.ecorecicla.Constants.DateKeysConstants;
import com.example.ecorecicla.Constants.TypeProductsConstants;
import com.example.ecorecicla.Utils.DateStringFormat;

public class ProductoReciclajeModel {



    private DateKeysConstants DATETIME_KEYS;
    private TypeProductsConstants typeProduct ;
    private Double quantity;
    private String year,month,day;

    public ProductoReciclajeModel(TypeProductsConstants typeProduct, Double quantity, DateStringFormat date) {
        this.typeProduct = typeProduct;
        this.quantity = quantity;
        this.year = date.getDateTime().get(DATETIME_KEYS.YEAR.toString());
        this.month = date.getDateTime().get(DATETIME_KEYS.MONTH.toString());
        this.day = date.getDateTime().get(DATETIME_KEYS.DAY.toString());
    }

    public String getTypeProduct() {
        return typeProduct.toString();
    }

    public String getYear() {
        return year;
    }

    public String getMonth() {
        return month;
    }

    public String getDay() {
        return day;
    }

    public Double getQuantity() {
        return quantity;
    }

}
