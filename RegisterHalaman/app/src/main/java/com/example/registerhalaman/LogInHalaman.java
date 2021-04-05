package com.example.registerhalaman;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class LogInHalaman extends AppCompatActivity {



    public static final String LOGIN_URL = "http://192.168.1.7/xampp/MobileApps/loginUser.php";

    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";
    String email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_halaman);

        this.setTitle("Log In");

        final EditText edtEmail1 = findViewById(R.id.edtEmail1);
        final EditText edtPassword1 = findViewById(R.id.edtPassword1);

        Button btnOK;
        btnOK = (Button) findViewById(R.id.btnOk);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = edtEmail1.getText().toString().trim();
                password = edtPassword1.getText().toString().trim();

                final Intent intent = new Intent(LogInHalaman.this, MenuHalaman.class);
                intent.putExtra("email", edtEmail1.getText().toString());

                StringRequest stringRequest = new StringRequest(Request.Method.POST, LOGIN_URL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if (response.trim().equals("Anda berhasil login")) {
                                    startActivity(intent);
                                    Toast.makeText(LogInHalaman.this, response, Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Toast.makeText(LogInHalaman.this, response, Toast.LENGTH_SHORT).show();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(LogInHalaman.this, error.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }) {

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> params = new HashMap<>();
                        params.put("email", email);
                        params.put("password", password);
                        return params;
                    }

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String,String> params = new HashMap<>();
                        params.put("Content-Type", "application/x-www-form-urlencoded");
                        return params;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(LogInHalaman.this);
                requestQueue.add(stringRequest);



            }
        });

    }


}