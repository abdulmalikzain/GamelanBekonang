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
import com.gamelanbekonang.api.BaseApiService;
import com.gamelanbekonang.beans.Kategori;
import com.gamelanbekonang.menuHome.DetailIklanActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterKategori extends RecyclerView.Adapter<AdapterKategori.KenongViewHolder>{

    private List<Kategori> kategoris;
    private Context context;

    public AdapterKategori(Context context, List<Kategori> list) {
        this.kategoris = list;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterKategori.KenongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.iklan_list, parent, false);

        return new AdapterKategori.KenongViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterKategori.KenongViewHolder holder, int position) {
        Kategori kategori = kategoris.get(position);
        holder.tvJudul.setText(kategori.getJudul());
        holder.tvJenis.setText(kategori.getJenis());
        holder.tvWaktu.setText(kategori.getCreated_at());
        holder.tvHarga.setText("Rp " + kategori.getHarga());
        holder.tvGambariklan.setText(kategori.getImageKategoriIklan());
//        holder.tvGambarUser.setText(iklan.getUser_image());
        Picasso.with(context).load(BaseApiService.BASE_URL_IMAGE+kategori.getImageKategoriIklan())
                .error(R.mipmap.ic_launcher)
                .into(holder.ivGambar);

//        Picasso.with(context).load(BaseApiService.BASE_URL_IMAGE+iklan.getUser_image())
//                .centerCrop()
//                .resize(600, 800)
//                .error(R.mipmap.ic_launcher)
//                .into(holder.civFotouser);
    }


    public class KenongViewHolder extends RecyclerView.ViewHolder {
        private TextView tvJudul, tvJenis, tvWaktu, tvHarga, tvGambariklan, tvGambarUser;
        private ImageView ivGambar;
        private CircleImageView civFotouser;
        private int post;


        public KenongViewHolder(View itemView) {
            super(itemView);
            tvJudul  = itemView.findViewById(R.id.tv_iklan_judul);
//            tvVolume = itemView.findViewById(R.id.tv_iklan_volume);
//            tvPerusahaan = itemView.findViewById(R.id.tv_iklan_perusahaan);
            tvHarga     = itemView.findViewById(R.id.tv_iklan_harga);
            civFotouser = itemView.findViewById(R.id.civ_iklan_fotouser);
            ivGambar    = itemView.findViewById(R.id.iv_iklan_foto);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    post = getAdapterPosition();

                    Intent intent = new Intent(context, DetailIklanActivity.class);
                    intent.putExtra("judul", tvJudul.getText().toString().trim());
//                    intent.putExtra("jenis", tvJenis.getText().toString().trim());
                    intent.putExtra("image", tvGambariklan.getText().toString().trim());
//                    intent.putExtra("waktu", tvWaktu.getText().toString().trim());
                    intent.putExtra("gambaruser", tvGambarUser.getText().toString().trim());
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return kategoris.size();
    }
}
