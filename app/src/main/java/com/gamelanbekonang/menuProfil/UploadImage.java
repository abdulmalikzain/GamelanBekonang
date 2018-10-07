package com.gamelanbekonang.menuProfil;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.gamelanbekonang.MainActivity;
import com.gamelanbekonang.R;
import com.gamelanbekonang.api.BaseApiService;
import com.gamelanbekonang.api.RetrofitClient;
import com.gamelanbekonang.beans.ResponseApiModel;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.gamelanbekonang.logRes.LoginActivity.my_shared_preferences;

public class UploadImage extends AppCompatActivity {
    Button btnUpload, btnGallery;
    ImageView ivHolder;
    String part_image;
    ProgressDialog pd;
    private int REQUEST_GALLERY = 9544;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);

        btnUpload = (Button) findViewById(R.id.btn_upload);
        btnGallery = (Button) findViewById(R.id.btn_gallery);
        ivHolder = (ImageView) findViewById(R.id.iv_holder);
        pd = new ProgressDialog(this);
        pd.setMessage("tunggu ... ");

        SharedPreferences sp = getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);
        token = (sp.getString("token", ""));


        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent, "Buka Galeri"), REQUEST_GALLERY);
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd.show();
                File imagefile = new File(part_image);
                final RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-file"), imagefile);
                MultipartBody.Part partImage = MultipartBody.Part.createFormData("image", imagefile.getName(), requestBody);

                BaseApiService apiService = RetrofitClient.getDataMyIklan();
                Call<ResponseApiModel> upload = apiService.gantiFoto(partImage, token);
                upload.enqueue(new Callback<ResponseApiModel>() {
                    @Override
                    public void onResponse(Call<ResponseApiModel> call, Response<ResponseApiModel> response) {
                        pd.dismiss();
//                        if (response.body().getResponse().equals("202")){
//                            Toast.makeText(UploadImage.this, response.body().getResponse(), Toast.LENGTH_SHORT).show();
//                        }else {
//                            Toast.makeText(UploadImage.this, response.body().getResponse(), Toast.LENGTH_SHORT).show();
//                        }
                        Toast.makeText(UploadImage.this, "Ganti foto Berhasil", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(UploadImage.this, MainActivity.class));
                    }

                    @Override
                    public void onFailure(Call<ResponseApiModel> call, Throwable t) {
                        pd.dismiss();

                    }
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            if (requestCode == REQUEST_GALLERY){
                Uri dataimage = data.getData();
                String[] imageprojection = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(dataimage, imageprojection, null,null,null);

                if (cursor !=null){
                    cursor.moveToFirst();
                    int indexImage = cursor.getColumnIndex(imageprojection[0]);
                    part_image = cursor.getString(indexImage);

                    if (part_image != null){
                        File image = new File(part_image);
                        ivHolder.setImageBitmap(BitmapFactory.decodeFile(image.getAbsolutePath()));
                    }
                }
            }
        }
    }
}
