package com.recolectarq.campo.recolectarq.Fragmentos.Umtp.Yacimiento;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
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
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.recolectarq.campo.recolectarq.Carpetas;
import com.recolectarq.campo.recolectarq.Modelo.AbanicosVisibilidades;
import com.recolectarq.campo.recolectarq.Modelo.AgentesAlteracion;
import com.recolectarq.campo.recolectarq.Modelo.CausasAlteracion;
import com.recolectarq.campo.recolectarq.Modelo.CircularesVisibilidades;
import com.recolectarq.campo.recolectarq.Modelo.GradoAlteracion;
import com.recolectarq.campo.recolectarq.Modelo.GradoProteccion;
import com.recolectarq.campo.recolectarq.Modelo.LinealesVisibilidades;
import com.recolectarq.campo.recolectarq.Modelo.ModoProteccion;
import com.recolectarq.campo.recolectarq.Modelo.ProteccionFisica;
import com.recolectarq.campo.recolectarq.Modelo.Proyectos;
import com.recolectarq.campo.recolectarq.Modelo.PuntualesVisibilidades;
import com.recolectarq.campo.recolectarq.Modelo.RegimenPropiedad;
import com.recolectarq.campo.recolectarq.Modelo.Tipos_Proyectos;
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

public class SituacionPatrimonialFragment extends Fragment  {

    private int idUsuario;

    private FragmentActivity contexto;

    private RequestQueue request;
    private JsonRequest jrq;
    private ArrayList<Tipos_Proyectos> listaTiposProyectos;
    private Spinner spinnerTipoProyecto;
    private JsonObjectRequest jsonObjectRequest;


    private Usuarios usuarioLogueado;
    private Proyectos proyectoSeleccionado;
    private Umtp umtpSeleccionado;
    private Bundle usuarioEnviado;

    private int idGradoAlteracion;
    private int idCausaAlteracion;
    private int idAgenteAlteracion;
    private int idRegimenPropiedad;
    private int idGradoProteccion;
    private int idModoProteccion;
    private int idProteccionFisica;
    private int idUmtp;

    private Spinner spinnerSituacionPatrimonialGradoAlteracion;
    private Spinner spinnerSituacionPatrimonialCausasAlteracion;
    private Spinner spinnerSituacionPatrimonialAgentesAlteracion;
    private Spinner spinnerSituacionPatrimonialRegimenPropiedad;
    private Spinner spinnerSituacionPatrimonialGradoProteccion;
    private Spinner spinnerSituacionPatrimonialModoProteccion;
    private Spinner spinnerSituacionPatrimonialProteccionFisica;

    private ArrayList<GradoAlteracion> listaGradoAlteracion;
    private GradoAlteracion gradoAlteracion;
    private ArrayList<CausasAlteracion> listaCausasAlteracion;
    private CausasAlteracion causasAlteracion;
    private ArrayList<AgentesAlteracion> listaAgentesAlteracion;
    private AgentesAlteracion agentesAlteracion;
    private ArrayList<RegimenPropiedad> listaRegimenPropiedad;
    private RegimenPropiedad regimenPropiedad;
    private ArrayList<GradoProteccion> listaGradoProteccion;
    private GradoProteccion gradoProteccion;
    private ArrayList<ModoProteccion> listaModoProteccion;
    private ModoProteccion modoProteccion;
    private ArrayList<ProteccionFisica> listaProteccionFisica;
    private ProteccionFisica proteccionFisica;

    private EditText editTextSituacionPatrimonialEstado;

