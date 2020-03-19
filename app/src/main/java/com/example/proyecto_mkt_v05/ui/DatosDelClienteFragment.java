package com.example.proyecto_mkt_v05.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.example.proyecto_mkt_v05.R;
import com.example.proyecto_mkt_v05.utilities.ConnectivityIsNotWorking;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DatosDelClienteFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DatosDelClienteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DatosDelClienteFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM0 = "param0";
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";

    // TODO: Rename and change types of parameters
    private String codigoDelCliente, nombreDelCliente, nombreDelMercado, direccionDelCliente;

    private OnFragmentInteractionListener mListener;

    public DatosDelClienteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DatosDelClienteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DatosDelClienteFragment newInstance(String param0, String param1, String param2, String param3) {
        DatosDelClienteFragment fragment = new DatosDelClienteFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM0, param0);
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3, param3);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            codigoDelCliente = getArguments().getString(ARG_PARAM0);
            nombreDelCliente = getArguments().getString(ARG_PARAM1);
            nombreDelMercado = getArguments().getString(ARG_PARAM2);
            direccionDelCliente = getArguments().getString(ARG_PARAM3);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_datos_del_cliente, container, false);

        final Button enviarCorreciones = vista.findViewById(R.id.id_boton_enviar_correcciones);
        enviarCorreciones.setVisibility(View.GONE);

        Button ingresarVentas = vista.findViewById(R.id.id_boton_ingresar_ventas);

        final EditText nombre = vista.findViewById(R.id.id_editText_nombre_del_cliente);
        nombre.setText(nombreDelCliente);
        nombre.setEnabled(false);
        final CheckBox corregirNombre = vista.findViewById(R.id.id_checkBox_corregir_nombre_del_cliente);
        corregirNombre.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    nombre.setEnabled(true);
                    nombre.requestFocus();
                    enviarCorreciones.setVisibility(View.VISIBLE);
                } else {
                    nombre.setEnabled(false);
                }
            }
        });

        final EditText mercado = vista.findViewById(R.id.id_editText_nombre_del_mercado);
        mercado.setText(nombreDelMercado);
        mercado.setEnabled(false);
        final CheckBox corregirMercado = vista.findViewById(R.id.id_checkBox_corregir_nombre_del_mercado);
        corregirMercado.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    mercado.setEnabled(true);
                    mercado.requestFocus();
                    enviarCorreciones.setVisibility(View.VISIBLE);
                } else {
                    mercado.setEnabled(false);
                }
            }
        });

        final EditText direccion = vista.findViewById(R.id.id_editText_direccion);
        direccion.setText(direccionDelCliente);
        direccion.setEnabled(false);
        final CheckBox corregirDireccion = vista.findViewById(R.id.id_checkBox_corregir_direccion);
        corregirDireccion.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    direccion.setEnabled(true);
                    direccion.requestFocus();
                    enviarCorreciones.setVisibility(View.VISIBLE);
                } else {
                    direccion.setEnabled(false);
                }
            }
        });

        enviarCorreciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isNameCorrect = false, isMarketCorrect = false, isAddresCorrect = false;

                if(corregirNombre.isChecked()){
                    if(nombre.getText().toString().compareToIgnoreCase(nombreDelCliente) == 0){
                        mostrarMismoNombre("del nombre del cliente");
                    } else {
                        isNameCorrect = true;
                    }
                } else {
                    isNameCorrect = true;
                }
                if(corregirMercado.isChecked()){
                    if(mercado.getText().toString().compareToIgnoreCase(nombreDelMercado) == 0){
                        mostrarMismoNombre("del nombre del mercado");
                        } else {
                        isMarketCorrect = true;
                    }
                } else {
                    isMarketCorrect = true;
                }
                if(corregirDireccion.isChecked()){
                    if(direccion.getText().toString().compareToIgnoreCase(direccionDelCliente) == 0){
                        mostrarMismoNombre("de la direccion del cliente");
                    } else {
                        isAddresCorrect = true;
                    }
                } else {
                    isAddresCorrect = true;
                }

                if(isNameCorrect && isMarketCorrect && isAddresCorrect){
                    if(corregirNombre.isChecked() && corregirMercado.isChecked() && corregirDireccion.isChecked()){
                        HashMap<String, Object> actualizarCliente = new HashMap<>();
                        actualizarCliente.put("nombre",nombre.getText().toString());
                        actualizarCliente.put("nombreDelMercado", mercado.getText().toString());
                        actualizarCliente.put("direccion", direccion.getText().toString());

                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        db.collection("clientes").document(codigoDelCliente)
                                .set(actualizarCliente).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getContext(), "Se actualizo correctamente\n" +
                                        "Gracias por actualizar los datos", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                                if(ConnectivityIsNotWorking.getConnectivityStatus(Objects.requireNonNull(getContext()))){
                                    Toast.makeText(getContext(), "El dispositivo movil " +
                                            "no tiene conexion a internet", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                    Log.d("ERROR", "onFailure: " + e.toString());
                                }
                            }
                        });
                    } else {
                        Toast.makeText(getContext(),"No hay nada que corregir", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        ingresarVentas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "ingresar ventas", Toast.LENGTH_SHORT).show();
            }
        });

        return vista;
    }

    private void mostrarMismoNombre(String text){
        Toast.makeText(getContext(), "No se hizo la corrección " + text + ", por favor " +
                        "verifique los datos o desactive la casilla",
                Toast.LENGTH_LONG).show();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
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
        void onFragmentInteraction(Uri uri);
    }
}
