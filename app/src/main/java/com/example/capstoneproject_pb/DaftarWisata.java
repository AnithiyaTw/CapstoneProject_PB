package com.example.capstoneproject_pb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

    public class DaftarWisata extends AppCompatActivity {

        private static String JSON_URL = "https://run.mocky.io/v3/34c40395-22cf-4f8c-a6cf-c81b9d342054";

        List<WisataModelClass> wisataList;
        RecyclerView recyclerView;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_daftar_wisata);

            wisataList = new ArrayList<>();
            recyclerView = findViewById(R.id.DaftarWisata);

            GetData getData = new GetData();
            getData.execute();
        }

        public void searchBooks(View view) {
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
            WisataAdapter wisataAdapter = new WisataAdapter(this, wisataList);
            LinearLayoutManager llm = new LinearLayoutManager(this);
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(llm);
            recyclerView.setAdapter( wisataAdapter );

        }

    }
