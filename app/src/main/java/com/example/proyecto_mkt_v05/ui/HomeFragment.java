package com.example.proyecto_mkt_v05.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.proyecto_mkt_v05.entities.Coordenadas;
import com.example.proyecto_mkt_v05.entities.Fecha;
import com.example.proyecto_mkt_v05.utilities.MapsActivity;
import com.example.proyecto_mkt_v05.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;
import io.grpc.okhttp.internal.Util;

public class HomeFragment extends Fragment implements  DatosDelClienteFragment.OnFragmentInteractionListener{

    private boolean isBack = false;
    private EditText codigoDelCliente;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private ProgressDialog loadingDialog;
    private ProgressBar progressBar;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_home, container, false);

        final TextView textView = root.findViewById(R.id.text_home);

        progressBar = root.findViewById(R.id.id_progress_bar_del_buscador);
        codigoDelCliente = root.findViewById(R.id.id_buscador_por_codigo);
        codigoDelCliente.setVisibility(View.INVISIBLE);
        final Button buscarCliente = root.findViewById(R.id.id_boton_buscar_cliente);
        buscarCliente.setVisibility(View.INVISIBLE);

        Button marcarAsistencia = root.findViewById(R.id.id_boton_marcar_asistencia);

        marcarAsistencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isBack){
                    setLoadingDialog();
                    Intent intent = new Intent(getActivity(), MapsActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getContext(), "Ya marco asistencia", Toast.LENGTH_SHORT).show();
                }
            }
        });

        MapsActivity.passVal(new MapsActivity.OnMapsActivityInteractionListener() {
            @Override
            public void onMapsActivityInteraction(Coordenadas coordenadas, Fecha fecha, boolean isBack) {
                TextView tiempoDeAsistencia = root.findViewById(R.id.id_tiempo_de_asistencia);
                HomeFragment.this.isBack = isBack;
                String date = "Hora de ingreso: " + fecha.getHour() + ":" + fecha.getMinute()
                        + "\n" + fecha.getDay() + "/" + fecha.getMonth() + "/" + fecha.getYear();
                tiempoDeAsistencia.setText(date);
                String coordinate = "Latitud: " + coordenadas.getLatitud() + "\nLongitud: " +
                        coordenadas.getLongitud();
                textView.setText(coordinate);
                codigoDelCliente.setVisibility(View.VISIBLE);
                buscarCliente.setVisibility(View.VISIBLE);
                if(loadingDialog != null){
                    loadingDialog.dismiss();
                }
            }
        });

        buscarCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Util.equal(codigoDelCliente.getText().toString(), "")){
                    Toast.makeText(getContext(), "El codigo del cliente no puede estar en blanco",
                            Toast.LENGTH_SHORT).show();
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    final String codigo = codigoDelCliente.getText().toString();
                    db.collection("clientes").document(codigo).get()
                            .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    if(documentSnapshot.exists()){
                                        codigoDelCliente.setFocusable(false);
                                        openFragment(codigo,
                                                documentSnapshot.getString("nombre"),
                                                documentSnapshot.getString("nombreDelMercado"),
                                                documentSnapshot.getString("direccion"));
                                        progressBar.setVisibility(View.GONE);
                                    } else {
                                        progressBar.setVisibility(View.GONE);
                                        Toast.makeText(getContext(), "El codigo " + codigo +
                                                " no esta en la base de datos", Toast.LENGTH_SHORT)
                                                .show();
                                    }
                                }
                            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            Log.d("ERROR", "onFailure: " + e.toString());
                        }
                    });
                }
            }
        });

        return root;
    }

    private void setLoadingDialog(){
        loadingDialog = new ProgressDialog(getContext());
        loadingDialog.setTitle("Asistencia");
        loadingDialog.setMessage("Obteniendo coordenadas, por favor espere...");
        loadingDialog.setCanceledOnTouchOutside(false);
        loadingDialog.show();
    }

    private void openFragment(String param0, String param1, String param2, String param3){
        DatosDelClienteFragment datosDelClienteFragment = DatosDelClienteFragment
                .newInstance(param0, param1, param2, param3);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = Objects.requireNonNull(fragmentManager).beginTransaction();
        transaction.addToBackStack(null);
        transaction.add(R.id.contenedor_datos_del_cliente, datosDelClienteFragment).commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}