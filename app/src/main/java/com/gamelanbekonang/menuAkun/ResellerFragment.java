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

import com.gamelanbekonang.R;
import com.gamelanbekonang.adapter.AdapterAkun;
import com.gamelanbekonang.logRes.LoginActivity;
import com.gamelanbekonang.menuProfil.ProfilActivity;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ResellerFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<String> AkunSeller;
    private ArrayList<Integer> GambarSeller;
    //nama listnya
    private String[] Seller = {"TENTANG", "BANTUAN"};
    //Daftar gambar
    private int[] Gambar = {R.drawable.ic_akun, R.drawable.ic_home};


    public ResellerFragment() {
        // Required empty public constructor
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reseller, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        AkunSeller = new ArrayList<>();
        GambarSeller = new ArrayList<>();
        recyclerView = view.findViewById(R.id.rv_reseller);
        DaftarItem();

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new AdapterAkun(AkunSeller, GambarSeller);
        //Memasang Adapter pada RecyclerView
        recyclerView.setAdapter(adapter);

    }

    private void DaftarItem() {
        for (int w=0; w<Seller.length; w++){
            GambarSeller.add(Gambar[w]);
            AkunSeller.add(Seller[w]);
        }
    }

}
