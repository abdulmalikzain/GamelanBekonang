package com.gamelanbekonang.menuAkun;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import com.gamelanbekonang.R;
import com.gamelanbekonang.menuBantuan.CaraDaftarActivity;
import com.gamelanbekonang.menuBantuan.JualBeliActivity;
import com.gamelanbekonang.menuBantuan.KebijakanActivity;
import com.gamelanbekonang.menuBantuan.KetentuanActivity;

public class BantuanActivity extends AppCompatActivity {

    private Toolbar mActionToolbar;
    private TextView akun,tip, syarat, syaratU, privasi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bantuan);

        mActionToolbar = (Toolbar) findViewById(R.id.tabs_bantuan);
        setSupportActionBar(mActionToolbar);
        getSupportActionBar().setTitle("BANTUAN");

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        akun = findViewById(R.id.tv_akun);
        akun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BantuanActivity.this, CaraDaftarActivity.class);
                startActivity(intent);
            }
        });

        tip = findViewById(R.id.tv_tips);
        tip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BantuanActivity.this, JualBeliActivity.class);
                startActivity(intent);
            }
        });


        syaratU = findViewById(R.id.tv_syaratumum);
        syaratU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BantuanActivity.this, KetentuanActivity.class);
                startActivity(intent);
            }
        });

        privasi = findViewById(R.id.tv_privasi);
        privasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BantuanActivity.this, KebijakanActivity.class);
                startActivity(intent);
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
