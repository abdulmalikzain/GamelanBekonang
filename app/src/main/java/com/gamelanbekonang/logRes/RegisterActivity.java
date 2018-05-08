package com.gamelanbekonang.logRes;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private EditText etNama;
    private EditText etEmail;
    private EditText etPassword,etConfirm;
    private EditText etNotelp;
    private EditText etAddress;
    private Button btnRegister;
    private ProgressDialog loading;

    private Context mContext;
    private BaseApiService mApiService;
    private Toolbar mActionToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mActionToolbar = (Toolbar) findViewById(R.id.tabs_register);
        setSupportActionBar(mActionToolbar);
        getSupportActionBar().setTitle("REGISTER");

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        mContext = this;
        mApiService = UtilsApi.getAPIService();

        initComponents();
    }
    private void initComponents() {

        etNama = (EditText) findViewById(R.id.et_namar);
        etEmail = (EditText) findViewById(R.id.et_emailr);
        etNotelp = (EditText) findViewById(R.id.et_notelpr);
        etAddress = findViewById(R.id.et_addressr);
        etPassword = (EditText) findViewById(R.id.et_passwordr);
        etConfirm = (EditText) findViewById(R.id.et_confirmpasswordr);
        btnRegister = (Button) findViewById(R.id.btn_registerr);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading = ProgressDialog.show(mContext, null, "Harap Tunggu...", true, false);
                requestRegister();
            }
        });
    }

    private void requestRegister() {

        mApiService.registerRequest(etNama.getText().toString(),
                etEmail.getText().toString(), etNotelp.getText().toString(), etAddress.getText().toString(),
                etPassword.getText().toString(), etConfirm.getText().toString())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            Log.i("debug", "onResponse: BERHASIL");
                            loading.dismiss();
                            try {
                                JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                if (!jsonRESULTS.getString("msg").equals("404")){
                                    String perfect = jsonRESULTS.getString("msg");
                                    Toast.makeText(mContext, perfect, Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(mContext, LoginActivity.class));
                                } else {
                                    String error_message = jsonRESULTS.getString("msg");
                                    Toast.makeText(mContext, error_message, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(mContext, "Register Gagal", Toast.LENGTH_SHORT).show();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Log.i("debug", "onResponse: GA BERHASIL");
                            loading.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.getMessage());
                        Toast.makeText(mContext, "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();
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
