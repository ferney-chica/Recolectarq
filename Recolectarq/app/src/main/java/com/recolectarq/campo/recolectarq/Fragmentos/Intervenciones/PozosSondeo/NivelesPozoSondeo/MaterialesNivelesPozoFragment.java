package com.recolectarq.campo.recolectarq.Fragmentos.Intervenciones.PozosSondeo.NivelesPozoSondeo;

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
import com.recolectarq.campo.recolectarq.AdaptadoresRecycler.AdapterMaterialesNiveles;
import com.recolectarq.campo.recolectarq.Carpetas;
import com.recolectarq.campo.recolectarq.CuadrosDialogos.CuadroDialogoActualizarMaterial;
import com.recolectarq.campo.recolectarq.Fragmentos.Intervenciones.CrearMaterialFragment;
import com.recolectarq.campo.recolectarq.Fragmentos.Intervenciones.PozosSondeo.VerPozoSondeoFragment;
import com.recolectarq.campo.recolectarq.Modelo.MaterialesEstratosPerfiles;
import com.recolectarq.campo.recolectarq.Modelo.MaterialesNiveles;
import com.recolectarq.campo.recolectarq.Modelo.MaterialesRecolecciones;
import com.recolectarq.campo.recolectarq.Modelo.NivelesPozos;
import com.recolectarq.campo.recolectarq.Modelo.PozosSondeo;
import com.recolectarq.campo.recolectarq.Modelo.Proyectos;
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

public class MaterialesNivelesPozoFragment extends Fragment implements CuadroDialogoActualizarMaterial.FinalizarCuadroDialogo, InicioUsuarioActivity.OnBackPressedListener {

    private String cedula;
    int niveles_pozos_nivel_pozo_id;
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    Date fechaFin;
    Date fechaInicio;
    private ArrayList<MaterialesNiveles> listMaterialesNiveles;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    JsonObjectRequest jsonObjectRequest;
    FloatingActionButton fabBotonAdicionarMaterialesNivelPozo;
    private MaterialesNiveles materialNivel;
    private Usuarios usuarioLogueado;
    private Proyectos proyectoSeleccionado;
    private Umtp umtpSeleccionado;
    private PozosSondeo pozoSeleccionado;
    private NivelesPozos nivelSeleccionado;
    private Bundle argumentosEnviados;
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
            pozoSeleccionado=(PozosSondeo) getArguments().getSerializable("pozo");
            nivelSeleccionado=(NivelesPozos) getArguments().getSerializable("nivel");
            Carpetas carpeta=new Carpetas();
            System.out.println(umtpSeleccionado.getUmtp_id()+ "DESDE MATERIALES RECOLECCION");
            System.out.println( carpeta.crearCarpeta("/Recolectarq/Proyectos/"+proyectoSeleccionado.getCodigo_identificacion()));
            carpeta.crearCarpeta("/Recolectarq/Proyectos/"+proyectoSeleccionado.getCodigo_identificacion()+"/UMTP");
            carpeta.crearCarpeta("/Recolectarq/Proyectos/"+proyectoSeleccionado.getCodigo_identificacion()+"/MemoriasTecnicas");
            int idUsuario = Integer.parseInt(usuarioLogueado.getUsuario_id());
            niveles_pozos_nivel_pozo_id=nivelSeleccionado.getNivel_pozo_id();

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
        View view= inflater.inflate(R.layout.fragment_materiales_nivel_pozo,container, false);

        listMaterialesNiveles= new ArrayList<>();
        mRecyclerView=view.findViewById(R.id.recyclerViewMaterialesNivelPozo);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
        fabBotonAdicionarMaterialesNivelPozo= view.findViewById(R.id.fabBotonAdicionarMaterialesNivelPozo);
        consultarMaterialesRecoleccionesSuperfiales();
        System.out.println("El usuario Logueado es desde proyectos" + usuarioLogueado.getNombre());
        fabBotonAdicionarMaterialesNivelPozo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                argumentosEnviados= new Bundle();
                argumentosEnviados.putSerializable("proyecto",proyectoSeleccionado);
                argumentosEnviados.putSerializable("usuario",usuarioLogueado);
                argumentosEnviados.putSerializable("umtp",umtpSeleccionado);
                argumentosEnviados.putSerializable("pozo",pozoSeleccionado);
                argumentosEnviados.putSerializable("nivel",nivelSeleccionado);

