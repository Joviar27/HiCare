package com.example.hicare_prototype;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RiwayatAdapter extends RecyclerView.Adapter<RiwayatAdapter.RiwayatViewHolder>{

    private ArrayList<AntrianModel> daftar_riwayat;
    private Context context;

    public RiwayatAdapter(Context context, ArrayList<AntrianModel> daftar_riwayat){
        this.context = context;
        this.daftar_riwayat = daftar_riwayat;
    }

    @NonNull
    @Override
    public RiwayatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View viewHolder = layoutInflater.inflate(R.layout.viewholder_riwayat,parent,false);
        return new RiwayatViewHolder(viewHolder);
    }

    @Override
    public void onBindViewHolder(@NonNull RiwayatViewHolder holder, int position) {
        holder.tv_tanggalLayanan.setText("Tanggal : "+daftar_riwayat.get(position).getTanggalLayanan());
        holder.tv_rumahSakit.setText(daftar_riwayat.get(position).getRumahSakit());
        holder.tv_beratBadan.setText(daftar_riwayat.get(position).getBeratBadan());
        holder.tv_tinggiBadan.setText(daftar_riwayat.get(position).getTinggiBadan());
        holder.tv_lingkarKepala.setText(daftar_riwayat.get(position).getLingkarKepala());
    }

    @Override
    public int getItemCount() {
        return daftar_riwayat.size();
    }

    public class RiwayatViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_tanggalLayanan;
        private TextView tv_rumahSakit;
        private TextView tv_beratBadan;
        private TextView tv_tinggiBadan;
        private TextView tv_lingkarKepala;

        public RiwayatViewHolder(View view){
            super(view);
            tv_tanggalLayanan = view.findViewById(R.id.tv_tanggalLayanan);
            tv_rumahSakit = view.findViewById(R.id.tv_rumahSakit);
            tv_beratBadan = view.findViewById(R.id.tv_beratBadan);
            tv_tinggiBadan = view.findViewById(R.id.tv_tinggiBadan);
            tv_lingkarKepala = view.findViewById(R.id.tv_lingkarKepala);
        }
    }

}
