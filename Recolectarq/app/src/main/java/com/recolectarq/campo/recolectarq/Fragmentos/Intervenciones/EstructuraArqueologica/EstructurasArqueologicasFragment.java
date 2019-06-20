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
import com.recolectarq.campo.recolectarq.AdaptadoresRecycler.AdapterEstructurasArqueologicas;
import com.recolectarq.campo.recolectarq.Carpetas;
import com.recolectarq.campo.recolectarq.CuadrosDialogos.CuadroDialogoActualizarEstructuraArqueologica;
import com.recolectarq.campo.recolectarq.Fragmentos.ImagenesFragment;
import com.recolectarq.campo.recolectarq.Modelo.EstructurasArqueologicas;
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

public class EstructurasArqueologicasFragment extends Fragment implements CuadroDialogoActualizarEstructuraArqueologica.FinalizarCuadroDialogo {

    private String cedula;
    int idPozo=0;
    int idRecoleccion=0;
    int idPerfil=0;
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    Date fechaFin;
    Date fechaInicio;
    private ArrayList<EstructurasArqueologicas> listEstructurasArqueologicas;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    JsonObjectRequest jsonObjectRequest;
    FloatingActionButton fabBotonAdicionarEstructuraArqueologica;
    private EstructurasArqueologicas estructura;
    private PozosSondeo pozoSeleccionado;
    private RecoleccionesSuperficiales recoleccionSeleccionado;
    private PerfilesExpuestos pefilSeleccionado;
    private Usuarios usuarioLogueado;
    private Proyectos proyectoSeleccionado;
    private Umtp umtpSeleccionado;
    private Bundle usuarioEnviado;
    private Bundle argumentosEnviados;
    private String origen;
    String url="";

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

            origen=getArguments().getString("origen");
            configurarObjetosSegunOrigen(origen);
            System.out.println(origen + "************_____-------");
            Carpetas carpeta=new Carpetas();
            System.out.println(umtpSeleccionado.getUmtp_id()+ "DESDE POZO SONDEO");
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

        View view= inflater.inflate(R.layout.fragment_estructuras_arqueologicas,container, false);

        listEstructurasArqueologicas= new ArrayList<>();
        mRecyclerView=view.findViewById(R.id.recyclerViewEstructuraArqueologica);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
        fabBotonAdicionarEstructuraArqueologica= view.findViewById(R.id.fabBotonAdicionarEstructuraArqueologica);
        configurarUrlSegunOrigen(origen);
        System.out.println("El usuario Logueado es desde proyectos" + usuarioLogueado.getNombre());
        fabBotonAdicionarEstructuraArqueologica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("CREAR ESTRUCTURA ARQUITECTURA ");
                argumentosEnviados= new Bundle();
                argumentosEnviados.putSerializable("proyecto",proyectoSeleccionado);
                argumentosEnviados.putSerializable("usuario",usuarioLogueado);
                argumentosEnviados.putSerializable("umtp",umtpSeleccionado);


                if(origen=="pozo")
                {
                    argumentosEnviados.putSerializable("pozo",pozoSeleccionado);
                    argumentosEnviados.putString("origen","pozo");
                }else
                {
                    if(origen=="recoleccion")
                    {
                        argumentosEnviados.putSerializable("recoleccion",recoleccionSeleccionado);
                        argumentosEnviados.putString("origen","recoleccion");
                    }else
                    {
                        argumentosEnviados.putSerializable("perfil",pefilSeleccionado);
                        argumentosEnviados.putString("origen","perfil");
                    }


                }

