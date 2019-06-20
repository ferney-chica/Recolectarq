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
import com.recolectarq.campo.recolectarq.Fragmentos.Intervenciones.PerfilesExpuestos.EstratosPerfilesExpuestos.EstratosPerfilesExpuestosFragment;
import com.recolectarq.campo.recolectarq.Fragmentos.Intervenciones.PerfilesExpuestos.VerPerfilExpuestoFragment;
import com.recolectarq.campo.recolectarq.Fragmentos.Intervenciones.PozosSondeo.EstratosPozoSondeo.EstratosPozosSondeoFragment;
import com.recolectarq.campo.recolectarq.Fragmentos.Intervenciones.PozosSondeo.VerPozoSondeoFragment;
import com.recolectarq.campo.recolectarq.Modelo.EstructurasEstratos;
import com.recolectarq.campo.recolectarq.Modelo.PerfilesExpuestos;
import com.recolectarq.campo.recolectarq.Modelo.PozosSondeo;
import com.recolectarq.campo.recolectarq.Modelo.Proyectos;
import com.recolectarq.campo.recolectarq.Modelo.TexturasEstratos;
import com.recolectarq.campo.recolectarq.Modelo.TiposEstratos;
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

public class CrearEstratoFragment extends Fragment  implements InicioUsuarioActivity.OnBackPressedListener{
    private TextView textViewCrearEstratoTitulo;
    private EditText editTextCrearEstratoColor;
    private EditText editTextCrearEstratoProfundidad;
    private Spinner spinnerCrearEstratoTextura;
    private Spinner spinnerCrearEstratoEstructura;
    private Spinner spinnerCrearEstratoTipoEstrato;
    private EditText editTextCrearEstratoObservacion;
    private Button buttonCrearEstrato;

    private TextView textViewCrearEstratoCodigoRotulo;
    private EditText editTextCrearEstratoCodigoRotulo;


    private String descripcionPozo;
    private String observacionEstratoPozoPerfil;
    private String origen;
    private int idUmtp;
    private int usuarioCreadorPozo;

    private TextView usuarioExistente;

    private RequestQueue request;
    private JsonRequest jrq;
    private ArrayList<TexturasEstratos> listaTexturasEstratos;
    private int idTexturaEstrato;
    private ArrayList<EstructurasEstratos> listaEstructurasEstratos;
    private int idEstructuraEstrato;
    private ArrayList<TiposEstratos> listaTiposEstratos;
    private int idTipoEstrato;

    private JsonObjectRequest jsonObjectRequest;


    private Usuarios usuarioLogueado;
    private Proyectos proyectoSeleccionado;
    private Umtp umtpSeleccionado;
    private PozosSondeo pozoSeleccionado;
    private PerfilesExpuestos perfilSeleccionado;
    private Bundle argumentosEnviados;
    private TexturasEstratos texturaEstrato;
    private EstructurasEstratos estructuraEstrato;
    private TiposEstratos tipoEstrato;


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

            switch (origen)
            {
                case "pozo":
                    System.out.println("Vengo desde pozo sondeo");
                    pozoSeleccionado=(PozosSondeo) getArguments().getSerializable("pozo");
                    break;

                case "perfil":
                    System.out.println("Vengo desde EstratoPerfil");
                    perfilSeleccionado=(PerfilesExpuestos) getArguments().getSerializable("perfil");
                    break;
            }

            Carpetas carpeta=new Carpetas();
            System.out.println(umtpSeleccionado.getUmtp_id()+ "DESDE CREARESTRATO --- " );
            System.out.println( carpeta.crearCarpeta("/Recolectarq/Proyectos/"+proyectoSeleccionado.getCodigo_identificacion()));
            carpeta.crearCarpeta("/Recolectarq/Proyectos/"+proyectoSeleccionado.getCodigo_identificacion()+"/UMTP");
            carpeta.crearCarpeta("/Recolectarq/Proyectos/"+proyectoSeleccionado.getCodigo_identificacion()+"/MemoriasTecnicas");
            usuarioCreadorPozo = Integer.parseInt(usuarioLogueado.getUsuario_id());
            idUmtp=umtpSeleccionado.getUmtp_id();

