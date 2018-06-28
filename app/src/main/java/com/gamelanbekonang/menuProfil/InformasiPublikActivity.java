package com.gamelanbekonang.menuProfil;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.EditText;

import com.gamelanbekonang.R;
import com.gamelanbekonang.adapter.AdapterIklan;
import com.gamelanbekonang.adapter.AdapterMyIklan;
import com.gamelanbekonang.api.BaseApiService;
import com.gamelanbekonang.api.RetrofitClient;
import com.gamelanbekonang.api.UtilsApi;
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

import static com.gamelanbekonang.logRes.LoginActivity.my_shared_preferences;

public class InformasiPublikActivity extends AppCompatActivity {
    private ArrayList<Iklan> infoiklan ;
    private AdapterMyIklan adapter;
    private RecyclerView recyclerView;
    private Toolbar mActionToolbar;
    private BaseApiService mBaseApiService;
    private String  idinfo;
    private EditText  et_idinfo;
    private SharedPreferences sharedpreferences;
    private String TAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informasi_publik);

        mActionToolbar = (Toolbar) findViewById(R.id.tabs_infopublik);
        setSupportActionBar(mActionToolbar);
        getSupportActionBar().setTitle("Iklan Penjual");

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        SharedPreferences sharedpreferences = getSharedPreferences(my_shared_preferences, MODE_PRIVATE);
        idinfo = (sharedpreferences.getString("id",""));

        mBaseApiService = UtilsApi.getAPIService();
        initView();
        infoiklan = new ArrayList<>();
    }

    private void initView() {
        recyclerView = findViewById(R.id.rv_infopublik);
        recyclerView.setHasFixedSize(true);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(layoutManager);
        final LinearLayoutManager mLayoutManager;
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        InfoData();

    }

    private void InfoData() {
        BaseApiService baseApiService  = RetrofitClient.getDataMyIklan();
        baseApiService.getInfo(idinfo).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject object = new JSONObject(response.body().string());
                        JSONArray jsonArray = object.getJSONArray("my_iklan");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String id = jsonObject.optString("id");
                            String judul = jsonObject.optString("judul");
                            String image1 = jsonObject.optString("image1");
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

                            infoiklan.add(iklan);
                            AdapterIklan adapter = new AdapterIklan(InformasiPublikActivity.this, infoiklan);
                            recyclerView.setAdapter(adapter);

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
    //button back toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()== android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}
