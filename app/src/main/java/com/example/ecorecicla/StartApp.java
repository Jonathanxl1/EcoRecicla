package com.example.ecorecicla;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.ecorecicla.Views.LoginView;

public class StartApp extends AppCompatActivity {

    /****** Create Thread that will sleep for 5 seconds****/
    Thread background = new Thread() {
        public void run() {
            try {
                // Thread will sleep for 5 seconds
                sleep(1*3000);

                // After 5 seconds redirect to another intent
                Intent i=new Intent(getBaseContext(), LoginView.class);
                startActivity(i);

                //Remove activity
                finish();
            } catch (Exception e) {
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        background.start();

    }
}