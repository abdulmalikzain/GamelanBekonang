package com.gamelanbekonang.logRes;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.annotation.RequiresPermission;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gamelanbekonang.MainActivity;
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

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail;
    private EditText etPassword;
    private TextView coba, tv_next, tv_skip;
    private Button btnLogin;
    private Button btnRegister;
    private CheckBox c;
    private ProgressDialog loading;
    private String TAG="";
    private Context mContext;
    private BaseApiService mApiService;
    private Toolbar mActionToolbar;
    SharedPreferences sharedpreferences;
    public static final  String value = "id";
    Boolean session = false;
    private String id,image,name,email,notelp,address, store_name,remember_token;
    public static final String session_status = "session_status";
    public static final String my_shared_preferences = "signin";
    SharedPreferences sharedPreferences;
    private int i;

    private String ctm = "ctm";
    private String seller = "seller";
    private String token;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        mActionToolbar = (Toolbar) findViewById(R.id.tabs_login);
//        setSupportActionBar(mActionToolbar);
//        getSupportActionBar().setTitle("LOGIN ACCOUNT");
//
//        if (getSupportActionBar() != null) {
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            getSupportActionBar().setDisplayShowHomeEnabled(true);
//        }

        mContext = this;
        mApiService = UtilsApi.getAPIService(); // meng-init yang ada di package apihelper
        initComponents();
    }



    private void initComponents() {
        sharedpreferences = getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);
//        session = sharedpreferences.getBoolean(session_status, false);
        String id = sharedpreferences.getString("id", null);
        image = sharedpreferences.getString("image", null);
        name = sharedpreferences.getString("name", null);
        email = sharedpreferences.getString("email", null);
        notelp = sharedpreferences.getString("notelp",null);
        address = sharedpreferences.getString("address", null);
        store_name = sharedpreferences.getString("store_name", null);
        remember_token = sharedpreferences.getString("token", null);
//        int i = sharedpreferences.getInt(value, 0);

        tv_next = (TextView)findViewById(R.id.tv_next);
        tv_skip = (TextView)findViewById(R.id.tv_skip);
        coba = (TextView) findViewById(R.id.coba);
        etEmail = (EditText) findViewById(R.id.et_emaill);
        etPassword = (EditText) findViewById(R.id.et_passwordl);
        btnLogin = (Button) findViewById(R.id.btn_loginl);
        c = (CheckBox) findViewById(R.id.checkBox);
        c.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked){
                    etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });
        btnRegister = (Button) findViewById(R.id.btn_registerl);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading = ProgressDialog.show(mContext, null, "Harap Tunggu...", true, false);
                requestLogin();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, RegisterActivity.class));
            }
        });

        tv_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, MainActivity.class));
            }
        });
        tv_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, MainActivity.class));
            }
        });
    }


    private void requestLogin() {
        mApiService.loginRequest(etEmail.getText().toString(), etPassword.getText().toString())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            loading.dismiss();
                            try {
                                JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                JSONArray jsonArray = jsonRESULTS.getJSONObject("user").getJSONArray("roles");
                                if (!jsonRESULTS.getString("msg").equals("404")){
                                    // Jika login berhasil maka data nama yang ada di response API
                                    // akan diparsing ke activity selanjutnya.
                                    String success =  jsonRESULTS.getString("msg");
                                    Toast.makeText(mContext, success, Toast.LENGTH_SHORT).show();
                                    String id = jsonRESULTS.getJSONObject("user").getString("id");
                                    String image = jsonRESULTS.getJSONObject("user").getString("image");
                                    String nama = jsonRESULTS.getJSONObject("user").getString("name");
                                    String email = jsonRESULTS.getJSONObject("user").getString("email");
                                    String notelp = jsonRESULTS.getJSONObject("user").getString("notelp");
                                    String address = jsonRESULTS.getJSONObject("user").getString("address");
                                    String store_name = jsonRESULTS.getJSONObject("user").getString("store_name");
                                    String remember_token = jsonRESULTS.getString("token");
                                    Log.d(TAG, "tokennnnnnn: "+remember_token);
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    String namerules = jsonObject.optString("role_name");





//                                    for (int i = 0 ; i < jsonArray.length() ; i++) {


                                        if (namerules.equals("customer")) {
                                            SharedPreferences sharedPreferences = LoginActivity.this.getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);

                                            //Creating editor to store values to shared preferences
                                            SharedPreferences.Editor editor = sharedPreferences.edit();
                                            editor.putString("id", id);
                                            editor.putString("image", image);
                                            editor.putString("name", nama);
                                            editor.putString("email", email);
                                            editor.putString("address", address);
                                            editor.putString("notelp", notelp);
                                            editor.putString("role_name", namerules);
                                            editor.putString("token", remember_token);
                                            editor.commit();
                                            Log.d(TAG, "Custome: "+remember_token);
                                            loading.dismiss();
                                            Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
                                            startActivity(intent1);

                                        }else if (namerules.equals("seller")) {
                                            SharedPreferences sharedPreferences = LoginActivity.this.getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);
                                            //Creating editor to store values to shared preferences
                                            SharedPreferences.Editor editor = sharedPreferences.edit();
                                            editor.putString("id", id);
                                            editor.putString("image", image);
                                            editor.putString("name", nama);
                                            editor.putString("email", email);
                                            editor.putString("address", address);
                                            editor.putString("notelp", notelp);
                                            editor.putString("role_name", namerules);
                                            editor.putString("token", remember_token);
                                            editor.putString("store_name", store_name);
                                            editor.commit();
                                            Log.d(TAG, "Seller: "+remember_token);
                                            Log.d(TAG, "onResponse: ");
                                            loading.dismiss();
                                            Intent intent2 = new Intent(getApplicationContext(), MainActivity.class);
                                            startActivity(intent2);
                                        }

//                                    }


                                }else {
                                    // Jika login gagal
                                    String error_message = jsonRESULTS.getString("404");
                                    Toast.makeText(mContext, error_message, Toast.LENGTH_SHORT).show();

//                                    Log.d(TAG, "opo yaa: "+error_message);
                                }


                        }catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(mContext, "Password atau Email Salah", Toast.LENGTH_SHORT).show();
                            }catch (IOException e) {
                                e.printStackTrace();
                            }
                    }else {

                            loading.dismiss();
                            Toast.makeText(mContext, "Password atau Email Salah", Toast.LENGTH_SHORT).show();
                        }


                }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
//                        Log.e("debug", "onFailure: ERROR > " + t.toString());
                        loading.dismiss();
                        Toast.makeText(mContext, "Login Gagal", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    //button back toolbar
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId()== android.R.id.home)
//            finish();
//        return super.onOptionsItemSelected(item);
//    }
}
