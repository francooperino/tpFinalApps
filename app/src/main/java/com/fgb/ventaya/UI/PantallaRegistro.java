package com.fgb.ventaya.UI;
import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.fgb.ventaya.NuevasPublicacionesUI.PantallaCargarImagenes;
import com.fgb.ventaya.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class PantallaRegistro extends AppCompatActivity {
    Toolbar myToolbar;
    private CheckBox checkTerminos;
    private Button registrar;
    private EditText nombre;
    private EditText apellido;
    private EditText username;
    private EditText correoElectronico;
    private EditText clave;
    private EditText clave2;
    private String name = "";
    private String mail = "";
    private String contraseña = "";
    private String apellidoo = "";
    private String user;
    private Boolean tipo= false;
    static final int CAMARA_REQUEST = 1;
    static final int GALERIA_REQUEST = 2;
    Uri imageUri;
    Uri downloadUri;
    private FirebaseAuth mAuth;
    private DatabaseReference db;
    private ImageButton buttonPerfil;
    private ImageView imagePerfil;
    byte[] datas;
    private TextView fotoText;
    private int valor=0;


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
            valor=1;
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            float proporcion = 600 / (float) imageBitmap.getWidth();
            imageBitmap = Bitmap.createScaledBitmap(imageBitmap,600,(int) (imageBitmap.getHeight() * proporcion),false);
            imagePerfil.setImageBitmap(imageBitmap);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            datas = baos.toByteArray();

        }
        if(requestCode == GALERIA_REQUEST && resultCode == RESULT_OK){
            imageUri = data.getData();
            valor=2;
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                float proporcion = 600 / (float) bitmap.getWidth();
                bitmap = Bitmap.createScaledBitmap(bitmap,600,(int) (bitmap.getHeight() * proporcion),false);
                imagePerfil.setImageBitmap(bitmap);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                datas = baos.toByteArray(); // Imagen en arreglo de bytes

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla_registro);
        //inicializar elementos
        myToolbar = findViewById(R.id.toolbarMainActivity);
        checkTerminos = findViewById(R.id.checkTerminos);
        registrar = findViewById(R.id.buttonRegistrar);
        correoElectronico = findViewById(R.id.textCorreo);
        clave = findViewById(R.id.textContrasena);
        clave2 = findViewById(R.id.textcontrasena2);
        nombre = findViewById(R.id.textNombre);
        apellido = findViewById(R.id.textApellido);
        username = findViewById(R.id.textUsername);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance().getReference();
        setSupportActionBar(myToolbar);
        //buttonPerfil = findViewById(R.id.imageButtonPerfil);
        fotoText = findViewById(R.id.textFotoPerfil);
        imagePerfil = findViewById(R.id.imageViewPerfil);
        //para mostrar icono flecha atrás
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //habilitar o deshabilitar boton de registrarse según acepte terminos&condic
        checkTerminos.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    registrar.setEnabled(true);
                }
                else {
                    registrar.setEnabled(false);
                }
            }
        });

        fotoText.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(PantallaRegistro.this, Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(PantallaRegistro.this,
                            new String[]{Manifest.permission.CAMERA},
                            9999);

                }

                AlertDialog.Builder builder= new AlertDialog.Builder(PantallaRegistro.this);
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

        registrar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                name = nombre.getText().toString();
                mail = correoElectronico.getText().toString();
                contraseña = clave.getText().toString();
                apellidoo = apellido.getText().toString();
                user= username.getText().toString();
                if(!validarCampos((EditText) findViewById(R.id.textNombre))){
                    registrarUsuario();
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

    private void registrarUsuario() {
        mAuth.createUserWithEmailAndPassword(mail,contraseña).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Map<String, Object> map= new HashMap<>();
                    map.put("name",name);
                    map.put("mail",mail);
                    map.put("contraseña",contraseña);
                    map.put("apellido",apellidoo);
                    map.put("user",user);

                    String id= mAuth.getCurrentUser().getUid();
                        db.child("Users").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task2) {
                                if(task2.isSuccessful()){
                                    Toast.makeText(PantallaRegistro.this, "Usuario creado con exito",Toast.LENGTH_LONG).show();
                                    Intent i = new Intent(PantallaRegistro.this, PantallaPublicaciones.class);
                                    startActivity(i);
                                    /*if(valor==1){
                                        i.putExtra("codigo", 1);
                                        startActivity(i);
                                    }
                                    else{
                                        if(valor==2){
                                            i.putExtras(b);
                                            i.putExtra("codigo", 2);
                                            startActivity(i);
                                        }
                                        else{
                                           // i.putExtra("codigo", 0);
                                            startActivity(i);
                                        }
                                    }*/

                                }
                                else {
                                    Toast.makeText(PantallaRegistro.this, "No se pudo crear el usuario",Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                }
                else{
                    Toast.makeText(PantallaRegistro.this, "No se pudo crear el usuario",Toast.LENGTH_LONG).show();
                }
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


    //funcion para validar campos para registrar el user
    public boolean validarCampos(View v){
        boolean validar=false;
        if (!isValidEmail(correoElectronico.getText().toString())) {
            correoElectronico.setError("El correo electrónico no es valido");
            validar=true;
        }
        if (nombre.getText().toString().isEmpty()) {
            nombre.setError("No tengas miedo! decinos tu nombre!");
            validar=true;
        }
        if (apellido.getText().toString().isEmpty()) {
            apellido.setError("Decinos tu apellido!");
            validar=true;
        }
        if (username.getText().toString().isEmpty()) {
            username.setError("Elegí un nombre de usuario!");
            validar=true;
        }
        if (clave.getText().toString().isEmpty()) {
            clave.setError("Por favor elija una contraseña");
            validar=true;
        }
        if (clave2.getText().toString().isEmpty()) {
            clave2.setError("Por favor elija una contraseña");
            validar=true;
        }

        if (!clave.getText().toString().equals(clave2.getText().toString())) {
            clave2.setError("Las claves deben ser iguales");
            validar=true;
        }
        return validar;
    }
    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

}
