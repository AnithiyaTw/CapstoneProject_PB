package com.example.capstoneproject_pb;


import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetailWisata extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_wisata);

        TextView namaTxt = findViewById(R.id.textViewNama);
        TextView kategoriTxt = findViewById(R.id.kategori_text);
        TextView deskripsiTxt = findViewById(R.id.deskripsi_text);
        ImageView gambarView = findViewById(R.id.imageView);


        String nama = "nama not set";
        String kategori = "kategori not set";
        String deskripsi = "deskripsi not set";
        String gambar = "gambar not set";



        Bundle extras = getIntent().getExtras();
        if(extras !=null){
            nama = extras.getString("nama");
            kategori = extras.getString("kategori");
            deskripsi = extras.getString("deskripsi");
            gambar = extras.getString("gambar_url");
        }

        namaTxt.setText(nama);
        kategoriTxt.setText(kategori);
        deskripsiTxt.setText(deskripsi);
        Glide.with(gambarView).load(gambar).into(gambarView);

    }
}