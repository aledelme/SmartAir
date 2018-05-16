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

import com.example.delme.smartair.R;

public class PlanesFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    public PlanesFragment() {}
    private Spinner categorieSpinner;
    private ImageView currentPlane;
    private LinearLayout data;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle s) {
        return inflater.inflate(R.layout.fragment_planes, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        categorieSpinner = getView().findViewById(R.id.planesFragment_spinnerCat);
        data = getView().findViewById(R.id.planesFragment_data);
        currentPlane = getView().findViewById(R.id.planesFragment_planeImageView);
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
                    break;
                case "Salón":
                    updateUI("plano_salon");
                    break;
                case "Cocina":
                    updateUI("plano_cocina");
                    break;
                case "Estudio":
                    updateUI("plano_estudio");
                    break;
                case "Dormitorio pequeño":
                    updateUI("plano_dormitorio_peq");
                    break;
                case "Dormitorio grande":
                    updateUI("plano_dormitorio_gra");
                    break;
                case "Baño pequeño":
                    updateUI("plano_aseo_peq");
                    break;
                case "Baño grande":
                    updateUI("plano_aseo_gra");
                    break;
            }
        }
    }

    public void onNothingSelected(AdapterView<?> parent) { }
}
