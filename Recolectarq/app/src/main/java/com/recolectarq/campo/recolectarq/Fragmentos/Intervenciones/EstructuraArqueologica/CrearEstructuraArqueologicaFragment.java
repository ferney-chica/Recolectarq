package com.recolectarq.campo.recolectarq.Fragmentos.Intervenciones.EstructuraArqueologica;

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
import com.recolectarq.campo.recolectarq.Fragmentos.Intervenciones.PerfilesExpuestos.EstratosPerfilesExpuestos.EstratosPerfilesExpuestosFragment;
import com.recolectarq.campo.recolectarq.Fragmentos.Intervenciones.PerfilesExpuestos.VerPerfilExpuestoFragment;
import com.recolectarq.campo.recolectarq.Fragmentos.Intervenciones.PozosSondeo.EstratosPozoSondeo.EstratosPozosSondeoFragment;
import com.recolectarq.campo.recolectarq.Fragmentos.Intervenciones.PozosSondeo.VerPozoSondeoFragment;
import com.recolectarq.campo.recolectarq.Fragmentos.Intervenciones.RecoleccionesSuperficiales.VerRecoleccionSuperficialFragment;
import com.recolectarq.campo.recolectarq.Modelo.EstructurasEstratos;
import com.recolectarq.campo.recolectarq.Modelo.PerfilesExpuestos;
import com.recolectarq.campo.recolectarq.Modelo.PozosSondeo;
import com.recolectarq.campo.recolectarq.Modelo.Proyectos;
import com.recolectarq.campo.recolectarq.Modelo.RecoleccionesSuperficiales;
import com.recolectarq.campo.recolectarq.Modelo.TexturasEstratos;
import com.recolectarq.campo.recolectarq.Modelo.TiposEstratos;
import com.recolectarq.campo.recolectarq.Modelo.TiposEstructuras;
import com.recolectarq.campo.recolectarq.Modelo.TopologiasEstructuras;
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

public class CrearEstructuraArqueologicaFragment extends Fragment implements InicioUsuarioActivity.OnBackPressedListener {

    private TextView textViewCrearEstructuraTitulo;
    private Spinner spinnerCrearEstructuraTipoEstructura;
    private Spinner spinnerCrearEstructuraTopologiaEstructura;
    private EditText editTextCrearEstructuraDescripcion;
    private EditText editTextCrearEstructuraPuntoConexo;
    private EditText editTextCrearEstructuraUtmx;
    private EditText editTextCrearEstructuraUtmy;
    private EditText editTextCrearEstructuraLatitud;
    private EditText editTextCrearEstructuraLongitud;
    private EditText editTextCrearEstructuraDimension;
    private EditText editTextCrearEstructuraEntorno;

    private Button buttonCrearEstructura;


    private String descripcionPozo;

    private String tipoIntervencion;
    private String origen;
    private int idUmtp;
    private int usuarioCreadorPozo;

    private int idPozo;
    private int idRecoleccion;
    private int idPerfil;

    private TextView usuarioExistente;

    private RequestQueue request;
    private JsonRequest jrq;
    private ArrayList<TiposEstructuras> listaTiposEstructuras;
    private int idTipoEstructura;
    private ArrayList<TopologiasEstructuras> listaTopologiasEstructuras;
    private int idTopologiaEstructura;


    private JsonObjectRequest jsonObjectRequest;


    private Usuarios usuarioLogueado;
    private Proyectos proyectoSeleccionado;
    private Umtp umtpSeleccionado;
    private PozosSondeo pozoSeleccionado;
    private PerfilesExpuestos perfilSeleccionado;
    private RecoleccionesSuperficiales recoleccionSeleccionado;
    private Bundle argumentosEnviados;
    private TiposEstructuras tipoEstructura;
    private TopologiasEstructuras topologiaEstructura;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            Toast.makeText(getContext(), "entro", Toast.LENGTH_LONG);
            Toast.makeText(getContext(), "entro", Toast.LENGTH_LONG);
            usuarioLogueado = (Usuarios) getArguments().getSerializable("usuario");
            proyectoSeleccionado = (Proyectos) getArguments().getSerializable("proyecto");
            umtpSeleccionado = (Umtp) getArguments().getSerializable("umtp");

