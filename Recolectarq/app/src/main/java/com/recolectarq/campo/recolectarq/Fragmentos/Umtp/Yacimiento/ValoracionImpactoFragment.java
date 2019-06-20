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
import com.recolectarq.campo.recolectarq.Modelo.Impactos;
import com.recolectarq.campo.recolectarq.Modelo.LinealesVisibilidades;
import com.recolectarq.campo.recolectarq.Modelo.Proyectos;
import com.recolectarq.campo.recolectarq.Modelo.PuntualesVisibilidades;
import com.recolectarq.campo.recolectarq.Modelo.TipoImpacto;
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

public class ValoracionImpactoFragment extends Fragment  {

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

    private int idTipoImpacto;
    private int idImpacto;
    private int idUmtp;

    private Spinner spinnerValoracionImpactoTipoImpacto;
    private Spinner spinnerValoracionImpactoImpacto;

    private ArrayList<Impactos> listaImpactos;
    private Impactos impactos;
    private ArrayList<TipoImpacto> listaTipoImpacto;
    private TipoImpacto tipoImpacto;

    private EditText editTextValoracionImpactoSintesis;
    private EditText editTextValoracionImpactoHipotesis;
    private EditText editTextValoracionImpactoValoracionBien;
    private EditText editTextValoracionImpactoCautelas;
    private EditText editTextValoracionImpactoReferenciaImpacto;

    private Button buttonValoracionImpactoGuardar;
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
        final View v= inflater.inflate(R.layout.fragment_valoracion_impacto,container, false);

        spinnerValoracionImpactoTipoImpacto=v.findViewById(R.id.spinnerValoracionImpactoTipoImpacto);
        spinnerValoracionImpactoImpacto=v.findViewById(R.id.spinnerValoracionImpactoImpacto);

        editTextValoracionImpactoSintesis=v.findViewById(R.id.editTextValoracionImpactoSintesis);
        editTextValoracionImpactoHipotesis=v.findViewById(R.id.editTextValoracionImpactoHipotesis);
        editTextValoracionImpactoValoracionBien=v.findViewById(R.id.editTextValoracionImpactoValoracionBien);
        editTextValoracionImpactoCautelas=v.findViewById(R.id.editTextValoracionImpactoCautelas);
        editTextValoracionImpactoReferenciaImpacto=v.findViewById(R.id.editTextValoracionImpactoReferenciaImpacto);

        buttonValoracionImpactoGuardar=v.findViewById(R.id.buttonValoracionImpactoGuardar);

        listaImpactos=new ArrayList<>();
        listaTipoImpacto=new ArrayList<>();

        cargarImpactos();
        cargarTipoImpacto();

        buttonValoracionImpactoGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                camposLlenos();
            }
        });

        return v;
    }

    private void camposLlenos()
    {
        if(editTextValoracionImpactoSintesis.getText().toString().trim().isEmpty() || editTextValoracionImpactoHipotesis.getText().toString().trim().isEmpty()
        || editTextValoracionImpactoValoracionBien.getText().toString().trim().isEmpty() || editTextValoracionImpactoCautelas.getText().toString().trim().isEmpty()
        || editTextValoracionImpactoReferenciaImpacto.getText().toString().trim().isEmpty() || idTipoImpacto==-1 || idImpacto==-1)
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
                    MedidasFragment medidas= new MedidasFragment();
                    medidas.setArguments(argumentosEnviados);
                    FragmentManager manager= getFragmentManager();
                    manager.beginTransaction().replace(R.id.contenidos, medidas).commit();

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
                parametros.put("origen",4+"");
                parametros.put("sintesis_valoracion_evidencia",editTextValoracionImpactoSintesis.getText().toString().trim());
                parametros.put("hipotesis",editTextValoracionImpactoHipotesis.getText().toString().trim());
                parametros.put("valoracion_bien",editTextValoracionImpactoValoracionBien.getText().toString().trim());
                parametros.put("cautela",editTextValoracionImpactoCautelas.getText().toString().trim());
                parametros.put("referencia_impacto",editTextValoracionImpactoReferenciaImpacto.getText().toString().trim());
                parametros.put("tipos_impactos_id",idTipoImpacto+"");
                parametros.put("impactos_id",idImpacto+"");
                parametros.put("umtp_id",idUmtp+"");
                //parametros.put("imagen",imagen);

                return parametros;
            }
        };
        //request.add(stringRequest);
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(stringRequest);
    }
    private void cargarImpactos() {
        /*final ProgressDialog pDialog;
        pDialog=new ProgressDialog(getContext());
        pDialog.setMessage("Cargando...");
        pDialog.show();*/

        final String ip=getString(R.string.ip_url);

        String url=ip+"/web_services/impactos.php";

        System.out.println(url);

        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //pDialog.hide();

                JSONArray json=response.optJSONArray("impactos");
                JSONObject jsonObject;
                List<String> listTipos=new ArrayList<String>();
                listTipos.add("Seleccione...");
                try {
                    for (int i=0;i<json.length();i++) {
                        jsonObject = json.getJSONObject(i);


                        impactos=new Impactos(jsonObject.getInt("id"), jsonObject.getString("nombre"));

                        System.out.println(jsonObject.getString("nombre")+ "  "+ i+json.length());
                        listaImpactos.add(impactos);
                        listTipos.add(jsonObject.getString("nombre"));

                    }
                    ArrayAdapter<CharSequence> spinnerArrayAdapter= new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, listTipos);
                    spinnerValoracionImpactoImpacto.setAdapter(spinnerArrayAdapter);

                    spinnerValoracionImpactoImpacto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            if (position!=0) {
                                System.out.println(listaImpactos.get(position - 1).getId());
                                idImpacto=listaImpactos.get(position - 1).getId();
                            }else
                            {
                                idImpacto=-1;
                                System.out.println(idImpacto);
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

    private void cargarTipoImpacto() {
        /*final ProgressDialog pDialog;
        pDialog=new ProgressDialog(getContext());
        pDialog.setMessage("Cargando...");
        pDialog.show();*/

        final String ip=getString(R.string.ip_url);

        String url=ip+"/web_services/tipos_impactos.php";

        System.out.println(url);

        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //pDialog.hide();

                JSONArray json=response.optJSONArray("tipos_impactos");
                JSONObject jsonObject;
                List<String> listTipos=new ArrayList<String>();
                listTipos.add("Seleccione...");
                try {
                    for (int i=0;i<json.length();i++) {
                        jsonObject = json.getJSONObject(i);


                        tipoImpacto=new TipoImpacto(jsonObject.getInt("id"), jsonObject.getString("nombre"));

                        System.out.println(jsonObject.getString("nombre")+ "  "+ i+json.length());
                        listaTipoImpacto.add(tipoImpacto);
                        listTipos.add(jsonObject.getString("nombre"));

                    }
                    ArrayAdapter<CharSequence> spinnerArrayAdapter= new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, listTipos);
                    spinnerValoracionImpactoTipoImpacto.setAdapter(spinnerArrayAdapter);

                    spinnerValoracionImpactoTipoImpacto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            if (position!=0) {
                                System.out.println(listaTipoImpacto.get(position - 1).getId());
                                idTipoImpacto=listaTipoImpacto.get(position - 1).getId();
                            }else
                            {
                                idTipoImpacto=-1;
                                System.out.println(idTipoImpacto);
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
