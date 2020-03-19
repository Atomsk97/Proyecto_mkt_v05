package com.example.proyecto_mkt_v05.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.proyecto_mkt_v05.R;
import com.example.proyecto_mkt_v05.reg_and_log.PantallaPrincipalActivity;

import java.util.Objects;

public class LogoutFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_logout, container, false);
        Intent intent = new Intent(getActivity(), PantallaPrincipalActivity.class);
        intent.putExtra("SESION_FINISHED", true);
        startActivity(intent);
        Objects.requireNonNull(getActivity()).finish();
        return root;
    }
}