package com.example.delme.smartair.Fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.delme.smartair.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConfigurationsFragment extends Fragment {

    private static final int MIN_TEMP = 18;
    private static final int MAX_TEMP = 28;

    private TextView tvActivateAutomatic, tvAllowManual, tvSetTemp;
    private Switch swActivateSystem, swActivateAutomatic, swAllowManual;
    private Spinner spSetTemp;

    public ConfigurationsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        tvActivateAutomatic = view.findViewById(R.id.tv_activate_automatic);
        tvAllowManual = view.findViewById(R.id.tv_allow_manual);
        tvSetTemp = view.findViewById(R.id.tv_set_temp);
        swActivateSystem = view.findViewById(R.id.switch_activate_system);
        swActivateAutomatic = view.findViewById(R.id.switch_activate_automatic);
        swAllowManual = view.findViewById(R.id.switch_allow_manual);

        String[] temperaturas = new String[MAX_TEMP - MIN_TEMP + 1];
        for(int i = 0; i<temperaturas.length; i++){
            temperaturas[i] = String.valueOf(MIN_TEMP + i) + " ºC";
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_dropdown_item,temperaturas);
        spSetTemp = view.findViewById(R.id.spinner_set_temp);
        spSetTemp.setAdapter(adapter);

        swActivateSystem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(swActivateSystem.isChecked()) {
                    Toast.makeText(getContext(),"Sistema activado",Toast.LENGTH_SHORT).show();
                    setSystemActivated(true);
                }
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("¿Seguro que desea apagar el sistema?");
                    builder.setMessage("Los sensores dejarán de enviar información y los actuadores se apagarán");
                    builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            setSystemActivated(false);
                            Toast.makeText(getContext(),"Sistema apagado",Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            swActivateSystem.setChecked(true);
                        }
                    });
                    builder.show();
                }
            }
        });

        swActivateAutomatic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(swActivateAutomatic.isChecked()) {
                    Toast.makeText(getContext(),"Funcionamiento automático activado",Toast.LENGTH_SHORT).show();
                }
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("¿Desea apagar el funcionamiento automático?");
                    builder.setMessage("El sistema automático procura crear un hábitat de confort con " +
                            "los gastos energéticos mínimos.");
                    builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(getContext(),"Funcionamiento automático apagado",Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            swActivateAutomatic.setChecked(true);
                        }
                    });
                    builder.show();
                }
            }
        });

        swAllowManual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(swAllowManual.isChecked()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("¿Desea permitir configuraciones manuales de estancias?");
                    builder.setMessage("Esto permitirá que las estancias puedan ser configuradas de forma individual e" +
                            " independientes del modo automático");
                    builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(getContext(),"Configuraciones manuales permitidas",Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            swAllowManual.setChecked(false);
                        }
                    });
                    builder.show();
                }
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("¿Desea suspender las configuraciones manuales?");
                    builder.setMessage("Esto hará que todas las estancias regresen a la configuración automática");
                    builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(getContext(),"Configuraciones manuales suspendidas",Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            swAllowManual.setChecked(true);
                        }
                    });
                    builder.show();
                }
            }
        });

        return view;
    }

    public void setSystemActivated(boolean setter){
        tvActivateAutomatic.setEnabled(setter);
        swActivateAutomatic.setEnabled(setter);
        tvAllowManual.setEnabled(setter);
        swAllowManual.setEnabled(setter);
        tvSetTemp.setEnabled(setter);
        spSetTemp.setEnabled(setter);
    }

}
