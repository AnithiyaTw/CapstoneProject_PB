package com.example.capstoneproject_pb;


import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailWisata extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_wisata);

        TextView namaTxt = findViewById(R.id.textViewNama);
        TextView kategoriTxt = findViewById(R.id.kategori_text);
        TextView deskripsiTxt = findViewById(R.id.deskripsi);
        ImageView gambarView = findViewById(R.id.imageView);


        String nama = "nama not set";
        String kategori = "kategori not set";
        String deskripsi = "deskripsi not set";



        Bundle extras = getIntent().getExtras();
        if(extras !=null){
            nama = extras.getString("nama");
            kategori = extras.getString("kategori");
            deskripsi = extras.getString("deskripsi");
        }

        namaTxt.setText(nama);
        kategoriTxt.setText(kategori);
        deskripsiTxt.setText(deskripsi);
        gambarView.findViewById(R.id.imageView);



    }
}