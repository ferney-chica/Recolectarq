package com.recolectarq.campo.recolectarq.Fragmentos.Intervenciones;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.recolectarq.campo.recolectarq.Actividades.InicioUsuarioActivity;
import com.recolectarq.campo.recolectarq.AdaptadoresViewPager.AdapterPagerReporteElementosDiagnosticos;
import com.recolectarq.campo.recolectarq.Carpetas;
import com.recolectarq.campo.recolectarq.Fragmentos.Proyectos.VerProyectoFragment;
import com.recolectarq.campo.recolectarq.Fragmentos.Umtp.SitioPotencial.SitioPotencialFragment;
import com.recolectarq.campo.recolectarq.Fragmentos.Umtp.Yacimiento.CondicionesEmplazamiento1Fragment;
import com.recolectarq.campo.recolectarq.Fragmentos.Umtp.Yacimiento.CondicionesEmplazamiento2Fragment;
import com.recolectarq.campo.recolectarq.Fragmentos.Umtp.Yacimiento.MedidasFragment;
import com.recolectarq.campo.recolectarq.Fragmentos.Umtp.Yacimiento.SituacionPatrimonialFragment;
import com.recolectarq.campo.recolectarq.Fragmentos.Umtp.Yacimiento.ValoracionImpactoFragment;
import com.recolectarq.campo.recolectarq.Modelo.Proyectos;
import com.recolectarq.campo.recolectarq.Modelo.Tipos_Proyectos;
import com.recolectarq.campo.recolectarq.Modelo.Umtp;
import com.recolectarq.campo.recolectarq.Modelo.Usuarios;
import com.recolectarq.campo.recolectarq.R;

import java.util.ArrayList;

public class ReporteElementosDiagnosticosFragment extends Fragment implements InicioUsuarioActivity.OnBackPressedListener  {
    private TabLayout tabLayoutElementosDiagnosticos;
    private ViewPager viewPagerElementosDiagnosticos;
    private PagerAdapter adaptadorPager;
    private ImageButton imageButtonCrearYacimiento;
    private ImageButton imageButtonCrearSitioPotencial;
    private TextView textViewReporteElementosMensaje;

    private Bundle argumentosEnviados;
    private int idUsuario;
    private int idPerfil;

    private FragmentActivity contexto;

    private RequestQueue request;
    private JsonRequest jrq;
    private ArrayList<Tipos_Proyectos> listaTiposProyectos;
    private Spinner spinnerTipoProyecto;
    private JsonObjectRequest jsonObjectRequest;


