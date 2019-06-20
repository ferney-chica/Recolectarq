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
import com.recolectarq.campo.recolectarq.Modelo.FlancosGeograficos;
import com.recolectarq.campo.recolectarq.Modelo.MaterialesEstratosPerfiles;
import com.recolectarq.campo.recolectarq.Modelo.MaterialesNiveles;
import com.recolectarq.campo.recolectarq.Modelo.MaterialesRecolecciones;
import com.recolectarq.campo.recolectarq.Modelo.Proyectos;
import com.recolectarq.campo.recolectarq.Modelo.TiposMateriales;
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


public class CuadroDialogoActualizarMaterial {

    private TextView textViewActualizarMaterialIdMaterial;
    private TextView textViewActualizarMaterialIdNivel_IdRecoleccion_IdEstratoPerfil;
    private TextView textViewActualizarMaterialFlanco;
    private TextView textViewActualizarMaterialCodigoRotulo;
    private EditText editTextActualizarMaterialCantidad;
    private EditText editTextActualizarMaterialCodigoRotulo;
    private EditText editTextActualizarMaterialObservacion;
    private EditText editTextActualizarMaterialObservacionElemento;
    private Spinner spinnerActualizarMaterialTipoMaterial;
    private Spinner spinnerActualizarMaterialElemento;
    private Spinner spinnerActualizarMaterialFlanco;


    private Button cancelar;
    private Button actualizar;
    private Context contexto1;

    private JsonObjectRequest jsonObjectRequest;
    private ArrayList<Tipos_Proyectos> listaTiposProyectos;
    private int idTipoSeleccionado;
    private  int idProyecto;
    private int idElementoDiagnostico;
    private String elementoDiagnostico;
    private int estadoElemento;
    private int idTipoMaterial;
    private int idTipoMaterialOrigen;
    private int idFlanco;
    private String nombreTipoMaterial;
    private FragmentManager manager1;
    private Proyectos proyecto=new Proyectos();
    private MaterialesNiveles materialNivelPozoEnviado;
    private MaterialesRecolecciones materialRecoleccionEnviado;
    private MaterialesEstratosPerfiles materialEstratoPerfilEnviado;
    private Usuarios usuario= new Usuarios();
    private boolean cambio=false;
    private  Bundle argumentosEnviados;
    private FinalizarCuadroDialogo interfaz;
    private Dialog dialogoMaterialNivelPozo;

    private ArrayList<TiposMateriales> listaTiposMateriales;
    private ArrayList<FlancosGeograficos> listaFlancosGeograficos;

    public interface FinalizarCuadroDialogo
    {
        void MaterialNivelPozoActualizado(MaterialesNiveles materialNivelPozo);
        void MaterialRecoleccion(MaterialesRecolecciones materialRecoleccion);
        void MaterialEstratoPerfil(MaterialesEstratosPerfiles materialEstratoPerfil);
    }


