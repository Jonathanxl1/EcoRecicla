package com.example.ecorecicla.Models;


import android.util.JsonWriter;

import androidx.annotation.NonNull;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;

public class UsuarioModel {


    private Integer id = 0;
    private String name;
    private String email;
    private String password;


    private EstadisticaModel estadisticaModel;


    public UsuarioModel( String name, String email,String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        estadisticaModel = new EstadisticaModel(id);
    }

    public UsuarioModel(int id, String name, String email,String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        estadisticaModel = new EstadisticaModel(id);
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
        Gson gson = new Gson();
        return (String) gson.toJson(this);

    }









}
