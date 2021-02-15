package com.fgb.ventaya.NuevasPublicacionesUI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
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
import com.fgb.ventaya.UI.PantallaIniciarSesion;
import com.fgb.ventaya.UI.PantallaInicio;
import com.fgb.ventaya.UI.PantallaRegistro;
import com.fgb.ventaya.map.MapActivity;
import com.fgb.ventaya.map.MapActivity2;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
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
    String idUser;
    String idUsuario;
    Boolean inicio = true;
    private MapView googleMap;
    public static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";
    TextView username, idusuarioInfo, mail, telefono;

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
        username = findViewById(R.id.usernameInfo);
        mail = findViewById(R.id.mailInfo);
        telefono = findViewById(R.id.telInfo);
        idusuarioInfo = findViewById(R.id.idUser);
        initGoogleMap(savedInstanceState);

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
                    if(isnapshot.getKey().toString().equals("idUsuario")){
                        idusuarioInfo.setText(isnapshot.getValue().toString());
                    }

                }
                    db.child("Users").child(idusuarioInfo.getText().toString()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()){
                                for (DataSnapshot isnapshot : snapshot.getChildren()){
                                    if(isnapshot.getKey().toString().equals("user")){
                                        username.setText(isnapshot.getValue().toString());
                                    }
                                    if(isnapshot.getKey().toString().equals("mail")){
                                        mail.setText(isnapshot.getValue().toString());
                                    }
                                    if(isnapshot.getKey().toString().equals("telefono")){
                                        telefono.setText(isnapshot.getValue().toString());
                                    }
                                }
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

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

        //Log.d("valor", idusuarioInfo.getText().toString());
        idUser = idusuarioInfo.getText().toString();




        setSupportActionBar(myToolbar);

        //para mostrar icono flecha atrás
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        carouselView = (CarouselView) findViewById(R.id.carouselView);
        carouselView.setPageCount(sampleImages.length);

        carouselView.setImageListener(imageListener);

    }


    private void initGoogleMap(Bundle savedInstanceState){
        // *** IMPORTANT ***
        // MapView requires that the Bundle you pass contain _ONLY_ MapView SDK
        // objects or sub-Bundles.
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }

        googleMap.onCreate(mapViewBundle);

        googleMap.getMapAsync(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAPVIEW_BUNDLE_KEY, mapViewBundle);
        }

        googleMap.onSaveInstanceState(mapViewBundle);
    }

    @Override
    public void onResume() {
        super.onResume();
        googleMap.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        googleMap.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        googleMap.onStop();
    }

    @Override
    public void onMapReady(GoogleMap map) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LatLng ubicacion = new LatLng(-31.6355675,-60.6876479);
        //map.addMarker(new MarkerOptions().position(ubicacion).title("Vendedor"));
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(ubicacion,15),100,null);
        CircleOptions circle = new CircleOptions()
                .center(ubicacion)
                .radius(300)
                .fillColor(0x66FF0000)
                .strokeColor(0x00000000);

        map.addCircle(circle);
        map.getUiSettings().setZoomControlsEnabled(false);
        map.getUiSettings().setAllGesturesEnabled(false);
        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                Intent i = new Intent(PantallaInfoPublicacion.this, MapActivity2.class);
                startActivity(i);
            }
        });
    }

    @Override
    public void onPause() {
        googleMap.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        googleMap.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        googleMap.onLowMemory();
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



}