    public CuadroDialogoActualizarMaterial(final Context contexto, MaterialesNiveles materialNivelPozo, MaterialesRecolecciones materialRecoleccion, MaterialesEstratosPerfiles materialEstratoPerfil, final String origen, final FinalizarCuadroDialogo actividad) {
     interfaz=actividad;
        dialogoMaterialNivelPozo= new Dialog(contexto);
        contexto1=contexto;
        //proyecto=proyectoSeleccionado;
        materialNivelPozoEnviado=materialNivelPozo;
        materialRecoleccionEnviado=materialRecoleccion;
        materialEstratoPerfilEnviado=materialEstratoPerfil;
        //idTipo=proyectoSeleccionado.getTipo_proyecto_id();
        //idProyecto=proyectoSeleccionado.getProyecto_id();
        dialogoMaterialNivelPozo.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogoMaterialNivelPozo.setCancelable(false);
        dialogoMaterialNivelPozo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogoMaterialNivelPozo.setContentView(R.layout.cuadro_dialogo_editar_material);

        textViewActualizarMaterialIdMaterial=dialogoMaterialNivelPozo.findViewById(R.id.textViewActualizarMaterialIdMaterial);
        textViewActualizarMaterialIdNivel_IdRecoleccion_IdEstratoPerfil=dialogoMaterialNivelPozo.findViewById(R.id.textViewActualizarMaterialIdNivel_IdRecoleccion_IdEstratoPerfil);
        spinnerActualizarMaterialTipoMaterial=dialogoMaterialNivelPozo.findViewById(R.id.spinnerActualizarMaterialTipoMaterial);
        editTextActualizarMaterialCantidad=dialogoMaterialNivelPozo.findViewById(R.id.editTextActualizarMaterialCantidad);
        textViewActualizarMaterialFlanco=dialogoMaterialNivelPozo.findViewById(R.id.textViewActualizarMaterialFlanco);
        spinnerActualizarMaterialFlanco=dialogoMaterialNivelPozo.findViewById(R.id.spinnerActualizarMaterialFlanco);
        textViewActualizarMaterialCodigoRotulo=dialogoMaterialNivelPozo.findViewById(R.id.textViewActualizarMaterialCodigoRotulo);
        editTextActualizarMaterialCodigoRotulo=dialogoMaterialNivelPozo.findViewById(R.id.editTextActualizarMaterialCodigoRotulo);
        editTextActualizarMaterialObservacion=dialogoMaterialNivelPozo.findViewById(R.id.editTextActualizarMaterialObservacion);
        spinnerActualizarMaterialElemento=dialogoMaterialNivelPozo.findViewById(R.id.spinnerActualizarMaterialElemento);
        editTextActualizarMaterialObservacionElemento=dialogoMaterialNivelPozo.findViewById(R.id.editTextActualizarMaterialObservacionElemento);

        cancelar=dialogoMaterialNivelPozo.findViewById(R.id.buttonCancelarActualizarMaterial);
        actualizar=dialogoMaterialNivelPozo.findViewById(R.id.buttonActualizarMaterial);

        llenarDatos(origen);
        esElementoDiagnostico(origen);
        llenarElementoDiagnostico();
        listaTiposMateriales=new ArrayList<>();
        consultarTiposMateriales();

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogoMaterialNivelPozo.dismiss();
                //System.out.println(proyectoSeleccionado.getNombre()+"     DESDE DIALOGO EDITAR");
            }
        });

        actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(proyecto+"  EEEEEEEEEEEEE "+usuario);
                CamposLlenos(origen);

            }
        });
        dialogoMaterialNivelPozo.show();
    }

    private void esElementoDiagnostico(String origen) {
        switch (origen)
        {
            case "materialNivelPozo":

                //El switch a continuación almacena si elemento diagnostico es Sí o No para darle el elemento seleccionado al spiner de elemento diagnostico
                switch (materialNivelPozoEnviado.getElemento_diagnostico())
                {
                    case "Sí":
                        estadoElemento=1;
                        break;
                    case "No":

                        estadoElemento=2;
                        break;
                }

                break;

            case "materialesRecoleccion":

                //El switch a continuación almacena si elemento diagnostico es Sí o No para darle el elemento seleccionado al spiner de elemento diagnostico
                switch (materialRecoleccionEnviado.getElemento_diagnostico())
                {
                    case "Sí":
                        estadoElemento=1;
                        break;
                    case "No":

                        estadoElemento=2;
                        break;
                }

                break;
            case "materialEstratoPerfil":

                //El switch a continuación almacena si elemento diagnostico es Sí o No para darle el elemento seleccionado al spiner de elemento diagnostico
                switch (materialEstratoPerfilEnviado.getElemento_diagnostico())
                {
                    case "Sí":
                        estadoElemento=1;
                        break;
                    case "No":

                        estadoElemento=2;
                        break;
                }

                break;
        }

            System.out.println("Estado Elemento: "+ estadoElemento + "Origen: " +origen);
    }

    private void llenarDatos(String origen) {

        switch (origen)
        {
            case "materialNivelPozo":
                textViewActualizarMaterialIdMaterial.setText("Id Material Nivel:"+" "+materialNivelPozoEnviado.getMaterial_nivel_id());
                textViewActualizarMaterialIdNivel_IdRecoleccion_IdEstratoPerfil.setText("Id Nivel:" +materialNivelPozoEnviado.getNiveles_pozos_nivel_pozo_id());
                editTextActualizarMaterialCantidad.setText(materialNivelPozoEnviado.getCantidad()+"");
                editTextActualizarMaterialObservacion.setText(materialNivelPozoEnviado.getObservacion());
                editTextActualizarMaterialObservacionElemento.setText(materialNivelPozoEnviado.getObservacion_elemento_diagnostico());
                idTipoMaterialOrigen=materialNivelPozoEnviado.getTipos_materiales_id();

                textViewActualizarMaterialFlanco.setVisibility(View.INVISIBLE);
                spinnerActualizarMaterialFlanco.setVisibility(View.INVISIBLE);
                textViewActualizarMaterialCodigoRotulo.setVisibility(View.INVISIBLE);
                editTextActualizarMaterialCodigoRotulo.setVisibility(View.INVISIBLE);
                break;

            case "materialesRecoleccion":
                textViewActualizarMaterialIdMaterial.setText("Id Material Recolección:"+" "+materialRecoleccionEnviado.getMaterial_recoleccion_id());
                textViewActualizarMaterialIdNivel_IdRecoleccion_IdEstratoPerfil.setText("Id Recolección:" +materialRecoleccionEnviado.getRecolecciones_superficiales_recoleccion_superficial_id());
                editTextActualizarMaterialCantidad.setText(materialRecoleccionEnviado.getCantidad()+"");
                editTextActualizarMaterialObservacion.setText(materialRecoleccionEnviado.getObservacion());
                editTextActualizarMaterialObservacionElemento.setText(materialRecoleccionEnviado.getObservacion_elemento_diagnostico());
                editTextActualizarMaterialCodigoRotulo.setText(materialRecoleccionEnviado.getCodigo_rotulo());
                idTipoMaterialOrigen=materialRecoleccionEnviado.getTipos_materiales_id();

                textViewActualizarMaterialFlanco.setVisibility(View.VISIBLE);
                spinnerActualizarMaterialFlanco.setVisibility(View.VISIBLE);
                textViewActualizarMaterialCodigoRotulo.setVisibility(View.VISIBLE);
                editTextActualizarMaterialCodigoRotulo.setVisibility(View.VISIBLE);

                consultarFlancosGeograficos();
                break;

            case "materialEstratoPerfil":

                System.out.println("VOY A ACTUALIZAR MATERIAL ESTRATO PERFIL");
                textViewActualizarMaterialIdMaterial.setText("Id Material Estrato Perfil:"+" "+materialEstratoPerfilEnviado.getMaterial_estrato_perfil_id());
                textViewActualizarMaterialIdNivel_IdRecoleccion_IdEstratoPerfil.setText("Id Estrato Perfil:" +materialEstratoPerfilEnviado.getEstratos_perfiles_estrato_perfil_id());
                editTextActualizarMaterialCantidad.setText(materialEstratoPerfilEnviado.getCantidad()+"");
                editTextActualizarMaterialObservacion.setText(materialEstratoPerfilEnviado.getObservacion());
                editTextActualizarMaterialObservacionElemento.setText(materialEstratoPerfilEnviado.getObservacion_elemento_diagnostico());
                idTipoMaterialOrigen=materialEstratoPerfilEnviado.getTipos_materiales_id();

                textViewActualizarMaterialFlanco.setVisibility(View.INVISIBLE);
                spinnerActualizarMaterialFlanco.setVisibility(View.INVISIBLE);
                textViewActualizarMaterialCodigoRotulo.setVisibility(View.INVISIBLE);
                editTextActualizarMaterialCodigoRotulo.setVisibility(View.INVISIBLE);
                break;
        }
    }

    private void CamposLlenos(String origen){

        if (idElementoDiagnostico==0 || idTipoMaterial==-1 || editTextActualizarMaterialCantidad.getText().toString().trim().isEmpty()
                || editTextActualizarMaterialObservacion.getText().toString().trim().isEmpty()){

            Toast.makeText(contexto1,"Faltan campos por diligenciar",Toast.LENGTH_LONG).show();
        }else
        {
            if (idElementoDiagnostico>0 && editTextActualizarMaterialObservacionElemento.getText().toString().trim().isEmpty() ){

                Toast.makeText(contexto1,"Falta el campo Observación Elemento por diligenciar",Toast.LENGTH_LONG).show();
            } else
                {
                    switch (origen)
                    {
                        case"materialNivelPozo":
                            webServiceActualizarMaterialNivelPozo();
                            dialogoMaterialNivelPozo.dismiss();
                            break;

                        case"materialesRecoleccion":

                            if(idFlanco==-1)
                            {
                                Toast.makeText(contexto1,"Falta el campo Flanco Geográfico por diligenciar"  ,Toast.LENGTH_LONG).show();
                            }else
                            {
                                if(editTextActualizarMaterialCodigoRotulo.getText().toString().trim().isEmpty())
                                {
                                    editTextActualizarMaterialCodigoRotulo.setText("---");

                                }
                                    webServiceActualizarMaterialRecoleccion();
                                    dialogoMaterialNivelPozo.dismiss();

                            }

                            break;

                        case"materialEstratoPerfil":
                            webServiceActualizarMaterialEstratoPerfil();
                            dialogoMaterialNivelPozo.dismiss();
                            break;
                    }


                }
        }


    }

    private void llenarElementoDiagnostico()
    {

        final List<String> listElementos=new ArrayList<String>();
        listElementos.add("Seleccione...");
        listElementos.add("Sí");
        listElementos.add("No");
        ArrayAdapter<CharSequence> spinnerArrayAdapter= new ArrayAdapter(contexto1, android.R.layout.simple_spinner_item, listElementos);
        spinnerActualizarMaterialElemento.setAdapter(spinnerArrayAdapter);
        spinnerActualizarMaterialElemento.setSelection(estadoElemento);
        spinnerActualizarMaterialElemento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (position)
                {
                    case 0:
                        System.out.println(position+"--- " +listElementos.get(position));
                        idElementoDiagnostico=position;
                        editTextActualizarMaterialObservacionElemento.setEnabled(false);

                        break;
                    case 1:
                        System.out.println(position+"--- " +listElementos.get(position));
                        idElementoDiagnostico=position;
                        elementoDiagnostico="Sí";
                        editTextActualizarMaterialObservacionElemento.setEnabled(true);
                        break;
                    case 2:
                        System.out.println(position+"--- " +listElementos.get(position));
                        idElementoDiagnostico=position;
                        elementoDiagnostico="No";
                        editTextActualizarMaterialObservacionElemento.setEnabled(false);
                        editTextActualizarMaterialObservacionElemento.setText("---");
                        break;

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void consultarFlancosGeograficos() {
        /*final ProgressDialog pDialog;
        pDialog=new ProgressDialog(contexto1);
        pDialog.setMessage("Cargando...");
        pDialog.show();*/
        listaFlancosGeograficos=new ArrayList<>();

        final String ip=contexto1.getString(R.string.ip_url);

        String url=ip+"/web_services/flancos_geograficos.php";

        System.out.println(url);

        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //pDialog.hide();

                FlancosGeograficos flancoGeografico;

                JSONArray json=response.optJSONArray("flancos_geograficos");
                JSONObject jsonObject;
                List<String> listTipos=new ArrayList<String>();
                listTipos.add("Seleccione...");
                try {
                    for (int i=0;i<json.length();i++) {
                        jsonObject = json.getJSONObject(i);


                        flancoGeografico=new FlancosGeograficos(jsonObject.getInt("id"), jsonObject.getString("nombre"));

                        System.out.println(jsonObject.getString("nombre")+ "  "+ i+json.length());
                        listaFlancosGeograficos.add(flancoGeografico);
                        listTipos.add(jsonObject.getString("nombre"));

                    }
                    ArrayAdapter<CharSequence> spinnerArrayAdapter= new ArrayAdapter(contexto1, android.R.layout.simple_spinner_item, listTipos);
                    spinnerActualizarMaterialFlanco.setAdapter(spinnerArrayAdapter);
                    spinnerActualizarMaterialFlanco.setSelection(materialRecoleccionEnviado.getFlancos_geograficos_id());

                    spinnerActualizarMaterialFlanco.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            if (position!=0) {
                                System.out.println("Flanco: "+ listaFlancosGeograficos.get(position - 1).getId());
                                idFlanco=listaFlancosGeograficos.get(position - 1).getId();
                            }else
                            {
                                idFlanco=-1;
                                System.out.println("El flanco geografico desde edtar material recoleccion es: " +idFlanco);
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

    private void consultarTiposMateriales() {
        /*final ProgressDialog pDialog;
        pDialog=new ProgressDialog(contexto1);
        pDialog.setMessage("Cargando...");
        pDialog.show();*/

        final String ip;
        ip = contexto1.getString( R.string.ip_url);

        String url=ip+"/web_services/tipos_materiales.php";

        System.out.println(url);

        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //pDialog.hide();

                TiposMateriales tipoMaterial;

                JSONArray json=response.optJSONArray("tipos_materiales");
                JSONObject jsonObject;
                List<String> listTipos=new ArrayList<String>();
                listTipos.add("Seleccione...");
                try {
                    for (int i=0;i<json.length();i++) {
                        jsonObject = json.getJSONObject(i);


                        tipoMaterial=new TiposMateriales(jsonObject.getInt("id"), jsonObject.getString("nombre"));

                        System.out.println(jsonObject.getString("nombre")+ "  "+ i+json.length());
                        listaTiposMateriales.add(tipoMaterial);
                        listTipos.add(jsonObject.getString("nombre"));

                    }
                    ArrayAdapter<CharSequence> spinnerArrayAdapter= new ArrayAdapter(contexto1, android.R.layout.simple_spinner_item, listTipos);
                    spinnerActualizarMaterialTipoMaterial.setAdapter(spinnerArrayAdapter);
                    spinnerActualizarMaterialTipoMaterial.setSelection(idTipoMaterialOrigen);

                    spinnerActualizarMaterialTipoMaterial.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            if (position!=0) {
                                System.out.println("Id: "+listaTiposMateriales.get(position - 1).getId()+" TipoMaterial: "+listaTiposMateriales.get(position-1).getNombre());
                                idTipoMaterial=listaTiposMateriales.get(position - 1).getId();
                                nombreTipoMaterial=listaTiposMateriales.get(position-1).getNombre();
                            }else
                            {
                                idTipoMaterial=-1;
                                System.out.println(idTipoMaterial);
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


    private void webServiceActualizarMaterialNivelPozo() {

        /*final ProgressDialog pDialog=new ProgressDialog(contexto1);
        pDialog.setMessage("Cargando...");
        pDialog.show();*/
        String ip=contexto1.getString(R.string.ip_url);

        String url=ip+"/web_services/editar_material_nivel_pozo_sondeo.php?";

        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //pDialog.hide();

                if (response.trim().equalsIgnoreCase("actualiza")){

                    Toast.makeText(contexto1,"Se ha Actualizado con exito",Toast.LENGTH_SHORT).show();

                    materialNivelPozoEnviado.setTipos_materiales_id(idTipoMaterial);
                    materialNivelPozoEnviado.setNombre_tipo_material(nombreTipoMaterial);
                    materialNivelPozoEnviado.setElemento_diagnostico(elementoDiagnostico);
                    materialNivelPozoEnviado.setCantidad(Integer.parseInt(editTextActualizarMaterialCantidad.getText().toString().trim()));
                    materialNivelPozoEnviado.setObservacion(editTextActualizarMaterialObservacion.getText().toString().trim());
                    materialNivelPozoEnviado.setObservacion_elemento_diagnostico(editTextActualizarMaterialObservacionElemento.getText().toString().trim());
                    interfaz.MaterialNivelPozoActualizado(materialNivelPozoEnviado);
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
                parametros.put("material_nivel_id",materialNivelPozoEnviado.getMaterial_nivel_id()+"");
                parametros.put("tipos_materiales_id",idTipoMaterial+"");
                parametros.put("cantidad",editTextActualizarMaterialCantidad.getText().toString().trim());
                parametros.put("observacion",editTextActualizarMaterialObservacion.getText().toString());
                parametros.put("elemento_diagnostico",elementoDiagnostico);
                parametros.put("observacion_elemento_diagnostico",editTextActualizarMaterialObservacionElemento.getText().toString());

                return parametros;
            }
        };
        //request.add(stringRequest);
        VolleySingleton.getIntanciaVolley(contexto1).addToRequestQueue(stringRequest);
    }

    private void webServiceActualizarMaterialRecoleccion() {

        /*final ProgressDialog pDialog=new ProgressDialog(contexto1);
        pDialog.setMessage("Cargando...");
        pDialog.show();*/
        String ip=contexto1.getString(R.string.ip_url);

        String url=ip+"/web_services/editar_material_recoleccion.php?";

        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //pDialog.hide();

                if (response.trim().equalsIgnoreCase("actualiza")){

                    Toast.makeText(contexto1,"Se ha Actualizado con exito",Toast.LENGTH_SHORT).show();

                    materialRecoleccionEnviado.setFlancos_geograficos_id(idFlanco);
                    materialRecoleccionEnviado.setTipos_materiales_id(idTipoMaterial);
                    materialRecoleccionEnviado.setNombre_tipo_material(nombreTipoMaterial);
                    materialRecoleccionEnviado.setCantidad(Integer.parseInt(editTextActualizarMaterialCantidad.getText().toString().trim()));
                    materialRecoleccionEnviado.setObservacion(editTextActualizarMaterialObservacion.getText().toString().trim());
                    materialRecoleccionEnviado.setCodigo_rotulo(editTextActualizarMaterialCodigoRotulo.getText().toString().trim());
                    materialRecoleccionEnviado.setElemento_diagnostico(elementoDiagnostico);
                    materialRecoleccionEnviado.setObservacion_elemento_diagnostico(editTextActualizarMaterialObservacionElemento.getText().toString().trim());
                    interfaz.MaterialRecoleccion(materialRecoleccionEnviado);
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
                parametros.put("material_recoleccion_id",materialRecoleccionEnviado.getMaterial_recoleccion_id()+"");
                parametros.put("flancos_geograficos_id",idFlanco+"");
                parametros.put("tipos_materiales_id",idTipoMaterial+"");
                parametros.put("cantidad",editTextActualizarMaterialCantidad.getText().toString().trim());
                parametros.put("observacion",editTextActualizarMaterialObservacion.getText().toString());
                parametros.put("codigo_rotulo",editTextActualizarMaterialObservacion.getText().toString());
                parametros.put("elemento_diagnostico",elementoDiagnostico);
                parametros.put("observacion_elemento_diagnostico",editTextActualizarMaterialObservacionElemento.getText().toString());

                return parametros;
            }
        };
        //request.add(stringRequest);
        VolleySingleton.getIntanciaVolley(contexto1).addToRequestQueue(stringRequest);
    }

    private void webServiceActualizarMaterialEstratoPerfil() {

        /*final ProgressDialog pDialog=new ProgressDialog(contexto1);
        pDialog.setMessage("Cargando...");
        pDialog.show();*/
        String ip=contexto1.getString(R.string.ip_url);

        String url=ip+"/web_services/editar_material_estrato_perfil_expuesto.php?";

        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //pDialog.hide();

                if (response.trim().equalsIgnoreCase("actualiza")){

                    Toast.makeText(contexto1,"Se ha Actualizado con exito",Toast.LENGTH_SHORT).show();

                    materialEstratoPerfilEnviado.setTipos_materiales_id(idTipoMaterial);
                    materialEstratoPerfilEnviado.setTipos_materiales_nombre(nombreTipoMaterial);
                    materialEstratoPerfilEnviado.setElemento_diagnostico(elementoDiagnostico);
                    materialEstratoPerfilEnviado.setCantidad(Integer.parseInt(editTextActualizarMaterialCantidad.getText().toString().trim()));
                    materialEstratoPerfilEnviado.setObservacion(editTextActualizarMaterialObservacion.getText().toString().trim());
                    materialEstratoPerfilEnviado.setObservacion_elemento_diagnostico(editTextActualizarMaterialObservacionElemento.getText().toString().trim());
                    interfaz.MaterialEstratoPerfil(materialEstratoPerfilEnviado);
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
                parametros.put("material_estrato_perfil_id",materialEstratoPerfilEnviado.getMaterial_estrato_perfil_id()+"");
                parametros.put("tipos_materiales_id",idTipoMaterial+"");
                parametros.put("cantidad",editTextActualizarMaterialCantidad.getText().toString().trim());
                parametros.put("observacion",editTextActualizarMaterialObservacion.getText().toString());
                parametros.put("elemento_diagnostico",elementoDiagnostico);
                parametros.put("observacion_elemento_diagnostico",editTextActualizarMaterialObservacionElemento.getText().toString());

                return parametros;
            }
        };
        //request.add(stringRequest);
        VolleySingleton.getIntanciaVolley(contexto1).addToRequestQueue(stringRequest);
    }

}
