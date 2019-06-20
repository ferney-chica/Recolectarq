package com.recolectarq.campo.recolectarq.Fragmentos.Umtp;

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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.recolectarq.campo.recolectarq.AdaptadoresRecycler.AdapterUmtp;
import com.recolectarq.campo.recolectarq.Fragmentos.Intervenciones.ReporteElementosDiagnosticosFragment;
import com.recolectarq.campo.recolectarq.Modelo.Proyectos;
import com.recolectarq.campo.recolectarq.Modelo.Umtp;
import com.recolectarq.campo.recolectarq.Modelo.Usuarios;
import com.recolectarq.campo.recolectarq.R;
import com.recolectarq.campo.recolectarq.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;


public class UmtpFragment extends Fragment {


    private Usuarios usuarioLogueado;
    private Proyectos proyecto;
    private Proyectos proyectoSeleccionado;
    private Bundle usuarioEnviado;
    private TextView usuarioTexto;
    private Bundle argumentosEnviados;

    private ArrayList<Umtp> listUmtp;
    private Umtp umtp;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    JsonObjectRequest jsonObjectRequest;
    FloatingActionButton fbtCrearUmtp;
    int idUsuario;
    int idProyecto;
    int idPerfil;
    
    
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments()!=null)
        {
            Toast.makeText(getContext(),"entro",Toast.LENGTH_LONG).show();
            Toast.makeText(getContext(),"entro",Toast.LENGTH_LONG);
            usuarioLogueado=(Usuarios) getArguments().getSerializable("usuario");
            proyectoSeleccionado=(Proyectos)getArguments().getSerializable("proyecto");
            System.out.println(proyectoSeleccionado.getNombre());
            usuarioEnviado= getArguments();
            idUsuario = Integer.parseInt(usuarioLogueado.getUsuario_id());
            idProyecto=proyectoSeleccionado.getProyecto_id();
            idPerfil=proyectoSeleccionado.getPerfil_id();
        }else{
            usuarioLogueado=new Usuarios("null","null", "null","null");
            Toast.makeText(getContext(),"NO entroOOOOOOOO",Toast.LENGTH_LONG).show();
            usuarioLogueado.setNombre("No hay usuario logueado");

        }
    }




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       // return super.onCreateView(inflater, container, savedInstanceState);

        final View v=inflater.inflate(R.layout.fragment_umtp,container, false);
        usuarioTexto=v.findViewById(R.id.textViewUsuarioUmtp);
        usuarioTexto.setText(usuarioLogueado.getUsuario_id());
        usuarioTexto.setText(String.valueOf(idProyecto));
        usuarioTexto.setText(proyectoSeleccionado.getNombre());
        Toast.makeText(getContext(),"Proyecto CODIGO desde UMTP  "+ idProyecto,Toast.LENGTH_LONG).show();
        Toast.makeText(getContext(), String.valueOf(idProyecto),Toast.LENGTH_LONG).show();
        listUmtp = new ArrayList<>();
        mRecyclerView=v.findViewById(R.id.recyclerViewUmtp);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
        fbtCrearUmtp= v.findViewById(R.id.fabBotonAdicionarUmtp);
        consultarUmtp();
        configurarSegunPerfil();
        fbtCrearUmtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),proyectoSeleccionado.getNombre()+" "+ proyectoSeleccionado.getProyecto_id()+" "+proyectoSeleccionado.getProyecto_id(),Toast.LENGTH_LONG).show();
                System.out.println("CLICBOTONCREARUMTP");
                argumentosEnviados= new Bundle();
                argumentosEnviados.putSerializable("proyecto",proyectoSeleccionado);
                argumentosEnviados.putSerializable("usuario",usuarioLogueado);
                System.out.println(proyectoSeleccionado.getNombre()+" "+ proyectoSeleccionado.getProyecto_id()+" "+usuarioLogueado.getNombre());
                CrearUmtpFragment umtp= new CrearUmtpFragment();
                FragmentManager manager= getFragmentManager();
                umtp.setArguments(argumentosEnviados);
                manager.beginTransaction().replace(R.id.contenidos, umtp).commit();

            }
        });

        
        return v;
    }

    private void configurarSegunPerfil()
    {
        if(idPerfil==3)
        {
            fbtCrearUmtp.setEnabled(false);
        }
    }

    private void consultarUmtp() {
        /*final ProgressDialog pDialog;
        pDialog=new ProgressDialog(getContext());
        pDialog.setMessage("Cargando...");
        pDialog.show();*/

        final String ip=getString(R.string.ip_url);

        String url=ip+"/web_services/umtp.php?proyecto="+idProyecto;

        System.out.println(url);

        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //pDialog.hide();

                JSONArray json=response.optJSONArray("umtp");
                JSONObject jsonObject;

                try {
                    jsonObject = json.getJSONObject(0);
                    if(jsonObject.getInt("umtp_id")==-1){
                        umtp = new Umtp(0,0,0,0,0,
                                0,0,new BigDecimal("0"),new BigDecimal("0"),new BigDecimal("0"),
                                0,new BigDecimal("0"),new BigDecimal("0"),"--",
                                "--","--","--","--","--","--",
                                "--","--","0","0",-1,0);
                        listUmtp.add(umtp);
                    }else {
                        for (int i = 0; i < json.length(); i++) {
                            jsonObject = json.getJSONObject(i);

                            umtp = new Umtp(jsonObject.getInt("umtp_id"), jsonObject.getInt("tipos_relieves_id"),
                                    jsonObject.getInt("geoforma_id"), jsonObject.getInt("vegetaciones_id"),
                                    jsonObject.getInt("dedicaciones_entornos_id"), jsonObject.getInt("proyectos_proyecto_id"),
                                    jsonObject.getInt("numero"),
                                    new BigDecimal(json.getJSONObject(i).get("largo").toString()), new BigDecimal(json.getJSONObject(i).get("ancho").toString()), new BigDecimal(json.getJSONObject(i).get("area").toString()),
                                    jsonObject.getInt("altura"), new BigDecimal(json.getJSONObject(i).get("utmx").toString()), new BigDecimal(json.getJSONObject(i).get("utmy").toString()),
                                    jsonObject.getString("latitud"),jsonObject.getString("longitud"), jsonObject.getString("municipio"),
                                    jsonObject.getString("departamento"),jsonObject.getString("vereda"),
                                    jsonObject.getString("sector"), jsonObject.getString("accesos"), jsonObject.getString("zona_incluyente"),
                                    jsonObject.getString("codigo_rotulo"),
                                    jsonObject.getString("yacimiento"), jsonObject.getString("sitio_potencial"),
                                    jsonObject.getInt("usuarios_usuario_id"),jsonObject.getInt("yacimiento_seccion")
                            );
                            System.out.println("El area es : "+ umtp.getLargo().multiply(umtp.getAncho()));
                            //proyecto.setNombre(jsonObject.optString("nombre"));
                            //miUsuario.setProfesion(jsonObject.optString("profesion"));
                            //miUsuario.setRutaImagen(jsonObject.optString("ruta_imagen"));
                            //System.out.println(jsonObject.getString("nombre"));
                            listUmtp.add(umtp);

                        }
                    }
                    mAdapter=new AdapterUmtp(listUmtp, R.layout.recycler_view_umtp, new AdapterUmtp.OnItemClickListener() {
                        @Override
                        public void onItemClick(Umtp listUmtp, int position, String boton) {
                            System.out.println("DIOOOOO clic boton: " + boton+ " DESDE VER PROYECTO");
                            System.out.println("El USUARIO" + usuarioLogueado.getNombre());
                            /*if(boton.equals("editar")){

                                System.out.println("VEEEEERRR");

                                System.out.println("El USUARIO" + usuarioLogueado.getNombre());
                                umtp=listUmtp;
                                System.out.println("LA UTMx AL LLEVARLA A VER  : "+ umtp.getUtmx());

                                argumentosEnviados= new Bundle();
                                argumentosEnviados.putSerializable("proyecto",proyectoSeleccionado);
                                argumentosEnviados.putSerializable("usuario",usuarioLogueado);
                                argumentosEnviados.putSerializable("umtp",umtp);
                                VerUmtpFragment verUmtp= new VerUmtpFragment();
                                verUmtp.setArguments(argumentosEnviados);
                                FragmentManager manager= getFragmentManager();
                                manager.beginTransaction().replace(R.id.contenidos, verUmtp).commit();
                            }*/
                            umtp=listUmtp;
                            FragmentManager manager= getFragmentManager();
                            switch(boton)
                            {
                                case "editar":

                                    System.out.println("VEEEEERRR");

                                    System.out.println("El USUARIO" + usuarioLogueado.getNombre());
                                    //umtp=listUmtp;
                                    System.out.println("LA UTMx AL LLEVARLA A VER  : "+ umtp.getUtmx());

                                    argumentosEnviados= new Bundle();
                                    argumentosEnviados.putSerializable("proyecto",proyectoSeleccionado);
                                    argumentosEnviados.putSerializable("usuario",usuarioLogueado);
                                    argumentosEnviados.putSerializable("umtp",umtp);
                                    VerUmtpFragment verUmtp= new VerUmtpFragment();
                                    verUmtp.setArguments(argumentosEnviados);
                                    //FragmentManager manager= getFragmentManager();
                                    manager.beginTransaction().replace(R.id.contenidos, verUmtp).commit();

                                   break;

                                case "ElementosDiagnosticos":

                                    System.out.println("CLIC ELEMENTOS DIAGNOSTICOS");
                                    argumentosEnviados= new Bundle();
                                    argumentosEnviados.putSerializable("proyecto",proyectoSeleccionado);
                                    argumentosEnviados.putSerializable("usuario",usuarioLogueado);
                                    argumentosEnviados.putSerializable("umtp",umtp);
                                    ReporteElementosDiagnosticosFragment elemento= new ReporteElementosDiagnosticosFragment();
                                    elemento.setArguments(argumentosEnviados);
                                    //FragmentManager manager= getFragmentManager();
                                    manager.beginTransaction().replace(R.id.contenidos, elemento).commit();
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
}
