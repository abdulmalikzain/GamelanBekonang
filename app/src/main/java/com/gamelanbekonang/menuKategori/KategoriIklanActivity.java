package com.gamelanbekonang.menuKategori;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.gamelanbekonang.R;
import com.gamelanbekonang.adapter.AdapterKategori;
import com.gamelanbekonang.api.ApiService;
import com.gamelanbekonang.api.BaseApiService;
import com.gamelanbekonang.api.RetroClient;
import com.gamelanbekonang.api.RetrofitClient;
import com.gamelanbekonang.beans.Iklan;
import com.gamelanbekonang.beans.Kategori;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class KategoriIklanActivity extends AppCompatActivity {

    private Toolbar mActionToolbar;
    private ArrayList<Kategori> kategoris;
    private AdapterKategori adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kategori_iklan);

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

        kategoris = new ArrayList<>();
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
        ApiService apiService  = RetrofitClient.getInstanceRetrofit();
        apiService.getData().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject object = new JSONObject(response.body().string());
                    JSONArray jsonArray  = object.optJSONArray("iklan");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String id = jsonObject.optString("id");
                        String user_id = jsonObject.optString("user_id");
                        String judul = jsonObject.optString("judul");
                        String deskripsi = jsonObject.optString("deskripsi");
                        String volume = jsonObject.optString("volume");
                        String stock     = jsonObject.optString("stock");
                        String jenis = jsonObject.optString("jenis");
                        String harga = jsonObject.optString("harga");
                        String created_at = jsonObject.optString("created_at");
                        String view_count   = jsonObject.optString("view_count");

                        JSONObject jsonObject1 = jsonObject.optJSONObject("users");
                        String imageuser = jsonObject1.getString("image");
                        String nameuser = jsonObject1.getString("name");
                        String emailuser = jsonObject1.getString("email");
                        String notelpuser = jsonObject1.getString("notelp");

                        JSONArray jsonArray1 = jsonObject.getJSONArray("photos");
                        for (int j = 0; j < jsonArray1.length(); j++) {
                            JSONObject jsonObject2 = jsonArray1.getJSONObject(i);
                            String filename = jsonObject2.optString("filename");

                            Iklan iklan = new Iklan();
                            iklan.setId(id);
                            iklan.setUserId(user_id);
                            iklan.setJudul(judul);
                            iklan.setCreated_at(created_at);
                            iklan.setDeskripsi(deskripsi);
                            iklan.setVolume(volume);
                            iklan.setStok(stock);
                            iklan.setHarga(harga);
                            iklan.setJenis(jenis);
                            iklan.setImage(imageuser);
                            iklan.setName(nameuser);
                            iklan.setEmail(emailuser);
                            iklan.setNotelp(notelpuser);
                            iklan.setFileName(filename);
//                            kategoris.add(iklan);
                            AdapterKategori adapter = new AdapterKategori(KategoriIklanActivity.this, kategoris);
                            recyclerView.setAdapter(adapter);
                        }

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
