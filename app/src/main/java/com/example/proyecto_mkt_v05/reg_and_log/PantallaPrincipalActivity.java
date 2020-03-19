package com.example.proyecto_mkt_v05.reg_and_log;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.proyecto_mkt_v05.utilities.ConnectivityIsNotWorking;
import com.example.proyecto_mkt_v05.MainActivity;
import com.example.proyecto_mkt_v05.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class PantallaPrincipalActivity extends AppCompatActivity
        implements RegistrarUsuarioFragment.OnFragmentInteractionListener {

    private EditText correo, contrasenha;
    private CheckBox mantenerSesion;
    private ProgressDialog loadingBar;

    private FirebaseAuth auth;

    private String email, password;
    private Boolean condition = false, isOpen = false;

    protected FrameLayout frameRegistro;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.iniciar_sesion);

        correo = findViewById(R.id.id_correo);
        contrasenha = findViewById(R.id.id_contrasenha);
        Button botonLoggear = findViewById(R.id.id_boton_iniciar_sesion);
        Button botonRegistrarUsuario = findViewById(R.id.id_boton_registrar_usuario);
        final ImageButton botonVerContrasenha = findViewById(R.id.id_boton_ver_contrasenha);
        mantenerSesion = findViewById(R.id.id_mantener_sesion);

        loadPreferences();

        auth = FirebaseAuth.getInstance();

        if(isOpen){
            if(getIntent().getBooleanExtra("SESION_FINISHED", false)){
                contrasenha.setText("");
                mantenerSesion.setChecked(false);
                clearPreferences();
            } else {
                loginUsuario();
            }
        }

        botonLoggear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = correo.getText().toString();
                password = contrasenha.getText().toString();
                condition = mantenerSesion.isChecked();

                if(!email.isEmpty() && !password.isEmpty()){
                    loadingBar = new ProgressDialog(PantallaPrincipalActivity.this);
                    loginUsuario();
                } else {
                    Toast.makeText(PantallaPrincipalActivity.this,
                            "No deje campos en blanco", Toast.LENGTH_SHORT).show();
                }
            }
        });

        botonRegistrarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frameRegistro = findViewById(R.id.contenedor_registro);
                openFragment(email);
            }
        });

        botonVerContrasenha.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int position;
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN: {
                        position = contrasenha.getSelectionStart();
                        contrasenha.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        botonVerContrasenha.setImageResource(R.drawable.ic_visibility_24px);
                        contrasenha.setSelection(position);
                        break;
                    }
                    case MotionEvent.ACTION_UP:{
                        position = contrasenha.getSelectionStart();
                        contrasenha.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        botonVerContrasenha.setImageResource(R.drawable.ic_visibility_off_24px);
                        contrasenha.setSelection(position);
                        break;
                    }
                }
                return true;
            }
        });
    }

    private void openFragment(String text){
        RegistrarUsuarioFragment usuarioFragment = RegistrarUsuarioFragment.newInstance(text);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_rigth, R.anim.exit_to_right,
                R.anim.enter_from_rigth, R.anim.exit_to_right);
        transaction.addToBackStack(null);
        transaction.add(R.id.contenedor_registro, usuarioFragment).commit();
    }

    private void loginUsuario() {
        loadingBarProgress();

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    savePreferences(email, password, condition);
                    startMainActivity();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                loadingBar.dismiss();
                if(e instanceof FirebaseAuthInvalidUserException){
                    Toast.makeText(PantallaPrincipalActivity.this, "El correo " +
                            email + " no esta registrado\nRevise el correo o " +
                            "registre uno", Toast.LENGTH_LONG).show();
                } else if(e instanceof FirebaseAuthInvalidCredentialsException){
                    Toast.makeText(PantallaPrincipalActivity.this, "La contraseña " +
                            "ingresada no es correcta", Toast.LENGTH_LONG)
                            .show();
                } else if(ConnectivityIsNotWorking.getConnectivityStatus(PantallaPrincipalActivity.this)){
                    Toast.makeText(PantallaPrincipalActivity.this, "El dispositivo movil " +
                            "no tiene conexion a internet", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(PantallaPrincipalActivity.this,
                            "Ah ocurrido un error inesperado\n" +
                                    e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void loadingBarProgress(){
        loadingBar = new ProgressDialog(PantallaPrincipalActivity.this);
        loadingBar.setTitle("Iniciando Sesion");
        loadingBar.setMessage("Por favor, espere...");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();
    }

    private void loadPreferences(){
        SharedPreferences preferences = getSharedPreferences("user_data", Context.MODE_PRIVATE);
        email = preferences.getString("username","");
        password = preferences.getString("userkey","");
        isOpen = preferences.getBoolean("usercondition",false);

        correo.setText(email);
        contrasenha.setText(password);
        mantenerSesion.setChecked(isOpen);
    }

    private void savePreferences(String userEmail, String userPassword, Boolean userCondition){
        SharedPreferences preferences = getSharedPreferences("user_data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        if(userCondition){
            editor.putString("username", userEmail);
            editor.putString("userkey", userPassword);
            editor.putBoolean("usercondition", true);
            editor.commit();
        }
    }

    private void clearPreferences() {
        SharedPreferences preferences = getSharedPreferences("user_data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear().commit();
    }

    @Override
    public void onFragmentInteraction(String sendBackText) {
        onBackPressed();
    }

    @Override
    public void onFragmentInteraction(String userEmail, String userPassword) {
        loadingBarProgress();

        email = userEmail;
        password = userPassword;

        savePreferences(userEmail, userPassword, true);
        startMainActivity();
    }

    private void startMainActivity(){
        final Intent intent = new Intent(PantallaPrincipalActivity.this, MainActivity.class);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("usuarios").document(email).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                loadingBar.dismiss();
                if(documentSnapshot.exists()){
                    intent.putExtra("USER_EMAIL", documentSnapshot.getString("email"));
                    intent.putExtra("USER_FULL_NAME", (documentSnapshot.getString("nombres")
                            + " " + documentSnapshot.getString("apellidos")));
                    intent.putExtra("USER_WORK", documentSnapshot.getString("ocupacion"));
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(PantallaPrincipalActivity.this, "El documento del usuario"
                    + email +" no existe", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}