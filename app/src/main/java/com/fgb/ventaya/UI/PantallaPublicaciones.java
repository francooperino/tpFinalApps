package com.fgb.ventaya.UI;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ColorSpace;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fgb.ventaya.Adapters.RecyclerAdapterPublicaciones;
import com.fgb.ventaya.Entity.Publicacion;
import com.fgb.ventaya.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.io.ByteArrayOutputStream;

public class PantallaPublicaciones extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar myToolbar;
    ActionBarDrawerToggle toggle;
    ImageView ip;
    RecyclerView recycler;
    RecyclerAdapterPublicaciones recyclerAdapterPublicaciones;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla_publicaciones);

        //inicializacion elementos
        myToolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.nav_view);
        drawerLayout = findViewById(R.id.drawer_layout);
        recycler=(RecyclerView) findViewById(R.id.recyclerPublicaciones);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        getSupportFragmentManager().beginTransaction().add(R.id.content, new HomeFragment()).commit();
        setTitle("Home");

        //Setup toolbar
        setSupportActionBar(myToolbar);

        toggle = new ActionBarDrawerToggle(this, drawerLayout,myToolbar,R.string.drawer_open,R.string.drawer_close);
        drawerLayout.addDrawerListener(toggle);


        navigationView.setNavigationItemSelectedListener(this);
        ip = (ImageView) navigationView.getMenu().findItem(R.id.imageViewPerfil);


        /*//if(getIntent().getExtras().getInt("codigo") != 0) {
            //Bundle parametros= this.getIntent().getExtras();
            //Bitmap bitmap = BitmapFactory.decodeByteArray(parametros.getByteArray("imagen"),0,parametros.getByteArray("imagen").length);
            //ip.setImageBitmap(bitmap);

            //Intent intent = getIntent();
            //Bitmap bitmap = (Bitmap) intent.getParcelableExtra("fotoPerfil");
            //ip.setImageBitmap(bitmap);
        Bundle parametros= this.getIntent().getExtras();
        Bitmap imageB = (Bitmap) parametros.get("data");
        float proporcion = 600 / (float) imageB.getWidth();
        imageB = Bitmap.createScaledBitmap(imageB,600,(int) (imageB.getHeight() * proporcion),false);
        ip.setImageBitmap(imageB);
        //}*/
        FirebaseRecyclerOptions<Publicacion> options =
            new FirebaseRecyclerOptions.Builder<Publicacion>()
            .setQuery(FirebaseDatabase.getInstance().getReference().child("Publicaciones"),Publicacion.class)
            .build();
        recyclerAdapterPublicaciones = new RecyclerAdapterPublicaciones(options);
        recycler.setAdapter(recyclerAdapterPublicaciones);
    }

    @Override
    protected  void onStart() {
        super.onStart();
        recyclerAdapterPublicaciones.startListening();
    }
    @Override
    protected  void onStop() {
        super.onStop();
        recyclerAdapterPublicaciones.stopListening();
    }


    @Override //agrega la funcionalidad de búsqueda en la toolbar!
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        MenuItem.OnActionExpandListener onActionExpandListener=new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                Toast.makeText(PantallaPublicaciones.this, "expandido", Toast.LENGTH_SHORT).show();
                return true;
            }
            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                Toast.makeText(PantallaPublicaciones.this, "colapsado", Toast.LENGTH_SHORT).show();
                return true;
            }
        };
        menu.findItem(R.id.search).setOnActionExpandListener(onActionExpandListener);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setQueryHint("¿Qué querés buscar?");
        return true;
    }


    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        toggle.syncState();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        toggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        selectItemNav(item);
        return true;
    }

    private void selectItemNav(MenuItem item) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        switch (item.getItemId()){
            case R.id.nav_home:
                ft.replace(R.id.content, new HomeFragment()).commit();
                break;
            case R.id.nav_profile:
                ft.replace(R.id.content, new PerfilFragment()).commit();
                break;
            case R.id.nav_publicar:
                ft.replace(R.id.content, new PublicarFragment()).commit();
                break;
            case R.id.nav_mis_publicaciones:
                ft.replace(R.id.content, new MisPublicacionesFragment()).commit();
                break;
            case R.id.nav_categorias:
                ft.replace(R.id.content, new CategoriasFragment()).commit();
                break;
            case R.id.nav_favoritos:
                ft.replace(R.id.content, new FavoritosFragment()).commit();
                break;
            case R.id.nav_closeSesion:
                ft.replace(R.id.content, new CerrarSesionFragment()).commit();
                break;
        }
        setTitle(item.getTitle());
        drawerLayout.closeDrawers();

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(toggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