            origen = getArguments().getString("origen");

            switch (origen) {
                case "pozo":
                    System.out.println("Vengo desde pozo sondeo");
                    pozoSeleccionado = (PozosSondeo) getArguments().getSerializable("pozo");
                    idPozo=pozoSeleccionado.getPozo_id();
                    idPerfil=0;
                    idRecoleccion=0;
                    tipoIntervencion="PS";
                    break;

                case "perfil":
                    System.out.println("Vengo desde EstratoPerfil");
                    perfilSeleccionado = (PerfilesExpuestos) getArguments().getSerializable("perfil");
                    idPozo=0;
                    idPerfil=perfilSeleccionado.getPerfil_expuesto_id();
                    idRecoleccion=0;
                    tipoIntervencion="PE";
                    break;
                case "recoleccion":
                    System.out.println("Vengo desde Recolección Superficial");
                    recoleccionSeleccionado = (RecoleccionesSuperficiales) getArguments().getSerializable("recoleccion");
                    idPozo=0;
                    idPerfil=0;
                    idRecoleccion=recoleccionSeleccionado.getrecoleccion_superficial_id();
                    tipoIntervencion="RS";
                    break;
            }

            /*Carpetas carpeta = new Carpetas();
            System.out.println(umtpSeleccionado.getIntervencion_id() + "DESDE CREARESTRATO --- ");
            System.out.println(carpeta.crearCarpeta("/Recolectarq/Proyectos/" + proyectoSeleccionado.getCodigo_identificacion()));
            carpeta.crearCarpeta("/Recolectarq/Proyectos/" + proyectoSeleccionado.getCodigo_identificacion() + "/UMTP");
            carpeta.crearCarpeta("/Recolectarq/Proyectos/" + proyectoSeleccionado.getCodigo_identificacion() + "/MemoriasTecnicas");*/
            usuarioCreadorPozo = Integer.parseInt(usuarioLogueado.getUsuario_id());
            idUmtp = umtpSeleccionado.getUmtp_id();

