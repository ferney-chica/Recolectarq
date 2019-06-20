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
import com.recolectarq.campo.recolectarq.Modelo.Muestras;
import com.recolectarq.campo.recolectarq.Modelo.TiposEstructuras;
import com.recolectarq.campo.recolectarq.Modelo.TiposMateriales;
import com.recolectarq.campo.recolectarq.Modelo.TiposMuestras;
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


public class CuadroDialogoActualizarMuestra {

    private TextView textViewActualizarMuestraId;
    private TextView textViewActualizarMuestraIdIntervencion;
    private Spinner spinnerActualizarMuestraTipoMuestra;
    private Spinner spinnerActualizarMuestraTipoMaterial;
    private EditText editTextActualizarMuestraArgumentacion;
    private EditText editTextActualizarMuestraDestino;
    private EditText editTextActualizarMuestraContexto;

    private Button buttonActualizarMuestraCancelar;
    private Button buttonActualizarMuestra;
    private Context contexto1;

    private JsonObjectRequest jsonObjectRequest;
    private ArrayList<Tipos_Proyectos> listaTiposProyectos;
    private ArrayList<TiposMuestras> listaTiposMuestras;
    private ArrayList<TiposMateriales> listaTiposMateriales;

    private TiposMuestras tipoMuestra;
    private TiposMateriales tipoMaterial;

    private int idTipoMuestra;
    private int tipoMuestraSeleccionada;
    private String tipoMuestraNombre;
    private int idTipoMaterial;
    private int tipoMaterialSeleccionado;
    private String tipoMaterialNombre;

    private int idPozo;
    private int idTipoSeleccionado;
    private  int idProyecto;
    private FragmentManager manager1;
    private Muestras muestraEnviada;
    private String origen1;
    private Usuarios usuario= new Usuarios();
    private boolean cambio=false;
    private  Bundle argumentosEnviados;
    private FinalizarCuadroDialogo interfaz;
    private Dialog dialogoMuestra;

    public interface FinalizarCuadroDialogo
    {
        void EstructuraArqueologicaActualizado(Muestras muestra);
    }


