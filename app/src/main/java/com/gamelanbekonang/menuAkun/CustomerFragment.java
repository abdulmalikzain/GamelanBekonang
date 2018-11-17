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
import com.gamelanbekonang.api.BaseApiService;
import com.gamelanbekonang.api.RetrofitClient;
import com.gamelanbekonang.logRes.LoginActivity;
import com.gamelanbekonang.menuBantuan.CaraDaftarActivity;
import com.gamelanbekonang.menuBantuan.JualBeliActivity;
import com.gamelanbekonang.menuBantuan.KebijakanActivity;
import com.gamelanbekonang.menuBantuan.KetentuanActivity;
import com.gamelanbekonang.menuProfil.EditProfileSeller;
import com.gamelanbekonang.menuProfil.GantiPasswordActivity;
import com.gamelanbekonang.menuProfil.UploadImage;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.gamelanbekonang.api.BaseApiService.BASE_URL_IMAGE_USER;
import static com.gamelanbekonang.logRes.LoginActivity.my_shared_preferences;

/**
 * A simple {@link Fragment} subclass.
 */
public class CustomerFragment extends Fragment {

    private TextView tv_idctm, tv_namectm, tv_emailctm, tv_addressctm, tv_notelpctm, keluar_ctm;
    private TextView tentangctm, caradaftarctm, tipsctm, kebijkanctm, ketentuanctm,tv_editpcm, tv_gantipassctm;
    private CircleImageView civ_ctm;
    public static final  String value = "id";
    private BaseApiService baseApiService;
    private String id;
    private String image;

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
        tv_namectm = view.findViewById(R.id.tv_namectm);
        tv_emailctm = view.findViewById(R.id.tv_emailctm);
        tv_addressctm= view.findViewById(R.id.tv_alamatctm);
        tentangctm = view.findViewById(R.id.tv_tentangctm);
        tv_editpcm = view.findViewById(R.id.tv_editpctm);
        tv_gantipassctm = view.findViewById(R.id.tv_editpassctm);
        caradaftarctm = view.findViewById(R.id.tv_caradaftarctm);
        kebijkanctm = view.findViewById(R.id.tv_kebijakanctm);
        tipsctm = view.findViewById(R.id.tv_tipscs);
        ketentuanctm = view.findViewById(R.id.tv_ketentuanctm);
        tv_notelpctm = view.findViewById(R.id.tv_notelpctm);

//        getDataFoto();

        SharedPreferences sp = getContext().getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);

        String image = (sp.getString("image", ""));
        Picasso.with(getContext())
                .load(BASE_URL_IMAGE_USER+image)
                .placeholder(R.drawable.user_ic)
                .into(civ_ctm);
        civ_ctm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), UploadImage.class));
            }
        });
        String id = (sp.getString("id", ""));
        String name = (sp.getString("name", ""));
        tv_namectm.setText(name);
        String email = (sp.getString("email", ""));
        tv_emailctm.setText(email);
        String notelp = (sp.getString("notelp", ""));
        tv_notelpctm.setText(notelp);
        String address = (sp.getString("address",""));
        tv_addressctm.setText(address);

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

        tv_editpcm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), EditProfileSeller.class));
            }
        });
        tentangctm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intenttcm = new Intent(getActivity(), TentangActivity.class);
                startActivity(intenttcm);
            }
        });
        caradaftarctm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentbcm = new Intent(getActivity(), CaraDaftarActivity.class);
                startActivity(intentbcm);
            }
        });
        tipsctm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intenttip = new Intent(getActivity(), JualBeliActivity.class);
                startActivity(intenttip);
            }
        });
        kebijkanctm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentkjcs = new Intent(getActivity(), KebijakanActivity.class);
                startActivity(intentkjcs);
            }
        });
        ketentuanctm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentktncs = new Intent(getActivity(), KetentuanActivity.class);
                startActivity(intentktncs);
            }
        });

        tv_gantipassctm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), GantiPasswordActivity.class));
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

        Intent intent1 = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent1);
    }
//    private void getDataFoto() {
//        baseApiService = RetrofitClient.getDataMyIklan();
//        baseApiService.getInfo(id).enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                try {
//                    JSONObject object = new JSONObject(response.body().string());
//                    JSONObject jsonObject = object.optJSONObject("user");
//                    String id = jsonObject.optString("id");
//                    String image = jsonObject.optString("iamge");
//
//
//                    Picasso.with(getActivity())
//                            .load(BaseApiService.BASE_URL_IMAGE_USER+image)
//                            .placeholder(R.drawable.user_ic)
//                            .into(civ_ctm);
//
//                } catch (JSONException e) {
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//
//
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//
//            }
//
//
//        });
//    }
}
