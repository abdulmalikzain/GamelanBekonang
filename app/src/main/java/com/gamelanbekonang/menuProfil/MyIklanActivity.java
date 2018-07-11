package com.gamelanbekonang.menuProfil;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.gamelanbekonang.MainActivity;
import com.gamelanbekonang.R;
import com.gamelanbekonang.adapter.AdapterIklan;
import com.gamelanbekonang.adapter.AdapterMyIklan;
import com.gamelanbekonang.api.ApiService;
import com.gamelanbekonang.api.BaseApiService;
import com.gamelanbekonang.api.RetroClient;
import com.gamelanbekonang.api.RetrofitClient;
import com.gamelanbekonang.api.UtilsApi;
import com.gamelanbekonang.beans.Iklan;

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

public class MyIklanActivity extends AppCompatActivity {

    private ArrayList<Iklan> myiklan ;
    private AdapterMyIklan adapter;
    private RecyclerView recyclerView;
    private Toolbar mActionToolbar;
    private BaseApiService mBaseApiService;
    private String token, idmyi;
    private EditText et_tokenmyi, et_idmyik;
    private SharedPreferences sharedpreferences;
    private String TAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_iklan);

        mActionToolbar = (Toolbar) findViewById(R.id.tabs_myiklan);
        setSupportActionBar(mActionToolbar);
        getSupportActionBar().setTitle("Iklan Saya");

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        SharedPreferences sharedpreferences = getSharedPreferences(my_shared_preferences, MODE_PRIVATE);
        token = (sharedpreferences.getString("token", ""));
//        et_tokenmyi.setText(token);
        idmyi = (sharedpreferences.getString("id", ""));
        Log.d(TAG,"Munculllll: "+token);


//        et_tokenmyi =(EditText) findViewById(R.id.et_tokenmyi);
//        et_tokenmyi.setVisibility(View.INVISIBLE);

        mBaseApiService = UtilsApi.getAPIService();
        initView();
        myiklan = new ArrayList<>();
    }

    private void initView() {
        recyclerView = findViewById(R.id.rv_myiklan);
        recyclerView.setHasFixedSize(true);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(layoutManager);
//        GridLayoutManager gridLayoutManager;
        GridLayoutManager gridLayoutManager = new GridLayoutManager(MyIklanActivity.this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        getData();


//        et_idmyik = (EditText) findViewById(R.id.et_idmyiklan);


    }

    private void getData() {
        BaseApiService baseApiService  = RetrofitClient.getDataMyIklan();
//        final String token = et_tokenmyi.getText().toString();
                baseApiService.MyData(token).enqueue(new Callback<ResponseBody>() {
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

                            myiklan.add(iklan);
                            AdapterMyIklan adapter = new AdapterMyIklan(MyIklanActivity.this, myiklan);
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
