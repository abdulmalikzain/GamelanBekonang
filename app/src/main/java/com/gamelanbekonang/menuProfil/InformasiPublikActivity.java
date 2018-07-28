package com.gamelanbekonang.menuProfil;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gamelanbekonang.MainActivity;
import com.gamelanbekonang.R;
import com.gamelanbekonang.adapter.AdapterIklan;
import com.gamelanbekonang.adapter.AdapterMyIklan;
import com.gamelanbekonang.api.BaseApiService;
import com.gamelanbekonang.api.RetrofitClient;
import com.gamelanbekonang.api.UtilsApi;
import com.gamelanbekonang.beans.Iklan;
import com.gamelanbekonang.logRes.LoginActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
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
    private Context mContext;
    private String image,name,email,notelp,address,store_name;
    private CircleImageView civ_informasipen;
    private TextView  namepen, emailpen, perusahaanpen, alamatpen;
    private EditText idpen;
    private String judul, image1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informasi_publik);

        mActionToolbar = (Toolbar) findViewById(R.id.tabs_infopublik);
        setSupportActionBar(mActionToolbar);
        getSupportActionBar().setTitle("Informasi Penjual");

//        civ_informasipen = findViewById(R.id.civ_informasipen);
//        idpen = findViewById(R.id.et_idinfo);
//        namepen = findViewById(R.id.tv_namapen);
//        emailpen = findViewById(R.id.tv_emailpen);
//        perusahaanpen = findViewById(R.id.tv_perusahaanpen);
//        alamatpen = findViewById(R.id.tv_alamatpen);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        SharedPreferences sharedpreferences = getSharedPreferences(my_shared_preferences, MODE_PRIVATE);
        idinfo = (sharedpreferences.getString("id",""));
        image = sharedpreferences.getString("image", null);
        name = sharedpreferences.getString("name", null);
        email = sharedpreferences.getString("email", null);
        notelp = sharedpreferences.getString("notelp",null);
        address = sharedpreferences.getString("address", null);
        store_name = sharedpreferences.getString("store_name", null);


        mBaseApiService = UtilsApi.getAPIService();
        initView();
        infoiklan = new ArrayList<>();
    }

    private void initView() {
        recyclerView = findViewById(R.id.rv_infopublik);
        recyclerView.setHasFixedSize(true);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(layoutManager);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(InformasiPublikActivity.this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
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
                        JSONObject infoObject = object.optJSONObject("user");
                        String id = infoObject.optString("id");
                        String image = infoObject.optString("image");
                        String name = infoObject.optString("name");
                        String email = infoObject.optString("email");
                        String notelp = infoObject.optString("notelp");
                        String address = infoObject.optString("address");
                        String created_at = infoObject.optString("created_at");
                        String store_name = infoObject.optString("store_name");
                        String store_description = infoObject.optString("store_description");



                        JSONArray infoArray1 = object.optJSONArray("iklans");
//                        String judul     = infoObject1.optString("judul");
//                        String image1    = infoObject1.optString("image1");
//                        String image2   = infoObject1.optString("image2");
//                        String image3   = infoObject1.optString("image3");
//                        String image4   = infoObject1.optString("image4");
//                        String image5   = infoObject1.optString("image5");
//                        String deskripsi = infoObject1.optString("deskripsi");
//                        String volume   = infoObject1.optString("volume");
//                        String stock    = infoObject1.optString("stock");
//                        String harga    = infoObject1.optString("harga");
//                        String created_at = infoObject1.optString("created_at");
//                        String view_count = infoObject1.optString("view_count");
//                        String contact_count = infoObject1.optString("contact_count");
//
//                        idpen.setText(id);
//                        namepen.setText(name);
//                        emailpen.setText(email);
//                        perusahaanpen.setText(store_name);
//                        alamatpen.setText(address);


                        Iklan iklan = new Iklan();
                        iklan.setId(id);
                        iklan.setJudul(judul);
                        iklan.setImage1(image1);
                        iklan.setCreated_at(created_at);
//                        iklan.setVolume(volume);
//                        iklan.setHarga(harga);
                        iklan.setUser_image(image);
                        iklan.setStore_name(store_name);

                            infoiklan.add(iklan);
                            AdapterIklan adapter = new AdapterIklan(InformasiPublikActivity.this, infoiklan);
                            recyclerView.setAdapter(adapter);

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
