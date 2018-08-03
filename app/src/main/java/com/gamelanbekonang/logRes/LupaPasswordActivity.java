package com.gamelanbekonang.logRes;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gamelanbekonang.R;
import com.gamelanbekonang.api.BaseApiService;
import com.gamelanbekonang.api.UtilsApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LupaPasswordActivity extends AppCompatActivity {

    private EditText etElupapass;
    private Button btnSuccess;
    private Toolbar mActionToolbar;
    private Context mContext;
    private BaseApiService mApiService;
    private ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lupa_password);

        mActionToolbar = (Toolbar) findViewById(R.id.tabs_lupas);
        setSupportActionBar(mActionToolbar);
        getSupportActionBar().setTitle("LUPA PASSWORD");

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        mContext =this;
        mApiService = UtilsApi.getAPIService();
        initView();
    }

    private void initView() {
        etElupapass = (EditText) findViewById(R.id.et_elupapass);
        btnSuccess = (Button) findViewById(R.id.btn_success);

        btnSuccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading = ProgressDialog.show(mContext, null, "Harap Tunggu...", true, false);
                recovery();
//                String urlPass = "http://gamelanwirun.com/api/v1/user/recover";
//                Intent ipass = new Intent(Intent.ACTION_VIEW);
//                ipass.setData(Uri.parse(urlPass));
//                Toast.makeText(mContext, "Silahkan Cek Email Andaw", Toast.LENGTH_SHORT).show();
//                startActivity(ipass);
            }
        });
    }

    private void recovery() {
        mApiService.lupapass(etElupapass.getText().toString())
                .enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                if (response.isSuccessful());
//                loading.dismiss();
//                try {
//                    JSONObject object = new JSONObject(response.body().string());
//                    if (!object.getString("msg").equals("401")) {
//                        String success = object.getString("msg");
                        Toast.makeText(mContext, "Email reset password telah dikirim! Silakan cek email Anda.", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(mContext, LoginActivity.class));
//                    }else {
//                        String gagal_bung = object.getString("msg");
//                        Toast.makeText(mContext, gagal_bung, Toast.LENGTH_SHORT).show();
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                    Toast.makeText(mContext, "GAGAL  RESET", Toast.LENGTH_SHORT).show();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(mContext, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}
