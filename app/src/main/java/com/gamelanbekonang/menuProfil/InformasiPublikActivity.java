package com.gamelanbekonang.menuProfil;

import android.app.Dialog;
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
import com.gamelanbekonang.adapter.AdapterInfoPublik;
import com.gamelanbekonang.adapter.AdapterMyIklan;
import com.gamelanbekonang.api.BaseApiService;
import com.gamelanbekonang.api.RetrofitClient;
import com.gamelanbekonang.api.UtilsApi;
import com.gamelanbekonang.beans.Iklan;
import com.gamelanbekonang.logRes.LoginActivity;
import com.google.gson.JsonObject;

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
    private AdapterInfoPublik adapter;
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
    private int i;
    private Dialog loading;
    private String id;
    private String id_user;
    private String id_iklans;


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
        Bundle bundle = getIntent().getExtras();
        id_user     = bundle.getString("user_id");
        Log.d(TAG, "Muncul: "+id_user);

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
        baseApiService.getInfo(id_user).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonRESULTS = new JSONObject(response.body().string());
                        JSONArray jsonArray = jsonRESULTS.getJSONObject("user").getJSONArray("iklans");
//                        if (!jsonRESULTS.getString("msg").equals("404")){
//                            String success =  jsonRESULTS.getString("msg");
//                            Toast.makeText(mContext, success, Toast.LENGTH_SHORT).show();
                            String id = jsonRESULTS.getJSONObject("user").getString("id");
                            String image = jsonRESULTS.getJSONObject("user").getString("image");
                            String nama = jsonRESULTS.getJSONObject("user").getString("name");
                            String email = jsonRESULTS.getJSONObject("user").getString("email");
                            String notelp = jsonRESULTS.getJSONObject("user").getString("notelp");
                            String address = jsonRESULTS.getJSONObject("user").getString("address");
                            String store_name = jsonRESULTS.getJSONObject("user").getString("store_name");
                            String created_at = jsonRESULTS.getJSONObject("user").getString("created_at");

                            for (int i = 0; i < jsonArray.length(); i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String id_user = jsonObject.optString("user_id");
                                String id_iklans = jsonObject.optString("id");
                                String judul = jsonObject.optString("judul");
                                String image1 = jsonObject.optString("image1");
                                String image2 = jsonObject.optString("image2");
                                String image3 = jsonObject.optString("image3");
                                String image4 = jsonObject.optString("image4");
                                String image5 = jsonObject.optString("image5");
                                String deskripsi = jsonObject.optString("deskripsi");
                                String volume = jsonObject.optString("volume");
                                String stock = jsonObject.optString("stock");
                                String harga = jsonObject.optString("harga");
//                            String created_at = jsonObject.optString("created_at");
                                String view_count = jsonObject.optString("view_count");
                                String contact_count = jsonObject.optString("contact_count");

                                Iklan iklan = new Iklan();
                                iklan.setId(id_iklans);
//                            iklan.setId(id_iklans);
                                iklan.setJudul(judul);
                                iklan.setImage1(image1);
                                iklan.setVolume(volume);
                                iklan.setHarga(harga);
                                iklan.setUser_image(image);
                                iklan.setStore_name(store_name);


                                infoiklan.add(iklan);
                                AdapterInfoPublik adapter = new AdapterInfoPublik(InformasiPublikActivity.this, infoiklan);
                                recyclerView.setAdapter(adapter);
                            }








//                        }


//                        JSONObject object = new JSONObject(response.body().string());
//                        JSONObject infoObject = object.optJSONObject("user");
//                        String id = infoObject.optString("id");
//                        String image = infoObject.optString("image");
//                        String name = infoObject.optString("name");
//                        String email = infoObject.optString("email");
//                        String notelp = infoObject.optString("notelp");
//                        String address = infoObject.optString("address");
//                        String created_at = infoObject.optString("created_at");
//                        String store_name = infoObject.optString("store_name");
//                        String store_description = infoObject.optString("store_description");



//                        JSONArray infoArray1 = object.optJSONArray("iklans");
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
