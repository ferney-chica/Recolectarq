package com.recolectarq.campo.recolectarq.AdaptadoresViewPager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.recolectarq.campo.recolectarq.Fragmentos.Intervenciones.PerfilesExpuestos.ElementoDiagnosticoPerfilesFragment;
import com.recolectarq.campo.recolectarq.Fragmentos.Intervenciones.PerfilesExpuestos.PerfilesExpuestosFragment;
import com.recolectarq.campo.recolectarq.Fragmentos.Intervenciones.PozosSondeo.ElementoDiagnosticoPozosFragment;
import com.recolectarq.campo.recolectarq.Fragmentos.Intervenciones.PozosSondeo.PozosSondeoFragment;
import com.recolectarq.campo.recolectarq.Fragmentos.Intervenciones.RecoleccionesSuperficiales.ElementoDiagnosticoRecoleccionesFragment;
import com.recolectarq.campo.recolectarq.Fragmentos.Intervenciones.RecoleccionesSuperficiales.RecoleccionesSuperficialesFragment;
import com.recolectarq.campo.recolectarq.Fragmentos.Proyectos.CrearProyectoFragment;
import com.recolectarq.campo.recolectarq.Modelo.Proyectos;
import com.recolectarq.campo.recolectarq.Modelo.Umtp;
import com.recolectarq.campo.recolectarq.Modelo.Usuarios;


public class AdapterPagerReporteElementosDiagnosticos extends FragmentStatePagerAdapter {
    private Proyectos proyectoSelecionado=new Proyectos();
    private Usuarios usuarioLogueado;
    private Umtp umtpSeleccionado;
    private Bundle argumentosEnviados;
    private int numeroTabs;

    FragmentManager fm1;
    public AdapterPagerReporteElementosDiagnosticos(FragmentManager fm, int numeroTabs, Proyectos pS, Usuarios uS, Umtp umtpS) {
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
        System.out.println(proyectoSelecionado.getNombre()+ "desde Elementos Diagnosticos"+ "   " + usuarioLogueado.getApellido()+" UMTP: "+umtpSeleccionado.getUmtp_id() );

        usuarioLogueado=usuarioLogueado;
        this.numeroTabs=numeroTabs;

    }

    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                Fragment fragment= fm1.findFragmentByTag("ElementoDiagnosticoPozosFragment");
                if(fragment == null){

                    argumentosEnviados= new Bundle();
                    argumentosEnviados.putSerializable("proyecto",proyectoSelecionado);
                    argumentosEnviados.putSerializable("usuario",usuarioLogueado);
                    argumentosEnviados.putSerializable("umtp",umtpSeleccionado);
                    System.out.println(umtpSeleccionado.getUmtp_id() + "id UMTP desde Pager");
                    ElementoDiagnosticoPozosFragment elementoPozo= new ElementoDiagnosticoPozosFragment();
                    elementoPozo.setArguments(argumentosEnviados);
                    return elementoPozo;
                }else{
                    System.out.println( "entro al return del NUUUUUULLLLLLLLLLLLLLLLLLLLLLLLLLLLLL");
                    return null;
                }
            case 1:
                System.out.println( "tab UMTP");

                fragment= fm1.findFragmentByTag("ElementoDiagnosticoRecoleccionesFragment");
                if(fragment == null){

                    argumentosEnviados= new Bundle();
                    argumentosEnviados.putSerializable("proyecto",proyectoSelecionado);
                    argumentosEnviados.putSerializable("usuario",usuarioLogueado);
                    argumentosEnviados.putSerializable("umtp",umtpSeleccionado);
                    System.out.println(umtpSeleccionado.getUmtp_id() + "id UMTP desde Pager");
                    ElementoDiagnosticoRecoleccionesFragment elementoRecoleccion= new ElementoDiagnosticoRecoleccionesFragment();
                    elementoRecoleccion.setArguments(argumentosEnviados);
                    return elementoRecoleccion;
                }else{
                    System.out.println( "entro al return del NUUUUUULLLLLLLLLLLLLLLLLLLLLLLLLLLLLL");
                    return null;
                }


            case 2:
                fragment= fm1.findFragmentByTag("ElementoDiagnosticoPerfilesFragment");
                if(fragment == null){

                    argumentosEnviados= new Bundle();
                    argumentosEnviados.putSerializable("proyecto",proyectoSelecionado);
                    argumentosEnviados.putSerializable("usuario",usuarioLogueado);
                    argumentosEnviados.putSerializable("umtp",umtpSeleccionado);
                    System.out.println(umtpSeleccionado.getUmtp_id() + "id UMTP desde Pager");
                    ElementoDiagnosticoPerfilesFragment elementoPerfil= new ElementoDiagnosticoPerfilesFragment();
                    elementoPerfil.setArguments(argumentosEnviados);
                    return elementoPerfil;
                }else{
                    System.out.println( "entro al return del NUUUUUULLLLLLLLLLLLLLLLLLLLLLLLLLLLLL");
                    return null;
                }

            case 3:
                return new CrearProyectoFragment();

            default:
                    return null;
        }

    }

    @Override
    public int getCount() {
        return numeroTabs;
    }
}
