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
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gamelanbekonang.MainActivity;
import com.gamelanbekonang.R;
import com.gamelanbekonang.api.BaseApiService;
import com.gamelanbekonang.api.Result;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_seller);

        mActionToolbar = (Toolbar) findViewById(R.id.tabs_profilsr);
        setSupportActionBar(mActionToolbar);
        getSupportActionBar().setTitle("EDIT AKUN");

        mApiService = UtilsApi.getAPIService();

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        if (ContextCompat.checkSelfPermission(EditProfileSeller.this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(EditProfileSeller.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(EditProfileSeller.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_FINE_LOCATION);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }

        etIdpsr = (EditText) findViewById(R.id.et_idprs);
        etIdpsr.setVisibility(View.INVISIBLE);
        et_token = (EditText) findViewById(R.id.et_tokenpsr);
//        et_token.setVisibility(View.INVISIBLE);
        cvProfilsr = findViewById(R.id.cv_profilsr);
        etNamapsr = (EditText) findViewById(R.id.et_namapsr);
        etEmailpsr = (EditText) findViewById(R.id.et_emailpsr);
        etNotelppsr = (EditText) findViewById(R.id.et_notelppsr);
        etAlamatpsr = (EditText) findViewById(R.id.et_alamatpsr);
        btnSimpanpsr = (Button) findViewById(R.id.btn_simpanpsr);

//        SharedPreferences sp = getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);
//
//        String id = sp.getString("id","");
//        etIdpsr.setText(id);
//        String name = (sp.getString("name", ""));
//        etNamapsr.setText(name);
//        String email = (sp.getString("email",""));
//        etEmailpsr.setText(email);
//        String notelp =(sp.getString("notelp",""));
//        etNotelppsr.setText(notelp);
//        String address = (sp.getString("address",""));
//        etAlamatpsr.setText(address);



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
                etNotelppsr.getText().toString(),img)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                        if (response.isSuccessful()){
//                            progressDialog.dismiss();
//                            try {
//                                JSONObject object = new JSONObject(response.body().string());
//                                String messeage = object.optString("message");
//                                Toast.makeText(EditProfileSeller.this, "" + messeage, Toast.LENGTH_SHORT).show();
//                                Toast.makeText(EditProfileSeller.this, "" + messeage, Toast.LENGTH_SHORT).show();
//                                Toast.makeText(EditProfileSeller.this, "" + messeage, Toast.LENGTH_SHORT).show();
//                                Toast.makeText(EditProfileSeller.this, "" + messeage, Toast.LENGTH_SHORT).show();
//                                Toast.makeText(EditProfileSeller.this, "" + messeage, Toast.LENGTH_SHORT).show();
//                                Toast.makeText(EditProfileSeller.this, "" + messeage, Toast.LENGTH_SHORT).show();
//                                Toast.makeText(EditProfileSeller.this, "" + messeage, Toast.LENGTH_SHORT).show();
//                                Log.e("Response : ", messeage);
                                SharedPreferences sp = getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);

                                SharedPreferences.Editor editor = sp.edit();
                                editor.putBoolean(session_status, true);
                                editor.putString("token", token);
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
//                        }
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
                img = new File(imagePath).getName();

                //Creating a shared preference
//                SharedPreferences sharedPreferences = EditProfileSeller.this.getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);


                //Creating editor to store values to shared preferences
                SharedPreferences.Editor editor = sharedpreferences.edit();

                //Adding values to editor
                editor.putString(SHARED_IMAGE,"http://gamelanwirun.com/images/users/"+img);
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
        final String token = et_token.getText().toString();
        final ProgressDialog p  ;
        p = new ProgressDialog(this);
        p.setMessage("Proses Upload Foto");
        p.show();

        BaseApiService s = (BaseApiService) RetrofitClient.getImgUsr();


        File f = new File(imagePath);

        Toast.makeText(EditProfileSeller.this, "Gambar " +f, Toast.LENGTH_SHORT).show();

        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), f);

        final MultipartBody.Part part = MultipartBody.Part.createFormData("image", f.getName(), requestFile);
        Call<ResponseBody> resultCAll = s.postImage(part, token);
        resultCAll.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                sharedpreferences = EditProfileSeller.this.getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);
                String token = sharedpreferences.getString("token", "");
                et_token.setText(token);
                Toast.makeText(EditProfileSeller.this, "Semoga Bisa", Toast.LENGTH_SHORT).show();
                p.dismiss();
//                               if (response.isSuccessful()){
//                    if (response.body().getResult().equals("success")){
//                        Toast.makeText(EditProfileSeller.this, "Sukses", Toast.LENGTH_SHORT).show();
//                    }
//
//                    else{
//                        Toast.makeText(EditProfileSeller.this, "Upload Gambar Gagal", Toast.LENGTH_SHORT).show();
//                    }
//
//                }else {
//                    Toast.makeText(EditProfileSeller.this, "Upload Gambar Gagal", Toast.LENGTH_SHORT).show();
//                }

                imagePath = "";

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(EditProfileSeller.this, "Gagal Upload Fail", Toast.LENGTH_SHORT).show();
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
        SharedPreferences sharedpreferences = getSharedPreferences(my_shared_preferences, MODE_PRIVATE);
        id = (sharedpreferences.getString("id",""));
        image = sharedpreferences.getString("image", null);
        name = sharedpreferences.getString("name", null);
        email = sharedpreferences.getString("email", null);
        notelp = sharedpreferences.getString("notelp",null);
        address = sharedpreferences.getString("address", null);
        token = sharedpreferences.getString("token", null);

    }
}
