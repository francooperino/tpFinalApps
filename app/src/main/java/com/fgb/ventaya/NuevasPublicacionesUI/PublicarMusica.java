package com.fgb.ventaya.NuevasPublicacionesUI;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.fgb.ventaya.R;

public class PublicarMusica extends AppCompatActivity {

    Toolbar myToolbar;
    Button botonSiguiente;
    EditText titulo, tipoInstrumento, textTelefono, marcaInstrumento, precio, colorInstrumento, descripcion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla_publicar_musica);
        myToolbar = findViewById(R.id.toolbarMusica);
        botonSiguiente = findViewById(R.id.buttonRegistrar);
        titulo = findViewById(R.id.titulo);
        tipoInstrumento = findViewById(R.id.tipoInstrumento);
        textTelefono = findViewById(R.id.textTelefono);
        marcaInstrumento = findViewById(R.id.marcaInstrumento);
        colorInstrumento = findViewById(R.id.colorInstrumento);
        precio = findViewById(R.id.precioInstrumento);
        descripcion = findViewById(R.id.textDescripcion);



        setSupportActionBar(myToolbar);
        //para mostrar icono flecha atrás
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        botonSiguiente.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PublicarMusica.this, PantallaCargarImagenes.class);
                i.putExtra("titulo", titulo.getText().toString());
                i.putExtra("tipo", tipoInstrumento.getText().toString());
                i.putExtra("telefono", textTelefono.getText().toString());
                i.putExtra("precio", precio.getText().toString());
                i.putExtra("comentario", descripcion.getText().toString());
                i.putExtra("marca",marcaInstrumento.getText().toString());
                i.putExtra("color",colorInstrumento.getText().toString());
                i.putExtra("categoria","Musica");
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