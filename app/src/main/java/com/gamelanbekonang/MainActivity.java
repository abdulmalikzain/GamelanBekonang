package com.gamelanbekonang;


import android.app.ProgressDialog;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.gamelanbekonang.Adapter.AdapterIklan;
import com.gamelanbekonang.Api.ApiService;
import com.gamelanbekonang.Api.RetroClient;
import com.gamelanbekonang.MenuAkun.AkunFragment;
import com.gamelanbekonang.MenuFavorite.FavoriteFragment;
import com.gamelanbekonang.MenuHome.HomeFragment;
import com.gamelanbekonang.beans.Iklan;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener  {
    private ArrayList<Iklan> iklans;
    private ProgressDialog dialog;
    private AdapterIklan adapter;
    RecyclerView recyclerView;
    FrameLayout frameLayout;
    RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //loading the default fragment
        loadFragment(new HomeFragment());
        frameLayout = findViewById(R.id.fragment_container);
        relativeLayout = findViewById(R.id.rel_home);

        //getting bottom navigation view and attaching the listener
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);

        initViews();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        switch (item.getItemId()) {
            case R.id.navigation_home:
                fragment = new HomeFragment();
                relativeLayout.setVisibility(View.VISIBLE);
                frameLayout.setVisibility(View.INVISIBLE);
                break;

            case R.id.navigation_favorite:
                fragment = new FavoriteFragment();
                frameLayout.setVisibility(View.VISIBLE);
                relativeLayout.setVisibility(View.INVISIBLE);
                break;

            case R.id.navigation_akun:
                fragment = new AkunFragment();
                frameLayout.setVisibility(View.VISIBLE);
                relativeLayout.setVisibility(View.INVISIBLE);
                break;

        }

        return loadFragment(fragment);
    }

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
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