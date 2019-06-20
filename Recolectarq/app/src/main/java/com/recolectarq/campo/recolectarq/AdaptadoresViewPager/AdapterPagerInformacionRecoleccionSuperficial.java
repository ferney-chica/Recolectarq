package com.recolectarq.campo.recolectarq.AdaptadoresViewPager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.recolectarq.campo.recolectarq.Fragmentos.Intervenciones.EstructuraArqueologica.EstructurasArqueologicasFragment;
import com.recolectarq.campo.recolectarq.Fragmentos.Intervenciones.MuestrasFragment;
import com.recolectarq.campo.recolectarq.Fragmentos.Intervenciones.RecoleccionesSuperficiales.EditarRecoleccionFragment;
import com.recolectarq.campo.recolectarq.Fragmentos.Intervenciones.RecoleccionesSuperficiales.MaterialesRecolecciones.MaterialesRecoleccionesSuperficialesFragment;
import com.recolectarq.campo.recolectarq.Modelo.Proyectos;
import com.recolectarq.campo.recolectarq.Modelo.RecoleccionesSuperficiales;
import com.recolectarq.campo.recolectarq.Modelo.Umtp;
import com.recolectarq.campo.recolectarq.Modelo.Usuarios;


public class AdapterPagerInformacionRecoleccionSuperficial extends FragmentStatePagerAdapter {
    private Proyectos proyectoSelecionado=new Proyectos();
    private Usuarios usuarioLogueado;
    private Umtp umtpSeleccionado;
    private RecoleccionesSuperficiales recoleccionSeleccionado;
    private Bundle argumentosEnviados;
    private int numeroTabs;

    FragmentManager fm1;
    public AdapterPagerInformacionRecoleccionSuperficial(FragmentManager fm, int numeroTabs, Proyectos pS, Usuarios uS, Umtp umtpS, RecoleccionesSuperficiales recoleccionS) {
        super(fm);
        fm1=fm;
        //proyectoSelecionado=proyectoSelecionado;
        /*proyectoSelecionado=new Proyectos(pS.getProyecto_id(),pS.getUsuario_id(), pS.getTipo_proyecto_id(),pS.getFase_proyecto_id(),
                pS.getNombre(), pS.getUbicacion(),pS.getFecha_inicio(), pS.getFecha_fin(),pS.getReferencias_administrativas(),
                pS.getAval_cientifico(),pS.getCodigo_identificacion());*/
        proyectoSelecionado=pS;
        usuarioLogueado=new Usuarios(uS.getUsuario_id(), uS.getNombre(), uS.getApellido(),uS.getContrasena());
        umtpSeleccionado=new Umtp();
        umtpSeleccionado=umtpS;
        recoleccionSeleccionado=recoleccionS;
        System.out.println(proyectoSelecionado.getNombre()+ "desde Informacion RECOLECCION"+ "   " + usuarioLogueado.getApellido()+" UMTP: "+umtpSeleccionado.getUmtp_id() );

        usuarioLogueado=usuarioLogueado;
        this.numeroTabs=numeroTabs;

    }

    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                Fragment fragment= fm1.findFragmentByTag("EditarRecoleccionFragment");
                if(fragment == null){

                    argumentosEnviados= new Bundle();
                    argumentosEnviados.putSerializable("proyecto",proyectoSelecionado);
                    argumentosEnviados.putSerializable("usuario",usuarioLogueado);
                    argumentosEnviados.putSerializable("umtp",umtpSeleccionado);
                    argumentosEnviados.putSerializable("recoleccion",recoleccionSeleccionado);
                    //System.out.println(proyectoSelecionado.getNombre() + "desde adaptador UMTP para crear el fragment");
                    EditarRecoleccionFragment recoleccion= new EditarRecoleccionFragment();
                    recoleccion.setArguments(argumentosEnviados);
                    return recoleccion;
                }else{
                    System.out.println( "entro al return del NUUUUUULLLLLLLLLLLLLLLLLLLLLLLLLLLLLL");
                    return null;
                }
            case 1:
                fragment= fm1.findFragmentByTag("MaterialesRecoleccionesSuperficialesFragment");
                if(fragment == null){

                    argumentosEnviados= new Bundle();
                    argumentosEnviados.putSerializable("proyecto",proyectoSelecionado);
                    argumentosEnviados.putSerializable("usuario",usuarioLogueado);
                    argumentosEnviados.putSerializable("umtp",umtpSeleccionado);
                    argumentosEnviados.putSerializable("recoleccion",recoleccionSeleccionado);
                    //System.out.println(proyectoSelecionado.getNombre() + "desde adaptador UMTP para crear el fragment");
                    MaterialesRecoleccionesSuperficialesFragment materialesRecoleccion= new MaterialesRecoleccionesSuperficialesFragment();
                    materialesRecoleccion.setArguments(argumentosEnviados);
                    return materialesRecoleccion;
                }else{
                    System.out.println( "entro al return del NUUUUUULLLLLLLLLLLLLLLLLLLLLLLLLLLLLL");
                    return null;
                }




            case 2:
                fragment= fm1.findFragmentByTag("EstructurasArqueologicasFragment");
                if(fragment == null){

                    argumentosEnviados= new Bundle();
                    argumentosEnviados.putSerializable("proyecto",proyectoSelecionado);
                    argumentosEnviados.putSerializable("usuario",usuarioLogueado);
                    argumentosEnviados.putSerializable("umtp",umtpSeleccionado);
                    argumentosEnviados.putSerializable("recoleccion",recoleccionSeleccionado);
                    argumentosEnviados.putString("origen","recoleccion");
                    //System.out.println(proyectoSelecionado.getNombre() + "desde adaptador UMTP para crear el fragment");
                    EstructurasArqueologicasFragment estructuraRecoleccion= new EstructurasArqueologicasFragment();
                    estructuraRecoleccion.setArguments(argumentosEnviados);
                    return estructuraRecoleccion;
                }else{
                    System.out.println( "entro al return del NUUUUUULLLLLLLLLLLLLLLLLLLLLLLLLLLLLL");
                    return null;
                }
            case 3:
                fragment= fm1.findFragmentByTag("MuestrasFragment");
                if(fragment == null){

                    argumentosEnviados= new Bundle();
                    argumentosEnviados.putSerializable("proyecto",proyectoSelecionado);
                    argumentosEnviados.putSerializable("usuario",usuarioLogueado);
                    argumentosEnviados.putSerializable("umtp",umtpSeleccionado);
                    argumentosEnviados.putSerializable("recoleccion",recoleccionSeleccionado);
                    argumentosEnviados.putString("origen","recoleccion");
                    //System.out.println(proyectoSelecionado.getNombre() + "desde adaptador UMTP para crear el fragment");
                    MuestrasFragment muestraRecoleccion= new MuestrasFragment();
                    muestraRecoleccion.setArguments(argumentosEnviados);
                    return muestraRecoleccion;
                }else{
                    System.out.println( "entro al return del NUUUUUULLLLLLLLLLLLLLLLLLLLLLLLLLLLLL");
                    return null;
                }
            default:
                    return null;
        }

    }

    @Override
    public int getCount() {
        return numeroTabs;
    }
}
