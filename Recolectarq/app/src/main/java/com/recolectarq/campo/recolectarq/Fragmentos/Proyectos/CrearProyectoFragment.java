package com.recolectarq.campo.recolectarq.Fragmentos.Proyectos;

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
import com.recolectarq.campo.recolectarq.Fragmentos.Intervenciones.PozosSondeo.NivelesPozoSondeo.MaterialesNivelesPozoFragment;
import com.recolectarq.campo.recolectarq.Modelo.Tipos_Proyectos;
import com.recolectarq.campo.recolectarq.Modelo.Usuarios;
import com.recolectarq.campo.recolectarq.R;
import com.recolectarq.campo.recolectarq.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CrearProyectoFragment extends Fragment implements InicioUsuarioActivity.OnBackPressedListener {
    private EditText ubicacion;
    private EditText nombre;
    private EditText referencias;
    private EditText aval;
    private TextView mensajeNombre;
    private String nombreProyecto;
    private String ubicacionProyecto;
    private int idUsuario;
    private int idTipo;
    private int idFase;
    private String fechaInicio;
    private String fechaFin;
    private String referenciasProyecto;
    private String avalProyecto;
    private String codigoProyecto;
    private TextView usuarioExistente;
    private Button btnCrearProyecto;
    private RequestQueue request;
    private JsonRequest jrq;
    private ArrayList<Tipos_Proyectos> listaTiposProyectos;
    private Spinner spinnerTipoProyecto;
    private JsonObjectRequest jsonObjectRequest;


    private Usuarios usuarioLogueado;
    private Bundle usuarioEnviado;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments()!=null)
        {
            Toast.makeText(getContext(),"entro",Toast.LENGTH_LONG);
            Toast.makeText(getContext(),"entro",Toast.LENGTH_LONG);
            usuarioLogueado=(Usuarios) getArguments().getSerializable("usuario");

            usuarioEnviado= getArguments();
            idUsuario=Integer.parseInt( usuarioLogueado.getUsuario_id());

            System.out.println("El usuario Logueado es desde crear es proyectos" + usuarioLogueado.getNombre() + "con cedula "+ idUsuario);
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
        final View v= inflater.inflate(R.layout.fragment_crear_proyecto,container, false);
        listaTiposProyectos=new ArrayList<>();
        spinnerTipoProyecto=v.findViewById(R.id.spinnerTipo);
        nombre=v.findViewById(R.id.editTextEditarUmtpLargo);
        referencias=v.findViewById(R.id.editTextEditarUmtpAltura);
        aval=v.findViewById(R.id.editTextAval);
        nombre.requestFocus();
        ubicacion=v.findViewById(R.id.editTextUbicacion);
        mensajeNombre=v.findViewById(R.id.textViewMensajeNombre);

        spinnerTipoProyecto.setFocusable(true);
        spinnerTipoProyecto.setFocusableInTouchMode(true);
        spinnerTipoProyecto.requestFocus();
        consultarTiposProyectos();

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
        btnCrearProyecto=v.findViewById(R.id.buttonCrearProyecto);
        btnCrearProyecto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Calendar.getInstance().getTime();

                nombreProyecto= nombre.getText().toString();
                ubicacionProyecto=ubicacion.getText().toString();
                idFase=1;
                fechaInicio=simpleDateFormat.format(Calendar.getInstance().getTime());
                fechaFin="0000-00-00";
                referenciasProyecto=referencias.getText().toString();
                avalProyecto=aval.getText().toString();
                codigoProyecto=simpleDateFormat.format(Calendar.getInstance().getTime());

                System.out.println("la consulta es de la siguiente forma"+nombreProyecto+" - "+idUsuario+ " - "+idTipo+ " - "+idFase +" - "+idUsuario+" - "+ubicacionProyecto+
                        " - "+referenciasProyecto+" - "+avalProyecto+" - "+codigoProyecto+" - "+fechaInicio+" - "+fechaFin);

                webServiceInsertar();

            }
        });

        return v;
    }


    private void consultarTiposProyectos() {
        /*final ProgressDialog pDialog;
        pDialog=new ProgressDialog(getContext());
        pDialog.setMessage("Cargando...");
        pDialog.show();*/

        final String ip=getString(R.string.ip_url);

        String url=ip+"/web_services/tipos_proyectos.php";

        System.out.println(url);

        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //pDialog.hide();

                Tipos_Proyectos tipoProyecto;

                JSONArray json=response.optJSONArray("tipos_proyectos");
                JSONObject jsonObject;
                List<String> listTipos=new ArrayList<String>();
                listTipos.add("Seleccione...");
                try {
                    for (int i=0;i<json.length();i++) {
                        jsonObject = json.getJSONObject(i);


                        tipoProyecto=new Tipos_Proyectos(jsonObject.getInt("tipo_proyecto_id"), jsonObject.getString("nombre"));

                        System.out.println(jsonObject.getString("nombre")+ "  "+ i+json.length());
                        listaTiposProyectos.add(tipoProyecto);
                        listTipos.add(jsonObject.getString("nombre"));

                    }
                    ArrayAdapter<CharSequence> spinnerArrayAdapter= new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, listTipos);
                    spinnerTipoProyecto.setAdapter(spinnerArrayAdapter);

                    spinnerTipoProyecto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            if (position!=0) {
                                System.out.println(listaTiposProyectos.get(position - 1).getTipo_proyecto_id());
                                idTipo=listaTiposProyectos.get(position - 1).getTipo_proyecto_id();
                            }else
                                {
                                idTipo=-1;
                                    System.out.println(idTipo);
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

        String url=ip+"/web_services/insertar_proyecto.php?";

        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //pDialog.hide();

                if (response.trim().equalsIgnoreCase("inserto")){
                    // etiNombre.setText("");
                    //  txtDocumento.setText("");
                    //   etiProfesion.setText("");
                    Toast.makeText(getContext(),"Se ha insertado con exito",Toast.LENGTH_SHORT).show();
                    ProyectosFragment proyecto= new ProyectosFragment();
                    FragmentManager manager= getFragmentManager();
                    proyecto.setArguments(usuarioEnviado);
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
                parametros.put("usuario",Integer.toString(idUsuario) );
                parametros.put("tipo",Integer.toString(idTipo));
                parametros.put("fase",Integer.toString(idFase));
                parametros.put("nombre",nombreProyecto);
                parametros.put("ubicacion",ubicacionProyecto);
                parametros.put("inicio",fechaInicio);
                parametros.put("final",fechaFin);
                parametros.put("referencias",referenciasProyecto);
                parametros.put("aval",avalProyecto);
                parametros.put("codigo",codigoProyecto);
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
        argumentosEnviados.putSerializable("usuario",usuarioLogueado);
        //System.out.println("Pozo seleccionado desde CrearMaterial  es: "+pozoSeleccionado.getPozo_id());
        ProyectosFragment proyecto= new ProyectosFragment();
        proyecto.setArguments(argumentosEnviados);
        FragmentManager manager=getFragmentManager();
        manager= getFragmentManager();
        manager.beginTransaction().replace(R.id.contenidos, proyecto).commit();
    }
}
