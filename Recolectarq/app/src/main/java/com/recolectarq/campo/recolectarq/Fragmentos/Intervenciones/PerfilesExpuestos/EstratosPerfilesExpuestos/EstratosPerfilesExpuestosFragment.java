package com.recolectarq.campo.recolectarq.Fragmentos.Intervenciones.PerfilesExpuestos.EstratosPerfilesExpuestos;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.recolectarq.campo.recolectarq.AdaptadoresRecycler.AdapterEstratosPerfilesExpuestos;
import com.recolectarq.campo.recolectarq.AdaptadoresRecycler.AdapterEstratosPozosSondeo;
import com.recolectarq.campo.recolectarq.Carpetas;
import com.recolectarq.campo.recolectarq.CuadrosDialogos.CuadroDialogoActualizarEstrato;
import com.recolectarq.campo.recolectarq.Fragmentos.ImagenesFragment;
import com.recolectarq.campo.recolectarq.Fragmentos.Intervenciones.CrearEstratoFragment;
import com.recolectarq.campo.recolectarq.Fragmentos.Intervenciones.PozosSondeo.NivelesPozoSondeo.NivelesPozosSondeoFragment;
import com.recolectarq.campo.recolectarq.Modelo.EstratosPerfiles;
import com.recolectarq.campo.recolectarq.Modelo.EstratosPozos;
import com.recolectarq.campo.recolectarq.Modelo.PerfilesExpuestos;
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
import java.util.HashMap;
import java.util.Map;

public class EstratosPerfilesExpuestosFragment extends Fragment implements CuadroDialogoActualizarEstrato.FinalizarCuadroDialogo{

    private String cedula;
    int idPerfil;
    int posicion;
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    Date fechaFin;
    Date fechaInicio;
    private ArrayList<EstratosPerfiles> listEstratosPerfil;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    JsonObjectRequest jsonObjectRequest;
    FloatingActionButton fabBotonAdicionarEstratoPerfilExpuesto;
    private EstratosPerfiles estratoPerfil;
    private Usuarios usuarioLogueado;
    private Proyectos proyectoSeleccionado;
    private Umtp umtpSeleccionado;
    private PerfilesExpuestos perfilSeleccionado;
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
            perfilSeleccionado=(PerfilesExpuestos) getArguments().getSerializable("perfil");
            usuarioEnviado= getArguments();
            Carpetas carpeta=new Carpetas();
            System.out.println(umtpSeleccionado.getUmtp_id()+ "DESDE POZO SONDEO");
            System.out.println( carpeta.crearCarpeta("/Recolectarq/Proyectos/"+proyectoSeleccionado.getCodigo_identificacion()));
            carpeta.crearCarpeta("/Recolectarq/Proyectos/"+proyectoSeleccionado.getCodigo_identificacion()+"/UMTP");
            carpeta.crearCarpeta("/Recolectarq/Proyectos/"+proyectoSeleccionado.getCodigo_identificacion()+"/MemoriasTecnicas");
            int idUsuario = Integer.parseInt(usuarioLogueado.getUsuario_id());
            idPerfil=perfilSeleccionado.getPerfil_expuesto_id();

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

        View view= inflater.inflate(R.layout.fragment_estratos_perfil,container, false);

