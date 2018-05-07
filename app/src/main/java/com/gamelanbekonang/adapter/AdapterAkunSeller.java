package com.gamelanbekonang.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gamelanbekonang.R;
import com.gamelanbekonang.menuAkun.BantuanActivity;
import com.gamelanbekonang.menuAkun.TentangActivity;

import java.util.ArrayList;

public class AdapterAkunSeller extends RecyclerView.Adapter<AdapterAkunSeller.ViewHolder> {
    private ArrayList<String> arrayList;
    private ArrayList<Integer> sellerList;

    public AdapterAkunSeller(ArrayList<String> arrayList, ArrayList<Integer> sellerList) {
        this.arrayList = arrayList;
        this.sellerList = sellerList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_rslist;
        private ImageView icon_seller;
        private RelativeLayout item_seller;
        private Context context;

        public ViewHolder(View itemView) {
            super(itemView);

            context = itemView.getContext();
            tv_rslist = itemView.findViewById(R.id.tv_rslist);
            icon_seller = itemView.findViewById(R.id.icon_seller);
            item_seller = itemView.findViewById(R.id.item_seller);

            item_seller.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    switch (getAdapterPosition()){
                        case 0 :
                            intent = new Intent(context, TentangActivity.class);
                            break;
                        case 1 :
                            intent = new Intent(context, BantuanActivity.class);
                            break;
                    }
                    context.startActivity(intent);
                }
            });
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View V = LayoutInflater.from(parent.getContext()).inflate(R.layout.reseller_list,parent,false);
        AdapterAkunSeller.ViewHolder VH = new AdapterAkunSeller.ViewHolder(V);
        return VH;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final String Seller = arrayList.get(position);
        holder.tv_rslist.setText(Seller);
        holder.icon_seller.setImageResource(sellerList.get(position));

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


}
