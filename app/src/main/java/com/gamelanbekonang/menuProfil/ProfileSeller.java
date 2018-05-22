package com.gamelanbekonang.menuProfil;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.gamelanbekonang.R;

import static com.gamelanbekonang.logRes.LoginActivity.my_shared_preferences;

public class ProfileSeller extends AppCompatActivity {

    private Toolbar mActionToolbar;
    private EditText etNamapsr;
    private EditText etEmailpsr;
    private EditText etNotelppsr;
    private EditText etAlamatpsr;
    private Button btnSimpanpsr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_seller);

        mActionToolbar = (Toolbar) findViewById(R.id.tabs_profilsr);
        setSupportActionBar(mActionToolbar);
        getSupportActionBar().setTitle("EDIT AKUN");

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        initView();
        btnSimpanpsr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                String nama = etNamapsr.getText().toString().trim();
//                String email = etEmailpsr.getText().toString().trim();
//                String notelp = etNotelppsr.getText().toString().trim();
//                String address = etAlamatpsr.getText().toString().trim();

                SharedPreferences sp = getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);

                String id = (sp.getString("id", ""));
                String name = (sp.getString("name", ""));
                etNamapsr.setText(name);
                String email = (sp.getString("email",""));
                etEmailpsr.setText(email);
                String notelp =(sp.getString("notelp",""));
                etNotelppsr.setText(notelp);
                String address = (sp.getString("address",""));
                etAlamatpsr.setText(address);
            }
        });
    }

    //button back toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    private void initView() {
        etNamapsr = (EditText) findViewById(R.id.et_namapsr);
        etEmailpsr = (EditText) findViewById(R.id.et_emailpsr);
        etNotelppsr = (EditText) findViewById(R.id.et_notelppsr);
        etAlamatpsr = (EditText) findViewById(R.id.et_alamatpsr);
        btnSimpanpsr = (Button) findViewById(R.id.btn_simpanpsr);
    }
}
