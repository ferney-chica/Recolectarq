package com.recolectarq.campo.recolectarq.Fragmentos.Intervenciones.RecoleccionesSuperficiales.MaterialesRecolecciones;

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
import com.recolectarq.campo.recolectarq.AdaptadoresRecycler.AdapterMaterialesRecolecciones;
import com.recolectarq.campo.recolectarq.Carpetas;
import com.recolectarq.campo.recolectarq.CuadrosDialogos.CuadroDialogoActualizarMaterial;
import com.recolectarq.campo.recolectarq.Fragmentos.ImagenesFragment;
import com.recolectarq.campo.recolectarq.Fragmentos.Intervenciones.CrearMaterialFragment;
import com.recolectarq.campo.recolectarq.Fragmentos.Intervenciones.PerfilesExpuestos.EstratosPerfilesExpuestos.EstratosPerfilesExpuestosFragment;
import com.recolectarq.campo.recolectarq.Fragmentos.Intervenciones.RecoleccionesSuperficiales.VerRecoleccionSuperficialFragment;
import com.recolectarq.campo.recolectarq.Modelo.MaterialesEstratosPerfiles;
import com.recolectarq.campo.recolectarq.Modelo.MaterialesNiveles;
import com.recolectarq.campo.recolectarq.Modelo.MaterialesRecolecciones;
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
import java.util.HashMap;
import java.util.Map;

public class MaterialesRecoleccionesSuperficialesFragment extends Fragment implements CuadroDialogoActualizarMaterial.FinalizarCuadroDialogo{

    private String cedula;
    int recolecciones_superficiales_recoleccion_superficial_id;
    int posicion;
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    Date fechaFin;
    Date fechaInicio;
    private ArrayList<MaterialesRecolecciones> listMaterialesRecolecciones;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    JsonObjectRequest jsonObjectRequest;
    FloatingActionButton fbtCrearMaterialRecoleccion;
    private MaterialesRecolecciones materialRecoleccion;
    private Usuarios usuarioLogueado;
    private Proyectos proyectoSeleccionado;
    private Umtp umtpSeleccionado;
    private RecoleccionesSuperficiales recoleccionSeleccionado;
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
            recoleccionSeleccionado=(RecoleccionesSuperficiales) getArguments().getSerializable("recoleccion");
            Carpetas carpeta=new Carpetas();
            System.out.println(umtpSeleccionado.getUmtp_id()+ "DESDE MATERIALES RECOLECCION");
            System.out.println( carpeta.crearCarpeta("/Recolectarq/Proyectos/"+proyectoSeleccionado.getCodigo_identificacion()));
            carpeta.crearCarpeta("/Recolectarq/Proyectos/"+proyectoSeleccionado.getCodigo_identificacion()+"/UMTP");
            carpeta.crearCarpeta("/Recolectarq/Proyectos/"+proyectoSeleccionado.getCodigo_identificacion()+"/MemoriasTecnicas");
            int idUsuario = Integer.parseInt(usuarioLogueado.getUsuario_id());
            recolecciones_superficiales_recoleccion_superficial_id=recoleccionSeleccionado.getrecoleccion_superficial_id();

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

        View view= inflater.inflate(R.layout.fragment_materiales_recolecciones_superficiales,container, false);

        listMaterialesRecolecciones= new ArrayList<>();
        mRecyclerView=view.findViewById(R.id.recyclerViewMaterialesRecoleccion);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
        fbtCrearMaterialRecoleccion= view.findViewById(R.id.fabBotonAdicionarMaterialesRecoleccion);
        consultarMaterialesRecoleccionesSuperfiales();
        System.out.println("El usuario Logueado es desde proyectos" + usuarioLogueado.getNombre());
        fbtCrearMaterialRecoleccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                argumentosEnviados= new Bundle();
                argumentosEnviados.putSerializable("proyecto",proyectoSeleccionado);
                argumentosEnviados.putSerializable("usuario",usuarioLogueado);
                argumentosEnviados.putSerializable("umtp",umtpSeleccionado);
                argumentosEnviados.putSerializable("recoleccion",recoleccionSeleccionado);
                argumentosEnviados.putString("origen","recoleccion");
                System.out.println("ENTRO A CREAR MATERIAL RECOLECCION///////////---------***************************");
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


        String url=ip+"/web_services/buscar_materiales_recoleccion_superficial.php?recolecciones_superficiales_recoleccion_superficial_id="+recolecciones_superficiales_recoleccion_superficial_id;

        System.out.println(url);

        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //pDialog.hide();

                JSONArray json=response.optJSONArray("materiales_recoleccion");
                JSONObject jsonObject;

