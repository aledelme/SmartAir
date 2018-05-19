package com.example.delme.smartair.Fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.delme.smartair.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConsumptionFragment extends Fragment {

    public PieChart monthConsum;
    public PieChart predictedConsum;
    public PieChart energySaved;

    public float arrayMonthEntries[] = {70,30};
    public float arrayPredictedEntries[] = {300,90};


    public ConsumptionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_consumption, container, false);

        List<PieEntry> monthEntries = Arrays.asList(new PieEntry(arrayMonthEntries[0],"Refrigeración"),
                new PieEntry(arrayMonthEntries[1],"Calefacción"));

        List<PieEntry> predictedEntries = Arrays.asList(new PieEntry(arrayPredictedEntries[0],"Refrigeración"),
                new PieEntry(arrayPredictedEntries[1],"Calefacción"));

        List<PieEntry> savedEntries = Arrays.asList(new PieEntry(arrayPredictedEntries[0]-arrayMonthEntries[0],"Refrigeración"),
                new PieEntry(arrayPredictedEntries[1]-arrayMonthEntries[1],"Calefacción"));

        monthConsum = view.findViewById(R.id.monthly_consumption);
        monthConsum.setData(generatePieData(monthConsum,monthEntries));

        predictedConsum = view.findViewById(R.id.predicted_consumption);
        predictedConsum.setData(generatePieData(predictedConsum,predictedEntries));

        energySaved = view.findViewById(R.id.energy_saved);
        energySaved.setData(generatePieData(energySaved,savedEntries));

        return view;
    }

    public PieData generatePieData(PieChart pieChart, List<PieEntry> pieEntry){
        pieChart.getDescription().setEnabled(false);
        pieChart.getLegend().setEnabled(false);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.animateY(1500);
        pieChart.setEntryLabelTextSize(20);
        pieChart.setCenterText("kWh");
        pieChart.setCenterTextSize(30);
        PieDataSet set = new PieDataSet(pieEntry, null);
        set.setColors(Color.BLUE,Color.RED);
        set.setValueTextSize(20);
        set.setSliceSpace(5);
        return new PieData(set);
    }

}
