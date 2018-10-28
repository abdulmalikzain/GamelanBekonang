package com.gamelanbekonang.menuHome;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
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
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.gamelanbekonang.R;
import com.gamelanbekonang.api.ApiService;
import com.gamelanbekonang.api.RetroClient;
import com.gamelanbekonang.logRes.LoginActivity;
import com.gamelanbekonang.menuProfil.InformasiPublikActivity;
import com.github.clans.fab.FloatingActionButton;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.gamelanbekonang.logRes.LoginActivity.my_shared_preferences;

public class DetailIklanActivity extends AppCompatActivity {
    private String gambarIklan, noTelp, id, token, imageSlider1, judulBarang, email, id_user, idIklanquery;
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
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_iklan);

        tvLihatProfil = findViewById(R.id.lihatprofil_detiliklan);
        toolbar = findViewById(R.id.toolbar);
        tbAddfavorite = findViewById(R.id.tb_add_favorite);
        collapsingToolbar = findViewById(R.id.judulbarang_detiliklan);
        tvUsername      = findViewById(R.id.username_detiliklan);
        tvMember        = findViewById(R.id.member_detiliklan);
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
        civFotoProfil   = findViewById(R.id.fotouser_detiliklan);

        Bundle bundle = getIntent().getExtras();
        id     = bundle.getString("idiklan");

        SharedPreferences sp = getApplicationContext().getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);
        token = (sp.getString("token", ""));
        id_user = (sp.getString("id", ""));

        tvLihatProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailIklanActivity.this, InformasiPublikActivity.class);
                intent.putExtra("id_penjual", tvUserId.getText().toString().trim());
                startActivity(intent);
            }
        });


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (token.equals("")){
            getDataIklanId();
        }else {
            getDataLog();
        }

        viewCount();

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
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
//        pDialog.setMessage("Logging in ...");
        showDialog();

        apiService = RetroClient.getInstanceRetrofit();
        apiService.getDataId(id)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            JSONObject object = new JSONObject(response.body().string());
                            JSONObject jsonObject = object.optJSONObject("iklan");
                            idIklanquery = jsonObject.optString("id");
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
                            String idPenjual    = jsonObject1.getString("id");
                            String fotoprofil   = jsonObject1.getString("image");
                            String nama         = jsonObject1.optString("name");
                            noTelp              = jsonObject1.optString("notelp");
                            email               = jsonObject1.optString("email");
                            String address      = jsonObject1.optString("address");
                            String store_name   = jsonObject1.optString("store_name");

                            tvUserId.setText(idPenjual);
                            collapsingToolbar.setTitle(judulBarang);
                            tvUsername.setText(store_name);
                            tvDeskripsi.setText(deskripsi);
                            tvDilihat.setText(view_count);
                            tvDihubungi.setText(contact_count);
                            tvStok.setText(stock);
                            tvAlamat.setText(address);
                            Picasso.with(getApplication()).load(ApiService.BASE_URL_IMAGEUSER+fotoprofil)
                                    .placeholder(R.drawable.ic_launcher_background).into(civFotoProfil);


                            tbAddfavorite.setChecked(false);
                            tbAddfavorite.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_favorite_border));
                            tbAddfavorite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                    if (isChecked) {

//                            postFavorite();
                                            delFavorite();
                                            tbAddfavorite.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_favorite_kuning));
                                            Toast.makeText(DetailIklanActivity.this, "iklan ditambahkan ke favorite", Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        tbAddfavorite.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_favorite_border));
//                        delFavorite();
                                        Toast.makeText(DetailIklanActivity.this, "diahapus dari Favorite", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });


                            hideDialog();

                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(mContext, "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();
                        hideDialog();
                    }
                });
    }

    private void getDataLog(){
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        showDialog();

        apiService = RetroClient.getInstanceRetrofit();
        apiService.getDadtaLog(id, token)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            JSONObject object = new JSONObject(response.body().string());
                            final JSONObject jsonObject = object.optJSONObject("iklan");
                            idIklanquery = jsonObject.optString("id");
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

                            final JSONObject jsonObject1 = jsonObject.optJSONObject("users");
                            String idPenjual    = jsonObject1.getString("id");
                            String fotoprofil   = jsonObject1.getString("image");
                            String nama         = jsonObject1.optString("name");
                            noTelp              = jsonObject1.optString("notelp");
                            email               = jsonObject1.optString("email");
                            String address      = jsonObject1.optString("address");
                            String store_name   = jsonObject1.optString("store_name");

                            JSONArray jsonArray = object.optJSONArray("wishlists");
                            for (int i = 0; i < jsonArray.length() ; i++) {
                            JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                            String idWishlists = jsonObject2.getString("id");
                            String idIklanWishlists = jsonObject2.getString("iklan_id");

                            if (!id.equals(idIklanWishlists)){
                                tbAddfavorite.setChecked(false);
                                tbAddfavorite.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_favorite_border));
                                tbAddfavorite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                        if (isChecked) {
                                            postFavorite();
                                            tbAddfavorite.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_favorite_kuning));
                                            Toast.makeText(DetailIklanActivity.this, "iklan ditambahkan ke favorite", Toast.LENGTH_SHORT).show();
                                        }
                                        else {
                                            tbAddfavorite.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_favorite_border));
                                            delFavorite();
                                            Toast.makeText(DetailIklanActivity.this, "diahapus dari Favorite", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }else {
                                tbAddfavorite.setChecked(true);
                                tbAddfavorite.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_favorite_border));
                                tbAddfavorite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                        if (isChecked) {

                                            postFavorite();
                                            tbAddfavorite.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_favorite_kuning));
                                            Toast.makeText(DetailIklanActivity.this, "iklan ditambahkan ke favorite", Toast.LENGTH_SHORT).show();
                                        }
                                        else {
                                            tbAddfavorite.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_favorite_border));
                                            delFavorite();
                                            Toast.makeText(DetailIklanActivity.this, "diahapus dari Favorite", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }


                            }

                            tvUserId.setText(idPenjual);
                            collapsingToolbar.setTitle(judulBarang);
                            tvUsername.setText(store_name);
                            tvDeskripsi.setText(deskripsi);
                            tvDilihat.setText(view_count);
                            tvDihubungi.setText(contact_count);
                            tvStok.setText(stock);
                            tvAlamat.setText(address);
                            Picasso.with(getApplication()).load(ApiService.BASE_URL_IMAGEUSER+fotoprofil)
                                    .placeholder(R.drawable.ic_launcher_background).into(civFotoProfil);

                            hideDialog();

                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.getMessage());
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
        apiService.postFavorite(token, id_user,  id)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(mContext, "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void delFavorite(){
        apiService = RetroClient.getInstanceRetrofit();
        apiService.delFavorite(id, token, "DELETE")
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
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
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, email);
        intent.putExtra(Intent.EXTRA_SUBJECT, "Tanya Gamelan");
        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent, "Choose an email client"));
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
