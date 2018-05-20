package com.gamelanbekonang.menuHome;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
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
import android.widget.Toast;
import android.widget.ToggleButton;

import com.gamelanbekonang.MainActivity;
import com.gamelanbekonang.R;
import com.gamelanbekonang.adapter.AdapterIklan;
import com.gamelanbekonang.api.ApiService;
import com.gamelanbekonang.api.BaseApiService;
import com.gamelanbekonang.api.RetroClient;
import com.gamelanbekonang.api.RetrofitClient;
import com.gamelanbekonang.beans.Iklan;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailIklanActivity extends AppCompatActivity {
    private String gambarIklan, noTelp, id;
    private ToggleButton tbAddfavorite;

    private FloatingActionButton fabTelpMess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_iklan);

        Bundle bundle = getIntent().getExtras();
        id     = bundle.getString("id");
        final String judul = bundle.getString("judul");
//        final String volume = bundle.getString("volume");
//        tvDetUsername.setText(bundle.getString("username"));
//        tvDetWaktu.setText(bundle.getString("waktu"));
//        tvDetStatus.setText(bundle.getString("status"));
//        tvDetTujuan.setText(bundle.getString("tujuan"));
        gambarIklan = bundle.getString("image");
        noTelp = "085226152856";
        Log.d("aa", "onCreate: "+id);

        final Toolbar toolbar = findViewById(R.id.toolbar);
        fabTelpMess = findViewById(R.id.FAB_telp_mess);
        tbAddfavorite = findViewById(R.id.tb_add_favorite);

        tbAddfavorite.setChecked(false);
        tbAddfavorite.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_favorite_border));
        tbAddfavorite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    tbAddfavorite.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_favorite_kuning));
                    Toast.makeText(DetailIklanActivity.this, "iklan ditambahkan ke favorite", Toast.LENGTH_SHORT).show();
                }
                else {
                    tbAddfavorite.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_favorite_border));
                    Toast.makeText(DetailIklanActivity.this, "diahpus dari Favorite", Toast.LENGTH_SHORT).show();
                }
            }
        });


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbar = findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(judul);

        loadBackdrop();

        fabTelpMess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(DetailIklanActivity.this, fabTelpMess);
                //Inflating the Popup using xml file
                popup.getMenuInflater()
                        .inflate(R.menu.popup_telp, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()){
                            case R.id.popup_telephone :
                                Intent callIntent = new Intent(Intent.ACTION_CALL);
                                callIntent.setData(Uri.parse("tel:" +noTelp));

                                if (ActivityCompat.checkSelfPermission(DetailIklanActivity.this,
                                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                }
                                startActivity(callIntent);
                                return true;

                            case R.id.popup_message :
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", noTelp,
                                        null)));

                                return true;
                        }
                        return false;
                    }
                });

                popup.show(); //showing popup menu
            }
        }); //closing the setOnClickListener method

    }

    private void loadBackdrop() {
        final ImageView imageView = findViewById(R.id.backdrop);
        Picasso.with(getApplication()).load(BaseApiService.BASE_URL_IMAGE+gambarIklan).centerCrop().resize(1600, 850).error(R.mipmap.ic_launcher).into(imageView);
    }

    //button back toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()== android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }


}
