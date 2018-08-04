package com.gamelanbekonang.menuProfil;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gamelanbekonang.MainActivity;
import com.gamelanbekonang.R;
import com.gamelanbekonang.api.BaseApiService;
import com.gamelanbekonang.logRes.LoginActivity;
import com.squareup.picasso.Picasso;

import java.net.URL;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.ContentValues.TAG;
import static com.gamelanbekonang.api.BaseApiService.BASE_URL_IMAGE;
import static com.gamelanbekonang.api.BaseApiService.BASE_URL_IMAGE_USER;
import static com.gamelanbekonang.logRes.LoginActivity.my_shared_preferences;

public class ProfilActivity extends AppCompatActivity {

//    SharedPreferences sp;
    Context context;
    private TextView tv_id,tv_token1, tv_name, tv_email, tv_notelp, tv_address, tv_reseller,keluar;
    private CircleImageView civp;
    public static final  String value = "id";
    private int i;
    private Toolbar mActionToolbar;
    private String id, image, name, email, notelp, address;
    private String token;
    private BaseApiService B;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);


        keluar = findViewById(R.id.tv_keluar);
        civp = findViewById(R.id.profile_photo);
        tv_id =  findViewById(R.id.tv_id);
        tv_token1 = findViewById(R.id.tv_token1);
        tv_name = findViewById(R.id.tv_namep);
        tv_email = findViewById(R.id.tv_emailp);
        tv_address = findViewById(R.id.tv_address);
        tv_notelp = findViewById(R.id.tv_notelp);
        tv_reseller = findViewById(R.id.tv_reseller);

        SharedPreferences sharedpreferences = getSharedPreferences(my_shared_preferences, MODE_PRIVATE);
        id = (sharedpreferences.getString("id",""));
        image = sharedpreferences.getString("image", null);
        name = sharedpreferences.getString("name", null);
        email = sharedpreferences.getString("email", null);
        notelp = sharedpreferences.getString("notelp",null);
        address = sharedpreferences.getString("address", null);
        token = sharedpreferences.getString("token", null);

//        SharedPreferences sp = PreferenceManager
//                .getDefaultSharedPreferences(this);
//        sp = context.getSharedPreferences(my_shared_preferences, MODE_PRIVATE);

        SharedPreferences sp = getApplicationContext().getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);

        String image = (sp.getString("image", ""));
        Picasso.with(getApplication())
                           .load(BaseApiService.BASE_URL_IMAGE_USER+image)
                           .placeholder(R.drawable.ic_akun)
                           .into(civp);
        String id = (sp.getString("id", ""));
        tv_id.setText(id);
        String name = (sp.getString("name", ""));
        tv_name.setText(name);
        String email = (sp.getString("email", ""));
        tv_email.setText(email);
        String notelp = (sp.getString("notelp", ""));
        tv_notelp.setText(notelp);
        String address = (sp.getString("address",""));
        tv_address.setText(address);
        String namerules = (sp.getString("name", ""));
        tv_reseller.setText(namerules);
        String token = (sharedpreferences.getString("token",""));
        tv_token1.setText(token);

        mActionToolbar = (Toolbar) findViewById(R.id.tabs_profil);
        setSupportActionBar(mActionToolbar);
        getSupportActionBar().setTitle("DETAIL AKUN");

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        keluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ProfilActivity.this);
                builder.setTitle("Ingin Keluar dari Akun ini?")
                        .setIcon(android.R.drawable.ic_lock_power_off)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                keluar();
                            }
                        }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
            }
        });
    }

    private void keluar() {
        SharedPreferences sharedPreferences = ProfilActivity.this.getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);

        //Creating editor to store values to shared preferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(LoginActivity.session_status, false);
        editor.putString("id", null);
        editor.putString("image", null);
        editor.putString("name", null);
        editor.putString("email", null);
        editor.putString("notelp", null);
        editor.putString("address", null);
        editor.clear();
        editor.commit();
        finish();

        Intent intent1 = new Intent(ProfilActivity.this, MainActivity.class);
        startActivity(intent1);

    }


    //button back toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()== android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}

