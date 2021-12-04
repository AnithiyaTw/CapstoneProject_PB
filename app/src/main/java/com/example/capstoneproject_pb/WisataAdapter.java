package com.example.capstoneproject_pb;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class WisataAdapter extends RecyclerView.Adapter<WisataAdapter.MyViewHolder>{



    private  Context mContext;
    private  List<WisataModelClass> mData;
    private static RecyclerViewClickListener listener;

    public WisataAdapter(Context mContext, List<WisataModelClass> mData,RecyclerViewClickListener listener) {
        this.mContext = mContext;
        this.mData = mData;
        this.listener =listener;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.wisata_item,parent,false);
        return new MyViewHolder(view);

    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.id.setText(mData.get(position).getId());
        holder.nama.setText(mData.get(position).getNama());
        holder.kategori.setText(mData.get(position).getKategori());

        //glide
        Glide.with(mContext)
                .load(mData.get(position).getGambar()).into(holder.gambar);

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
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
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onClick(view,getAdapterPosition());
        }
    }


    public interface RecyclerViewClickListener{
        void onClick(View view,int position);
    }
}
