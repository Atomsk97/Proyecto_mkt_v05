package com.example.proyecto_mkt_v05.reg_and_log;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.proyecto_mkt_v05.utilities.ConnectivityIsNotWorking;
import com.example.proyecto_mkt_v05.R;
import com.example.proyecto_mkt_v05.entities.Usuarios;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class RegistrarUsuarioFragment extends Fragment{

    private static final String TEXT = "text";

    // TODO: Rename and change types of parameters
    private String mText;

    private EditText nombres, apellidos, correo, contrasenha;
    private ProgressDialog loadingBar;
    private AutoCompleteTextView cargo;

    private FirebaseAuth auth;
    private FirebaseFirestore db;

    private String name, lastname, email, password, ocupacion;

    private OnFragmentInteractionListener mListener;

    private boolean isClose = true;

    public RegistrarUsuarioFragment() {
        // Required empty public constructor
    }

    public static RegistrarUsuarioFragment newInstance(String text) {
        RegistrarUsuarioFragment fragment = new RegistrarUsuarioFragment();
        Bundle args = new Bundle();
        args.putString(TEXT,text);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mText = getArguments().getString(TEXT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_registrar_usuario, container, false);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        nombres = vista.findViewById(R.id.id_nombres);
        apellidos = vista.findViewById(R.id.id_apellidos);
        correo = vista.findViewById(R.id.id_correo_nuevo);
        contrasenha = vista.findViewById(R.id.id_contrasenha_nueva);

        nombres.requestFocus();

        ImageButton botonDesplegarLista = vista.findViewById(R.id.id_boton_ver_opciones_de_cargos);
        final String[] ocupaciones = getResources().getStringArray(R.array.ocupaciones);
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(Objects.requireNonNull(getActivity()),
                android.R.layout.simple_dropdown_item_1line, ocupaciones);
        cargo = vista.findViewById(R.id.id_cargo);
        cargo.setAdapter(adapter);
        cargo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isClose){
                    cargo.showDropDown();
                    isClose = false;
                } else {
                    cargo.dismissDropDown();
                    isClose = true;
                }
            }
        });

        botonDesplegarLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isClose){
                    cargo.showDropDown();
                    isClose = false;
                } else {
                    cargo.dismissDropDown();
                    isClose = true;
                }
            }
        });

        Button botonCrearCuenta = vista.findViewById(R.id.id_boton_crear_cuenta);
        Button botonCancelar = vista.findViewById(R.id.id_boton_cancelar);
        final ImageButton botonVerContrasenha = vista.findViewById(R.id.id_boton_ver_nueva_contrasenha);

        botonCrearCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name = nombres.getText().toString();
                lastname = apellidos.getText().toString();
                email = correo.getText().toString();
                password = contrasenha.getText().toString();
                ocupacion = cargo.getText().toString();

                if(!name.isEmpty() && !lastname.isEmpty() && !email.isEmpty() &&
                        !password.isEmpty() && !ocupacion.isEmpty()){
                    if(password.length() >= 6){
                        for(int i = 0; i < ocupaciones.length; i++){
                            if ((ocupaciones[i].compareToIgnoreCase(ocupacion) == 0)){
                                crearCuenta();
                                break;
                            } else if (i == (ocupaciones.length - 1)){
                                Toast.makeText(getContext(),"La ocupacion escrita no existe",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else {
                        Toast.makeText(getContext(), "La contraseña debe tener 6 caracteres como mínimo",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(),"No deje campos en blanco", Toast.LENGTH_SHORT).show();
                }
            }
        });

        botonCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String deRegreso = correo.getText().toString();
                sendBack(deRegreso);
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

        return vista;
    }

    private void crearCuenta() {

        loadingBar = new ProgressDialog(getActivity());
        loadingBar.setTitle("Creando Usuario");
        loadingBar.setMessage("Por favor, espere...");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    db.collection("usuarios").document(email)
                            .set(new Usuarios (name, lastname, email, password, ocupacion))
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            loadingBar.dismiss();
                            Toast.makeText(getContext(), "Se ha registrado correctamente",
                                    Toast.LENGTH_SHORT).show();
                            sendBack(email, password);
                        }
                    });
                } else {
                    loadingBar.dismiss();
                    if(task.getException() instanceof  FirebaseAuthUserCollisionException){
                        Toast.makeText(getContext(), "El correo " + email + " ya tiene propietario",
                                Toast.LENGTH_SHORT).show();
                    } else if(ConnectivityIsNotWorking.getConnectivityStatus(Objects.requireNonNull(getContext()))){
                        Toast.makeText(getContext(), "El dispositivo movil " +
                                "no tiene conexion a internet", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "No se pudo registrar\n" +
                                        "Revise los datos antes de volver a enviarlos",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public void sendBack(String sendBackText) {
        if (mListener != null) {
            mListener.onFragmentInteraction(sendBackText);
        }
    }

    public void sendBack(String userEmail, String userPassword){
        if(mListener != null){
            mListener.onFragmentInteraction(userEmail, userPassword);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String sendBackText);
        void onFragmentInteraction(String userEmail, String userPassword);
    }
}
