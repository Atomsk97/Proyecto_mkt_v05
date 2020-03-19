package com.example.proyecto_mkt_v05.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.proyecto_mkt_v05.R;

public class ContenedorImpulsoFragment extends Fragment {

    private static final String USER_NAME = "USER_NAME";
    private static final String USER_CODE = "USER_CODE";
    private String userName, userCode;
    private OnFragmentInteractionListener mListener;

    public ContenedorImpulsoFragment(){
    }

    public static ContenedorImpulsoFragment newInstance(String parm1, String parm2){
        ContenedorImpulsoFragment fragment = new ContenedorImpulsoFragment();
        Bundle args = new Bundle();
        args.putString(USER_NAME, parm1);
        args.putString(USER_CODE, parm2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            userName = getArguments().getString(USER_NAME);
            userCode = getArguments().getString(USER_CODE);
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_contenedor_impulso, container, false);
        TextView txtUserCode = root.findViewById(R.id.id_textView_codigo_del_cliente);
        txtUserCode.setText(userCode);

        TextView txtUserName = root.findViewById(R.id.id_textView_nombre_del_cliente);
        txtUserName.setText(userName);

        Button agregarUnaVenta = root.findViewById(R.id.id_boton_agregar_una_venta);

        Button finalizarVentas = root.findViewById(R.id.id_boton_finalizar_ventas);

        return root;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof OnFragmentInteractionListener){
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw  new RuntimeException(context.toString()
            + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener{
        void onFragmentInteraction();
    }
}