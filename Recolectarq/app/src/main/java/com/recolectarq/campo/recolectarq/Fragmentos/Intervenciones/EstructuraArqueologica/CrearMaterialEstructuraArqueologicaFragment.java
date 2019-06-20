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
import com.recolectarq.campo.recolectarq.Fragmentos.Intervenciones.PerfilesExpuestos.VerPerfilExpuestoFragment;
import com.recolectarq.campo.recolectarq.Fragmentos.Intervenciones.PozosSondeo.VerPozoSondeoFragment;
import com.recolectarq.campo.recolectarq.Fragmentos.Intervenciones.RecoleccionesSuperficiales.VerRecoleccionSuperficialFragment;
import com.recolectarq.campo.recolectarq.Modelo.EstructurasArqueologicas;
import com.recolectarq.campo.recolectarq.Modelo.PerfilesExpuestos;
import com.recolectarq.campo.recolectarq.Modelo.PozosSondeo;
import com.recolectarq.campo.recolectarq.Modelo.Proyectos;
import com.recolectarq.campo.recolectarq.Modelo.RecoleccionesSuperficiales;
import com.recolectarq.campo.recolectarq.Modelo.TiposEstructuras;
import com.recolectarq.campo.recolectarq.Modelo.TiposMateriales;
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

public class CrearMaterialEstructuraArqueologicaFragment extends Fragment implements InicioUsuarioActivity.OnBackPressedListener {

    private TextView textViewCrearEstructuraTitulo;
    private Spinner spinnerCrearMaterialEstructuraTipoMaterial;


    private Button buttonCrearMaterialEstructura;


    private String descripcionPozo;

    private String tipoIntervencion;
    private String origen;
    private int idUmtp;
    private int usuarioCreadorPozo;
    private int idEstructura;

    private int idPozo;
    private int idRecoleccion;
    private int idPerfil;

    private TextView usuarioExistente;

    private RequestQueue request;
    private JsonRequest jrq;
    private ArrayList<TiposMateriales> listaTiposMateriales;
    private int idTipoMaterial;
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
    private TiposMateriales tipoMaterial;
    private TopologiasEstructuras topologiaEstructura;
    private EstructurasArqueologicas estructuraArqueologicaSeleccionada;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            Toast.makeText(getContext(), "entro", Toast.LENGTH_LONG);
            Toast.makeText(getContext(), "entro", Toast.LENGTH_LONG);
            usuarioLogueado = (Usuarios) getArguments().getSerializable("usuario");
            proyectoSeleccionado = (Proyectos) getArguments().getSerializable("proyecto");
            umtpSeleccionado = (Umtp) getArguments().getSerializable("umtp");
            estructuraArqueologicaSeleccionada = (EstructurasArqueologicas) getArguments().getSerializable("estructura");
            idEstructura=estructuraArqueologicaSeleccionada.getEstructuras_arqueologicas_id();
            origen = getArguments().getString("origen");

