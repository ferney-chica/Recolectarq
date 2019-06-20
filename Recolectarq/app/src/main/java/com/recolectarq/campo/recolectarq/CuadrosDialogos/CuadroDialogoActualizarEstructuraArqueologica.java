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
import com.recolectarq.campo.recolectarq.Modelo.EstructurasArqueologicas;
import com.recolectarq.campo.recolectarq.Modelo.PerfilesExpuestos;
import com.recolectarq.campo.recolectarq.Modelo.PozosSondeo;
import com.recolectarq.campo.recolectarq.Modelo.Proyectos;
import com.recolectarq.campo.recolectarq.Modelo.RecoleccionesSuperficiales;
import com.recolectarq.campo.recolectarq.Modelo.TiposEstructuras;
import com.recolectarq.campo.recolectarq.Modelo.Tipos_Proyectos;
import com.recolectarq.campo.recolectarq.Modelo.TopologiasEstructuras;
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


public class CuadroDialogoActualizarEstructuraArqueologica {

    private TextView textViewActualizarEstructuraId;
    private TextView textViewActualizarEstructuraIdIntervencion;
    private Spinner spinnerActualizarEstructuraTipoEstructura;
    private Spinner spinnerActualizarEstructuraTopologiaEstructura;
    private EditText editTextActualizarEstructuraDescripcion;
    private EditText editTextActualizarEstructuraPuntoConexo;
    private EditText editTextActualizarEstructuraUtmx;
    private EditText editTextActualizarEstructuraUtmy;
    private EditText editTextActualizarEstructuraLatitud;
    private EditText editTextActualizarEstructuraLongitud;
    private EditText editTextActualizarEstructuraDimension;
    private EditText editTextActualizarEstructuraEntorno;

    private Button buttonActualizarEstructuraCancelar;
    private Button buttonActualizarEstructura;
    private Context contexto1;

    private JsonObjectRequest jsonObjectRequest;
    private ArrayList<Tipos_Proyectos> listaTiposProyectos;
    private ArrayList<TiposEstructuras> listaTiposEstructuras;
    private ArrayList<TopologiasEstructuras> listaTopologiasEstructuras;

    private TiposEstructuras tipoEstructura;
    private TopologiasEstructuras topologiaEstructura;

    private int idTipoEstructura;
    private int tipoEstructuraSeleccionado;
    private String tipoEstructuraNombre;
    private int idTopologiaEstructura;
    private int topologiaSeleccionada;
    private String topologiaEstructuraNombre;

    private int idPozo;
    private int idRecoleccion;
    private int idPerfil;
    private int idTipoSeleccionado;
    private  int idProyecto;
    private FragmentManager manager1;
    private EstructurasArqueologicas estructuraEnviada;
    private String origen1;
    private Usuarios usuario= new Usuarios();
    private boolean cambio=false;
    private  Bundle argumentosEnviados;
    private FinalizarCuadroDialogo interfaz;
    private Dialog dialogoEstructura;

    public interface FinalizarCuadroDialogo
    {
        void EstructuraArqueologicaActualizado(EstructurasArqueologicas estructuraArqueologica);
    }


