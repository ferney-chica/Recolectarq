package com.recolectarq.campo.recolectarq.CuadrosDialogos;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.recolectarq.campo.recolectarq.Modelo.EstratosPerfiles;
import com.recolectarq.campo.recolectarq.Modelo.EstratosPozos;
import com.recolectarq.campo.recolectarq.Modelo.EstructurasEstratos;
import com.recolectarq.campo.recolectarq.Modelo.NivelesPozos;
import com.recolectarq.campo.recolectarq.Modelo.Proyectos;
import com.recolectarq.campo.recolectarq.Modelo.TexturasEstratos;
import com.recolectarq.campo.recolectarq.Modelo.TiposEstratos;
import com.recolectarq.campo.recolectarq.Modelo.Tipos_Proyectos;
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


public class CuadroDialogoActualizarEstrato {

    private TextView textViewActualizarEstratoId;
    private TextView textViewActualizarPozo_PerfilExpuestoId;
    private EditText editTextActualizarEstratoProfundidad;
    private EditText editTextActualizarEstratoColor;
    private EditText editTextActualizarEstratoObservacion;
    private Spinner spinnerActualizarEstratoTextura;
    private Spinner spinnerActualizarEstratoTipoEstrato;
    private Spinner spinnerActualizarEstratoEstructura;


    private TextView textViewActualizarEstratoCodigoRotulo;
    private EditText editTextActualizarEstratoCodigoRotulo;

    private Button cancelar;
    private Button actualizar;
    private Context contexto1;

    private JsonObjectRequest jsonObjectRequest;
    private ArrayList<Tipos_Proyectos> listaTiposProyectos;
    private int idTipo;
    private int idTipoSeleccionado;
    private  int idProyecto;
    private int idTipoEstrato;
    private int idTipoEstratoSeleccionado;
    private int idEstructuraEstrato;
    private int idEstructuraEstratoSeleccionado;
    private int idTexturaEstrato;
    private int idTexturaEstratoSeleccionado;
    private FragmentManager manager1;
    private Proyectos proyecto=new Proyectos();
    private EstratosPozos estratoPozoEnviado;
    private EstratosPerfiles estratoPerfilEnviado;

    private Usuarios usuario= new Usuarios();
    private boolean cambio=false;
    private  Bundle argumentosEnviados;
    private FinalizarCuadroDialogo interfaz;
    private TiposEstratos tipoEstrato;
    private ArrayList<TiposEstratos> listaTiposEstratos;
    private EstructurasEstratos estructuraEstrato;
    private ArrayList<EstructurasEstratos> listaEstructurasEstratos;
    private TexturasEstratos texturaEstrato;
    private ArrayList<TexturasEstratos> listaTexturasEstratos;
    final Dialog dialogoEstrato;
    private String origen1;


    public interface FinalizarCuadroDialogo
    {
        void EstratoPozoActualizado(EstratosPozos estratoPozo);
        void EstratoPerfilActualizado(EstratosPerfiles estratoPerfil);
    }


