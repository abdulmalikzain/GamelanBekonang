package com.gamelanbekonang.MenuHome;

import android.app.ProgressDialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.gamelanbekonang.Adapter.AdapterIklan;
import com.gamelanbekonang.Api.ApiService;
import com.gamelanbekonang.Api.RetroClient;
import com.gamelanbekonang.R;
import com.gamelanbekonang.beans.Iklan;
import com.gamelanbekonang.beans.IklanList;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class HomeFragment extends Fragment {

    private ArrayList<Iklan> iklans;
    private ProgressDialog dialog;
    private AdapterIklan eAdapter;
    RecyclerView rv;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        rv = view.findViewById(R.id.rv_iklan);
//        LinearLayoutManager linearLayoutManager=
//                new LinearLayoutManager(getActivity());
//        rv.setLayoutManager(linearLayoutManager);
        loadData();

        return view;
    }

    private void loadData(){
        //Creating an object of our api interface
        ApiService api = RetroClient.getApiService();

        /**
         * Calling JSON
         */
        Call<IklanList> call = api.getMyJSONIklan();

        /**
         * Enqueue Callback will be call when get response...
         */
        call.enqueue(new Callback<IklanList>() {
            @Override
            public void onResponse(Call<IklanList> call, Response<IklanList> response) {
                //Dismiss Dialog

                if (response.isSuccessful()) {
                    /**
                     * Got Successfully
                     */

                    iklans = (ArrayList<Iklan>) response.body().getIklans();
                    eAdapter = new AdapterIklan(iklans);

                    RecyclerView.LayoutManager eLayoutManager = new LinearLayoutManager(getActivity());
                    rv.setLayoutManager(eLayoutManager);
                    rv.setItemAnimator(new DefaultItemAnimator());
                    rv.setAdapter(eAdapter);
                    Log.d(TAG, "aaaaaaaa: " +iklans);
                }
            }

            @Override
            public void onFailure(Call<IklanList> call, Throwable t) {
            }
        });
    }

}
