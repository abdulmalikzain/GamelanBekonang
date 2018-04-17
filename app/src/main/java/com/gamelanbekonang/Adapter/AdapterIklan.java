package com.gamelanbekonang.Adapter;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gamelanbekonang.MenuHome.DetailIklanActivity;
import com.gamelanbekonang.R;
import com.gamelanbekonang.beans.Iklan;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.ContentValues.TAG;

/**
 * Created by Lenovo on 27/03/2018.
 */

public class AdapterIklan extends RecyclerView.Adapter<AdapterIklan.CustomViewHolder> {
    private List<Iklan> iklans;

    public AdapterIklan(List<Iklan> iklans) {
        this.iklans = iklans;
    }

    @Override
    public AdapterIklan.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.iklan_list, parent, false);

        return new AdapterIklan.CustomViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        Iklan iklan = iklans.get(position);
        holder.tvJudul.setText(iklan.getJudul());
        holder.tvJenis.setText(iklan.getJenis());
        holder.tvWaktu.setText(iklan.getCreated_at());
        holder.tvHarga.setText("Rp " + iklan.getHarga());
        Picasso.get().load("http://bekonang-store.000webhostapp.com/images/"+iklan.getImage())
                .error(R.mipmap.ic_launcher)
                .into(holder.ivGambar);

//        Picasso.get().load("http://bekonang-store.000webhostapp.com/images/"+iklan.getImage())
//                .error(R.mipmap.ic_launcher)
//                .into(holder.ivGambar);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView tvJudul, tvJenis, tvWaktu, tvHarga;
        ImageView ivGambar;
        CircleImageView civFotouser;
        int post;
        Context context;

        public CustomViewHolder(View itemView) {
            super(itemView);
            tvJudul  = itemView.findViewById(R.id.tv_judulhome);
            tvJenis = itemView.findViewById(R.id.tv_jenishome);
            ivGambar = itemView.findViewById(R.id.iv_fotoiklanhome);
            tvWaktu     = itemView.findViewById(R.id.tv_waktuhome);
            tvHarga     = itemView.findViewById(R.id.tv_hargahome);
            civFotouser = itemView.findViewById(R.id.civ_fotohome);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    post = getAdapterPosition();

                    Intent intent = new Intent(context, DetailIklanActivity.class);
//                    intent.putExtra("username", tvAUsername.getText().toString().trim());
//                    intent.putExtra("waktu", tvAWaktu.getText().toString().trim());
//                    intent.putExtra("status", tvStatus.getText().toString().trim());
//                    intent.putExtra("tujuan", tvTujuan.getText().toString().trim());
//                    intent.putExtra("foto_status", tvFoto_status.getText().toString().trim());
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return iklans.size();
    }

}