                argumentosEnviados.putString("origen","nivelpozo");
                System.out.println("ENTRO A CREAR MATERIAL NIVEL POZO///////////---------***************************");
                CrearMaterialFragment materialRecoleccion= new CrearMaterialFragment();
                materialRecoleccion.setArguments(argumentosEnviados);
                FragmentManager manager= getFragmentManager();
                manager.beginTransaction().replace(R.id.contenidos, materialRecoleccion).commit();
            }
        });

        return view;
    }



    private void consultarMaterialesRecoleccionesSuperfiales() {
        /*final ProgressDialog pDialog;
        pDialog=new ProgressDialog(getContext());
        pDialog.setMessage("Cargando...");
        pDialog.show();*/

        final String ip=getString(R.string.ip_url);


        String url=ip+"/web_services/buscar_materiales_niveles.php?niveles_pozos_nivel_pozo_id="+niveles_pozos_nivel_pozo_id;

        System.out.println(url);

        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //pDialog.hide();

                JSONArray json=response.optJSONArray("materiales_niveles");
                JSONObject jsonObject;

                try {
                    for (int i=0;i<json.length();i++) {
                        jsonObject = json.getJSONObject(i);

                        materialNivel=new MaterialesNiveles(jsonObject.getInt("material_nivel_id"), jsonObject.getInt("tipos_materiales_id"),
                                jsonObject.getString("nombre_tipo_material"),jsonObject.getInt("niveles_pozos_nivel_pozo_id"),
                                jsonObject.getInt("cantidad"), jsonObject.getString("observacion"),
                                jsonObject.getString("elemento_diagnostico"),jsonObject.getString("observacion_elemento_diagnostico"));
                        //proyecto.setNombre(jsonObject.optString("nombre"));
                        //miUsuario.setProfesion(jsonObject.optString("profesion"));
                        //miUsuario.setRutaImagen(jsonObject.optString("ruta_imagen"));
                        //System.out.println(jsonObject.getString("nombre"));
                        listMaterialesNiveles.add(materialNivel);

                    }

                    mAdapter=new AdapterMaterialesNiveles(listMaterialesNiveles, R.layout.recycler_view_materiales_nivel_pozo, new AdapterMaterialesNiveles.OnItemClickListener() {
                        @Override
                        public void onItemClick(MaterialesNiveles listMaterialNivel, int position, String boton) {

                            if(boton.equals("Ver")){


                                materialNivel=listMaterialNivel;
                                System.out.println("MATERIAL NIVEL -- ID NIVEL: "+listMaterialNivel.getNiveles_pozos_nivel_pozo_id());

                                /*argumentosEnviados= new Bundle();
                                argumentosEnviados.putSerializable("proyecto",proyectoSeleccionado);
                                argumentosEnviados.putSerializable("usuario",usuarioLogueado);
                                argumentosEnviados.putSerializable("umtp",umtpSeleccionado);
                                argumentosEnviados.putSerializable("recoleccion",recoleccionSeleccionado);
                                VerRecoleccionSuperficialFragment verRecoleccion= new VerRecoleccionSuperficialFragment();
                                verRecoleccion.setArguments(argumentosEnviados);
                                FragmentManager manager= getFragmentManager();
                                manager.beginTransaction().replace(R.id.contenidos, verRecoleccion).commit();*/
                                MaterialesRecolecciones materialeRecoleccion=new MaterialesRecolecciones();
                                MaterialesEstratosPerfiles materialEstratoPerfil=new MaterialesEstratosPerfiles();
                                new CuadroDialogoActualizarMaterial(getContext(),listMaterialNivel,materialeRecoleccion,materialEstratoPerfil,"materialNivelPozo",MaterialesNivelesPozoFragment.this);
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
    public void MaterialNivelPozoActualizado(MaterialesNiveles materialNivelPozo) {
        listMaterialesNiveles.clear();
        consultarMaterialesRecoleccionesSuperfiales();
    }

    @Override
    public void MaterialRecoleccion(MaterialesRecolecciones materialRecoleccion) {

    }

    @Override
    public void MaterialEstratoPerfil(MaterialesEstratosPerfiles materialEstratoPerfil) {


    }

    @Override
    public void doBack() {
        Bundle argumentosEnviados;
        argumentosEnviados= new Bundle();
        argumentosEnviados.putSerializable("proyecto",proyectoSeleccionado);
        argumentosEnviados.putSerializable("usuario",usuarioLogueado);
        argumentosEnviados.putSerializable("umtp",umtpSeleccionado);
        argumentosEnviados.putSerializable("pozo",pozoSeleccionado);
//System.out.println("Pozo seleccionado desde MaterialesNivel Pozo es: "+pozoSeleccionado.getPozo_id());
        VerPozoSondeoFragment verPozo= new VerPozoSondeoFragment();
        verPozo.setArguments(argumentosEnviados);
        FragmentManager manager= getFragmentManager();
        manager.beginTransaction().replace(R.id.contenidos, verPozo).commit();
    }
}
