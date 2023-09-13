package com.example.ecorecicla.Models;


import com.example.ecorecicla.Constants.TypeProductsConstants;

import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

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

    public EnumMap<TypeProductsConstants,ArrayList<ProductoReciclajeModel>> getMapArrProductsByType(){
        EnumMap<TypeProductsConstants,ArrayList<ProductoReciclajeModel> > filteresProduct = new EnumMap<>(TypeProductsConstants.class);
        // Agua []
        // PAPEL []

        for(TypeProductsConstants type: TypeProductsConstants.values()){
            filteresProduct.put(type,new ArrayList<ProductoReciclajeModel>());
        }

        Comparator sortByDate = new Comparator<ProductoReciclajeModel>() {
            @Override
            public int compare(ProductoReciclajeModel o1, ProductoReciclajeModel o2) {
                return o1.getDay() - o2.getDay();
            }
        };

        for(ProductoReciclajeModel product: getArrProductosReciclados()){
            for(TypeProductsConstants type: TypeProductsConstants.values()){
                if(product.getTypeProduct() == type.getTypeProducto()){
                    filteresProduct.get(type).add(product);
                    filteresProduct.get(type).sort(sortByDate);
                }
            }
        }

        return filteresProduct;


    }

    public EnumMap<TypeProductsConstants,Double> getMapTotalProductsByType(){
        //Suma total de elementos de cada tipo
        // Agua 200
        //Papel 300
        EnumMap<TypeProductsConstants,Double> filteredTotalByType = new EnumMap<>(TypeProductsConstants.class);

        for(Map.Entry typeProduct :getMapArrProductsByType().entrySet()){
           Double suma = 0.0;
           ArrayList<ProductoReciclajeModel> arrProduct = (ArrayList)typeProduct.getValue();
           for(ProductoReciclajeModel product : arrProduct){
               suma += product.getQuantity();
           }
           filteredTotalByType.put(((TypeProductsConstants)typeProduct.getKey()),suma);
        }

        return filteredTotalByType;
    }

    public EnumMap<TypeProductsConstants,ArrayList<ProductoReciclajeModel>> getMapArrProductsTypeByMonth(Month monthToFilter){
        EnumMap<TypeProductsConstants,ArrayList<ProductoReciclajeModel>> filteredMapByMonths = new EnumMap<>(TypeProductsConstants.class);

        for(TypeProductsConstants type:TypeProductsConstants.values()){
            filteredMapByMonths.put(type,new ArrayList<ProductoReciclajeModel>());
        }

        for(Map.Entry category: getMapArrProductsByType().entrySet()){
            ArrayList<ProductoReciclajeModel> filteredByMonths = new ArrayList<>();
            for(TypeProductsConstants type:TypeProductsConstants.values()){
                if(category.getKey() == type){
                    for(ProductoReciclajeModel p: ((ArrayList<ProductoReciclajeModel>)category.getValue())){
                        if(p.getMonth()== monthToFilter.ordinal()){
                            filteredByMonths.add(p);
                        }
                    }
                }
            }
            filteredMapByMonths.put(((TypeProductsConstants) category.getKey()),filteredByMonths);
        }

        return filteredMapByMonths;

    }

    public ArrayList<Month> getMesesDisponibles(){
        EnumMap<TypeProductsConstants,ArrayList<ProductoReciclajeModel>> arrProducts = getMapArrProductsByType();
        ArrayList<Month> ableMonths = new ArrayList<>();
        for(Map.Entry category:arrProducts.entrySet()){
            for(ProductoReciclajeModel p : ((ArrayList<ProductoReciclajeModel>)category.getValue())){
                if(!ableMonths.contains(Month.of(p.getMonth()))){
                    ableMonths.add(Month.of(p.getMonth()+1));
                }
            }
        }
        Set<Month> filter = new LinkedHashSet<>(ableMonths);
        ArrayList<Month> filteredMonths  = new ArrayList<>(filter);
        Collections.sort(filteredMonths);
        return filteredMonths;
    }
}
