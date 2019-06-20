package com.recolectarq.campo.recolectarq.Fragmentos.Proyectos;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.recolectarq.campo.recolectarq.CuadrosDialogos.CuadroDialogoActualizarProyecto;
import com.recolectarq.campo.recolectarq.CuadrosDialogos.CuadroDiaogoPrueba;
import com.recolectarq.campo.recolectarq.Modelo.Proyectos;
import com.recolectarq.campo.recolectarq.Modelo.Usuarios;
import com.recolectarq.campo.recolectarq.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EditarProyectoFragment extends Fragment implements  CuadroDialogoActualizarProyecto.FinalizarCuadroDialogo{



    private Usuarios usuarioLogueado;
    private Proyectos proyectoSeleccionado;
    private Bundle usuarioEnviado;
    private TextView nombreProyecto;
    private TextView idProyecto;
    private TextView codigoProyecto;
    private TextView tipoProyecto;
    private TextView faseProyecto;
    private TextView fechaInicio;
    private TextView fechaFin;
    private TextView ubicacionProyecto;
    private TextView referenciasProyecto;
    private TextView avalProyecto;
    private FloatingActionButton fbEditarProyecto;
    private Context contexto;
    private int idPerfil;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments()!=null)
        {
            Toast.makeText(getContext(),"entro a EDITAR",Toast.LENGTH_LONG);
            Toast.makeText(getContext(),"entro",Toast.LENGTH_LONG);
            usuarioLogueado=(Usuarios) getArguments().getSerializable("usuario");
            proyectoSeleccionado=(Proyectos)getArguments().getSerializable("proyecto"); 
            usuarioEnviado= getArguments();
           int idUsuario = Integer.parseInt(usuarioLogueado.getUsuario_id());
            idPerfil=proyectoSeleccionado.getPerfil_id();
            System.out.println("El usuario Logueado es desde editar es proyectos" + usuarioLogueado.getNombre() + "con cedula "+ idUsuario);
            System.out.println("Entro a editar PROYECTO"+usuarioLogueado);
        System.out.println("PROYECTO" + proyectoSeleccionado.getNombre());
            System.out.println("PROYECTO   CODIGO" + proyectoSeleccionado.getCodigo_identificacion());
        }else{
            usuarioLogueado=new Usuarios("null","null", "null","null");
            Toast.makeText(getContext(),"NO entroOOOOOOOO",Toast.LENGTH_LONG);
            Toast.makeText(getContext(),"NO entroOOOOOOOO",Toast.LENGTH_LONG);

            usuarioLogueado.setNombre("No hay usuario logueado");

        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       // return super.onCreateView(inflater, container, savedInstanceState);
        final View v= inflater.inflate(R.layout.fragment_editar_proyecto,container, false);
        nombreProyecto=v.findViewById(R.id.textViewEditarUmtpLargo);
        idProyecto=v.findViewById(R.id.textViewIdProyecto);
        codigoProyecto=v.findViewById(R.id.textViewCodigo);
        tipoProyecto=v.findViewById(R.id.textViewEditarUmtpCodigoRotulo);
        faseProyecto=v.findViewById(R.id.textViewFase);
        fechaInicio=v.findViewById(R.id.textViewFechaInicio);
        fechaFin=v.findViewById(R.id.textViewFechaFin);
        ubicacionProyecto=v.findViewById(R.id.textViewEditarUmtpTipoRelieve);
        referenciasProyecto=v.findViewById(R.id.textViewEditarUmtpAltura);
        avalProyecto=v.findViewById(R.id.textViewEditarUmtpUTMx);
        fbEditarProyecto=v.findViewById(R.id.fabBotonEditarProyecto);

        Date f_i,f_f= new Date();
        f_i=proyectoSeleccionado.getFecha_inicio();
        f_f=proyectoSeleccionado.getFecha_fin();

        System.out.println("LA FECHA ES :" +f_i.compareTo(f_f));

        if(f_i.compareTo(f_f)>0)
        {
            f_f=f_i;
        }
        //System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(f_i));

        //System.out.println("PROYECTO   en vista" + proyectoSeleccionado.getNombre());
        nombreProyecto.setText("Nombre proyecto: "+proyectoSeleccionado.getNombre());
        idProyecto.setText(String.valueOf("Proyecto Id: "+proyectoSeleccionado.getProyecto_id()));
        codigoProyecto.setText("Código Identificación: "+proyectoSeleccionado.getCodigo_identificacion());
        tipoProyecto.setText("Tipo proyecto: "+proyectoSeleccionado.getNombre_tipo_proyecto());
        faseProyecto.setText("Fase proyecto: "+proyectoSeleccionado.getNombre_fases_proyectos());
        fechaInicio.setText("Fecha inicio: "+ new SimpleDateFormat("yyyy-MM-dd").format(f_i));
        fechaFin.setText("Fecha final: "+new SimpleDateFormat("yyyy-MM-dd").format(f_f));
        ubicacionProyecto.setText("Ubicación: "+proyectoSeleccionado.getUbicacion());
        referenciasProyecto.setText("Referencias del proyecto: "+proyectoSeleccionado.getReferencias_administrativas());
        avalProyecto.setText("Aval del proyecto: "+proyectoSeleccionado.getAval_cientifico());

        configurarSegunPerfil();

        fbEditarProyecto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager= getFragmentManager();
                System.out.println("CLIC EN EDITAR PROYECTO");
                contexto=getContext();
                new CuadroDialogoActualizarProyecto(contexto, proyectoSeleccionado,EditarProyectoFragment.this);
                //new CuadroDiaogoPrueba(contexto);
            }
        });
        return v;
    }

    private void configurarSegunPerfil()
    {
        switch (idPerfil)
        {
            case 2:
                fbEditarProyecto.setEnabled(false);
                break;
            case 3:
                fbEditarProyecto.setEnabled(false);
                break;

        }
    }


    @Override
    public void ProyectoActualizado(Proyectos proyecto) {
        nombreProyecto.setText(proyecto.getNombre());
        idProyecto.setText(String.valueOf(proyecto.getProyecto_id()));
        codigoProyecto.setText(proyecto.getCodigo_identificacion());
        tipoProyecto.setText(String.valueOf(proyecto.getTipo_proyecto_id()));
        faseProyecto.setText(String.valueOf(proyecto.getFase_proyecto_id()));
        fechaInicio.setText(proyecto.getFecha_inicio().toString());
        fechaFin.setText(proyecto.getFecha_fin().toString());
        ubicacionProyecto.setText(proyecto.getUbicacion());
        referenciasProyecto.setText(proyecto.getReferencias_administrativas());
        avalProyecto.setText(proyecto.getAval_cientifico());
    }
}
