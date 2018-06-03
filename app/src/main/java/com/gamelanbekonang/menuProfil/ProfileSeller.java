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
import android.util.Config;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gamelanbekonang.R;
import com.gamelanbekonang.api.BaseApiService;
import com.gamelanbekonang.api.RetroClient;
import com.gamelanbekonang.api.RetrofitClient;
import com.gamelanbekonang.api.UtilsApi;
import com.gamelanbekonang.logRes.LoginActivity;
import com.gamelanbekonang.menuAkun.SellerFragment;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import javax.xml.transform.Result;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.gamelanbekonang.api.BaseApiService.BASE_URL_IMAGE_USER;
import static com.gamelanbekonang.logRes.LoginActivity.my_shared_preferences;

public class ProfileSeller extends AppCompatActivity {

    private static String session_status;
    private Toolbar mActionToolbar;
    private EditText etNamapsr;
    private EditText etEmailpsr;
    private EditText etNotelppsr;
    private EditText etAlamatpsr;
    private EditText etIdpsr;
    private CircleImageView cvProfilsr;
    private Button btnSimpanpsr;
    private ProgressDialog progressDialog;
    private BaseApiService mApiService;
    private int MY_PERMISSIONS_REQUEST_FINE_LOCATION = 1;
    private SharedPreferences sharedpreferences;
    private String imagePath;
    private String f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_seller);

        mActionToolbar = (Toolbar) findViewById(R.id.tabs_profilsr);
        setSupportActionBar(mActionToolbar);
        getSupportActionBar().setTitle("EDIT AKUN");

        mApiService = UtilsApi.getAPIService();

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        if (ContextCompat.checkSelfPermission(ProfileSeller.this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(ProfileSeller.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(ProfileSeller.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_FINE_LOCATION);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }

        etIdpsr = (EditText) findViewById(R.id.et_idprs);
        etIdpsr.setVisibility(View.INVISIBLE);
        cvProfilsr = findViewById(R.id.cv_profilsr);
        etNamapsr = (EditText) findViewById(R.id.et_namapsr);
        etEmailpsr = (EditText) findViewById(R.id.et_emailpsr);
        etNotelppsr = (EditText) findViewById(R.id.et_notelppsr);
        etAlamatpsr = (EditText) findViewById(R.id.et_alamatpsr);
        btnSimpanpsr = (Button) findViewById(R.id.btn_simpanpsr);

        SharedPreferences sp = getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);

        String id = sp.getString(String.valueOf(0),"");
        etIdpsr.setText(id);
        String name = (sp.getString("name", ""));
        etNamapsr.setText(name);
        String email = (sp.getString("email",""));
        etEmailpsr.setText(email);
        String notelp =(sp.getString("notelp",""));
        etNotelppsr.setText(notelp);
        String address = (sp.getString("address",""));
        etAlamatpsr.setText(address);
        String image = (sp.getString("image",""));
        Picasso.with(this)
                .load(BASE_URL_IMAGE_USER+image)
                .placeholder(R.drawable.ic_akun)
                .into(cvProfilsr);

        initView();
        btnSimpanpsr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UbahDataSeller();               
            }
        });
        cvProfilsr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooseGallery();
            }
        });
    }



    private void UbahDataSeller() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Mohon Tunggu...");
        progressDialog.show();
        final String id = etIdpsr.getText().toString();
        final String name = etNamapsr.getText().toString();
        final String email = etEmailpsr.getText().toString();
        final String notelp = etNotelppsr.getText().toString();
        final String address = etAlamatpsr.getText().toString();

        mApiService.updateProfile(etNamapsr.getText().toString().trim(),
                etEmailpsr.getText().toString().trim(),
                etAlamatpsr.getText().toString().trim(),
                etNotelppsr.getText().toString().trim())
                .enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try {
                        JSONObject object = new JSONObject(response.body().string());
                        String messeage = object.optString("message");

                        SharedPreferences sharedPreferences = getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean(session_status, true);
                        editor.putString("id", id);
                        editor.putString("name", name);
                        editor.putString("email", email);
                        editor.putString("notelp", notelp);
                        editor.putString("address", address);
                        editor.commit();
                        Log.e("onResponse: ",messeage );
                        Toast.makeText(ProfileSeller.this, ("Edit Profil Berhasil"), Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(ProfileSeller.this, SellerFragment.class);
                        startActivity(intent);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
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
                Picasso.with(this).load(new File(imagePath)).into(cvProfilsr);
                f = new File(imagePath).getName();

                //Creating a shared preference
                SharedPreferences sharedPreferences = ProfileSeller.this.getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);

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

        Toast.makeText(ProfileSeller.this, "Gambar " + f, Toast.LENGTH_SHORT).show();

        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), f);

        final MultipartBody.Part part = MultipartBody.Part.createFormData("uploaded_file", f.getName(), requestFile);
        Call<com.gamelanbekonang.api.Result> resultCAll = s.postImage(part);
        resultCAll.enqueue(new Callback<com.gamelanbekonang.api.Result>() {
            @Override
            public void onResponse(Call<com.gamelanbekonang.api.Result> call, Response<com.gamelanbekonang.api.Result> response) {

                p.dismiss();
                if (response.isSuccessful()){
                    if (response.body().getResult().equals("success")){
                        Toast.makeText(ProfileSeller.this, "Sukses", Toast.LENGTH_SHORT).show();
                    }

                    else{
                        Toast.makeText(ProfileSeller.this, "Upload Gambar Gagal", Toast.LENGTH_SHORT).show();
                    }

                }else {
                    Toast.makeText(ProfileSeller.this, "Upload Gambar Gagal", Toast.LENGTH_SHORT).show();
                }

                imagePath = "";

            }

            @Override
            public void onFailure(Call<com.gamelanbekonang.api.Result> call, Throwable t) {
                Toast.makeText(ProfileSeller.this, "Gagal Upload Fail", Toast.LENGTH_SHORT).show();
                p.dismiss();



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

    }
}
