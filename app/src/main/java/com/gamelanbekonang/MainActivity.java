package com.gamelanbekonang;


import android.app.ProgressDialog;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.gamelanbekonang.adapter.AdapterIklan;
import com.gamelanbekonang.api.ApiService;
import com.gamelanbekonang.api.RetroClient;
import com.gamelanbekonang.menuAkun.AkunFragment;
import com.gamelanbekonang.menuFavorite.FavoriteFragment;
import com.gamelanbekonang.menuHome.HomeFragment;
import com.gamelanbekonang.menuKategori.KategoriFragment;
import com.gamelanbekonang.utils.BottomNavigationViewHelper;
import com.gamelanbekonang.beans.Iklan;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Iklan> iklans;
    private ProgressDialog dialog;
    private AdapterIklan adapter;
    RecyclerView recyclerView;
    FrameLayout frameLayout;
    RelativeLayout relativeLayout;
    private static final String TAG = "MainActivity";
    Toolbar mToolbar;
    private BottomNavigationViewEx bottomNavigationViewEx;


    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        relativeLayout = findViewById(R.id.rel_home);
        frameLayout = findViewById(R.id.fragment_container);

        mToolbar = findViewById(R.id.toolbar_home);
        setSupportActionBar(mToolbar);
        mToolbar.setTitle("Gamelan Bekonang");

        initViews();
        setupBottomNavigationView();



    }

    private BottomNavigationView.OnNavigationItemSelectedListener botnav = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();

            switch (item.getItemId()){
                case R.id.navigation_home:
                    transaction.replace(R.id.fragment_container, new HomeFragment()).commit();
                    relativeLayout.setVisibility(View.VISIBLE);
                    frameLayout.setVisibility(View.GONE);
                    return true;

                case R.id.navigation_favorite:
                    transaction.replace(R.id.fragment_container, new FavoriteFragment()).commit();
                    relativeLayout.setVisibility(View.INVISIBLE);
                    frameLayout.setVisibility(View.VISIBLE);
                    return true;

                case R.id.navigation_kategori:
                    transaction.replace(R.id.fragment_container, new KategoriFragment()).commit();
                    relativeLayout.setVisibility(View.INVISIBLE);
                    frameLayout.setVisibility(View.VISIBLE);
                    return true;

                case R.id.navigation_akun:
                    transaction.replace(R.id.fragment_container, new AkunFragment()).commit();
                    relativeLayout.setVisibility(View.INVISIBLE);
                    frameLayout.setVisibility(View.VISIBLE);
                    return true;
            }
            return false;
        }
    };

    private void setupBottomNavigationView(){
        Log.d(TAG, "setupBottomNavigationView: setting up BottomNavigationView");
        bottomNavigationViewEx = findViewById(R.id.navigasi);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);

        BottomNavigationView BNV = findViewById(R.id.navigasi);
        BNV.setOnNavigationItemSelectedListener(botnav);

    }


    //////////////BECK PRESED
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Tekan beck lagi untuk keluar", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    ////////////////////////////    PARSING RETROFIT TAMPIL DATA
    private void initViews(){
        recyclerView = findViewById(R.id.rv_iklan);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
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
                adapter = new AdapterIklan(MainActivity.this, iklans);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<RetroClient> call, Throwable t) {
                Log.d("Error",t.getMessage());
            }
        });
    }
}