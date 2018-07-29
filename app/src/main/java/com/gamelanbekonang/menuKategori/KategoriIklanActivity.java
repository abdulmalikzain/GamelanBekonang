package com.gamelanbekonang.menuKategori;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.gamelanbekonang.R;
import com.gamelanbekonang.adapter.AdapterIklan;
import com.gamelanbekonang.api.ApiService;
import com.gamelanbekonang.api.RetroClient;
import com.gamelanbekonang.api.RetrofitClient;
import com.gamelanbekonang.beans.Iklan;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KategoriIklanActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private Toolbar mActionToolbar;
    private ArrayList<Iklan> kategoris;
    private RecyclerView recyclerView;
    private ApiService mApiService;
    private String tittle;
    private String idKategori, judul;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView tvJudul;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kategori_iklan);

        mActionToolbar = findViewById(R.id.toolbar_kenong);
        swipeRefreshLayout = findViewById(R.id.swipe_kategori);
        tvJudul        = findViewById(R.id.tv_judul_kategori);

        setSupportActionBar(mActionToolbar);
        getSupportActionBar().setTitle(tittle);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        recyclerView = findViewById(R.id.rv_kenong);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        kategoris = new ArrayList<>();
        getData();

        swipeRefreshLayout.setColorSchemeResources(R.color.kuningFirebase,R.color.orangeFirebase,R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(new Runnable() {
                           @Override
                           public void run() {
                               swipeRefreshLayout.setRefreshing(true);
                               getData();
                           }
                       }
        );
    }

    //button back toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()== android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    ////////////////////////////    PARSING RETROFIT TAMPIL DATA
    private void getData() {
        swipeRefreshLayout.setRefreshing(true);

        Bundle bundle = getIntent().getExtras();
        idKategori     = bundle.getString("idCategory");
        Toast.makeText(this, "id"+idKategori, Toast.LENGTH_SHORT).show();
        mApiService = RetroClient.getInstanceRetrofit();
        mApiService.getCategory(idKategori).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject object = new JSONObject(response.body().string());
                    JSONArray jsonArray  = object.optJSONArray("Category");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String id = jsonObject.optString("id");
                        judul = jsonObject.optString("judul");
                        String image1   = jsonObject.optString("image1");
                        String volume = jsonObject.optString("volume");
                        String harga = jsonObject.optString("harga");
                        String created_at = jsonObject.optString("created_at");
                        String imageuser = jsonObject.getString("user_image");
                        String storename = jsonObject.getString("store_name");

                        tvJudul.setText(judul);

                        Iklan iklan = new Iklan();
                        iklan.setId(id);
                        iklan.setJudul(judul);
                        iklan.setImage1(image1);
                        iklan.setCreated_at(created_at);
                        iklan.setVolume(volume);
                        iklan.setHarga(harga);
                        iklan.setUser_image(imageuser);
                        iklan.setStore_name(storename);

                        kategoris.add(iklan);
                        AdapterIklan adapter = new AdapterIklan(KategoriIklanActivity.this, kategoris);
                        recyclerView.setAdapter(adapter);

                    }
                    swipeRefreshLayout.setRefreshing(false);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onRefresh() {
        getData();
    }
}
