package com.gamelanbekonang;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
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
import com.gamelanbekonang.logRes.LoginActivity;
import com.gamelanbekonang.menuAkun.CustomerFragment;
import com.gamelanbekonang.menuAkun.SellerFragment;
import com.gamelanbekonang.menuFavorite.FavoriteFragment;
import com.gamelanbekonang.menuHome.HomeFragment;
import com.gamelanbekonang.menuKategori.KategoriFragment;
import com.gamelanbekonang.utils.BottomNavigationViewHelper;
import com.gamelanbekonang.beans.Iklan;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.gamelanbekonang.logRes.LoginActivity.my_shared_preferences;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private ArrayList<Iklan> iklans ;
    private List<Iklan> iklans1;
    private AdapterIklan adapter;
    private RecyclerView recyclerView;
    private FrameLayout frameLayout;
    private RelativeLayout relativeLayout;
    private static final String TAG = "MainActivity";
    private Toolbar mToolbar;
    private BottomNavigationViewEx bottomNavigationViewEx;
    private String roleName, filename;
    private SwipeRefreshLayout swipeHome;
    boolean doubleBackToExitPressedOnce = false;

    private int previousTotal = 0;
    private boolean loading = true;
    private int visibleThreshold = 5;
    int firstVisibleItem, visibleItemCount, totalItemCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        relativeLayout = findViewById(R.id.rel_home);
        frameLayout = findViewById(R.id.fragment_container);
        swipeHome   = findViewById(R.id.swipe_home);

        mToolbar = findViewById(R.id.toolbar_home);
        initViews();
        setupBottomNavigationView();

        iklans1 = new ArrayList<>();

        SharedPreferences sp = getApplicationContext().getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);
        roleName = (sp.getString("role_name", ""));

        swipeHome.setColorSchemeResources(R.color.kuningFirebase,R.color.orangeFirebase,R.color.colorPrimary);
        swipeHome.setOnRefreshListener(this);
        swipeHome.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeHome.setRefreshing(true);
                                        getData();
                                    }
                                }
        );

    }

    private BottomNavigationView.OnNavigationItemSelectedListener botnav = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();


            switch (item.getItemId()){
                case R.id.navigation_home:
                    getData();
                    relativeLayout.setVisibility(View.VISIBLE);
                    frameLayout.setVisibility(View.GONE);
                    return true;

                case R.id.navigation_favorite:
                    if (roleName.equals("customer")){
                        transaction.replace(R.id.fragment_container, new FavoriteFragment()).commit();
                        relativeLayout.setVisibility(View.INVISIBLE);
                        frameLayout.setVisibility(View.VISIBLE);
                        return true;
                    }else if (roleName.equals("seller")){
                        transaction.replace(R.id.fragment_container, new FavoriteFragment()).commit();
                        relativeLayout.setVisibility(View.INVISIBLE);
                        frameLayout.setVisibility(View.VISIBLE);
                        return true;
                    }else {
                        alertFavorite();
                        transaction.replace(R.id.fragment_container, new HomeFragment()).commit();
                        relativeLayout.setVisibility(View.VISIBLE);
                        frameLayout.setVisibility(View.GONE);
                        return false;
                    }


                case R.id.navigation_kategori:
                    transaction.replace(R.id.fragment_container, new KategoriFragment()).commit();
                    relativeLayout.setVisibility(View.INVISIBLE);
                    frameLayout.setVisibility(View.VISIBLE);
                    return true;

                case R.id.navigation_akun:
                    if (roleName.equals("customer")){
                        transaction.replace(R.id.fragment_container, new CustomerFragment()).commit();
                        relativeLayout.setVisibility(View.INVISIBLE);
                        frameLayout.setVisibility(View.VISIBLE);
                        return true;
                    }else if (roleName.equals("seller")){
                        transaction.replace(R.id.fragment_container, new SellerFragment()).commit();
                        relativeLayout.setVisibility(View.INVISIBLE);
                        frameLayout.setVisibility(View.VISIBLE);
                        return true;
                    }else {
                        alertAkun();
                        transaction.replace(R.id.fragment_container, new HomeFragment()).commit();
                        relativeLayout.setVisibility(View.VISIBLE);
                        frameLayout.setVisibility(View.GONE);
                        return false;
                    }

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

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            finishAffinity();
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



    private void initViews(){
        recyclerView = findViewById(R.id.rv_iklan);
        recyclerView.setHasFixedSize(true);
        final LinearLayoutManager mLayoutManager;
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);


    }

    private void getData() {

        swipeHome.setRefreshing(true);

        ApiService apiService  = RetroClient.getInstanceRetrofit();
        apiService.getData().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject object = new JSONObject(response.body().string());
                    JSONArray jsonArray  = object.optJSONArray("iklan");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String id = jsonObject.optString("id");
                        String judul = jsonObject.optString("judul");
                        String image1   = jsonObject.optString("filename");
                        String volume = jsonObject.optString("volume");
                        String harga = jsonObject.optString("harga");
                        String created_at = jsonObject.optString("created_at");
                        String imageuser = jsonObject.getString("user_image");
                        String storename = jsonObject.getString("store_name");

                        Iklan iklan = new Iklan();
                        iklan.setId(id);
                        iklan.setJudul(judul);
                        iklan.setImage1(image1);
                        iklan.setCreated_at(created_at);
                        iklan.setVolume(volume);
                        iklan.setHarga(harga);
                        iklan.setUser_image(imageuser);
                        iklan.setStore_name(storename);

                        iklans1.add(iklan);
                        AdapterIklan adapter = new AdapterIklan(MainActivity.this, iklans1);
                        recyclerView.setAdapter(adapter);

                    }

                    swipeHome.setRefreshing(false);

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                swipeHome.setRefreshing(false);
            }
        });
    }

    private void alertFavorite(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Belum Login");
        builder.setMessage("anda harus login lebih dahulu untuk melihat menu favorite")
                .setIcon(R.mipmap.ic_icon_gw)
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();

                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void alertAkun(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Belum Login");
        builder.setMessage("anda harus login lebih dahulu untuk melihat menu akun")
                .setIcon(R.mipmap.ic_icon_gw)
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onRefresh() {
        iklans1.clear();
//        adapter.notifyDataSetChanged();
        getData();
    }
}