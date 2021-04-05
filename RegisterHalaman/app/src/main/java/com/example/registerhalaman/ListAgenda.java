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
//import android.support.v7.app.AppCompatActivity;
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

public class ListAgenda extends AppCompatActivity {

    // menampilkan seluruh agenda event sekolah
    ProgressBar progressBarAgenda;
    ListView listViewAgenda;
    ArrayList<HashMap<String,String>> list_notes;
    String url = "http://192.168.1.7/xampp/MobileApps/getSkor.php";
//    String url = "https://info-covid-19-info.000webhostapp.com/conn.php";

     String email;

     static final String TAG_NOTES="data";
     static String TAG_EMAIL;
     final String TAG_TANGGAL_EVENT="tanggal_event";
     final String TAG_DETAIL_EVENT="detail_event";

     @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_list_agenda);

         final Intent intent = getIntent();
         email = intent.getStringExtra("email");
         Toast.makeText(ListAgenda.this, email, Toast.LENGTH_SHORT).show();
         TAG_EMAIL = intent.getStringExtra("email");

         list_notes = new ArrayList<>();
         listViewAgenda = (ListView) findViewById(R.id.listViewAgenda);

         progressBarAgenda = (ProgressBar) findViewById(R.id.progressBarAgenda);
         progressBarAgenda.setVisibility(View.VISIBLE);
         RequestQueue queue = Volley.newRequestQueue(ListAgenda.this);

         StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
             @Override
             public void onResponse(String response) {
                 try {
                     JSONObject jsonObject = new JSONObject(response);
                     JSONArray notes = jsonObject.getJSONArray(TAG_NOTES);
                     for (int i = 0; i < notes.length(); i++) {
                         JSONObject jsonObject1 = notes.getJSONObject(i);
                         String tanggal_event = jsonObject1.getString(TAG_TANGGAL_EVENT);
                         String detail_event = jsonObject1.getString(TAG_DETAIL_EVENT);
                         HashMap<String, String> hashMap = new HashMap<>();
                         hashMap.put("tanggal_event", tanggal_event);
                         hashMap.put("detail_event", detail_event);
                         list_notes.add(hashMap);
                     }
                     JSONObject object = notes.getJSONObject(0);
                     ListAgenda.this.setTitle("AGENDA");
                     progressBarAgenda.setVisibility(View.GONE);
                     String[] from = {"tanggal_event", "detail_event"};
                     int[] to = {R.id.txtTanggal, R.id.txtDetail};
                     ListAdapter listAdapter = new SimpleAdapter(ListAgenda.this, list_notes, R.layout.list_agenda, from, to);
                     listViewAgenda.setAdapter(listAdapter);
                 } catch (Exception ex) {
                     Log.e("Error", ex.toString());
                     progressBarAgenda.setVisibility(View.GONE);
                 }
             }
             }, new Response.ErrorListener() {
             @Override
             public void onErrorResponse(VolleyError error) {
                 Log.e("Error", error.getMessage());
                 Toast.makeText(ListAgenda.this, "Silahkan Cek Koneksi Internet Anda", Toast.LENGTH_LONG).show();
                 finish();
             }
         }) {
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
