package com.recolectarq.campo.recolectarq.Fragmentos.Intervenciones.RecoleccionesSuperficiales;

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
import android.widget.Button;
import android.widget.EditText;
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
import com.recolectarq.campo.recolectarq.Fragmentos.Intervenciones.IntervencionesFragment;
import com.recolectarq.campo.recolectarq.Modelo.Proyectos;
import com.recolectarq.campo.recolectarq.Modelo.RecoleccionesSuperficiales;
import com.recolectarq.campo.recolectarq.Modelo.Umtp;
import com.recolectarq.campo.recolectarq.Modelo.Usuarios;
import com.recolectarq.campo.recolectarq.R;
import com.recolectarq.campo.recolectarq.VolleySingleton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CrearRecoleccionSuperficialFragment extends Fragment implements InicioUsuarioActivity.OnBackPressedListener{
    private EditText editTextDescripcion;
    private EditText editTextCodigoRotulo;
    private String descripcionRecoleccion;
    private String codigoRotuloRecoleccion;
    private int idUmtp;
    private int usuarioCreadorRecoleccion;
    private TextView usuarioExistente;
    private Button btnCrearRecoleccion;
    private RequestQueue request;
    private JsonRequest jrq;
    private ArrayList<RecoleccionesSuperficiales> listaRecolecciones;
    private JsonObjectRequest jsonObjectRequest;


    private Usuarios usuarioLogueado;
    private Proyectos proyectoSeleccionado;
    private Umtp umtpSeleccionado;
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
            Carpetas carpeta=new Carpetas();
            System.out.println(umtpSeleccionado.getUmtp_id()+ "DESDE POZO SONDEO");
            System.out.println( carpeta.crearCarpeta("/Recolectarq/Proyectos/"+proyectoSeleccionado.getCodigo_identificacion()));
            carpeta.crearCarpeta("/Recolectarq/Proyectos/"+proyectoSeleccionado.getCodigo_identificacion()+"/UMTP");
            carpeta.crearCarpeta("/Recolectarq/Proyectos/"+proyectoSeleccionado.getCodigo_identificacion()+"/MemoriasTecnicas");
            usuarioCreadorRecoleccion = Integer.parseInt(usuarioLogueado.getUsuario_id());
            idUmtp=umtpSeleccionado.getUmtp_id();

            System.out.println("El usuario CREADOR del POZO: " + usuarioLogueado.getNombre() + "con cedula "+ usuarioCreadorRecoleccion+ " LA UMTP ES: "+ idUmtp);
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
        final View v= inflater.inflate(R.layout.fragment_crear_recoleccion_superficial,container, false);
        editTextDescripcion=v.findViewById(R.id.editTextEditarUmtpLargo);
        editTextCodigoRotulo=v.findViewById(R.id.editTextEditarUmtpCodigoRotulo);

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
        btnCrearRecoleccion=v.findViewById(R.id.buttonCrearRecoleccion);
        btnCrearRecoleccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (editTextCodigoRotulo.getText().toString().trim().isEmpty()){
                    codigoRotuloRecoleccion="---";
                }else
                {
                    codigoRotuloRecoleccion=editTextCodigoRotulo.getText().toString().trim();
                }

                if (editTextDescripcion.getText().toString().trim().isEmpty()){
                    Toast.makeText(getContext(),"Diligencie la descripción del pozo", Toast.LENGTH_LONG).show();
                }else{
                    webServiceInsertarRecoleccion();
                }

            }
        });

        return v;
    }




   private void webServiceInsertarRecoleccion() {
        /*final ProgressDialog pDialog=new ProgressDialog(getContext());
        pDialog.setMessage("Cargando...");
        pDialog.show();*/

        String ip=getString(R.string.ip_url);

        String url=ip+"/web_services/insertar_recoleccion.php?";

        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //pDialog.hide();

                if (response.trim().equalsIgnoreCase("inserto")){
                    // etiNombre.setText("");
                    //  txtDocumento.setText("");
                    //   etiProfesion.setText("");
                    Toast.makeText(getContext(),"Se ha insertado con exito",Toast.LENGTH_SHORT).show();
                    IntervencionesFragment intervencion= new IntervencionesFragment();
                    FragmentManager manager= getFragmentManager();
                    argumentosEnviados= new Bundle();
                    argumentosEnviados.putSerializable("usuario",usuarioLogueado);
                    argumentosEnviados.putSerializable("proyecto",proyectoSeleccionado);
                    argumentosEnviados.putSerializable("umtp",umtpSeleccionado);
                    intervencion.setArguments(argumentosEnviados);
                    manager.beginTransaction().replace(R.id.contenidos, intervencion).commit();
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
                parametros.put("umtp_id",Integer.toString(idUmtp) );
                parametros.put("descripcion",editTextDescripcion.getText().toString().trim());
                parametros.put("usuario_creador",Integer.toString(usuarioCreadorRecoleccion));
                parametros.put("usuario_encargado",Integer.toString(usuarioCreadorRecoleccion));
                parametros.put("codigo_rotulo",codigoRotuloRecoleccion);
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
        IntervencionesFragment intervenciones= new IntervencionesFragment();
        intervenciones.setArguments(argumentosEnviados);
        FragmentManager manager= getFragmentManager();
        manager.beginTransaction().replace(R.id.contenidos, intervenciones).commit();
    }
}
