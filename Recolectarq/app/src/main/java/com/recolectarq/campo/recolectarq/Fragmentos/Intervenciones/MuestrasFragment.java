package com.recolectarq.campo.recolectarq.Fragmentos.Intervenciones;

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
import com.recolectarq.campo.recolectarq.AdaptadoresRecycler.AdapterMuestras;
import com.recolectarq.campo.recolectarq.Carpetas;
import com.recolectarq.campo.recolectarq.CuadrosDialogos.CuadroDialogoActualizarEstructuraArqueologica;
import com.recolectarq.campo.recolectarq.CuadrosDialogos.CuadroDialogoActualizarMuestra;
import com.recolectarq.campo.recolectarq.Modelo.EstructurasArqueologicas;
import com.recolectarq.campo.recolectarq.Modelo.Muestras;
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

public class MuestrasFragment extends Fragment implements CuadroDialogoActualizarMuestra.FinalizarCuadroDialogo  {

    private String cedula;
    int idPozo;
    int idRecoleccion;
    int idPerfil;
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    Date fechaFin;
    Date fechaInicio;
    private ArrayList<Muestras> listMuestras;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    JsonObjectRequest jsonObjectRequest;
    FloatingActionButton fabBotonAdicionarMuestra;
    private Muestras muestra;
    private PozosSondeo pozoSeleccionado;
    private RecoleccionesSuperficiales recoleccionSeleccionado;
    private PerfilesExpuestos pefilSeleccionado;
    private Usuarios usuarioLogueado;
    private Proyectos proyectoSeleccionado;
    private Umtp umtpSeleccionado;
    private Bundle usuarioEnviado;
    private Bundle argumentosEnviados;
    private String origen;
    private String url;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments()!=null)
        {
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
            //idPozo=pozoSeleccionado.getPozo_id();

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

        View view= inflater.inflate(R.layout.fragment_muestras,container, false);

        listMuestras= new ArrayList<>();
        mRecyclerView=view.findViewById(R.id.recyclerViewMuestras);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
        fabBotonAdicionarMuestra= view.findViewById(R.id.fabBotonAdicionarMuestra);
        configurarUrlSegunOrigen(origen);
        System.out.println("El usuario Logueado es desde proyectos" + usuarioLogueado.getNombre());
        fabBotonAdicionarMuestra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("CREAR MUESTRA POZO ");
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
                CrearMuestraFragment estructuraMuestra= new CrearMuestraFragment();
                estructuraMuestra.setArguments(argumentosEnviados);
                FragmentManager manager= getFragmentManager();
                manager.beginTransaction().replace(R.id.contenidos, estructuraMuestra).commit();
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

                url=getString(R.string.ip_url)+"web_services/buscar_muestras.php?pozos_pozo_id="+idPozo;
                consultarMuestras();
                break;

            case "recoleccion":
                url=getString(R.string.ip_url)+"web_services/buscar_muestras.php?recoleccion_id="+idRecoleccion;
                consultarMuestras();
                break;
            case "perfil":

                url=getString(R.string.ip_url)+"web_services/buscar_muestras.php?perfil_id="+idPerfil;
                consultarMuestras();
                break;
        }
    }

    private void consultarMuestras() {
        /*final ProgressDialog pDialog;
        pDialog=new ProgressDialog(getContext());
        pDialog.setMessage("Cargando...");
        pDialog.show();*/


        System.out.println(url);

        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //pDialog.hide();

                JSONArray json=response.optJSONArray("muestras");
                JSONObject jsonObject;

                try {
                    for (int i=0;i<json.length();i++) {
                        jsonObject = json.getJSONObject(i);

                        muestra=new Muestras(jsonObject.getInt("muestra_id"),jsonObject.getInt("tipos_muestras_id"),
                                jsonObject.getString("tipos_muestras_nombre"), jsonObject.getInt("tipos_materiales_id"),
                                jsonObject.getString("tipos_materiales_nombre"), idPerfil,idRecoleccion,
                                idPozo,
                                jsonObject.getString("argumentacion"),jsonObject.getString("destino"),
                                jsonObject.getString("contexto"),
                                jsonObject.getString("intervencion"));
                        //proyecto.setNombre(jsonObject.optString("nombre"));
                        //miUsuario.setProfesion(jsonObject.optString("profesion"));
                        //miUsuario.setRutaImagen(jsonObject.optString("ruta_imagen"));
                        //System.out.println(jsonObject.getString("nombre"));
                        listMuestras.add(muestra);

                    }

                    mAdapter=new AdapterMuestras(origen,listMuestras, R.layout.recycler_view_muestras, new AdapterMuestras.OnItemClickListener() {
                        @Override
                        public void onItemClick(Muestras muestraP, int position, String boton) {
                            System.out.println("DIOOOOO clic boton: " + boton);
                            if(boton.equals("Ver")){

                                System.out.println("VEEEEERRR ESTRUCTURA");

                                muestra=muestraP;
                                System.out.println("MUESTRA: "+muestra.getMuestra_id());

                                /*argumentosEnviados= new Bundle();
                                argumentosEnviados.putSerializable("proyecto",proyectoSeleccionado);
                                argumentosEnviados.putSerializable("usuario",usuarioLogueado);
                                argumentosEnviados.putSerializable("umtp",umtpSeleccionado);
                                argumentosEnviados.putSerializable("pozo",pozoSeleccionado);
                                argumentosEnviados.putSerializable("estructura",estructura);
                                VerPozoSondeoFragment verPozo= new VerPozoSondeoFragment();
                                verPozo.setArguments(argumentosEnviados);
                                FragmentManager manager= getFragmentManager();
                                manager.beginTransaction().replace(R.id.contenidos, verPozo).commit();*/

                                new CuadroDialogoActualizarMuestra(getContext(), muestra, origen,MuestrasFragment.this);
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
    public void EstructuraArqueologicaActualizado(Muestras muestra) {
        listMuestras.clear();
        consultarMuestras();
    }

}
