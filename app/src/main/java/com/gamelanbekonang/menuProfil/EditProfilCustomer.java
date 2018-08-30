package com.gamelanbekonang.menuProfil;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gamelanbekonang.MainActivity;
import com.gamelanbekonang.R;
import com.gamelanbekonang.api.BaseApiService;
import com.gamelanbekonang.api.RetrofitClient;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.gamelanbekonang.api.BaseApiService.BASE_URL_IMAGE_USER;
import static com.gamelanbekonang.logRes.LoginActivity.my_shared_preferences;
import static com.gamelanbekonang.logRes.LoginActivity.session_status;

public class EditProfilCustomer extends AppCompatActivity {

    private Toolbar tabsEtprofilcs;
    private EditText etTokenepcs;
    private EditText etIdepcs;
    private CircleImageView cvProfilepcs;
    private EditText etNamapcs;
    private EditText etEmailpcs;
    private EditText etAlamatpcs;
    private EditText etNotelppcs;
    private Button btnSimpanpcs;
    private Toolbar mActionToolbar;
    private BaseApiService mApiService;
    private int MY_PERMISSIONS_REQUEST_FINE_LOCATION =1;
    private SharedPreferences sharedpreferences;
    private ProgressDialog progressDialog;
    private String img;
    private String imagePath;
    private String f;
    private String TAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profil_customer);
        initView();

        mActionToolbar = (Toolbar) findViewById(R.id.tabs_etprofilcs);
        setSupportActionBar(mActionToolbar);
        getSupportActionBar().setTitle("EDIT AKUN");

        mApiService = RetrofitClient.getUpdateProfilRetrofit();

        tabsEtprofilcs = (Toolbar) findViewById(R.id.tabs_etprofilcs);
        etTokenepcs = (EditText) findViewById(R.id.et_tokenepcs);
        etTokenepcs.setVisibility(View.INVISIBLE);
        etIdepcs = (EditText) findViewById(R.id.et_idepcs);
        etIdepcs.setVisibility(View.INVISIBLE);
        cvProfilepcs = (CircleImageView) findViewById(R.id.cv_profilepcs);
        etNamapcs = (EditText) findViewById(R.id.et_namapcs);
        etEmailpcs = (EditText) findViewById(R.id.et_emailpcs);
        etAlamatpcs = (EditText) findViewById(R.id.et_alamatpcs);
        etNotelppcs = (EditText) findViewById(R.id.et_notelppcs);
        btnSimpanpcs = (Button) findViewById(R.id.btn_simpanpcs);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        if (ContextCompat.checkSelfPermission(EditProfilCustomer.this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(EditProfilCustomer.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {

            } else {
                ActivityCompat.requestPermissions(EditProfilCustomer.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_FINE_LOCATION);
            }
        }
        sharedpreferences = getSharedPreferences(my_shared_preferences, MODE_PRIVATE);
        String name = (sharedpreferences.getString("name", ""));
        etNamapcs.setText(name);
        String email = (sharedpreferences.getString("email", ""));
        etEmailpcs.setText(email);
        String address = (sharedpreferences.getString("address", ""));
        etAlamatpcs.setText(address);
        String notelp = (sharedpreferences.getString("notelp", ""));
        etNotelppcs.setText(notelp);
        String image = (sharedpreferences.getString("image",""));
        Picasso.with(this)
                .load(BASE_URL_IMAGE_USER+image)
                .placeholder(R.drawable.ic_akun)
                .into(cvProfilepcs);

        String id = (sharedpreferences.getString("id", ""));
        etIdepcs.setText(id);
        String token = (sharedpreferences.getString("token",""));
        etTokenepcs.setText(token);
        Log.d(TAG, "token: "+token);
        btnSimpanpcs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UbahDataCs();
            }
        });
        cvProfilepcs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooseGallery();
            }
        });
    }



    private void UbahDataCs() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Mohon Tunggu...");
        progressDialog.show();
        final String token = etTokenepcs.getText().toString();
        final String id = etIdepcs.getText().toString();
        final String name = etNamapcs.getText().toString();
        final String email = etEmailpcs.getText().toString();
        final String notelp = etNotelppcs.getText().toString();
        final String address = etAlamatpcs.getText().toString();

        mApiService.updateProfile(token,id,etNamapcs.getText().toString()
                ,etEmailpcs.getText().toString()
                ,etNotelppcs.getText().toString()
                ,etAlamatpcs.getText().toString()
                , img)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                       if (response.isSuccessful()){
                           progressDialog.dismiss();
                           SharedPreferences sp = getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);

                           SharedPreferences.Editor editor = sp.edit();
                           editor.putBoolean(session_status, true);
                           editor.putString("name", name);
                           editor.putString("email", email);
                           editor.putString("address", address);
                           editor.putString("notelp", notelp);
                           editor.commit();
//                                Log.e("onResponse: ",messeage );
                           Toast.makeText(EditProfilCustomer.this, ("Edit Profil Berhasil"), Toast.LENGTH_SHORT).show();

                           Intent intent = new Intent(EditProfilCustomer.this, MainActivity.class);
                           startActivity(intent);
                       }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        progressDialog.dismiss();
                    }
                });
    }
    private void showFileChooseGallery() {
        Intent foto = new Intent(Intent.ACTION_PICK);
        foto.setType("image/*");
        startActivityForResult(Intent.createChooser(foto, "Pilih Foto"), 100);
    }
    private void initView() {

    }
}
