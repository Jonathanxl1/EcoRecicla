package com.example.ecorecicla.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.ecorecicla.R;

public class LoginView extends AppCompatActivity {

    private Button btnLogin ;
    private Button btnRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_view);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegistro = findViewById(R.id.registroUsers);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainPage = new Intent(getBaseContext(), MainView.class);
                startActivity(mainPage);
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
}