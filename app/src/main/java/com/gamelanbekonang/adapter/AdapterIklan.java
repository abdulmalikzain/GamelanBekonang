package com.gamelanbekonang.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gamelanbekonang.menuHome.DetailIklanActivity;
import com.gamelanbekonang.R;
import com.gamelanbekonang.beans.Iklan;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Lenovo on 27/03/2018.
 */

public class AdapterIklan extends RecyclerView.Adapter<AdapterIklan.IklanViewHolder> {
    private List<Iklan> iklans;
    private Context context;

    public AdapterIklan(Context context, List<Iklan> list) {
        this.iklans = list;
        this.context = context;
    }

    @Override
    public void onBindViewHolder(IklanViewHolder holder, int position) {

            Iklan iklan = iklans.get(position);
            holder.tvJudul.setText(iklan.getJudul());
            holder.tvJenis.setText(iklan.getJenis());
            holder.tvWaktu.setText(iklan.getCreated_at());
            holder.tvHarga.setText("Rp " + iklan.getHarga());
            holder.tvGambariklan.setText(iklan.getImage());
//        holder.tvGambarUser.setText(iklan.getUser_image());
            Picasso.with(context).load("http://bekonang-store.000webhostapp.com/images/"+iklan.getImage())
                    .error(R.mipmap.ic_launcher)
                    .into(holder.ivGambar);

            Picasso.with(context).load("http://bekonang-store.000webhostapp.com/images/"+iklan.getUser_image())
                    .centerCrop()
                    .resize(600, 800)
                    .error(R.mipmap.ic_launcher)
                    .into(holder.civFotouser);
    }

    public class IklanViewHolder extends RecyclerView.ViewHolder {
        TextView tvJudul, tvJenis, tvWaktu, tvHarga, tvGambariklan, tvGambarUser;
        ImageView ivGambar;
        CircleImageView civFotouser;
        int post;


        public IklanViewHolder(View itemView) {
            super(itemView);
            tvJudul  = itemView.findViewById(R.id.tv_judulhome);
            tvJenis = itemView.findViewById(R.id.tv_jenishome);
            ivGambar = itemView.findViewById(R.id.iv_fotoiklanhome);
            tvWaktu     = itemView.findViewById(R.id.tv_waktuhome);
            tvHarga     = itemView.findViewById(R.id.tv_hargahome);
            civFotouser = itemView.findViewById(R.id.civ_fotohome);
            tvGambariklan = itemView.findViewById(R.id.tv_gambariklan);
//            tvGambarUser = itemView.findViewById(R.id.tv_gambaruser);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    post = getAdapterPosition();

                    Intent intent = new Intent(context, DetailIklanActivity.class);
                    intent.putExtra("judul", tvJudul.getText().toString().trim());
                    intent.putExtra("jenis", tvJenis.getText().toString().trim());
                    intent.putExtra("image", tvGambariklan.getText().toString().trim());
                    intent.putExtra("waktu", tvWaktu.getText().toString().trim());
//                    intent.putExtra("gambaruser", tvGambarUser.getText().toString().trim());
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public AdapterIklan.IklanViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.iklan_list, parent, false);

        return new AdapterIklan.IklanViewHolder(itemView);

//        IklanViewHolder vh = new IklanViewHolder(itemView);
//
//        return vh;
    }


    @Override
    public int getItemCount() {
        return iklans.size();
    }



}
