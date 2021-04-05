package com.example.registerhalaman;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
//import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

// new empty activity untuk splash screen
public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        // buat menghilangkan judul aplikasi
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        // buat menutup splash screen setelah 5000 milidetik
        Thread thread = new Thread() {
            public void run() {
                try {
                    sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    startActivity(new Intent(SplashScreen.this, Utama.class));
                    finish();
                }
            }
        };
        thread.start();
    }
}
