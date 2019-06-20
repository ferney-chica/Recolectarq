package com.recolectarq.campo.recolectarq.CuadrosDialogos;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.recolectarq.campo.recolectarq.Modelo.Perfiles;
import com.recolectarq.campo.recolectarq.Modelo.Proyectos;
import com.recolectarq.campo.recolectarq.Modelo.Tipos_Proyectos;
import com.recolectarq.campo.recolectarq.Modelo.Usuarios;
import com.recolectarq.campo.recolectarq.Modelo.UsuariosProyectos;
import com.recolectarq.campo.recolectarq.R;
import com.recolectarq.campo.recolectarq.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CuadroDialogoAdicionarUsuarioProyecto {

    private EditText editTextIdUsuario;
    private Spinner  spinnerPerfilUsuario;
    private TextView mensajeNoEncontrado;
    private TextView textViewIdUsuario;
    private TextView textViewNombre;
    private TextView textViewApellidos;
    private TextView textViewCorreo;
    private ConstraintLayout constraintLayoutUsuario;
    private ImageButton imageButtonBuscarUsuario;
    private Button cancelar;
    private Button asignar;
    private Context contexto1;

    private JsonObjectRequest jsonObjectRequest;
    private ArrayList<Tipos_Proyectos> listaTiposProyectos;
    private ArrayList<Usuarios> listaUsuarios;
    private ArrayList<Perfiles> listaPerfiles;
    private ArrayList<UsuariosProyectos> listaUsuariosProyectos2;
    private UsuariosProyectos usuarioProyecto;
    private int idPerfil;
    private String descripcionPerfil;
    private int idTipoSeleccionado;
    private int idUsuario;
    private String nombreUsuario;
    private String apellidoUsuario;
    private  int idProyecto;
    private String nombreProyecto;
    private FragmentManager manager1;
    private Proyectos proyecto=new Proyectos();
    private Usuarios usuario= new Usuarios();
    private boolean cambio=false;
    private  Bundle argumentosEnviados;
    private FinalizarCuadroDialogo interfaz;

    public interface FinalizarCuadroDialogo
    {
        void UsuarioProyectoAsignado(UsuariosProyectos usuarioProyecto);
    }


    public CuadroDialogoAdicionarUsuarioProyecto(final Context contexto, final Proyectos proyectoSeleccionado, final ArrayList<UsuariosProyectos> listUsuariosProyectos, final FinalizarCuadroDialogo actividad) {
     interfaz=actividad;
        final Dialog dialogoUsuarioPryecto= new Dialog(contexto);
         //manager1= manager;
         contexto1=contexto;
         proyecto=proyectoSeleccionado;
         listaUsuariosProyectos2= new ArrayList<>();
         listaUsuariosProyectos2=listUsuariosProyectos;

        System.out.println("Nombre del proyecto desde dialogo UsuariosProyecto AAAAAAA: "+listaUsuariosProyectos2.get(0).getProyecto_nombre());
         //usuario=usuarioLogueado;
        //idTipo=proyectoSeleccionado.getTipo_proyecto_id();
         System.out.println(proyecto+"  FFFFFFF "+usuario);
        dialogoUsuarioPryecto.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogoUsuarioPryecto.setCancelable(false);
        dialogoUsuarioPryecto.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogoUsuarioPryecto.setContentView(R.layout.cuadro_dialogo_asignar_usuario_proyecto);
        idProyecto=proyectoSeleccionado.getProyecto_id();
        nombreProyecto=proyectoSeleccionado.getNombre();
        editTextIdUsuario= dialogoUsuarioPryecto.findViewById(R.id.editTextEditarUmtpLargo);
        spinnerPerfilUsuario=dialogoUsuarioPryecto.findViewById(R.id.spinnerPerfilUsuario);
        mensajeNoEncontrado=dialogoUsuarioPryecto.findViewById(R.id.textViewMensaje);
        textViewIdUsuario= dialogoUsuarioPryecto.findViewById(R.id.textViewIdUsuario);
        textViewNombre=dialogoUsuarioPryecto.findViewById(R.id.textViewNombre);
        textViewApellidos=dialogoUsuarioPryecto.findViewById(R.id.textViewApellidos);
        textViewCorreo=dialogoUsuarioPryecto.findViewById(R.id.textViewCorreo);
        constraintLayoutUsuario=dialogoUsuarioPryecto.findViewById(R.id.constraintLayoutUsuario);
        imageButtonBuscarUsuario=dialogoUsuarioPryecto.findViewById(R.id.imageButtonBuscarUsuario);
        cancelar=dialogoUsuarioPryecto.findViewById(R.id.buttonCancelarPozo);
        asignar=dialogoUsuarioPryecto.findViewById(R.id.buttonAsignarUsuario);
        asignar.setEnabled(false);

        constraintLayoutUsuario.setVisibility(View.INVISIBLE);
        mensajeNoEncontrado.setVisibility(View.INVISIBLE);
        //consultarTiposProyectos();

        imageButtonBuscarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("CLIC EN BUSCAR USUARIO");
                asignar.setEnabled(false);
                if (CamposLlenos())
                {
                    mensajeNoEncontrado.setVisibility(View.INVISIBLE);
                    mensajeNoEncontrado.setText("");
                    idUsuario=Integer.parseInt( editTextIdUsuario.getText().toString());
                    System.out.println("Nombre del proyecto desde dialogo UsuariosProyecto: "+listUsuariosProyectos.get(0).getProyecto_nombre());
                    buscarUsuario();
                }else{
                    mensajeNoEncontrado.setVisibility(View.VISIBLE);
                    constraintLayoutUsuario.setVisibility(View.INVISIBLE);
                    mensajeNoEncontrado.setText("Digite la cédula del usuario");
                }
            }
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogoUsuarioPryecto.dismiss();
                System.out.println(proyectoSeleccionado.getNombre()+"     DESDE DIALOGO EDITAR");
            }
        });

        asignar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(proyecto+"  EEEEEEEEEEEEE "+usuario);
                if(CamposLlenos()) {
                    webServiceAsignarUsuario();
                    dialogoUsuarioPryecto.dismiss();
                }else {
                    Toast.makeText(contexto1,"Hay campos vacios o listas sin elementos seleccionados",Toast.LENGTH_LONG).show();
                }

            }
        });
        dialogoUsuarioPryecto.show();
    }

   private boolean CamposLlenos(){
        boolean campoLleno=true;
        if(editTextIdUsuario.getText().toString().isEmpty()   ) {
            campoLleno=false;
        }
        return campoLleno;
    }

    private void buscarUsuario() {
       /* final ProgressDialog pDialog;
        pDialog=new ProgressDialog(contexto1);
        pDialog.setMessage("Cargando...");
        pDialog.show();*/

        listaUsuarios=new ArrayList<>();
        final String ip=contexto1.getString(R.string.ip_url);

        String url=ip+"/web_services/buscar_usuario.php?user="+idUsuario;

        System.out.println(url);

        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //pDialog.hide();

                Usuarios Usuario;

                JSONArray json=response.optJSONArray("datos");
                JSONObject jsonObject;
                List<String> listTipos=new ArrayList<String>();
                listTipos.add("Seleccione...");
                try {
                    for (int i=0;i<json.length();i++) {
                        jsonObject = json.getJSONObject(i);


                        usuario=new Usuarios(jsonObject.getString("usuario_id"), jsonObject.getString("nombre"), jsonObject.getString("apellido"), jsonObject.getString("contrasena"));

                        System.out.println(jsonObject.getString("nombre")+ "  "+ i+json.length());
                        listaUsuarios.add(usuario);

                    }

                    if(listaUsuarios.size()>0)
                    {
                        System.out.println("LA longitud es : "+listaUsuarios.size( ) + listaUsuarios.get(0).getNombre()  );

                        if(listaUsuarios.get(0).getNombre().equals("null") )
                        {
                            constraintLayoutUsuario.setVisibility(View.INVISIBLE);
                            mensajeNoEncontrado.setText("El usuario con cédula " + idUsuario + " no se encuentra registrado");
                            mensajeNoEncontrado.setVisibility(View.VISIBLE);

                        }else
                        {
                            boolean a=true;
                            int i=0;
                            while (a && listaUsuariosProyectos2.size()>i)
                            {
                                if (listaUsuariosProyectos2.get(i).getUsuario_nombre().equals(listaUsuarios.get(0).getNombre()))
                                {
                                    mensajeNoEncontrado.setText("El usuario "+ listaUsuarios.get(0).getNombre()+ " con identificación:"+listaUsuarios.get(0).getUsuario_id()+", ya se encuentra asignado al proyecto");
                                    mensajeNoEncontrado.setVisibility(View.VISIBLE);
                                    constraintLayoutUsuario.setVisibility(View.INVISIBLE);
                                    a=false;
                                }else
                                {
                                    mensajeNoEncontrado.setVisibility(View.INVISIBLE);
                                    constraintLayoutUsuario.setVisibility(View.VISIBLE);
                                    textViewIdUsuario.setText(listaUsuarios.get(0).getUsuario_id());
                                    textViewNombre.setText(listaUsuarios.get(0).getNombre());
                                    nombreUsuario=listaUsuarios.get(0).getNombre();
                                    apellidoUsuario=listaUsuarios.get(0).getNombre();
                                    textViewApellidos.setText(listaUsuarios.get(0).getApellido());
                                    textViewCorreo.setText("Correo Electronico");
                                    System.out.println("APELLIDOS: "+listaUsuarios.get(0).getApellido() );
                                    consultarPerfiles();
                                    i+=1;
                                }


                            }



                        }

                    }



                }  catch (JSONException e) {
                    e.printStackTrace();
                }
                json=null;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(contexto1, "No se puede conectar "+error.toString(), Toast.LENGTH_LONG).show();
                System.out.println();
                //pDialog.hide();
                Log.d("ERROR: ", error.toString());
            }
        });

        // request.add(jsonObjectRequest);
        VolleySingleton.getIntanciaVolley(contexto1).addToRequestQueue(jsonObjectRequest);

    }
    private void consultarPerfiles() {
        /*final ProgressDialog pDialog;
        pDialog=new ProgressDialog(contexto1);
        pDialog.setMessage("Cargando...");
        pDialog.show();*/

        listaPerfiles=new ArrayList<>();
        final String ip=contexto1.getString(R.string.ip_url);

        String url=ip+"web_services/perfiles.php";

        System.out.println(url);

        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //pDialog.hide();

                Perfiles perfil;

                JSONArray json=response.optJSONArray("perfiles");
                JSONObject jsonObject;
                List<String> listTipos=new ArrayList<String>();
                listTipos.add("Seleccione...");
                try {
                    for (int i=0;i<json.length();i++) {
                        jsonObject = json.getJSONObject(i);


                        perfil=new Perfiles(jsonObject.getInt("perfil_id"), jsonObject.getString("descripcion"));

                        System.out.println(jsonObject.getString("descripcion")+ "  "+ i+json.length());
                        listaPerfiles.add(perfil);
                        listTipos.add(jsonObject.getString("descripcion"));

                    }
                    ArrayAdapter<CharSequence> spinnerArrayAdapter= new ArrayAdapter(contexto1, android.R.layout.simple_spinner_item, listTipos);
                    spinnerPerfilUsuario.setAdapter(spinnerArrayAdapter);
                    spinnerPerfilUsuario.setSelection(0);
                    spinnerPerfilUsuario.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            System.out.println(" el seleccionado: "+ position);
                            if (position!=0) {
                                System.out.println(listaPerfiles.get(position - 1).getPefil_id());
                                idPerfil=listaPerfiles.get(position - 1).getPefil_id();
                                descripcionPerfil=listaPerfiles.get(position - 1).getDescripcion();
                                asignar.setEnabled(true);

                            }else
                            {
                                idPerfil=-1;
                                asignar.setEnabled(false);



                                System.out.println(idPerfil);
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
                Toast.makeText(contexto1, "No se puede conectar "+error.toString(), Toast.LENGTH_LONG).show();
                System.out.println();
                //pDialog.hide();
                Log.d("ERROR: ", error.toString());
            }
        });

        // request.add(jsonObjectRequest);
        VolleySingleton.getIntanciaVolley(contexto1).addToRequestQueue(jsonObjectRequest);

    }


    private void webServiceAsignarUsuario() {
        System.out.println("Usuario:" + idUsuario+" Proyecto: "+ idProyecto+ " Perfil:"+ idPerfil);

        /*final ProgressDialog pDialog=new ProgressDialog(contexto1);
        pDialog.setMessage("Cargando...");
        pDialog.show();*/
        System.out.println(proyecto+"  GGGGGGGGGGG "+usuario);
        String ip=contexto1.getString(R.string.ip_url);

        String url=ip+"/web_services/insertar_usuario_proyecto.php?";

        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //pDialog.hide();

                if (response.trim().equalsIgnoreCase("inserto")){
                    // etiNombre.setText("");
                    //  txtDocumento.setText("");
                    //   etiProfesion.setText("");
                    Toast.makeText(contexto1,"Se ha asignado el usuario al proyecto con exito",Toast.LENGTH_SHORT).show();
                    System.out.println(proyecto.getNombre()+"  GGGGGGGGGGG "+usuario.getUsuario_id());
                    //argumentosEnviados= new Bundle();
                    //argumentosEnviados.putSerializable("proyecto",proyecto);
                    //argumentosEnviados.putSerializable("usuario",usuario);
                    //VerProyectoFragment proyectos= new VerProyectoFragment();
                    //FragmentManager manager= manager1;
                    //manager.beginTransaction().replace(R.id.contenidos, proyectos).commit();
                   // proyecto.setNombre(nombreProyecto.getText().toString());
                    //proyecto.setUbicacion(ubicacion.getText().toString());
                    //proyecto.setReferencias_administrativas(referencias.getText().toString());
                    //proyecto.setAval_cientifico(aval.getText().toString());

                    usuarioProyecto=new UsuariosProyectos(idProyecto, idUsuario, idPerfil, nombreProyecto
                            , nombreUsuario , apellidoUsuario, descripcionPerfil);
                      interfaz.UsuarioProyectoAsignado(usuarioProyecto);
                }else{
                    Toast.makeText(contexto1,"No se ha insertado ",Toast.LENGTH_SHORT).show();
                    Log.i("RESPUESTA: ",""+response);
                    System.out.println(response);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(contexto1,"No se ha podido conectar",Toast.LENGTH_SHORT).show();
                //pDialog.hide();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {


                Map<String,String> parametros=new HashMap<>();
                parametros.put("perfil_id",idPerfil+"");
                parametros.put("proyectos_proyecto_id",idProyecto+"");
                parametros.put("usuarios_usuario_id",idUsuario+"");
                return parametros;
            }
        };
        //request.add(stringRequest);
        VolleySingleton.getIntanciaVolley(contexto1).addToRequestQueue(stringRequest);
    }

}