        listEstratosPerfil= new ArrayList<>();
        mRecyclerView=view.findViewById(R.id.recyclerViewEstratosPerfilExpuesto);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
        fabBotonAdicionarEstratoPerfilExpuesto= view.findViewById(R.id.fabBotonAdicionarEstratoPerfilExpuesto);
        consultarEstratosPerfilExpuesto();
        System.out.println("El usuario Logueado es desde proyectos" + usuarioLogueado.getNombre());
        fabBotonAdicionarEstratoPerfilExpuesto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                argumentosEnviados= new Bundle();
                argumentosEnviados.putSerializable("proyecto",proyectoSeleccionado);
                argumentosEnviados.putSerializable("usuario",usuarioLogueado);
                argumentosEnviados.putSerializable("umtp",umtpSeleccionado);
                argumentosEnviados.putSerializable("perfil",perfilSeleccionado);
                argumentosEnviados.putString("origen","perfil");
                //System.out.println(proyectoSelecionado.getNombre() + "desde adaptador UMTP para crear el fragment");
                CrearEstratoFragment estratoPerfil= new CrearEstratoFragment();
                estratoPerfil.setArguments(argumentosEnviados);
                FragmentManager manager= getFragmentManager();
                manager.beginTransaction().replace(R.id.contenidos, estratoPerfil).commit();
            }
        });

        return view;
    }



    private void consultarEstratosPerfilExpuesto() {
        /*final ProgressDialog pDialog;
        pDialog=new ProgressDialog(getContext());
        pDialog.setMessage("Cargando...");
        pDialog.show();*/

        final String ip=getString(R.string.ip_url);


        String url=ip+"/web_services/buscar_estratos_perfil_expuesto.php?perfiles_expuestos_perfil_expuesto_id="+idPerfil;

        System.out.println(url);

        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //pDialog.hide();

                JSONArray json=response.optJSONArray("estratos_perfiles");
                JSONObject jsonObject;

                try {
                    for (int i=0;i<json.length();i++) {
                        jsonObject = json.getJSONObject(i);

                        estratoPerfil=new EstratosPerfiles(jsonObject.getInt("estrato_perfil_id"), jsonObject.getInt("texturas_estratos_textura_estrato_id"),
                                jsonObject.getString("textura_estrato_nombre"), jsonObject.getInt("estructuras_estratos_estructura_estrato_id"),
                                jsonObject.getString("estructura_estrato_nombre"), jsonObject.getInt("tipos_estratos_tipo_estrato_id"),
                                jsonObject.getString("tipo_estrato_nombre"),jsonObject.getInt("perfiles_expuestos_perfil_expuesto_id"), jsonObject.getLong("profundidad"),
                                jsonObject.getString("color"), jsonObject.getString("observacion"),jsonObject.getString("codigo_rotulo"));
                        //proyecto.setNombre(jsonObject.optString("nombre"));
                        //miUsuario.setProfesion(jsonObject.optString("profesion"));
                        //miUsuario.setRutaImagen(jsonObject.optString("ruta_imagen"));
                        //System.out.println(jsonObject.getString("nombre"));
                        listEstratosPerfil.add(estratoPerfil);

                    }

                    mAdapter=new AdapterEstratosPerfilesExpuestos(listEstratosPerfil, R.layout.recycler_view_estratos_perfiles_expuestos, new AdapterEstratosPerfilesExpuestos.OnItemClickListener() {
                        @Override
                        public void onItemClick(EstratosPerfiles listEstratoPerfil, int position, String boton) {
                            estratoPerfil=listEstratoPerfil;
                            posicion=position;
                            FragmentManager manager= getFragmentManager();
                            /*if(boton.equals("Ver")){

                                System.out.println("VEEEEERRR ESTRATO PERFIL");
                                estratoPerfil=listEstratoPerfil;

                                *//*argumentosEnviados= new Bundle();
                                argumentosEnviados.putSerializable("proyecto",proyectoSeleccionado);
                                argumentosEnviados.putSerializable("usuario",usuarioLogueado);
                                argumentosEnviados.putSerializable("umtp",umtpSeleccionado);
                                argumentosEnviados.putSerializable("pozo",pozoSeleccionado);
                                VerPozoSondeoFragment verPozo= new VerPozoSondeoFragment();
                                verPozo.setArguments(argumentosEnviados);
                                FragmentManager manager= getFragmentManager();
                                manager.beginTransaction().replace(R.id.contenidos, verPozo).commit();*//*
                                EstratosPozos estratoPozo=new EstratosPozos();
                                new CuadroDialogoActualizarEstrato(getContext(),estratoPozo,estratoPerfil,"estratoPerfil",EstratosPerfilesExpuestosFragment.this);

                            }*/

                            switch(boton)
                            {
                                case "Ver":
                                    System.out.println("VEEEEERRR ESTRATO PERFIL");

                                    EstratosPozos estratoPozo=new EstratosPozos();
                                    new CuadroDialogoActualizarEstrato(getContext(),estratoPozo,estratoPerfil,"estratoPerfil",EstratosPerfilesExpuestosFragment.this);

                                    break;

                                case "Materiales":
                                    System.out.println("IR A MATERIALES ESTRATO PERFIL");

                                    argumentosEnviados= new Bundle();
                                    argumentosEnviados.putSerializable("proyecto",proyectoSeleccionado);
                                    argumentosEnviados.putSerializable("usuario",usuarioLogueado);
                                    argumentosEnviados.putSerializable("umtp",umtpSeleccionado);
                                    argumentosEnviados.putSerializable("perfil",perfilSeleccionado);
                                    argumentosEnviados.putSerializable("estratoPerfil",estratoPerfil);
                                    MaterialesPerfilesExpuestosFragment materialesEstratoPerfil= new MaterialesPerfilesExpuestosFragment();
                                    materialesEstratoPerfil.setArguments(argumentosEnviados);

                                    manager.beginTransaction().replace(R.id.contenidos, materialesEstratoPerfil).commit();
                                    break;
                                case "Imagenes":
                                    System.out.println("IMAGENES ESTRATOS PERFILES");
                                    System.out.println("CLIC IMAGENES NIVEL POZO");
                                    argumentosEnviados= new Bundle();
                                    argumentosEnviados.putSerializable("proyecto",proyectoSeleccionado);
                                    argumentosEnviados.putSerializable("usuario",usuarioLogueado);
                                    argumentosEnviados.putSerializable("umtp",umtpSeleccionado);
                                    argumentosEnviados.putSerializable("perfil",perfilSeleccionado);
                                    argumentosEnviados.putSerializable("estratoPerfil",estratoPerfil);
                                    argumentosEnviados.putString("origen","estratoPerfil");
                                    ImagenesFragment imagenesEstratoPerfil= new ImagenesFragment();
                                    imagenesEstratoPerfil.setArguments(argumentosEnviados);
                                    //FragmentManager manager= getFragmentManager();
                                    manager.beginTransaction().replace(R.id.contenidos, imagenesEstratoPerfil).commit();

                                    break;
                                case "CodigoRotulo":
                                    System.out.println("CODIGO ROTULO ESTRATOS PERFILES");
                                    editarCodigoQR();

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


    private void editarCodigoQR() {
        final AlertDialog.Builder dialogoAdvertencia = new AlertDialog.Builder(getContext());
        dialogoAdvertencia.setTitle("Advertencia");
        dialogoAdvertencia.setMessage("Va realizar un escaneo de código QR, esto podría traer pérdida de información. ¿Desea continuar?");
        dialogoAdvertencia.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                IntentIntegrator integrator = new IntentIntegrator(getActivity());
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                integrator.setPrompt("Scan a barcode");
                integrator.setCameraId(0);  // Use a specific camera of the device
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(true);
                IntentIntegrator.forSupportFragment(EstratosPerfilesExpuestosFragment.this).initiateScan();
                dialog.dismiss();

            }
        });

        dialogoAdvertencia.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        dialogoAdvertencia.show();
    }

    String scanContent;
    String scanFormat;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        System.out.println("El resultado del escaneo es: "+ scanningResult);

        if (scanningResult.getContents() != null) {
            System.out.println("El resultado del escaneo es DIFERENTE de NULL: "+ scanningResult);
            scanContent = scanningResult.getContents().toString();
            scanFormat = scanningResult.getFormatName().toString();


            actualizarCodigoRotulo();
        }else
        {
            System.out.println("El resultado del escaneo es NULL: "+ scanningResult);
            Toast.makeText(getActivity(), "No se ha escaneado nada, o se ha cancelado el escaneo", Toast.LENGTH_SHORT).show();

        }
    }

    private void actualizarCodigoRotulo() {
        /*final ProgressDialog pDialog=new ProgressDialog(getContext());
        pDialog.setMessage("Cargando...");
        pDialog.show();*/
        String ip=getString(R.string.ip_url);
        String url;


        url=ip+"/web_services/editar_codigo_rotulo.php?";



        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //pDialog.hide();

                if (response.trim().equalsIgnoreCase("actualiza")){
                    // etiNombre.setText("");
                    //  txtDocumento.setText("");
                    //   etiProfesion.setText("");
                    //textViewVerUmtpCodigoRotulo.setText("Código Rótulo: "+scanContent);

                    String actual=    listEstratosPerfil.get(posicion).getCodigo_rotulo();
                    listEstratosPerfil.get(posicion).setCodigo_rotulo(scanContent);
                    System.out.println("Anterior: "+actual+" Impuesto "+ listEstratosPerfil.get(posicion).getCodigo_rotulo());
                    mAdapter.notifyItemChanged(posicion);



                    Toast.makeText(getContext(),"Se ha Actualizado con exito",Toast.LENGTH_SHORT).show();


                }else{
                    Toast.makeText(getContext(),"No se ha Actualizado ",Toast.LENGTH_SHORT).show();
                    Log.i("RESPUESTA: ",""+response);
                    System.out.println(response);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),"No se ha podido conectar",Toast.LENGTH_SHORT).show();
                //pDialog.hide();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {


                Map<String,String> parametros=new HashMap<>();


                parametros.put("estrato_perfil_id", listEstratosPerfil.get(posicion).getEstrato_perfil_id()+"");
                parametros.put("codigo_rotulo",scanContent);
                parametros.put("origen","estratoPerfil");
                return parametros;
            }
        };
        //request.add(stringRequest);
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(stringRequest);
    }

    @Override
    public void EstratoPozoActualizado(EstratosPozos estratoPozo) {

    }

    @Override
    public void EstratoPerfilActualizado(EstratosPerfiles estratoPerfil) {
        listEstratosPerfil.clear();
        consultarEstratosPerfilExpuesto();
    }

    /*@Override
    public void EstratoPozoActualizado(EstratosPozos nivelPozo) {
        listEstratosPerfil.clear();
        consultarEstratosPerfilExpuesto();
    }*/
}
