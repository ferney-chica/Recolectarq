package com.recolectarq.campo.recolectarq.Fragmentos.Intervenciones.PozosSondeo.EstratosPozoSondeo;

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
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.recolectarq.campo.recolectarq.AdaptadoresRecycler.AdapterEstratosPozosSondeo;
import com.recolectarq.campo.recolectarq.Carpetas;
import com.recolectarq.campo.recolectarq.CuadrosDialogos.CuadroDialogoActualizarEstrato;
import com.recolectarq.campo.recolectarq.Fragmentos.ImagenesFragment;
import com.recolectarq.campo.recolectarq.Fragmentos.Intervenciones.CrearEstratoFragment;
import com.recolectarq.campo.recolectarq.Modelo.EstratosPerfiles;
import com.recolectarq.campo.recolectarq.Modelo.EstratosPozos;
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

public class EstratosPozosSondeoFragment extends Fragment implements CuadroDialogoActualizarEstrato.FinalizarCuadroDialogo{

    private String cedula;
    int idPozo;
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    Date fechaFin;
    Date fechaInicio;
    private ArrayList<EstratosPozos> listEstratosPozos;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    JsonObjectRequest jsonObjectRequest;
    FloatingActionButton fbtCrearEstratoPozo;
    private ImageButton imageButtonImagenesEstratosPozo;

    private EstratosPozos estratoPozo;
    private Usuarios usuarioLogueado;
    private Proyectos proyectoSeleccionado;
    private Umtp umtpSeleccionado;
    private PozosSondeo pozoSeleccionado;
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
            proyectoSeleccionado=(Proyectos)getArguments().getSerializable("proyecto");
            umtpSeleccionado=(Umtp) getArguments().getSerializable("umtp");
            pozoSeleccionado=(PozosSondeo) getArguments().getSerializable("pozo");
            usuarioEnviado= getArguments();
            Carpetas carpeta=new Carpetas();
            System.out.println(umtpSeleccionado.getUmtp_id()+ "DESDE POZO SONDEO");
            System.out.println( carpeta.crearCarpeta("/Recolectarq/Proyectos/"+proyectoSeleccionado.getCodigo_identificacion()));
            carpeta.crearCarpeta("/Recolectarq/Proyectos/"+proyectoSeleccionado.getCodigo_identificacion()+"/UMTP");
            carpeta.crearCarpeta("/Recolectarq/Proyectos/"+proyectoSeleccionado.getCodigo_identificacion()+"/MemoriasTecnicas");
            int idUsuario = Integer.parseInt(usuarioLogueado.getUsuario_id());
            idPozo=pozoSeleccionado.getPozo_id();

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

        View view= inflater.inflate(R.layout.fragment_estratos_pozo,container, false);

