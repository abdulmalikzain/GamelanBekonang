package com.gamelanbekonang.menuKategori;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.gamelanbekonang.R;
import com.gamelanbekonang.adapter.AdapterKenong;
import com.gamelanbekonang.api.ApiService;
import com.gamelanbekonang.api.BaseApiService;
import com.gamelanbekonang.api.RetroClient;
import com.gamelanbekonang.beans.Kategori;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class KenongActivity extends AppCompatActivity {

    private Toolbar mActionToolbar;
    private ArrayList<Kategori> kategoris;
    private AdapterKenong adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kenong);

        mActionToolbar = findViewById(R.id.toolbar_kenong);
        setSupportActionBar(mActionToolbar);
        getSupportActionBar().setTitle("Kenong");

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        recyclerView = findViewById(R.id.rv_kenong);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        loadJSON();


    }

    //button back toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()== android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    ////////////////////////////    PARSING RETROFIT TAMPIL DATA


    private void loadJSON(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseApiService.BASE_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService request = retrofit.create(ApiService.class);
        Call<RetroClient> call = request.getJSONCategory();
        call.enqueue(new Callback<RetroClient>() {
            @Override
            public void onResponse(Call<RetroClient> call, Response<RetroClient> response) {
                RetroClient jsonResponse = response.body();
                kategoris = new ArrayList<>(Arrays.asList(jsonResponse.getCategory()));
                adapter = new AdapterKenong(KenongActivity.this, kategoris);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<RetroClient> call, Throwable t) {
                Log.d("Error",t.getMessage());
            }
        });
    }
}
