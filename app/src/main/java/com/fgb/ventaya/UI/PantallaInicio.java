package com.fgb.ventaya.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.fgb.ventaya.R;

public class PantallaInicio extends AppCompatActivity {

    //creacion de elementos
    Button btnLogin, btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla_inicio);

        //inicializar elementos
        btnLogin = findViewById(R.id.buttonLogin);
        btnRegister = findViewById(R.id.buttonRegister);

        //intent hacia login
        btnLogin.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View view) {
                Intent i = new Intent(PantallaInicio.this, PantallaIniciarSesion.class);
                startActivity(i);
            }
        });

        //intent hacia registrarse
        btnRegister.setOnClickListener(new View.OnClickListener(){

            //intent hacia login
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PantallaInicio.this, PantallaRegistro.class);
                startActivity(i);
            }
        });

    }




}
