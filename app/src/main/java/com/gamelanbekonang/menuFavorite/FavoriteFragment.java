package com.gamelanbekonang.menuFavorite;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.gamelanbekonang.MainActivity;
import com.gamelanbekonang.R;
import com.gamelanbekonang.adapter.AdapterIklan;
import com.gamelanbekonang.api.ApiService;
import com.gamelanbekonang.api.RetroClient;
import com.gamelanbekonang.beans.Iklan;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.gamelanbekonang.logRes.LoginActivity.my_shared_preferences;


public class FavoriteFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    private ApiService apiService;
//    private TextView tv_tokenfav;
    private String token, id, userId;
    private Context context;
    private List<Iklan> list;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);

        swipeRefreshLayout = view.findViewById(R.id.swipe_favorite);
        recyclerView        = view.findViewById(R.id.rv_favorite);
//        tv_tokenfav = view.findViewById(R.id.tv_tokenfav);
//        tv_tokenfav.setVisibility(View.INVISIBLE);


        SharedPreferences sp = getContext().getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);
        userId = (sp.getString("id", ""));
        token = (sp.getString("token", ""));
//        tv_tokenfav.setText(token);
        Log.d("isoooooooo", "onCreateView: "+token);

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        list = new ArrayList<>();

        getFavorite();

        swipeRefreshLayout.setColorSchemeResources(R.color.kuningFirebase,R.color.orangeFirebase,R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener((SwipeRefreshLayout.OnRefreshListener) getActivity());
        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);
                                        getFavorite();
                                    }
                                }
        );

        return view;
    }

    private void getFavorite(){
//        final String token = tv_tokenfav.getText().toString();
        swipeRefreshLayout.setRefreshing(true);
        apiService = RetroClient.getInstanceRetrofit();
        apiService.getFavorite(token)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            Log.i("debug", "onResponse: BERHASIL");
                            try {
                                JSONObject object = new JSONObject(response.body().string());
                                JSONArray jsonArray  = object.optJSONArray("wishlists");

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    String id = jsonObject.optString("id");
                                    String judul = jsonObject.optString("judul");
                                    String image1   = jsonObject.optString("image1");
                                    String volume = jsonObject.optString("volume");
                                    String harga = jsonObject.optString("harga");
                                    String created_at = jsonObject.optString("created_at");
                                    String imageuser = jsonObject.getString("user_image");
                                    String storename = jsonObject.getString("store_name");

                                    Iklan iklan = new Iklan();
                                    iklan.setId(id);
                                    iklan.setJudul(judul);
                                    iklan.setImage1(image1);
                                    iklan.setCreated_at(created_at);
                                    iklan.setVolume(volume);
                                    iklan.setHarga(harga);
                                    iklan.setUser_image(imageuser);
                                    iklan.setStore_name(storename);

                                    list.add(iklan);
                                    AdapterIklan adapter = new AdapterIklan(getContext(), list);
                                    recyclerView.setAdapter(adapter);

                                }

                                swipeRefreshLayout.setRefreshing(false);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.getMessage());
                        Toast.makeText(context, "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
    }

    @Override
    public void onRefresh() {
        getFavorite();
    }
}
