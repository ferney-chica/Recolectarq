package com.recolectarq.campo.recolectarq.Fragmentos.Intervenciones.RecoleccionesSuperficiales;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.recolectarq.campo.recolectarq.Carpetas;
import com.recolectarq.campo.recolectarq.CuadrosDialogos.CuadroDialogoActualizarPozo;
import com.recolectarq.campo.recolectarq.CuadrosDialogos.CuadroDialogoActualizarRecoleccion;
import com.recolectarq.campo.recolectarq.Modelo.PozosSondeo;
import com.recolectarq.campo.recolectarq.Modelo.Proyectos;
import com.recolectarq.campo.recolectarq.Modelo.RecoleccionesSuperficiales;
import com.recolectarq.campo.recolectarq.Modelo.Umtp;
import com.recolectarq.campo.recolectarq.Modelo.Usuarios;
import com.recolectarq.campo.recolectarq.R;

import java.util.ArrayList;

public class EditarRecoleccionFragment extends Fragment implements CuadroDialogoActualizarRecoleccion.FinalizarCuadroDialogo {
    private TextView textViewEditarRecoleccionId;
    private TextView textViewEditarRecoleccionUmtpId;
    private TextView textViewEditarRecoleccionDescripcion;
    private TextView textViewEditarRecoleccionIdUsuarioCreador;
    private TextView textViewEditarRecoleccionCodigoRotulo;
    private String descripcionRecoleccion;
    private String codigoRotuloRecoleccion;
    private int idUmtp;
    private int usuarioCreadorPozo;
    private TextView usuarioExistente;
    private FloatingActionButton floatingActionButtonEditarRecoleccion;
    private RequestQueue request;
    private JsonRequest jrq;
    private ArrayList<PozosSondeo> listarecolecciones;
    private JsonObjectRequest jsonObjectRequest;


    private Usuarios usuarioLogueado;
    private Proyectos proyectoSeleccionado;
    private Umtp umtpSeleccionado;
    private RecoleccionesSuperficiales recoleccionSeleccionado;
    private Bundle argumentosEnviados;
    private Context contexto;

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
            recoleccionSeleccionado=(RecoleccionesSuperficiales) getArguments().getSerializable("recoleccion");

            Carpetas carpeta=new Carpetas();
            System.out.println(recoleccionSeleccionado.getrecoleccion_superficial_id()+ "*************DESDE EDITAR RECOLECCION SONDEO*******************");
            System.out.println(umtpSeleccionado.getUmtp_id()+ "DESDE EDITAR POZO SONDEO");
            System.out.println( carpeta.crearCarpeta("/Recolectarq/Proyectos/"+proyectoSeleccionado.getCodigo_identificacion()));
            carpeta.crearCarpeta("/Recolectarq/Proyectos/"+proyectoSeleccionado.getCodigo_identificacion()+"/UMTP");
            carpeta.crearCarpeta("/Recolectarq/Proyectos/"+proyectoSeleccionado.getCodigo_identificacion()+"/MemoriasTecnicas");
            usuarioCreadorPozo = Integer.parseInt(usuarioLogueado.getUsuario_id());
            idUmtp=umtpSeleccionado.getUmtp_id();

            System.out.println("El usuario CREADOR de la RECOLECCION: " + usuarioLogueado.getNombre() + "con cedula "+ usuarioCreadorPozo+ " LA UMTP ES: "+ idUmtp);
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
        final View v= inflater.inflate(R.layout.fragment_editar_recoleccion,container, false);
        textViewEditarRecoleccionId=v.findViewById(R.id.textViewEditarRecoleccionId);
        textViewEditarRecoleccionUmtpId=v.findViewById(R.id.textViewEditarRecoleccionUmtpId);
        textViewEditarRecoleccionDescripcion=v.findViewById(R.id.textViewEditarRecoleccionDescripcion);
        textViewEditarRecoleccionIdUsuarioCreador=v.findViewById(R.id.textViewEditarRecoleccionIdUsuarioCreador);
        textViewEditarRecoleccionCodigoRotulo=v.findViewById(R.id.textViewEditarRecoleccionCodigoRotulo);
        floatingActionButtonEditarRecoleccion=v.findViewById(R.id.floatingActionButtonEditarRecoleccion);

        textViewEditarRecoleccionId.setText(textViewEditarRecoleccionId.getText()+" "+recoleccionSeleccionado.getrecoleccion_superficial_id());
        textViewEditarRecoleccionUmtpId.setText(textViewEditarRecoleccionUmtpId.getText()+" "+recoleccionSeleccionado.getUmtp_id());
        textViewEditarRecoleccionDescripcion.setText(textViewEditarRecoleccionDescripcion.getText()+" "+recoleccionSeleccionado.getDescripcion());
        textViewEditarRecoleccionIdUsuarioCreador.setText(textViewEditarRecoleccionIdUsuarioCreador.getText()+" "+recoleccionSeleccionado.getUsuario_creador());
        textViewEditarRecoleccionCodigoRotulo.setText(textViewEditarRecoleccionCodigoRotulo.getText()+" "+recoleccionSeleccionado.getCodigo_rotulo());


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
        floatingActionButtonEditarRecoleccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            System.out.println("CLIC EN EDITAR INFO RECOLECCION "+ recoleccionSeleccionado.getDescripcion());
                FragmentManager manager= getFragmentManager();
                System.out.println("CLIC EN EDITAR PROYECTO");
                contexto=getContext();
                new CuadroDialogoActualizarRecoleccion(contexto, proyectoSeleccionado, recoleccionSeleccionado,EditarRecoleccionFragment.this);

            }
        });

        return v;
    }



    @Override
    public void RecoleccionActualizado(RecoleccionesSuperficiales recoleccion) {
        textViewEditarRecoleccionDescripcion.setText("Descripcion: "+recoleccion.getDescripcion());
        textViewEditarRecoleccionCodigoRotulo.setText("Código Rótulo: "+recoleccion.getCodigo_rotulo());
    }




   /*private void webServiceInsertarPozo() {
        final ProgressDialog pDialog=new ProgressDialog(getContext());
        pDialog.setMessage("Cargando...");
        pDialog.show();

        String ip=getString(R.string.ip_url);

        String url=ip+"/web_services/insertar_pozo.php?";

        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pDialog.hide();

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
                    System.out.println("LA UMTP SELECCIONADA ES: "+umtpSeleccionado.getIntervencion_id());

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
                pDialog.hide();
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
                parametros.put("usuario_creador",Integer.toString(usuarioCreadorPozo));
                parametros.put("usuario_encargado",Integer.toString(usuarioCreadorPozo));
                parametros.put("codigo_rotulo",codigoRotuloPozo);
                //parametros.put("imagen",imagen);

                return parametros;
            }
        };
        //request.add(stringRequest);
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(stringRequest);
    }*/





}
