package com.recolectarq.campo.recolectarq.Fragmentos.Intervenciones.PozosSondeo;

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
import com.recolectarq.campo.recolectarq.AdaptadoresViewPager.AdapterPagerIntervenciones;
import com.recolectarq.campo.recolectarq.Carpetas;
import com.recolectarq.campo.recolectarq.Fragmentos.Intervenciones.IntervencionesFragment;
import com.recolectarq.campo.recolectarq.Modelo.PozosSondeo;
import com.recolectarq.campo.recolectarq.Modelo.Proyectos;
import com.recolectarq.campo.recolectarq.Modelo.Tipos_Proyectos;
import com.recolectarq.campo.recolectarq.Modelo.Umtp;
import com.recolectarq.campo.recolectarq.Modelo.Usuarios;
import com.recolectarq.campo.recolectarq.R;

import java.util.ArrayList;

public class VerPozoSondeoFragment extends Fragment implements InicioUsuarioActivity.OnBackPressedListener {
    private TabLayout tabInformacionPozoSondeo;
    private ViewPager pagerInformacionPozoSondeo;
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
    private PozosSondeo pozoSeleccionado;
    private Bundle usuarioEnviado;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments()!=null)
        {
            usuarioLogueado=(Usuarios) getArguments().getSerializable("usuario");
            proyectoSeleccionado=(Proyectos)getArguments().getSerializable("proyecto");
            umtpSeleccionado=(Umtp) getArguments().getSerializable("umtp");
            pozoSeleccionado=(PozosSondeo) getArguments().getSerializable("pozo");
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
            //System.out.println("POZO IDDDDDDDDD:" + pozoSeleccionado.getPozo_id());
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
        final View v= inflater.inflate(R.layout.fragment_ver_pozos_sondeo,container, false);
        tabInformacionPozoSondeo= v.findViewById(R.id.tabLayoutInformacionPozo);
        tabInformacionPozoSondeo.addTab(tabInformacionPozoSondeo.newTab().setText("Informacion Pozos"));
        tabInformacionPozoSondeo.addTab(tabInformacionPozoSondeo.newTab().setText("Niveles Pozo"));
        tabInformacionPozoSondeo.addTab(tabInformacionPozoSondeo.newTab().setText("Lectura Estratigráfica Pozo"));
        tabInformacionPozoSondeo.addTab(tabInformacionPozoSondeo.newTab().setText("Estructura Arqueológica Pozo"));
        tabInformacionPozoSondeo.addTab(tabInformacionPozoSondeo.newTab().setText("Muestras Pozo"));

        //tabProyecto.addTab(tabProyecto.newTab().setText("Memorias Técnicas"));
        tabInformacionPozoSondeo.setTabGravity(TabLayout.GRAVITY_FILL);



        pagerInformacionPozoSondeo=v.findViewById(R.id.viewPagerInformacionPozo);
        //pagerProyectos.setOffscreenPageLimit(3);
        //System.out.println("PROYECTO SELECCIONADO AL VOLVER ES---------: "+ proyectoSeleccionado.getProyecto_id());
        adaptadorPager= new AdapterPagerInformacionPozoSondeo(getFragmentManager(),tabInformacionPozoSondeo.getTabCount(), proyectoSeleccionado, usuarioLogueado, umtpSeleccionado, pozoSeleccionado);
        pagerInformacionPozoSondeo.setAdapter(adaptadorPager);
        pagerInformacionPozoSondeo.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabInformacionPozoSondeo));
        tabInformacionPozoSondeo.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position=tab.getPosition();
                pagerInformacionPozoSondeo.setCurrentItem(position);
                Toast.makeText(getContext(),"ENTRO EN TAB",Toast.LENGTH_LONG).show();
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
