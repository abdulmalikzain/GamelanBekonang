package com.gamelanbekonang.menuProfil;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gamelanbekonang.R;
import com.gamelanbekonang.api.BaseApiService;
import com.gamelanbekonang.api.UtilsApi;
import com.gamelanbekonang.logRes.LoginActivity;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static com.gamelanbekonang.logRes.LoginActivity.my_shared_preferences;
import static com.gamelanbekonang.logRes.LoginActivity.session_status;

public class GantiPasswordActivity extends AppCompatActivity {

    private Toolbar tabsGantipasssr;
    private EditText etTokenpsr;
    private EditText etIdprs;
    private EditText etPasswordsr;
    private EditText etNewpasssr;
    private EditText etConfrimpassr;
    private Button btnSimpanpasssr;
    private Toolbar mActionToolbar;
    private SharedPreferences sharedpreferences;
    private ProgressDialog progressDialog;
    private String TAG;
    private String token;
    private String current_password;
    private String new_password;
    private String new_password_confirmation;
    private BaseApiService mApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ganti_password);
        initView();

        mActionToolbar = (Toolbar) findViewById(R.id.tabs_changepasssr);
        setSupportActionBar(mActionToolbar);
        getSupportActionBar().setTitle("Change Password");

        mApiService = UtilsApi.getAPIService();
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        etTokenpsr = (EditText) findViewById(R.id.et_tokencpsr);
        etIdprs = (EditText) findViewById(R.id.et_idcpsr);
        etPasswordsr = (EditText) findViewById(R.id.et_passwordsr);
        etNewpasssr = (EditText) findViewById(R.id.et_newpasssr);
        etConfrimpassr = (EditText) findViewById(R.id.et_confrimpassr);
        btnSimpanpasssr = (Button) findViewById(R.id.btn_simpanpasssr);


        sharedpreferences = getSharedPreferences(my_shared_preferences, MODE_PRIVATE);
        String current_password = (sharedpreferences.getString("current_password", ""));
        String new_password = (sharedpreferences.getString("new_password", ""));
        String new_password_confirmation = (sharedpreferences.getString("new_password_confirmation", ""));


        String id = (sharedpreferences.getString("id", ""));
        etIdprs.setText(id);
        String token = (sharedpreferences.getString("token",""));
        etTokenpsr.setText(token);

        btnSimpanpasssr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UbahPassword();
            }
        });
    }

    private void UbahPassword() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Mohon Tunggu...");
        progressDialog.show();
        final String token = etTokenpsr.getText().toString();
        final String current_password = etPasswordsr.getText().toString();
        final String new_password = etNewpasssr.getText().toString();
        final String new_password_confirmation = etConfrimpassr.getText().toString();

        mApiService.ubahPassword(token,
                etPasswordsr.getText().toString().trim(),
                etNewpasssr.getText().toString(),
                etConfrimpassr.getText().toString())
                .enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    progressDialog.dismiss();
                    Log.d(TAG, "PLISSSS: "+token+etPasswordsr+etConfrimpassr);;

                    SharedPreferences sp = getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);

                    SharedPreferences.Editor editor = sp.edit();
                    editor.putBoolean(session_status, true);
                    editor.putString("token", token);
                    editor.putString("current_password", current_password);
                    editor.putString("new_password", new_password);
                    editor.putString("new_password_confirmation", new_password_confirmation);
                    editor.commit();
                    Toast.makeText(GantiPasswordActivity.this, ("Ganti Password Berhasil"), Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(GantiPasswordActivity.this, LoginActivity.class);
                    startActivity(intent);


                }
            }@Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        progressDialog.dismiss();
                    }
                });


    }

    private void initView() {

        sharedpreferences = getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);
        token = sharedpreferences.getString("token", null);
        current_password = sharedpreferences.getString("current_password", null);
        new_password = sharedpreferences.getString("new_password", null);
        new_password_confirmation = sharedpreferences.getString("new_password_confirmation", null);
    }

    //button back toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }


}
