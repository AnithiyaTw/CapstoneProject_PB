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
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

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
    private static final String JSON_URL = "https://run.mocky.io/v3/95ab07a8-0c51-4b8e-a82c-10b11a470f6a";
    private WisataAdapter.RecyclerViewClickListener listener;
    List<WisataModelClass> wisataList;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_wisata);

        wisataList = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);

        GetData getData = new GetData();
        getData.execute();
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
                JSONArray jsonArray = jsonObject.getJSONArray("wisata");

                for (int i = 0 ; i< jsonArray.length(); i++){
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

    }


}
