package com.example.capstoneproject_pb;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DaftarWisata extends AppCompatActivity {
    private static final String TAG = "DaftarWisata";

    ArrayList<WisataModelClass> wisataList;
    RecyclerView recyclerView;
    WisataAdapter wisataAdapter;
    
    SearchView searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_wisata);
        
        searchView = findViewById(R.id.search_view);
        recyclerView = findViewById(R.id.recyclerView);

        wisataList = new ArrayList<>();
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
        String JSON_URL = "https://61ac6ee5264ec200176d448d.mockapi.io/api/wisata";
        RequestQueue queue = Volley.newRequestQueue(DaftarWisata.this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, JSON_URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
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


    private void getDataSearch(String newtext){
        String urlSearch= "https://61ac6ee5264ec200176d448d.mockapi.io/api/wisata?search="+newtext;
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




    /*private void filter(String newText) {
        List<WisataModelClass> filteredList = new ArrayList<>();
        for (WisataModelClass item : wisataList){
            if (item.getNama().toLowerCase().contains(newText.toLowerCase())){
                filteredList.add(item);
            }
            else if (item.getKategori().toLowerCase().contains(newText.toLowerCase())){
                filteredList.add(item);
            }
        }
        wisataAdapter.filterList(filteredList);
    }


    public class GetData extends AsyncTask<String,String,String>{

        @Override
        protected String doInBackground(String... strings) {
            String current = "";
            try{
                URL url;
                HttpURLConnection urlConnection = null;
                try{
                    url = new URL(JSON_URL);
                    urlConnection = (HttpURLConnection) url.openConnection();

                    InputStream is = urlConnection.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);

                    int data = isr.read();
                    while(data != -1){
                        current += (char) data;
                        data = isr.read();
                    }
                    return current;

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if(urlConnection != null){
                        urlConnection.disconnect();
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }


            return current;
        }


        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("0");
                JSONArray jsonArray2 = jsonArray.getJSONArray(0);

                Log.d(TAG, "onPostExecute: " + jsonArray);

                for (int i = 0 ; i<jsonArray2.length(); i++){
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                    WisataModelClass model = new WisataModelClass();
                    model.setId(jsonObject1.getString("id"));
                    model.setNama(jsonObject1.getString("nama"));
                    model.setKategori(jsonObject1.getString("kategori"));
                    model.setDeskripsi(jsonObject1.getString("deskripsi"));
                    model.setGambar(jsonObject1.getString("gambar_url"));

                    wisataList.add(model);
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

            PutDataIntoRecyclerView(wisataList);
        }
    }

    private void PutDataIntoRecyclerView(List<WisataModelClass> wisataList){
        setOnClickListener();
        WisataAdapter wisataAdapter = new WisataAdapter(this, wisataList,listener);
        recyclerView = findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter( wisataAdapter );

    }

    private void setOnClickListener() {
        listener = new WisataAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(getApplicationContext(),DetailWisata.class);
                intent.putExtra("nama",wisataList.get(position).getNama());
                intent.putExtra("kategori", wisataList.get(position).getKategori());
                intent.putExtra("deskripsi", wisataList.get(position).getDeskripsi());
                intent.putExtra("gambar_url", wisataList.get(position).getGambar());
                startActivity(intent);
            }
        };
    }*/


}
