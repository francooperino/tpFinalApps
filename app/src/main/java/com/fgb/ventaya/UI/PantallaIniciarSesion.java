package com.fgb.ventaya.UI;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.fgb.ventaya.R;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PantallaIniciarSesion extends AppCompatActivity {

    private static final int RC_SIGN_IN = 123;
    Toolbar myToolbar;
    Button btnIniciarSesion;
    EditText usuario,password;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla_iniciarsesion);
        //inicializar elementos
        myToolbar = findViewById(R.id.toolbarIniciarSesion);
        btnIniciarSesion = findViewById(R.id.buttonIniciarSesion);
        usuario = findViewById(R.id.editTextTextPersonName);
        password = findViewById(R.id.editTextTextPassword);
        mAuth = FirebaseAuth.getInstance();

        setSupportActionBar(myToolbar);

        //para mostrar icono flecha atrás
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //intent hacia publicaciones
        btnIniciarSesion.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                iniciarSesion();

            }


        });


    }

    private void iniciarSesion() {
            mAuth.signInWithEmailAndPassword(usuario.getText().toString(),password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Intent i = new Intent(PantallaIniciarSesion.this, PantallaPublicaciones.class);
                        i.putExtra("pantalla", "iniciosesion");
                        startActivity(i);
                    }
                }
            });


    }


    //para aplicar funcionalidad flecha atrás
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }




}
