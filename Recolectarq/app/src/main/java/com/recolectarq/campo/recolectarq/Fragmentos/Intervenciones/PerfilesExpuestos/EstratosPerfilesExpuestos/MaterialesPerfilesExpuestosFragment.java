package com.recolectarq.campo.recolectarq.Fragmentos.Intervenciones.PerfilesExpuestos.EstratosPerfilesExpuestos;

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
import com.recolectarq.campo.recolectarq.AdaptadoresRecycler.AdapterMaterialesEstratosPerfiles;
import com.recolectarq.campo.recolectarq.AdaptadoresRecycler.AdapterMaterialesRecolecciones;
import com.recolectarq.campo.recolectarq.Carpetas;
import com.recolectarq.campo.recolectarq.CuadrosDialogos.CuadroDialogoActualizarMaterial;
import com.recolectarq.campo.recolectarq.Fragmentos.Intervenciones.CrearMaterialFragment;
import com.recolectarq.campo.recolectarq.Fragmentos.Intervenciones.PerfilesExpuestos.VerPerfilExpuestoFragment;
import com.recolectarq.campo.recolectarq.Modelo.EstratosPerfiles;
import com.recolectarq.campo.recolectarq.Modelo.MaterialesEstratosPerfiles;
import com.recolectarq.campo.recolectarq.Modelo.MaterialesNiveles;
import com.recolectarq.campo.recolectarq.Modelo.MaterialesRecolecciones;
import com.recolectarq.campo.recolectarq.Modelo.PerfilesExpuestos;
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

public class MaterialesPerfilesExpuestosFragment extends Fragment implements CuadroDialogoActualizarMaterial.FinalizarCuadroDialogo, InicioUsuarioActivity.OnBackPressedListener{

    private String cedula;
    int estratos_perfiles_estrato_perfil_id;
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    Date fechaFin;
    Date fechaInicio;
    private ArrayList<MaterialesEstratosPerfiles> listMaterialesEstratoPerfil;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    JsonObjectRequest jsonObjectRequest;
    FloatingActionButton fabBotonAdicionarMaterialesEstratoPerfil;
    private MaterialesEstratosPerfiles materialEstratoPerfil;
    private Usuarios usuarioLogueado;
    private Proyectos proyectoSeleccionado;
    private Umtp umtpSeleccionado;
    private PerfilesExpuestos perfilSeleccionado;
    private EstratosPerfiles estratoPerfilSeleccionado;
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
            perfilSeleccionado=(PerfilesExpuestos) getArguments().getSerializable("perfil");
            estratoPerfilSeleccionado=(EstratosPerfiles) getArguments().getSerializable("estratoPerfil");
            Carpetas carpeta=new Carpetas();
            System.out.println(umtpSeleccionado.getUmtp_id()+ "DESDE MATERIALES RECOLECCION");
            System.out.println( carpeta.crearCarpeta("/Recolectarq/Proyectos/"+proyectoSeleccionado.getCodigo_identificacion()));
            carpeta.crearCarpeta("/Recolectarq/Proyectos/"+proyectoSeleccionado.getCodigo_identificacion()+"/UMTP");
            carpeta.crearCarpeta("/Recolectarq/Proyectos/"+proyectoSeleccionado.getCodigo_identificacion()+"/MemoriasTecnicas");
            int idUsuario = Integer.parseInt(usuarioLogueado.getUsuario_id());
            estratos_perfiles_estrato_perfil_id=estratoPerfilSeleccionado.getEstrato_perfil_id();

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
        View view= inflater.inflate(R.layout.fragment_materiales_estrato_perfil,container, false);

        listMaterialesEstratoPerfil= new ArrayList<>();
        mRecyclerView=view.findViewById(R.id.recyclerViewMaterialesEstratoPerfil);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
        fabBotonAdicionarMaterialesEstratoPerfil= view.findViewById(R.id.fabBotonAdicionarMaterialesEstratoPerfil);
        consultarMaterialesEstratoPerfil();
        System.out.println("El usuario Logueado es desde proyectos" + usuarioLogueado.getNombre());
        fabBotonAdicionarMaterialesEstratoPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                argumentosEnviados= new Bundle();
                argumentosEnviados.putSerializable("proyecto",proyectoSeleccionado);
                argumentosEnviados.putSerializable("usuario",usuarioLogueado);
                argumentosEnviados.putSerializable("umtp",umtpSeleccionado);
                argumentosEnviados.putSerializable("perfil",perfilSeleccionado);
                argumentosEnviados.putSerializable("estratoPerfil",estratoPerfilSeleccionado);

