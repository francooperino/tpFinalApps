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

public class PublicarIndumentaria extends AppCompatActivity {

    Toolbar myToolbar;
    Button botonSiguiente;
    EditText titulo, tipo, talle,telefono, marca, comentario, precio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla_publicar_indumentaria);
        myToolbar = findViewById(R.id.toolbarIndumentaria);
        botonSiguiente = findViewById(R.id.buttonRegistrar);
        titulo = findViewById(R.id.titulo);
        tipo = findViewById(R.id.tipoIndumentaria);
        talle = findViewById(R.id.talleIndumentaria);
        telefono = findViewById(R.id.textTelefono);
        marca = findViewById(R.id.marcaIndumentaria);
        precio = findViewById(R.id.precioIndumentaria);
        comentario = findViewById(R.id.textDescripcion);
        setSupportActionBar(myToolbar);
        //para mostrar icono flecha atrás
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        botonSiguiente.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent i = new Intent(PublicarIndumentaria.this, PantallaCargarImagenes.class);
                i.putExtra("titulo", titulo.getText().toString());
                i.putExtra("tipo", tipo.getText().toString());
                i.putExtra("talle",talle.getText().toString());
                i.putExtra("telefono", telefono.getText().toString());
                i.putExtra("marca",marca.getText().toString());
                i.putExtra("precio", precio.getText().toString());
                i.putExtra("comentario", comentario.getText().toString());
                i.putExtra("categoria","Indumentaria");


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