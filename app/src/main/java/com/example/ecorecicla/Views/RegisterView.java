package com.example.ecorecicla.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ecorecicla.R;
import com.example.ecorecicla.Models.UsuarioModel;

public class RegisterView extends AppCompatActivity {

    private Button btnRegistrar;
    private UsuarioModel usuarioModel;
    private EditText txNombre,txEmail,txConfirmPassword,txPassword;

    private String name,email,password,confirmPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_view);
        init();
            btnRegistrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    getData();
                    if(password.isEmpty() || !confirmPassword.equals(password)){
                        Toast.makeText(getApplicationContext(),"Verify the password",Toast.LENGTH_LONG).show();
                        return;
                    }
                    boolean registered = registerUser(1,name,email,password);
                    if(registered){
                        Toast.makeText(getApplicationContext(),"Success to saved User",Toast.LENGTH_LONG).show();
                        Intent mainpage = new Intent(getApplicationContext(), MainView.class);
                            startActivity(mainpage);
                    }else{
                        Toast.makeText(getApplicationContext(),"Failed to save User",Toast.LENGTH_LONG).show();
                    }
                }
            });

    }

    private void init(){
        btnRegistrar = findViewById(R.id.btnRegistarUsuarios);
        txNombre = findViewById(R.id.txNombre);
        txEmail = findViewById(R.id.txEmail);
        txConfirmPassword = findViewById(R.id.txConfirmPassword);
        txPassword = findViewById(R.id.txPassword);
    }

    private void getData(){
        this.name = txNombre.getText().toString();
        this.email = txEmail.getText().toString();
        this.confirmPassword = txConfirmPassword.getText().toString();
        this.password = txPassword.getText().toString();
    }

    private Boolean registerUser(int id,String name,String email,String password){
             usuarioModel = new UsuarioModel(id,name,email,password);
             return usuarioModel.validateUserModel();
    }
}