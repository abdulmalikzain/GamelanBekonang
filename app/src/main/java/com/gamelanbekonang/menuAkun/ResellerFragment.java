package com.gamelanbekonang.menuAkun;


import android.content.Context;
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

import com.gamelanbekonang.R;
import com.squareup.picasso.Picasso;


import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.ContentValues.TAG;
import static com.gamelanbekonang.api.BaseApiService.BASE_URL_IMAGE;
import static com.gamelanbekonang.logRes.LoginActivity.my_shared_preferences;

/**
 * A simple {@link Fragment} subclass.
 */
public class ResellerFragment extends Fragment {

    private TextView tv_idseller, tv_nameseller, tv_emailseller, tv_notelpseller, tv_alamatseller, keluar;
    private CircleImageView civ_seller;
    public static final  String value = "id";

    public ResellerFragment() {
        // Required empty public constructor
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reseller, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        civ_seller = view.findViewById(R.id.civ_seller);
        tv_idseller =  view.findViewById(R.id.tv_id);
        tv_nameseller = view.findViewById(R.id.tv_nameseller);
        tv_emailseller = view.findViewById(R.id.tv_emailseller);
        tv_alamatseller = view.findViewById(R.id.tv_alamatseller);
        tv_notelpseller = view.findViewById(R.id.tv_notelpseller);


        SharedPreferences sp = getContext().getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);

        String image = (sp.getString("image", ""));
        Picasso.with(getContext())
                .load(BASE_URL_IMAGE+image)
                .placeholder(R.drawable.ic_akun)
                .into(civ_seller);
        String id = (sp.getString("id", ""));
        tv_idseller.setText(id);
        String name = (sp.getString("name", ""));
        tv_nameseller.setText(name);
        String email = (sp.getString("email", ""));
        tv_emailseller.setText(email);
        String notelp = (sp.getString("notelp", ""));
        tv_notelpseller.setText(notelp);
        Log.d(TAG, "JKOEr: "+id+civ_seller+name+email+notelp);
    }



}