    public CuadroDialogoActualizarMuestra(final Context contexto, Muestras muestra, String origen, final FinalizarCuadroDialogo actividad) {
     interfaz=actividad;
         dialogoMuestra= new Dialog(contexto);
         //manager1= manager;
         contexto1=contexto;
         muestraEnviada=muestra;
         origen1=origen;

        dialogoMuestra.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogoMuestra.setCancelable(false);
        dialogoMuestra.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogoMuestra.setContentView(R.layout.cuadro_dialogo_editar_muestra);

        textViewActualizarMuestraId=dialogoMuestra.findViewById(R.id.textViewActualizarMuestraId);
        textViewActualizarMuestraIdIntervencion=dialogoMuestra.findViewById(R.id.textViewActualizarMuestraIdIntervencion);
        spinnerActualizarMuestraTipoMuestra=dialogoMuestra.findViewById(R.id.spinnerActualizarMuestraTipoMuestra);
        spinnerActualizarMuestraTipoMaterial=dialogoMuestra.findViewById(R.id.spinnerActualizarMuestraTipoMaterial);
        editTextActualizarMuestraArgumentacion=dialogoMuestra.findViewById(R.id.editTextActualizarMuestraArgumentacion);
        editTextActualizarMuestraDestino=dialogoMuestra.findViewById(R.id.editTextActualizarMuestraDestino);
        editTextActualizarMuestraContexto=dialogoMuestra.findViewById(R.id.editTextActualizarMuestraContexto);


        buttonActualizarMuestraCancelar=dialogoMuestra.findViewById(R.id.buttonActualizarMuestraCancelar);
        buttonActualizarMuestra=dialogoMuestra.findViewById(R.id.buttonActualizarMuestra);

        listaTiposMuestras=new ArrayList<>();
        listaTiposMateriales=new ArrayList<>();

        llenarCampos();
        //consultarTiposProyectos();



        buttonActualizarMuestraCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogoMuestra.dismiss();
            }
        });

        buttonActualizarMuestra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CamposLlenos();

            }
        });
        dialogoMuestra.show();
    }

    private void llenarCampos()
    {
        textViewActualizarMuestraId.setText("Id Muestra:"+muestraEnviada.getMuestra_id()+"");

        textViewActualizarMuestraIdIntervencion.setText(muestraEnviada.getPozos_pozo_id()+"");
        editTextActualizarMuestraArgumentacion.setText(muestraEnviada.getArgumentacion()+"");
        editTextActualizarMuestraDestino.setText(muestraEnviada.getDestino()+"");
        editTextActualizarMuestraContexto.setText(muestraEnviada.getContexto()+"");

        consultarTiposMuestras();
        consultarTiposMateriales();

        switch (origen1)
        {
            case "pozo":

                tipoMuestraSeleccionada=muestraEnviada.getTipos_muestras_id();
                tipoMaterialSeleccionado=muestraEnviada.getTipos_materiales_id();
                idPozo=muestraEnviada.getPozos_pozo_id();
                textViewActualizarMuestraIdIntervencion.setText("Id Pozo: "+muestraEnviada.getPozos_pozo_id()+"");
            break;
            case "recoleccion":

            tipoMuestraSeleccionada=muestraEnviada.getTipos_muestras_id();
            tipoMaterialSeleccionado=muestraEnviada.getTipos_materiales_id();
            idPozo=muestraEnviada.getPozos_pozo_id();
            textViewActualizarMuestraIdIntervencion.setText("Id Recolección: "+muestraEnviada.getRecolecciones_superficiales_recoleccion_superficial_id()+"");
            break;
            case "perfil":

                tipoMuestraSeleccionada=muestraEnviada.getTipos_muestras_id();
                tipoMaterialSeleccionado=muestraEnviada.getTipos_materiales_id();
                idPozo=muestraEnviada.getPozos_pozo_id();
                textViewActualizarMuestraIdIntervencion.setText("Id Perfil: "+muestraEnviada.getPerfiles_expuestos_perfil_expuesto_id()+"");
                break;
        }

    }

   private void CamposLlenos(){
       if (idTipoMuestra == -1 || idTipoMaterial == -1 || editTextActualizarMuestraArgumentacion.getText().toString().trim().isEmpty() ||
               editTextActualizarMuestraDestino.getText().toString().trim().isEmpty() || editTextActualizarMuestraContexto.getText().toString().trim().isEmpty()) {
           Toast.makeText(contexto1, "Hay campos sin diligenciar", Toast.LENGTH_LONG).show();
       } else {

           webServiceActualizarMuestra();
           dialogoMuestra.dismiss();
       }

    }

    private void consultarTiposMuestras() {
        /*final ProgressDialog pDialog;
        pDialog = new ProgressDialog(contexto1);
        pDialog.setMessage("Cargando...");
        pDialog.show();*/

        final String ip = contexto1.getString(R.string.ip_url);

        String url = ip + "/web_services/tipos_muestras.php";

        System.out.println(url);

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //pDialog.hide();

                JSONArray json = response.optJSONArray("tipos_muestras");
                JSONObject jsonObject;
                List<String> listTipos = new ArrayList<String>();
                listTipos.add("Seleccione...");
                try {
                    for (int i = 0; i < json.length(); i++) {
                        jsonObject = json.getJSONObject(i);


                        tipoMuestra = new TiposMuestras(jsonObject.getInt("id"), jsonObject.getString("nombre"));

                        System.out.println(jsonObject.getString("nombre") + "  " + i + json.length());
                        listaTiposMuestras.add(tipoMuestra);
                        listTipos.add(jsonObject.getString("nombre"));

                    }
                    ArrayAdapter<CharSequence> spinnerArrayAdapter = new ArrayAdapter(contexto1, android.R.layout.simple_spinner_item, listTipos);
                    spinnerActualizarMuestraTipoMuestra.setAdapter(spinnerArrayAdapter);
                    spinnerActualizarMuestraTipoMuestra.setSelection(tipoMuestraSeleccionada);

                    spinnerActualizarMuestraTipoMuestra.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            if (position != 0) {
                                System.out.println(listaTiposMuestras.get(position - 1).getId());
                                idTipoMuestra = listaTiposMuestras.get(position - 1).getId();
                                tipoMuestraNombre=listaTiposMuestras.get(position - 1).getNombre();
                            } else {
                                idTipoMuestra = -1;
                                System.out.println(idTipoMuestra);
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

    private void consultarTiposMateriales() {
        /*final ProgressDialog pDialog;
        pDialog = new ProgressDialog(contexto1);
        pDialog.setMessage("Cargando...");
        pDialog.show();*/

        final String ip = contexto1.getString(R.string.ip_url);

        String url = ip + "/web_services/tipos_materiales.php";

        System.out.println(url);

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //pDialog.hide();

                JSONArray json = response.optJSONArray("tipos_materiales");
                JSONObject jsonObject;
                List<String> listTipos = new ArrayList<String>();
                listTipos.add("Seleccione...");
                try {
                    for (int i = 0; i < json.length(); i++) {
                        jsonObject = json.getJSONObject(i);


                        tipoMaterial = new TiposMateriales(jsonObject.getInt("id"), jsonObject.getString("nombre"));

                        System.out.println(jsonObject.getString("nombre") + "  " + i + json.length());
                        listaTiposMateriales.add(tipoMaterial);
                        listTipos.add(jsonObject.getString("nombre"));

                    }
                    ArrayAdapter<CharSequence> spinnerArrayAdapter = new ArrayAdapter(contexto1, android.R.layout.simple_spinner_item, listTipos);
                    spinnerActualizarMuestraTipoMaterial.setAdapter(spinnerArrayAdapter);
                    spinnerActualizarMuestraTipoMaterial.setSelection(tipoMaterialSeleccionado);

                    spinnerActualizarMuestraTipoMaterial.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            if (position != 0) {
                                System.out.println(listaTiposMateriales.get(position - 1).getId());
                                idTipoMaterial = listaTiposMateriales.get(position - 1).getId();
                                tipoMaterialNombre=listaTiposMateriales.get(position - 1).getNombre();
                            } else {
                                idTipoMaterial = -1;
                                System.out.println(idTipoMaterial);
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

    private void webServiceActualizarMuestra() {
        /*final ProgressDialog pDialog=new ProgressDialog(contexto1);
        pDialog.setMessage("Cargando...");
        pDialog.show();*/
        String ip=contexto1.getString(R.string.ip_url);

        String url=ip+"/web_services/editar_muestra.php?";

        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
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
                    Muestras muestraActuaizada=new Muestras(muestraEnviada.getMuestra_id(),idTipoMuestra,tipoMuestraNombre,
                            idTipoMaterial, tipoMaterialNombre,muestraEnviada.getPerfiles_expuestos_perfil_expuesto_id(),muestraEnviada.getRecolecciones_superficiales_recoleccion_superficial_id(),
                            muestraEnviada.getPozos_pozo_id(),editTextActualizarMuestraArgumentacion.getText().toString().trim(),editTextActualizarMuestraDestino.getText().toString().trim(),
                            editTextActualizarMuestraContexto.getText().toString().trim(),muestraEnviada.getIntervencion());
                      interfaz.EstructuraArqueologicaActualizado(muestraActuaizada);
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
                parametros.put("muestra_id",muestraEnviada.getMuestra_id()+"");
                parametros.put("tipos_muestras_id",idTipoMuestra+"");
                parametros.put("tipos_materiales_id",idTipoMaterial+"");
                parametros.put("argumentacion",editTextActualizarMuestraArgumentacion.getText().toString().trim());
                parametros.put("destino",editTextActualizarMuestraDestino.getText().toString().trim());
                parametros.put("contexto",editTextActualizarMuestraContexto.getText().toString().trim());
                parametros.put("intervencion",muestraEnviada.getIntervencion());


                return parametros;
            }
        };
        //request.add(stringRequest);
        VolleySingleton.getIntanciaVolley(contexto1).addToRequestQueue(stringRequest);
    }

}
