package com.example.ecorecicla.Models;


import android.content.Context;

import com.example.ecorecicla.Controllers.DataAdministrator;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

public class UsuarioModel {

    @Expose private Integer id = 0;
    @Expose private String name;
    @Expose private String email;
    @Expose private String password;

    private Integer id = 0;
    private String name;
    private String email;
    private String password;

   @Expose(serialize = false,deserialize = false)
   private EstadisticaModel estadisticaModel;


    public UsuarioModel( String name, String email,String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        estadisticaModel = new EstadisticaModel(id);
    }

    public UsuarioModel(int id, String name, String email, String password, Context context) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        estadisticaModel = new EstadisticaModel(id);
        DataAdministrator estadisticasAdministrador = new DataAdministrator(estadisticaModel,context);
        estadisticasAdministrador.createEstadisticaModel();
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }


    public String getPassword() {
        return password;
    }

    public Boolean validateUserModel(){
        if(name.isEmpty() || email.isEmpty() || password.isEmpty()){
            return false;
        }else{
            return true;
        }
    }

    public String getUserModelJson () {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        return (String) gson.toJson(this);

    }









}
