package com.gamelanbekonang.menuProfil;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

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

public class EditProfileSeller extends AppCompatActivity {

    private static String session_status;
    private Toolbar mActionToolbar;
    private EditText etNamapsr;
    private EditText etEmailpsr;
    private EditText etNotelppsr;
    private EditText etAlamatpsr;
    private EditText et_token;
    private EditText etIdpsr;
    private CircleImageView cvProfilsr;
    private Button btnSimpanpsr;
    private ProgressDialog progressDialog;
    private BaseApiService mApiService;
    private int MY_PERMISSIONS_REQUEST_FINE_LOCATION = 1;
    private SharedPreferences sharedpreferences;
    public static final String SHARED_IMAGE = "image";
    private String imagePath;
    private String f;
    private String token;
    private String img;
    private String id;
    private String TAG;
    private String image, name, email, notelp, address;
    private String h;
    private int REQUEST_GALLERY = 9544;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_seller);

        mActionToolbar = (Toolbar) findViewById(R.id.tabs_profilsr);
        setSupportActionBar(mActionToolbar);
        getSupportActionBar().setTitle("EDIT AKUN");

        initView();


        if (ContextCompat.checkSelfPermission(EditProfileSeller.this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //
            if (ActivityCompat.shouldShowRequestPermissionRationale(EditProfileSeller.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {

            } else {

                ActivityCompat.requestPermissions(EditProfileSeller.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_FINE_LOCATION);

            }
        }
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }




        sharedpreferences = getSharedPreferences(my_shared_preferences, MODE_PRIVATE);
        String name = (sharedpreferences.getString("name", ""));
        etNamapsr.setText(name);
        String email = (sharedpreferences.getString("email", ""));
        etEmailpsr.setText(email);
        String address = (sharedpreferences.getString("address", ""));
        etAlamatpsr.setText(address);
        String notelp = (sharedpreferences.getString("notelp", ""));
        etNotelppsr.setText(notelp);
        String image = (sharedpreferences.getString("image",""));
        Picasso.with(this)
                .load(BASE_URL_IMAGE_USER+image)
                .placeholder(R.drawable.ic_akun)
                .into(cvProfilsr);

        String id = (sharedpreferences.getString("id", ""));
        etIdpsr.setText(id);
        String token = (sharedpreferences.getString("token",""));
        et_token.setText(token);
        Log.d(TAG, "token: "+token);

        mApiService = UtilsApi.getAPIService();
        btnSimpanpsr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UbahDataSeller();
            }
        });
        cvProfilsr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                showFileChooseGallery();
                startActivity(new Intent(EditProfileSeller.this, UploadImage.class));
            }
        });
    }



    private void UbahDataSeller() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Mohon tunggu...");
        progressDialog.show();
        final String token = et_token.getText().toString();
        final String id = etIdpsr.getText().toString();
        final String name = etNamapsr.getText().toString();
        final String email = etEmailpsr.getText().toString();
        final String notelp = etNotelppsr.getText().toString();
        final String address = etAlamatpsr.getText().toString();

        mApiService.updateProfile(token , id,
                etNamapsr.getText().toString(),
                etEmailpsr.getText().toString(),
                etAlamatpsr.getText().toString(),
                etNotelppsr.getText().toString(),h)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            progressDialog.dismiss();
//                            try {
//                                JSONObject object = new JSONObject(response.body().string());
//                                String messeage = object.optString("message");
                            SharedPreferences sp = getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);

                            SharedPreferences.Editor editor = sp.edit();
                            editor.putBoolean(session_status, true);
                            editor.putString("name", name);
                            editor.putString("email", email);
                            editor.putString("address", address);
                            editor.putString("notelp", notelp);
                            editor.commit();
                                Toast.makeText(EditProfileSeller.this, ("Edit Profil Berhasil"), Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(EditProfileSeller.this, MainActivity.class);
                                startActivity(intent);
//                            }
//                            catch (JSONException e) {
//                                e.printStackTrace();
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        progressDialog.dismiss();
                    }
                });

    }

//    private void showFileChooseGallery() {
//
//        Intent foto = new Intent(Intent.ACTION_PICK);
//        foto.setType("image/*");
//        startActivityForResult(Intent.createChooser(foto, "Pilih Foto"), 100);
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if ( requestCode == REQUEST_GALLERY && resultCode == Activity.RESULT_OK){
//            if (data == null){
//                Toast.makeText(this, "Gambar Tidak Ada", Toast.LENGTH_SHORT).show();
//                return;
//
//            }
//            Uri selectImageUri = data.getData();
//            String[] filePathColumn = {MediaStore.Images.Media.DATA};
//
//            Cursor cursor =getContentResolver().query(selectImageUri, filePathColumn, null, null, null);
//            if (cursor != null){
//                cursor.moveToFirst();
//
//                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//                imagePath = cursor.getString(columnIndex);
//
//                Picasso.with(this).load(new File(imagePath)).into(cvProfilsr);
//                h = new File(imagePath).getName();
//
//                //Creating a shared preference
//                SharedPreferences sharedPreferences = EditProfileSeller.this.getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);
//
//
//                //Creating editor to store values to shared preferences
//                SharedPreferences.Editor editor = sharedpreferences.edit();
//
//                //Adding values to editor
//                editor.putString(SHARED_IMAGE,"http://gamelanwirun.com/api/v1/user/"+h);
//                editor.commit();
//                uploadImage();
//                cursor.close();
//
//            }else {
//                Toast.makeText(this, "Gambar Tidak Ada", Toast.LENGTH_SHORT).show();
//            }
//        }
//
//
//    }

//    private void uploadImage() {
//        final ProgressDialog p  ;
//        p = new ProgressDialog(this);
//        p.setMessage("Proses Upload Foto");
//        p.show();
//        final String token = et_token.getText().toString();
//
//
////        BaseApiService s = (BaseApiService) RetrofitClient.getImgUsr();
//
//
//        File f = new File(imagePath);
//
//        Toast.makeText(EditProfileSeller.this, "Gambar " +f, Toast.LENGTH_SHORT).show();
//
//        final RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-file"), f);
//        MultipartBody.Part part = MultipartBody.Part.createFormData("image", f.getName(), requestFile);
//        Call<ResponseBody> resultCAll = mApiService.postImage(part);
//        resultCAll.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                p.dismiss();
//                Toast.makeText(EditProfileSeller.this, "Semoga Bisa", Toast.LENGTH_SHORT).show();
//
////
//
//
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                p.dismiss();
//                Toast.makeText(EditProfileSeller.this, "Gagal Upload Fail", Toast.LENGTH_SHORT).show();
//
//
//            }
//        });
//    }


    //button back toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    private void initView() {

        etIdpsr = (EditText) findViewById(R.id.et_idprs);
        etIdpsr.setVisibility(View.INVISIBLE);
        et_token = (EditText) findViewById(R.id.et_tokenpsr);
        cvProfilsr = findViewById(R.id.cv_profilsr);
        etNamapsr = (EditText) findViewById(R.id.et_namapsr);
        etEmailpsr = (EditText) findViewById(R.id.et_emailpsr);
        etNotelppsr = (EditText) findViewById(R.id.et_notelppsr);
        etAlamatpsr = (EditText) findViewById(R.id.et_alamatpsr);
        btnSimpanpsr = (Button) findViewById(R.id.btn_simpanpsr);


    }
}
