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
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.fgb.ventaya.R;
import java.util.Calendar;
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