                try {
                    for (int i=0;i<json.length();i++) {
                        jsonObject = json.getJSONObject(i);

                        materialRecoleccion=new MaterialesRecolecciones(jsonObject.getInt("material_recoleccion_id"), jsonObject.getInt("flancos_geograficos_id"),
                                jsonObject.getString("nombre_flanco_geografico"), jsonObject.getInt("tipos_materiales_id"),
                                jsonObject.getString("nombre_tipo_material"),jsonObject.getInt("recolecciones_superficiales_recoleccion_superficial_id"),
                                jsonObject.getInt("cantidad"), jsonObject.getString("observacion"), jsonObject.getString("codigo_rotulo"),
                                jsonObject.getString("elemento_diagnostico"),jsonObject.getString("observacion_elemento_diagnostico"));
                        //proyecto.setNombre(jsonObject.optString("nombre"));
                        //miUsuario.setProfesion(jsonObject.optString("profesion"));
                        //miUsuario.setRutaImagen(jsonObject.optString("ruta_imagen"));
                        //System.out.println(jsonObject.getString("nombre"));
                        listMaterialesRecolecciones.add(materialRecoleccion);

                    }

                    mAdapter=new AdapterMaterialesRecolecciones(listMaterialesRecolecciones, R.layout.recycler_view_materiales_recolecciones_superficiales, new AdapterMaterialesRecolecciones.OnItemClickListener() {
                        @Override
                        public void onItemClick(MaterialesRecolecciones listMaterialRecoleccion, int position, String boton) {
                            System.out.println("DIOOOOO clic boton: " + boton);
                            materialRecoleccion=listMaterialRecoleccion;
                            posicion=position;
                            /*if(boton.equals("Ver")){

                                System.out.println("VEEEEERRR");
                                materialRecoleccion=listMaterialRecoleccion;
                                System.out.println("RECOLECCIONOOOOOO: "+listMaterialRecoleccion.getRecolecciones_superficiales_recoleccion_superficial_id());

                                *//*argumentosEnviados= new Bundle();
                                argumentosEnviados.putSerializable("proyecto",proyectoSeleccionado);
                                argumentosEnviados.putSerializable("usuario",usuarioLogueado);
                                argumentosEnviados.putSerializable("umtp",umtpSeleccionado);
                                argumentosEnviados.putSerializable("recoleccion",recoleccionSeleccionado);
                                VerRecoleccionSuperficialFragment verRecoleccion= new VerRecoleccionSuperficialFragment();
                                verRecoleccion.setArguments(argumentosEnviados);
                                FragmentManager manager= getFragmentManager();
                                manager.beginTransaction().replace(R.id.contenidos, verRecoleccion).commit();*//*
                                MaterialesNiveles materialeNiveles=new MaterialesNiveles();
                                MaterialesEstratosPerfiles materialEstratoPerfil= new MaterialesEstratosPerfiles();
                                new CuadroDialogoActualizarMaterial(getContext(),materialeNiveles, listMaterialRecoleccion,materialEstratoPerfil,"materialesRecoleccion",MaterialesRecoleccionesSuperficialesFragment.this);

                            }*/

                            switch(boton)
                            {

                                case "Ver":
                                    System.out.println("VEEEEERRR");
                                    materialRecoleccion=listMaterialRecoleccion;
                                    System.out.println("RECOLECCIONOOOOOO: "+listMaterialRecoleccion.getRecolecciones_superficiales_recoleccion_superficial_id());

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
                                    MaterialesEstratosPerfiles materialEstratoPerfil= new MaterialesEstratosPerfiles();
                                    new CuadroDialogoActualizarMaterial(getContext(),materialeNiveles, listMaterialRecoleccion,materialEstratoPerfil,"materialesRecoleccion",MaterialesRecoleccionesSuperficialesFragment.this);

                                    break;
                                case "Imagenes":
                                    System.out.println("IMAGENES RECOLECCIONOOOOOO: ");
                                    System.out.println("CLIC IMAGENES NIVEL POZO");
                                    argumentosEnviados= new Bundle();
                                    argumentosEnviados.putSerializable("proyecto",proyectoSeleccionado);
                                    argumentosEnviados.putSerializable("usuario",usuarioLogueado);
                                    argumentosEnviados.putSerializable("umtp",umtpSeleccionado);
                                    argumentosEnviados.putSerializable("recoleccion",recoleccionSeleccionado);
                                    argumentosEnviados.putSerializable("materialRecoleccion",materialRecoleccion);
                                    argumentosEnviados.putString("origen","materialRecoleccion");
                                    ImagenesFragment imagenesUmtp= new ImagenesFragment();
                                    imagenesUmtp.setArguments(argumentosEnviados);
                                    FragmentManager manager= getFragmentManager();
                                    manager.beginTransaction().replace(R.id.contenidos, imagenesUmtp).commit();



                                    break;

                                case "CodigoRotulo":
                                    System.out.println("CODIGO ROTULO MATERIALES RECOLECCIONOOOOOO: ");
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
                IntentIntegrator.forSupportFragment(MaterialesRecoleccionesSuperficialesFragment.this).initiateScan();
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

                    String actual=    listMaterialesRecolecciones.get(posicion).getCodigo_rotulo();
                    listMaterialesRecolecciones.get(posicion).setCodigo_rotulo(scanContent);
                    System.out.println("Anterior: "+actual+" Impuesto "+ listMaterialesRecolecciones.get(posicion).getCodigo_rotulo());
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


                parametros.put("material_recoleccion_id", listMaterialesRecolecciones.get(posicion).getMaterial_recoleccion_id()+"");
                parametros.put("codigo_rotulo",scanContent);
                parametros.put("origen","materialRecoleccion");
                return parametros;
            }
        };
        //request.add(stringRequest);
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(stringRequest);
    }


    @Override
    public void MaterialNivelPozoActualizado(MaterialesNiveles materialNivelPozo) {

    }

    @Override
    public void MaterialRecoleccion(MaterialesRecolecciones materialRecoleccion) {
        listMaterialesRecolecciones.clear();
        consultarMaterialesRecoleccionesSuperfiales();

    }

    @Override
    public void MaterialEstratoPerfil(MaterialesEstratosPerfiles materialEstratoPerfil) {


    }
}
