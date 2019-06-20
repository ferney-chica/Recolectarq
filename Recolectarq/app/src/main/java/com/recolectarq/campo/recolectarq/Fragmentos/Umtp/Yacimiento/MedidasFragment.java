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
import com.recolectarq.campo.recolectarq.Fragmentos.Umtp.UmtpFragment;
import com.recolectarq.campo.recolectarq.Modelo.IntervencionesRecomendadas;
import com.recolectarq.campo.recolectarq.Modelo.Proyectos;
import com.recolectarq.campo.recolectarq.Modelo.PuntualesVisibilidades;
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

public class MedidasFragment extends Fragment  {

    private int idUsuario;
    private int idUmtp;

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

    private int idIntervencionRecomendada;
    private Spinner spinnerMedidasTipoIntervencion;
    private ArrayList<IntervencionesRecomendadas> listaIntervenciones;
    private IntervencionesRecomendadas intervencion;

    private EditText editTextMedidasPreventivas;
    private EditText editTextMedidasActuacionRecomendada;
    private EditText editTextMedidasEjecucion;
    private EditText editTextMedidasCorrectoras;
    private EditText editTextMedidasActuacionMinima;
    private EditText editTextMedidasJustificacion;
    private EditText editTextMedidasEjecucionCorrectoras;
    private EditText editTextMedidasRevision;

    private Button buttonMedidasGuardar;
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
        final View v= inflater.inflate(R.layout.fragment_medidas,container, false);
        spinnerMedidasTipoIntervencion=v.findViewById(R.id.spinnerMedidasTipoIntervencion);

        listaIntervenciones=new ArrayList<>();

        editTextMedidasPreventivas=v.findViewById(R.id.editTextMedidasPreventivas);
        editTextMedidasActuacionRecomendada=v.findViewById(R.id.editTextMedidasActuacionRecomendada);
        editTextMedidasEjecucion=v.findViewById(R.id.editTextMedidasEjecucion);
        editTextMedidasCorrectoras=v.findViewById(R.id.editTextMedidasCorrectoras);
        editTextMedidasActuacionMinima=v.findViewById(R.id.editTextMedidasActuacionMinima);
        editTextMedidasJustificacion=v.findViewById(R.id.editTextMedidasJustificacion);
        editTextMedidasEjecucionCorrectoras=v.findViewById(R.id.editTextMedidasEjecucionCorrectoras);
        editTextMedidasRevision=v.findViewById(R.id.editTextMedidasRevision);

        buttonMedidasGuardar=v.findViewById(R.id.buttonMedidasGuardar);


        cargarIntervenciones();
        buttonMedidasGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                camposLLenos();
            }
        });
        return v;
    }

    private void camposLLenos()
    {
        if(editTextMedidasPreventivas.getText().toString().trim().isEmpty() || editTextMedidasActuacionRecomendada.getText().toString().trim().isEmpty()
        || editTextMedidasEjecucion.getText().toString().trim().isEmpty() || editTextMedidasCorrectoras.getText().toString().trim().isEmpty()
                || editTextMedidasActuacionMinima.getText().toString().trim().isEmpty() || editTextMedidasJustificacion.getText().toString().trim().isEmpty()
                || editTextMedidasEjecucionCorrectoras.getText().toString().trim().isEmpty() || editTextMedidasRevision.getText().toString().trim().isEmpty()
        || idIntervencionRecomendada==-1)
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
                    UmtpFragment umtp= new UmtpFragment();
                    umtp.setArguments(argumentosEnviados);
                    FragmentManager manager= getFragmentManager();
                    manager.beginTransaction().replace(R.id.contenidos, umtp).commit();

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
                parametros.put("origen",5+"");
                parametros.put("medida_preventiva",editTextMedidasPreventivas.getText().toString().trim());
                parametros.put("actuacion_recomendada",editTextMedidasActuacionRecomendada.getText().toString().trim());
                parametros.put("ejecucion_medida_preventiva",editTextMedidasEjecucion.getText().toString().trim());
                parametros.put("medida_correctora",editTextMedidasCorrectoras.getText().toString().trim());
                parametros.put("actuacion_minima",editTextMedidasActuacionMinima.getText().toString().trim());
                parametros.put("justificacion",editTextMedidasJustificacion.getText().toString().trim());
                parametros.put("ejecucion_medida_correctora",editTextMedidasEjecucionCorrectoras.getText().toString().trim());
                parametros.put("revision",editTextMedidasRevision.getText().toString().trim());
                parametros.put("intervenciones_recomendadas_id",idIntervencionRecomendada+"");
                parametros.put("umtp_id",idUmtp+"");
                //parametros.put("imagen",imagen);

                return parametros;
            }
        };
        //request.add(stringRequest);
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(stringRequest);
    }

    private void cargarIntervenciones() {
        /*final ProgressDialog pDialog;
        pDialog=new ProgressDialog(getContext());
        pDialog.setMessage("Cargando...");
        pDialog.show();*/

        final String ip=getString(R.string.ip_url);

        String url=ip+"/web_services/intervenciones_recomendadas.php";

        System.out.println(url);

        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //pDialog.hide();

                JSONArray json=response.optJSONArray("intervenciones_recomendadas");
                JSONObject jsonObject;
                List<String> listTipos=new ArrayList<String>();
                listTipos.add("Seleccione...");
                try {
                    for (int i=0;i<json.length();i++) {
                        jsonObject = json.getJSONObject(i);


                        intervencion=new IntervencionesRecomendadas(jsonObject.getInt("id"), jsonObject.getString("nombre"));

                        System.out.println(jsonObject.getString("nombre")+ "  "+ i+json.length());
                        listaIntervenciones.add(intervencion);
                        listTipos.add(jsonObject.getString("nombre"));

                    }
                    ArrayAdapter<CharSequence> spinnerArrayAdapter= new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, listTipos);
                    spinnerMedidasTipoIntervencion.setAdapter(spinnerArrayAdapter);

                    spinnerMedidasTipoIntervencion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            if (position!=0) {
                                System.out.println(listaIntervenciones.get(position - 1).getId());
                                idIntervencionRecomendada=listaIntervenciones.get(position - 1).getId();
                            }else
                            {
                                idIntervencionRecomendada=-1;
                                System.out.println(idIntervencionRecomendada);
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