    public CuadroDialogoActualizarEstructuraArqueologica(final Context contexto, EstructurasArqueologicas estructura, String origen, final FinalizarCuadroDialogo actividad) {
     interfaz=actividad;
         dialogoEstructura= new Dialog(contexto);
         //manager1= manager;
         contexto1=contexto;
         estructuraEnviada=estructura;
         origen1=origen;

        dialogoEstructura.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogoEstructura.setCancelable(false);
        dialogoEstructura.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogoEstructura.setContentView(R.layout.cuadro_dialogo_editar_estructura_arqueologica);

        textViewActualizarEstructuraId=dialogoEstructura.findViewById(R.id.textViewActualizarEstructuraId);
        textViewActualizarEstructuraIdIntervencion=dialogoEstructura.findViewById(R.id.textViewActualizarEstructuraIdIntervencion);
        spinnerActualizarEstructuraTipoEstructura=dialogoEstructura.findViewById(R.id.spinnerActualizarEstructuraTipoEstructura);
        spinnerActualizarEstructuraTopologiaEstructura=dialogoEstructura.findViewById(R.id.spinnerActualizarEstructuraTopologiaEstructura);
        editTextActualizarEstructuraDescripcion=dialogoEstructura.findViewById(R.id.editTextActualizarEstructuraDescripcion);
        editTextActualizarEstructuraPuntoConexo=dialogoEstructura.findViewById(R.id.editTextActualizarEstructuraPuntoConexo);
        editTextActualizarEstructuraUtmx=dialogoEstructura.findViewById(R.id.editTextActualizarEstructuraUtmx);
        editTextActualizarEstructuraUtmy=dialogoEstructura.findViewById(R.id.editTextActualizarEstructuraUtmy);
        editTextActualizarEstructuraLatitud=dialogoEstructura.findViewById(R.id.editTextActualizarEstructuraLatitud);
        editTextActualizarEstructuraLongitud=dialogoEstructura.findViewById(R.id.editTextActualizarEstructuraLongitud);
        editTextActualizarEstructuraDimension=dialogoEstructura.findViewById(R.id.editTextActualizarEstructuraDimension);
        editTextActualizarEstructuraEntorno=dialogoEstructura.findViewById(R.id.editTextActualizarEstructuraEntorno);

        buttonActualizarEstructuraCancelar=dialogoEstructura.findViewById(R.id.buttonActualizarEstructuraCancelar);
        buttonActualizarEstructura=dialogoEstructura.findViewById(R.id.buttonActualizarEstructura);

        listaTopologiasEstructuras=new ArrayList<>();
        listaTiposEstructuras=new ArrayList<>();

        llenarCampos();
        //consultarTiposProyectos();



        buttonActualizarEstructuraCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogoEstructura.dismiss();
            }
        });

        buttonActualizarEstructura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CamposLlenos();

            }
        });
        dialogoEstructura.show();
    }

    private void llenarCampos()
    {
        textViewActualizarEstructuraId.setText("Id Estructura:"+estructuraEnviada.getEstructuras_arqueologicas_id()+"");

        editTextActualizarEstructuraDescripcion.setText(estructuraEnviada.getDescripcion()+"");
        editTextActualizarEstructuraPuntoConexo.setText(estructuraEnviada.getPunto_conexo()+"");
        editTextActualizarEstructuraUtmx.setText(estructuraEnviada.getUtmx()+"");
        editTextActualizarEstructuraUtmy.setText(estructuraEnviada.getUtmy()+"");
        editTextActualizarEstructuraLatitud.setText(estructuraEnviada.getLatitud()+"");
        editTextActualizarEstructuraLongitud.setText(estructuraEnviada.getLongitud()+"");
        editTextActualizarEstructuraDimension.setText(estructuraEnviada.getDimension()+"");
        editTextActualizarEstructuraEntorno.setText(estructuraEnviada.getEntorno()+"");

        consultarTopologiasEstructuras();
        consultarTiposEstructuras();

        switch (origen1)
        {
            case "pozo":

                tipoEstructuraSeleccionado=estructuraEnviada.getTipos_estructuras_id();
                topologiaSeleccionada=estructuraEnviada.getTopologias_estructuras_id();
                idPozo=estructuraEnviada.getPozos_pozo_id();
                textViewActualizarEstructuraIdIntervencion.setText("Id Pozo: "+estructuraEnviada.getPozos_pozo_id()+"");
            break;
            case "recoleccion":

                tipoEstructuraSeleccionado=estructuraEnviada.getTipos_estructuras_id();
                topologiaSeleccionada=estructuraEnviada.getTopologias_estructuras_id();
                idRecoleccion=estructuraEnviada.getRecolecciones_superficiales_recoleccion_superficial_id();
                textViewActualizarEstructuraIdIntervencion.setText("Id Recolección: "+estructuraEnviada.getRecolecciones_superficiales_recoleccion_superficial_id()+"");
                break;
            case "perfil":

                tipoEstructuraSeleccionado=estructuraEnviada.getTipos_estructuras_id();
                topologiaSeleccionada=estructuraEnviada.getTopologias_estructuras_id();
                idPerfil=estructuraEnviada.getPerfiles_expuestos_perfil_expuesto_id();
                textViewActualizarEstructuraIdIntervencion.setText("Id Recolección: "+estructuraEnviada.getPerfiles_expuestos_perfil_expuesto_id()+"");
                break;
        }

    }

   private void CamposLlenos(){
       if (idTipoEstructura == -1 || idTopologiaEstructura == -1 || editTextActualizarEstructuraDescripcion.getText().toString().trim().isEmpty() ||
               editTextActualizarEstructuraPuntoConexo.getText().toString().trim().isEmpty() || editTextActualizarEstructuraUtmx.getText().toString().trim().isEmpty() ||
               editTextActualizarEstructuraUtmy.getText().toString().trim().isEmpty() || editTextActualizarEstructuraLatitud.getText().toString().trim().isEmpty() ||
               editTextActualizarEstructuraLongitud.getText().toString().trim().isEmpty() || editTextActualizarEstructuraDimension.getText().toString().trim().isEmpty()
               || editTextActualizarEstructuraEntorno.getText().toString().trim().isEmpty()) {
           Toast.makeText(contexto1, "Hay campos sin diligenciar", Toast.LENGTH_LONG).show();
       } else {

           webServiceActualizarEstructura();
           dialogoEstructura.dismiss();
       }

    }

    private void consultarTiposEstructuras() {
        /*final ProgressDialog pDialog;
        pDialog = new ProgressDialog(contexto1);
        pDialog.setMessage("Cargando...");
        pDialog.show();*/

        final String ip = contexto1.getString(R.string.ip_url);

        String url = ip + "/web_services/tipos_estructuras.php";

        System.out.println(url);

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //pDialog.hide();

                JSONArray json = response.optJSONArray("tipos_estructuras");
                JSONObject jsonObject;
                List<String> listTipos = new ArrayList<String>();
                listTipos.add("Seleccione...");
                try {
                    for (int i = 0; i < json.length(); i++) {
                        jsonObject = json.getJSONObject(i);


                        tipoEstructura = new TiposEstructuras(jsonObject.getInt("id"), jsonObject.getString("nombre"));

                        System.out.println(jsonObject.getString("nombre") + "  " + i + json.length());
                        listaTiposEstructuras.add(tipoEstructura);
                        listTipos.add(jsonObject.getString("nombre"));

                    }
                    ArrayAdapter<CharSequence> spinnerArrayAdapter = new ArrayAdapter(contexto1, android.R.layout.simple_spinner_item, listTipos);
                    spinnerActualizarEstructuraTipoEstructura.setAdapter(spinnerArrayAdapter);
                    spinnerActualizarEstructuraTipoEstructura.setSelection(tipoEstructuraSeleccionado);

                    spinnerActualizarEstructuraTipoEstructura.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            if (position != 0) {
                                System.out.println(listaTiposEstructuras.get(position - 1).getId());
                                idTipoEstructura = listaTiposEstructuras.get(position - 1).getId();
                                tipoEstructuraNombre=listaTiposEstructuras.get(position - 1).getNombre();
                            } else {
                                idTipoEstructura = -1;
                                System.out.println(idTipoEstructura);
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
                Toast.makeText(contexto1, "No se puede conectar " + error.toString(), Toast.LENGTH_LONG).show();
                System.out.println();
                //pDialog.hide();
                Log.d("ERROR: ", error.toString());
            }
        });

        // request.add(jsonObjectRequest);
        VolleySingleton.getIntanciaVolley(contexto1).addToRequestQueue(jsonObjectRequest);

    }

    private void consultarTopologiasEstructuras() {
        /*final ProgressDialog pDialog;
        pDialog = new ProgressDialog(contexto1);
        pDialog.setMessage("Cargando...");
        pDialog.show();*/

        final String ip = contexto1.getString(R.string.ip_url);

        String url = ip + "/web_services/topologias_estructuras.php";

        System.out.println(url);

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //pDialog.hide();

                JSONArray json = response.optJSONArray("topologias_estructuras");
                JSONObject jsonObject;
                List<String> listTipos = new ArrayList<String>();
                listTipos.add("Seleccione...");
                try {
                    for (int i = 0; i < json.length(); i++) {
                        jsonObject = json.getJSONObject(i);


                        topologiaEstructura = new TopologiasEstructuras(jsonObject.getInt("id"), jsonObject.getString("nombre"));

                        System.out.println(jsonObject.getString("nombre") + "  " + i + json.length());
                        listaTopologiasEstructuras.add(topologiaEstructura);
                        listTipos.add(jsonObject.getString("nombre"));

                    }
                    ArrayAdapter<CharSequence> spinnerArrayAdapter = new ArrayAdapter(contexto1, android.R.layout.simple_spinner_item, listTipos);
                    spinnerActualizarEstructuraTopologiaEstructura.setAdapter(spinnerArrayAdapter);
                    spinnerActualizarEstructuraTopologiaEstructura.setSelection(topologiaSeleccionada);

                    spinnerActualizarEstructuraTopologiaEstructura.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            if (position != 0) {
                                System.out.println(listaTopologiasEstructuras.get(position - 1).getId());
                                idTopologiaEstructura = listaTopologiasEstructuras.get(position - 1).getId();
                                topologiaEstructuraNombre=listaTopologiasEstructuras.get(position - 1).getNombre();
                            } else {
                                idTopologiaEstructura = -1;
                                System.out.println(idTopologiaEstructura);
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
                Toast.makeText(contexto1, "No se puede conectar " + error.toString(), Toast.LENGTH_LONG).show();
                System.out.println();
                //pDialog.hide();
                Log.d("ERROR: ", error.toString());
            }
        });

        // request.add(jsonObjectRequest);
        VolleySingleton.getIntanciaVolley(contexto1).addToRequestQueue(jsonObjectRequest);

    }

    private void webServiceActualizarEstructura() {
        /*final ProgressDialog pDialog=new ProgressDialog(contexto1);
        pDialog.setMessage("Cargando...");
        pDialog.show();*/
        String ip=contexto1.getString(R.string.ip_url);

        String url1=ip+"/web_services/editar_estructura_arqueologica.php?";

        StringRequest stringRequest=new StringRequest(Request.Method.POST, url1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //pDialog.hide();

                if (response.trim().equalsIgnoreCase("actualiza")){
                    // etiNombre.setText("");
                    //  txtDocumento.setText("");
                    //   etiProfesion.setText("");
                    Toast.makeText(contexto1,"Se ha Actualizado con exito",Toast.LENGTH_SHORT).show();
                    //argumentosEnviados= new Bundle();
                    //argumentosEnviados.putSerializable("proyecto",proyecto);
                    //argumentosEnviados.putSerializable("usuario",usuario);
                    //VerProyectoFragment proyectos= new VerProyectoFragment();
                    //FragmentManager manager= manager1;
                    //manager.beginTransaction().replace(R.id.contenidos, proyectos).commit();
                    /*pozoEnviado.setDescripcion(editTextDescripcion.getText().toString());
                    pozoEnviado.setCodigo_rotulo(editTextCodigoRotulo.getText().toString());*/
                    EstructurasArqueologicas estructuraActuaizada=new EstructurasArqueologicas(estructuraEnviada.getEstructuras_arqueologicas_id(),idTipoEstructura,tipoEstructuraNombre,
                            idTopologiaEstructura,topologiaEstructuraNombre,estructuraEnviada.getPerfiles_expuestos_perfil_expuesto_id(),estructuraEnviada.getRecolecciones_superficiales_recoleccion_superficial_id(),
                            estructuraEnviada.getPozos_pozo_id(),editTextActualizarEstructuraDescripcion.getText().toString().trim(),editTextActualizarEstructuraPuntoConexo.getText().toString().trim(),
                            Float.parseFloat(editTextActualizarEstructuraUtmx.getText().toString().trim()),Float.parseFloat(editTextActualizarEstructuraUtmy.getText().toString().trim()),editTextActualizarEstructuraLatitud.getText().toString().trim(),
                            editTextActualizarEstructuraLongitud.getText().toString().trim(), editTextActualizarEstructuraDimension.getText().toString().trim(),
                            editTextActualizarEstructuraEntorno.getText().toString().trim(),estructuraEnviada.getIntervencion());
                      interfaz.EstructuraArqueologicaActualizado(estructuraActuaizada);
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
                /*parametros.put("pozo_id",pozoEnviado.getPozo_id()+"");*/
                parametros.put("estructuras_arqueologicas_id",estructuraEnviada.getEstructuras_arqueologicas_id()+"");
                parametros.put("tipos_estructuras_id",idTipoEstructura+"");
                parametros.put("topologias_estructuras_id",idTopologiaEstructura+"");
                parametros.put("descripcion",editTextActualizarEstructuraDescripcion.getText().toString().trim());
                parametros.put("punto_conexo",editTextActualizarEstructuraPuntoConexo.getText().toString().trim());
                parametros.put("utmx",editTextActualizarEstructuraUtmx.getText().toString().trim());
                parametros.put("utmy",editTextActualizarEstructuraUtmy.getText().toString().trim());
                parametros.put("latitud",editTextActualizarEstructuraLatitud.getText().toString().trim());
                parametros.put("longitud",editTextActualizarEstructuraLongitud.getText().toString().trim());
                parametros.put("dimension",editTextActualizarEstructuraDimension.getText().toString().trim());
                parametros.put("entorno",editTextActualizarEstructuraEntorno.getText().toString().trim());


                return parametros;
            }
        };
        //request.add(stringRequest);
        VolleySingleton.getIntanciaVolley(contexto1).addToRequestQueue(stringRequest);
    }

}
