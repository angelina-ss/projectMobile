package com.example.registerhalaman;

//import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Utama extends AppCompatActivity {

    Button Register, Login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_utama);
        Register = (Button) findViewById(R.id.btnRegister);
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentR = new Intent(Utama.this, RegisterHalaman.class);
                startActivity(intentR);
            }
        });
        Login = (Button) findViewById(R.id.btnLogin);
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentL = new Intent(Utama.this, LogInHalaman.class);
                startActivity(intentL);
            }
        });
    }
}