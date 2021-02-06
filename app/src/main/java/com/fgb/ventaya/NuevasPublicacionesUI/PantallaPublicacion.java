package com.fgb.ventaya.NuevasPublicacionesUI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.fgb.ventaya.R;

public class PantallaPublicacion extends AppCompatActivity {
    Toolbar myToolbar;
    TextView titulo;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publicacion);
        intent = getIntent();
        myToolbar = findViewById(R.id.toolbarPublicacion);
        titulo = findViewById(R.id.textViewTitulo);
        titulo.setText(intent.getExtras().get("Titulo").toString());
        setSupportActionBar(myToolbar);
        //para mostrar icono flecha atrás
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
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