    private Button buttonSituacionPatrimonialGuardar;
    private Bundle argumentosEnviados;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments()!=null)
        {
            usuarioLogueado=(Usuarios) getArguments().getSerializable("usuario");
            proyectoSeleccionado=(Proyectos)getArguments().getSerializable("proyecto");
            umtpSeleccionado=(Umtp) getArguments().getSerializable("umtp");
            usuarioEnviado= getArguments();
            idUmtp=umtpSeleccionado.getUmtp_id();
            Carpetas carpeta=new Carpetas();
            System.out.println("PROYECTO SELECCIONADO AL VOLVER ES---------: "+ proyectoSeleccionado.getProyecto_id());
            System.out.println( carpeta.crearCarpeta("/Recolectarq/Proyectos/"+proyectoSeleccionado.getCodigo_identificacion()));
            carpeta.crearCarpeta("/Recolectarq/Proyectos/"+proyectoSeleccionado.getCodigo_identificacion()+"/UMTP");
            carpeta.crearCarpeta("/Recolectarq/Proyectos/"+proyectoSeleccionado.getCodigo_identificacion()+"/MemoriasTecnicas");
            int idUsuario = Integer.parseInt(usuarioLogueado.getUsuario_id());

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
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
       // return super.onCreateView(inflater, container, savedInstanceState);
        final View v= inflater.inflate(R.layout.fragment_situacion_patrimonial,container, false);

        spinnerSituacionPatrimonialGradoAlteracion=v.findViewById(R.id.spinnerSituacionPatrimonialGradoAlteracion);
        spinnerSituacionPatrimonialCausasAlteracion=v.findViewById(R.id.spinnerSituacionPatrimonialCausasAlteracion);
        spinnerSituacionPatrimonialAgentesAlteracion=v.findViewById(R.id.spinnerSituacionPatrimonialAgentesAlteracion);
        spinnerSituacionPatrimonialRegimenPropiedad=v.findViewById(R.id.spinnerSituacionPatrimonialRegimenPropiedad);
        spinnerSituacionPatrimonialGradoProteccion=v.findViewById(R.id.spinnerSituacionPatrimonialGradoProteccion);
        spinnerSituacionPatrimonialModoProteccion=v.findViewById(R.id.spinnerSituacionPatrimonialModoProteccion);
        spinnerSituacionPatrimonialProteccionFisica=v.findViewById(R.id.spinnerSituacionPatrimonialProteccionFisica);

        editTextSituacionPatrimonialEstado=v.findViewById(R.id.editTextSituacionPatrimonialEstado);
        buttonSituacionPatrimonialGuardar=v.findViewById(R.id.buttonSituacionPatrimonialGuardar);

        listaGradoAlteracion=new ArrayList<>();
        listaCausasAlteracion=new ArrayList<>();
        listaAgentesAlteracion=new ArrayList<>();
        listaRegimenPropiedad=new ArrayList<>();
        listaGradoProteccion=new ArrayList<>();
        listaModoProteccion=new ArrayList<>();
        listaProteccionFisica=new ArrayList<>();

        CargarGradoAlteracion();
        cargarCausasAlteracion();
        cargarAgentesAlteracion();
        cargarRegimenPropiedad();
        cargarGradoProteccion();
        cargarModoProteccion();
        cargarProteccionFisica();

        buttonSituacionPatrimonialGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                camposLlenos();
            }
        });

        return v;
    }

    private void camposLlenos()
    {
        if(editTextSituacionPatrimonialEstado.getText().toString().trim().isEmpty() || idGradoAlteracion==-1
                || idCausaAlteracion==-1 || idAgenteAlteracion==-1 || idRegimenPropiedad==-1 || idGradoProteccion==-1
                || idModoProteccion==-1 || idProteccionFisica==-1)
        {
            Toast.makeText(getContext(),"Hay campos sin diligenciar",Toast.LENGTH_LONG).show();
        }else
        {
            webServiceActualizarPlanManejo();
        }
    }

    private void webServiceActualizarPlanManejo()
    {
        /*final ProgressDialog pDialog=new ProgressDialog(getContext());
        pDialog.setMessage("Cargando...");
        pDialog.show();*/

        String ip=getString(R.string.ip_url);

        String url=ip+"/web_services/editar_plan_manejo.php?";

        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //pDialog.hide();

                if (response.trim().equalsIgnoreCase("actualiza")){
                    // etiNombre.setText("");
                    //  txtDocumento.setText("");
                    //   etiProfesion.setText("");
                    Toast.makeText(getContext(),"Se ha insertado con exito",Toast.LENGTH_SHORT).show();
                    /*VerRecoleccionSuperficialFragment recoleccion= new VerRecoleccionSuperficialFragment();
                    FragmentManager manager= getFragmentManager();
                    argumentosEnviados= new Bundle();
                    argumentosEnviados.putSerializable("usuario",usuarioLogueado);
                    argumentosEnviados.putSerializable("proyecto",proyectoSeleccionado);
                    argumentosEnviados.putSerializable("umtp",umtpSeleccionado);
                    argumentosEnviados.putSerializable("recoleccion",recoleccionSeleccionado);
                    recoleccion.setArguments(argumentosEnviados);
                    manager.beginTransaction().replace(R.id.contenidos, recoleccion).commit();
                    System.out.println("LA UMTP SELECCIONADA ES: "+umtpSeleccionado.getUmtp_id());*/

                    argumentosEnviados= new Bundle();
                    argumentosEnviados.putSerializable("proyecto",proyectoSeleccionado);
                    argumentosEnviados.putSerializable("usuario",usuarioLogueado);
                    argumentosEnviados.putSerializable("umtp",umtpSeleccionado);
                    ValoracionImpactoFragment valoracion= new ValoracionImpactoFragment();
                    valoracion.setArguments(argumentosEnviados);
                    FragmentManager manager= getFragmentManager();
                    manager.beginTransaction().replace(R.id.contenidos, valoracion).commit();

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
                parametros.put("origen",3+"");
                parametros.put("estado_conservacion",editTextSituacionPatrimonialEstado.getText().toString().trim());
                parametros.put("grados_alteraciones_id",idGradoAlteracion+"");
                parametros.put("causas_alteraciones_id",idCausaAlteracion+"");
                parametros.put("agentes_alteraciones_id",idAgenteAlteracion+"");
                parametros.put("regimenes_propiedades_id",idRegimenPropiedad+"");
                parametros.put("grados_protecciones_id",idGradoProteccion+"");
                parametros.put("modos_protecciones_id",idModoProteccion+"");
                parametros.put("protecciones_fisicas_id",idProteccionFisica+"");
                parametros.put("umtp_id",idUmtp+"");
                //parametros.put("imagen",imagen);

                return parametros;
            }
        };
        //request.add(stringRequest);
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(stringRequest);
    }

    private void CargarGradoAlteracion() {
        /*final ProgressDialog pDialog;
        pDialog=new ProgressDialog(getContext());
        pDialog.setMessage("Cargando...");
        pDialog.show();*/

        final String ip=getString(R.string.ip_url);

        String url=ip+"/web_services/grados_alteraciones.php";

        System.out.println(url);

        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //pDialog.hide();

                JSONArray json=response.optJSONArray("grados_alteraciones");
                JSONObject jsonObject;
                List<String> listTipos=new ArrayList<String>();
                listTipos.add("Seleccione...");
                try {
                    for (int i=0;i<json.length();i++) {
                        jsonObject = json.getJSONObject(i);


                        gradoAlteracion=new GradoAlteracion(jsonObject.getInt("id"), jsonObject.getString("nombre"));

                        System.out.println(jsonObject.getString("nombre")+ "  "+ i+json.length());
                        listaGradoAlteracion.add(gradoAlteracion);
                        listTipos.add(jsonObject.getString("nombre"));

                    }
                    ArrayAdapter<CharSequence> spinnerArrayAdapter= new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, listTipos);
                    spinnerSituacionPatrimonialGradoAlteracion.setAdapter(spinnerArrayAdapter);

                    spinnerSituacionPatrimonialGradoAlteracion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            if (position!=0) {
                                System.out.println(listaGradoAlteracion.get(position - 1).getId());
                                idGradoAlteracion=listaGradoAlteracion.get(position - 1).getId();
                            }else
                            {
                                idGradoAlteracion=-1;
                                System.out.println(idGradoAlteracion);
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

    private void cargarCausasAlteracion() {
        /*final ProgressDialog pDialog;
        pDialog=new ProgressDialog(getContext());
        pDialog.setMessage("Cargando...");
        pDialog.show();*/

        final String ip=getString(R.string.ip_url);

        String url=ip+"/web_services/causas_alteraciones.php";

        System.out.println(url);

        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //pDialog.hide();

                JSONArray json=response.optJSONArray("causas_alteraciones");
                JSONObject jsonObject;
                List<String> listTipos=new ArrayList<String>();
                listTipos.add("Seleccione...");
                try {
                    for (int i=0;i<json.length();i++) {
                        jsonObject = json.getJSONObject(i);


                        causasAlteracion=new CausasAlteracion(jsonObject.getInt("id"), jsonObject.getString("nombre"));

                        System.out.println(jsonObject.getString("nombre")+ "  "+ i+json.length());
                        listaCausasAlteracion.add(causasAlteracion);
                        listTipos.add(jsonObject.getString("nombre"));

                    }
                    ArrayAdapter<CharSequence> spinnerArrayAdapter= new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, listTipos);
                    spinnerSituacionPatrimonialCausasAlteracion.setAdapter(spinnerArrayAdapter);

                    spinnerSituacionPatrimonialCausasAlteracion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            if (position!=0) {
                                System.out.println(listaCausasAlteracion.get(position - 1).getId());
                                idCausaAlteracion=listaCausasAlteracion.get(position - 1).getId();
                            }else
                            {
                                idCausaAlteracion=-1;
                                System.out.println(idCausaAlteracion);
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

    private void cargarAgentesAlteracion() {
        /*final ProgressDialog pDialog;
        pDialog=new ProgressDialog(getContext());
        pDialog.setMessage("Cargando...");
        pDialog.show();*/

        final String ip=getString(R.string.ip_url);

        String url=ip+"/web_services/agentes_alteraciones.php";

        System.out.println(url);

        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //pDialog.hide();

                JSONArray json=response.optJSONArray("agentes_alteraciones");
                JSONObject jsonObject;
                List<String> listTipos=new ArrayList<String>();
                listTipos.add("Seleccione...");
                try {
                    for (int i=0;i<json.length();i++) {
                        jsonObject = json.getJSONObject(i);


                        agentesAlteracion=new AgentesAlteracion(jsonObject.getInt("id"), jsonObject.getString("nombre"));

                        System.out.println(jsonObject.getString("nombre")+ "  "+ i+json.length());
                        listaAgentesAlteracion.add(agentesAlteracion);
                        listTipos.add(jsonObject.getString("nombre"));

                    }
                    ArrayAdapter<CharSequence> spinnerArrayAdapter= new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, listTipos);
                    spinnerSituacionPatrimonialAgentesAlteracion.setAdapter(spinnerArrayAdapter);

                    spinnerSituacionPatrimonialAgentesAlteracion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            if (position!=0) {
                                System.out.println(listaAgentesAlteracion.get(position - 1).getId());
                                idAgenteAlteracion=listaAgentesAlteracion.get(position - 1).getId();
                            }else
                            {
                                idAgenteAlteracion=-1;
                                System.out.println(idAgenteAlteracion);
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

    private void cargarRegimenPropiedad() {
        /*final ProgressDialog pDialog;
        pDialog=new ProgressDialog(getContext());
        pDialog.setMessage("Cargando...");
        pDialog.show();*/

        final String ip=getString(R.string.ip_url);

        String url=ip+"/web_services/regimenes_propiedades.php";

        System.out.println(url);

        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //pDialog.hide();

                JSONArray json=response.optJSONArray("regimenes_propiedades");
                JSONObject jsonObject;
                List<String> listTipos=new ArrayList<String>();
                listTipos.add("Seleccione...");
                try {
                    for (int i=0;i<json.length();i++) {
                        jsonObject = json.getJSONObject(i);


                        regimenPropiedad=new RegimenPropiedad(jsonObject.getInt("id"), jsonObject.getString("nombre"));

                        System.out.println(jsonObject.getString("nombre")+ "  "+ i+json.length());
                        listaRegimenPropiedad.add(regimenPropiedad);
                        listTipos.add(jsonObject.getString("nombre"));

                    }
                    ArrayAdapter<CharSequence> spinnerArrayAdapter= new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, listTipos);
                    spinnerSituacionPatrimonialRegimenPropiedad.setAdapter(spinnerArrayAdapter);

                    spinnerSituacionPatrimonialRegimenPropiedad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            if (position!=0) {
                                System.out.println(listaRegimenPropiedad.get(position - 1).getId());
                                idRegimenPropiedad=listaRegimenPropiedad.get(position - 1).getId();
                            }else
                            {
                                idRegimenPropiedad=-1;
                                System.out.println(idRegimenPropiedad);
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

    private void cargarGradoProteccion() {
        /*final ProgressDialog pDialog;
        pDialog=new ProgressDialog(getContext());
        pDialog.setMessage("Cargando...");
        pDialog.show();*/

        final String ip=getString(R.string.ip_url);

        String url=ip+"/web_services/grados_protecciones.php";

        System.out.println(url);

        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //pDialog.hide();

                JSONArray json=response.optJSONArray("grados_protecciones");
                JSONObject jsonObject;
                List<String> listTipos=new ArrayList<String>();
                listTipos.add("Seleccione...");
                try {
                    for (int i=0;i<json.length();i++) {
                        jsonObject = json.getJSONObject(i);


                        gradoProteccion=new GradoProteccion(jsonObject.getInt("id"), jsonObject.getString("nombre"));

                        System.out.println(jsonObject.getString("nombre")+ "  "+ i+json.length());
                        listaGradoProteccion.add(gradoProteccion);
                        listTipos.add(jsonObject.getString("nombre"));

                    }
                    ArrayAdapter<CharSequence> spinnerArrayAdapter= new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, listTipos);
                    spinnerSituacionPatrimonialGradoProteccion.setAdapter(spinnerArrayAdapter);

                    spinnerSituacionPatrimonialGradoProteccion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            if (position!=0) {
                                System.out.println(listaGradoProteccion.get(position - 1).getId());
                                idGradoProteccion=listaGradoProteccion.get(position - 1).getId();
                            }else
                            {
                                idGradoProteccion=-1;
                                System.out.println(idGradoProteccion);
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

    private void cargarModoProteccion() {
        /*final ProgressDialog pDialog;
        pDialog=new ProgressDialog(getContext());
        pDialog.setMessage("Cargando...");
        pDialog.show();*/

        final String ip=getString(R.string.ip_url);

        String url=ip+"/web_services/modos_protecciones.php";

        System.out.println(url);

        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //pDialog.hide();

                JSONArray json=response.optJSONArray("modos_protecciones");
                JSONObject jsonObject;
                List<String> listTipos=new ArrayList<String>();
                listTipos.add("Seleccione...");
                try {
                    for (int i=0;i<json.length();i++) {
                        jsonObject = json.getJSONObject(i);


                        modoProteccion=new ModoProteccion(jsonObject.getInt("id"), jsonObject.getString("nombre"));

                        System.out.println(jsonObject.getString("nombre")+ "  "+ i+json.length());
                        listaModoProteccion.add(modoProteccion);
                        listTipos.add(jsonObject.getString("nombre"));

                    }
                    ArrayAdapter<CharSequence> spinnerArrayAdapter= new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, listTipos);
                    spinnerSituacionPatrimonialModoProteccion.setAdapter(spinnerArrayAdapter);

                    spinnerSituacionPatrimonialModoProteccion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            if (position!=0) {
                                System.out.println(listaModoProteccion.get(position - 1).getId());
                                idModoProteccion=listaModoProteccion.get(position - 1).getId();
                            }else
                            {
                                idModoProteccion=-1;
                                System.out.println(idModoProteccion);
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

    private void cargarProteccionFisica() {
        /*final ProgressDialog pDialog;
        pDialog=new ProgressDialog(getContext());
        pDialog.setMessage("Cargando...");
        pDialog.show();*/

        final String ip=getString(R.string.ip_url);

        String url=ip+"/web_services/protecciones_fisicas.php";

        System.out.println(url);

        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //pDialog.hide();

                JSONArray json=response.optJSONArray("protecciones_fisicas");
                JSONObject jsonObject;
                List<String> listTipos=new ArrayList<String>();
                listTipos.add("Seleccione...");
                try {
                    for (int i=0;i<json.length();i++) {
                        jsonObject = json.getJSONObject(i);


                        proteccionFisica=new ProteccionFisica(jsonObject.getInt("id"), jsonObject.getString("nombre"));

                        System.out.println(jsonObject.getString("nombre")+ "  "+ i+json.length());
                        listaProteccionFisica.add(proteccionFisica);
                        listTipos.add(jsonObject.getString("nombre"));

                    }
                    ArrayAdapter<CharSequence> spinnerArrayAdapter= new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, listTipos);
                    spinnerSituacionPatrimonialProteccionFisica.setAdapter(spinnerArrayAdapter);

                    spinnerSituacionPatrimonialProteccionFisica.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            if (position!=0) {
                                System.out.println(listaProteccionFisica.get(position - 1).getId());
                                idProteccionFisica=listaProteccionFisica.get(position - 1).getId();
                            }else
                            {
                                idProteccionFisica=-1;
                                System.out.println(idProteccionFisica);
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


}
