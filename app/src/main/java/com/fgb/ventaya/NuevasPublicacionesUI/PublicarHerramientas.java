package com.fgb.ventaya.NuevasPublicacionesUI;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.fgb.ventaya.R;

public class PublicarHerramientas extends AppCompatActivity {

    Toolbar myToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla_publicar_herramientas);
        myToolbar = findViewById(R.id.toolbarHerramientas);


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
