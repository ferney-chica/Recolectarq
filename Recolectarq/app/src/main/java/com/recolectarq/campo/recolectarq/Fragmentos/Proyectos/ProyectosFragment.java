package com.recolectarq.campo.recolectarq.Fragmentos.Proyectos;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
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
import com.recolectarq.campo.recolectarq.Actividades.LogueoActivity;
import com.recolectarq.campo.recolectarq.AdaptadoresRecycler.AdapterProyectos;
import com.recolectarq.campo.recolectarq.Fragmentos.DashboardFragment;
import com.recolectarq.campo.recolectarq.Modelo.Proyectos;
import com.recolectarq.campo.recolectarq.Modelo.Usuarios;
import com.recolectarq.campo.recolectarq.R;
import com.recolectarq.campo.recolectarq.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ProyectosFragment extends Fragment implements InicioUsuarioActivity.OnBackPressedListener {

    private String cedula;
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    Date fechaFin;
    Date fechaInicio;
    private ArrayList<Proyectos> listProyectos;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    JsonObjectRequest jsonObjectRequest;
    FloatingActionButton fbtCrearProyecto;
    private Proyectos proyecto;
    private Usuarios usuarioLogueado;
    private Bundle usuarioEnviado;
    private Bundle argumentosEnviados;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments()!=null)
        {
            Toast.makeText(getContext(),"entro",Toast.LENGTH_LONG);
            Toast.makeText(getContext(),"entro",Toast.LENGTH_LONG);
            usuarioLogueado=(Usuarios) getArguments().getSerializable("usuario");
            System.out.println("El usuario Logueado es desde proyectos" + usuarioLogueado.getNombre()+ " con cedula: "+ usuarioLogueado.getUsuario_id());
            usuarioEnviado= getArguments();
            cedula=usuarioLogueado.getUsuario_id();
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

        View view= inflater.inflate(R.layout.fragment_proyectos,container, false);
        ((InicioUsuarioActivity) getActivity()).setOnBackPressedListener(this);
        listProyectos= new ArrayList<>();
        mRecyclerView=view.findViewById(R.id.recyclerViewProyecto);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
        fbtCrearProyecto= view.findViewById(R.id.fabBotonAdicionarProyecto);
        consultarProyectos();
        System.out.println("El usuario Logueado es desde proyectos" + usuarioLogueado.getNombre());
        fbtCrearProyecto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CrearProyectoFragment crearProyecto= new CrearProyectoFragment();
                crearProyecto.setArguments(usuarioEnviado);
                FragmentManager manager= getFragmentManager();
                manager.beginTransaction().replace(R.id.contenidos, crearProyecto).commit();
            }
        });

        return view;
    }



    private void consultarProyectos() {
        /*final ProgressDialog pDialog;
        pDialog=new ProgressDialog(getContext());
        pDialog.setMessage("Cargando...");
        pDialog.show();*/

        final String ip=getString(R.string.ip_url);

        String url=ip+"/web_services/proyectos.php?documento="+cedula;

        System.out.println(url);

        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //pDialog.hide();

                JSONArray json=response.optJSONArray("proyectos");
                JSONObject jsonObject;

                try {
                    for (int i=0;i<json.length();i++) {
                        jsonObject = json.getJSONObject(i);

                        fechaFin=formatter.parse(jsonObject.getString("fecha_fin"));
                        fechaInicio=formatter.parse(jsonObject.getString("fecha_inicio"));

                        proyecto=new Proyectos(jsonObject.getInt("proyecto_id"), jsonObject.getInt("usuario_creador"), jsonObject.getInt("usuarios_usuario_id"),
                                jsonObject.getString("nombre_perfil"),jsonObject.getInt("perfil_id"),jsonObject.getString("nombre_tipo_proyecto"),
                                jsonObject.getInt("tipos_proyectos_tipo_proyecto_id"), jsonObject.getString("nombre_fases_proyectos"), jsonObject.getInt("fases_proyectos_fase_proyecto_id"),
                                jsonObject.getString("nombre"), jsonObject.getString("ubicacion"), fechaInicio,
                                fechaFin, jsonObject.getString("referencias_administrativas"),
                                jsonObject.getString("aval_cientifico"),jsonObject.getString("codigo_identificacion"));
                        //proyecto.setNombre(jsonObject.optString("nombre"));
                        //miUsuario.setProfesion(jsonObject.optString("profesion"));
                        //miUsuario.setRutaImagen(jsonObject.optString("ruta_imagen"));
                        System.out.println(jsonObject.getString("nombre"));
                        listProyectos.add(proyecto);

                    }

                    mAdapter=new AdapterProyectos(listProyectos, R.layout.recycler_view_proyecto, new AdapterProyectos.OnItemClickListener() {
                        @Override
                        public void onItemClick(Proyectos listProyectos, int position, String boton) {
                            System.out.println("DIOOOOO clic boton: " + boton);
                            System.out.println("El USUARIO" + usuarioLogueado.getNombre());
                            if(boton.equals("Ver")){

                                System.out.println("VEEEEERRR");

                                System.out.println("El USUARIO" + usuarioLogueado.getNombre());
                                proyecto=listProyectos;
                                System.out.println(listProyectos.getNombre());

                                argumentosEnviados= new Bundle();
                                argumentosEnviados.putSerializable("proyecto",proyecto);
                                argumentosEnviados.putSerializable("usuario",usuarioLogueado);
                                VerProyectoFragment verProyecto= new VerProyectoFragment();
                                verProyecto.setArguments(argumentosEnviados);
                                FragmentManager manager= getFragmentManager();
                                manager.beginTransaction().replace(R.id.contenidos, verProyecto).commit();
                            }
                        }
                    });

                }  catch (JSONException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
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
        argumentosEnviados= new Bundle();
        argumentosEnviados.putSerializable("usuario",usuarioLogueado);
        DashboardFragment dashboard= new DashboardFragment();
        dashboard.setArguments(argumentosEnviados);
        FragmentManager manager= getFragmentManager();
        manager.beginTransaction().replace(R.id.contenidos, dashboard).commitAllowingStateLoss();
    }
}
