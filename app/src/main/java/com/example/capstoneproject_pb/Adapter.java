package com.example.capstoneproject_pb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    LayoutInflater inflater;
    List<Wisata> wisataList;

    public Adapter (Context context, List<Wisata> wisataList){
        this.inflater = LayoutInflater.from(context);
        this.wisataList = wisataList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =inflater.inflate(R.layout.list_wisata, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.WisataTitle.setText(wisataList.get(position).getNama());
        holder.KategoriWisata.setText(wisataList.get(position).getKategori());
        Picasso.get().load(wisataList.get(position).getGambar_url()).into(holder.CoverWisata);
    }

    @Override
    public int getItemCount() {
        return wisataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView WisataTitle, KategoriWisata;
        ImageView CoverWisata;

    public ViewHolder(@NonNull View itemView){
        super(itemView);

        WisataTitle = itemView.findViewById(R.id.wisataTitle);
        KategoriWisata = itemView.findViewById(R.id.kategoriWisata);
        CoverWisata = itemView.findViewById(R.id.coverWisata);

    }
}
}
