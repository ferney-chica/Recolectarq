package com.recolectarq.campo.recolectarq.Fragmentos;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.recolectarq.campo.recolectarq.Actividades.InicioUsuarioActivity;
import com.recolectarq.campo.recolectarq.Actividades.LogueoActivity;
import com.recolectarq.campo.recolectarq.R;
import com.recolectarq.campo.recolectarq.Modelo.Usuarios;

public class DashboardFragment extends Fragment implements InicioUsuarioActivity.OnBackPressedListener {
    private Usuarios usuarioLogueado= null;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((InicioUsuarioActivity) getActivity()).setOnBackPressedListener(this);
        if (getArguments()!=null)
        {
            Toast.makeText(getContext(),"entro",Toast.LENGTH_LONG);
            Toast.makeText(getContext(),"entro",Toast.LENGTH_LONG);
            usuarioLogueado=(Usuarios) getArguments().getSerializable("usuario");

        }else{

            Toast.makeText(getContext(),"NO entroOOOOOOOO",Toast.LENGTH_LONG);
            Toast.makeText(getContext(),"NO entroOOOOOOOO",Toast.LENGTH_LONG);
            Toast.makeText(getContext(),"NO entroOOOOOOOO",Toast.LENGTH_LONG);
        }
    }

    TextView a;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       // return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_dashboard,container,false);

        a=  view.findViewById(R.id.textViewUsuario);

        a.setText(usuarioLogueado.getNombre());
        //return inflater.inflate(R.layout.fragment_dashboard,container, false);
        return view;
    }

    @Override
    public void doBack() {
        getActivity().finish();
        Intent intencion = new Intent(getContext(), LogueoActivity.class);
        startActivity(intencion);
    }
}
