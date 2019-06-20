package com.recolectarq.campo.recolectarq.Fragmentos.Usuarios;

import android.app.ProgressDialog;
import android.os.Bundle;
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
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.recolectarq.campo.recolectarq.AdaptadoresRecycler.AdapterUsuarios;
import com.recolectarq.campo.recolectarq.R;
import com.recolectarq.campo.recolectarq.Modelo.Usuarios;

import com.recolectarq.campo.recolectarq.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.recolectarq.campo.recolectarq.R.layout.fragment_usuarios;

public class UsuariosFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener {
    //private List<String> names;
    private ArrayList<Usuarios> listaUsuarios;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    RequestQueue request;
    JsonRequest jrq;
    FloatingActionButton botonAdicionarUsuario;

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }




    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
       // return super.onCreateView(inflater, container, savedInstanceState);


        View view = inflater.inflate(fragment_usuarios,container,false);
        //listaUsuarios=this.getAllNames();

        botonAdicionarUsuario= view.findViewById(R.id.fabBotonAdicionarUsuario);
        botonAdicionarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),"dio clic", Toast.LENGTH_LONG);
                System.out.println("si entró al clic del boon");
                CrearUsuarioFragment crearUsuario= new CrearUsuarioFragment();
                FragmentManager manager= getFragmentManager();
                manager.beginTransaction().replace(R.id.contenidos, crearUsuario).commit();
            }
        });

        listaUsuarios=new ArrayList<>();
        mRecyclerView=view.findViewById(R.id.recyclerViewUsuario);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
        request=Volley.newRequestQueue(view.getContext());
        System.out.println("Despues de crear viene acá");
        cargarWebService();
        //Toast.makeText(getContext(),"despues",Toast.LENGTH_LONG);
        //Usuarios usuario;
        //usuario=new Usuarios("f","h","j","j");

        //listaUsuarios.add(usuario);


        return view;
    }






    private void cargarWebService()
    {
        String url=R.string.ip_url+"/web_services/usuarios.php";
        System.out.println("Despues de crear viene acá 1" );
        listaUsuarios.clear();
        System.out.println("Despues de crear viene acá 2");
        jrq=new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        request.add(jrq);

    }


    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getContext(), "HUBO UN ERROR AL CONECTARSE CON LA BASE DE DATOS: "+ error.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        Usuarios usuario;
        //Toast.makeText(getContext(),"entro al servicio",Toast.LENGTH_LONG);
        System.out.println("Despues de crear viene acá 3");
        JSONArray json=response.optJSONArray("datos");
        try {
                System.out.println("la longitud de la cadena es: " + json.length());
            for (int i=0;i<json.length();i++)
            {

                JSONObject jsonObject;
                jsonObject=json.getJSONObject(i);
                usuario=new Usuarios(jsonObject.optString("usuario_id"),jsonObject.optString("nombre"),jsonObject.optString("apellido"),jsonObject.optString("contrasena"));
                usuario.setUsuario_id(jsonObject.optString("usuario_id"));
                usuario.setNombre(jsonObject.optString("nombre"));

                listaUsuarios.add(usuario);
            }

            mAdapter= new AdapterUsuarios(listaUsuarios, R.layout.recycler_view_usuario, new AdapterUsuarios.OnItemClickListener() {
                @Override
                public void onItemClick(Usuarios listaUsuarios, int position, String boton) {
                    Toast.makeText(getContext(), listaUsuarios.getNombre() + " --"+ position, Toast.LENGTH_LONG);
                    System.out.println("Entro aca" + listaUsuarios.getNombre());
                    System.out.println("Le dio clic " + boton + " a: "+ listaUsuarios.getNombre());

                    if(boton.equals("eliminar")){
                        System.out.println("clic en el BOTON" + boton+ " se eliminará a usuario" + listaUsuarios.getNombre()+ " con cedula "+ listaUsuarios.getUsuario_id());
                        webServiceEliminar(listaUsuarios.getUsuario_id());
                    }else
                    {
                        if (boton.equals("editar")){
                            System.out.println("clic en el BOTON" + boton+ " se EDITARÁ a usuario" + listaUsuarios.getNombre()+ " con cedula "+ listaUsuarios.getUsuario_id());
                            Bundle bundleUsuario= new Bundle();
                            bundleUsuario.putSerializable("usuario",listaUsuarios);

                            EditarUsuarioFragment editarUsuario= new EditarUsuarioFragment();
                            editarUsuario.setArguments(bundleUsuario);
                            FragmentManager manager= getFragmentManager();
                            manager.beginTransaction().replace(R.id.contenidos, editarUsuario).commit();
                        }
                    }

                }
            });
            mRecyclerView.setAdapter(mAdapter);
            json=null;

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getContext(),"No entro al servicio" + e.getMessage(),Toast.LENGTH_LONG);
        }


    }

    private List<String> getAllNames(){
        return new ArrayList<String>(){
            {
                add("Alejandro");
                add("Ferney");
                add("Yuliana");
                add("Maria Alejandra");

            }
        };
    }

    //Desde acá adicioné

    private ProgressDialog pDialog;
    private StringRequest stringRequest;
    private void webServiceEliminar(String cedulaUsuarioEliminar) {
        pDialog=new ProgressDialog(getContext());
        pDialog.setMessage("Cargando...");
        pDialog.show();

        String ip=getString(R.string.ip_url);

        String url=ip+"/web_services/eliminar_usuario.php?cedula="+cedulaUsuarioEliminar;

        stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pDialog.hide();

                if (response.trim().equalsIgnoreCase("elimina")){
                    //etiNombre.setText("");
                    //txtDocumento.setText("");
                    //etiProfesion.setText("");
                    //campoImagen.setImageResource(R.drawable.img_base);
                    Toast.makeText(getContext(),"Se ha Eliminado con exito",Toast.LENGTH_SHORT).show();
                    cargarWebService() ;

                }else{
                    if (response.trim().equalsIgnoreCase("noExiste")){
                        Toast.makeText(getContext(),"No se encuentra la persona ",Toast.LENGTH_SHORT).show();
                        Log.i("RESPUESTA: ",""+response);
                    }else{
                        Toast.makeText(getContext(),"No se ha Eliminado ",Toast.LENGTH_SHORT).show();
                        Log.i("RESPUESTA: ",""+response);
                    }

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),"No se ha podido conectar",Toast.LENGTH_SHORT).show();
                pDialog.hide();
            }
        });
        //request.add(stringRequest);
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(stringRequest);
    }



}
