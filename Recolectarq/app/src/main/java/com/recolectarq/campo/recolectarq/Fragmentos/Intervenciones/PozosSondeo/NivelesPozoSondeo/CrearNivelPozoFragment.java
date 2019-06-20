package com.recolectarq.campo.recolectarq.Fragmentos.Intervenciones.PozosSondeo.NivelesPozoSondeo;

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
import com.recolectarq.campo.recolectarq.Fragmentos.Intervenciones.PozosSondeo.VerPozoSondeoFragment;
import com.recolectarq.campo.recolectarq.Modelo.PozosSondeo;
import com.recolectarq.campo.recolectarq.Modelo.Proyectos;
import com.recolectarq.campo.recolectarq.Modelo.Umtp;
import com.recolectarq.campo.recolectarq.Modelo.Usuarios;
import com.recolectarq.campo.recolectarq.R;
import com.recolectarq.campo.recolectarq.VolleySingleton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CrearNivelPozoFragment extends Fragment implements InicioUsuarioActivity.OnBackPressedListener{
    private EditText editTextCrearNivelNumero;
    private EditText editTextCrearNivelProfundidad;
    private EditText editTextCrearNivelCodigoRotulo;
    private TextView textViewMensajeNiveles;
    private String descripcionPozo;
    private String codigoRotuloPozo;
    private int idUmtp;
    private int usuarioCreadorPozo;
    private int numeroNiveles;
    private TextView usuarioExistente;
    private Button buttonCrearNivelPozo;
    private RequestQueue request;
    private JsonRequest jrq;
    private ArrayList<PozosSondeo> listaPozos;
    private JsonObjectRequest jsonObjectRequest;


    private Usuarios usuarioLogueado;
    private Proyectos proyectoSeleccionado;
    private Umtp umtpSeleccionado;
    private PozosSondeo pozoSeleccionado;
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
            pozoSeleccionado=(PozosSondeo) getArguments().getSerializable("pozo");
            numeroNiveles=(int)getArguments().getInt("numeroNiveles");
            Carpetas carpeta=new Carpetas();
            System.out.println(umtpSeleccionado.getUmtp_id()+ "DESDE CREARNIVEL --- Numero de niveles creados:"+ numeroNiveles );
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
        final View v= inflater.inflate(R.layout.fragment_crear_nivel_pozo,container, false);
        editTextCrearNivelNumero=v.findViewById(R.id.editTextCrearNivelNumero);
        editTextCrearNivelProfundidad=v.findViewById(R.id.editTextCrearNivelProfundidad);
        editTextCrearNivelCodigoRotulo=v.findViewById(R.id.editTextCrearNivelCodigoRotulo);
        textViewMensajeNiveles=v.findViewById(R.id.textViewMensajeNiveles);
        numeroNiveles+=1;
        editTextCrearNivelNumero.setText(numeroNiveles+"");
        editTextCrearNivelProfundidad.setText(numeroNiveles*5+"");


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
        buttonCrearNivelPozo=v.findViewById(R.id.buttonCrearNivelPozo);
        buttonCrearNivelPozo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (editTextCrearNivelCodigoRotulo.getText().toString().trim().isEmpty()){
                    codigoRotuloPozo="---";
                }else
                {
                    codigoRotuloPozo=editTextCrearNivelCodigoRotulo.getText().toString().trim();
                }

                if (editTextCrearNivelNumero.getText().toString().trim().isEmpty()|| editTextCrearNivelProfundidad.getText().toString().trim().isEmpty()){
                    Toast.makeText(getContext(),"Falta informacion del nivel del pozo por diligenciar ", Toast.LENGTH_LONG).show();
                }else{
                    webServiceInsertarNivelPozo();
                }

            }
        });

        return v;
    }




   private void webServiceInsertarNivelPozo() {
        /*final ProgressDialog pDialog=new ProgressDialog(getContext());
        pDialog.setMessage("Cargando...");
        pDialog.show();*/

        String ip=getString(R.string.ip_url);

        String url=ip+"/web_services/insertar_nivel_pozo.php?";

        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //pDialog.hide();

                if (response.trim().equalsIgnoreCase("inserto")){
                    // etiNombre.setText("");
                    //  txtDocumento.setText("");
                    //   etiProfesion.setText("");
                    Toast.makeText(getContext(),"Se ha insertado con exito",Toast.LENGTH_SHORT).show();
                    NivelesPozosSondeoFragment nivelPozo= new NivelesPozosSondeoFragment();
                    FragmentManager manager= getFragmentManager();
                    argumentosEnviados= new Bundle();
                    argumentosEnviados.putSerializable("usuario",usuarioLogueado);
                    argumentosEnviados.putSerializable("proyecto",proyectoSeleccionado);
                    argumentosEnviados.putSerializable("umtp",umtpSeleccionado);
                    argumentosEnviados.putSerializable("pozo",pozoSeleccionado);
                    nivelPozo.setArguments(argumentosEnviados);
                    manager.beginTransaction().replace(R.id.contenidos, nivelPozo).commit();
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
                parametros.put("pozos_pozo_id",pozoSeleccionado.getPozo_id()+"");
                parametros.put("numero",editTextCrearNivelNumero.getText().toString().trim());
                parametros.put("profundidad",editTextCrearNivelProfundidad.getText().toString().trim());
                parametros.put("codigo_rotulo",codigoRotuloPozo);
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
        argumentosEnviados.putSerializable("pozo",pozoSeleccionado);

        VerPozoSondeoFragment verPozo= new VerPozoSondeoFragment();
        verPozo.setArguments(argumentosEnviados);
        FragmentManager manager= getFragmentManager();
        manager.beginTransaction().replace(R.id.contenidos, verPozo).commit();
    }
}