                argumentosEnviados.putString("origen","estratoPerfil");
                System.out.println("ENTRO A CREAR MATERIAL ESTRATO PERFIL///////////---------***************************");
                CrearMaterialFragment materialEstratoPerfil= new CrearMaterialFragment();
                materialEstratoPerfil.setArguments(argumentosEnviados);
                FragmentManager manager= getFragmentManager();
                manager.beginTransaction().replace(R.id.contenidos, materialEstratoPerfil).commit();
            }
        });

        return view;
    }



    private void consultarMaterialesEstratoPerfil() {
        /*final ProgressDialog pDialog;
        pDialog=new ProgressDialog(getContext());
        pDialog.setMessage("Cargando...");
        pDialog.show();*/

        final String ip=getString(R.string.ip_url);


        String url=ip+"/web_services/buscar_materiales_estrato_perfil.php?estratos_perfiles_estrato_perfil_id="+estratos_perfiles_estrato_perfil_id;

        System.out.println(url);

        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //pDialog.hide();

                JSONArray json=response.optJSONArray("materiales_estrato_perfil");
                JSONObject jsonObject;

                try {
                    for (int i=0;i<json.length();i++) {
                        jsonObject = json.getJSONObject(i);

                        materialEstratoPerfil=new MaterialesEstratosPerfiles(jsonObject.getInt("material_estrato_perfil_id"), jsonObject.getInt("estratos_perfiles_estrato_perfil_id"),
                                jsonObject.getInt("tipos_materiales_id"), jsonObject.getString("tipos_materiales_nombre"),
                                jsonObject.getInt("cantidad"), jsonObject.getString("observacion"),
                                jsonObject.getString("elemento_diagnostico"),jsonObject.getString("observacion_elemento_diagnostico"));
                        //proyecto.setNombre(jsonObject.optString("nombre"));
                        //miUsuario.setProfesion(jsonObject.optString("profesion"));
                        //miUsuario.setRutaImagen(jsonObject.optString("ruta_imagen"));
                        //System.out.println(jsonObject.getString("nombre"));
                        listMaterialesEstratoPerfil.add(materialEstratoPerfil);

                    }

                    mAdapter=new AdapterMaterialesEstratosPerfiles(listMaterialesEstratoPerfil, R.layout.recycler_view_materiales_estrato_perfil, new AdapterMaterialesEstratosPerfiles.OnItemClickListener() {
                        @Override
                        public void onItemClick(MaterialesEstratosPerfiles listMaterialEstratoPerfil, int position, String boton) {
                            System.out.println("DIOOOOO clic boton: " + boton);

                            if(boton.equals("Ver")){

                                System.out.println("VEEEEERRR");
                                materialEstratoPerfil=listMaterialEstratoPerfil;
                                System.out.println("ESTRATO PERFIL: "+materialEstratoPerfil.getEstratos_perfiles_estrato_perfil_id());

                                /*argumentosEnviados= new Bundle();
                                argumentosEnviados.putSerializable("proyecto",proyectoSeleccionado);
                                argumentosEnviados.putSerializable("usuario",usuarioLogueado);
                                argumentosEnviados.putSerializable("umtp",umtpSeleccionado);
                                argumentosEnviados.putSerializable("recoleccion",recoleccionSeleccionado);
                                VerRecoleccionSuperficialFragment verRecoleccion= new VerRecoleccionSuperficialFragment();
                                verRecoleccion.setArguments(argumentosEnviados);
                                FragmentManager manager= getFragmentManager();
                                manager.beginTransaction().replace(R.id.contenidos, verRecoleccion).commit();*/
                                MaterialesNiveles materialeNiveles=new MaterialesNiveles();
                                MaterialesRecolecciones materialesRecolecciones=new MaterialesRecolecciones();
                                new CuadroDialogoActualizarMaterial(getContext(),materialeNiveles, materialesRecolecciones,materialEstratoPerfil,"materialEstratoPerfil",MaterialesPerfilesExpuestosFragment.this);

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

    }

    @Override
    public void MaterialRecoleccion(MaterialesRecolecciones materialRecoleccion) {


    }

    @Override
    public void MaterialEstratoPerfil(MaterialesEstratosPerfiles materialEstratoPerfil) {
        listMaterialesEstratoPerfil.clear();
        consultarMaterialesEstratoPerfil();

    }

    @Override
    public void doBack() {
        argumentosEnviados= new Bundle();
        argumentosEnviados.putSerializable("proyecto",proyectoSeleccionado);
        argumentosEnviados.putSerializable("usuario",usuarioLogueado);
        argumentosEnviados.putSerializable("umtp",umtpSeleccionado);
        argumentosEnviados.putSerializable("perfil",perfilSeleccionado);

        argumentosEnviados.putString("origen","estratoPerfil");
        VerPerfilExpuestoFragment perfil= new VerPerfilExpuestoFragment();
        perfil.setArguments(argumentosEnviados);
        FragmentManager manager= getFragmentManager();
        manager.beginTransaction().replace(R.id.contenidos, perfil).commit();
    }
}
