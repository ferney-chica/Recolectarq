package com.recolectarq.campo.recolectarq.AdaptadoresViewPager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.recolectarq.campo.recolectarq.Fragmentos.Intervenciones.EstructuraArqueologica.EstructurasArqueologicasFragment;
import com.recolectarq.campo.recolectarq.Fragmentos.Intervenciones.MuestrasFragment;
import com.recolectarq.campo.recolectarq.Fragmentos.Intervenciones.PozosSondeo.EditarPozoFragment;
import com.recolectarq.campo.recolectarq.Fragmentos.Intervenciones.PozosSondeo.EstratosPozoSondeo.EstratosPozosSondeoFragment;
import com.recolectarq.campo.recolectarq.Fragmentos.Intervenciones.PozosSondeo.NivelesPozoSondeo.NivelesPozosSondeoFragment;
import com.recolectarq.campo.recolectarq.Modelo.PozosSondeo;
import com.recolectarq.campo.recolectarq.Modelo.Proyectos;
import com.recolectarq.campo.recolectarq.Modelo.Umtp;
import com.recolectarq.campo.recolectarq.Modelo.Usuarios;


public class AdapterPagerInformacionPozoSondeo extends FragmentStatePagerAdapter {
    private Proyectos proyectoSelecionado=new Proyectos();
    private Usuarios usuarioLogueado;
    private Umtp umtpSeleccionado;
    private PozosSondeo pozoSeleccionado;
    private Bundle argumentosEnviados;
    private int numeroTabs;

    FragmentManager fm1;
    public AdapterPagerInformacionPozoSondeo(FragmentManager fm, int numeroTabs, Proyectos pS, Usuarios uS, Umtp umtpS, PozosSondeo pozoS) {
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
        pozoSeleccionado=pozoS;
        System.out.println(proyectoSelecionado.getNombre()+ "desde InformacionPOZOSONDEO"+ "   " + usuarioLogueado.getApellido()+" UMTP: "+umtpSeleccionado.getUmtp_id() );

        usuarioLogueado=usuarioLogueado;
        this.numeroTabs=numeroTabs;

    }

    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                Fragment fragment= fm1.findFragmentByTag("EditarPozoFragment");
                if(fragment == null){

                    argumentosEnviados= new Bundle();
                    argumentosEnviados.putSerializable("proyecto",proyectoSelecionado);
                    argumentosEnviados.putSerializable("usuario",usuarioLogueado);
                    argumentosEnviados.putSerializable("umtp",umtpSeleccionado);
                    argumentosEnviados.putSerializable("pozo",pozoSeleccionado);
                    //System.out.println(proyectoSelecionado.getNombre() + "desde adaptador UMTP para crear el fragment");
                    EditarPozoFragment pozoSondeo= new EditarPozoFragment();
                    pozoSondeo.setArguments(argumentosEnviados);
                    return pozoSondeo;
                }else{
                    System.out.println( "entro al return del NUUUUUULLLLLLLLLLLLLLLLLLLLLLLLLLLLLL");
                    return null;
                }
            case 1:
                System.out.println( "tab UMTP");

                fragment= fm1.findFragmentByTag("NivelesPozosSondeoFragment");
                if(fragment == null){

                    argumentosEnviados= new Bundle();
                    argumentosEnviados.putSerializable("proyecto",proyectoSelecionado);
                    argumentosEnviados.putSerializable("usuario",usuarioLogueado);
                    argumentosEnviados.putSerializable("umtp",umtpSeleccionado);
                    argumentosEnviados.putSerializable("pozo",pozoSeleccionado);
                    //System.out.println(proyectoSelecionado.getNombre() + "desde adaptador UMTP para crear el fragment");
                    NivelesPozosSondeoFragment nivelPozo= new NivelesPozosSondeoFragment();
                    nivelPozo.setArguments(argumentosEnviados);
                    return nivelPozo;
                }else{
                    System.out.println( "entro al return del NUUUUUULLLLLLLLLLLLLLLLLLLLLLLLLLLLLL");
                    return null;
                }


            case 2:
                fragment= fm1.findFragmentByTag("EstratosPozosSondeoFragment");
                if(fragment == null){

                    argumentosEnviados= new Bundle();
                    argumentosEnviados.putSerializable("proyecto",proyectoSelecionado);
                    argumentosEnviados.putSerializable("usuario",usuarioLogueado);
                    argumentosEnviados.putSerializable("umtp",umtpSeleccionado);
                    argumentosEnviados.putSerializable("pozo",pozoSeleccionado);
                    //System.out.println(proyectoSelecionado.getNombre() + "desde adaptador UMTP para crear el fragment");
                    EstratosPozosSondeoFragment estratoPozo= new EstratosPozosSondeoFragment();
                    estratoPozo.setArguments(argumentosEnviados);
                    return estratoPozo;
                }else{
                    System.out.println( "entro al return del NUUUUUULLLLLLLLLLLLLLLLLLLLLLLLLLLLLL");
                    return null;
                }

            case 3:
                fragment= fm1.findFragmentByTag("EstructurasArqueologicasFragment");
                if(fragment == null){

                    argumentosEnviados= new Bundle();
                    argumentosEnviados.putSerializable("proyecto",proyectoSelecionado);
                    argumentosEnviados.putSerializable("usuario",usuarioLogueado);
                    argumentosEnviados.putSerializable("umtp",umtpSeleccionado);
                    argumentosEnviados.putSerializable("pozo",pozoSeleccionado);
                    argumentosEnviados.putString("origen","pozo");
                    //System.out.println(proyectoSelecionado.getNombre() + "desde adaptador UMTP para crear el fragment");
                    EstructurasArqueologicasFragment estructuraPozo= new EstructurasArqueologicasFragment();
                    estructuraPozo.setArguments(argumentosEnviados);
                    return estructuraPozo;
                }else{
                    System.out.println( "entro al return del NUUUUUULLLLLLLLLLLLLLLLLLLLLLLLLLLLLL");
                    return null;
                }

            case 4:
                fragment= fm1.findFragmentByTag("MuestrasFragment");
                if(fragment == null){

                    argumentosEnviados= new Bundle();
                    argumentosEnviados.putSerializable("proyecto",proyectoSelecionado);
                    argumentosEnviados.putSerializable("usuario",usuarioLogueado);
                    argumentosEnviados.putSerializable("umtp",umtpSeleccionado);
                    argumentosEnviados.putSerializable("pozo",pozoSeleccionado);
                    argumentosEnviados.putString("origen","pozo");
                    //System.out.println(proyectoSelecionado.getNombre() + "desde adaptador UMTP para crear el fragment");
                    MuestrasFragment muestraPozo= new MuestrasFragment();
                    muestraPozo.setArguments(argumentosEnviados);
                    return muestraPozo;
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
