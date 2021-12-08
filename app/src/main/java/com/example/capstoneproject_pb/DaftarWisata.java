package com.example.capstoneproject_pb;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Database;
import androidx.room.Room;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DaftarWisata extends AppCompatActivity {
    private static final String TAG = "DaftarWisata";

    ArrayList<WisataModelClass> wisataList;
    RecyclerView recyclerView;
    WisataAdapter wisataAdapter;

    SearchView searchView;

    ShimmerFrameLayout shimmerFrameLayout;

    public static FavoriteDatabase favoriteDatabase;
    Button btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_wisata);
        shimmerFrameLayout= findViewById(R.id.shimmer);
        shimmerFrameLayout.startShimmer();
        searchView = findViewById(R.id.search_view);
        recyclerView = findViewById(R.id.recyclerView);


        wisataList = new ArrayList<>();

        btn=findViewById(R.id.favbtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplication(),FavoriteActivity.class));
            }
        });

        favoriteDatabase = Room.databaseBuilder(getApplicationContext(), FavoriteDatabase.class,"myfavdb").allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();

        getData();


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.length() > 1){
                    wisataList  = new ArrayList<>();
                    getDataSearch(newText);
                    buildRecyclerView();
                }else if (newText.length()< 1){
                    wisataList = new ArrayList<>();
                    getData();
                    buildRecyclerView();
                }
                return false;
            }
        });
        buildRecyclerView();
    }

    private void getData(){
        String JSON_URL = "https://61ac75cf264ec200176d44a0.mockapi.io/api/wisata";
        RequestQueue queue = Volley.newRequestQueue(DaftarWisata.this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, JSON_URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        shimmerFrameLayout.stopShimmer();
                        shimmerFrameLayout.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        JSONObject responseObj = response.getJSONObject(i);
                        String wisataId = responseObj.getString("id");
                        String wisataNama = responseObj.getString("nama");
                        String wisataKategori = responseObj.getString("kategori");
                        String wisataDeskripsi = responseObj.getString("deskripsi");
                        String wisataGambar = responseObj.getString("gambar_url");
                        wisataList.add(new WisataModelClass(wisataId, wisataNama, wisataKategori,
                                wisataDeskripsi, wisataGambar));
                        buildRecyclerView();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DaftarWisata.this, "Fail to get the data..", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(jsonArrayRequest);
    }


    private void getDataSearch(String newtext){
        String urlSearch= "https://61ac75cf264ec200176d44a0.mockapi.io/api/wisata?search="+newtext;
        Log.d(TAG, "getDataSearch: " + urlSearch);
        RequestQueue queue = Volley.newRequestQueue(DaftarWisata.this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, urlSearch, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i <response.length(); i++) {
                    try {
                        JSONObject responseObj = response.getJSONObject(i);
                        String wisataId = responseObj.getString("id");
                        String wisataNama = responseObj.getString("nama");
                        String wisataKategori = responseObj.getString("kategori");
                        String wisataDeskripsi = responseObj.getString("deskripsi");
                        String wisataGambar = responseObj.getString("gambar_url");
                        wisataList.add(new WisataModelClass(wisataId, wisataNama, wisataKategori,
                                wisataDeskripsi, wisataGambar));
                        buildRecyclerView();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DaftarWisata.this, "Fail to get the data..", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(jsonArrayRequest);
    }

    private void buildRecyclerView() {
        List<FavoriteList> favoriteLists=DaftarWisata.favoriteDatabase.favoriteDao().getFavoriteData();
        wisataAdapter = new WisataAdapter(DaftarWisata.this, wisataList, favoriteLists);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(wisataAdapter);
    }
}

/*
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Database;
import androidx.room.Room;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DaftarWisata extends AppCompatActivity {
    private static final String TAG = "DaftarWisata";

    ArrayList<WisataModelClass> wisataList;
    RecyclerView recyclerView;
    WisataAdapter wisataAdapter;
    
    SearchView searchView;

    ShimmerFrameLayout shimmerFrameLayout;

    public static FavoriteDatabase favoriteDatabase;
    Button btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_wisata);
        shimmerFrameLayout= findViewById(R.id.shimmer);
        shimmerFrameLayout.startShimmer();
        searchView = findViewById(R.id.search_view);
        recyclerView = findViewById(R.id.recyclerView);


        wisataList = new ArrayList<>();

        btn=findViewById(R.id.favbtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplication(),FavoriteActivity.class));
            }
        });

        favoriteDatabase = Room.databaseBuilder(getApplicationContext(), FavoriteDatabase.class,"myfavdb").allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();

        getData();


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.length() > 1){
                    wisataList  = new ArrayList<>();
                    getDataSearch(newText);
                    buildRecyclerView();
                }else if (newText.length()< 1){
                    wisataList = new ArrayList<>();
                    getData();
                    buildRecyclerView();
                }
                return false;
            }
        });
        buildRecyclerView();
    }

    private void getData(){
        String JSON_URL = "https://61ac75cf264ec200176d44a0.mockapi.io/api/wisata";
        RequestQueue queue = Volley.newRequestQueue(DaftarWisata.this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, JSON_URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        shimmerFrameLayout.stopShimmer();
                        shimmerFrameLayout.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        JSONObject responseObj = response.getJSONObject(i);
                        String wisataId = responseObj.getString("id");
                        String wisataNama = responseObj.getString("nama");
                        String wisataKategori = responseObj.getString("kategori");
                        String wisataDeskripsi = responseObj.getString("deskripsi");
                        String wisataGambar = responseObj.getString("gambar_url");
                        wisataList.add(new WisataModelClass(wisataId, wisataNama, wisataKategori,
                                wisataDeskripsi, wisataGambar));
                        buildRecyclerView();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DaftarWisata.this, "Fail to get the data..", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(jsonArrayRequest);
    }


    private void getDataSearch(String newtext){
        String urlSearch= "https://61ac75cf264ec200176d44a0.mockapi.io/api/wisata?search="+newtext;
        Log.d(TAG, "getDataSearch: " + urlSearch);
        RequestQueue queue = Volley.newRequestQueue(DaftarWisata.this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, urlSearch, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i <response.length(); i++) {
                    try {
                        JSONObject responseObj = response.getJSONObject(i);
                        String wisataId = responseObj.getString("id");
                        String wisataNama = responseObj.getString("nama");
                        String wisataKategori = responseObj.getString("kategori");
                        String wisataDeskripsi = responseObj.getString("deskripsi");
                        String wisataGambar = responseObj.getString("gambar_url");
                        wisataList.add(new WisataModelClass(wisataId, wisataNama, wisataKategori,
                                wisataDeskripsi, wisataGambar));
                        buildRecyclerView();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DaftarWisata.this, "Fail to get the data..", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(jsonArrayRequest);
    }

    private void buildRecyclerView() {
        wisataAdapter = new WisataAdapter(DaftarWisata.this, wisataList);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(wisataAdapter);
    }


}
*/