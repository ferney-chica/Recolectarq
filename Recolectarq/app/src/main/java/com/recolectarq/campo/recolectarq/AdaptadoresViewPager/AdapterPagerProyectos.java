package com.recolectarq.campo.recolectarq.AdaptadoresViewPager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.recolectarq.campo.recolectarq.Fragmentos.Proyectos.CrearProyectoFragment;
import com.recolectarq.campo.recolectarq.Fragmentos.Proyectos.EditarProyectoFragment;
import com.recolectarq.campo.recolectarq.Fragmentos.Proyectos.VerProyectoFragment;
import com.recolectarq.campo.recolectarq.Fragmentos.Umtp.UmtpFragment;
import com.recolectarq.campo.recolectarq.Fragmentos.Usuarios.CrearUsuarioFragment;
import com.recolectarq.campo.recolectarq.Fragmentos.UsuariosProyectos.UsuariosProyectosFragment;
import com.recolectarq.campo.recolectarq.Modelo.Proyectos;
import com.recolectarq.campo.recolectarq.Modelo.Usuarios;


public class AdapterPagerProyectos extends FragmentStatePagerAdapter {
    private Proyectos proyectoSelecionado=new Proyectos();
    private Usuarios usuarioLogueado;
    private Bundle argumentosEnviados;
    private int numeroTabs;

    FragmentManager fm1;
    public AdapterPagerProyectos(FragmentManager fm, int numeroTabs, Proyectos pS, Usuarios uS) {
        super(fm);
        fm1=fm;
        //proyectoSelecionado=proyectoSelecionado;
        /*proyectoSelecionado=new Proyectos(pS.getProyecto_id(),pS.getUsuario_id(), pS.getTipo_proyecto_id(),pS.getFase_proyecto_id(),
                pS.getNombre(), pS.getUbicacion(),pS.getFecha_inicio(), pS.getFecha_fin(),pS.getReferencias_administrativas(),
                pS.getAval_cientifico(),pS.getCodigo_identificacion());*/
        proyectoSelecionado=pS;
        usuarioLogueado=new Usuarios(uS.getUsuario_id(), uS.getNombre(), uS.getApellido(),uS.getContrasena());
        System.out.println(proyectoSelecionado.getNombre()+ "desde ADAPTADOR"+ "   " + usuarioLogueado.getApellido());

        usuarioLogueado=usuarioLogueado;
        this.numeroTabs=numeroTabs;

    }

    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                argumentosEnviados= new Bundle();
                argumentosEnviados.putSerializable("proyecto",proyectoSelecionado);
                argumentosEnviados.putSerializable("usuario",usuarioLogueado);
             System.out.println(proyectoSelecionado.getNombre() + "desde adaptador para crear el fragment");
                EditarProyectoFragment editarProyecto= new EditarProyectoFragment();
                editarProyecto.setArguments(argumentosEnviados);
                //FragmentManager manager= getFragmentManager();
            return editarProyecto;
            case 1:
                System.out.println( "tab UMTP");

                Fragment fragment= fm1.findFragmentByTag("UmtpFragment");
                if(fragment == null){

                    argumentosEnviados= new Bundle();
                    argumentosEnviados.putSerializable("proyecto",proyectoSelecionado);
                    argumentosEnviados.putSerializable("usuario",usuarioLogueado);
                    System.out.println(proyectoSelecionado.getNombre() + "desde adaptador UMTP para crear el fragment");
                    UmtpFragment umtpProyecto= new UmtpFragment();
                    umtpProyecto.setArguments(argumentosEnviados);
                    return umtpProyecto;
                }else{
                    System.out.println( "entro al return del NUUUUUULLLLLLLLLLLLLLLLLLLLLLLLLLLLLL");
                    return null;
                }


            case 2:
                fragment= fm1.findFragmentByTag("UsuariosProyectosFragment");
                if(fragment == null){

                    argumentosEnviados= new Bundle();
                    argumentosEnviados.putSerializable("proyecto",proyectoSelecionado);
                    argumentosEnviados.putSerializable("usuario",usuarioLogueado);
                    System.out.println(proyectoSelecionado.getNombre() + "desde adaptador UMTP para crear el fragment");
                    UsuariosProyectosFragment usuarioProyecto= new UsuariosProyectosFragment();
                    usuarioProyecto.setArguments(argumentosEnviados);
                    return usuarioProyecto;
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
