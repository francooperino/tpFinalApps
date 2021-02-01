package com.fgb.ventaya.UI;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.fgb.ventaya.NuevasPublicacionesUI.PantallaCargarImagenes;
import com.fgb.ventaya.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

    private FirebaseAuth mAuth;
    private DatabaseReference db;
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

        registrar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                validarCampos((EditText) findViewById(R.id.textNombre));
                name = nombre.getText().toString();
                mail = correoElectronico.getText().toString();
                contraseña = clave.getText().toString();
                apellidoo = apellido.getText().toString();
                user= username.getText().toString();
                registrarUsuario();
            }
        });

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
                                    Toast.makeText(PantallaRegistro.this, "Publicacion Creada",Toast.LENGTH_LONG).show();
                                }
                                else {
                                    Toast.makeText(PantallaRegistro.this, "Nose pudo crear el usuario",Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                }
                else{
                    Toast.makeText(PantallaRegistro.this, "Nose pudo crear el usuario",Toast.LENGTH_LONG).show();
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
    public void validarCampos(View v){
        if (!isValidEmail(correoElectronico.getText().toString())) {
            correoElectronico.setError("El correo electrónico no es valido");
        }
        if (nombre.getText().toString().isEmpty()) {
            nombre.setError("No tengas miedo! decinos tu nombre!");
        }
        if (apellido.getText().toString().isEmpty()) {
            apellido.setError("Decinos tu apellido!");
        }
        if (username.getText().toString().isEmpty()) {
            username.setError("Elegí un nombre de usuario!");
        }
        if (clave.getText().toString().isEmpty()) {
            clave.setError("Por favor elija una contraseña");
        }
        if (clave2.getText().toString().isEmpty()) {
            clave2.setError("Por favor elija una contraseña");
        }

        if (!clave.getText().toString().equals(clave2.getText().toString())) {
            clave2.setError("Las claves deben que ser iguales");
        }
    }
    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

}
