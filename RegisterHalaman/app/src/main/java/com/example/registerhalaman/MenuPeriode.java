package com.example.registerhalaman;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MenuPeriode extends AppCompatActivity {

    ProgressBar progressBarSkor;
    TextView txtPeriodeNilai, txtNama, txtKelas;
    ListView listView;
    ArrayList<HashMap<String,String>> list_skor;
    String url = "http://192.168.1.7/xampp/MobileApps/getSkorByIdSiswa.php";
//    String url = "https://info-covid-19-info.000webhostapp.com/conn.php";

    // setiap activity setelah login akan menerima nilai email,
    // agar bisa jadi kaya PK untuk konten selanjutnya
    String email;
    String periode_nilai;

    static final String TAG_SISWA="data";
    static String TAG_EMAIL;
    static String TAG_PERIODE_NILAI;
    static final String TAG_NAMA="nama";
    static final String TAG_KELAS="kelas";
    static final String TAG_SUBJEK="subjek";
    static final String TAG_SKOR="skor";
    static final String TAG_GRADE="grade";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_periode);

        final Intent intent = getIntent();
        email = intent.getStringExtra("email");
        periode_nilai = intent.getStringExtra("periode_nilai");
        Toast.makeText(MenuPeriode.this, email+" "+periode_nilai, Toast.LENGTH_SHORT).show();
        TAG_EMAIL = intent.getStringExtra("email");
        TAG_PERIODE_NILAI = intent.getStringExtra("periode_nilai");

        txtNama = (TextView) findViewById(R.id.txtNama);
        txtKelas = (TextView) findViewById(R.id.txtKelas);

        list_skor = new ArrayList<>();
        listView = (ListView) findViewById(R.id.listView);

        progressBarSkor = (ProgressBar) findViewById(R.id.progressBarSkor);
        progressBarSkor.setVisibility(View.VISIBLE);
        RequestQueue queue = Volley.newRequestQueue(MenuPeriode.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray siswa = jsonObject.getJSONArray(TAG_SISWA);
                    for (int i = 0; i < siswa.length(); i++) {
                        JSONObject jsonObject1 = siswa.getJSONObject(i);
                        String nama = jsonObject1.getString(TAG_NAMA);
                        String kelas = jsonObject1.getString(TAG_KELAS);
                        String subjek = jsonObject1.getString(TAG_SUBJEK);
                        String skor = jsonObject1.getString(TAG_SKOR);
                        String grade = jsonObject1.getString(TAG_GRADE);
                        HashMap<String, String> hashMap = new HashMap<>();
//                        hashMap.put("email", email);
//                        hashMap.put("periode_nilai", periode_nilai);
                        hashMap.put("nama", nama);
                        hashMap.put("kelas", kelas);
                        hashMap.put("subjek", subjek);
                        hashMap.put("skor", skor);
                        hashMap.put("grade", grade);
                        list_skor.add(hashMap);
                    }
                    JSONObject object = siswa.getJSONObject(0);
                    MenuPeriode.this.setTitle(periode_nilai);
                    String nama = object.getString(TAG_NAMA);
                    txtNama.setText(nama);
                    String kelas = object.getString(TAG_KELAS);
                    txtKelas.setText(kelas);
                    progressBarSkor.setVisibility(View.GONE);
                    String[] from = {"subjek", "skor", "grade"};
                    int[] to = {R.id.txtSubjek, R.id.txtSkor, R.id.txtGrade};
                    ListAdapter listAdapter = new SimpleAdapter(MenuPeriode.this, list_skor, R.layout.list_skor_siswa, from, to);
                    listView.setAdapter(listAdapter);
                } catch (Exception ex) {
                    Log.e("Error", ex.toString());
                    progressBarSkor.setVisibility(View.GONE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.getMessage());
                Toast.makeText(MenuPeriode.this, "Silahkan Cek Koneksi Internet Anda", Toast.LENGTH_LONG).show();
                finish();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> params = new HashMap<>();
                params.put("email", email);
                params.put("periode_nilai", periode_nilai);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };
//        queue.getCache().clear();
        queue.add(stringRequest);
    }
}
