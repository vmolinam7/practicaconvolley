package com.example.practicaconvolley;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    TextView txtnuevo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtnuevo = (TextView) findViewById(R.id.txtmostrar);
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://api.uealecpeterson.net/public/productos/search";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String listapro="";
                        try {
                            JSONObject lstProducts= new JSONObject(response);
                            JSONArray JSONlista = lstProducts.getJSONArray("productos");
                            for(int i=0; i< JSONlista.length();i++) {
                                JSONObject producto = JSONlista.getJSONObject(i);
                                listapro +=
                                        "id: " + producto.getString("id").toString() + "\n" +
                                        "Precio: " + producto.getString("precio_unidad").toString()+ "\n"+
                                        "Descripcion: " + producto.getString("descripcion").toString() +"\n"+
                                        "Barcode: " + producto.getString("barcode").toString()+ "\n"
                                ;;
                            }
                            txtnuevo.setText(listapro);
                        }catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, error -> Toast.makeText(getApplicationContext(),  error.getMessage(), Toast.LENGTH_LONG).show()) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headerMap = new HashMap<String, String>();
                headerMap.put("Authorization",
                        "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZHVzciI6NDYsImVtYWlsIjoiY2FybG9zQGdtYWlsLmNvbSIsInRpcG9fdXNlciI6MywiZXN0YWJsZWNpbWllbnRvX2lkIjoxLCJiZF9ub21icmUiOiJ1Nzg3OTU2NDQyX2FwaSIsImJkX3VzdWFyaW8iOiJ1Nzg3OTU2NDQyX2FwaSIsImJkX2NsYXZlIjoiVXRlcTIwMjIqIiwiYmRfaG9zdCI6ImxvY2FsaG9zdCIsImJkX2lwIjoiIiwiaWF0IjoxNjg4MzMzNTE4LCJleHAiOjE2ODg2OTM1MTh9.e2KSsO1bUjEh0Rn8xZNyb6J5jDSLZ1YgAtmnUhAC1bQ");
                return headerMap;
            }
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
                params.put("fuente", "1");
                return params;
            }
        };
        queue.add(stringRequest);

    }
}