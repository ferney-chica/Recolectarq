package com.recolectarq.campo.recolectarq.Fragmentos.Intervenciones;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.recolectarq.campo.recolectarq.Actividades.InicioUsuarioActivity;
import com.recolectarq.campo.recolectarq.Carpetas;
import com.recolectarq.campo.recolectarq.Fragmentos.Intervenciones.PerfilesExpuestos.EstratosPerfilesExpuestos.MaterialesPerfilesExpuestosFragment;
import com.recolectarq.campo.recolectarq.Fragmentos.Intervenciones.PozosSondeo.NivelesPozoSondeo.MaterialesNivelesPozoFragment;
import com.recolectarq.campo.recolectarq.Fragmentos.Intervenciones.PozosSondeo.VerPozoSondeoFragment;
import com.recolectarq.campo.recolectarq.Fragmentos.Intervenciones.RecoleccionesSuperficiales.VerRecoleccionSuperficialFragment;
import com.recolectarq.campo.recolectarq.Modelo.EstratosPerfiles;
import com.recolectarq.campo.recolectarq.Modelo.FlancosGeograficos;
import com.recolectarq.campo.recolectarq.Modelo.MaterialesEstratosPerfiles;
import com.recolectarq.campo.recolectarq.Modelo.NivelesPozos;
import com.recolectarq.campo.recolectarq.Modelo.PerfilesExpuestos;
import com.recolectarq.campo.recolectarq.Modelo.PozosSondeo;
import com.recolectarq.campo.recolectarq.Modelo.Proyectos;
import com.recolectarq.campo.recolectarq.Modelo.RecoleccionesSuperficiales;
import com.recolectarq.campo.recolectarq.Modelo.TiposMateriales;
import com.recolectarq.campo.recolectarq.Modelo.Umtp;
import com.recolectarq.campo.recolectarq.Modelo.Usuarios;
import com.recolectarq.campo.recolectarq.R;
import com.recolectarq.campo.recolectarq.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CrearMaterialFragment extends Fragment implements InicioUsuarioActivity.OnBackPressedListener {

    private TextView textViewTituloCrearMaterial;
    private TextView textViewCrearMaterialFlanco;
    private TextView textViewCrearMaterialCodigoRotulo;
    private Spinner spinnerCrearMaterialTipoMaterial;
    private EditText editTextCrearMaterialCantidad;
    private Spinner spinnerCrearMaterialFlanco;
    private EditText editTextCrearMaterialObservacion;
    private EditText editTextCrearMaterialCodigoRotulo;
    private Spinner spinnerCrearMaterialElementoDiagnostico;
    private EditText editTextCrearMaterialObservacionElemento;


    private String observacionMaterial;
    private String codigoRotuloMaterialRecoleccion;
    private String origen;

    private int idUmtp;
    private int usuarioCreadorRecoleccion;
    private int idTipoMaterial;
    private int idFlanco;
    private String elementoDiagnostico;
    private int idElementoDiagnostico;

    private TextView usuarioExistente;
    private Button buttonCrearMaterial;
    private RequestQueue request;
    private JsonRequest jrq;
    private ArrayList<RecoleccionesSuperficiales> listaRecolecciones;
    private ArrayList<TiposMateriales> listaTiposMateriales;
    private ArrayList<FlancosGeograficos> listaFlancosGeograficos;
    private JsonObjectRequest jsonObjectRequest;


    private Usuarios usuarioLogueado;
    private Proyectos proyectoSeleccionado;
    private Umtp umtpSeleccionado;
    private PozosSondeo pozoSeleccionado;
    private NivelesPozos nivelSeleccionado;

    private EstratosPerfiles estratoPerfilSeleccionado;
    private PerfilesExpuestos perfilSeleccionado;

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
            origen=getArguments().getString("origen");

            System.out.println("EL ORIGEN ES: "+origen);

            switch (origen) {
                case "recoleccion":
                    recoleccionSeleccionado=(RecoleccionesSuperficiales) getArguments().getSerializable("recoleccion");
                    System.out.println("LLEGO DESDE RECOLECCION");
                    break;
                case "nivelpozo":
                    nivelSeleccionado=(NivelesPozos) getArguments().getSerializable("nivel");
                    pozoSeleccionado=(PozosSondeo) getArguments().getSerializable("pozo");
                    System.out.println("LLEGO DESDE MATERIAL NIVEL POZO");
                    break;
                case "estratoPerfil":
                    estratoPerfilSeleccionado=(EstratosPerfiles) getArguments().getSerializable("estratoPerfil");
                    perfilSeleccionado=(PerfilesExpuestos) getArguments().getSerializable("perfil");
                    System.out.println("LLEGO DESDE ESTRATO PERFIL");
                    break;
            }



            Carpetas carpeta=new Carpetas();
            carpeta.crearCarpeta("/Recolectarq/Proyectos/"+proyectoSeleccionado.getCodigo_identificacion()+"/UMTP");
            carpeta.crearCarpeta("/Recolectarq/Proyectos/"+proyectoSeleccionado.getCodigo_identificacion()+"/MemoriasTecnicas");
            usuarioCreadorRecoleccion = Integer.parseInt(usuarioLogueado.getUsuario_id());
            idUmtp=umtpSeleccionado.getUmtp_id();
        }else{
            usuarioLogueado=new Usuarios("null","null", "null","null");
            Toast.makeText(getContext(),"NO entroOOOOOOOO",Toast.LENGTH_LONG);
            usuarioLogueado.setNombre("No hay usuario logueado");

        }
    }




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       // return super.onCreateView(inflater, container, savedInstanceState);
        ((InicioUsuarioActivity) getActivity()).setOnBackPressedListener(this);
        final View v= inflater.inflate(R.layout.fragment_crear_material,container, false);
        spinnerCrearMaterialTipoMaterial=v.findViewById(R.id.spinnerCrearMaterialTipoMaterial);
        editTextCrearMaterialCantidad=v.findViewById(R.id.editTextCrearMaterialCantidad);
        spinnerCrearMaterialFlanco=v.findViewById(R.id.spinnerCrearMaterialFlanco);
        editTextCrearMaterialObservacion=v.findViewById(R.id.editTextCrearMaterialObservacion);
        editTextCrearMaterialCodigoRotulo=v.findViewById(R.id.editTextCrearMaterialCodigoRotulo);
        spinnerCrearMaterialElementoDiagnostico=v.findViewById(R.id.spinnerCrearMaterialElementoDiagnostico);
        editTextCrearMaterialObservacionElemento=v.findViewById(R.id.editTextCrearMaterialObservacionElemento);

        textViewTituloCrearMaterial=v.findViewById(R.id.textViewTituloCrearMaterial);
        textViewCrearMaterialFlanco=v.findViewById(R.id.textViewCrearMaterialFlanco);
        textViewCrearMaterialCodigoRotulo=v.findViewById(R.id.textViewCrearMaterialCodigoRotulo);


        editTextCrearMaterialObservacionElemento.setEnabled(false);

        ocultarCampos();

        listaTiposMateriales= new ArrayList<>();
        listaFlancosGeograficos= new ArrayList<>();
        llenarElementoDiagnostico();
        consultarTiposMateriales();


        buttonCrearMaterial=v.findViewById(R.id.buttonCrearMaterial);
        buttonCrearMaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                camposLlenos();

                /*if (editTextCodigoRotulo.getText().toString().trim().isEmpty()){
                    codigoRotuloRecoleccion="---";
                }else
                {
                    codigoRotuloRecoleccion=editTextCodigoRotulo.getText().toString().trim();
                }

                if (editTextDescripcion.getText().toString().trim().isEmpty()){
                    Toast.makeText(getContext(),"Diligencie la descripción del pozo", Toast.LENGTH_LONG).show();
                }else{
                    webServiceInsertarRecoleccion();
                }*/

            }
        });

        return v;
    }

    private void ocultarCampos(){


        switch (origen) {
            case "recoleccion":
                textViewTituloCrearMaterial.setText("Crear Material Recoleccion");
                consultarFlancosGeograficos();
                break;
            case "nivelpozo":
                spinnerCrearMaterialFlanco.setVisibility(View.INVISIBLE);
                editTextCrearMaterialCodigoRotulo.setVisibility(View.INVISIBLE);
                textViewTituloCrearMaterial.setText("Crear Material Nivel Pozo");
                textViewCrearMaterialFlanco.setVisibility(View.INVISIBLE);
                textViewCrearMaterialCodigoRotulo.setVisibility(View.INVISIBLE);
                break;
            case "estratoPerfil":
                textViewTituloCrearMaterial.setText("Crear Material Estrato Perfil");
                spinnerCrearMaterialFlanco.setVisibility(View.INVISIBLE);
                editTextCrearMaterialCodigoRotulo.setVisibility(View.INVISIBLE);
                textViewCrearMaterialFlanco.setVisibility(View.INVISIBLE);
                textViewCrearMaterialCodigoRotulo.setVisibility(View.INVISIBLE);
                break;
        }


    }
    private void camposLlenos()
    {
        switch (origen) {
            case "recoleccion":
                if(idElementoDiagnostico==0||idTipoMaterial==-1 || idFlanco==-1 ||
                        editTextCrearMaterialCantidad.getText().toString().trim().isEmpty())
                {
                    Toast.makeText(getContext(),"Faltan datos por diligenciar",Toast.LENGTH_LONG).show();
                }else
                {
                    if(idElementoDiagnostico==1 && editTextCrearMaterialObservacionElemento.getText().toString().trim().isEmpty())
                    {
                        Toast.makeText(getContext(),"Faltan datos por diligenciar",Toast.LENGTH_LONG).show();
                    } else
                    {
                        webServiceInsertarMaterialRecoleccion();
                    }
                }
                if (editTextCrearMaterialCodigoRotulo.getText().toString().trim().isEmpty())
                {
                    codigoRotuloMaterialRecoleccion="---";
                }

                if (editTextCrearMaterialObservacion.getText().toString().trim().isEmpty())
                {
                    observacionMaterial="---";
                }
                break;
            case "nivelpozo":
                if(idElementoDiagnostico==0||idTipoMaterial==-1 ||
                        editTextCrearMaterialCantidad.getText().toString().trim().isEmpty())
                {
                    Toast.makeText(getContext(),"Faltan datos por diligenciar",Toast.LENGTH_LONG).show();
                }else
                {
                    if(idElementoDiagnostico==1 && editTextCrearMaterialObservacionElemento.getText().toString().trim().isEmpty())
                    {
                        Toast.makeText(getContext(),"Faltan datos por diligenciar",Toast.LENGTH_LONG).show();
                    } else
                    {
                        webServiceInsertarMaterialNivel();
                    }
                }

                if (editTextCrearMaterialObservacion.getText().toString().trim().isEmpty())
                {
                    observacionMaterial="---";
                }
                break;
            case "estratoPerfil":
                if(idElementoDiagnostico==0||idTipoMaterial==-1 ||
                        editTextCrearMaterialCantidad.getText().toString().trim().isEmpty())
                {
                    Toast.makeText(getContext(),"Faltan datos por diligenciar",Toast.LENGTH_LONG).show();
                }else
                {
                    if(idElementoDiagnostico==1 && editTextCrearMaterialObservacionElemento.getText().toString().trim().isEmpty())
                    {
                        Toast.makeText(getContext(),"Faltan datos por diligenciar",Toast.LENGTH_LONG).show();
                    } else
                    {
                        webServiceInsertarMaterialEstratoPerfil();
                    }
                }

                if (editTextCrearMaterialObservacion.getText().toString().trim().isEmpty())
                {
                    observacionMaterial="---";
                }
                break;
        }


    }


    private void llenarElementoDiagnostico()
    {
        final List<String> listElementos=new ArrayList<String>();
        listElementos.add("Seleccione...");
        listElementos.add("Sí");
        listElementos.add("No");
        ArrayAdapter<CharSequence> spinnerArrayAdapter= new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, listElementos);
        spinnerCrearMaterialElementoDiagnostico.setAdapter(spinnerArrayAdapter);

        spinnerCrearMaterialElementoDiagnostico.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (position)
                {
                    case 0:
                        System.out.println(position+"--- " +listElementos.get(position));
                        idElementoDiagnostico=position;
                        editTextCrearMaterialObservacionElemento.setEnabled(false);
                        break;
                    case 1:
                        System.out.println(position+"--- " +listElementos.get(position));
                        idElementoDiagnostico=position;
                        elementoDiagnostico="Sí";
                        editTextCrearMaterialObservacionElemento.setText("");
                        editTextCrearMaterialObservacionElemento.setEnabled(true);
                        break;
                    case 2:
                        System.out.println(position+"--- " +listElementos.get(position));
                        idElementoDiagnostico=position;
                        elementoDiagnostico="No";
                        editTextCrearMaterialObservacionElemento.setText("---");
                        editTextCrearMaterialObservacionElemento.setEnabled(false);
                        break;

                }
               /* if (position!=0) {
                    //System.out.println(listaGeoforma.get(position - 1).getId());
                    //idElementoDiagnostico=listaGeoforma.get(position - 1).getId();

                }else
                {
                    //idElementoDiagnostico=-1;
                    System.out.println(position+"--- " +listElementos.get(position));
                }*/
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

   private void webServiceInsertarMaterialRecoleccion() {
        /*final ProgressDialog pDialog=new ProgressDialog(getContext());
        pDialog.setMessage("Cargando...");
        pDialog.show();*/

        String ip=getString(R.string.ip_url);

        String url=ip+"/web_services/insertar_material_recoleccion.php?";

        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //pDialog.hide();

                if (response.trim().equalsIgnoreCase("inserto")){
                    // etiNombre.setText("");
                    //  txtDocumento.setText("");
                    //   etiProfesion.setText("");
                    Toast.makeText(getContext(),"Se ha insertado con exito",Toast.LENGTH_SHORT).show();
                    VerRecoleccionSuperficialFragment recoleccion= new VerRecoleccionSuperficialFragment();
                    FragmentManager manager= getFragmentManager();
                    argumentosEnviados= new Bundle();
                    argumentosEnviados.putSerializable("usuario",usuarioLogueado);
                    argumentosEnviados.putSerializable("proyecto",proyectoSeleccionado);
                    argumentosEnviados.putSerializable("umtp",umtpSeleccionado);
                    argumentosEnviados.putSerializable("recoleccion",recoleccionSeleccionado);
                    recoleccion.setArguments(argumentosEnviados);
                    manager.beginTransaction().replace(R.id.contenidos, recoleccion).commit();
                    System.out.println("LA UMTP SELECCIONADA ES: "+umtpSeleccionado.getUmtp_id());

                }else{
                    Toast.makeText(getContext(),"No se ha insertado el proyecto ",Toast.LENGTH_SHORT).show();
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
                //String cedulaU=cedula.getText().toString();
                //String nombreU=nombre.getText().toString();
                //String apellidoU=apellido.getText().toString();

                //String imagen=convertirImgString(bitmap);

                Map<String,String> parametros=new HashMap<>();
                parametros.put("flancos_geograficos_id",idFlanco+"");
                parametros.put("tipos_materiales_id",idTipoMaterial+"");
                parametros.put("recolecciones_superficiales_recoleccion_superficial_id",recoleccionSeleccionado.getrecoleccion_superficial_id()+"");
                parametros.put("cantidad",editTextCrearMaterialCantidad.getText().toString());
                parametros.put("observacion",editTextCrearMaterialObservacion.getText().toString());
                parametros.put("codigo_rotulo",editTextCrearMaterialCodigoRotulo.getText().toString());
                parametros.put("elemento_diagnostico",elementoDiagnostico);
                parametros.put("observacion_elemento_diagnostico",editTextCrearMaterialObservacionElemento.getText().toString());
                //parametros.put("imagen",imagen);

                return parametros;
            }
        };
        //request.add(stringRequest);
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(stringRequest);
    }

    private void webServiceInsertarMaterialNivel() {
        /*final ProgressDialog pDialog=new ProgressDialog(getContext());
        pDialog.setMessage("Cargando...");
        pDialog.show();*/

        String ip=getString(R.string.ip_url);

        String url=ip+"/web_services/insertar_material_nivel.php?";

        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //pDialog.hide();

                if (response.trim().equalsIgnoreCase("inserto")){
                    // etiNombre.setText("");
                    //  txtDocumento.setText("");
                    //   etiProfesion.setText("");
                    Toast.makeText(getContext(),"Se ha insertado con exito",Toast.LENGTH_SHORT).show();
                    MaterialesNivelesPozoFragment materialNivel= new MaterialesNivelesPozoFragment();
                    FragmentManager manager= getFragmentManager();
                    argumentosEnviados= new Bundle();
                    argumentosEnviados.putSerializable("usuario",usuarioLogueado);
                    argumentosEnviados.putSerializable("proyecto",proyectoSeleccionado);
                    argumentosEnviados.putSerializable("umtp",umtpSeleccionado);
                    argumentosEnviados.putSerializable("pozo",pozoSeleccionado);
                    argumentosEnviados.putSerializable("nivel",nivelSeleccionado);
                    materialNivel.setArguments(argumentosEnviados);
                    manager.beginTransaction().replace(R.id.contenidos, materialNivel).commit();

                }else{
                    Toast.makeText(getContext(),"No se ha insertado el material ",Toast.LENGTH_SHORT).show();
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
                //String cedulaU=cedula.getText().toString();
                //String nombreU=nombre.getText().toString();
                //String apellidoU=apellido.getText().toString();

                //String imagen=convertirImgString(bitmap);

                Map<String,String> parametros=new HashMap<>();
                parametros.put("tipos_materiales_id",idTipoMaterial+"");
                parametros.put("niveles_pozos_nivel_pozo_id",nivelSeleccionado.getNivel_pozo_id()+"");
                parametros.put("cantidad",editTextCrearMaterialCantidad.getText().toString());
                parametros.put("observacion",editTextCrearMaterialObservacion.getText().toString());
                parametros.put("elemento_diagnostico",elementoDiagnostico);
                parametros.put("observacion_elemento_diagnostico",editTextCrearMaterialObservacionElemento.getText().toString());
                //parametros.put("imagen",imagen);

                return parametros;
            }
        };
        //request.add(stringRequest);
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(stringRequest);
    }

    private void webServiceInsertarMaterialEstratoPerfil() {
        /*final ProgressDialog pDialog=new ProgressDialog(getContext());
        pDialog.setMessage("Cargando...");
        pDialog.show();*/

        String ip=getString(R.string.ip_url);

        String url=ip+"/web_services/insertar_material_estrato_perfil.php?";

        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //pDialog.hide();

                if (response.trim().equalsIgnoreCase("inserto")){
                    // etiNombre.setText("");
                    //  txtDocumento.setText("");
                    //   etiProfesion.setText("");
                    Toast.makeText(getContext(),"Se ha insertado con exito",Toast.LENGTH_SHORT).show();
                    MaterialesPerfilesExpuestosFragment materialEstratoPerfil= new MaterialesPerfilesExpuestosFragment();
                    FragmentManager manager= getFragmentManager();
                    argumentosEnviados= new Bundle();
                    argumentosEnviados.putSerializable("usuario",usuarioLogueado);
                    argumentosEnviados.putSerializable("proyecto",proyectoSeleccionado);
                    argumentosEnviados.putSerializable("umtp",umtpSeleccionado);
                    argumentosEnviados.putSerializable("perfil",perfilSeleccionado);
                    argumentosEnviados.putSerializable("estratoPerfil",estratoPerfilSeleccionado);
                    materialEstratoPerfil.setArguments(argumentosEnviados);
                    manager.beginTransaction().replace(R.id.contenidos, materialEstratoPerfil).commit();

                }else{
                    Toast.makeText(getContext(),"No se ha insertado el material ",Toast.LENGTH_SHORT).show();
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
                //String cedulaU=cedula.getText().toString();
                //String nombreU=nombre.getText().toString();
                //String apellidoU=apellido.getText().toString();

                //String imagen=convertirImgString(bitmap);

                Map<String,String> parametros=new HashMap<>();
                parametros.put("tipos_materiales_id",idTipoMaterial+"");
                parametros.put("estratos_perfiles_estrato_perfil_id",estratoPerfilSeleccionado.getEstrato_perfil_id()+"");
                parametros.put("cantidad",editTextCrearMaterialCantidad.getText().toString());
                parametros.put("observacion",editTextCrearMaterialObservacion.getText().toString());
                parametros.put("elemento_diagnostico",elementoDiagnostico);
                parametros.put("observacion_elemento_diagnostico",editTextCrearMaterialObservacionElemento.getText().toString());
                //parametros.put("imagen",imagen);

                return parametros;
            }
        };
        //request.add(stringRequest);
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(stringRequest);
    }

    private void consultarTiposMateriales() {
        /*final ProgressDialog pDialog;
        pDialog=new ProgressDialog(getContext());
        pDialog.setMessage("Cargando...");
        pDialog.show();*/

        final String ip=getString(R.string.ip_url);

        String url=ip+"/web_services/tipos_materiales.php";

        System.out.println(url);

        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //pDialog.hide();

                TiposMateriales tipoMaterial;

                JSONArray json=response.optJSONArray("tipos_materiales");
                JSONObject jsonObject;
                List<String> listTipos=new ArrayList<String>();
                listTipos.add("Seleccione...");
                try {
                    for (int i=0;i<json.length();i++) {
                        jsonObject = json.getJSONObject(i);


                        tipoMaterial=new TiposMateriales(jsonObject.getInt("id"), jsonObject.getString("nombre"));

                        System.out.println(jsonObject.getString("nombre")+ "  "+ i+json.length());
                        listaTiposMateriales.add(tipoMaterial);
                        listTipos.add(jsonObject.getString("nombre"));

                    }
                    ArrayAdapter<CharSequence> spinnerArrayAdapter= new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, listTipos);
                    spinnerCrearMaterialTipoMaterial.setAdapter(spinnerArrayAdapter);

                    spinnerCrearMaterialTipoMaterial.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            if (position!=0) {
                                System.out.println(listaTiposMateriales.get(position - 1).getId());
                                idTipoMaterial=listaTiposMateriales.get(position - 1).getId();
                            }else
                            {
                                idTipoMaterial=-1;
                                System.out.println(idTipoMaterial);
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });



                }  catch (JSONException e) {
                    e.printStackTrace();
                }
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

    private void consultarFlancosGeograficos() {
        /*final ProgressDialog pDialog;
        pDialog=new ProgressDialog(getContext());
        pDialog.setMessage("Cargando...");
        pDialog.show();*/

        final String ip=getString(R.string.ip_url);

        String url=ip+"/web_services/flancos_geograficos.php";

        System.out.println(url);

        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //pDialog.hide();

                FlancosGeograficos flancoGeografico;

                JSONArray json=response.optJSONArray("flancos_geograficos");
                JSONObject jsonObject;
                List<String> listTipos=new ArrayList<String>();
                listTipos.add("Seleccione...");
                try {
                    for (int i=0;i<json.length();i++) {
                        jsonObject = json.getJSONObject(i);


                        flancoGeografico=new FlancosGeograficos(jsonObject.getInt("id"), jsonObject.getString("nombre"));

                        System.out.println(jsonObject.getString("nombre")+ "  "+ i+json.length());
                        listaFlancosGeograficos.add(flancoGeografico);
                        listTipos.add(jsonObject.getString("nombre"));

                    }
                    ArrayAdapter<CharSequence> spinnerArrayAdapter= new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, listTipos);
                    spinnerCrearMaterialFlanco.setAdapter(spinnerArrayAdapter);

                    spinnerCrearMaterialFlanco.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            if (position!=0) {
                                System.out.println(listaFlancosGeograficos.get(position - 1).getId());
                                idFlanco=listaFlancosGeograficos.get(position - 1).getId();
                            }else
                            {
                                idFlanco=-1;
                                System.out.println(idFlanco);
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });



                }  catch (JSONException e) {
                    e.printStackTrace();
                }
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
        switch (origen) {
            case "recoleccion":
                Bundle argumentosEnviados;
                argumentosEnviados= new Bundle();
                argumentosEnviados.putSerializable("proyecto",proyectoSeleccionado);
                argumentosEnviados.putSerializable("usuario",usuarioLogueado);
                argumentosEnviados.putSerializable("umtp",umtpSeleccionado);
                argumentosEnviados.putSerializable("recoleccion",recoleccionSeleccionado);

                VerRecoleccionSuperficialFragment verRecoleccion= new VerRecoleccionSuperficialFragment();
                verRecoleccion.setArguments(argumentosEnviados);
                FragmentManager manager= getFragmentManager();
                manager.beginTransaction().replace(R.id.contenidos, verRecoleccion).commit();
                break;
            case "nivelpozo":
                argumentosEnviados= new Bundle();
                argumentosEnviados.putSerializable("proyecto",proyectoSeleccionado);
                argumentosEnviados.putSerializable("usuario",usuarioLogueado);
                argumentosEnviados.putSerializable("umtp",umtpSeleccionado);
                argumentosEnviados.putSerializable("pozo",pozoSeleccionado);
                argumentosEnviados.putSerializable("nivel",nivelSeleccionado);
                //System.out.println("Pozo seleccionado desde CrearMaterial  es: "+pozoSeleccionado.getPozo_id());
                MaterialesNivelesPozoFragment materialNivel= new MaterialesNivelesPozoFragment();
                materialNivel.setArguments(argumentosEnviados);
                manager= getFragmentManager();
                manager.beginTransaction().replace(R.id.contenidos, materialNivel).commit();
                break;
            case "estratoPerfil":

                argumentosEnviados= new Bundle();
                argumentosEnviados.putSerializable("proyecto",proyectoSeleccionado);
                argumentosEnviados.putSerializable("usuario",usuarioLogueado);
                argumentosEnviados.putSerializable("umtp",umtpSeleccionado);
                argumentosEnviados.putSerializable("perfil",perfilSeleccionado);
                argumentosEnviados.putSerializable("estratoPerfil",estratoPerfilSeleccionado);
                //System.out.println("Pozo seleccionado desde CrearMaterial  es: "+pozoSeleccionado.getPozo_id());
                MaterialesPerfilesExpuestosFragment materialEstrato= new MaterialesPerfilesExpuestosFragment();
                materialEstrato.setArguments(argumentosEnviados);
                manager= getFragmentManager();
                manager.beginTransaction().replace(R.id.contenidos, materialEstrato).commit();

                break;
        }
    }
}
