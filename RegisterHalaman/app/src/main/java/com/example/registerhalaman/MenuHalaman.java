package com.example.registerhalaman;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.QuickContactBadge;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class MenuHalaman extends AppCompatActivity {

    Button btnNilai, btnAgenda, btnLogout;
    // setiap activity setelah login akan menerima nilai email,
    // agar bisa jadi kaya PK untuk konten selanjutnya
    String email;
    String url = "http://192.168.1.7/xampp/MobileApps/logout.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_halaman);

        this.setTitle("Menu Utama");

        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        Toast.makeText(MenuHalaman.this, email.toString(), Toast.LENGTH_LONG).show();

        btnNilai = (Button) findViewById(R.id.btnNilai);
        btnNilai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MenuHalaman.this, NilaiHalaman.class);
                intent1.putExtra("email", email);
                startActivity(intent1);
            }
        });
        btnAgenda = (Button) findViewById(R.id.btnAgenda);
        btnAgenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MenuHalaman.this, ListAgenda.class);
                intent1.putExtra("email", email);
                startActivity(intent1);
            }
        });
        btnLogout = (Button) findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btnLogout:
                        final Intent intent = new Intent(MenuHalaman.this, Utama.class);

                        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        startActivity(intent);
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(MenuHalaman.this, error.toString(), Toast.LENGTH_SHORT).show();
                                    }
                                }) {

                            @Override
                            public Map<String, String> getHeaders() throws AuthFailureError {
                                Map<String,String> params = new HashMap<>();
                                params.put("Content-Type", "application/x-www-form-urlencoded");
                                return params;
                            }
                        };

                        RequestQueue requestQueue = Volley.newRequestQueue(MenuHalaman.this);
                        requestQueue.add(stringRequest);
                }
            };
        });
    }
}