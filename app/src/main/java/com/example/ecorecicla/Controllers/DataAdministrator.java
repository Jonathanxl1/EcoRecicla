package com.example.ecorecicla.Controllers;

import android.content.Context;
import android.util.Log;

import com.example.ecorecicla.Models.EstadisticaModel;
import com.example.ecorecicla.Models.UsuarioModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class DataAdministrator {
    final private String USUARIOS_FILE = "Usuarios.json";
    final private String ESTADISTICAS_FILE = "Estadisticas.json";
    private File file ;
    private BufferedWriter bufferedWriter;
    private UsuarioModel userModel;
    private EstadisticaModel estadisticaModel;



    public DataAdministrator(UsuarioModel userModel, Context context) {
        this.file = new File(context.getFilesDir(),USUARIOS_FILE);
        this.userModel = userModel;
    }


    public void createFile(){
        try {
                bufferedWriter = writeOnFile();
                bufferedWriter.write("[]");
                bufferedWriter.close();
        }catch (Exception ex){
            Log.e("Error", "saveUser: "+ex);
        }
    }

    public Boolean existFile(){
        return file.exists();
    }

    public JSONArray readData(){
        try{
            FileReader fileReader = new FileReader(file.getAbsoluteFile());
            BufferedReader bufferReader = new BufferedReader(fileReader);
            String line = bufferReader.readLine();
            bufferReader.close();
            if(line.length() == 0){
                return new JSONArray();
            }else{
                JSONArray dataJson = new JSONArray(line);
                return dataJson;
            }

        }
        catch(Exception ex){
            Log.e("Error", ex.toString());
            return null;
        }
    }

    public BufferedWriter writeOnFile(){
        try {
            FileWriter fileWriter = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            return bufferedWriter;
        }catch (Exception ex){
            Log.e("Error", "writeOnFile: "+ex);
        }
        return bufferedWriter;
    }

    public Integer assignIdsUser(){
        if(!existFile()) {
            createFile();
        }
        JSONArray jsonArray = readData();
        return jsonArray.length();
    }

    public void saveUserData(){
        try {
            if(!existFile()) {
                createFile();
            }
            JSONArray jsonData = readData();
            String stringData = jsonData.toString();
            String prevData = stringData.substring(1,stringData.length()-1);
            String newData = userModel.getUserModelJson();
            bufferedWriter = writeOnFile();
            if(prevData.isEmpty()){
                bufferedWriter.write("["+newData+"]");
            }else{
                bufferedWriter.write("[" +prevData + ", " + newData + "]");
            }
            bufferedWriter.close();

        }catch (Exception ex){

        }
    }

    public Boolean validateRegisterUser(String email){
        try{
            JSONArray jsonArray = readData();

            for (int i = 0; i < jsonArray.length();i++) {
                JSONObject jsonObject = jsonArray.optJSONObject(i);
                if( jsonObject != null && jsonObject.has("email") && jsonObject.getString("email").equals(email)){
                    return false;
                }
            }
        }catch (JSONException e){
            e.printStackTrace();
        }

        return true;

    }


    public Boolean validateLoginUser(String email,String password){
        try {
            JSONArray jsonArray = readData();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.optJSONObject(i);
                if (jsonObject != null &&
                        jsonObject.has("email") && jsonObject.has("password") &&
                        jsonObject.getString("email").equals(email) && jsonObject.getString("password").equals(password)) {
                    return true;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return false;


    }

}
