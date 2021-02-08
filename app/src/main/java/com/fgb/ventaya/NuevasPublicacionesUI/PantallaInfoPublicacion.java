package com.fgb.ventaya.NuevasPublicacionesUI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.fgb.ventaya.R;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

public class PantallaInfoPublicacion extends AppCompatActivity {
    Toolbar myToolbar;
    TextView titulo;
    Intent intent;

    CarouselView carouselView;

    int[] sampleImages = {R.drawable.baloncesto, R.drawable.categoria_vehiculos, R.drawable.categoria_electronica};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla_info_publicacion);
        intent = getIntent();
        myToolbar = findViewById(R.id.toolbarPublicacion);

        //titulo = findViewById(R.id.textViewTitulo);
        //titulo.setText(intent.getExtras().get("Titulo").toString());

        setSupportActionBar(myToolbar);
        //para mostrar icono flecha atrás
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        carouselView = (CarouselView) findViewById(R.id.carouselView);
        carouselView.setPageCount(sampleImages.length);

        carouselView.setImageListener(imageListener);

    }

    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(sampleImages[position]);
        }
    };

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