    private Usuarios usuarioLogueado;
    private Proyectos proyectoSeleccionado;
    private Umtp umtpSeleccionada;
    private Bundle usuarioEnviado;
    private int idYacimientoSeccion;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments()!=null)
        {
            usuarioLogueado=(Usuarios) getArguments().getSerializable("usuario");
            proyectoSeleccionado=(Proyectos)getArguments().getSerializable("proyecto");
            umtpSeleccionada=(Umtp) getArguments().getSerializable("umtp");
            usuarioEnviado= getArguments();
            idPerfil=proyectoSeleccionado.getPerfil_id();
            idYacimientoSeccion=umtpSeleccionada.getYacimiento_seccion();
            Carpetas carpeta=new Carpetas();
            System.out.println("PROYECTO SELECCIONADO AL VOLVER ES---------: "+ proyectoSeleccionado.getProyecto_id());
            System.out.println( carpeta.crearCarpeta("/Recolectarq/Proyectos/"+proyectoSeleccionado.getCodigo_identificacion()));
            carpeta.crearCarpeta("/Recolectarq/Proyectos/"+proyectoSeleccionado.getCodigo_identificacion()+"/UMTP");
            carpeta.crearCarpeta("/Recolectarq/Proyectos/"+proyectoSeleccionado.getCodigo_identificacion()+"/MemoriasTecnicas");
            int idUsuario = Integer.parseInt(usuarioLogueado.getUsuario_id());

            System.out.println("El usuario Logueado es desde Ver es proyectos" + usuarioLogueado.getNombre() + "con cedula "+ idUsuario);
            System.out.println("PROYECTO");
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
        ((InicioUsuarioActivity) getActivity()).setOnBackPressedListener(this);

        final View v= inflater.inflate(R.layout.fragment_reporte_elementos_diagnosticos,container, false);
        tabLayoutElementosDiagnosticos= v.findViewById(R.id.tabLayoutElementosDiagnosticos);
        tabLayoutElementosDiagnosticos.addTab(tabLayoutElementosDiagnosticos.newTab().setText("Pozos Sondeo"));
        tabLayoutElementosDiagnosticos.addTab(tabLayoutElementosDiagnosticos.newTab().setText("Recolecciones Superficiales"));
        tabLayoutElementosDiagnosticos.addTab(tabLayoutElementosDiagnosticos.newTab().setText("Perfiles Expuestos"));
        //tabProyecto.addTab(tabProyecto.newTab().setText("Memorias Técnicas"));
        tabLayoutElementosDiagnosticos.setTabGravity(TabLayout.GRAVITY_FILL);



        viewPagerElementosDiagnosticos=v.findViewById(R.id.viewPagerElementosDiagnosticos);
        //pagerProyectos.setOffscreenPageLimit(3);
        //System.out.println("PROYECTO SELECCIONADO AL VOLVER ES---------: "+ proyectoSeleccionado.getProyecto_id());
        adaptadorPager= new AdapterPagerReporteElementosDiagnosticos(getFragmentManager(),tabLayoutElementosDiagnosticos.getTabCount(), proyectoSeleccionado, usuarioLogueado, umtpSeleccionada);
        viewPagerElementosDiagnosticos.setAdapter(adaptadorPager);
        viewPagerElementosDiagnosticos.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayoutElementosDiagnosticos));
        tabLayoutElementosDiagnosticos.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position=tab.getPosition();
                viewPagerElementosDiagnosticos.setCurrentItem(position);
                Toast.makeText(getContext(),"ENTRO EN TAB",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        imageButtonCrearYacimiento=v.findViewById(R.id.imageViewCrearYacimiento);
        imageButtonCrearSitioPotencial=v.findViewById(R.id.imageViewCrearSitioPotencial);
        textViewReporteElementosMensaje=v.findViewById(R.id.textViewReporteElementosMensaje);
        configurarSegunPerfil();
        configurarBotones();

        imageButtonCrearYacimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("CLIC EN CREAR YACIMEINTO"+" Yacimiento seccion:"+umtpSeleccionada.getYacimiento_seccion());
                argumentosEnviados= new Bundle();
                argumentosEnviados.putSerializable("proyecto",proyectoSeleccionado);
                argumentosEnviados.putSerializable("usuario",usuarioLogueado);
                argumentosEnviados.putSerializable("umtp",umtpSeleccionada);
                FragmentManager manager= getFragmentManager();

                switch (idYacimientoSeccion)
                {
                    case 0:
                        CondicionesEmplazamiento1Fragment condicion1= new CondicionesEmplazamiento1Fragment();
                        condicion1.setArguments(argumentosEnviados);
                        //FragmentManager manager= getFragmentManager();
                        manager.beginTransaction().replace(R.id.contenidos, condicion1).commit();
                        break;
                    case 1:
                        CondicionesEmplazamiento2Fragment condicion2= new CondicionesEmplazamiento2Fragment();
                        condicion2.setArguments(argumentosEnviados);
                        //FragmentManager manager= getFragmentManager();
                        manager.beginTransaction().replace(R.id.contenidos, condicion2).commit();
                        break;
                    case 2:
                        SituacionPatrimonialFragment situacionPatrimonial= new SituacionPatrimonialFragment();
                        situacionPatrimonial.setArguments(argumentosEnviados);
                        //FragmentManager manager= getFragmentManager();
                        manager.beginTransaction().replace(R.id.contenidos, situacionPatrimonial).commit();
                        break;
                    case 3:
                        ValoracionImpactoFragment valoracionImpacto= new ValoracionImpactoFragment();
                        valoracionImpacto.setArguments(argumentosEnviados);
                        //FragmentManager manager= getFragmentManager();
                        manager.beginTransaction().replace(R.id.contenidos, valoracionImpacto).commit();
                        break;
                    case 4:
                        MedidasFragment medidas= new MedidasFragment();
                        medidas.setArguments(argumentosEnviados);
                        //FragmentManager manager= getFragmentManager();
                        manager.beginTransaction().replace(R.id.contenidos, medidas).commit();
                        break;
                }



            }
        });

        imageButtonCrearSitioPotencial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("CLIC EN CREAR SITIO POTENCIAL");
                argumentosEnviados= new Bundle();
                argumentosEnviados.putSerializable("proyecto",proyectoSeleccionado);
                argumentosEnviados.putSerializable("usuario",usuarioLogueado);
                argumentosEnviados.putSerializable("umtp",umtpSeleccionada);
                SitioPotencialFragment sitioPotencial= new SitioPotencialFragment();
                sitioPotencial.setArguments(argumentosEnviados);
                FragmentManager manager= getFragmentManager();
                manager.beginTransaction().replace(R.id.contenidos, sitioPotencial).commit();
            }
        });

        return v;
    }

    private void configurarSegunPerfil()
    {
        switch (idPerfil)
        {
            case 3:
                imageButtonCrearSitioPotencial.setEnabled(false);
                imageButtonCrearYacimiento.setEnabled(false);
                break;
        }
    }

    private void configurarBotones() {

        System.out.println("Yacimiento es: "+umtpSeleccionada.getYacimiento());
        if(Integer.parseInt(umtpSeleccionada.getYacimiento().toString())==1 && 0<umtpSeleccionada.getYacimiento_seccion() && umtpSeleccionada.getYacimiento_seccion()<=5  )
        {
            System.out.println("Yacimiento es: "+umtpSeleccionada.getYacimiento());
            imageButtonCrearSitioPotencial.setVisibility(View.INVISIBLE);

            if (umtpSeleccionada.getYacimiento_seccion()==5)
            {
                imageButtonCrearYacimiento.setVisibility(View.INVISIBLE);
                textViewReporteElementosMensaje.setVisibility(View.VISIBLE);
                textViewReporteElementosMensaje.setText("La UMTP"+umtpSeleccionada.getUmtp_id()+" fue declarada yacimiento");
            }


        }else
        {
            System.out.println("Yacimiento no entro");
            if(Integer.parseInt(umtpSeleccionada.getSitio_potencial().toString())==1)
            {
                imageButtonCrearYacimiento.setVisibility(View.INVISIBLE);
                imageButtonCrearSitioPotencial.setVisibility(View.INVISIBLE);
                textViewReporteElementosMensaje.setVisibility(View.VISIBLE);
                textViewReporteElementosMensaje.setText("La UMTP"+umtpSeleccionada.getUmtp_id()+" fue declarada Sitio Potencial");
            }
        }
    }


    @Override
    public void doBack() {
        argumentosEnviados= new Bundle();
        argumentosEnviados.putSerializable("proyecto",proyectoSeleccionado);
        argumentosEnviados.putSerializable("usuario",usuarioLogueado);
        VerProyectoFragment verProyecto= new VerProyectoFragment();
        verProyecto.setArguments(argumentosEnviados);
        FragmentManager manager= getFragmentManager();
        manager.beginTransaction().replace(R.id.contenidos, verProyecto).commit();
    }
}