    public CuadroDialogoActualizarEstrato(final Context contexto, EstratosPozos estratoPozoSondeo, EstratosPerfiles estratoPerfil,String origen, final FinalizarCuadroDialogo actividad) {
     interfaz=actividad;

        origen1=origen;
        listaTiposEstratos=new ArrayList<>();
        listaEstructurasEstratos=new ArrayList<>();
        listaTexturasEstratos=new ArrayList<>();
        contexto1=contexto;
        dialogoEstrato= new Dialog(contexto);
        dialogoEstrato.setCancelable(false);
        dialogoEstrato.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogoEstrato.setContentView(R.layout.cuadro_dialogo_editar_estrato);
        textViewActualizarEstratoId=dialogoEstrato.findViewById(R.id.textViewActualizarEstratoId);
        textViewActualizarPozo_PerfilExpuestoId=dialogoEstrato.findViewById(R.id.textViewActualizarPozo_PerfilExpuestoId);
        editTextActualizarEstratoProfundidad=dialogoEstrato.findViewById(R.id.editTextActualizarEstratoProfundidad);
        editTextActualizarEstratoColor=dialogoEstrato.findViewById(R.id.editTextActualizarEstratoColor);
        editTextActualizarEstratoObservacion=dialogoEstrato.findViewById(R.id.editTextActualizarEstratoObservacion);
        spinnerActualizarEstratoTextura=dialogoEstrato.findViewById(R.id.spinnerActualizarEstratoTextura);
        spinnerActualizarEstratoTipoEstrato=dialogoEstrato.findViewById(R.id.spinnerActualizarEstratoTipoEstrato);
        spinnerActualizarEstratoEstructura=dialogoEstrato.findViewById(R.id.spinnerActualizarEstratoEstructura);

        textViewActualizarEstratoCodigoRotulo=dialogoEstrato.findViewById(R.id.textViewActualizarEstratoCodigoRotulo);
        editTextActualizarEstratoCodigoRotulo=dialogoEstrato.findViewById(R.id.editTextActualizarEstratoCodigoRotulo);


        cancelar=dialogoEstrato.findViewById(R.id.buttonCancelarActualizarEstrato);
        actualizar=dialogoEstrato.findViewById(R.id.buttonActualizarEstrato);
         System.out.println(proyecto+"  FFFFFFF "+usuario);

        estratoPozoEnviado=estratoPozoSondeo;
        estratoPerfilEnviado=estratoPerfil;
        llenarCampos(origen);

        consultarTiposEstratos();
        consultarEstructuras();
        consultarTexturas();

        System.out.println("VIENE DE ESTRATO:"+ origen);


       /* textViewActualizarNivelIdNivel.setText(textViewActualizarNivelIdNivel.getText()+" "+dialogoEstrato.getNivel_pozo_id());
        textViewActualizarNivelIdPozo.setText(textViewActualizarNivelIdPozo.getText()+" "+dialogoEstrato.getPozos_pozo_id());
        editTextActualizarNivelNumero.setText(nivelPozoSondeo.getNumero()+"");
        editTextActualizarNivelProfundidad.setText(nivelPozoSondeo.getProfundidad()+"");
        editTextActualizarNivelCodigoRotulo.setText(nivelPozoSondeo.getCodigo_rotulo());*/



        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogoEstrato.dismiss();
                //System.out.println(proyectoSeleccionado.getNombre()+"     DESDE DIALOGO EDITAR");
            }
        });

        actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(proyecto+"  EEEEEEEEEEEEE "+usuario);
                CamposLlenos();

            }
        });
        dialogoEstrato.show();
    }

    private void llenarCampos(String origen)
    {
        switch (origen)
        {
            case "estratopozo":

                textViewActualizarEstratoId.setText("Id Estrato: " +estratoPozoEnviado.getEstrato_pozo_id()+"");
                editTextActualizarEstratoProfundidad.setText(estratoPozoEnviado.getProfundidad()+"");
                editTextActualizarEstratoColor.setText(estratoPozoEnviado.getColor()+"");
                editTextActualizarEstratoObservacion.setText(estratoPozoEnviado.getObservacion());

                textViewActualizarEstratoCodigoRotulo.setVisibility(View.INVISIBLE);
                editTextActualizarEstratoCodigoRotulo.setVisibility(View.INVISIBLE);

                idTipoEstratoSeleccionado=estratoPozoEnviado.getTipos_estratos_tipo_estrato_id();
                idEstructuraEstratoSeleccionado=estratoPozoEnviado.getEstructuras_estratos_estructura_estrato_id();
                System.out.println("El id de la estructura seleccionada es " +idEstructuraEstratoSeleccionado);
                idTexturaEstratoSeleccionado=estratoPozoEnviado.getTexturas_estratos_textura_estrato_id();

                textViewActualizarPozo_PerfilExpuestoId.setText("Id Pozo: "+estratoPozoEnviado.getPozos_pozo_id()+"");
                break;
            case "estratoPerfil":

                textViewActualizarEstratoId.setText("Id Estrato: " +estratoPerfilEnviado.getEstrato_perfil_id()+"");
                editTextActualizarEstratoProfundidad.setText(estratoPerfilEnviado.getProfundidad()+"");
                editTextActualizarEstratoColor.setText(estratoPerfilEnviado.getColor()+"");
                editTextActualizarEstratoObservacion.setText(estratoPerfilEnviado.getObservacion());
                editTextActualizarEstratoCodigoRotulo.setText(estratoPerfilEnviado.getCodigo_rotulo());

                idTipoEstratoSeleccionado=estratoPerfilEnviado.getTipos_estratos_tipo_estrato_id();
                idEstructuraEstratoSeleccionado=estratoPerfilEnviado.getEstructuras_estratos_estructura_estrato_id();
                idTexturaEstratoSeleccionado=estratoPerfilEnviado.getTexturas_estratos_textura_estrato_id();

                textViewActualizarPozo_PerfilExpuestoId.setText("Id Perfil: "+ estratoPerfilEnviado.getPerfiles_expuestos_perfil_expuesto_id());
                break;

        }
    }

   private void CamposLlenos(){

        if(idEstructuraEstrato==-1 || idTexturaEstrato==-1 || idTipoEstrato==-1 || editTextActualizarEstratoProfundidad.getText().toString().trim().isEmpty()|| editTextActualizarEstratoColor.getText().toString().trim().isEmpty() ) {
            Toast.makeText(contexto1,"Hay campos sin diligenciar",Toast.LENGTH_LONG).show();
        }else
            {

                if (editTextActualizarEstratoObservacion.getText().toString().trim().isEmpty())
                {
                    editTextActualizarEstratoObservacion.setText("---");
                }

                if (editTextActualizarEstratoCodigoRotulo.getText().toString().trim().isEmpty())
                {
                    editTextActualizarEstratoCodigoRotulo.setText("---");
                }
                webServiceActualizarEstrato();
                dialogoEstrato.dismiss();
            }

    }


    private void webServiceActualizarEstrato() {

       /*final ProgressDialog pDialog=new ProgressDialog(contexto1);
        pDialog.setMessage("Cargando...");
        pDialog.show();*/
        String ip=contexto1.getString(R.string.ip_url);
        String url;

        if (origen1.equals("estratopozo"))
        {
            url=ip+"/web_services/editar_estrato_pozo_sondeo.php?";
        }else {
            url=ip+"/web_services/editar_estrato_perfil_expuesto.php?";
        }


        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //pDialog.hide();

                if (response.trim().equalsIgnoreCase("actualiza")){
                    // etiNombre.setText("");
                    //  txtDocumento.setText("");
                    //   etiProfesion.setText("");
                    Toast.makeText(contexto1,"Se ha Actualizado con exito",Toast.LENGTH_SHORT).show();
                    System.out.println(proyecto.getNombre()+"  GGGGGGGGGGG "+usuario.getUsuario_id());
                    //argumentosEnviados= new Bundle();
                    //argumentosEnviados.putSerializable("proyecto",proyecto);
                    //argumentosEnviados.putSerializable("usuario",usuario);
                    //VerProyectoFragment proyectos= new VerProyectoFragment();
                    //FragmentManager manager= manager1;
                    //manager.beginTransaction().replace(R.id.contenidos, proyectos).commit();
                    if (origen1.equals("estratopozo")) {
                        estratoPozoEnviado.setProfundidad(Float.parseFloat(editTextActualizarEstratoProfundidad.getText().toString().trim()));
                        estratoPozoEnviado.setTipos_estratos_tipo_estrato_id(idTipoEstrato);
                        estratoPozoEnviado.setColor(editTextActualizarEstratoColor.getText().toString());
                        estratoPozoEnviado.setTexturas_estratos_textura_estrato_id(idTexturaEstrato);
                        estratoPozoEnviado.setEstructuras_estratos_estructura_estrato_id(idEstructuraEstrato);
                        estratoPozoEnviado.setObservacion(editTextActualizarEstratoObservacion.getText().toString().trim());
                        interfaz.EstratoPozoActualizado(estratoPozoEnviado);
                    }else
                    {
                        estratoPerfilEnviado.setObservacion(editTextActualizarEstratoObservacion.getText().toString().trim());
                        interfaz.EstratoPerfilActualizado(estratoPerfilEnviado);
                    }


                }else{
                    Toast.makeText(contexto1,"No se ha Actualizado ",Toast.LENGTH_SHORT).show();
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

                if (origen1.equals("estratopozo"))
                {
                    parametros.put("estrato_pozo_id",estratoPozoEnviado.getEstrato_pozo_id()+"");
                    parametros.put("texturas_estratos_textura_estrato_id",idTexturaEstrato+"");
                    parametros.put("tipos_estratos_tipo_estrato_id",idTipoEstrato+"");
                    parametros.put("estructuras_estratos_estructura_estrato_id",idEstructuraEstrato+"");
                    parametros.put("profundidad",editTextActualizarEstratoProfundidad.getText().toString().trim());
                    parametros.put("color",editTextActualizarEstratoColor.getText().toString().trim());
                    parametros.put("observacion",editTextActualizarEstratoObservacion.getText().toString().trim());
                }else {
                    parametros.put("estrato_perfil_id",estratoPerfilEnviado.getEstrato_perfil_id()+"");
                    parametros.put("texturas_estratos_textura_estrato_id",idTexturaEstrato+"");
                    parametros.put("tipos_estratos_tipo_estrato_id",idTipoEstrato+"");
                    parametros.put("estructuras_estratos_estructura_estrato_id",idEstructuraEstrato+"");
                    parametros.put("profundidad",editTextActualizarEstratoProfundidad.getText().toString().trim());
                    parametros.put("color",editTextActualizarEstratoColor.getText().toString().trim());
                    parametros.put("observacion",editTextActualizarEstratoObservacion.getText().toString().trim());
                    parametros.put("codigo_rotulo",editTextActualizarEstratoCodigoRotulo.getText().toString().trim());

                }
                return parametros;
            }
        };
        //request.add(stringRequest);
        VolleySingleton.getIntanciaVolley(contexto1).addToRequestQueue(stringRequest);
    }

    private void consultarTiposEstratos() {
        /*final ProgressDialog pDialog;
        pDialog=new ProgressDialog(contexto1);
        pDialog.setMessage("Cargando...");
        pDialog.show();*/

        final String ip=contexto1.getString(R.string.ip_url);

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
                    ArrayAdapter<CharSequence> spinnerArrayAdapter= new ArrayAdapter(contexto1, android.R.layout.simple_spinner_item, listTipos);
                    spinnerActualizarEstratoTipoEstrato.setAdapter(spinnerArrayAdapter);
                    spinnerActualizarEstratoTipoEstrato.setSelection(idTipoEstratoSeleccionado);

                    spinnerActualizarEstratoTipoEstrato.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                Toast.makeText(contexto1, "No se puede conectar "+error.toString(), Toast.LENGTH_LONG).show();
                System.out.println();
                //pDialog.hide();
                Log.d("ERROR: ", error.toString());
            }
        });

        // request.add(jsonObjectRequest);
        VolleySingleton.getIntanciaVolley(contexto1).addToRequestQueue(jsonObjectRequest);

    }

    private void consultarEstructuras() {
        /*final ProgressDialog pDialog;
        pDialog=new ProgressDialog(contexto1);
        pDialog.setMessage("Cargando...");
        pDialog.show();*/

        final String ip=contexto1.getString(R.string.ip_url);

        String url=ip+"/web_services/estructuras_estratos.php";

        System.out.println(url);

        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //pDialog.hide();

                JSONArray json=response.optJSONArray("estructuras_estratos");
                JSONObject jsonObject;
                List<String> listTipos=new ArrayList<String>();
                System.out.print("longitud de lista antes"+listTipos.size());
                listTipos.add("Seleccione...");
                try {

                    System.out.println("La longitud es: "+ json.length()+"////////////////////");
                    for (int i=0;i<json.length();i++) {
                        jsonObject = json.getJSONObject(i);


                        estructuraEstrato =new EstructurasEstratos(jsonObject.getInt("estructura_estrato_id"), jsonObject.getString("nombre"));

                        System.out.println(jsonObject.getString("nombre")+ "  "+ i+json.length());
                        listaEstructurasEstratos.add(estructuraEstrato);
                        listTipos.add(jsonObject.getString("nombre"));

                    }

                    System.out.print("longitud de lista "+listTipos.size());
                    ArrayAdapter<CharSequence> spinnerArrayAdapter= new ArrayAdapter(contexto1, android.R.layout.simple_spinner_item, listTipos);
                    spinnerActualizarEstratoEstructura.setAdapter(spinnerArrayAdapter);
                    spinnerActualizarEstratoEstructura.setSelection(idEstructuraEstratoSeleccionado);

                    spinnerActualizarEstratoEstructura.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            if (position!=0) {
                                System.out.println("Selecciono"+ listaEstructurasEstratos.get(position-1).getNombre()+" "+ listaEstructurasEstratos.get(position - 1).getEstructura_estrato_id());
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
                Toast.makeText(contexto1, "No se puede conectar "+error.toString(), Toast.LENGTH_LONG).show();
                System.out.println();
                //pDialog.hide();
                Log.d("ERROR: ", error.toString());
            }
        });

        // request.add(jsonObjectRequest);
        VolleySingleton.getIntanciaVolley(contexto1).addToRequestQueue(jsonObjectRequest);

    }

    private void consultarTexturas() {
        /*final ProgressDialog pDialog;
        pDialog=new ProgressDialog(contexto1);
        pDialog.setMessage("Cargando...");
        pDialog.show();*/

        final String ip=contexto1.getString(R.string.ip_url);

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
                    ArrayAdapter<CharSequence> spinnerArrayAdapter= new ArrayAdapter(contexto1, android.R.layout.simple_spinner_item, listTipos);
                    spinnerActualizarEstratoTextura.setAdapter(spinnerArrayAdapter);
                    spinnerActualizarEstratoTextura.setSelection(idTexturaEstratoSeleccionado);

                    spinnerActualizarEstratoTextura.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                Toast.makeText(contexto1, "No se puede conectar "+error.toString(), Toast.LENGTH_LONG).show();
                System.out.println();
                //pDialog.hide();
                Log.d("ERROR: ", error.toString());
            }
        });

        // request.add(jsonObjectRequest);
        VolleySingleton.getIntanciaVolley(contexto1).addToRequestQueue(jsonObjectRequest);

    }

}


