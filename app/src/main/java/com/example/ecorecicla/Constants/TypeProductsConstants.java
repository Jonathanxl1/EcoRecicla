package com.example.ecorecicla.Constants;

public enum TypeProductsConstants {
    AGUA("Agua"),
    PLASTICO("Plastico"),
    ENERGIA("Energia"),
    PAPEL("Papel");

    private String typeProducto;
    TypeProductsConstants(String typeProducto) {
        this.typeProducto = typeProducto;
    }

    @Override
    public String toString() {
        return this.getTypeProducto();
    }

    public String getTypeProducto() {
        return this.typeProducto;
    }
}