            System.out.println("El usuario CREADOR del POZO: " + usuarioLogueado.getNombre() + "con cedula " + usuarioCreadorPozo + " LA UMTP ES: " + idUmtp);
            System.out.println("PROYECTO");
            System.out.println("PROYECTO" + proyectoSeleccionado.getNombre());
            System.out.println("PROYECTO   CODIGO" + proyectoSeleccionado.getCodigo_identificacion());
        } else {
            usuarioLogueado = new Usuarios("null", "null", "null", "null");
            Toast.makeText(getContext(), "NO entroOOOOOOOO", Toast.LENGTH_LONG);
            Toast.makeText(getContext(), "NO entroOOOOOOOO", Toast.LENGTH_LONG);

            usuarioLogueado.setNombre("No hay usuario logueado");

        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // return super.onCreateView(inflater, container, savedInstanceState);
        ((InicioUsuarioActivity) getActivity()).setOnBackPressedListener(this);
        final View v = inflater.inflate(R.layout.fragment_crear_estructura_arqueologica, container, false);
        textViewCrearEstructuraTitulo = v.findViewById(R.id.textViewCrearEstructuraTitulo);
        spinnerCrearEstructuraTipoEstructura = v.findViewById(R.id.spinnerCrearEstructuraTipoEstructura);
        spinnerCrearEstructuraTopologiaEstructura = v.findViewById(R.id.spinnerCrearEstructuraTopologiaEstructura);
        editTextCrearEstructuraDescripcion = v.findViewById(R.id.editTextCrearEstructuraDescripcion);
        editTextCrearEstructuraPuntoConexo = v.findViewById(R.id.editTextCrearEstructuraPuntoConexo);

        editTextCrearEstructuraUtmx = v.findViewById(R.id.editTextCrearEstructuraUtmx);
        editTextCrearEstructuraUtmy = v.findViewById(R.id.editTextCrearEstructuraUtmy);
        editTextCrearEstructuraLatitud = v.findViewById(R.id.editTextCrearEstructuraLatitud);

        editTextCrearEstructuraLongitud = v.findViewById(R.id.editTextCrearEstructuraLongitud);
        editTextCrearEstructuraDimension = v.findViewById(R.id.editTextCrearEstructuraDimension);
        editTextCrearEstructuraEntorno = v.findViewById(R.id.editTextCrearEstructuraEntorno);


        listaTiposEstructuras = new ArrayList<>();
        listaTopologiasEstructuras = new ArrayList<>();

        consultarTiposEstructuras();
        consultarTopologiasEstructuras();


        buttonCrearEstructura = v.findViewById(R.id.buttonCrearEstructura);
        buttonCrearEstructura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                camposLLenos();


            }
        });


        switch (origen) {
            case "pozo":

                textViewCrearEstructuraTitulo.setText("Crear Estructura Arqueológica Pozo");
                buttonCrearEstructura.setText("Crear Estructura Pozo");
                break;

            case "perfil":
                textViewCrearEstructuraTitulo.setText("Creaar Estructura Arqueológica Perfil");
                buttonCrearEstructura.setText("Crear Estructura Perfil");
                break;

            case "recoleccion":
                textViewCrearEstructuraTitulo.setText("Creaar Estructura Arquitectura Recolección");
                buttonCrearEstructura.setText("Crear Estructura Recolección");
                break;
        }


        return v;
    }


    private void camposLLenos() {
        if (idTipoEstructura == -1 || idTopologiaEstructura == -1 || editTextCrearEstructuraDescripcion.getText().toString().trim().isEmpty() ||
                editTextCrearEstructuraPuntoConexo.getText().toString().trim().isEmpty() || editTextCrearEstructuraUtmx.getText().toString().trim().isEmpty() ||
                editTextCrearEstructuraUtmy.getText().toString().trim().isEmpty() || editTextCrearEstructuraLatitud.getText().toString().trim().isEmpty() ||
                editTextCrearEstructuraLongitud.getText().toString().trim().isEmpty() || editTextCrearEstructuraDimension.getText().toString().trim().isEmpty()
                || editTextCrearEstructuraEntorno.getText().toString().trim().isEmpty()) {
            Toast.makeText(getContext(), "Hay campos sin diligenciar", Toast.LENGTH_LONG).show();
        } else {

            webServiceInsertarEstructura();
        }

    }


    private void consultarTiposEstructuras() {
        /*final ProgressDialog pDialog;
        pDialog = new ProgressDialog(getContext());
        pDialog.setMessage("Cargando...");
        pDialog.show();*/

        final String ip = getString(R.string.ip_url);

        String url = ip + "/web_services/tipos_estructuras.php";

        System.out.println(url);

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //pDialog.hide();

                JSONArray json = response.optJSONArray("tipos_estructuras");
                JSONObject jsonObject;
                List<String> listTipos = new ArrayList<String>();
                listTipos.add("Seleccione...");
                try {
                    for (int i = 0; i < json.length(); i++) {
                        jsonObject = json.getJSONObject(i);


                        tipoEstructura = new TiposEstructuras(jsonObject.getInt("id"), jsonObject.getString("nombre"));

                        System.out.println(jsonObject.getString("nombre") + "  " + i + json.length());
                        listaTiposEstructuras.add(tipoEstructura);
                        listTipos.add(jsonObject.getString("nombre"));

                    }
                    ArrayAdapter<CharSequence> spinnerArrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, listTipos);
                    spinnerCrearEstructuraTipoEstructura.setAdapter(spinnerArrayAdapter);

                    spinnerCrearEstructuraTipoEstructura.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            if (position != 0) {
                                System.out.println(listaTiposEstructuras.get(position - 1).getId());
                                idTipoEstructura = listaTiposEstructuras.get(position - 1).getId();
                            } else {
                                idTipoEstructura = -1;
                                System.out.println(idTipoEstructura);
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                json = null;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "No se puede conectar " + error.toString(), Toast.LENGTH_LONG).show();
                System.out.println();
                //pDialog.hide();
                Log.d("ERROR: ", error.toString());
            }
        });

        // request.add(jsonObjectRequest);
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(jsonObjectRequest);

    }

    private void consultarTopologiasEstructuras() {
        /*final ProgressDialog pDialog;
        pDialog = new ProgressDialog(getContext());
        pDialog.setMessage("Cargando...");
        pDialog.show();*/

        final String ip = getString(R.string.ip_url);

        String url = ip + "/web_services/topologias_estructuras.php";

        System.out.println(url);

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //pDialog.hide();

                JSONArray json = response.optJSONArray("topologias_estructuras");
                JSONObject jsonObject;
                List<String> listTipos = new ArrayList<String>();
                listTipos.add("Seleccione...");
                try {
                    for (int i = 0; i < json.length(); i++) {
                        jsonObject = json.getJSONObject(i);


                        topologiaEstructura = new TopologiasEstructuras(jsonObject.getInt("id"), jsonObject.getString("nombre"));

                        System.out.println(jsonObject.getString("nombre") + "  " + i + json.length());
                        listaTopologiasEstructuras.add(topologiaEstructura);
                        listTipos.add(jsonObject.getString("nombre"));

                    }
                    ArrayAdapter<CharSequence> spinnerArrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, listTipos);
                    spinnerCrearEstructuraTopologiaEstructura.setAdapter(spinnerArrayAdapter);

                    spinnerCrearEstructuraTopologiaEstructura.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            if (position != 0) {
                                System.out.println(listaTopologiasEstructuras.get(position - 1).getId());
                                idTopologiaEstructura = listaTopologiasEstructuras.get(position - 1).getId();
                            } else {
                                idTopologiaEstructura = -1;
                                System.out.println(idTopologiaEstructura);
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                json = null;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "No se puede conectar " + error.toString(), Toast.LENGTH_LONG).show();
                System.out.println();
                //pDialog.hide();
                Log.d("ERROR: ", error.toString());
            }
        });

        // request.add(jsonObjectRequest);
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(jsonObjectRequest);

    }


    private void webServiceInsertarEstructura() {
        /*final ProgressDialog pDialog = new ProgressDialog(getContext());
        pDialog.setMessage("Cargando...");
        pDialog.show();*/

        String ip = getString(R.string.ip_url);
        String url = ip + "/web_services/insertar_estructura_arqueologica.php?";


        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //pDialog.hide();

                if (response.trim().equalsIgnoreCase("inserto")) {
                    // etiNombre.setText("");
                    //  txtDocumento.setText("");
                    //   etiProfesion.setText("");
                    Toast.makeText(getContext(), "Se ha insertado con exito", Toast.LENGTH_SHORT).show();
                    FragmentManager manager = getFragmentManager();
                    switch (origen) {

                        case "pozo":
                            VerPozoSondeoFragment pozo = new VerPozoSondeoFragment();

                            argumentosEnviados = new Bundle();
                            argumentosEnviados.putSerializable("usuario", usuarioLogueado);
                            argumentosEnviados.putSerializable("proyecto", proyectoSeleccionado);
                            argumentosEnviados.putSerializable("umtp", umtpSeleccionado);
                            argumentosEnviados.putSerializable("pozo", pozoSeleccionado);
                            pozo.setArguments(argumentosEnviados);
                            manager.beginTransaction().replace(R.id.contenidos, pozo).commit();
                            break;
                        case "perfil":
                            VerPerfilExpuestoFragment perfil = new VerPerfilExpuestoFragment();

                            argumentosEnviados = new Bundle();
                            argumentosEnviados.putSerializable("usuario", usuarioLogueado);
                            argumentosEnviados.putSerializable("proyecto", proyectoSeleccionado);
                            argumentosEnviados.putSerializable("umtp", umtpSeleccionado);
                            argumentosEnviados.putSerializable("perfil", perfilSeleccionado);
                            perfil.setArguments(argumentosEnviados);
                            manager.beginTransaction().replace(R.id.contenidos, perfil).commit();
                            break;

                        case "recoleccion":
                            VerRecoleccionSuperficialFragment recoleccion = new VerRecoleccionSuperficialFragment();

                            argumentosEnviados = new Bundle();
                            argumentosEnviados.putSerializable("usuario", usuarioLogueado);
                            argumentosEnviados.putSerializable("proyecto", proyectoSeleccionado);
                            argumentosEnviados.putSerializable("umtp", umtpSeleccionado);
                            argumentosEnviados.putSerializable("recoleccion", recoleccionSeleccionado);
                            recoleccion.setArguments(argumentosEnviados);
                            manager.beginTransaction().replace(R.id.contenidos, recoleccion).commit();
                            break;
                    }


                } else {
                    Toast.makeText(getContext(), "No se ha insertado el proyecto ", Toast.LENGTH_SHORT).show();
                    Log.i("RESPUESTA: ", "" + response);
                    System.out.println(response);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "No se ha podido conectar", Toast.LENGTH_SHORT).show();
                //pDialog.hide();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //String cedulaU=cedula.getText().toString();
                //String nombreU=nombre.getText().toString();
                //String apellidoU=apellido.getText().toString();

                //String imagen=convertirImgString(bitmap);
                Map<String, String> parametros = new HashMap<>();

                parametros.put("tipos_estructuras_id", idTipoEstructura+"");
                parametros.put("topologias_estructuras_id", idTopologiaEstructura + "");
                parametros.put("perfiles_expuestos_perfil_expuesto_id", idPerfil + "");
                parametros.put("recolecciones_superficiales_recoleccion_superficial_id", idRecoleccion + "");
                parametros.put("pozos_pozo_id", idPozo+"");
                parametros.put("descripcion", editTextCrearEstructuraDescripcion.getText().toString().trim());
                parametros.put("punto_conexo", editTextCrearEstructuraPuntoConexo.getText().toString().trim());
                parametros.put("utmx", editTextCrearEstructuraUtmx.getText().toString().trim());
                parametros.put("utmy", editTextCrearEstructuraUtmy.getText().toString().trim());
                parametros.put("latitud", editTextCrearEstructuraLatitud.getText().toString());
                parametros.put("longitud", editTextCrearEstructuraLongitud.getText().toString());
                parametros.put("dimension", editTextCrearEstructuraDimension.getText().toString());
                parametros.put("entorno", editTextCrearEstructuraEntorno.getText().toString());
                parametros.put("intervencion", tipoIntervencion);
                //parametros.put("imagen",imagen);


                return parametros;
            }
        };
        //request.add(stringRequest);
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(stringRequest);
    }


    @Override
    public void doBack() {
        switch (origen) {
            case "pozo":
                Bundle argumentosEnviados;
                argumentosEnviados = new Bundle();
                argumentosEnviados.putSerializable("proyecto", proyectoSeleccionado);
                argumentosEnviados.putSerializable("usuario", usuarioLogueado);
                argumentosEnviados.putSerializable("umtp", umtpSeleccionado);
                argumentosEnviados.putSerializable("pozo", pozoSeleccionado);

                VerPozoSondeoFragment verPozo = new VerPozoSondeoFragment();
                verPozo.setArguments(argumentosEnviados);
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.contenidos, verPozo).commit();

                break;

            case "perfil":
                argumentosEnviados= new Bundle();
                argumentosEnviados.putSerializable("proyecto",proyectoSeleccionado);
                argumentosEnviados.putSerializable("usuario",usuarioLogueado);
                argumentosEnviados.putSerializable("umtp",umtpSeleccionado);
                argumentosEnviados.putSerializable("perfil",perfilSeleccionado);

                VerPerfilExpuestoFragment verPerfil= new VerPerfilExpuestoFragment();
                verPerfil.setArguments(argumentosEnviados);
                manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.contenidos, verPerfil).commit();

                break;

            case "recoleccion":
                argumentosEnviados = new Bundle();
                argumentosEnviados.putSerializable("proyecto", proyectoSeleccionado);
                argumentosEnviados.putSerializable("usuario", usuarioLogueado);
                argumentosEnviados.putSerializable("umtp", umtpSeleccionado);
                argumentosEnviados.putSerializable("recoleccion", recoleccionSeleccionado);

                VerRecoleccionSuperficialFragment verRecoleccion = new VerRecoleccionSuperficialFragment();
                verRecoleccion.setArguments(argumentosEnviados);
                manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.contenidos, verRecoleccion).commit();
                break;
        }
    }
}
