package com.example.delme.smartair.Fragments;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.delme.smartair.R;

public class PlanesFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    public PlanesFragment() {}
    private Spinner categorieSpinner;
    private ImageView currentPlane;
    private LinearLayout data;
    private TextView temperature, humdity, quality;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle s) {
        return inflater.inflate(R.layout.fragment_planes, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        data = getView().findViewById(R.id.planesFragment_data);
        categorieSpinner = getView().findViewById(R.id.planesFragment_spinnerCat);
        currentPlane = getView().findViewById(R.id.planesFragment_planeImageView);
        temperature = getView().findViewById(R.id.planesFragment_temp);
        humdity = getView().findViewById(R.id.planesFragment_humidity);
        quality = getView().findViewById(R.id.planesFragment_quality);

        setUpCategories();
        categorieSpinner.setOnItemSelectedListener(this);
    }

    private void setUpCategories() {
        ArrayAdapter<CharSequence> categorieAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.homeCat, R.layout.support_simple_spinner_dropdown_item);
        categorieAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        categorieSpinner.setAdapter(categorieAdapter);
    }

    public void updateUI(String place){
        data.setVisibility(View.VISIBLE);
        Context context = currentPlane.getContext();
        int id = context.getResources().
                getIdentifier(place, "drawable", context.getPackageName());
        if (place.equals("plano")){
            currentPlane.setBackgroundResource(id);
            currentPlane.setImageResource(0);
        }else {
            currentPlane.setBackgroundResource(R.color.transparent);
            currentPlane.setImageResource(id);
        }
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        if (parent.getId() == R.id.planesFragment_spinnerCat) {
            switch (parent.getItemAtPosition(pos).toString()){
                case "General":
                    updateUI("plano");
                    humdity.setText("40 %HR");
                    temperature.setText("24 ºC");
                    quality.setText("95/100");
                    break;
                case "Salón":
                    updateUI("plano_salon");
                    humdity.setText("60 %HR");
                    temperature.setText("25.1 ºC");
                    quality.setText("90/100");
                    break;
                case "Cocina":
                    updateUI("plano_cocina");
                    humdity.setText("40 %HR");
                    temperature.setText("25.5 ºC");
                    quality.setText("92/100");
                    break;
                case "Estudio":
                    updateUI("plano_estudio");
                    humdity.setText("40 %HR");
                    temperature.setText("24 ºC");
                    quality.setText("95/100");
                    break;
                case "Dormitorio pequeño":
                    updateUI("plano_dormitorio_peq");
                    humdity.setText("45 %HR");
                    temperature.setText("24.8 ºC");
                    quality.setText("95/100");
                    break;
                case "Dormitorio grande":
                    updateUI("plano_dormitorio_gra");
                    humdity.setText("45 %HR");
                    temperature.setText("25 ºC");
                    quality.setText("95/100");
                    break;
                case "Baño pequeño":
                    updateUI("plano_aseo_peq");
                    humdity.setText("50 %HR");
                    temperature.setText("25 ºC");
                    quality.setText("93/100");
                    break;
                case "Baño grande":
                    updateUI("plano_aseo_gra");
                    humdity.setText("60 %HR");
                    temperature.setText("25.1 ºC");
                    quality.setText("92/100");
                    break;
            }
        }
    }

    public void onNothingSelected(AdapterView<?> parent) { }
}
