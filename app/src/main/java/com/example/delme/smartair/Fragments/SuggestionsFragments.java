package com.example.delme.smartair.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.delme.smartair.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SuggestionsFragments extends Fragment {

    public SuggestionsFragments() {
        // Required empty public constructor
    }

    //https://www.siberzone.es/blog-sistemas-ventilacion/niveles-recomendados-co2-temperatura/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_suggestions_fragments, container, false);
    }

}
