package com.recolectarq.campo.recolectarq.Fragmentos.ArchivosProyecto;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
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
import com.recolectarq.campo.recolectarq.AdaptadoresRecycler.AdapterUsuariosProyectos;
import com.recolectarq.campo.recolectarq.Modelo.Proyectos;
import com.recolectarq.campo.recolectarq.Modelo.Usuarios;
import com.recolectarq.campo.recolectarq.Modelo.UsuariosProyectos;
import com.recolectarq.campo.recolectarq.R;
import com.recolectarq.campo.recolectarq.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ArchivosProyectosFragment extends Fragment {

    private int idProyecto;
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    Date fechaFin;
    Date fechaInicio;
    private ArrayList<UsuariosProyectos> listUsuariosProyectos;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    JsonObjectRequest jsonObjectRequest;
    FloatingActionButton fbtAdicionarUsuarioProyecto;

    private UsuariosProyectos usuarioProyecto;
    private Usuarios usuarioLogueado;
    private Proyectos proyectoSeleccionado;
    private Bundle usuarioEnviado;
    private Bundle argumentosEnviados;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments()!=null)
        {
            Toast.makeText(getContext(),"entro a EDITAR",Toast.LENGTH_LONG);
            Toast.makeText(getContext(),"entro",Toast.LENGTH_LONG);
            usuarioLogueado=(Usuarios) getArguments().getSerializable("usuario");
            proyectoSeleccionado=(Proyectos)getArguments().getSerializable("proyecto");
            usuarioEnviado= getArguments();
            int idUsuario = Integer.parseInt(usuarioLogueado.getUsuario_id());
            idProyecto=proyectoSeleccionado.getProyecto_id();

            System.out.println("El usuario Logueado es desde editar es proyectos" + usuarioLogueado.getNombre() + "con cedula "+ idUsuario);
            System.out.println("Entro a editar PROYECTO"+usuarioLogueado);
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

        View view= inflater.inflate(R.layout.fragment_usuarios_proyectos,container, false);

        listUsuariosProyectos= new ArrayList<>();
        mRecyclerView=view.findViewById(R.id.recyclerViewUsuariosProyecto);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
        fbtAdicionarUsuarioProyecto= view.findViewById(R.id.fabBotonAdicionarUsuarioProyecto);
        consultarUsuariosProyectos();
        System.out.println("El usuario Logueado es desde proyectos" + usuarioLogueado.getNombre());
        fbtAdicionarUsuarioProyecto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("CLIC EN BOTON CREAR PROYECTO");
                /*CrearProyectoFragment crearProyecto= new CrearProyectoFragment();
                crearProyecto.setArguments(usuarioEnviado);
                FragmentManager manager= getFragmentManager();
                manager.beginTransaction().replace(R.id.contenidos, crearProyecto).commit();*/
            }
        });

        return view;
    }



    private void consultarUsuariosProyectos() {
        final ProgressDialog pDialog;
        pDialog=new ProgressDialog(getContext());
        pDialog.setMessage("Cargando...");
        pDialog.show();
        Toast.makeText(this.getContext(),"ESTE ES EL ID DEL PROYECTO "+idProyecto,Toast.LENGTH_LONG).show();
        final String ip=getString(R.string.ip_url);

        String url=ip+"/web_services/buscar_usuarios_proyectos.php?proyecto="+idProyecto;
        Toast.makeText(this.getContext(),url,Toast.LENGTH_LONG).show();
        System.out.println(url);

        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                pDialog.hide();

                JSONArray json=response.optJSONArray("usuarios_proyectos");
                JSONObject jsonObject;

                try {
                    for (int i=0;i<json.length();i++) {
                        jsonObject = json.getJSONObject(i);

                        //fechaFin=formatter.parse(jsonObject.getString("fecha_fin"));
                        //fechaInicio=formatter.parse(jsonObject.getString("fecha_inicio"));

                        usuarioProyecto=new UsuariosProyectos(jsonObject.getInt("proyecto_id"), jsonObject.getInt("usuario_id"), jsonObject.getInt("perfil_id"),
                                jsonObject.getString("proyecto_nombre"), jsonObject.getString("usuario_nombre") , jsonObject.getString("usuario_apellido"),
                                jsonObject.getString("perfil_descripcion"));
                        //proyecto.setNombre(jsonObject.optString("nombre"))
                        //miUsuario.setProfesion(jsonObject.optString("profesion"));
                        //miUsuario.setRutaImagen(jsonObject.optString("ruta_imagen"));
                        //System.out.println(jsonObject.getString("nombre"));

                        Toast.makeText(getContext(),usuarioProyecto.getProyecto_nombre()+"  "+ usuarioProyecto.getUsuario_nombre(),Toast.LENGTH_LONG).show();
                        listUsuariosProyectos.add(usuarioProyecto);

                    }

                    mAdapter=new AdapterUsuariosProyectos(listUsuariosProyectos, R.layout.recycler_view_usuario_proyecto, new AdapterUsuariosProyectos.OnItemClickListener() {
                        @Override
                        public void onItemClick(UsuariosProyectos listUsuariosProyectos, int position, String boton) {
                            System.out.println("DIOOOOO clic boton: " + boton);
                            System.out.println("El USUARIO" + usuarioLogueado.getNombre());
                            if(boton.equals("Ver")){

                                System.out.println("VEEEEERRR");

                                /*System.out.println("El USUARIO" + usuarioLogueado.getNombre());
                                proyecto=listProyectos;
                                System.out.println(listProyectos.getNombre());

                                argumentosEnviados= new Bundle();
                                argumentosEnviados.putSerializable("proyecto",proyecto);
                                argumentosEnviados.putSerializable("usuario",usuarioLogueado);
                                VerProyectoFragment verProyecto= new VerProyectoFragment();
                                verProyecto.setArguments(argumentosEnviados);
                                FragmentManager manager= getFragmentManager();
                                manager.beginTransaction().replace(R.id.contenidos, verProyecto).commit();*/
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
                pDialog.hide();
                Log.d("ERROR: ", error.toString());
            }
        });

        // request.add(jsonObjectRequest);
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(jsonObjectRequest);
    }
}
