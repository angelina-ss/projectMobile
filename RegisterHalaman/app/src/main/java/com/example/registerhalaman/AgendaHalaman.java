package com.example.registerhalaman;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

public class AgendaHalaman extends AppCompatActivity {

    // setiap activity setelah login akan menerima nilai email,
    // agar bisa jadi kaya PK untuk konten selanjutnya
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda_halaman);

        this.setTitle("Agenda");

        Intent intent = getIntent();
        email = intent.getStringExtra("email");

        FragmentCalendar frag1 = new FragmentCalendar();
        frag1.setArguments(getIntent().getExtras());

        FargmentNote frag2 = new FargmentNote();
        frag2.setArguments(getIntent().getExtras());

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.add(R.id.frame1,frag1);
        fragmentTransaction.add(R.id.frame2,frag2);
        fragmentTransaction.commit();

    }
}
