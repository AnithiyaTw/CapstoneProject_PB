package com.example.capstoneproject_pb;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.List;

public class FavoriteActivity extends AppCompatActivity {
    private RecyclerView rv;
    private FavoriteAdapter favoriteAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        rv=findViewById(R.id.recyclerView);
        rv.setLayoutManager(new LinearLayoutManager(this));

        getFavData();
    }
    private void getFavData(){
       List<FavoriteList>favoriteLists=DaftarWisata.favoriteDatabase.favoriteDao().getFavoriteData();


        favoriteAdapter=new FavoriteAdapter(favoriteLists,getApplicationContext());
        rv.setAdapter(favoriteAdapter);

    }

}