package com.gamelanbekonang.logRes;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
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
import com.gamelanbekonang.menuAkun.ResellerFragment;
import com.gamelanbekonang.menuProfil.ProfilActivity;
import com.gamelanbekonang.menuProfil.ProfileReseller;

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
    private TextView coba;
    private Button btnLogin;
    private Button btnRegister;
    private CheckBox c;
    private ProgressDialog loading;
    private String TAG="";
    private Context mContext;
    private BaseApiService mApiService;
    private Toolbar mActionToolbar;
    SharedPreferences sharedpreferences;
    public static final  String value = "key";
    Boolean session = false;
    private String id,image,name,email,notelp;
    public static final String session_status = "session_status";
    public static final String my_shared_preferences = "signin";
    SharedPreferences sharedPreferences;
    private int i;

    private String ctm = "ctm";
    private String seller = "seller";

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mActionToolbar = (Toolbar) findViewById(R.id.tabs_login);
        setSupportActionBar(mActionToolbar);
        getSupportActionBar().setTitle("LOGIN");

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        mContext = this;
        mApiService = UtilsApi.getAPIService(); // meng-init yang ada di package apihelper
        initComponents();
    }



    private void initComponents() {
        sharedpreferences = getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);
//        session = sharedpreferences.getBoolean(session_status, false);
        id = sharedpreferences.getString("id",null);
        image = sharedpreferences.getString("iamge", null);
        name = sharedpreferences.getString("name", null);
        email = sharedpreferences.getString("email", null);
        notelp = sharedpreferences.getString("notelp",null);
        int i = sharedpreferences.getInt(value, 0);

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
<<<<<<< Updated upstream
                                    String address = jsonRESULTS.getJSONObject("user").getString("address");
=======
<<<<<<< Updated upstream
>>>>>>> Stashed changes

                                    JSONArray jsonArray = jsonRESULTS.getJSONObject("user").getJSONArray("roles");
                                    for (int i = 0 ; i < jsonArray.length() ; i++) {
                                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                                        String namerules = jsonObject.optString("name");

                                        if (namerules.equals("customer")){
                                            SharedPreferences sharedPreferences = LoginActivity.this.getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);

                                            //Creating editor to store values to shared preferences
                                            SharedPreferences.Editor editor = sharedPreferences.edit();
                                            editor.putString("id", id);
                                            editor.putString("image", image);
                                            editor.putString("name", nama);
                                            editor.putString("email", email);
                                            editor.putString("address", address);
                                            editor.putString("notelp", notelp);
                                            editor.putString("name", namerules);
                                            editor.commit();
=======
                                    String rule = jsonRESULTS.getJSONObject("user").getString("roles");

                                    JSONArray jsonArray = jsonRESULTS.getJSONArray("roles");
                                    for (int i = 0; i < jsonArray.length() ; i++) {
                                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                        String namerule = jsonObject1.optString("name");
                                        Log.d(TAG, "Jancooookkkk noooobbbbbb: "+namerule);
                                    }


                                    if (rule.equals("2")){
                                        SharedPreferences sharedPreferences = LoginActivity.this.getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);

                                        //Creating editor to store values to shared preferences
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString("id", id);
                                        editor.putString("image", image);
                                        editor.putString("name", nama);
                                        editor.putString("email", email);
                                        editor.putString("notelp", notelp);
                                        editor.commit();
>>>>>>> Stashed changes
//                                        loading.dismiss();
                                            Intent intent = new Intent(mContext, ProfilActivity.class);
                                            startActivity(intent);

                                        }else if (namerules.equals("seller")){
                                            SharedPreferences sharedPreferences = LoginActivity.this.getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);

                                            SharedPreferences.Editor editor = sharedPreferences.edit();
                                            editor.putString("id", id);
                                            editor.putString("image", image);
                                            editor.putString("name", nama);
                                            editor.putString("email", email);
                                            editor.putString("address", address);
                                            editor.putString("notelp", notelp);
                                            editor.putString("name", namerules);
                                            editor.commit();
                                            loading.dismiss();
                                            Intent intent = new Intent(getApplicationContext(), ProfileReseller.class);
                                            startActivity(intent);
                                        }

                                    }

                                } else {
                                    // Jika login gagal
                                    String error_message = jsonRESULTS.getString("404");
                                    Toast.makeText(mContext, error_message, Toast.LENGTH_SHORT).show();

                                    Log.d(TAG, "onResponse: "+error_message);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();

                                Toast.makeText(mContext, "Login Gagal", Toast.LENGTH_SHORT).show();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            loading.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.toString());
                        loading.dismiss();
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
