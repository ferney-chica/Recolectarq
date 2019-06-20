package com.recolectarq.campo.recolectarq.Fragmentos.Intervenciones.EstructuraArqueologica;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.recolectarq.campo.recolectarq.Actividades.InicioUsuarioActivity;
import com.recolectarq.campo.recolectarq.AdaptadoresRecycler.AdapterMaterialesEstructuraArqueologica;
import com.recolectarq.campo.recolectarq.AdaptadoresRecycler.AdapterMaterialesNiveles;
import com.recolectarq.campo.recolectarq.Carpetas;
import com.recolectarq.campo.recolectarq.CuadrosDialogos.CuadroDialogoActualizarMaterial;
import com.recolectarq.campo.recolectarq.Fragmentos.Intervenciones.PerfilesExpuestos.VerPerfilExpuestoFragment;
import com.recolectarq.campo.recolectarq.Fragmentos.Intervenciones.PozosSondeo.VerPozoSondeoFragment;
import com.recolectarq.campo.recolectarq.Fragmentos.Intervenciones.RecoleccionesSuperficiales.VerRecoleccionSuperficialFragment;
import com.recolectarq.campo.recolectarq.Modelo.EstructurasArqueologicas;
import com.recolectarq.campo.recolectarq.Modelo.MaterialesEstratosPerfiles;
import com.recolectarq.campo.recolectarq.Modelo.MaterialesEstructurasArqueologicas;
import com.recolectarq.campo.recolectarq.Modelo.MaterialesNiveles;
import com.recolectarq.campo.recolectarq.Modelo.MaterialesRecolecciones;
import com.recolectarq.campo.recolectarq.Modelo.NivelesPozos;
import com.recolectarq.campo.recolectarq.Modelo.PerfilesExpuestos;
import com.recolectarq.campo.recolectarq.Modelo.PozosSondeo;
import com.recolectarq.campo.recolectarq.Modelo.Proyectos;
import com.recolectarq.campo.recolectarq.Modelo.RecoleccionesSuperficiales;
import com.recolectarq.campo.recolectarq.Modelo.Umtp;
import com.recolectarq.campo.recolectarq.Modelo.Usuarios;
import com.recolectarq.campo.recolectarq.R;
import com.recolectarq.campo.recolectarq.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MaterialesEstructuraArqueologicaFragment extends Fragment implements InicioUsuarioActivity.OnBackPressedListener{

    private String cedula;
    int estructuras_arqueologicas_id;
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    Date fechaFin;
    Date fechaInicio;
    private ArrayList<MaterialesEstructurasArqueologicas> listMaterialesEstructura;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    JsonObjectRequest jsonObjectRequest;
    FloatingActionButton fabBotonAdicionarMaterialesEstructuraArqueologica;
    private MaterialesEstructurasArqueologicas materialEstructura;
    private Usuarios usuarioLogueado;
    private Proyectos proyectoSeleccionado;
    private Umtp umtpSeleccionado;
    private PozosSondeo pozoSeleccionado;
    private RecoleccionesSuperficiales recoleccionSeleccionado;
    private PerfilesExpuestos perfilSeleccionado;
    private EstructurasArqueologicas estructuraSeleccionada;
    private Bundle argumentosEnviados;
    private String origen;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments()!=null)
        {
            Toast.makeText(getContext(),"entro",Toast.LENGTH_LONG);
            Toast.makeText(getContext(),"entro",Toast.LENGTH_LONG);
            usuarioLogueado=(Usuarios) getArguments().getSerializable("usuario");
            proyectoSeleccionado=(Proyectos)getArguments().getSerializable("proyecto");
            umtpSeleccionado=(Umtp) getArguments().getSerializable("umtp");
            //pozoSeleccionado=(PozosSondeo) getArguments().getSerializable("pozo");
            estructuraSeleccionada=(EstructurasArqueologicas) getArguments().getSerializable("estructura");
            estructuras_arqueologicas_id=estructuraSeleccionada.getEstructuras_arqueologicas_id();
            origen=getArguments().getString("origen");
            //System.out.println(origen+"  Materiles estructuras");
            Carpetas carpeta=new Carpetas();
            System.out.println(umtpSeleccionado.getUmtp_id()+ "DESDE MATERIALES RECOLECCION");
            System.out.println( carpeta.crearCarpeta("/Recolectarq/Proyectos/"+proyectoSeleccionado.getCodigo_identificacion()));
            carpeta.crearCarpeta("/Recolectarq/Proyectos/"+proyectoSeleccionado.getCodigo_identificacion()+"/UMTP");
            carpeta.crearCarpeta("/Recolectarq/Proyectos/"+proyectoSeleccionado.getCodigo_identificacion()+"/MemoriasTecnicas");
            int idUsuario = Integer.parseInt(usuarioLogueado.getUsuario_id());
            //niveles_pozos_nivel_pozo_id=nivelSeleccionado.getNivel_pozo_id();

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
        View view= inflater.inflate(R.layout.fragment_materiales_estructura_arqueologica,container, false);

        listMaterialesEstructura= new ArrayList<>();
        mRecyclerView=view.findViewById(R.id.recyclerViewMaterialesEstructuraArqueologica);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
        fabBotonAdicionarMaterialesEstructuraArqueologica= view.findViewById(R.id.fabBotonAdicionarMaterialesEstructuraArqueologica);
        consultarMaterialesEstructuraArqueologica();
        //System.out.println("El usuario Logueado es desde proyectos" + usuarioLogueado.getNombre());
        fabBotonAdicionarMaterialesEstructuraArqueologica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               argumentosEnviados= new Bundle();
                argumentosEnviados.putSerializable("proyecto",proyectoSeleccionado);
                argumentosEnviados.putSerializable("usuario",usuarioLogueado);
                argumentosEnviados.putSerializable("umtp",umtpSeleccionado);
                argumentosEnviados.putSerializable("estructura",estructuraSeleccionada);

                switch (origen)
                {
                    case "pozoEstructura":
                        argumentosEnviados.putSerializable("pozo",(PozosSondeo) getArguments().getSerializable("pozo"));
                        break;
                    case "recoleccionEstructura":
                        argumentosEnviados.putSerializable("recoleccion",(RecoleccionesSuperficiales) getArguments().getSerializable("recoleccion"));
                        break;
                    case "perfilEstructura":
                        argumentosEnviados.putSerializable("perfil",(PerfilesExpuestos) getArguments().getSerializable("perfil"));
                        break;
                }
                argumentosEnviados.putString("origen",origen);
                System.out.println("ENTRO A CREAR MATERIAL ESTRUCTURA///////////---------***************************");
                CrearMaterialEstructuraArqueologicaFragment materialEstructura= new CrearMaterialEstructuraArqueologicaFragment();
                materialEstructura.setArguments(argumentosEnviados);
                FragmentManager manager= getFragmentManager();
                manager.beginTransaction().replace(R.id.contenidos, materialEstructura).commit();
            }
        });

        return view;
    }



    private void consultarMaterialesEstructuraArqueologica() {
        /*final ProgressDialog pDialog;
        pDialog=new ProgressDialog(getContext());
        pDialog.setMessage("Cargando...");
        pDialog.show();*/

        final String ip=getString(R.string.ip_url);


        String url=ip+"/web_services/buscar_materiales_estructura_arqueologica.php?estructuras_arqueologicas_id="+estructuras_arqueologicas_id;

        System.out.println(url);

        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //pDialog.hide();

                JSONArray json=response.optJSONArray("materiales_estructuras_arqueologicas");
                JSONObject jsonObject;

                try {
                    for (int i=0;i<json.length();i++) {
                        jsonObject = json.getJSONObject(i);

                        materialEstructura=new MaterialesEstructurasArqueologicas(jsonObject.getInt("id"), jsonObject.getInt("estructuras_arqueologicas_id"),
                                jsonObject.getInt("tipos_materiales_id"), jsonObject.getString("nombre_tipo_material"));
                        //proyecto.setNombre(jsonObject.optString("nombre"));
                        //miUsuario.setProfesion(jsonObject.optString("profesion"));
                        //miUsuario.setRutaImagen(jsonObject.optString("ruta_imagen"));
                        //System.out.println(jsonObject.getString("nombre"));
                        listMaterialesEstructura.add(materialEstructura);

                    }

                    mAdapter=new AdapterMaterialesEstructuraArqueologica(listMaterialesEstructura, R.layout.recycler_view_materiales_estructura_arqueologica, new AdapterMaterialesEstructuraArqueologica.OnItemClickListener() {
                        @Override
                        public void onItemClick(MaterialesEstructurasArqueologicas materialEstructuraS, int position, String boton) {

                            if(boton.equals("Ver")){


                                materialEstructura=materialEstructuraS;
                                System.out.println("MATERIAL ESTRUCTURA: "+materialEstructura.getId());

                                /*argumentosEnviados= new Bundle();
                                argumentosEnviados.putSerializable("proyecto",proyectoSeleccionado);
                                argumentosEnviados.putSerializable("usuario",usuarioLogueado);
                                argumentosEnviados.putSerializable("umtp",umtpSeleccionado);
                                argumentosEnviados.putSerializable("recoleccion",recoleccionSeleccionado);
                                VerRecoleccionSuperficialFragment verRecoleccion= new VerRecoleccionSuperficialFragment();
                                verRecoleccion.setArguments(argumentosEnviados);
                                FragmentManager manager= getFragmentManager();
                                manager.beginTransaction().replace(R.id.contenidos, verRecoleccion).commit();*//*
                                MaterialesRecolecciones materialeRecoleccion=new MaterialesRecolecciones();
                                MaterialesEstratosPerfiles materialEstratoPerfil=new MaterialesEstratosPerfiles();
                                new CuadroDialogoActualizarMaterial(getContext(),listMaterialNivel,materialeRecoleccion,materialEstratoPerfil,"materialNivelPozo", MaterialesEstructuraArqueologicaFragment.this);
                           */ }
                        }
                    });

                }  catch (JSONException e) {
                    e.printStackTrace();
                }

                //etiNombre.setText(miUsuario.getNombre());//SE MODIFICA
                //etiProfesion.setText(miUsuario.getProfesion());//SE MODIFICA

                //String urlImagen=ip+"/ejemploBDRemota/"+miUsuario.getRutaImagen();
                //Toast.makeText(getContext(), "url "+urlImagen, Toast.LENGTH_LONG).show();
                //cargarWebServiceImagen(urlImagen);

                mRecyclerView.setAdapter(mAdapter);
                json=null;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "No se puede conectar "+error.toString(), Toast.LENGTH_LONG).show();
                System.out.println();
                //pDialog.hide();
                Log.d("ERROR: ", error.toString());
            }
        });

        // request.add(jsonObjectRequest);
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(jsonObjectRequest);
    }

    @Override
    public void doBack() {

        System.out.println("EL ORIGEN Al DAr ATRAS es: "+ origen);
        switch (origen)
        {
            case "pozoEstructura":

                pozoSeleccionado=(PozosSondeo) getArguments().getSerializable("pozo");

                Bundle argumentosEnviados;
                argumentosEnviados= new Bundle();
                argumentosEnviados.putSerializable("proyecto",proyectoSeleccionado);
                argumentosEnviados.putSerializable("usuario",usuarioLogueado);
                argumentosEnviados.putSerializable("umtp",umtpSeleccionado);
                argumentosEnviados.putSerializable("pozo",pozoSeleccionado);

                VerPozoSondeoFragment verPozo= new VerPozoSondeoFragment();
                verPozo.setArguments(argumentosEnviados);
                FragmentManager manager= getFragmentManager();
                manager.beginTransaction().replace(R.id.contenidos, verPozo).commit();

                break;
            case "recoleccionEstructura":

                recoleccionSeleccionado=(RecoleccionesSuperficiales) getArguments().getSerializable("recoleccion");


                argumentosEnviados= new Bundle();
                argumentosEnviados.putSerializable("proyecto",proyectoSeleccionado);
                argumentosEnviados.putSerializable("usuario",usuarioLogueado);
                argumentosEnviados.putSerializable("umtp",umtpSeleccionado);
                argumentosEnviados.putSerializable("recoleccion",recoleccionSeleccionado);

                VerRecoleccionSuperficialFragment verRecoleccion= new VerRecoleccionSuperficialFragment();
                verRecoleccion.setArguments(argumentosEnviados);
                manager= getFragmentManager();
                manager.beginTransaction().replace(R.id.contenidos, verRecoleccion).commit();

                break;
            case "perfilEstructura":

                perfilSeleccionado=(PerfilesExpuestos) getArguments().getSerializable("perfil");


                argumentosEnviados= new Bundle();
                argumentosEnviados.putSerializable("proyecto",proyectoSeleccionado);
                argumentosEnviados.putSerializable("usuario",usuarioLogueado);
                argumentosEnviados.putSerializable("umtp",umtpSeleccionado);
                argumentosEnviados.putSerializable("perfil",perfilSeleccionado);

                VerPerfilExpuestoFragment verPerfil= new VerPerfilExpuestoFragment();
                verPerfil.setArguments(argumentosEnviados);
                manager= getFragmentManager();
                manager.beginTransaction().replace(R.id.contenidos, verPerfil).commit();

                break;
        }




    }

   /* @Override
    public void MaterialNivelPozoActualizado(MaterialesNiveles materialNivelPozo) {
        listMaterialesNiveles.clear();
        consultarMaterialesRecoleccionesSuperfiales();
    }

    @Override
    public void MaterialRecoleccion(MaterialesRecolecciones materialRecoleccion) {

    }

    @Override
    public void MaterialEstratoPerfil(MaterialesEstratosPerfiles materialEstratoPerfil) {


    }*/
}