            switch (origen) {
                case "pozoEstructura":
                    System.out.println("Vengo desde pozo sondeo");
                    pozoSeleccionado = (PozosSondeo) getArguments().getSerializable("pozo");
                    idPozo=pozoSeleccionado.getPozo_id();
                    idPerfil=0;
                    idRecoleccion=0;
                    tipoIntervencion="PS";
                    break;

                case "perfilEstructura":
                    System.out.println("Vengo desde EstratoPerfil");
                    perfilSeleccionado = (PerfilesExpuestos) getArguments().getSerializable("perfil");
                    idPozo=0;
                    idPerfil=perfilSeleccionado.getPerfil_expuesto_id();
                    idRecoleccion=0;
                    tipoIntervencion="PE";
                    break;
                case "recoleccionEstructura":
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
        final View v = inflater.inflate(R.layout.fragment_crear_material_estructura_arqueologica, container, false);
        textViewCrearEstructuraTitulo = v.findViewById(R.id.textViewCrearEstructuraTitulo);
        spinnerCrearMaterialEstructuraTipoMaterial = v.findViewById(R.id.spinnerCrearMaterialEstructuraTipoMaterial);



        listaTiposMateriales = new ArrayList<>();

        consultarTiposMateriales();


        buttonCrearMaterialEstructura = v.findViewById(R.id.buttonCrearMaterialEstructura);
        buttonCrearMaterialEstructura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                camposLLenos();


            }
        });



        return v;
    }


    private void camposLLenos() {
        if (idTipoMaterial == -1 || idTopologiaEstructura == -1)
        {
            Toast.makeText(getContext(), "Hay campos sin diligenciar", Toast.LENGTH_LONG).show();
        } else {

            webServiceInsertarMaterialEstructura();
        }

    }


    private void consultarTiposMateriales() {
        /*final ProgressDialog pDialog;
        pDialog = new ProgressDialog(getContext());
        pDialog.setMessage("Cargando...");
        pDialog.show();*/

        final String ip = getString(R.string.ip_url);

        String url = ip + "/web_services/tipos_materiales.php";

        System.out.println(url);

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //pDialog.hide();

                JSONArray json = response.optJSONArray("tipos_materiales");
                JSONObject jsonObject;
                List<String> listTipos = new ArrayList<String>();
                listTipos.add("Seleccione...");
                try {
                    for (int i = 0; i < json.length(); i++) {
                        jsonObject = json.getJSONObject(i);


                        tipoMaterial = new TiposMateriales(jsonObject.getInt("id"), jsonObject.getString("nombre"));

                        System.out.println(jsonObject.getString("nombre") + "  " + i + json.length());
                        listaTiposMateriales.add(tipoMaterial);
                        listTipos.add(jsonObject.getString("nombre"));

                    }
                    ArrayAdapter<CharSequence> spinnerArrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, listTipos);
                    spinnerCrearMaterialEstructuraTipoMaterial.setAdapter(spinnerArrayAdapter);

                    spinnerCrearMaterialEstructuraTipoMaterial.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            if (position != 0) {
                                System.out.println(listaTiposMateriales.get(position - 1).getId());
                                idTipoMaterial = listaTiposMateriales.get(position - 1).getId();
                            } else {
                                idTipoMaterial = -1;
                                System.out.println(idTipoMaterial);
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




    private void webServiceInsertarMaterialEstructura() {
        /*final ProgressDialog pDialog = new ProgressDialog(getContext());
        pDialog.setMessage("Cargando...");
        pDialog.show();*/

        String ip = getString(R.string.ip_url);
        String url = ip + "/web_services/insertar_material_estructura_arqueologica.php?";


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

                            MaterialesEstructuraArqueologicaFragment materialEstructura = new MaterialesEstructuraArqueologicaFragment();

                            argumentosEnviados = new Bundle();
                            argumentosEnviados.putSerializable("usuario", usuarioLogueado);
                            argumentosEnviados.putSerializable("proyecto", proyectoSeleccionado);
                            argumentosEnviados.putSerializable("umtp", umtpSeleccionado);
                            argumentosEnviados.putSerializable("estructura", estructuraArqueologicaSeleccionada);

                    switch (origen) {
                        case "pozoEstructura":
                            argumentosEnviados.putString("origen","pozoEstructura");
                            argumentosEnviados.putSerializable("pozo",pozoSeleccionado);

                            break;

                        case "perfilEstructura":
                            argumentosEnviados.putString("origen","perfilEstructura");
                            argumentosEnviados.putSerializable("perfil",perfilSeleccionado);

                            break;
                        case "recoleccionEstructura":
                            argumentosEnviados.putString("origen","recoleccionEstructura");
                            argumentosEnviados.putSerializable("recoleccion",recoleccionSeleccionado);
                            break;
                    }


                    materialEstructura.setArguments(argumentosEnviados);
                            manager.beginTransaction().replace(R.id.contenidos, materialEstructura).commit();

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

                parametros.put("estructuras_arqueologicas_id", idEstructura+"");
                parametros.put("tipos_materiales_id", idTipoMaterial + "");

                //parametros.put("imagen",imagen);


                return parametros;
            }
        };
        //request.add(stringRequest);
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(stringRequest);
    }


    @Override
    public void doBack() {


        Bundle argumentosEnviados;
        argumentosEnviados= new Bundle();
        argumentosEnviados.putSerializable("proyecto",proyectoSeleccionado);
        argumentosEnviados.putSerializable("usuario",usuarioLogueado);
        argumentosEnviados.putSerializable("umtp",umtpSeleccionado);
        argumentosEnviados.putSerializable("estructura",estructuraArqueologicaSeleccionada);

        switch (origen) {
            case "pozoEstructura":
                argumentosEnviados.putString("origen","pozoEstructura");
                argumentosEnviados.putSerializable("pozo",pozoSeleccionado);

                break;

            case "perfilEstructura":
                argumentosEnviados.putString("origen","perfilEstructura");
                argumentosEnviados.putSerializable("perfil",perfilSeleccionado);

                break;
            case "recoleccionEstructura":
                argumentosEnviados.putString("origen","recoleccionEstructura");
                argumentosEnviados.putSerializable("recoleccion",recoleccionSeleccionado);
                break;
        }

        MaterialesEstructuraArqueologicaFragment materialEstructura= new MaterialesEstructuraArqueologicaFragment();
        materialEstructura.setArguments(argumentosEnviados);
        FragmentManager manager= getFragmentManager();
        manager.beginTransaction().replace(R.id.contenidos, materialEstructura).commit();
    }
}
