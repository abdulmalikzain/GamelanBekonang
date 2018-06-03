package com.gamelanbekonang.menuKategori;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.gamelanbekonang.R;

import java.util.HashMap;

import static android.content.ContentValues.TAG;


public class KategoriFragment extends Fragment {

    private SliderLayout sliderLayout;
    private LinearLayout llKenong, llDemung, llBonang, llGambang, llKendang, llPeking, llRebab,
    llSaron, llSlenthem, llGong, llKethukKempyang;
    private String kenong = "1", demung ="2", bonang = "3", gambang ="5", kendang="6", peking ="7", rebab="8",
            saron="9", slenthem="10", gong="11", kethukKempyang="12";

    private TextView tvKenong, tvBonang, tvDemung, tvGambang, tvKendang, tvPeking, tvRebab, tvSaron, tvSlenthem, tvGong, tvKethuk;

    public KategoriFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_kategori, container, false);

        sliderLayout = view.findViewById(R.id.slider);
        llKenong        = view.findViewById(R.id.ll_kenong);
        llBonang        = view.findViewById(R.id.ll_bonang);
        llDemung        = view.findViewById(R.id.ll_demung);
        llGambang       = view.findViewById(R.id.ll_gambang);
        llKendang       = view.findViewById(R.id.ll_kendang);
        llPeking        = view.findViewById(R.id.ll_peking);
        llRebab         = view.findViewById(R.id.ll_rebab);
        llSaron         = view.findViewById(R.id.ll_saron);
        llSlenthem      = view.findViewById(R.id.ll_slentem);
        llGong          = view.findViewById(R.id.ll_gong);
        llKethukKempyang   = view.findViewById(R.id.ll_kethukkempyang);

        tvKenong        = view.findViewById(R.id.tv_k_idkenong);

        // Load image dari URL
//        HashMap<String,String> url_maps = new HashMap<String, String>();
//        url_maps.put("Hannibal", "http://static2.hypable.com/wp-content/uploads/2013/12/hannibal-season-2-release-date.jpg");
//        url_maps.put("Big Bang Theory", "http://tvfiles.alphacoders.com/100/hdclearart-10.png");
//        url_maps.put("House of Cards", "http://cdn3.nflximg.net/images/3093/2043093.jpg");
//        url_maps.put("Game of Thrones", "http://images.boomsbeat.com/data/images/full/19640/game-of-thrones-season-4-jpg.jpg");
        // Load Image Dari res/drawable
        HashMap<String,Integer> file_maps = new HashMap<String, Integer>();
        file_maps.put("Gamelan Emas",R.drawable.gamelanemas);
        file_maps.put("Pengrajin Gamelan",R.drawable.pem);
        file_maps.put("Pembuatan gamelan",R.drawable.pembuatan);
        for(String name : file_maps.keySet()){
            TextSliderView textSliderView = new TextSliderView(getContext());
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit);
            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra",name);
            sliderLayout.addSlider(textSliderView);
        }
        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion);
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Left_Top);
        sliderLayout.setCustomAnimation(new DescriptionAnimation());

        sliderLayout.setDuration(4000);

        tvKenong.setText(kenong);
        tvBonang.setText(bonang);
        tvDemung.setText(demung);
        tvGambang.setText(gambang);
        tvGong.setText(gong);
        tvKendang.setText(kendang);
        tvKethuk.setText(kethukKempyang);
        tvPeking.setText(peking);
        tvRebab.setText(rebab);
        tvSlenthem.setText(slenthem);
        tvSaron.setText(saron);

        llKenong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), KategoriIklanActivity.class);
                intent.putExtra("idCategory", tvKenong.getText().toString().trim());
                startActivity(intent);
            }
        });

        llBonang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), KategoriIklanActivity.class);
                intent.putExtra("idCategory", tvBonang.getText().toString().trim());
                startActivity(intent);
            }
        });

        llKethukKempyang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), KategoriIklanActivity.class);
                intent.putExtra("idCategory", tvKethuk.getText().toString().trim());
                startActivity(intent);
            }
        });

        llGong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), KategoriIklanActivity.class);
                intent.putExtra("idCategory", tvGong.getText().toString().trim());
                startActivity(intent);
            }
        });

        llSlenthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), KategoriIklanActivity.class);
                intent.putExtra("idCategory", tvSlenthem.getText().toString().trim());
                startActivity(intent);
            }
        });

        llSaron.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), KategoriIklanActivity.class);
                intent.putExtra("idCategory", tvSaron.getText().toString().trim());
                startActivity(intent);
            }
        });

        llRebab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), KategoriIklanActivity.class);
                intent.putExtra("idCategory", tvRebab.getText().toString().trim());
                startActivity(intent);
            }
        });

        llKendang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), KategoriIklanActivity.class);
                intent.putExtra("idCategory", tvKendang.getText().toString().trim());
                startActivity(intent);
            }
        });

        llPeking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), KategoriIklanActivity.class);
                intent.putExtra("idCategory", tvPeking.getText().toString().trim());
                startActivity(intent);
            }
        });

        llGambang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), KategoriIklanActivity.class);
                intent.putExtra("idCategory", tvGambang.getText().toString().trim());
                startActivity(intent);
            }
        });

        llDemung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), KategoriIklanActivity.class);
                intent.putExtra("idCategory", tvDemung.getText().toString().trim());
                startActivity(intent);
            }
        });

        return view;
    }

}
