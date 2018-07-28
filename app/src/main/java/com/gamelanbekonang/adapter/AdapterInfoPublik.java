package com.gamelanbekonang.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gamelanbekonang.R;
import com.gamelanbekonang.api.ApiService;
import com.gamelanbekonang.beans.Iklan;
import com.gamelanbekonang.menuHome.DetailIklanActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterInfoPublik extends RecyclerView.Adapter<AdapterInfoPublik.InfoPublikViewHolder> {

    private ArrayList<Iklan> info_iklans;
    private Context context;


    public AdapterInfoPublik(ArrayList<Iklan> info_iklans, Context context) {
        this.info_iklans = info_iklans;
        this.context = context;
    }



    @Override
    public AdapterInfoPublik.InfoPublikViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_item_myiklan, parent, false);

        return new AdapterInfoPublik.InfoPublikViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AdapterInfoPublik.InfoPublikViewHolder holder, int position) {

        Iklan iklan = info_iklans.get(position);
        holder.tvJudul.setText(iklan.getJudul());
//        holder.tvVolume.setText(iklan.getVolume());
//            holder.tvWaktu.setText(iklan.getCreated_at());
        holder.tvHarga.setText(iklan.getHarga());
        holder.tvId.setText(iklan.getId());
        Picasso.with(context).load(ApiService.BASE_URL_IMAGEIKLAN+iklan.getImage1())
                .error(R.mipmap.ic_launcher)
                .into(holder.ivGambar);

//        Picasso.with(context).load(ApiService.BASE_URL_IMAGEUSER+iklan.getUser_image())
//                .centerCrop()
//                .resize(80, 80)
//                .error(R.mipmap.ic_launcher)
//                .into(holder.civFotouser);
    }

    @Override
    public int getItemCount() {
        return info_iklans.size();
    }

    public class InfoPublikViewHolder extends RecyclerView.ViewHolder {
        private TextView tvJudul, tvVolume, tvPerusahaan, tvWaktu, tvHarga, tvGambariklan, tvGambarUser, tvId, tvCreateAt;
        private ImageView ivGambar;
        private CircleImageView civFotouser;
        private int post;
        public InfoPublikViewHolder(View itemView) {
            super(itemView);
            tvId        = itemView.findViewById(R.id.tv_myiklan_idiklan);
            tvJudul  = itemView.findViewById(R.id.tv_myiklan_judul);
//            tvVolume = itemView.findViewById(R.id.tv_iklan_volume);
//            tvPerusahaan = itemView.findViewById(R.id.tv_iklan_perusahaan);
            tvHarga     = itemView.findViewById(R.id.tv_myiklan_harga);
//            civFotouser = itemView.findViewById(R.id.civ_iklan_fotouser);
            ivGambar    = itemView.findViewById(R.id.iv_myiklan_foto);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    post = getAdapterPosition();

                    Intent intent = new Intent(context, DetailIklanActivity.class);
                    intent.putExtra("id", tvId.getText().toString().trim());
                    context.startActivity(intent);
                }
            });
        }
    }
}
