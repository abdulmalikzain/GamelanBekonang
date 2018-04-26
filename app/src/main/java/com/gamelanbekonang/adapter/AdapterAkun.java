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

public class AdapterAkun extends RecyclerView.Adapter<AdapterAkun.ViewHolder>  {
    private ArrayList<String> arrayList;
    private ArrayList<Integer> akunList;

    public AdapterAkun(ArrayList<String> arrayList, ArrayList<Integer> akunList) {
        this.arrayList = arrayList;
        this.akunList = akunList;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_info;
        private ImageView icon_akun;
        private RelativeLayout item_list;
        private Context context;

        public ViewHolder(View itemView) {
            super(itemView);

            context = itemView.getContext();

            tv_info =  itemView.findViewById(R.id.tv_info);
            icon_akun = itemView.findViewById(R.id.icon_akun);
            item_list = itemView.findViewById(R.id.item_akun);

            item_list.setOnClickListener(new View.OnClickListener() {
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
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View V = LayoutInflater.from(parent.getContext()).inflate(R.layout.akun_list,parent,false);
        ViewHolder VH = new ViewHolder(V);
        return VH;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder,final int position) {
        final String Nama = arrayList.get(position);
        holder.tv_info.setText(Nama);
        holder.icon_akun.setImageResource(akunList.get(position));

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}