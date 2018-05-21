package com.gamelanbekonang.menuAkun;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gamelanbekonang.MainActivity;
import com.gamelanbekonang.R;
import com.gamelanbekonang.logRes.LoginActivity;
import com.gamelanbekonang.menuProfil.ProfilActivity;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.ContentValues.TAG;
import static com.gamelanbekonang.api.BaseApiService.BASE_URL_IMAGE;
import static com.gamelanbekonang.logRes.LoginActivity.my_shared_preferences;

/**
 * A simple {@link Fragment} subclass.
 */
public class CustomerFragment extends Fragment {

    private TextView tv_idctm, tv_namectm, tv_emailctm, tv_notelpctm, keluar_ctm;
    private TextView tentangctm, bantuanctm;
    private CircleImageView civ_ctm;
    public static final  String value = "id";

    public CustomerFragment() {
        // Required empty public constructor
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_customer, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        keluar_ctm = view.findViewById(R.id.tv_keluarctm);
        civ_ctm = view.findViewById(R.id.civ_customer);
//        tv_idctm =  view.findViewById(R.id.tv_idctm);
        tv_namectm = view.findViewById(R.id.tv_namectm);
        tv_emailctm = view.findViewById(R.id.tv_emailctm);
        tentangctm = view.findViewById(R.id.tv_tentangctm);
        bantuanctm = view.findViewById(R.id.tv_bantuanctm);
        tv_notelpctm = view.findViewById(R.id.tv_notelpctm);

//        SharedPreferences sharedPreferences = PreferenceManager
//                .getDefaultSharedPreferences(this);
//        sharedpreferences = context.getSharedPreferences(my_shared_preferences, MODE_PRIVATE);
        SharedPreferences sp = getContext().getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);

        String image = (sp.getString("image", ""));
        Picasso.with(getContext())
                .load(BASE_URL_IMAGE+image)
                .placeholder(R.drawable.ic_akun)
                .into(civ_ctm);
        String id = (sp.getString("id", ""));
        String name = (sp.getString("name", ""));
        tv_namectm.setText(name);
        String email = (sp.getString("email", ""));
        tv_emailctm.setText(email);
        String notelp = (sp.getString("notelp", ""));
        tv_notelpctm.setText(notelp);
        Log.d(TAG, "JKOEr: "+id+civ_ctm+name+email+notelp);

        keluar_ctm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Ingin Keluar dari Akun ini?")
                        .setIcon(android.R.drawable.ic_lock_power_off)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                keluarctm();
                            }
                        }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
            }
        });

        tentangctm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intenttcm = new Intent(getActivity(), TentangActivity.class);
                startActivity(intenttcm);
            }
        });
        bantuanctm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentbcm = new Intent(getActivity(), BantuanActivity.class);
                startActivity(intentbcm);
            }
        });
    }

    private void keluarctm() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);

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

        Intent intent1 = new Intent(getActivity(), MainActivity.class);
        startActivity(intent1);
    }

}
