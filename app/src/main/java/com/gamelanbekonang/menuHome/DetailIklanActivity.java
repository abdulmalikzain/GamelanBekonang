package com.gamelanbekonang.menuHome;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.gamelanbekonang.R;
import com.squareup.picasso.Picasso;

public class DetailIklanActivity extends AppCompatActivity {
    private String gambarIklan, noTelp;

    private FloatingActionButton fabTelpMess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_iklan);

        Bundle bundle = getIntent().getExtras();
        final String judul = bundle.getString("judul");
//        tvDetUsername.setText(bundle.getString("username"));
//        tvDetWaktu.setText(bundle.getString("waktu"));
//        tvDetStatus.setText(bundle.getString("status"));
//        tvDetTujuan.setText(bundle.getString("tujuan"));
        gambarIklan = bundle.getString("image");
        noTelp = "085226152856";

        final Toolbar toolbar = findViewById(R.id.toolbar);
        fabTelpMess = findViewById(R.id.FAB_telp_mess);

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
        Picasso.with(getApplication()).load("http://bekonang-store.000webhostapp.com/images/"+gambarIklan).centerCrop().resize(1600, 850).error(R.mipmap.ic_launcher).into(imageView);
    }

    //button back toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()== android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}
