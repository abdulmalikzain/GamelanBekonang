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

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
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
import com.github.clans.fab.FloatingActionButton;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

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
    private String gambarIklan, noTelp, id, idUser, token, imageSlider1, judulBarang, email;
    private ToggleButton tbAddfavorite;
    private ApiService apiService;
    private Context mContext;
    private CircleImageView civFotoProfil;
    private TextView tvLihatProfil, tvUsername, tvMember, tvJudulbarang, tvDeskripsi, tvDilihat, tvDihubungi, tvStok, tvAlamat,
            tvUserId;
    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbar;
    private SliderLayout sliderLayout;
    private FloatingActionButton fabTelepon, fabSms, fabEmail;
//    private FloatingActionButton fabTelpMess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_iklan);

        tvLihatProfil = findViewById(R.id.lihatprofil_detiliklan);
        toolbar = findViewById(R.id.toolbar);
//        fabTelpMess = findViewById(R.id.FAB_telp_mess);
        tbAddfavorite = findViewById(R.id.tb_add_favorite);
        collapsingToolbar = findViewById(R.id.judulbarang_detiliklan);
        tvUsername      = findViewById(R.id.username_detiliklan);
        tvMember        = findViewById(R.id.member_detiliklan);
//        tvJudulbarang   = findViewById(R.id.judulbarang_detiliklan);
        tvDeskripsi     = findViewById(R.id.deskripsi_detiliklan);
        tvDilihat       = findViewById(R.id.dilihat_detiliklan);

        tvDihubungi     = findViewById(R.id.dihubungi_detiliklan);
        tvStok          = findViewById(R.id.stok_detiliklan);
        tvAlamat        = findViewById(R.id.alamat_detiliklan);
        sliderLayout    = findViewById(R.id.slider_detailiklan);
        fabTelepon      = findViewById(R.id.fab_telepon_detailiklan);
        fabSms          = findViewById(R.id.fab_sms_detailiklan);
        fabEmail        = findViewById(R.id.fab_email_detailiklan);
        tvUserId        = findViewById(R.id.userid_detailiklan);

        Bundle bundle = getIntent().getExtras();
        id     = bundle.getString("id");

        SharedPreferences sp = getApplicationContext().getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);
        token = (sp.getString("token", ""));
        Log.d("token", "wwwwwww: "+token);

        tvLihatProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailIklanActivity.this, InformasiPublikActivity.class);
//                startActivity(new Intent(DetailIklanActivity.this, InformasiPublikActivity.class));
                intent.putExtra("idUser", tvUserId.getText().toString().trim());
                startActivity(intent);
            }
        });

        tbAddfavorite.setChecked(false);
        tbAddfavorite.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_favorite_border));
        tbAddfavorite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    if (token.equals("")){
                        alertLogin();
                    }else {
                        postFavorite();
                        tbAddfavorite.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_favorite_kuning));
                        Toast.makeText(DetailIklanActivity.this, "iklan ditambahkan ke favorite", Toast.LENGTH_SHORT).show();
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

        // Load image dari URL
        HashMap<String,String> url_maps = new HashMap<String, String>();
        url_maps.put("Hannibal", ApiService.BASE_URL_IMAGEIKLAN+imageSlider1);
//        url_maps.put("Big Bang Theory", "http://tvfiles.alphacoders.com/100/hdclearart-10.png");
//        url_maps.put("House of Cards", "http://cdn3.nflximg.net/images/3093/2043093.jpg");
//        url_maps.put("Game of Thrones", "http://images.boomsbeat.com/data/images/full/19640/game-of-thrones-season-4-jpg.jpg");
        for(String name : url_maps.keySet()){
            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(url_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit);
            //add your extra information
//            textSliderView.bundle(new Bundle());
//            textSliderView.getBundle()
//                    .putString("extra",name);
//            sliderLayout.addSlider(textSliderView);
        }
        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion);
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderLayout.setCustomAnimation(new DescriptionAnimation());
        sliderLayout.setDuration(10000);

        viewCount();
        getDataIklanId();

        fabTelepon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                telephone();
            }
        });

        fabSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                kirimSms();
            }
        });

        fabEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                kirimEmail();
            }
        });
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
                            judulBarang     = jsonObject.optString("judul");
                            imageSlider1    = jsonObject.optString("image1");
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
                            idUser = jsonObject.optString("id");
                            String fotoprofil   = jsonObject1.getString("image");
                            String nama         = jsonObject1.optString("name");
                            noTelp              = jsonObject1.optString("notelp");
                            email               = jsonObject1.optString("email");
                            String address      = jsonObject1.optString("address");
                            String store_name   = jsonObject1.optString("store_name");

                            collapsingToolbar.setTitle(judulBarang);
                            tvDeskripsi.setText(deskripsi);
                            tvDilihat.setText(view_count);
                            tvDihubungi.setText(contact_count);
                            tvStok.setText(stock);
                            tvAlamat.setText(address);
                            tvUserId.setText(idUser);

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
        Log.d("postFavorite", "postFavorite: "+idUser+id);
        apiService.postFavorite(token, idUser,  id)
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

    //////////////////telephone
    private void telephone(){
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" +noTelp));

        if (ActivityCompat.checkSelfPermission(DetailIklanActivity.this,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
        }
        startActivity(callIntent);
    }

    ////////////////////SMS
    private void kirimSms(){
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", noTelp,
                null)));
    }

    private void kirimEmail(){
        Intent sendIntent = new Intent(Intent.ACTION_VIEW);
        sendIntent.setType("plain/text");
        sendIntent.setData(Uri.parse("test@gmail.com"));
        sendIntent.setClassName("com.google.android.gm", "com.gamelanbekonang.menuHome.DetailIklanActivity");
        sendIntent.putExtra(Intent.EXTRA_EMAIL, new String[] { "test@gmail.com" });
//        sendIntent.putExtra(Intent.EXTRA_SUBJECT, "test");
//        sendIntent.putExtra(Intent.EXTRA_TEXT, "hello. this is a message sent from my demo app :-)");
        startActivity(sendIntent);
    }
}
