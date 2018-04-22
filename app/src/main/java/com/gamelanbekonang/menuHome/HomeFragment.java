package com.gamelanbekonang.menuHome;

import android.app.ProgressDialog;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gamelanbekonang.adapter.AdapterIklan;
import com.gamelanbekonang.R;
import com.gamelanbekonang.beans.Iklan;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private ArrayList<Iklan> iklans;
    private ProgressDialog dialog;
    private AdapterIklan adapter;
    RecyclerView recyclerView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        View view = inflater.inflate(R.layout.fragment_home, container, false);
//        recyclerView = view.findViewById(R.id.rv_iklan);
//        initViews();
//        return view;

        return inflater.inflate(R.layout.fragment_home, container, false);
    }



}
