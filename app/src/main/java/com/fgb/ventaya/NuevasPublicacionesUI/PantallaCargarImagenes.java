package com.fgb.ventaya.NuevasPublicacionesUI;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.fgb.ventaya.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

public class PantallaCargarImagenes extends AppCompatActivity {

    private int botonSeleccionado=0;
    static final int CAMARA_REQUEST = 1;
    static final int GALERIA_REQUEST = 2;
    Uri imageUri;
    Uri downloadUri;
    Boolean tieneImagen=false;
    byte[] datas;
    byte[] datas1;
    byte[] datas2;
    byte[] datas3;
    ArrayList<byte[]> datos;
    private Boolean tipo= false;
    ImageView imagen1;
    ImageView imagen2;
    ImageView imagen3;
    ImageButton button1;
    ImageButton button2;
    ImageButton button3;
    Button publicar;
    Toolbar myToolbar;
    private void lanzarCamara() {
        Intent camaraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(camaraIntent, CAMARA_REQUEST);
    }

    private void abrirGaleria() {
        Intent galeriaIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(galeriaIntent, GALERIA_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMARA_REQUEST  && resultCode == RESULT_OK) {
            tieneImagen=true;
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            float proporcion = 600 / (float) imageBitmap.getWidth();
            imageBitmap = Bitmap.createScaledBitmap(imageBitmap,600,(int) (imageBitmap.getHeight() * proporcion),false);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            switch(botonSeleccionado){
                case 1:
                    imagen1.setImageBitmap(imageBitmap);
                    button1.setVisibility(View.GONE);
                    imagen1.setVisibility(View.VISIBLE);
                    imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    datas1 = baos.toByteArray(); // Imagen en arreglo de bytes
                    datos.add(datas1);
                    break;
                case 2:
                    imagen2.setImageBitmap(imageBitmap);
                    button2.setVisibility(View.GONE);
                    imagen2.setVisibility(View.VISIBLE);
                    imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    datas2 = baos.toByteArray(); // Imagen en arreglo de bytes
                    datos.add(datas2);
                    break;
                case 3:
                    imagen3.setImageBitmap(imageBitmap);
                    button3.setVisibility(View.GONE);
                    imagen3.setVisibility(View.VISIBLE);
                    imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    datas3 = baos.toByteArray(); // Imagen en arreglo de bytes
                    datos.add(datas3);
                    break;
            }

            //ByteArrayOutputStream baos = new ByteArrayOutputStream();
            //imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            //datas = baos.toByteArray(); // Imagen en arreglo de bytes

        }
        if(requestCode == GALERIA_REQUEST && resultCode == RESULT_OK){
            tieneImagen=true;
            imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                float proporcion = 600 / (float) bitmap.getWidth();
                bitmap = Bitmap.createScaledBitmap(bitmap,600,(int) (bitmap.getHeight() * proporcion),false);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                switch(botonSeleccionado){
                    case 1:
                        imagen1.setImageBitmap(bitmap);
                        button1.setVisibility(View.GONE);
                        imagen1.setVisibility(View.VISIBLE);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                        datas1 = baos.toByteArray(); // Imagen en arreglo de bytes
                        datos.add(datas1);
                        break;
                    case 2:
                        imagen2.setImageBitmap(bitmap);
                        button2.setVisibility(View.GONE);
                        imagen2.setVisibility(View.VISIBLE);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                        datas2 = baos.toByteArray(); // Imagen en arreglo de bytes
                        datos.add(datas2);
                        break;
                    case 3:
                        imagen3.setImageBitmap(bitmap);
                        button3.setVisibility(View.GONE);
                        imagen3.setVisibility(View.VISIBLE);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                        datas3 = baos.toByteArray(); // Imagen en arreglo de bytes
                        datos.add(datas3);
                        break;
                }

                //ByteArrayOutputStream baos = new ByteArrayOutputStream();
                //bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                //datas = baos.toByteArray(); // Imagen en arreglo de bytes

            } catch (IOException e) {
                e.printStackTrace();
            }

            /*imagenPlato.setImageURI(imageUri);
            imagenPlato.setMaxWidth(50);
            imagenPlato.setMaxHeight(50);*/

        }
    }
    private Boolean subirImagen(UUID id, byte[] imagen) {
        final Boolean[] result = {false};
        // Creamos una referencia a nuestro Storage
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

        // Creamos una referencia a 'images/plato_id.jpg'
        StorageReference platosImagesRef = storageRef.child("images/"+id.toString()+".jpg");

        // Cual quiera de los tres métodos tienen la misma implementación, se debe utilizar el que corresponda
        UploadTask uploadTask = platosImagesRef.putBytes(imagen);
        // UploadTask uploadTask = platosImagesRef.putFile(file);
        // UploadTask uploadTask = platosImagesRef.putStream(stream);

        // Registramos un listener para saber el resultado de la operación
        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    Toast.makeText(PantallaCargarImagenes.this, "error",Toast.LENGTH_LONG).show();
                    throw task.getException();
                }

                // Continuamos con la tarea para obtener la URL
                return platosImagesRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {

                if (task.isSuccessful()) {
                    // URL de descarga del archivo
                    downloadUri = task.getResult();
                    result[0] =true;
                }
                else{
                    Toast.makeText(PantallaCargarImagenes.this, "No se pudo cargar la imagen",Toast.LENGTH_LONG).show();
                }
            }
        });
        return result[0];
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla_cargar_imagenes);
        imagen1 = findViewById(R.id.imageView1);
        imagen2 = findViewById(R.id.imageView2);
        imagen3 = findViewById(R.id.imageView3);
        button1 = findViewById(R.id.imageButton1);
        button2 = findViewById(R.id.imageButton2);
        button3 = findViewById(R.id.imageButton3);
        publicar = findViewById(R.id.button);
        myToolbar = findViewById(R.id.toolbarImagenes);

        datos = new ArrayList<byte[]>();
        //clickListener(button1);
        //clickListener(button2);
        //clickListener(button3);

        setSupportActionBar(myToolbar);
        //para mostrar icono flecha atrás
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        button1.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                botonSeleccionado=1;
                if (ActivityCompat.checkSelfPermission(PantallaCargarImagenes.this, Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(PantallaCargarImagenes.this,
                            new String[]{Manifest.permission.CAMERA},
                            9999);

                }

                AlertDialog.Builder builder= new AlertDialog.Builder(PantallaCargarImagenes.this);
                builder.setMessage("Seleccione desde donde desea cargar la imagen")
                        .setTitle("Cargar Imagen")
                        .setPositiveButton("Camara",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dlgInt, int i) {
                                        tipo=true;
                                        lanzarCamara();
                                    }
                                })
                        .setNegativeButton("Galeria",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dlgInt, int i) {
                                        abrirGaleria();
                                        //TODO: pedir permisos galeria
                                    }
                                });
                AlertDialog dialog= builder.create();
                dialog.show();


            }

        });

        button2.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                botonSeleccionado=2;
                if (ActivityCompat.checkSelfPermission(PantallaCargarImagenes.this, Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(PantallaCargarImagenes.this,
                            new String[]{Manifest.permission.CAMERA},
                            9999);

                }

                AlertDialog.Builder builder= new AlertDialog.Builder(PantallaCargarImagenes.this);
                builder.setMessage("Seleccione desde donde desea cargar la imagen")
                        .setTitle("Cargar Imagen")
                        .setPositiveButton("Camara",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dlgInt, int i) {
                                        tipo=true;
                                        lanzarCamara();
                                    }
                                })
                        .setNegativeButton("Galeria",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dlgInt, int i) {
                                        abrirGaleria();
                                        //TODO: pedir permisos galeria
                                    }
                                });
                AlertDialog dialog= builder.create();
                dialog.show();


            }

        });

        button3.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                botonSeleccionado=3;
                if (ActivityCompat.checkSelfPermission(PantallaCargarImagenes.this, Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(PantallaCargarImagenes.this,
                            new String[]{Manifest.permission.CAMERA},
                            9999);

                }

                AlertDialog.Builder builder= new AlertDialog.Builder(PantallaCargarImagenes.this);
                builder.setMessage("Seleccione desde donde desea cargar la imagen")
                        .setTitle("Cargar Imagen")
                        .setPositiveButton("Camara",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dlgInt, int i) {
                                        tipo=true;
                                        lanzarCamara();
                                    }
                                })
                        .setNegativeButton("Galeria",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dlgInt, int i) {
                                        abrirGaleria();
                                        //TODO: pedir permisos galeria
                                    }
                                });
                AlertDialog dialog= builder.create();
                dialog.show();


            }

        });

        publicar.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                //Log.d("ID", ID.toString());
                //Toast.makeText(PantallaCargarImagenes.this, ID.toString(),Toast.LENGTH_LONG).show();
                int m =datos.size();
                for(int i=0;i<m;i++){
                    UUID ID = UUID.randomUUID();
                    subirImagen(ID,datos.get(i));
                }


            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 9999 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                if (tipo==true){
                    lanzarCamara();
                }
                else{
                    abrirGaleria();
                }

            }

        }
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

    /*public void clickListener (View vista){

        vista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(v.getId()){
                    case R.id.imageButton1:
                        botonSeleccionado=1;
                        break;
                    case R.id.imageButton2:
                        botonSeleccionado=2;
                        break;
                    case R.id.imageButton3:
                        botonSeleccionado=3;
                        break;
                }
                if (ActivityCompat.checkSelfPermission(PantallaCargarImagenes.this, Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(PantallaCargarImagenes.this,
                            new String[]{Manifest.permission.CAMERA},
                            9999);

                }

                AlertDialog.Builder builder= new AlertDialog.Builder(PantallaCargarImagenes.this);
                builder.setMessage("Seleccione desde donde desea cargar la imagen")
                        .setTitle("Cargar Imagen")
                        .setPositiveButton("Camara",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dlgInt, int i) {
                                        tipo=true;
                                        lanzarCamara();
                                    }
                                })
                        .setNegativeButton("Galeria",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dlgInt, int i) {
                                        abrirGaleria();
                                        //TODO: pedir permisos galeria
                                    }
                                });
                AlertDialog dialog= builder.create();
                dialog.show();
            }
        });
    }*/


}
