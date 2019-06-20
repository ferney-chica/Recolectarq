package com.recolectarq.campo.recolectarq.Fragmentos.Intervenciones.RecoleccionesSuperficiales;

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
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.recolectarq.campo.recolectarq.Actividades.InicioUsuarioActivity;
import com.recolectarq.campo.recolectarq.AdaptadoresViewPager.AdapterPagerInformacionPozoSondeo;
import com.recolectarq.campo.recolectarq.AdaptadoresViewPager.AdapterPagerInformacionRecoleccionSuperficial;
import com.recolectarq.campo.recolectarq.Carpetas;
import com.recolectarq.campo.recolectarq.Fragmentos.Intervenciones.IntervencionesFragment;
import com.recolectarq.campo.recolectarq.Modelo.PozosSondeo;
import com.recolectarq.campo.recolectarq.Modelo.Proyectos;
import com.recolectarq.campo.recolectarq.Modelo.RecoleccionesSuperficiales;
import com.recolectarq.campo.recolectarq.Modelo.Tipos_Proyectos;
import com.recolectarq.campo.recolectarq.Modelo.Umtp;
import com.recolectarq.campo.recolectarq.Modelo.Usuarios;
import com.recolectarq.campo.recolectarq.R;

import java.util.ArrayList;

public class VerRecoleccionSuperficialFragment extends Fragment implements InicioUsuarioActivity.OnBackPressedListener {
    private TabLayout tabInformacionRecoleccionSuperficial;
    private ViewPager pagerInformacionRecoleccionSuperficial;
    private PagerAdapter adaptadorPager;
    private int idUsuario;

    private FragmentActivity contexto;

    private RequestQueue request;
    private JsonRequest jrq;
    private ArrayList<Tipos_Proyectos> listaTiposProyectos;
    private Spinner spinnerTipoProyecto;
    private JsonObjectRequest jsonObjectRequest;


    private Usuarios usuarioLogueado;
    private Proyectos proyectoSeleccionado;
    private Umtp umtpSeleccionado;
    private RecoleccionesSuperficiales recoleccionSeleccionado;
    private Bundle usuarioEnviado;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments()!=null)
        {
            usuarioLogueado=(Usuarios) getArguments().getSerializable("usuario");
            proyectoSeleccionado=(Proyectos)getArguments().getSerializable("proyecto");
            umtpSeleccionado=(Umtp) getArguments().getSerializable("umtp");
            recoleccionSeleccionado=(RecoleccionesSuperficiales) getArguments().getSerializable("recoleccion");
            usuarioEnviado= getArguments();
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
            System.out.println("POZO IDDDDDDDDD:" + recoleccionSeleccionado.getrecoleccion_superficial_id());
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
        final View v= inflater.inflate(R.layout.fragment_ver_recoleccion_superficial,container, false);
        tabInformacionRecoleccionSuperficial= v.findViewById(R.id.tabLayoutInformacionRecoleccion);
        tabInformacionRecoleccionSuperficial.addTab(tabInformacionRecoleccionSuperficial.newTab().setText("Informacion Recolección"));
        tabInformacionRecoleccionSuperficial.addTab(tabInformacionRecoleccionSuperficial.newTab().setText("Materiales Recolección"));
        tabInformacionRecoleccionSuperficial.addTab(tabInformacionRecoleccionSuperficial.newTab().setText("Estructuras Arqueológicas"));
        tabInformacionRecoleccionSuperficial.addTab(tabInformacionRecoleccionSuperficial.newTab().setText("Muestras Recolección"));
        //tabInformacionPozoSondeo.addTab(tabInformacionPozoSondeo.newTab().setText("Lectura Estratigráfica Pozo"));
        //tabProyecto.addTab(tabProyecto.newTab().setText("Memorias Técnicas"));
        tabInformacionRecoleccionSuperficial.setTabGravity(TabLayout.GRAVITY_FILL);



        pagerInformacionRecoleccionSuperficial=v.findViewById(R.id.viewPagerInformacionRecoleccion);
        //pagerProyectos.setOffscreenPageLimit(3);
        //System.out.println("PROYECTO SELECCIONADO AL VOLVER ES---------: "+ proyectoSeleccionado.getProyecto_id());
        adaptadorPager= new AdapterPagerInformacionRecoleccionSuperficial(getFragmentManager(),tabInformacionRecoleccionSuperficial.getTabCount(), proyectoSeleccionado, usuarioLogueado, umtpSeleccionado, recoleccionSeleccionado);
        pagerInformacionRecoleccionSuperficial.setAdapter(adaptadorPager);
        pagerInformacionRecoleccionSuperficial.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabInformacionRecoleccionSuperficial));
        tabInformacionRecoleccionSuperficial.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position=tab.getPosition();
                pagerInformacionRecoleccionSuperficial.setCurrentItem(position);
                //Toast.makeText(getContext(),"ENTRO EN TAB",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return v;
    }


    @Override
    public void doBack() {
        Bundle argumentosEnviados;
        argumentosEnviados= new Bundle();
        argumentosEnviados.putSerializable("proyecto",proyectoSeleccionado);
        argumentosEnviados.putSerializable("usuario",usuarioLogueado);
        argumentosEnviados.putSerializable("umtp",umtpSeleccionado);
        IntervencionesFragment intervenciones= new IntervencionesFragment();
        intervenciones.setArguments(argumentosEnviados);
        FragmentManager manager= getFragmentManager();
        manager.beginTransaction().replace(R.id.contenidos, intervenciones).commit();

    }
}
