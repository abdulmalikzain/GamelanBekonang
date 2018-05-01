package com.gamelanbekonang.menuBantuan;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.gamelanbekonang.R;

public class CaraDaftarActivity extends AppCompatActivity {

    private Toolbar mActionToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cara_daftar);


        mActionToolbar = (Toolbar) findViewById(R.id.tabs);
        setSupportActionBar(mActionToolbar);
        getSupportActionBar().setTitle("CARA DAFTAR");

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    //button back toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}
