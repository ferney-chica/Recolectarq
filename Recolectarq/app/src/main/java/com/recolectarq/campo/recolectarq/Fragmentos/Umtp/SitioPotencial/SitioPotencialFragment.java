package com.recolectarq.campo.recolectarq.Fragmentos.Umtp.SitioPotencial;

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
import com.recolectarq.campo.recolectarq.Fragmentos.Umtp.Yacimiento.CondicionesEmplazamiento2Fragment;
import com.recolectarq.campo.recolectarq.Modelo.AfloramientosElementos;
import com.recolectarq.campo.recolectarq.Modelo.FisiografiasElementos;
import com.recolectarq.campo.recolectarq.Modelo.Geoforma;
import com.recolectarq.campo.recolectarq.Modelo.HidrografiasElementos;
import com.recolectarq.campo.recolectarq.Modelo.Proyectos;
import com.recolectarq.campo.recolectarq.Modelo.TiposViasNaturales;
import com.recolectarq.campo.recolectarq.Modelo.Tipos_Proyectos;
import com.recolectarq.campo.recolectarq.Modelo.Umtp;
import com.recolectarq.campo.recolectarq.Modelo.UsosViasNaturales;
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

public class SitioPotencialFragment extends Fragment  {

    private int idUsuario;
    private int idAfloramiento;
    private int idHidrografia;
    private int idFisiografia;
    private int idTipoVias;
    private int idUsoVias;
    private int idUmtp;

    private FragmentActivity contexto;

    private RequestQueue request;
    private JsonRequest jrq;
    private ArrayList<Tipos_Proyectos> listaTiposProyectos;

    private JsonObjectRequest jsonObjectRequest;


    private Usuarios usuarioLogueado;
    private Proyectos proyectoSeleccionado;
    private Umtp umtpSeleccionado;
    private Bundle usuarioEnviado;

    private EditText editTextSitioPotencialConjuntoMaterial;
    private EditText editTextSitioPotencialDispercionMaterial;
    private EditText editTextSitioPotencialOtroMaterial;
    private EditText editTextSitioPotencialTipoTrabajo;
    private EditText editTextSitioPotencialCondicionHallazgo;
    private EditText editTextSitioPotencialElementoNoArqueologico;
    private EditText editTextSitioPotencialYacimientosConexos;


    private Button buttonSitioPotencialGuardar;



    private Bundle argumentosEnviados;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments()!=null)
        {
            usuarioLogueado=(Usuarios) getArguments().getSerializable("usuario");
            proyectoSeleccionado=(Proyectos)getArguments().getSerializable("proyecto");
            umtpSeleccionado=(Umtp) getArguments().getSerializable("umtp");
            idUmtp=umtpSeleccionado.getUmtp_id();
            usuarioEnviado= getArguments();
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
        final View v= inflater.inflate(R.layout.fragment_sitio_potencial,container, false);

        editTextSitioPotencialConjuntoMaterial=v.findViewById(R.id.editTextSitioPotencialConjuntoMaterial);
        editTextSitioPotencialDispercionMaterial=v.findViewById(R.id.editTextSitioPotencialDispercionMaterial);
        editTextSitioPotencialOtroMaterial=v.findViewById(R.id.editTextSitioPotencialOtroMaterial);
        editTextSitioPotencialTipoTrabajo=v.findViewById(R.id.editTextSitioPotencialTipoTrabajo);
        editTextSitioPotencialCondicionHallazgo=v.findViewById(R.id.editTextSitioPotencialCondicionHallazgo);
        editTextSitioPotencialElementoNoArqueologico=v.findViewById(R.id.editTextSitioPotencialElementoNoArqueologico);
        editTextSitioPotencialYacimientosConexos=v.findViewById(R.id.editTextSitioPotencialYacimientosConexos);

        buttonSitioPotencialGuardar=v.findViewById(R.id.buttonSitioPotencialGuardar);

        buttonSitioPotencialGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                camposLLenos();
            }
        });



        return v;
    }

    private void camposLLenos() {

        if(editTextSitioPotencialConjuntoMaterial.getText().toString().trim().isEmpty() || editTextSitioPotencialDispercionMaterial.getText().toString().trim().isEmpty()
        || editTextSitioPotencialOtroMaterial.getText().toString().trim().isEmpty() || editTextSitioPotencialTipoTrabajo.getText().toString().trim().isEmpty()
                || editTextSitioPotencialCondicionHallazgo.getText().toString().trim().isEmpty() || editTextSitioPotencialElementoNoArqueologico.getText().toString().trim().isEmpty()
                || editTextSitioPotencialYacimientosConexos.getText().toString().trim().isEmpty())
        {
            Toast.makeText(getContext(),"Hay campos sin diligenciar",Toast.LENGTH_LONG).show();
        }else
        {
            webServiceInsertarSitioPotencial();
        }
    }

    private void webServiceInsertarSitioPotencial()
    {
        /*final ProgressDialog pDialog=new ProgressDialog(getContext());
        pDialog.setMessage("Cargando...");
        pDialog.show();*/

        String ip=getString(R.string.ip_url);

        String url=ip+"/web_services/insertar_sitio_potencial.php?";

        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //pDialog.hide();

                if (response.trim().equalsIgnoreCase("inserto")){
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
                parametros.put("conjunto_material",editTextSitioPotencialConjuntoMaterial.getText().toString().trim());
                parametros.put("dispercion_material",editTextSitioPotencialDispercionMaterial.getText().toString().trim());
                parametros.put("otro_material",editTextSitioPotencialOtroMaterial.getText().toString().trim());
                parametros.put("tipo_trabajo",editTextSitioPotencialTipoTrabajo.getText().toString().trim());
                parametros.put("condicion_hallazgo",editTextSitioPotencialCondicionHallazgo.getText().toString().trim());
                parametros.put("elemento_no_arqueologico",editTextSitioPotencialElementoNoArqueologico.getText().toString().trim());
                parametros.put("yacimientos_conexos",editTextSitioPotencialYacimientosConexos.getText().toString().trim());
                parametros.put("umtp_id",idUmtp+"");
                //parametros.put("imagen",imagen);

                return parametros;
            }
        };
        //request.add(stringRequest);
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(stringRequest);
    }


}
