package com.fgb.ventaya.NuevasPublicacionesUI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.fgb.ventaya.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PantallaInfoPublicacion extends AppCompatActivity implements OnMapReadyCallback {
    Toolbar myToolbar;
    TextView titulo;
    Intent intent;
    TextView descripcion;
    TextView precio;
    String concat;
    CarouselView carouselView;
    String idPublicacion;
    ImageView imagenPubli [];
    Boolean publicionFavorita = false;
    String idUsuario;
    Boolean inicio = true;
    private View googleMap;

    ArrayList<String> url = new ArrayList<String>();
    int[] sampleImages = {R.drawable.baloncesto, R.drawable.categoria_vehiculos, R.drawable.categoria_electronica};

    DatabaseReference db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla_info_publicacion);
        intent = getIntent();
        myToolbar = findViewById(R.id.toolbarPublicacion);

        //inicializamos datos que nos pasa la activity anterior
        idPublicacion = intent.getExtras().get("id").toString();
        titulo = findViewById(R.id.titPublicacion);
        titulo.setText(intent.getExtras().get("Titulo").toString());
        descripcion = findViewById(R.id.descPublicacion);
        descripcion.setText(intent.getExtras().get("Descripcion").toString());
        precio= findViewById(R.id.precPublicacion);
        concat = "$ " +intent.getExtras().get("Precio").toString();
        precio.setText(concat);
        imagenPubli = new ImageView[4];
        imagenPubli[0] = findViewById(R.id.imagenPubli);
        imagenPubli[1] = findViewById(R.id.imagenPubli2);
        imagenPubli[2] = findViewById(R.id.imagenPubli3);
        googleMap = findViewById(R.id.mapaVendedor);

        ImageSlider imageSlider = findViewById(R.id.slider);
        List<SlideModel> slideModels = new ArrayList<>();


        //imageSlider.setImageList(slideModels,true);


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        idUsuario = user.getUid();
        //ahora para recuperar fotos de la publi
        db = FirebaseDatabase.getInstance().getReference();



        db.child("Publicacion").child(idPublicacion).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(inicio){
                for (DataSnapshot isnapshot : snapshot.getChildren()){
                    if (isnapshot.getValue().toString().contains("https://firebasestorage")){
                        url.add(isnapshot.getValue().toString());
                    }

                }
                //traemos imagenes con Glide para cada link
                for (int i=0; i<url.size(); i++) {
                    Glide.with(getApplicationContext())
                            .load(url.get(i))
                            .into(imagenPubli[i]);
                }

                for (int i=0; i<url.size(); i++) {
                    slideModels.add(new SlideModel(url.get(i)));
                }
                imageSlider.setImageList(slideModels,true);

            }}

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });







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



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbarinfopublicaciones_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.favoritos:


                inicio = false;
                if(publicionFavorita){
                  DatabaseReference mPostReference =  db.child("Publicacion").child(idPublicacion).child(idUsuario);

                  mPostReference.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {

                      @Override
                      public void onComplete(@NonNull Task<Void> task2) {
                          if (task2.isSuccessful()) {
                              //item.setIcon(R.drawable.flecha);
                              Toast.makeText(PantallaInfoPublicacion.this, "Se quito como favorito!", Toast.LENGTH_LONG).show();
                              publicionFavorita = false;
                              ActualizarIconoToolbar(item);


                          } else {
                              Toast.makeText(PantallaInfoPublicacion.this, "No se pudo quitar favorito", Toast.LENGTH_LONG).show();
                              publicionFavorita = false;
                          }
                      }
                  });

                } else {
                Map<String, Object> map= new HashMap<>();
                map.put(idUsuario,"Favorito");
                db.child("Publicacion").child(idPublicacion).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {

                    @Override
                    public void onComplete(@NonNull Task<Void> task2) {
                        if (task2.isSuccessful()) {
                            //item.setIcon(R.drawable.flecha);
                            Toast.makeText(PantallaInfoPublicacion.this, "Agregado a favoritos!", Toast.LENGTH_LONG).show();
                            publicionFavorita = true;
                            ActualizarIconoToolbar(item);


                        } else {
                            Toast.makeText(PantallaInfoPublicacion.this, "No se pudo establecer como favorito", Toast.LENGTH_LONG).show();
                            publicionFavorita = false;
                        }
                    }
                }); }

                return true;

        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        MenuItem settingsItem = menu.findItem(R.id.favoritos);


        db.child("Publicacion").child(idPublicacion).child(idUsuario).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get user value
                        if (dataSnapshot.getValue() != null) {
                            if (dataSnapshot.getValue().equals("Favorito")) {
                                publicionFavorita = true;

                            } else {
                                publicionFavorita = false;
                            }
                        }else {
                            publicionFavorita = false;
                        }
                        ActualizarIconoToolbar(settingsItem);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        return super.onPrepareOptionsMenu(menu);
    }

    private void ActualizarIconoToolbar( MenuItem settingsItem){
        if(publicionFavorita){
            settingsItem.setIcon(ContextCompat.getDrawable(PantallaInfoPublicacion.this, R.drawable.ic_favoritos));
        }else {
            settingsItem.setIcon(ContextCompat.getDrawable(PantallaInfoPublicacion.this, R.drawable.ic_no_es_favorito));
        }

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

    }
}