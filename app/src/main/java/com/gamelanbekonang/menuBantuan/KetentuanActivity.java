package com.gamelanbekonang.menuBantuan;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import com.gamelanbekonang.R;

public class KetentuanActivity extends AppCompatActivity {

    private Toolbar mActionToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ketentuan);

        mActionToolbar = (Toolbar) findViewById(R.id.tabs_ketentuan);
        setSupportActionBar(mActionToolbar);
        getSupportActionBar().setTitle("KETENTUAN UMUM");

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()== android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}

