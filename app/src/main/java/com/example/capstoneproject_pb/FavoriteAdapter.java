package com.example.capstoneproject_pb;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import static com.example.capstoneproject_pb.DaftarWisata.favoriteDatabase;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.MyViewHolder> {

    private List<FavoriteList> favoriteLists;

    Context mContext;


    public FavoriteAdapter(List<FavoriteList> favoriteLists, Context mContext) {
        this.favoriteLists = favoriteLists;
        this.mContext = mContext;
    }


    @NonNull

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.favorite_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        int posisi = position;
        holder.id.setText(favoriteLists.get(position).getId());
        holder.nama.setText(favoriteLists.get(position).getNama());
        holder.kategori.setText(favoriteLists.get(position).getKategori());

        //glide
        Glide.with(mContext)
                .load(favoriteLists.get(position).getGambar()).into(holder.gambar);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext,DetailWisata.class);
                intent.putExtra("nama",favoriteLists.get(posisi).getNama());
                intent.putExtra("kategori", favoriteLists.get(posisi).getKategori());
                intent.putExtra("gambar_url", favoriteLists.get(posisi).getGambar());
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return favoriteLists.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView fav_btn;
        TextView id;
        TextView nama;
        TextView kategori;
        TextView deskripsi;
        ImageView gambar;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            id = itemView.findViewById(R.id.id_text);
            nama = itemView.findViewById(R.id.nama_text);
            kategori = itemView.findViewById(R.id.kategori_text);
            deskripsi = itemView.findViewById(R.id.deskripsi_text);
            gambar = itemView.findViewById(R.id.imageView);
        }
    }
}
