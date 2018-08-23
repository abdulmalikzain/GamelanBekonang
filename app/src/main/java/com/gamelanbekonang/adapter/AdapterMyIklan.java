package com.gamelanbekonang.adapter;

import android.content.Context;
import android.content.Intent;
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

public class AdapterMyIklan extends RecyclerView.Adapter<AdapterMyIklan.MyIklanViewHolder> {
    private ArrayList<Iklan> my_iklans;
    private Context context;

    public AdapterMyIklan(Context context, ArrayList<Iklan> arraylist) {
        this.my_iklans = arraylist;
        this.context = context;
    }

    @Override
    public AdapterMyIklan.MyIklanViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_item_myiklan, parent, false);

        return new AdapterMyIklan.MyIklanViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(AdapterMyIklan.MyIklanViewHolder holder, int position) {

        Iklan iklan = my_iklans.get(position);
        holder.tvJudul.setText(iklan.getJudul());
        holder.tvHarga.setText(iklan.getHarga());
        holder.tvId.setText(iklan.getId());
        Picasso.with(context).load(ApiService.BASE_URL_IMAGEIKLAN+iklan.getImage1())
                .error(R.mipmap.ic_launcher)
                .into(holder.ivGambar);
    }

    @Override
    public int getItemCount() {
        return my_iklans.size();
    }

    public class MyIklanViewHolder extends RecyclerView.ViewHolder {
        private TextView tvJudul, tvVolume, tvPerusahaan, tvWaktu, tvHarga, tvGambariklan, tvGambarUser, tvId, tvCreateAt;
        private ImageView ivGambar;
        private CircleImageView civFotouser;
        private int post;
        public MyIklanViewHolder(View itemView) {
            super(itemView);

            tvId        = itemView.findViewById(R.id.tv_myiklan_idiklan);
            tvJudul  = itemView.findViewById(R.id.tv_myiklan_judul);
            tvHarga     = itemView.findViewById(R.id.tv_myiklan_harga);
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
