package com.example.capstoneproject_pb;


import static com.example.capstoneproject_pb.DaftarWisata.favoriteDatabase;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class WisataAdapter extends RecyclerView.Adapter<WisataAdapter.MyViewHolder>{

    private List<FavoriteList> favoriteLists;
    private  Context mContext;
    private ArrayList<WisataModelClass> mData;

    public WisataAdapter(Context mContext , ArrayList<WisataModelClass> wisataModelClassArrayList,List<FavoriteList> favoriteLists) {
        this.mContext = mContext;
        this.mData= wisataModelClassArrayList;
        this.favoriteLists = favoriteLists;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.wisata_item, parent, false)
        );

    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        int posisi = position;
        holder.id.setText(mData.get(position).getId());
        holder.nama.setText(mData.get(position).getNama());
        holder.kategori.setText(mData.get(position).getKategori());

        //glide
        Glide.with(mContext)
                .load(mData.get(position).getGambar()).into(holder.gambar);

        if (DaftarWisata.favoriteDatabase.favoriteDao().isFavorite(mData.get(position).getId())==null)
            holder.fav_btn.setImageResource(R.drawable.ic_favorite);
        else holder.fav_btn.setImageResource(R.drawable.ic_favorite_border_black_24dp);

        holder.fav_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){

                FavoriteList favoriteList = new FavoriteList();;
                String id = mData.get(posisi).getId();
                String gambar = mData.get(posisi).getGambar();
                String nama = mData.get(posisi).getNama();
                String kategori = mData.get(posisi).getKategori();

                favoriteList.setId(id);
                favoriteList.setGambar(gambar);
                favoriteList.setNama(nama);
                favoriteList.setKategori(kategori);

                if (DaftarWisata.favoriteDatabase.favoriteDao().isFavorite(id) != null) {
                    holder.fav_btn.setImageResource(R.drawable.ic_favorite);
                    DaftarWisata.favoriteDatabase.favoriteDao().addData(favoriteList);
                } else {
                    holder.fav_btn.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                    DaftarWisata.favoriteDatabase.favoriteDao().delete(favoriteList);
                }
            }
        });


     /*   if (DaftarWisata.favoriteDatabase.favoriteDao().isFavorite(mData.get(position).getId())==null){
            holder.fav_btn.setImageResource(R.drawable.ic_favorite);
        }else {
            holder.fav_btn.setImageResource(R.drawable.ic_favorite_border_black_24dp);
            holder.fav_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FavoriteList favoriteList = new FavoriteList();;
                    String id = mData.get(posisi).getId();
                    String gambar = mData.get(posisi).getGambar();
                    String nama = mData.get(posisi).getNama();
                    String kategori = mData.get(posisi).getKategori();

                    favoriteList.setId(id);
                    favoriteList.setGambar(gambar);
                    favoriteList.setNama(nama);
                    favoriteList.setKategori(kategori);

                    if (DaftarWisata.favoriteDatabase.favoriteDao().isFavorite(id) != null) {
                        holder.fav_btn.setImageResource(R.drawable.ic_favorite);
                        holder.fav_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                onDelete(posisi);
                            }
                        });
                    } else {
                        holder.fav_btn.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                    }
                }
            });
        }
*/
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mContext,DetailWisata.class);
                intent.putExtra("nama",mData.get(posisi).getNama());
                intent.putExtra("kategori", mData.get(posisi).getKategori());
                intent.putExtra("deskripsi", mData.get(posisi).getDeskripsi());
                intent.putExtra("gambar_url", mData.get(posisi).getGambar());
                mContext.startActivity(intent);
            }
        });
    }

    private void onDelete(int posisi) {
        favoriteDatabase.favoriteDao().delete(favoriteLists.get(posisi));
        favoriteLists.remove(posisi);
        notifyItemRemoved(posisi);
        notifyItemRangeRemoved(posisi, favoriteLists.size());
        Toast.makeText(mContext, "Berhasil dihapus", Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView id;
        TextView nama;
        TextView kategori;
        TextView deskripsi;
        ImageView gambar;
        ImageView fav_btn;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            id = itemView.findViewById(R.id.id_text);
            nama = itemView.findViewById(R.id.nama_text);
            kategori = itemView.findViewById(R.id.kategori_text);
            deskripsi = itemView.findViewById(R.id.deskripsi_text);
            gambar = itemView.findViewById(R.id.imageView);
            fav_btn=(ImageView)itemView.findViewById(R.id.fav_btn);
        }
    }
}

