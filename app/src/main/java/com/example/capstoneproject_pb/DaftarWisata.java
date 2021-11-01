package com.example.capstoneproject_pb;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DaftarWisata extends AppCompatActivity {
    RecyclerView recyclerView;
    List<Wisata> wisataList;
    private static String JSON_URL = "https://dev.farizdotil.com/api/purwakarta/wisata";
    Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_wisata);

        recyclerView = findViewById(R.id.DaftarWisata);
        wisataList = new ArrayList<>();
        extractWisata();

    }

    private void extractWisata() {
        RequestQueue Queue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, JSON_URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject wisataObject = response.getJSONObject(i);

                        Wisata wisata = new Wisata();
                        wisata.setNama(wisataObject.getString("nama").toString());
                        wisata.setGambar_url(wisataObject.getString("ugambar_url"));
                        wisata.setId(wisataObject.getInt("id"));
                        wisata.setKategori(wisataObject.getString("kategori"));

                        wisataList.add(wisata);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                adapter = new Adapter(getApplicationContext(), wisataList);
                recyclerView.setAdapter(adapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("tag", "onErrorResponse: " + error.getMessage());
            }
        });

        Queue.add(jsonArrayRequest);
    }
}
