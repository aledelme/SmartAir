package com.example.delme.smartair.Fragments;


import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.delme.smartair.R;

import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class OverviewFragment extends Fragment {

    NumerosAleatorios numerosAleatorios = new NumerosAleatorios();
    TextView outTemp, outHum, inTemp, inHum, oxyLvl, co2Lvl, airQlt;
    double rangos[][] = {{18.9, 19.1}, {55.2, 55.4}, {23.9, 24.1}, {49.8, 50.0}, {20.7, 20.8}, {515.0, 520.2}};
    double resultados[] = new double[6];

    public OverviewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_overview, container, false);

        outTemp = view.findViewById(R.id.outside_temp);
        outHum = view.findViewById(R.id.outside_moisture);
        inTemp = view.findViewById(R.id.inside_temp);
        inHum = view.findViewById(R.id.inside_moisture);
        oxyLvl = view.findViewById(R.id.oxygen_level);
        co2Lvl = view.findViewById(R.id.co2_level);
        airQlt = view.findViewById(R.id.air_quality);

        numerosAleatorios.execute();

        return view;
    }

    private class NumerosAleatorios extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            for (int i = 0; i < resultados.length; i++) {
                resultados[i] = rangos[i][0] + Math.random() * (rangos[i][1] - rangos[i][0]);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void voids){
            outTemp.setText(String.format(Locale.getDefault(), "%.1f", resultados[0]) + " ºC");
            outHum.setText(String.format(Locale.getDefault(), "%.1f", resultados[1]) + " %HR");
            inTemp.setText(String.format(Locale.getDefault(), "%.1f", resultados[2]) + " ºC");
            inHum.setText(String.format(Locale.getDefault(), "%.1f", resultados[3]) + " %HR");
            oxyLvl.setText(String.format(Locale.getDefault(), "%.1f", resultados[4]) + " %");
            co2Lvl.setText(String.format(Locale.getDefault(), "%.1f", resultados[5]) + " ppm");

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    NumerosAleatorios repetir = new NumerosAleatorios();
                    repetir.execute();
                }
            },2000);
        }
    }
}
