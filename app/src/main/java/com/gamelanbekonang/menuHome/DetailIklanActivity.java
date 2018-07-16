package com.gamelanbekonang.menuHome;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.renderscript.Sampler;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.gamelanbekonang.MainActivity;
import com.gamelanbekonang.R;
import com.gamelanbekonang.adapter.AdapterIklan;
import com.gamelanbekonang.api.ApiService;
import com.gamelanbekonang.api.BaseApiService;
import com.gamelanbekonang.api.RetroClient;
import com.gamelanbekonang.api.RetrofitClient;
import com.gamelanbekonang.api.UtilsApi;
import com.gamelanbekonang.beans.Iklan;
import com.gamelanbekonang.logRes.LoginActivity;
import com.gamelanbekonang.menuProfil.InformasiPublikActivity;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.gamelanbekonang.logRes.LoginActivity.my_shared_preferences;

public class DetailIklanActivity extends AppCompatActivity {
    private String gambarIklan, noTelp, id, userId, token;
    private ToggleButton tbAddfavorite;
    private ApiService apiService;
    private Context mContext;
    private CircleImageView civFotoProfil;
    private TextView tvLihatProfil, tvUsername, tvMember, tvJudulbarang, tvDeskripsi, tvDilihat, tvDihubungi, tvStok, tvAlamat;
    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbar;
//    private FloatingActionButton fabTelpMess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_iklan);

        tvLihatProfil = findViewById(R.id.lihatprofil_detiliklan);
        toolbar = findViewById(R.id.toolbar);
//        fabTelpMess = findViewById(R.id.FAB_telp_mess);
        tbAddfavorite = findViewById(R.id.tb_add_favorite);
        collapsingToolbar = findViewById(R.id.collapsing_toolbar);
        tvUsername      = findViewById(R.id.username_detiliklan);
        tvMember        = findViewById(R.id.member_detiliklan);
        tvJudulbarang   = findViewById(R.id.judulbarang_detiliklan);
        tvDeskripsi     = findViewById(R.id.deskripsi_detiliklan);
        tvDilihat       = findViewById(R.id.dilihat_detiliklan);
        tvDihubungi     = findViewById(R.id.dihubungi_detiliklan);
        tvStok          = findViewById(R.id.stok_detiliklan);
        tvAlamat        = findViewById(R.id.alamat_detiliklan);

        Bundle bundle = getIntent().getExtras();
        id     = bundle.getString("id");

        SharedPreferences sp = getApplicationContext().getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);
        token = (sp.getString("token", ""));

        tvLihatProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DetailIklanActivity.this, InformasiPublikActivity.class));
            }
        });

        tbAddfavorite.setChecked(false);
        tbAddfavorite.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_favorite_border));
        tbAddfavorite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    if (token.equals(null)){
                        postFavorite();
                        tbAddfavorite.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_favorite_kuning));
                        Toast.makeText(DetailIklanActivity.this, "iklan ditambahkan ke favorite", Toast.LENGTH_SHORT).show();
                    }else {
                        alertLogin();
                    }
                }
                else {
                    tbAddfavorite.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_favorite_border));
                    Toast.makeText(DetailIklanActivity.this, "diahpus dari Favorite", Toast.LENGTH_SHORT).show();
                }
            }
        });


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        collapsingToolbar.setTitle("aa");

//        loadBackdrop();

//        fabTelpMess.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //Creating the instance of PopupMenu
//                PopupMenu popup = new PopupMenu(DetailIklanActivity.this, fabTelpMess);
//                //Inflating the Popup using xml file
//                popup.getMenuInflater()
//                        .inflate(R.menu.popup_telp, popup.getMenu());
//
//                //registering popup with OnMenuItemClickListener
//                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                    public boolean onMenuItemClick(MenuItem item) {
//
//                        switch (item.getItemId()){
//                            case R.id.popup_telephone :
//                                Intent callIntent = new Intent(Intent.ACTION_CALL);
//                                callIntent.setData(Uri.parse("tel:" +noTelp));
//
//                                if (ActivityCompat.checkSelfPermission(DetailIklanActivity.this,
//                                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//                                }
//                                startActivity(callIntent);
//                                return true;
//
//                            case R.id.popup_message :
//                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", noTelp,
//                                        null)));
//
//                                return true;
//                        }
//                        return false;
//                    }
//                });
//
//                popup.show(); //showing popup menu
//            }
//        }); //closing the setOnClickListener method

//        apiService = UtilsApi.getAPICount();
        viewCount();
        getDataIklanId();
//
    }

    private void alertLogin(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Belum Login");
        builder.setMessage("anda harus login lebih dahulu")
                .setIcon(R.mipmap.ic_icon_gw)
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(DetailIklanActivity.this, LoginActivity.class);
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

    //button back toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()== android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    private void getDataIklanId(){
        apiService = RetroClient.getInstanceRetrofit();
        apiService.getDataId(id)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            JSONObject object = new JSONObject(response.body().string());
                            JSONObject jsonObject = object.optJSONObject("iklan");
                            String judul = jsonObject.optString("judul");
                            String image1   = jsonObject.optString("image1");
                            String image2   = jsonObject.optString("image2");
                            String image3   = jsonObject.optString("image3");
                            String image4   = jsonObject.optString("image4");
                            String image5   = jsonObject.optString("image5");
                            String deskripsi = jsonObject.optString("deskripsi");
                            String volume   = jsonObject.optString("volume");
                            String stock    = jsonObject.optString("stock");
                            String harga    = jsonObject.optString("harga");
                            String created_at = jsonObject.optString("created_at");
                            String view_count = jsonObject.optString("view_count");
                            String contact_count = jsonObject.optString("contact_count");

                            JSONObject jsonObject1 = jsonObject.optJSONObject("users");
                            String fotoprofil   = jsonObject1.getString("image");
                            String nama = jsonObject1.optString("name");
                            String notelp       = jsonObject1.optString("notelp");
                            String address      = jsonObject1.optString("address");
                            String store_name   = jsonObject1.optString("store_name");

                            tvJudulbarang.setText(judul);
                            tvDeskripsi.setText(deskripsi);
                            tvDilihat.setText(view_count);
                            tvDihubungi.setText(contact_count);
                            tvStok.setText(stock);
                            tvAlamat.setText(address);

                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.getMessage());
                        Toast.makeText(mContext, "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void viewCount() {
        apiService = RetroClient.getInstanceRetrofit();
        apiService.viewCount(id, "PATCH")
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            Log.i("debug", "onResponse: BERHASIL");
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.getMessage());
                    }
                });
    }

    private void postFavorite(){
        apiService = RetroClient.getInstanceRetrofit();
        apiService.postFavorite(token, userId,  id)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            Log.i("debug", "onResponse: BERHASIL");
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.getMessage());
                        Toast.makeText(mContext, "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();
                    }
                });
    }


}
