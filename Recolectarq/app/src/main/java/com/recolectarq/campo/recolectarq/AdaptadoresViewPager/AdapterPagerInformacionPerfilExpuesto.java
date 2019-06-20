package com.recolectarq.campo.recolectarq.AdaptadoresViewPager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.recolectarq.campo.recolectarq.Fragmentos.Intervenciones.EstructuraArqueologica.EstructurasArqueologicasFragment;
import com.recolectarq.campo.recolectarq.Fragmentos.Intervenciones.MuestrasFragment;
import com.recolectarq.campo.recolectarq.Fragmentos.Intervenciones.PerfilesExpuestos.EditarPerfilExpuestoFragment;
import com.recolectarq.campo.recolectarq.Fragmentos.Intervenciones.PerfilesExpuestos.EstratosPerfilesExpuestos.EstratosPerfilesExpuestosFragment;
import com.recolectarq.campo.recolectarq.Modelo.PerfilesExpuestos;
import com.recolectarq.campo.recolectarq.Modelo.Proyectos;
import com.recolectarq.campo.recolectarq.Modelo.Umtp;
import com.recolectarq.campo.recolectarq.Modelo.Usuarios;


public class AdapterPagerInformacionPerfilExpuesto extends FragmentStatePagerAdapter {
    private Proyectos proyectoSelecionado= new Proyectos();
    private Usuarios usuarioLogueado;
    private Umtp umtpSeleccionado;
    private PerfilesExpuestos perfilSeleccionado;
    private Bundle argumentosEnviados;
    private int numeroTabs;

    FragmentManager fm1;
    public AdapterPagerInformacionPerfilExpuesto(FragmentManager fm, int numeroTabs, Proyectos pS, Usuarios uS, Umtp umtpS, PerfilesExpuestos perfilS) {
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
        perfilSeleccionado=perfilS;
        System.out.println(proyectoSelecionado.getNombre()+ "desde Informacion PERFIL"+ "   " + usuarioLogueado.getApellido()+" UMTP: "+umtpSeleccionado.getUmtp_id() );

        usuarioLogueado=usuarioLogueado;
        this.numeroTabs=numeroTabs;

    }

    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                Fragment fragment= fm1.findFragmentByTag("EditarPerfilExpuestoFragment");
                if(fragment == null){

                    argumentosEnviados= new Bundle();
                    argumentosEnviados.putSerializable("proyecto",proyectoSelecionado);
                    argumentosEnviados.putSerializable("usuario",usuarioLogueado);
                    argumentosEnviados.putSerializable("umtp",umtpSeleccionado);
                    argumentosEnviados.putSerializable("perfil",perfilSeleccionado);
                    //System.out.println(proyectoSelecionado.getNombre() + "desde adaptador UMTP para crear el fragment");
                    EditarPerfilExpuestoFragment perfilExpuesto= new EditarPerfilExpuestoFragment();
                    perfilExpuesto.setArguments(argumentosEnviados);
                    return perfilExpuesto;
                }else{
                    System.out.println( "entro al return del NUUUUUULLLLLLLLLLLLLLLLLLLLLLLLLLLLLL");
                    return null;
                }
            case 1:
                fragment= fm1.findFragmentByTag("EstratosPerfilesExpuestosFragment");
                if(fragment == null){

                    argumentosEnviados= new Bundle();
                    argumentosEnviados.putSerializable("proyecto",proyectoSelecionado);
                    argumentosEnviados.putSerializable("usuario",usuarioLogueado);
                    argumentosEnviados.putSerializable("umtp",umtpSeleccionado);
                    argumentosEnviados.putSerializable("perfil",perfilSeleccionado);
                    //System.out.println(proyectoSelecionado.getNombre() + "desde adaptador UMTP para crear el fragment");
                    EstratosPerfilesExpuestosFragment estratoPerfil= new EstratosPerfilesExpuestosFragment();
                    estratoPerfil.setArguments(argumentosEnviados);
                    return estratoPerfil;
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
                    argumentosEnviados.putSerializable("perfil",perfilSeleccionado);
                    argumentosEnviados.putString("origen","perfil");
                    //System.out.println(proyectoSelecionado.getNombre() + "desde adaptador UMTP para crear el fragment");
                    EstructurasArqueologicasFragment estructuraPozo= new EstructurasArqueologicasFragment();
                    estructuraPozo.setArguments(argumentosEnviados);
                    return estructuraPozo;
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
                    argumentosEnviados.putSerializable("perfil",perfilSeleccionado);
                    argumentosEnviados.putString("origen","perfil");
                    //System.out.println(proyectoSelecionado.getNombre() + "desde adaptador UMTP para crear el fragment");
                    MuestrasFragment muestraPerfil= new MuestrasFragment();
                    muestraPerfil.setArguments(argumentosEnviados);
                    return muestraPerfil;
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
