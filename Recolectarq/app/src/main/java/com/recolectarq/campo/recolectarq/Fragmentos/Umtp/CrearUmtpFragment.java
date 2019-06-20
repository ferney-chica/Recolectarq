package com.recolectarq.campo.recolectarq.Fragmentos.Umtp;

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
import com.recolectarq.campo.recolectarq.Fragmentos.Proyectos.VerProyectoFragment;
import com.recolectarq.campo.recolectarq.Modelo.DedicacionesEntornos;
import com.recolectarq.campo.recolectarq.Modelo.Geoforma;
import com.recolectarq.campo.recolectarq.Modelo.Proyectos;
import com.recolectarq.campo.recolectarq.Modelo.TiposRelieves;
import com.recolectarq.campo.recolectarq.Modelo.Usuarios;
import com.recolectarq.campo.recolectarq.Modelo.Vegetaciones;
import com.recolectarq.campo.recolectarq.R;
import com.recolectarq.campo.recolectarq.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CrearUmtpFragment extends Fragment implements InicioUsuarioActivity.OnBackPressedListener{

    private EditText editTextCrearUmtpLargo;
    private EditText editTextCrearUmtpAncho;
    private EditText editTextCrearUmtpArea;
    private EditText editTextCrearUmtpAltura;
    private EditText editTextCrearUmtpUtmx;
    private EditText editTextCrearUmtpUtmy;

    private EditText editTextCrearUmtpGradosLatitud;
    private EditText editTextCrearUmtpMinutosLatitud;
    private EditText editTextCrearUmtpSegundosLatitud;

    private EditText editTextCrearUmtpGradosLongitud;
    private EditText editTextCrearUmtpMinutosLongitud;
    private EditText editTextCrearUmtpSegundosLongitud;


    private EditText editTextCrearUmtpDepartamento;
    private EditText editTextCrearUmtpMunicipio;
    private EditText editTextCrearUmtpVereda;
    private EditText editTextCrearUmtpSector;
    private EditText editTextCrearUmtpAccesos;
    private EditText editTextCrearUmtpZonaIncluyente;

    private Spinner spinnerCrearUmtpTipoRelieve;
    private Spinner spinnerCrearUmtpGeoforma;
    private Spinner spinnerCrearUmtpVegetacion;
    private Spinner spinnerCrearUmtpDedicacionEntorno;

    private ArrayList<Geoforma> listaGeoforma;
    private ArrayList<Vegetaciones> listaVegetaciones;
    private ArrayList<DedicacionesEntornos> listaDedicacion;
    private ArrayList<TiposRelieves> listaTiposRelieves;


    private Button btnCrearUmtp;
    private RequestQueue request;
    private JsonRequest jrq;
    private JsonObjectRequest jsonObjectRequest;

    private String largo;
    private String ancho;
    private String area;
    private String altura;
    private String utmx;
    private String utmy;

    private String latitud;
    private String longitud;

    private String latitudGrados;
    private String latitudMinutos;
    private String latitudSegundos;
    private String longitudGrados;
    private String longitudMinutos;
    private String longitudSegundos;

    private String municipio;
    private String departamento;
    private String vereda;
    private String sector;
    private String accesos;
    private String zonasIncluyentes;
    private int idGeoforma;
    private int idVegetacion;
    private int idDedicacion;
    private int idTipoRelieve;
    private int idProyecto;
    private int idUsuario;
    private Usuarios usuarioLogueado;
    private Proyectos proyectoSeleccionado;
    private Bundle usuarioEnviado;
    private Bundle argumentosEnviados;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("METODO ONCREATE DE CREAR UMTP");
        if (getArguments()!=null)
        {

            System.out.println("Entro En CREARUMTP con argumentos");
            usuarioLogueado=(Usuarios) getArguments().getSerializable("usuario");
            proyectoSeleccionado=(Proyectos) getArguments().getSerializable("proyecto");
            System.out.println("DESDE "+proyectoSeleccionado.getNombre()+ " Usuario logueado: "+ usuarioLogueado.getNombre());
            usuarioEnviado= getArguments();
            idProyecto=proyectoSeleccionado.getProyecto_id();
            idUsuario=Integer.parseInt(usuarioLogueado.getUsuario_id());

            //System.out.println("El usuario Logueado es desde crear es proyectos" + usuarioLogueado.getNombre() + "con cedula "+ idUsuario);
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
        final View v= inflater.inflate(R.layout.fragment_crear_umtp,container, false);

        editTextCrearUmtpLargo= v.findViewById(R.id.editTextCrearUmtpLargo);
        editTextCrearUmtpAncho= v.findViewById(R.id.editTextCrearUmtpAncho);
        editTextCrearUmtpArea= v.findViewById(R.id.editTextCrearUmtpArea);
        editTextCrearUmtpAltura= v.findViewById(R.id.editTextCrearUmtpAltura);
        editTextCrearUmtpUtmx= v.findViewById(R.id.editTextCrearUmtpUtmx);
        editTextCrearUmtpUtmy= v.findViewById(R.id.editTextCrearUmtpUtmy);


        editTextCrearUmtpGradosLatitud= v.findViewById(R.id.editTextCrearUmtpGradosLatitud);
        editTextCrearUmtpMinutosLatitud= v.findViewById(R.id.editTextCrearUmtpMinutosLatitud);
        editTextCrearUmtpSegundosLatitud= v.findViewById(R.id.editTextCrearUmtpSegundosLatitud);
        editTextCrearUmtpGradosLongitud= v.findViewById(R.id.editTextCrearUmtpGradosLongitud);
        editTextCrearUmtpMinutosLongitud= v.findViewById(R.id.editTextCrearUmtpMinutosLongitud);
        editTextCrearUmtpSegundosLongitud= v.findViewById(R.id.editTextCrearUmtpSegundosLongitud);


        editTextCrearUmtpDepartamento= v.findViewById(R.id.editTextCrearUmtpDepartamento);
        editTextCrearUmtpMunicipio= v.findViewById(R.id.editTextCrearUmtpMunicipio);
        editTextCrearUmtpVereda= v.findViewById(R.id.editTextCrearUmtpVereda);
        editTextCrearUmtpSector= v.findViewById(R.id.editTextCrearUmtpSector);
        editTextCrearUmtpAccesos= v.findViewById(R.id.editTextCrearUmtpAccesos);
        editTextCrearUmtpZonaIncluyente= v.findViewById(R.id.editTextCrearUmtpZonaIncluyente);
        spinnerCrearUmtpTipoRelieve=v.findViewById(R.id.spinnerCrearUmtpTipoRelieve);
        spinnerCrearUmtpGeoforma=v.findViewById(R.id.spinnerCrearUmtpGeoforma);
        spinnerCrearUmtpVegetacion=v.findViewById(R.id.spinnerCrearUmtpVegetacion);
        spinnerCrearUmtpDedicacionEntorno=v.findViewById(R.id.spinnerCrearUmtpDedicacionEntorno);
        btnCrearUmtp=v.findViewById(R.id.buttonCrearUmtp);
        listaGeoforma=new ArrayList<>();
        listaVegetaciones=new ArrayList<>();
        listaDedicacion=new ArrayList<>();
        listaTiposRelieves=new ArrayList<>();
        consultarGeoformas();
        consultarVegetaciones();
        consultarDedicaciones();
        consultarTiposRelieves();

        btnCrearUmtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"CLICK BOTON CREAR UMTP",Toast.LENGTH_LONG).show();
                if(camposNoVacios())
                {
                    webServiceInsertar();
                }else
                {

                    Toast.makeText(getContext(),"HAY CAMPOS SIN DILIGENCIAR",Toast.LENGTH_LONG).show();
                }
            }
        });

        /*nombre.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String s= nombre.getText().toString();

                if (s.toString().isEmpty())
                {
                    mensajeNombre.setText("Digite un nombre");
                    mensajeNombre.setVisibility(View.VISIBLE);
                }else {
                    //usuario.setText(s);

                }
            }
        });*/



        return v;
    }

    private boolean camposNoVacios() {
     boolean bandera=false;
        largo=editTextCrearUmtpLargo.getText().toString().trim();
        ancho= editTextCrearUmtpAncho.getText().toString().trim();
        area=editTextCrearUmtpArea.getText().toString().trim();
        altura=editTextCrearUmtpAltura.getText().toString().trim();
        utmx=editTextCrearUmtpUtmx.getText().toString().trim();
        utmy=editTextCrearUmtpUtmy.getText().toString().trim();

        latitudGrados=editTextCrearUmtpGradosLatitud.getText().toString().trim();
        latitudMinutos=editTextCrearUmtpMinutosLatitud.getText().toString().trim();
        latitudSegundos= editTextCrearUmtpSegundosLatitud.getText().toString().trim();
        longitudGrados=editTextCrearUmtpGradosLongitud.getText().toString().trim();
        longitudMinutos=editTextCrearUmtpMinutosLongitud.getText().toString().trim();
        longitudSegundos= editTextCrearUmtpSegundosLongitud.getText().toString().trim();

        latitud=editTextCrearUmtpGradosLatitud.getText().toString().trim()+"°"+
                editTextCrearUmtpMinutosLatitud.getText().toString().trim()+"'"+
                editTextCrearUmtpSegundosLatitud.getText().toString().trim()+"\"";
        longitud=editTextCrearUmtpGradosLongitud.getText().toString().trim()+"°"+
                editTextCrearUmtpMinutosLongitud.getText().toString().trim()+"'"+
                editTextCrearUmtpSegundosLongitud.getText().toString().trim()+"\"";

        municipio=editTextCrearUmtpMunicipio.getText().toString().trim();
        departamento=editTextCrearUmtpDepartamento.getText().toString().trim();
        vereda=editTextCrearUmtpVereda.getText().toString().trim();
        sector=editTextCrearUmtpSector.getText().toString().trim();
        accesos=editTextCrearUmtpAccesos.getText().toString().trim();
        zonasIncluyentes=editTextCrearUmtpZonaIncluyente.getText().toString().trim();

        System.out.println(largo + " " + ancho +" " + area +" " + altura +" " + utmx +" " + utmy + " " +latitud +" "
                + " " + longitud + " " + municipio + " " + departamento +
                 vereda + " " +sector + accesos + " " +zonasIncluyentes+ " " + idGeoforma+ " " + idVegetacion+ " " + idDedicacion);

        if (largo.isEmpty()||ancho.isEmpty()||area.isEmpty()||altura.isEmpty()|| utmx.isEmpty()|| utmy.isEmpty()||latitudGrados .isEmpty()||latitudMinutos .isEmpty()||latitudSegundos .isEmpty()||
                longitudGrados.isEmpty()|| longitudMinutos.isEmpty()|| longitudSegundos.isEmpty()|| municipio.isEmpty()|| departamento.isEmpty()||
                vereda.isEmpty()||sector.isEmpty() || accesos.isEmpty() || zonasIncluyentes.isEmpty()|| idGeoforma==-1 || idVegetacion==-1 || idDedicacion==-1)
        {
            bandera=false;
        }else
        {
            bandera=true;
        }

     System.out.println(bandera);
     return bandera;
    }


    private void consultarGeoformas() {
        /*final ProgressDialog pDialog;
        pDialog=new ProgressDialog(getContext());
        pDialog.setMessage("Cargando...");
        pDialog.show();*/

        final String ip=getString(R.string.ip_url);

        String url=ip+"/web_services/geoforma.php";

        System.out.println(url);

        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //pDialog.hide();

                Geoforma geoforma;

                JSONArray json=response.optJSONArray("geoforma");
                JSONObject jsonObject;
                List<String> listTipos=new ArrayList<String>();
                listTipos.add("Seleccione...");
                try {
                    for (int i=0;i<json.length();i++) {
                        jsonObject = json.getJSONObject(i);


                        geoforma=new Geoforma(jsonObject.getInt("id"), jsonObject.getString("nombre"));

                        System.out.println(jsonObject.getString("nombre")+ "  "+ i+json.length());
                        listaGeoforma.add(geoforma);
                        listTipos.add(jsonObject.getString("nombre"));

                    }
                    ArrayAdapter<CharSequence> spinnerArrayAdapter= new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, listTipos);
                    spinnerCrearUmtpGeoforma.setAdapter(spinnerArrayAdapter);

                    spinnerCrearUmtpGeoforma.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            if (position!=0) {
                                System.out.println(listaGeoforma.get(position - 1).getId());
                                idGeoforma=listaGeoforma.get(position - 1).getId();
                            }else
                            {
                                idGeoforma=-1;
                                System.out.println(idGeoforma);
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

    private void consultarVegetaciones() {
        /*final ProgressDialog pDialog;
        pDialog=new ProgressDialog(getContext());
        pDialog.setMessage("Cargando...");
        pDialog.show();*/

        final String ip=getString(R.string.ip_url);

        String url=ip+"/web_services/vegetaciones.php";

        System.out.println(url);

        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //pDialog.hide();

                Vegetaciones vegetaciones;

                JSONArray json=response.optJSONArray("vegetaciones");
                JSONObject jsonObject;
                List<String> listTipos=new ArrayList<String>();
                listTipos.add("Seleccione...");
                try {
                    for (int i=0;i<json.length();i++) {
                        jsonObject = json.getJSONObject(i);


                        vegetaciones=new Vegetaciones(jsonObject.getInt("id"), jsonObject.getString("nombre"));

                        System.out.println(jsonObject.getString("nombre")+ "  "+ i+json.length());
                        listaVegetaciones.add(vegetaciones);
                        listTipos.add(jsonObject.getString("nombre"));

                    }
                    ArrayAdapter<CharSequence> spinnerArrayAdapter= new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, listTipos);
                    spinnerCrearUmtpVegetacion.setAdapter(spinnerArrayAdapter);

                    spinnerCrearUmtpVegetacion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            if (position!=0) {
                                System.out.println(listaVegetaciones.get(position - 1).getId());
                                idVegetacion=listaVegetaciones.get(position - 1).getId();
                            }else
                            {
                                idVegetacion=-1;
                                System.out.println(idVegetacion);
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

    private void consultarDedicaciones() {
        /*final ProgressDialog pDialog;
        pDialog=new ProgressDialog(getContext());
        pDialog.setMessage("Cargando...");
        pDialog.show();*/

        final String ip=getString(R.string.ip_url);

        String url=ip+"/web_services/dedicaciones.php";

        System.out.println(url);

        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //pDialog.hide();

                DedicacionesEntornos dedicaciones;

                JSONArray json=response.optJSONArray("dedicaciones_entornos");
                JSONObject jsonObject;
                List<String> listTipos=new ArrayList<String>();
                listTipos.add("Seleccione...");
                try {
                    for (int i=0;i<json.length();i++) {
                        jsonObject = json.getJSONObject(i);


                        dedicaciones=new DedicacionesEntornos(jsonObject.getInt("id"), jsonObject.getString("nombre"));

                        System.out.println(jsonObject.getString("nombre")+ "  "+ i+json.length());
                        listaDedicacion.add(dedicaciones);
                        listTipos.add(jsonObject.getString("nombre"));

                    }
                    ArrayAdapter<CharSequence> spinnerArrayAdapter= new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, listTipos);
                    spinnerCrearUmtpDedicacionEntorno.setAdapter(spinnerArrayAdapter);

                    spinnerCrearUmtpDedicacionEntorno.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            if (position!=0) {
                                System.out.println(listaDedicacion.get(position - 1).getId());
                                idDedicacion=listaDedicacion.get(position - 1).getId();
                            }else
                            {
                                idDedicacion=-1;
                                System.out.println(idDedicacion);
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

    private void consultarTiposRelieves() {
        /*final ProgressDialog pDialog;
        pDialog=new ProgressDialog(getContext());
        pDialog.setMessage("Cargando...");
        pDialog.show();*/

        final String ip=getString(R.string.ip_url);

        String url=ip+"/web_services/tipos_relieves.php";

        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //pDialog.hide();

                TiposRelieves relieves;

                JSONArray json=response.optJSONArray("tipos_relieves");
                JSONObject jsonObject;
                List<String> listTipos=new ArrayList<String>();
                listTipos.add("Seleccione...");
                try {
                    for (int i=0;i<json.length();i++) {
                        jsonObject = json.getJSONObject(i);


                        relieves=new TiposRelieves(jsonObject.getInt("id"), jsonObject.getString("nombre"));

                        System.out.println(jsonObject.getString("nombre")+ "  "+ i+json.length());
                        listaTiposRelieves.add(relieves);
                        listTipos.add(jsonObject.getString("nombre"));

                    }
                    ArrayAdapter<CharSequence> spinnerArrayAdapter= new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, listTipos);
                    spinnerCrearUmtpTipoRelieve.setAdapter(spinnerArrayAdapter);

                    spinnerCrearUmtpTipoRelieve.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            if (position!=0) {
                                System.out.println("Tipo Relieve: " +listaTiposRelieves.get(position - 1).getId());
                                idTipoRelieve=listaTiposRelieves.get(position - 1).getId();
                            }else
                            {
                                idTipoRelieve=-1;
                                System.out.println( idTipoRelieve);
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


    private void webServiceInsertar() {
        /*final ProgressDialog pDialog=new ProgressDialog(getContext());
        pDialog.setMessage("Cargando...");
        pDialog.show();*/

        String ip=getString(R.string.ip_url);

        String url=ip+"/web_services/insertar_umtp.php?";

        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //pDialog.hide();

                if (response.trim().equalsIgnoreCase("inserto")){
                    // etiNombre.setText("");
                    //  txtDocumento.setText("");
                    //   etiProfesion.setText("");
                    Toast.makeText(getContext(),"Se ha insertado con exito",Toast.LENGTH_SHORT).show();
                    VerProyectoFragment proyecto= new VerProyectoFragment();
                    FragmentManager manager= getFragmentManager();
                    argumentosEnviados= new Bundle();
                    argumentosEnviados.putSerializable("usuario",usuarioLogueado);
                    argumentosEnviados.putSerializable("proyecto",proyectoSeleccionado);
                    proyecto.setArguments(argumentosEnviados);
                    manager.beginTransaction().replace(R.id.contenidos, proyecto).commit();


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
                parametros.put("largo",largo );
                parametros.put("ancho",ancho);
                parametros.put("area",area);
                parametros.put("altura",altura);
                parametros.put("utmx",utmx);
                parametros.put("utmy",utmy);
                parametros.put("latitud",latitud);
                parametros.put("longitud",longitud);
                parametros.put("municipio",municipio);
                parametros.put("departamento",departamento);
                parametros.put("vereda",vereda);
                parametros.put("sector",sector);
                parametros.put("accesos",accesos);
                parametros.put("tipoRelieve_id",Integer.toString(idTipoRelieve));
                parametros.put("zonasIncluyente",zonasIncluyentes);
                parametros.put("geoforma",Integer.toString(idGeoforma));
                parametros.put("vegetaciones",Integer.toString(idVegetacion));
                parametros.put("dedicacion",Integer.toString(idDedicacion));
                parametros.put("proyecto_id",Integer.toString(idProyecto));
                parametros.put("usuario_id",Integer.toString( idUsuario));
                //parametros.put("imagen",imagen);

                System.out.println("ID USUARIO" + idUsuario+" ID PROYECTO: "+ idProyecto);



                return parametros;
            }
        };
        //request.add(stringRequest);
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(stringRequest);
    }


    @Override
    public void doBack() {
        argumentosEnviados= new Bundle();
        argumentosEnviados.putSerializable("proyecto",proyectoSeleccionado);
        argumentosEnviados.putSerializable("usuario",usuarioLogueado);
        VerProyectoFragment verProyecto= new VerProyectoFragment();
        verProyecto.setArguments(argumentosEnviados);
        FragmentManager manager=getFragmentManager();
        manager.beginTransaction().replace(R.id.contenidos, verProyecto).commit();

    }
}
