package com.gamelanbekonang;


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
import com.gamelanbekonang.api.BaseApiService;
import com.gamelanbekonang.api.RetroClient;
import com.gamelanbekonang.api.RetrofitClient;
import com.gamelanbekonang.menuAkun.AkunFragment;
import com.gamelanbekonang.menuFavorite.FavoriteFragment;
import com.gamelanbekonang.menuHome.HomeFragment;
import com.gamelanbekonang.menuKategori.KategoriFragment;
import com.gamelanbekonang.utils.BottomNavigationViewHelper;
import com.gamelanbekonang.beans.Iklan;
import com.gamelanbekonang.utils.EndlessRecyclerViewScrollListener;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Iklan> iklans ;
    private List<Iklan> iklans1;
    private AdapterIklan adapter;
    private RecyclerView recyclerView;
    private FrameLayout frameLayout;
    private RelativeLayout relativeLayout;
    private static final String TAG = "MainActivity";
    private Toolbar mToolbar;
    private BottomNavigationViewEx bottomNavigationViewEx;

    private String id;
    private String user_id;
    private String category_id;
    private String judul;
    private String url;
    private String deskripsi;
    private String lokasi;
    private String jenis;
    private String harga;
    private String created_at;
    private String updated_at;

    private String id1;
    private String iklan_id;
    private String filename;
    private String created_at1;
    private String updated_at1;


    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        relativeLayout = findViewById(R.id.rel_home);
        frameLayout = findViewById(R.id.fragment_container);

        mToolbar = findViewById(R.id.toolbar_home);
        setSupportActionBar(mToolbar);
        mToolbar.setTitle("Gamelan Wirun");

        initViews();
        setupBottomNavigationView();

        iklans1 = new ArrayList<>();

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
//        loadJSON();
        getData();
    }

//    private void loadJSON(){
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(BaseApiService.BASE_API_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        ApiService request = retrofit.create(ApiService.class);
//        Call<RetroClient> call = request.getJSON();
//        call.enqueue(new Callback<RetroClient>() {
//            @Override
//            public void onResponse(Call<RetroClient> call, Response<RetroClient> response) {
//                RetroClient jsonResponse = response.body();
//                iklans = new ArrayList<>(Arrays.asList(jsonResponse.getIklan()));
//                adapter = new AdapterIklan(MainActivity.this, iklans);
//                recyclerView.setAdapter(adapter);
//            }
//
//            @Override
//            public void onFailure(Call<RetroClient> call, Throwable t) {
//                Log.d("Error",t.getMessage());
//            }
//        });
//    }


    private void getData() {
        ApiService apiService  = RetrofitClient.getInstanceRetrofit();
        apiService.getData().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject object = new JSONObject(response.body().string());
                    JSONArray jsonArray  = object.optJSONArray("iklan");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        id = jsonObject.optString("id");
                        user_id = jsonObject.optString("user_id");
                        category_id = jsonObject.optString("category_id");
                        judul = jsonObject.optString("judul");
                        url = jsonObject.optString("url");
                        deskripsi = jsonObject.optString("deskripsi");
                        lokasi = jsonObject.optString("lokasi");
                        jenis = jsonObject.optString("jenis");
                        harga = jsonObject.optString("harga");
                        created_at = jsonObject.optString("created_at");
                        updated_at = jsonObject.optString("updated_at");


                        JSONObject jsonObject1 = jsonObject.optJSONObject("users");
                        String id = jsonObject1.getString("id");
                        String image = jsonObject1.getString("image");
                        String name = jsonObject1.getString("name");
                        String email = jsonObject1.getString("email");
                        String notelp = jsonObject1.getString("notelp");
                        String created_at = jsonObject1.getString("created_at");

                        JSONArray jsonArray1 = jsonObject.getJSONArray("photos");
                        for (int j = 0; j < jsonArray1.length(); j++) {
                            JSONObject jsonObject2 = jsonArray1.getJSONObject(i);

                            id1 = jsonObject2.optString("id");
                            iklan_id = jsonObject2.optString("iklan_id");
                            filename = jsonObject2.optString("filename");
                            created_at1 = jsonObject2.optString("created_at");
                            updated_at1 = jsonObject2.optString("updated_at");

                            Iklan iklan = new Iklan();
                            iklan.setJudul(judul);
//                            iklan.setImage(filename);
                            iklan.setCreated_at(created_at);
                            iklan.setDeskripsi(deskripsi);
                            iklan.setHarga(harga);
                            iklan.setJenis(jenis);
                            iklan.setUser_image(image);
                            iklans1.add(iklan);
                            AdapterIklan adapter = new AdapterIklan(MainActivity.this, iklans1);
                            recyclerView.setAdapter(adapter);
                        }


//                        JSONObject jsonObject2 = jsonObject.optJSONObject("category");
//                        String id1 = jsonObject2.optString("id");
//                        String image1 = jsonObject2.optString("image");
//                        String name1 = jsonObject2.optString("name");
//                        String created_at1 = jsonObject2.optString("created_at");
//                        String updated_at1 = jsonObject2.optString("updated_at");


                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}