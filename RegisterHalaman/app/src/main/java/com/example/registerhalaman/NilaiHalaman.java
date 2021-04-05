package com.example.registerhalaman;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class NilaiHalaman extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Button btnUts, btnUas, btnTryOut, btnUsbn, btnUnbk;
    Spinner spinnerTahunAjar;
    String tahunAjar[] = {"- Pilih Tahun Ajaran -",
            "2017/2018", "2018/2019", "2019/2020"};
    // setiap activity setelah login akan menerima nilai email,
    // agar bisa jadi kaya PK untuk konten selanjutnya
    String email;
    String pilihanTA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nilai_halaman);

        this.setTitle("Nilai Siswa");

        final Intent intent = getIntent();
        email = intent.getStringExtra("email");
        Toast.makeText(NilaiHalaman.this, email, Toast.LENGTH_LONG).show();

        spinnerTahunAjar = (Spinner) findViewById(R.id.spinnerUjian);
        spinnerTahunAjar.setOnItemSelectedListener(this);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, tahunAjar);
        spinnerTahunAjar.setAdapter(adapter);
        String pilihanTahunAjar = spinnerTahunAjar.getSelectedItem().toString();

        btnUts = (Button) findViewById(R.id.btnUts);
        btnUas = (Button) findViewById(R.id.btnUas);
        btnTryOut = (Button) findViewById(R.id.btnTryOut);
        btnUsbn = (Button) findViewById(R.id.btnUsbn);
        btnUnbk = (Button) findViewById(R.id.btnUnbk);

        //intent untuk menuju halaman yang menampilkan nilai siswa
        //sesuai dengan jenis ujian dan periode T.A. ujian
        final Intent intent1 = new Intent(NilaiHalaman.this, MenuPeriode.class);

        btnUts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String periode_nilai = "UTS" + pilihanTA;
                intent1.putExtra("email", email);
                intent1.putExtra("periode_nilai", periode_nilai);
                startActivity(intent1);
            }
        });

        btnUas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String periode_nilai = "UAS" + pilihanTA;
                intent1.putExtra("email", email);
                intent1.putExtra("periode_nilai", periode_nilai);
                startActivity(intent1);
            }
        });

        //lanjut buat button TryOut, Usbn, Unbk
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        pilihanTA = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