                //System.out.println(proyectoSelecionado.getNombre() + "desde adaptador UMTP para crear el fragment");
                CrearEstructuraArqueologicaFragment estructuraArquitectura= new CrearEstructuraArqueologicaFragment();
                estructuraArquitectura.setArguments(argumentosEnviados);
                FragmentManager manager= getFragmentManager();
                manager.beginTransaction().replace(R.id.contenidos, estructuraArquitectura).commit();
            }
        });

        return view;
    }
    private void configurarObjetosSegunOrigen(String origen) {

        switch(origen){
            case "pozo":
                pozoSeleccionado=(PozosSondeo) getArguments().getSerializable("pozo");
                idPozo=pozoSeleccionado.getPozo_id();
                break;

            case "recoleccion":
                recoleccionSeleccionado=(RecoleccionesSuperficiales) getArguments().getSerializable("recoleccion");
                idRecoleccion=recoleccionSeleccionado.getrecoleccion_superficial_id();

                break;
            case "perfil":
                pefilSeleccionado=(PerfilesExpuestos) getArguments().getSerializable("perfil");
                idPerfil=pefilSeleccionado.getPerfil_expuesto_id();

                break;
        }


    }
    private void configurarUrlSegunOrigen(String origen) {
        switch(origen){
            case "pozo":
                url="";
               url=getString(R.string.ip_url)+"web_services/buscar_estructuras_arqueologicas.php?pozos_pozo_id="+idPozo;
                consultarEstructurasArqueologicas();
                break;

            case "recoleccion":
                url="";
                url=getString(R.string.ip_url)+"web_services/buscar_estructuras_arqueologicas.php?recoleccion_id="+idRecoleccion;
                consultarEstructurasArqueologicas();
                break;
            case "perfil":
                url="";
                url=getString(R.string.ip_url)+"web_services/buscar_estructuras_arqueologicas.php?perfil_id="+idPerfil;
                consultarEstructurasArqueologicas();
                break;
        }
    }


    private void consultarEstructurasArqueologicas() {
        /*final ProgressDialog pDialog;
        pDialog = new ProgressDialog(getContext());
        pDialog.setMessage("Cargando...");
        pDialog.show();*/

        //String ip=getString(R.string.ip_url);


        //url=ip+url;

        System.out.println("ESTA es LA URL QUE LLEGA"+url);

        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                //pDialog.hide();

                JSONArray json=response.optJSONArray("estructuras_arqueologicas");
                JSONObject jsonObject;

                try {
                    for (int i=0;i<json.length();i++) {
                        jsonObject = json.getJSONObject(i);

                        estructura=new EstructurasArqueologicas(jsonObject.getInt("estructuras_arqueologicas_id"),jsonObject.getInt("tipos_estructuras_id"),
                                jsonObject.getString("tipos_estructuras_nombre"), jsonObject.getInt("topologias_estructuras_id"),
                                jsonObject.getString("topologias_estructuras_nombre"),idPerfil,idRecoleccion,idPozo,
                                jsonObject.getString("descripcion"),jsonObject.getString("punto_conexo"),
                                jsonObject.getLong("utmx"),jsonObject.getLong("utmy"),
                                jsonObject.getString("latitud"),jsonObject.getString("longitud"),
                                jsonObject.getString("dimension"),jsonObject.getString("entorno"),
                                jsonObject.getString("intervencion"));
                        //proyecto.setNombre(jsonObject.optString("nombre"));
                        //miUsuario.setProfesion(jsonObject.optString("profesion"));
                        //miUsuario.setRutaImagen(jsonObject.optString("ruta_imagen"));
                        //System.out.println(jsonObject.getString("nombre"));
                        listEstructurasArqueologicas.add(estructura);

                    }

                    mAdapter=new AdapterEstructurasArqueologicas(origen,listEstructurasArqueologicas, R.layout.recycler_view_estructuras_arqueologicas, new AdapterEstructurasArqueologicas.OnItemClickListener() {
                        @Override
                        public void onItemClick(EstructurasArqueologicas estructuraArqueologica, int position, String boton) {
                            System.out.println("DIOOOOO clic boton: " + boton);
                            estructura=estructuraArqueologica;
                            /*if(boton.equals("Ver")){

                                System.out.println("VEEEEERRR ESTRUCTURA");


                                System.out.println("ESTRCTURA ARQUE: "+estructura.getEstructuras_arqueologicas_id());

                                *//*argumentosEnviados= new Bundle();
                                argumentosEnviados.putSerializable("proyecto",proyectoSeleccionado);
                                argumentosEnviados.putSerializable("usuario",usuarioLogueado);
                                argumentosEnviados.putSerializable("umtp",umtpSeleccionado);
                                argumentosEnviados.putSerializable("pozo",pozoSeleccionado);
                                argumentosEnviados.putSerializable("estructura",estructura);
                                VerPozoSondeoFragment verPozo= new VerPozoSondeoFragment();
                                verPozo.setArguments(argumentosEnviados);
                                FragmentManager manager= getFragmentManager();
                                manager.beginTransaction().replace(R.id.contenidos, verPozo).commit();*//*

                                new CuadroDialogoActualizarEstructuraArqueologica(getContext(), estructura, origen,EstructurasArqueologicasFragment.this);
                            }*/

                            switch (boton)
                            {
                                case "Ver":
                                    System.out.println("VEEEEERRR ESTRUCTURA");

                                    estructura=estructuraArqueologica;
                                    System.out.println("ESTRCTURA ARQUE: "+estructura.getEstructuras_arqueologicas_id());

                                    new CuadroDialogoActualizarEstructuraArqueologica(getContext(), estructura, origen,EstructurasArqueologicasFragment.this);
                                    break;
                                case "Imagenes":

                                    System.out.println("CLIC IMAGENES ESTRUCTURAS"+origen);
                                    argumentosEnviados= new Bundle();
                                    switch(origen)
                                    {
                                        case "pozo":
                                            argumentosEnviados.putSerializable("pozo",pozoSeleccionado);
                                            System.out.println("PO SONDEOOOOOOO SELECCONADO------------------------------: "+pozoSeleccionado.getPozo_id());

                                            break;

                                        case "recoleccion":
                                            argumentosEnviados.putSerializable("recoleccion",recoleccionSeleccionado);
                                            System.out.println("RECOLECCION SELECCONADO------------------------------: "+recoleccionSeleccionado.getrecoleccion_superficial_id());

                                            break;
                                        case "perfil":
                                            argumentosEnviados.putSerializable("perfil",pefilSeleccionado);
                                            System.out.println("PERFIL SELECCONADO------------------------------: "+pefilSeleccionado.getPerfil_expuesto_id());
                                            break;
                                    }


                                    argumentosEnviados.putSerializable("proyecto",proyectoSeleccionado);
                                    argumentosEnviados.putSerializable("usuario",usuarioLogueado);
                                    argumentosEnviados.putSerializable("umtp",umtpSeleccionado);

                                    argumentosEnviados.putSerializable("estructura",estructura);
                                    argumentosEnviados.putString("origen",origen+"Estructura");
                                    ImagenesFragment imagenesUmtp= new ImagenesFragment();
                                    imagenesUmtp.setArguments(argumentosEnviados);
                                    FragmentManager manager= getFragmentManager();
                                    manager.beginTransaction().replace(R.id.contenidos, imagenesUmtp).commit();
                                    System.out.println("CLIC IMAGENES ESTRUCTURAS"+origen+"Estructura");
                                    break;

                                case "Materiales":

                                    argumentosEnviados=new Bundle();

                                    argumentosEnviados.putSerializable("proyecto",proyectoSeleccionado);
                                    argumentosEnviados.putSerializable("usuario",usuarioLogueado);
                                    argumentosEnviados.putSerializable("umtp",umtpSeleccionado);
                                    argumentosEnviados.putSerializable("estructura",estructura);
                                    argumentosEnviados.putString("origen",origen+"Estructura");
                                    switch (origen)
                                    {

                                        case "pozo":
                                            System.out.println("CLIC MATERIALES ESTRUCTURA"+origen+ proyectoSeleccionado.getNombre());

                                            argumentosEnviados.putSerializable("pozo",pozoSeleccionado);


                                            MaterialesEstructuraArqueologicaFragment materialesEstructura= new MaterialesEstructuraArqueologicaFragment();
                                            materialesEstructura.setArguments(argumentosEnviados);
                                            manager= getFragmentManager();
                                            manager.beginTransaction().replace(R.id.contenidos, materialesEstructura).commit();
                                            break;

                                        case "recoleccion":

                                            System.out.println("CLIC MATERIALES ESTRUCTURA Recoleccion"+origen+ recoleccionSeleccionado.getrecoleccion_superficial_id());
                                            argumentosEnviados.putSerializable("recoleccion",recoleccionSeleccionado);

                                            materialesEstructura= new MaterialesEstructuraArqueologicaFragment();
                                            materialesEstructura.setArguments(argumentosEnviados);
                                            manager= getFragmentManager();
                                            manager.beginTransaction().replace(R.id.contenidos, materialesEstructura).commit();
                                                break;

                                        case "perfil":

                                            System.out.println("CLIC MATERIALES ESTRUCTURA Perfil"+origen+ pefilSeleccionado.getPerfil_expuesto_id());
                                            argumentosEnviados.putSerializable("perfil",pefilSeleccionado);

                                            materialesEstructura= new MaterialesEstructuraArqueologicaFragment();
                                            materialesEstructura.setArguments(argumentosEnviados);
                                            manager= getFragmentManager();
                                            manager.beginTransaction().replace(R.id.contenidos, materialesEstructura).commit();
                                            break;
                                    }


                                    break;

                            }


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
    public void EstructuraArqueologicaActualizado(EstructurasArqueologicas estructuraArqueologica) {
        listEstructurasArqueologicas.clear();
        configurarUrlSegunOrigen(origen);
        consultarEstructurasArqueologicas();
    }



}
