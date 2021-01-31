package com.fgb.ventaya.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;

import com.fgb.ventaya.R;

public class PublicarFragment extends Fragment {

    ImageButton vehiculos;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pantalla_publicar, container, false);
        vehiculos = view.findViewById(R.id.imageButton13);

        vehiculos.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), PublicarVehiculos.class);
                getActivity().startActivity(i);
            }
        });

        // Inflate the layout for this fragment
        return view;

    }

}
