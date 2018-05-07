package com.gamelanbekonang.menuProfil;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.gamelanbekonang.logRes.LoginActivity;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.ContentValues.TAG;
import static com.gamelanbekonang.api.BaseApiService.BASE_URL_IMAGE;
import static com.gamelanbekonang.logRes.LoginActivity.my_shared_preferences;

public class ProfilActivity extends AppCompatActivity {

    SharedPreferences sharedpreferences;
    Context context;
    private TextView tv_id, tv_name, tv_email, tv_notelp, keluar;
    private CircleImageView civp;
    public static final  String value = "id";
    private int i;
    private Toolbar mActionToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);


        keluar = findViewById(R.id.tv_keluar);
        civp = findViewById(R.id.profile_photo);
        tv_id =  findViewById(R.id.tv_id);
        tv_name = findViewById(R.id.tv_namep);
        tv_email = findViewById(R.id.tv_emailp);
        tv_notelp = findViewById(R.id.tv_notelp);

//        SharedPreferences sharedPreferences = PreferenceManager
//                .getDefaultSharedPreferences(this);
//        sharedpreferences = context.getSharedPreferences(my_shared_preferences, MODE_PRIVATE);
        SharedPreferences sp = getApplicationContext().getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);

        String image = (sp.getString("image", ""));
        Picasso.with(ProfilActivity.this)
                .load(BASE_URL_IMAGE+image)
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
        Log.d(TAG, "JKOEr: "+id+civp+name+email+notelp);

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

