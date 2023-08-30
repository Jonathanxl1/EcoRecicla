package com.example.ecorecicla.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ecorecicla.Controllers.DataAdministrator;
import com.example.ecorecicla.Models.UsuarioModel;
import com.example.ecorecicla.R;

public class LoginView extends AppCompatActivity {

    private Button btnLogin ;
    private Button btnRegistro;

    private EditText emailText, passwordText;
    private String email,password;


    private DataAdministrator dataAdministrator;
    private UsuarioModel usuarioModel;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_view);
        init();
        getData();



        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer validateLogin = dataAdministrator.validateLoginUser(email,password);
                if(validateLogin > -1){
                    Intent mainPage = new Intent(getBaseContext(), MainView.class);
                    //mainPage.putExtra("USER_ID_REF",validateLogin);
                    SharedPreferences preferences = getApplicationContext().getSharedPreferences("dataUser",MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putInt("userIdRef",validateLogin);
                    editor.commit();
                    startActivity(mainPage);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(),"Invalid Log In, check User and/or Password",Toast.LENGTH_LONG).show();
                }


            }
        });
        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registro = new Intent(getBaseContext(), RegisterView.class);
                startActivity(registro);
            }
        });
    }

    public void init(){
        emailText = findViewById(R.id.emailUser);
        passwordText = findViewById(R.id.passwordUser);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegistro = findViewById(R.id.registroUsers);
        dataAdministrator = new DataAdministrator(usuarioModel,getApplicationContext());
        validateBtn();
    }

    public void getData(){


        emailText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                email = emailText.getText().toString();
                validateBtn();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        passwordText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                password = passwordText.getText().toString();
                validateBtn();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    public void validateBtn(){
        if(email == null || password == null || email.isEmpty() || password.isEmpty()){
            btnLogin.setEnabled(false);
        }else{
            btnLogin.setEnabled(true);
        }
    }


}