            System.out.println("El usuario CREADOR del POZO: " + usuarioLogueado.getNombre() + "con cedula "+ usuarioCreadorPozo+ " LA UMTP ES: "+ idUmtp);
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
        ((InicioUsuarioActivity) getActivity()).setOnBackPressedListener(this);
        final View v= inflater.inflate(R.layout.fragment_crear_estrato,container, false);
        textViewCrearEstratoTitulo=v.findViewById(R.id.textViewCrearEstratoTitulo);
        editTextCrearEstratoColor=v.findViewById(R.id.editTextCrearEstratoColor);
        editTextCrearEstratoProfundidad=v.findViewById(R.id.editTextCrearEstratoProfundidad);
        spinnerCrearEstratoTextura=v.findViewById(R.id.spinnerCrearEstratoTextura);
        spinnerCrearEstratoEstructura=v.findViewById(R.id.spinnerCrearEstratoEstructura);
        spinnerCrearEstratoTipoEstrato=v.findViewById(R.id.spinnerCrearEstratoTipoEstrato);
        editTextCrearEstratoObservacion=v.findViewById(R.id.editTextCrearEstratoObservacion);
        buttonCrearEstrato=v.findViewById(R.id.buttonCrearEstrato);

        editTextCrearEstratoCodigoRotulo=v.findViewById(R.id.editTextCrearEstratoCodigoRotulo);
        textViewCrearEstratoCodigoRotulo=v.findViewById(R.id.textViewCrearEstratoCodigoRotulo);

        listaTexturasEstratos=new ArrayList<>();
        listaEstructurasEstratos=new ArrayList<>();
        listaTiposEstratos=new ArrayList<>();
        consultarTexturas();
        consultarEstructuras();
        consultarTiposEstratos();
        //textViewMensajeNiveles.setText(" Se creará el Nivel # "+ numeroNiveles+" con una profundidad de: " +numeroNiveles*5+" cm");
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

