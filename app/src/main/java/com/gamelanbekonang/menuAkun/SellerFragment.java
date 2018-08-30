package com.gamelanbekonang.menuAkun;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.gamelanbekonang.R;
import com.gamelanbekonang.logRes.LoginActivity;
import com.gamelanbekonang.menuBantuan.CaraDaftarActivity;
import com.gamelanbekonang.menuBantuan.JualBeliActivity;
import com.gamelanbekonang.menuBantuan.KebijakanActivity;
import com.gamelanbekonang.menuBantuan.KetentuanActivity;
import com.gamelanbekonang.menuProfil.GantiPasswordActivity;
import com.gamelanbekonang.menuProfil.EditProfileSeller;
import com.gamelanbekonang.menuProfil.MyIklanActivity;
import com.squareup.picasso.Picasso;
import de.hdodenhof.circleimageview.CircleImageView;
import static com.gamelanbekonang.api.BaseApiService.BASE_URL_IMAGE_USER;
import static com.gamelanbekonang.logRes.LoginActivity.my_shared_preferences;

/**
 * A simple {@link Fragment} subclass.
 */
public class SellerFragment extends Fragment {

    private TextView tv_idseller, tv_nameseller, tv_emailseller, tv_notelpseller, tv_alamatseller, tv_keluarseller;
    private TextView tv_tentangsr, tv_caradftsr, tv_tipssr, tv_kebijakansr, tv_ketentuansr, tv_iklanseller, tv_editsr,tv_editpasssr;
    private CircleImageView civ_seller;
    public static final  String value = "id";

    public SellerFragment() {
        // Required empty public constructor
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_seller, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        civ_seller = view.findViewById(R.id.civ_seller);
        tv_nameseller = view.findViewById(R.id.tv_nameseller);
        tv_emailseller = view.findViewById(R.id.tv_emailseller);
        tv_alamatseller = view.findViewById(R.id.tv_alamatseller);
        tv_notelpseller = view.findViewById(R.id.tv_notelpseller);
        tv_editsr = view.findViewById(R.id.tv_editpsr);
        tv_editpasssr = view.findViewById(R.id.tv_editpasspsr);
        tv_iklanseller = view.findViewById(R.id.tv_iklanseller);
        tv_tentangsr = view.findViewById(R.id.tv_tentangseller);
        tv_caradftsr = view.findViewById(R.id.tv_caradaftarsr);
        tv_tipssr = view.findViewById(R.id.tv_tipssr);
        tv_kebijakansr = view.findViewById(R.id.tv_kebijakansr);
        tv_ketentuansr = view.findViewById(R.id.tv_ketentuansr);
        tv_keluarseller = view.findViewById(R.id.tv_keluarseller);


        SharedPreferences sp = getContext().getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);

        String image = (sp.getString("image", ""));
        Picasso.with(getContext())
                .load(BASE_URL_IMAGE_USER+image)
                .placeholder(R.drawable.ic_akun)
                .into(civ_seller);
        String id = (sp.getString("id", ""));
        String name = (sp.getString("name", ""));
        tv_nameseller.setText(name);
        String email = (sp.getString("email", ""));
        tv_emailseller.setText(email);
        String alamat = (sp.getString("address", ""));
        tv_alamatseller.setText(alamat);
        String notelp = (sp.getString("notelp", ""));
        tv_notelpseller.setText(notelp);

        tv_keluarseller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Ingin Keluar dari Akun ini?")
                        .setIcon(android.R.drawable.ic_lock_power_off)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                keluarseller();
                            }
                        }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
            }
        });
        tv_editsr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentiksr = new Intent(getActivity(), EditProfileSeller.class);
                startActivity(intentiksr);
            }
        });

        tv_tentangsr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intenttgs = new Intent(getActivity(), TentangActivity.class);
                startActivity(intenttgs);
            }
        });
        tv_caradftsr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentbts = new Intent(getActivity(), CaraDaftarActivity.class);
                startActivity(intentbts);
            }
        });
        tv_tipssr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intenttipsr = new Intent(getActivity(), JualBeliActivity.class);
                startActivity(intenttipsr);
            }
        });
        tv_kebijakansr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentkbjksr = new Intent(getActivity(), KebijakanActivity.class);
                startActivity(intentkbjksr);
            }
        });
        tv_ketentuansr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentktsr = new Intent(getActivity(), KetentuanActivity.class);
                startActivity(intentktsr);
            }
        });
        tv_iklanseller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MyIklanActivity.class));

            }
        });
        tv_editpasssr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), GantiPasswordActivity.class));
            }
        });
    }
    private void keluarseller() {
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

        Intent intent1 = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent1);
    }


}







