package com.example.registerhalaman;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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

public class RegisterHalaman extends AppCompatActivity {

    ProgressBar progressBar;
    EditText edtNik, edtEmail, edtPassword;
    Button btnSubmit;
    String id_siswa, email, password;
    String url = "http://192.168.1.7/xampp/MobileApps/addUser.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_halaman);

        this.setTitle("Sign Up");

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        edtNik = (EditText) findViewById(R.id.edtNik);
        edtNik.requestFocus();

        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtPassword = (EditText) findViewById(R.id.edtPassword);

        btnSubmit = (Button)findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                final EditText edtNik = findViewById(R.id.edtNik);
                id_siswa = edtNik.getText().toString();

//                final EditText edtEmail = findViewById(R.id.edtEmail);
                email = edtEmail.getText().toString();

//                final  EditText edtPassword = findViewById(R.id.edtPassword);
                password = edtPassword.getText().toString();

                RequestQueue queue = Volley.newRequestQueue(RegisterHalaman.this);

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int sukses = jsonObject.getInt("success");
                            if (sukses == 1) {
                                Toast.makeText(RegisterHalaman.this, "Anda Berhasil Mendaftar!", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(RegisterHalaman.this, "Pendaftaran Anda belum Berhasil!", Toast.LENGTH_SHORT).show();
                            }
                            progressBar.setVisibility(View.GONE);
                        } catch (Exception ex) {
                            Log.e("Error", ex.toString());
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error", error.getMessage());
                        Toast.makeText(RegisterHalaman.this, "Silahkan Cek Koneksi Internet Anda", Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();
                        params.put("id_siswa", id_siswa);
                        params.put("email", email);
                        params.put("password", password);
                        return params;
                    }

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("Content-Type", "application/x-www-form-urlencoded");
                        return params;
                    }
                };
                queue.getCache().clear();
                queue.add(stringRequest);
                Intent intent = new Intent(RegisterHalaman.this, LogInHalaman.class);
//                intent.putExtra("id_siswa", edtNik.getText().toString());
                intent.putExtra("email", edtEmail.getText().toString());
                startActivity(intent);
            }
//            Intent intent = new Intent(RegisterHalaman.this, LogInHalaman.class);
////            intent.putExtra("NIK", Nomor);
////            intent.putExtra("Email", Email);
////            intent.putExtra("Password", Password);
//            startActivity(intent);
        });
    }
}