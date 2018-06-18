package com.gamelanbekonang.menuProfil;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import com.gamelanbekonang.api.UtilsApi;
import com.squareup.picasso.Picasso;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
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
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(EditProfilCustomer.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(EditProfilCustomer.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_FINE_LOCATION);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ( requestCode ==100 && resultCode == Activity.RESULT_OK){
            if (data == null){
                Toast.makeText(this, "Gambar Tidak Ada", Toast.LENGTH_SHORT).show();
                return;

            }
//            else {
//                Toast.makeText(this, "Gambar Ada", Toast.LENGTH_SHORT).show();
//            }
            Uri selectImageUri = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor c =getContentResolver().query(selectImageUri, filePathColumn, null, null, null);
            if (c !=null){
                c.moveToFirst();

                int columnIndex = c.getColumnIndex(filePathColumn[0]);
                imagePath = c.getString(columnIndex);

//                Glide.with(this).load(new File(imagePath)).into(cvEditProfil);
                Picasso.with(this).load(new File(imagePath)).into(cvProfilepcs);
                img = new File(imagePath).getName();

                //Creating a shared preference
                SharedPreferences sharedPreferences = EditProfilCustomer.this.getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);

                //Creating editor to store values to shared preferences
                SharedPreferences.Editor editor = sharedPreferences.edit();

                //Adding values to editor
                editor.putString("image", BASE_URL_IMAGE_USER+f);
                editor.commit();
                uploadImage();
//                Toast.makeText(this, "Mbuh", Toast.LENGTH_SHORT).show();
                c.close();

//                te.setVisibility(View.GONE);
//                imageVi.setVisibility(View.VISIBLE);
            }else {
//                te.setVisibility(View.VISIBLE);
//                imageVi.setVisibility(View.GONE);
                Toast.makeText(this, "Gambar Tidak Ada", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void uploadImage() {
        final ProgressDialog p  ;
        p = new ProgressDialog(this);
        p.setMessage("Proses Upload Foto");
        p.show();

        BaseApiService s = (BaseApiService) RetrofitClient.getUpdateProfilRetrofit();


        File f = new File(imagePath);

        Toast.makeText(EditProfilCustomer.this, "Gambar " +img, Toast.LENGTH_SHORT).show();

        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), f);

        final MultipartBody.Part part = MultipartBody.Part.createFormData("uploaded_file", f.getName(), requestFile);
        Call<com.gamelanbekonang.api.Result> resultCAll = s.postImage(part);
        resultCAll.enqueue(new Callback<com.gamelanbekonang.api.Result>() {
            @Override
            public void onResponse(Call<com.gamelanbekonang.api.Result> call, Response<com.gamelanbekonang.api.Result> response) {

                p.dismiss();
                if (response.isSuccessful()){
                    if (response.body().getResult().equals("success")){
                        Toast.makeText(EditProfilCustomer.this, "Sukses", Toast.LENGTH_SHORT).show();
                    }

                    else{
                        Toast.makeText(EditProfilCustomer.this, "Upload Gambar Gagal", Toast.LENGTH_SHORT).show();
                    }

                }else {
                    Toast.makeText(EditProfilCustomer.this, "Upload Gambar Gagal", Toast.LENGTH_SHORT).show();
                }

                imagePath = "";

            }

            @Override
            public void onFailure(Call<com.gamelanbekonang.api.Result> call, Throwable t) {
                Toast.makeText(EditProfilCustomer.this, "Gagal Upload Fail", Toast.LENGTH_SHORT).show();
                p.dismiss();



            }
        });
    }

    private void initView() {

    }
}