        buttonCrearEstrato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               camposLLenos();


            }
        });


        switch (origen)
        {
            case "pozo":

                textViewCrearEstratoCodigoRotulo.setVisibility(View.INVISIBLE);
                editTextCrearEstratoCodigoRotulo.setVisibility(View.INVISIBLE);
                buttonCrearEstrato.setText("Crear Estrato Pozo");
                break;

            case "perfil":
                buttonCrearEstrato.setText("Crear Estrato Perfil");
                break;
        }


        return v;
    }


    private void camposLLenos()
    {
        if (editTextCrearEstratoObservacion.getText().toString().trim().isEmpty()){
            observacionEstratoPozoPerfil="---";
        }else
        {
            observacionEstratoPozoPerfil=editTextCrearEstratoObservacion.getText().toString().trim();
        }

        if(editTextCrearEstratoCodigoRotulo.getText().toString().trim().isEmpty())
        {
            editTextCrearEstratoCodigoRotulo.setText("---");
        }

        if (editTextCrearEstratoColor.getText().toString().trim().isEmpty()|| editTextCrearEstratoProfundidad.getText().toString().trim().isEmpty() ||
                idTipoEstrato==-1 || idEstructuraEstrato==-1 ||idTexturaEstrato==-1){
            Toast.makeText(getContext(),"Falta informacion del estrato del pozo por diligenciar ", Toast.LENGTH_LONG).show();
        }else{
            webServiceInsertarEstrato();
        }

    }



    private void consultarTexturas() {
        /*final ProgressDialog pDialog;
        pDialog=new ProgressDialog(getContext());
        pDialog.setMessage("Cargando...");
        pDialog.show();*/

        final String ip=getString(R.string.ip_url);

        String url=ip+"/web_services/texturas_estratos.php";

        System.out.println(url);

        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //pDialog.hide();

                JSONArray json=response.optJSONArray("texturas_estratos");
                JSONObject jsonObject;
                List<String> listTipos=new ArrayList<String>();
                listTipos.add("Seleccione...");
                try {
                    for (int i=0;i<json.length();i++) {
                        jsonObject = json.getJSONObject(i);


                        texturaEstrato =new TexturasEstratos(jsonObject.getInt("textura_estrato_id"), jsonObject.getString("nombre"));

                        System.out.println(jsonObject.getString("nombre")+ "  "+ i+json.length());
                        listaTexturasEstratos.add(texturaEstrato);
                        listTipos.add(jsonObject.getString("nombre"));

                    }
                    ArrayAdapter<CharSequence> spinnerArrayAdapter= new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, listTipos);
                    spinnerCrearEstratoTextura.setAdapter(spinnerArrayAdapter);

                    spinnerCrearEstratoTextura.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            if (position!=0) {
                                System.out.println(listaTexturasEstratos.get(position - 1).getTextura_estrato_id());
                                idTexturaEstrato=listaTexturasEstratos.get(position - 1).getTextura_estrato_id();
                            }else
                            {
                                idTexturaEstrato=-1;
                                System.out.println(idTexturaEstrato);
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

    private void consultarEstructuras() {
        /*final ProgressDialog pDialog;
        pDialog=new ProgressDialog(getContext());
        pDialog.setMessage("Cargando...");
        pDialog.show();*/

        final String ip=getString(R.string.ip_url);

        String url=ip+"/web_services/estructuras_estratos.php";

        System.out.println(url);

        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //pDialog.hide();

                JSONArray json=response.optJSONArray("estructuras_estratos");
                JSONObject jsonObject;
                List<String> listTipos=new ArrayList<String>();
                listTipos.add("Seleccione...");
                try {
                    for (int i=0;i<json.length();i++) {
                        jsonObject = json.getJSONObject(i);


                        estructuraEstrato =new EstructurasEstratos(jsonObject.getInt("estructura_estrato_id"), jsonObject.getString("nombre"));

                        System.out.println(jsonObject.getString("nombre")+ "  "+ i+json.length());
                        listaEstructurasEstratos.add(estructuraEstrato);
                        listTipos.add(jsonObject.getString("nombre"));

                    }
                    ArrayAdapter<CharSequence> spinnerArrayAdapter= new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, listTipos);
                    spinnerCrearEstratoEstructura.setAdapter(spinnerArrayAdapter);

                    spinnerCrearEstratoEstructura.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            if (position!=0) {
                                System.out.println(listaEstructurasEstratos.get(position - 1).getEstructura_estrato_id());
                                idEstructuraEstrato=listaEstructurasEstratos.get(position - 1).getEstructura_estrato_id();
                            }else
                            {
                                idEstructuraEstrato=-1;
                                System.out.println(idEstructuraEstrato);
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

    private void consultarTiposEstratos() {
        /*final ProgressDialog pDialog;
        pDialog=new ProgressDialog(getContext());
        pDialog.setMessage("Cargando...");
        pDialog.show();*/

        final String ip=getString(R.string.ip_url);

        String url=ip+"/web_services/tipos_estratos.php";

        System.out.println(url);

        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //pDialog.hide();

                JSONArray json=response.optJSONArray("tipos_estratos");
                JSONObject jsonObject;
                List<String> listTipos=new ArrayList<String>();
                listTipos.add("Seleccione...");
                try {
                    for (int i=0;i<json.length();i++) {
                        jsonObject = json.getJSONObject(i);


                        tipoEstrato =new TiposEstratos(jsonObject.getInt("tipo_estrato_id"), jsonObject.getString("nombre"));

                        System.out.println(jsonObject.getString("nombre")+ "  "+ i+json.length());
                        listaTiposEstratos.add(tipoEstrato);
                        listTipos.add(jsonObject.getString("nombre"));

                    }
                    ArrayAdapter<CharSequence> spinnerArrayAdapter= new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, listTipos);
                    spinnerCrearEstratoTipoEstrato.setAdapter(spinnerArrayAdapter);

                    spinnerCrearEstratoTipoEstrato.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            if (position!=0) {
                                System.out.println(listaTiposEstratos.get(position - 1).getTipo_estrato_id());
                                idTipoEstrato=listaTiposEstratos.get(position - 1).getTipo_estrato_id();
                            }else
                            {
                                idTipoEstrato=-1;
                                System.out.println(idTipoEstrato);
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

   private void webServiceInsertarEstrato() {
        /*final ProgressDialog pDialog=new ProgressDialog(getContext());
        pDialog.setMessage("Cargando...");
        pDialog.show();*/

        String ip=getString(R.string.ip_url);
       String url="";

        switch(origen)
        {
            case "pozo":
                url=ip+"/web_services/insertar_estrato_pozo.php?";
                break;
            case "perfil":
                url=ip+"/web_services/insertar_estrato_perfil.php?";
                break;
        }


        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //pDialog.hide();

                if (response.trim().equalsIgnoreCase("inserto")){
                    // etiNombre.setText("");
                    //  txtDocumento.setText("");
                    //   etiProfesion.setText("");
                    Toast.makeText(getContext(),"Se ha insertado con exito",Toast.LENGTH_SHORT).show();
                    FragmentManager manager= getFragmentManager();
                    switch(origen)
                    {

                        case "pozo":
                            VerPozoSondeoFragment estratoPozo= new VerPozoSondeoFragment();

                            argumentosEnviados= new Bundle();
                            argumentosEnviados.putSerializable("usuario",usuarioLogueado);
                            argumentosEnviados.putSerializable("proyecto",proyectoSeleccionado);
                            argumentosEnviados.putSerializable("umtp",umtpSeleccionado);
                            argumentosEnviados.putSerializable("pozo",pozoSeleccionado);
                            estratoPozo.setArguments(argumentosEnviados);
                            manager.beginTransaction().replace(R.id.contenidos, estratoPozo).commit();
                            break;
                        case "perfil":
                            VerPerfilExpuestoFragment estratoPerfil= new VerPerfilExpuestoFragment();

                            argumentosEnviados= new Bundle();
                            argumentosEnviados.putSerializable("usuario",usuarioLogueado);
                            argumentosEnviados.putSerializable("proyecto",proyectoSeleccionado);
                            argumentosEnviados.putSerializable("umtp",umtpSeleccionado);
                            argumentosEnviados.putSerializable("perfil",perfilSeleccionado);
                            estratoPerfil.setArguments(argumentosEnviados);
                            manager.beginTransaction().replace(R.id.contenidos, estratoPerfil).commit();
                            break;
                    }


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
                switch(origen)
                {
                    case "pozo":

                        parametros.put("texturas_estratos_textura_estrato_id",idTexturaEstrato+"");
                        parametros.put("tipos_estratos_tipo_estrato_id",idTipoEstrato+"");
                        parametros.put("estructuras_estratos_estructura_estrato_id",idEstructuraEstrato+"");
                        parametros.put("pozos_pozo_id",pozoSeleccionado.getPozo_id()+"");
                        parametros.put("profundidad",editTextCrearEstratoProfundidad.getText().toString());
                        parametros.put("color",editTextCrearEstratoColor.getText().toString());
                        parametros.put("observacion",observacionEstratoPozoPerfil);
                        //parametros.put("imagen",imagen);
                        break;
                    case "perfil":
                        parametros.put("texturas_estratos_textura_estrato_id",idTexturaEstrato+"");
                        parametros.put("tipos_estratos_tipo_estrato_id",idTipoEstrato+"");
                        parametros.put("estructuras_estratos_estructura_estrato_id",idEstructuraEstrato+"");
                        parametros.put("perfiles_expuestos_perfil_expuesto_id",perfilSeleccionado.getPerfil_expuesto_id()+"");
                        parametros.put("profundidad",editTextCrearEstratoProfundidad.getText().toString());
                        parametros.put("color",editTextCrearEstratoColor.getText().toString());
                        parametros.put("observacion",observacionEstratoPozoPerfil);
                        parametros.put("codigo_rotulo",editTextCrearEstratoCodigoRotulo.getText().toString());
                        //parametros.put("imagen",imagen);
                        break;
                }



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
        argumentosEnviados.putSerializable("pozo",pozoSeleccionado);

        VerPozoSondeoFragment verPozo= new VerPozoSondeoFragment();
        verPozo.setArguments(argumentosEnviados);
        FragmentManager manager= getFragmentManager();
        manager.beginTransaction().replace(R.id.contenidos, verPozo).commit();
    }
}
