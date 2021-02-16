package com.fgb.ventaya.NuevasPublicacionesUI;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.fgb.ventaya.R;

public class PublicarMuebles extends AppCompatActivity {

    Toolbar myToolbar;
    Button botonSiguiente;
    EditText titulo, tipo, telefono, peso, comentario, precio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla_publicar_muebles);
        myToolbar = findViewById(R.id.toolbarMuebles);
        botonSiguiente = findViewById(R.id.buttonRegistrar);
        titulo = findViewById(R.id.titulo);
        tipo = findViewById(R.id.tipoMueble);
        telefono = findViewById(R.id.textTelefono);
        peso = findViewById(R.id.pesoMueble);
        comentario = findViewById(R.id.textDescripcion);
        precio = findViewById(R.id.precioMueble);

        setSupportActionBar(myToolbar);
        //para mostrar icono flecha atrás
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        botonSiguiente.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent i = new Intent(PublicarMuebles.this, PantallaCargarImagenes.class);
                i.putExtra("titulo", titulo.getText().toString());
                i.putExtra("tipo", tipo.getText().toString());
                i.putExtra("telefono", telefono.getText().toString());
                i.putExtra("peso",peso.getText().toString());
                i.putExtra("precio", precio.getText().toString());
                i.putExtra("comentario", comentario.getText().toString());
                i.putExtra("categoria", "Muebles");
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