package com.gamelanbekonang.menuAkun;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gamelanbekonang.R;
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

    SharedPreferences sharedpreferences;
    Context context;
    private TextView tv_id, tv_name, tv_email, tv_notelp, keluar;
    private CircleImageView civp;
    public static final  String value = "id";
    private int i;
    private Toolbar mActionToolbar;


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


        keluar = view.findViewById(R.id.tv_keluar);
        civp = view.findViewById(R.id.civ_customer);
        tv_id =  view.findViewById(R.id.tv_idctm);
        tv_name = view.findViewById(R.id.tv_namectm);
        tv_email = view.findViewById(R.id.tv_emailctm);
        tv_notelp = view.findViewById(R.id.tv_notelpctm);

//        SharedPreferences sharedPreferences = PreferenceManager
//                .getDefaultSharedPreferences(this);
//        sharedpreferences = context.getSharedPreferences(my_shared_preferences, MODE_PRIVATE);
        SharedPreferences sp = getContext().getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);

        String image = (sp.getString("image", ""));
        Picasso.with(getContext())
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
    }

}
