package com.recolectarq.campo.recolectarq.CuadrosDialogos;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.recolectarq.campo.recolectarq.Modelo.Proyectos;
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


public class CuadroDialogoActualizarProyecto {

    private EditText nombreProyecto;
    private Spinner  spinnerTipo;
    private EditText ubicacion;
    private EditText referencias;
    private EditText aval;
    private Button cancelar;
    private Button actualizar;
    private Context contexto1;

    private JsonObjectRequest jsonObjectRequest;
    private ArrayList<Tipos_Proyectos> listaTiposProyectos;
    private int idTipo;
    private int idTipoSeleccionado;
    private  int idProyecto;
    private FragmentManager manager1;
    private Proyectos proyecto=new Proyectos();
    private Usuarios usuario= new Usuarios();
    private boolean cambio=false;
    private  Bundle argumentosEnviados;
    private FinalizarCuadroDialogo interfaz;
    Dialog dialogoPryecto;
    public interface FinalizarCuadroDialogo
    {
        void ProyectoActualizado(Proyectos proyecto);
    }


    public CuadroDialogoActualizarProyecto(final Context contexto, final Proyectos proyectoSeleccionado, final FinalizarCuadroDialogo actividad) {
     interfaz=actividad;
        dialogoPryecto=new Dialog(contexto);
         //manager1= manager;
         contexto1=contexto;
         proyecto=proyectoSeleccionado;
         //usuario=usuarioLogueado;
        idTipo=proyectoSeleccionado.getTipo_proyecto_id();
         System.out.println(proyecto+"  FFFFFFF "+usuario);
        dialogoPryecto.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogoPryecto.setCancelable(false);
        dialogoPryecto.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogoPryecto.setContentView(R.layout.cuadro_dialogo_editar_proyecto);
        idProyecto=proyectoSeleccionado.getProyecto_id();
        idTipoSeleccionado=proyectoSeleccionado.getTipo_proyecto_id();
        nombreProyecto= dialogoPryecto.findViewById(R.id.editTextEditarUmtpLargo);
        spinnerTipo=dialogoPryecto.findViewById(R.id.spinnerTipo);
        ubicacion=dialogoPryecto.findViewById(R.id.editTextUbicacion);
        referencias= dialogoPryecto.findViewById(R.id.editTextEditarUmtpAltura);
        aval=dialogoPryecto.findViewById(R.id.editTextAval);
        cancelar=dialogoPryecto.findViewById(R.id.buttonCancelarPozo);
        actualizar=dialogoPryecto.findViewById(R.id.buttonActualizarProyecto);
        actualizar.setEnabled(false);
        nombreProyecto.setText(proyectoSeleccionado.getNombre());

        ubicacion.setText(proyectoSeleccionado.getUbicacion());

        referencias.setText(proyectoSeleccionado.getReferencias_administrativas());
                aval.setText(proyectoSeleccionado.getAval_cientifico());
        consultarTiposProyectos();

        nombreProyecto.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                actualizar.setEnabled(true);
                cambio=true;
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        ubicacion.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                actualizar.setEnabled(true);
                cambio=true;
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        aval.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                actualizar.setEnabled(true);
                cambio=true;
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        referencias.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                actualizar.setEnabled(true);
                cambio=true;
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogoPryecto.dismiss();
                System.out.println(proyectoSeleccionado.getNombre()+"     DESDE DIALOGO EDITAR");
            }
        });

        actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(proyecto+"  EEEEEEEEEEEEE "+usuario);
                if(CamposLlenos()) {
                    webServiceActualizarProyecto();
                    dialogoPryecto.dismiss();
                }else {
                    Toast.makeText(contexto1,"Hay campos vacios o listas sin elementos seleccionados",Toast.LENGTH_LONG).show();
                }

            }
        });
        dialogoPryecto.show();
    }

   private boolean CamposLlenos(){
        boolean campoLleno=true;
        if(nombreProyecto.getText().toString().isEmpty()|| idTipo==-1 || ubicacion.getText().toString().isEmpty()
       || referencias.getText().toString().isEmpty() ||aval.getText().toString().isEmpty()   ) {
            campoLleno=false;
        }
        return campoLleno;
    }

    private void consultarTiposProyectos() {
        /*final ProgressDialog pDialog;
        pDialog=new ProgressDialog(contexto1);
        pDialog.setMessage("Cargando...");
        pDialog.show();*/

        listaTiposProyectos=new ArrayList<>();
        final String ip=contexto1.getString(R.string.ip_url);

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
                    ArrayAdapter<CharSequence> spinnerArrayAdapter= new ArrayAdapter(contexto1, android.R.layout.simple_spinner_item, listTipos);
                    spinnerTipo.setAdapter(spinnerArrayAdapter);
                    spinnerTipo.setSelection(idTipoSeleccionado);
                    spinnerTipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            System.out.println("del proyecto:  "+idTipoSeleccionado+"  el seleccionado: "+ position);
                            if (position!=0) {
                                System.out.println(listaTiposProyectos.get(position - 1).getTipo_proyecto_id());
                                idTipo=listaTiposProyectos.get(position - 1).getTipo_proyecto_id();

                                if (position!=idTipoSeleccionado || cambio){
                                    actualizar.setEnabled(true);
                                }else{

                                    actualizar.setEnabled(false);
                                }
                            }else
                            {
                                idTipo=-1;
                                if(cambio)
                                {
                                    actualizar.setEnabled(true);
                                }else{
                                    actualizar.setEnabled(false);
                                }


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
                Toast.makeText(contexto1, "No se puede conectar "+error.toString(), Toast.LENGTH_LONG).show();
                System.out.println();
                //pDialog.hide();
                Log.d("ERROR: ", error.toString());
            }
        });

        // request.add(jsonObjectRequest);
        VolleySingleton.getIntanciaVolley(contexto1).addToRequestQueue(jsonObjectRequest);

    }


    private void webServiceActualizarProyecto() {
        /*final ProgressDialog pDialog=new ProgressDialog(contexto1);
        pDialog.setMessage("Cargando...");
        pDialog.show();*/
        System.out.println(proyecto+"  GGGGGGGGGGG "+usuario);
        String ip=contexto1.getString(R.string.ip_url);

        String url=ip+"/web_services/editar_proyecto.php?";

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
                    proyecto.setNombre(nombreProyecto.getText().toString());
                    proyecto.setTipo_proyecto_id(idTipo);
                    proyecto.setUbicacion(ubicacion.getText().toString());
                    proyecto.setReferencias_administrativas(referencias.getText().toString());
                    proyecto.setAval_cientifico(aval.getText().toString());
                      interfaz.ProyectoActualizado(proyecto);
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
                parametros.put("proyectoId",String.valueOf(idProyecto));
                parametros.put("nombre",nombreProyecto.getText().toString());
                parametros.put("tipo",String.valueOf( idTipo));
                parametros.put("ubicacion",ubicacion.getText().toString());
                parametros.put("referencias",referencias.getText().toString());
                parametros.put("aval",aval.getText().toString());

                return parametros;
            }
        };
        //request.add(stringRequest);
        VolleySingleton.getIntanciaVolley(contexto1).addToRequestQueue(stringRequest);
    }

}
