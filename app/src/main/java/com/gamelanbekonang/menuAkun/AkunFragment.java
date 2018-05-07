package com.gamelanbekonang.menuAkun;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.gamelanbekonang.adapter.AdapterAkun;
import com.gamelanbekonang.logRes.LoginActivity;
import com.gamelanbekonang.R;
import com.gamelanbekonang.menuProfil.ProfilActivity;

import java.util.ArrayList;


public class AkunFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<String> InfoAkun;
    private ArrayList<Integer> GambarAkun;
    private TextView tv_detail;
    //nama listnya
    private String[] Nama = {"TENTANG", "BANTUAN"};
    //Daftar gambar
    private int[] Gambar = {R.drawable.ic_akun, R.drawable.ic_home};

    Button btnlogin;
    public AkunFragment() {
        // Required empty public constructor

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_akun, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        InfoAkun = new ArrayList<>();
        GambarAkun = new ArrayList<>();
        recyclerView = view.findViewById(R.id.rv_akun);
        DaftarItem();

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new AdapterAkun(InfoAkun, GambarAkun);
        //Memasang Adapter pada RecyclerView
        recyclerView.setAdapter(adapter);
        tv_detail = (TextView) view.findViewById(R.id.tv_detailakun);
        tv_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ProfilActivity.class);
                getActivity().startActivity(intent);
            }
        });

        btnlogin = (Button) view.findViewById(R.id.btn_masuk);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                getActivity().startActivity(intent);
            }
        });

    }

    private void DaftarItem() {
        for (int w=0; w<Nama.length; w++){
            GambarAkun.add(Gambar[w]);
            InfoAkun.add(Nama[w]);
        }
    }
}
