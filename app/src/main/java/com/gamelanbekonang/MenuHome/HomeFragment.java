package com.gamelanbekonang.MenuHome;

import android.app.ProgressDialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.gamelanbekonang.Adapter.AdapterIklan;
import com.gamelanbekonang.Api.ApiService;
import com.gamelanbekonang.Api.RetroClient;
import com.gamelanbekonang.R;
import com.gamelanbekonang.beans.Iklan;
import com.gamelanbekonang.beans.IklanList;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;

public class HomeFragment extends Fragment {

    private ArrayList<Iklan> iklans;
    private ProgressDialog dialog;
    private AdapterIklan adapter;
    RecyclerView recyclerView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = view.findViewById(R.id.rv_iklan);
        initViews();
        return view;
    }

    private void initViews(){
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        loadJSON();
    }

    private void loadJSON(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://bekonang-store.000webhostapp.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService request = retrofit.create(ApiService.class);
        Call<RetroClient> call = request.getJSON();
        call.enqueue(new Callback<RetroClient>() {
            @Override
            public void onResponse(Call<RetroClient> call, Response<RetroClient> response) {

                RetroClient jsonResponse = response.body();
                iklans = new ArrayList<>(Arrays.asList(jsonResponse.getAndroid()));
                adapter = new AdapterIklan(iklans);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<RetroClient> call, Throwable t) {
                Log.d("Error",t.getMessage());
            }
        });
    }

}
