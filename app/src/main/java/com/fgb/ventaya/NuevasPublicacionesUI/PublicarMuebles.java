package com.fgb.ventaya.NuevasPublicacionesUI;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.fgb.ventaya.R;

public class PublicarMuebles extends AppCompatActivity {

    Toolbar myToolbar;
    Button botonSiguiente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla_publicar_muebles);
        myToolbar = findViewById(R.id.toolbarMuebles);
        botonSiguiente = findViewById(R.id.buttonRegistrar);

        setSupportActionBar(myToolbar);
        //para mostrar icono flecha atrás
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        botonSiguiente.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent i = new Intent(PublicarMuebles.this, PantallaCargarImagenes.class);
                startActivity(i);

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