        listEstratosPozos= new ArrayList<>();
        mRecyclerView=view.findViewById(R.id.recyclerViewEstratosPozosSondeo);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
        fbtCrearEstratoPozo= view.findViewById(R.id.fabBotonAdicionarEstratoPozoSondeo);
        imageButtonImagenesEstratosPozo= view.findViewById(R.id.imageButtonImagenesEstratosPozo);
        consultarEstratosPozosSondeo();
        System.out.println("El usuario Logueado es desde proyectos" + usuarioLogueado.getNombre());
        fbtCrearEstratoPozo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                argumentosEnviados= new Bundle();
                argumentosEnviados.putSerializable("proyecto",proyectoSeleccionado);
                argumentosEnviados.putSerializable("usuario",usuarioLogueado);
                argumentosEnviados.putSerializable("umtp",umtpSeleccionado);
                argumentosEnviados.putSerializable("pozo",pozoSeleccionado);
                argumentosEnviados.putString("origen","pozo");
                //System.out.println(proyectoSelecionado.getNombre() + "desde adaptador UMTP para crear el fragment");
                CrearEstratoFragment estratoPozo= new CrearEstratoFragment();
                estratoPozo.setArguments(argumentosEnviados);
                FragmentManager manager= getFragmentManager();
                manager.beginTransaction().replace(R.id.contenidos, estratoPozo).commit();
            }
        });

        imageButtonImagenesEstratosPozo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("CLICK IMAGENES ESTRATO");
                System.out.println("PO SONDEOOOOOOO SELECCONADO------------------------------: "+pozoSeleccionado.getPozo_id());
                argumentosEnviados= new Bundle();
                argumentosEnviados.putSerializable("proyecto",proyectoSeleccionado);
                argumentosEnviados.putSerializable("usuario",usuarioLogueado);
                argumentosEnviados.putSerializable("umtp",umtpSeleccionado);
                argumentosEnviados.putSerializable("pozo",pozoSeleccionado);
                argumentosEnviados.putString("origen","estratoPozo");
                ImagenesFragment imagenesUmtp= new ImagenesFragment();
                imagenesUmtp.setArguments(argumentosEnviados);
                FragmentManager manager= getFragmentManager();
                manager.beginTransaction().replace(R.id.contenidos, imagenesUmtp).commit();
            }
        });

        return view;
    }



    private void consultarEstratosPozosSondeo() {
        /*final ProgressDialog pDialog;
        pDialog=new ProgressDialog(getContext());
        pDialog.setMessage("Cargando...");
        pDialog.show();*/

        final String ip=getString(R.string.ip_url);


        String url=ip+"/web_services/buscar_estratos_pozo_sondeo.php?pozos_pozo_id="+idPozo;

        System.out.println(url);

        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //pDialog.hide();

                JSONArray json=response.optJSONArray("estratos_pozos");
                JSONObject jsonObject;

                try {
                    for (int i=0;i<json.length();i++) {
                        jsonObject = json.getJSONObject(i);

                        estratoPozo=new EstratosPozos(jsonObject.getInt("estrato_pozo_id"), jsonObject.getInt("texturas_estratos_textura_estrato_id"),
                                jsonObject.getInt("tipos_estratos_tipo_estrato_id"), jsonObject.getInt("estructuras_estratos_estructura_estrato_id"),
                                jsonObject.getInt("pozos_pozo_id"), jsonObject.getLong("profundidad"),
                                jsonObject.getString("color"), jsonObject.getString("observacion"));
                        //proyecto.setNombre(jsonObject.optString("nombre"));
                        //miUsuario.setProfesion(jsonObject.optString("profesion"));
                        //miUsuario.setRutaImagen(jsonObject.optString("ruta_imagen"));
                        //System.out.println(jsonObject.getString("nombre"));

                        if(estratoPozo.getEstrato_pozo_id()==0)
                        {
                            imageButtonImagenesEstratosPozo.setEnabled(false);
                        }

                        listEstratosPozos.add(estratoPozo);

                    }

                    mAdapter=new AdapterEstratosPozosSondeo(listEstratosPozos, R.layout.recycler_view_estratos_pozos_sondeo, new AdapterEstratosPozosSondeo.OnItemClickListener() {
                        @Override
                        public void onItemClick(EstratosPozos listEstratoPozo, int position, String boton) {

                            if(boton.equals("Ver")){

                                System.out.println("VEEEEERRR ESTRATO POZO");
                                estratoPozo=listEstratoPozo;

                                /*argumentosEnviados= new Bundle();
                                argumentosEnviados.putSerializable("proyecto",proyectoSeleccionado);
                                argumentosEnviados.putSerializable("usuario",usuarioLogueado);
                                argumentosEnviados.putSerializable("umtp",umtpSeleccionado);
                                argumentosEnviados.putSerializable("pozo",pozoSeleccionado);
                                VerPozoSondeoFragment verPozo= new VerPozoSondeoFragment();
                                verPozo.setArguments(argumentosEnviados);
                                FragmentManager manager= getFragmentManager();
                                manager.beginTransaction().replace(R.id.contenidos, verPozo).commit();*/
                                EstratosPerfiles estratoPerfil=new EstratosPerfiles();
                                new CuadroDialogoActualizarEstrato(getContext(),estratoPozo,estratoPerfil,"estratopozo",EstratosPozosSondeoFragment.this);
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
    public void EstratoPozoActualizado(EstratosPozos nivelPozo) {
        listEstratosPozos.clear();
        consultarEstratosPozosSondeo();
    }

    @Override
    public void EstratoPerfilActualizado(EstratosPerfiles estratoPerfil) {

    }
}
