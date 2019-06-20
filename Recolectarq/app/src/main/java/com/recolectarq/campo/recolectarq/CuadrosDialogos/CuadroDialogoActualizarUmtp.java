package com.recolectarq.campo.recolectarq.CuadrosDialogos;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.recolectarq.campo.recolectarq.Fragmentos.Umtp.VerUmtpFragment;
import com.recolectarq.campo.recolectarq.Modelo.DedicacionesEntornos;
import com.recolectarq.campo.recolectarq.Modelo.Geoforma;
import com.recolectarq.campo.recolectarq.Modelo.TiposRelieves;
import com.recolectarq.campo.recolectarq.Modelo.Umtp;
import com.recolectarq.campo.recolectarq.Modelo.Usuarios;
import com.recolectarq.campo.recolectarq.Modelo.Vegetaciones;
import com.recolectarq.campo.recolectarq.R;
import com.recolectarq.campo.recolectarq.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CuadroDialogoActualizarUmtp extends Activity{

    private EditText editTextEditarUmtpLargo;
    private EditText editTextEditarUmtpAncho;
    private EditText editTextEditarUmtpArea;
    private EditText editTextEditarUmtpAltura;
    private EditText editTextEditarUmtpUtmx;
    private EditText editTextEditarUmtpUtmy;
    private EditText editTextEditarUmtpGradosLatitud;
    private EditText editTextEditarUmtpMinutosLatitud;
    private EditText editTextEditarUmtpSegundosLatitud;
    private EditText editTextEditarUmtpGradosLongitud;
    private EditText editTextEditarUmtpMinutosLongitud;
    private EditText editTextEditarUmtpSegundosLongitud;
    private EditText editTextEditarUmtpMunicipio;
    private EditText editTextEditarUmtpDepartamento;
    private EditText editTextEditarUmtpVereda;
    private EditText editTextEditarUmtpSector;
    private Spinner  spinnerEditarUmtpTipoRelieve;
    private Spinner  spinnerEditarUmtpGeoforma;
    private Spinner  spinnerEditarUmtpVegetacion;
    private Spinner  spinnerEditarUmtpDedicacionEntorno;
    private EditText editTextEditarUmtpAccesos;
    private EditText editTextEditarUmtpZonaIncluyente;
    private EditText editTextEditarUmtpCodigoRotulo;
    private EditText editTextEditarUmtpNumero;
    private Button buttonEditarUmtpCancelar;
    private Button buttonEditarUmtpActualizar;
    private Context contexto1;

    private ArrayList<TiposRelieves> listaTiposRelieves;
    private ArrayList<Geoforma> listaGeoformas;
    private ArrayList<Vegetaciones> listaVegetaciones;
    private ArrayList<DedicacionesEntornos> listaDedicaciones;
    private JsonObjectRequest jsonObjectRequest;

    private int idUmtp;
    private int idTipoRelieveSeleccionado;
    private int idTipoRelieve;
    private int idGeoformaSeleccionado;
    private int idGeoforma;
    private int idVegetacionSeleccionado;
    private int idVegetacion;
    private int idDedicacionSeleccionado;
    private int idDedicacion;
    private  int idProyecto;
    private FragmentManager manager1;
    private Umtp umtp=new Umtp();
    private Usuarios usuario= new Usuarios();
    private boolean cambio=false;
    private  Bundle argumentosEnviados;
    private FinalizarCuadroDialogo interfaz;

    String gradosLong;
    String minutosLong;
    String segundosLong;

    String gradosLat;
    String minutosLat;
    String segundosLat;

    String latitud;
    String longitud;

    public interface FinalizarCuadroDialogo
    {
        void UmtpActualizado(Umtp umtp);
    }


    public CuadroDialogoActualizarUmtp(final Context contexto, final Umtp umtpSeleccionado, Activity actividadF, final FinalizarCuadroDialogo actividad) {
     interfaz=actividad;
     final Activity act=actividadF;
        final Dialog dialogoUmtp= new Dialog(contexto);
         //manager1= manager;
         contexto1=contexto;
        umtp=umtpSeleccionado;
        idProyecto=umtpSeleccionado.getProyectos_proyecto_id();
         //usuario=usuarioLogueado;
        idUmtp=umtpSeleccionado.getUmtp_id();
         System.out.println(idUmtp+"  ID DE LA UMTP A ACTUALIZAR "+usuario);
        dialogoUmtp.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogoUmtp.setCancelable(false);
        dialogoUmtp.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogoUmtp.setContentView(R.layout.cuadro_dialogo_editar_umtp);

        editTextEditarUmtpLargo=dialogoUmtp.findViewById(R.id.editTextEditarUmtpLargo);
        editTextEditarUmtpAncho=dialogoUmtp.findViewById(R.id.editTextEditarUmtpAncho);
        editTextEditarUmtpArea=dialogoUmtp.findViewById(R.id.editTextEditarUmtpArea);
        editTextEditarUmtpAltura=dialogoUmtp.findViewById(R.id.editTextEditarUmtpAltura);
        editTextEditarUmtpUtmx=dialogoUmtp.findViewById(R.id.editTextEditarUmtpUtmx);
        editTextEditarUmtpUtmy=dialogoUmtp.findViewById(R.id.editTextEditarUmtpUtmy);
        editTextEditarUmtpGradosLatitud=dialogoUmtp.findViewById(R.id.editTextEditarUmtpGradosLatitud);
        editTextEditarUmtpMinutosLatitud=dialogoUmtp.findViewById(R.id.editTextEditarUmtpMinutosLatitud);
        editTextEditarUmtpSegundosLatitud=dialogoUmtp.findViewById(R.id.editTextEditarUmtpSegundosLatitud);
        editTextEditarUmtpGradosLongitud=dialogoUmtp.findViewById(R.id.editTextEditarUmtpGradosLongitud);
        editTextEditarUmtpMinutosLongitud=dialogoUmtp.findViewById(R.id.editTextEditarUmtpMinutosLongitud);
        editTextEditarUmtpSegundosLongitud=dialogoUmtp.findViewById(R.id.editTextEditarUmtpSegundosLongitud);
        editTextEditarUmtpMunicipio=dialogoUmtp.findViewById(R.id.editTextEditarUmtpMunicipio);
        editTextEditarUmtpDepartamento=dialogoUmtp.findViewById(R.id.editTextEditarUmtpDepartamento);
        editTextEditarUmtpVereda=dialogoUmtp.findViewById(R.id.editTextEditarUmtpVereda);
        editTextEditarUmtpSector=dialogoUmtp.findViewById(R.id.editTextEditarUmtpSector);
        spinnerEditarUmtpTipoRelieve=dialogoUmtp.findViewById(R.id.spinnerEditarUmtpTipoRelieve);
        spinnerEditarUmtpGeoforma=dialogoUmtp.findViewById(R.id.spinnerEditarUmtpGeoforma);
        spinnerEditarUmtpVegetacion=dialogoUmtp.findViewById(R.id.spinnerEditarUmtpVegetacion);
        spinnerEditarUmtpDedicacionEntorno=dialogoUmtp.findViewById(R.id.spinnerEditarUmtpDedicacionEntorno);
        editTextEditarUmtpAccesos=dialogoUmtp.findViewById(R.id.editTextEditarUmtpAccesos);
        editTextEditarUmtpZonaIncluyente=dialogoUmtp.findViewById(R.id.editTextEditarUmtpZonaIncluyente);
        editTextEditarUmtpCodigoRotulo=dialogoUmtp.findViewById(R.id.editTextEditarUmtpCodigoRotulo);
        editTextEditarUmtpNumero=dialogoUmtp.findViewById(R.id.editTextEditarUmtpNumero);
        buttonEditarUmtpCancelar=dialogoUmtp.findViewById(R.id.buttonEditarUmtpCancelar);
        buttonEditarUmtpActualizar=dialogoUmtp.findViewById(R.id.buttonEditarUmtpActualizar);



        //actualizar.setEnabled(false);

        latitud = umtp.getLatitud().toString().trim();
        longitud = umtp.getLongitud().toString().trim();

        separarLatitud(latitud);
        separarLongitud(longitud);

        editTextEditarUmtpLargo.setText(umtp.getLargo()+"");
        editTextEditarUmtpAncho.setText(umtp.getAncho()+"");
        editTextEditarUmtpArea.setText(umtp.getArea()+"");
        editTextEditarUmtpAltura.setText(umtp.getAltura()+"");
        editTextEditarUmtpUtmx.setText(umtp.getUtmx()+"");
        editTextEditarUmtpUtmy.setText(umtp.getUtmy()+"");


        editTextEditarUmtpGradosLatitud.setText(gradosLat);
        editTextEditarUmtpGradosLongitud.setText(gradosLong);
        editTextEditarUmtpMinutosLatitud.setText(minutosLat);
        editTextEditarUmtpMinutosLongitud.setText(minutosLong);
        editTextEditarUmtpSegundosLatitud.setText(segundosLat);
        editTextEditarUmtpSegundosLongitud.setText(segundosLong);






        editTextEditarUmtpMunicipio.setText(umtp.getMunicipio()+"");
        editTextEditarUmtpDepartamento.setText(umtp.getDepartamento()+"");
        editTextEditarUmtpVereda.setText(umtp.getVereda()+"");
        editTextEditarUmtpSector.setText(umtp.getSector()+"");
        editTextEditarUmtpAccesos.setText(umtp.getAccesos()+"");
        editTextEditarUmtpZonaIncluyente.setText(umtp.getZona_incluyente()+"");
        editTextEditarUmtpCodigoRotulo.setText(umtp.getCodigo_rotulo());
        editTextEditarUmtpNumero.setText(String.valueOf( umtp.getNumero()));

        idTipoRelieveSeleccionado=umtpSeleccionado.getTipos_relieves_id();
        idGeoformaSeleccionado=umtpSeleccionado.getGeoforma_id();
        idVegetacionSeleccionado=umtpSeleccionado.getVegetaciones_id();
        idDedicacionSeleccionado=umtpSeleccionado.getDedicaciones_entornos_id();

        consultarTipoRelieve();
        consultarGeoforma();
        consultarVegetacion();
        consultarDedicacion();

        buttonEditarUmtpCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogoUmtp.dismiss();
                System.out.println(umtpSeleccionado.getUmtp_id()+"     DESDE DIALOGO EDITAR");
            }
        });

        buttonEditarUmtpActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(umtpSeleccionado.getUmtp_id()+"  EEEEEEEEEEEEE ");
                if(CamposLlenos()) {
                    webServiceActualizarUmtp();
                    dialogoUmtp.dismiss();
                }else {
                    Toast.makeText(contexto1,"Hay campos vacios o listas sin elementos seleccionados",Toast.LENGTH_LONG).show();
                }

            }
        });






        dialogoUmtp.show();
    }




    private void separarLatitud(String latitud)
    {
        System.out.println("LA LATITUD ES: "+latitud);
        String[] parts = latitud.split("°");
        gradosLat = parts[0]; // 123
        String part2 = parts[1]; // 654321
        parts = part2.split("'");
        minutosLat=parts[0];
        segundosLat=parts[1];
        segundosLat=segundosLat.substring(0,segundosLat.length()-1);
        System.out.println("La latitud es: "+gradosLat + "   "+minutosLat+ "   "+segundosLat);
    }

    private void separarLongitud(String longitud)
    {
        String[] parts = longitud.split("°");
        gradosLong = parts[0]; // 123
        String part2 = parts[1]; // 654321
        parts = part2.split("'");
        minutosLong=parts[0];
        segundosLong=parts[1];
        segundosLong=segundosLong.substring(0,segundosLong.length()-1);
        System.out.println("La longitud es: "+gradosLong + "   "+minutosLong+ "   "+segundosLong);
    }

   private boolean CamposLlenos(){
        boolean campoLleno=true;
       if(editTextEditarUmtpLargo.getText().toString().trim().isEmpty()|| editTextEditarUmtpAncho.getText().toString().trim().isEmpty() ||
               editTextEditarUmtpArea.getText().toString().trim().isEmpty()|| editTextEditarUmtpAltura.getText().toString().trim().isEmpty()||
               editTextEditarUmtpUtmx.getText().toString().trim().isEmpty()|| editTextEditarUmtpUtmy.getText().toString().trim().isEmpty()||
               editTextEditarUmtpGradosLatitud.getText().toString().trim().isEmpty()|| editTextEditarUmtpMinutosLatitud.getText().toString().trim().isEmpty()||
               editTextEditarUmtpSegundosLatitud.getText().toString().trim().isEmpty()|| editTextEditarUmtpGradosLongitud.getText().toString().trim().isEmpty()||
               editTextEditarUmtpMinutosLongitud.getText().toString().trim().isEmpty()|| editTextEditarUmtpSegundosLongitud.getText().toString().trim().isEmpty()||
               editTextEditarUmtpMunicipio.getText().toString().trim().isEmpty()|| editTextEditarUmtpDepartamento.getText().toString().trim().isEmpty()||
               editTextEditarUmtpAccesos.getText().toString().trim().isEmpty()|| editTextEditarUmtpZonaIncluyente.getText().toString().trim().isEmpty()||
               idTipoRelieve==-1 || idGeoforma==-1 || idVegetacion==-1 ||idDedicacion==-1    ) {
            campoLleno=false;
        }

        if (editTextEditarUmtpVereda.getText().toString().trim().isEmpty())
        {
            editTextEditarUmtpVereda.setText("---");
        }
       if (editTextEditarUmtpSector.getText().toString().trim().isEmpty())
       {
           editTextEditarUmtpSector.setText("---");
       }

        return campoLleno;
    }

    private void consultarTipoRelieve() {
        /*final ProgressDialog pDialog;
        pDialog=new ProgressDialog(contexto1);
        pDialog.setMessage("Cargando...");
        pDialog.show();*/

        listaTiposRelieves=new ArrayList<>();
        final String ip=contexto1.getString(R.string.ip_url);

        String url=ip+"/web_services/tipos_relieves.php";

        System.out.println(url);

        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //pDialog.hide();

                TiposRelieves tipoRelieve;

                JSONArray json=response.optJSONArray("tipos_relieves");
                JSONObject jsonObject;
                List<String> listTipos=new ArrayList<String>();
                listTipos.add("Seleccione...");
                try {
                    for (int i=0;i<json.length();i++) {
                        jsonObject = json.getJSONObject(i);


                        tipoRelieve=new TiposRelieves(jsonObject.getInt("id"), jsonObject.getString("nombre"));

                        System.out.println(jsonObject.getString("nombre")+ "  "+ i+json.length());
                        listaTiposRelieves.add(tipoRelieve);
                        listTipos.add(jsonObject.getString("nombre"));

                    }
                    ArrayAdapter<CharSequence> spinnerArrayAdapter= new ArrayAdapter(contexto1, android.R.layout.simple_spinner_item, listTipos);
                    spinnerEditarUmtpTipoRelieve.setAdapter(spinnerArrayAdapter);
                    spinnerEditarUmtpTipoRelieve.setSelection(idTipoRelieveSeleccionado);
                    spinnerEditarUmtpTipoRelieve.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            System.out.println("Tipo relieve es:  "+idTipoRelieveSeleccionado+"  el seleccionado: "+ position);
                            if (position!=0) {
                                System.out.println(listaTiposRelieves.get(position - 1).getId());
                                idTipoRelieve=listaTiposRelieves.get(position - 1).getId();


                            }else
                            {
                                idTipoRelieve=-1;
                                System.out.println(idTipoRelieve);
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

    private void consultarGeoforma() {
        /*final ProgressDialog pDialog;
        pDialog=new ProgressDialog(contexto1);
        pDialog.setMessage("Cargando...");
        pDialog.show();*/

        listaGeoformas=new ArrayList<>();
        final String ip=contexto1.getString(R.string.ip_url);

        String url=ip+"/web_services/geoforma.php";

        System.out.println(url);

        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //pDialog.hide();

                Geoforma geoforma;

                JSONArray json=response.optJSONArray("geoforma");
                JSONObject jsonObject;
                List<String> listTipos=new ArrayList<String>();
                listTipos.add("Seleccione...");
                try {
                    for (int i=0;i<json.length();i++) {
                        jsonObject = json.getJSONObject(i);


                        geoforma=new Geoforma(jsonObject.getInt("id"), jsonObject.getString("nombre"));

                        System.out.println(jsonObject.getString("nombre")+ "  "+ i+json.length());
                        listaGeoformas.add(geoforma);
                        listTipos.add(jsonObject.getString("nombre"));

                    }
                    ArrayAdapter<CharSequence> spinnerArrayAdapter= new ArrayAdapter(contexto1, android.R.layout.simple_spinner_item, listTipos);
                    spinnerEditarUmtpGeoforma.setAdapter(spinnerArrayAdapter);
                    spinnerEditarUmtpGeoforma.setSelection(idGeoformaSeleccionado);
                    spinnerEditarUmtpGeoforma.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            System.out.println("Tipo geoforma es:  "+idGeoformaSeleccionado+"  el seleccionado: "+ position);
                            if (position!=0) {
                                System.out.println(listaGeoformas.get(position - 1).getId());
                                idGeoforma=listaGeoformas.get(position - 1).getId();


                            }else
                            {
                                idGeoforma=-1;
                                System.out.println(idGeoforma);
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

    private void consultarVegetacion() {
        /*final ProgressDialog pDialog;
        pDialog=new ProgressDialog(contexto1);
        pDialog.setMessage("Cargando...");
        pDialog.show();*/

        listaVegetaciones=new ArrayList<>();
        final String ip=contexto1.getString(R.string.ip_url);

        String url=ip+"/web_services/vegetaciones.php";

        System.out.println(url);

        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //pDialog.hide();

                Vegetaciones vegetacion;

                JSONArray json=response.optJSONArray("vegetaciones");
                JSONObject jsonObject;
                List<String> listTipos=new ArrayList<String>();
                listTipos.add("Seleccione...");
                try {
                    for (int i=0;i<json.length();i++) {
                        jsonObject = json.getJSONObject(i);


                        vegetacion=new Vegetaciones(jsonObject.getInt("id"), jsonObject.getString("nombre"));

                        System.out.println(jsonObject.getString("nombre")+ "  "+ i+json.length());
                        listaVegetaciones.add(vegetacion);
                        listTipos.add(jsonObject.getString("nombre"));

                    }
                    ArrayAdapter<CharSequence> spinnerArrayAdapter= new ArrayAdapter(contexto1, android.R.layout.simple_spinner_item, listTipos);
                    spinnerEditarUmtpVegetacion.setAdapter(spinnerArrayAdapter);
                    spinnerEditarUmtpVegetacion.setSelection(idVegetacionSeleccionado);
                    spinnerEditarUmtpVegetacion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            System.out.println("Tipo vegetacion es:  "+idVegetacionSeleccionado+"  el seleccionado: "+ position);
                            if (position!=0) {
                                System.out.println(listaVegetaciones.get(position - 1).getId());
                                idVegetacion=listaVegetaciones.get(position - 1).getId();


                            }else
                            {
                                idVegetacion=-1;
                                System.out.println(idVegetacion);
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

    private void consultarDedicacion() {
        /*final ProgressDialog pDialog;
        pDialog=new ProgressDialog(contexto1);
        pDialog.setMessage("Cargando...");
        pDialog.show();*/

        listaDedicaciones=new ArrayList<>();
        final String ip=contexto1.getString(R.string.ip_url);

        String url=ip+"/web_services/dedicaciones.php";

        System.out.println(url);

        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //pDialog.hide();

                DedicacionesEntornos dedicacion;

                JSONArray json=response.optJSONArray("dedicaciones_entornos");
                JSONObject jsonObject;
                List<String> listTipos=new ArrayList<String>();
                listTipos.add("Seleccione...");
                try {
                    for (int i=0;i<json.length();i++) {
                        jsonObject = json.getJSONObject(i);


                        dedicacion=new DedicacionesEntornos(jsonObject.getInt("id"), jsonObject.getString("nombre"));

                        System.out.println(jsonObject.getString("nombre")+ "  "+ i+json.length());
                        listaDedicaciones.add(dedicacion);
                        listTipos.add(jsonObject.getString("nombre"));

                    }
                    ArrayAdapter<CharSequence> spinnerArrayAdapter= new ArrayAdapter(contexto1, android.R.layout.simple_spinner_item, listTipos);
                    spinnerEditarUmtpDedicacionEntorno.setAdapter(spinnerArrayAdapter);
                    spinnerEditarUmtpDedicacionEntorno.setSelection(idDedicacionSeleccionado);
                    spinnerEditarUmtpDedicacionEntorno.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            System.out.println("Tipo dedicacion es:  "+idDedicacionSeleccionado+"  el seleccionado: "+ position);
                            if (position!=0) {
                                System.out.println(listaDedicaciones.get(position - 1).getId());
                                idDedicacion=listaDedicaciones.get(position - 1).getId();


                            }else
                            {
                                idDedicacion=-1;
                                System.out.println(idDedicacion);
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

    private void webServiceActualizarUmtp() {

        System.out.println("ESTA ES LA PRUEBA: "+gradosLat+"º"+minutosLat+"'"+segundosLat+"\"");
        /*final ProgressDialog pDialog=new ProgressDialog(contexto1);
        pDialog.setMessage("Cargando...");
        pDialog.show();*/
        //System.out.println(proyecto+"  GGGGGGGGGGG "+usuario);
        String ip=contexto1.getString(R.string.ip_url);

        String url=ip+"/web_services/editar_umtp.php";


        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //pDialog.hide();
                Umtp umtp1=new Umtp();
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
                    umtp1.setUmtp_id(idUmtp);
                    umtp1.setTipos_relieves_id(idTipoRelieve);
                    umtp1.setGeoforma_id(idGeoforma);
                    umtp1.setVegetaciones_id(idVegetacion);
                    umtp1.setDedicaciones_entornos_id(idDedicacion);
                    umtp1.setProyectos_proyecto_id(idProyecto);
                    umtp1.setNumero(Integer.parseInt(editTextEditarUmtpNumero.getText().toString()));
                    umtp1.setLargo(new BigDecimal(editTextEditarUmtpLargo.getText().toString()));
                    umtp1.setAncho(new BigDecimal(editTextEditarUmtpAncho.getText().toString()));
                    umtp1.setArea(new BigDecimal(editTextEditarUmtpArea.getText().toString()));
                    umtp1.setAltura(Integer.parseInt(editTextEditarUmtpAltura.getText().toString()));
                    //umtp1.setUtmx(Float.parseFloat(editTextUtmx.getText().toString()));
                    umtp1.setUtmx(new BigDecimal( editTextEditarUmtpUtmx.getText().toString()));
                    umtp1.setUtmy(new BigDecimal(editTextEditarUmtpUtmy.getText().toString()));

                    umtp1.setLatitud(editTextEditarUmtpGradosLatitud.getText().toString().trim()+
                            "°"+editTextEditarUmtpMinutosLatitud.getText().toString().trim()+"'"+
                            editTextEditarUmtpSegundosLatitud.getText().toString().trim()+"\"");
                    umtp1.setLongitud(editTextEditarUmtpGradosLongitud.getText().toString().trim()+
                            "°"+editTextEditarUmtpMinutosLongitud.getText().toString().trim()+"'"+
                            editTextEditarUmtpSegundosLongitud.getText().toString().trim()+"\"");

                    umtp1.setMunicipio(editTextEditarUmtpMunicipio.getText().toString());
                    umtp1.setDepartamento(editTextEditarUmtpDepartamento.getText().toString());
                    umtp1.setVereda(editTextEditarUmtpVereda.getText().toString());
                    umtp1.setSector(editTextEditarUmtpSector.getText().toString());
                    umtp1.setAccesos(editTextEditarUmtpAccesos.getText().toString());
                    umtp1.setZona_incluyente(editTextEditarUmtpZonaIncluyente.getText().toString());
                    umtp1.setCodigo_rotulo(editTextEditarUmtpCodigoRotulo.getText().toString());
                    umtp1.setYacimiento(umtp.getYacimiento());
                    umtp1.setSitio_potencial(umtp.getSitio_potencial());
                    umtp1.setUsuarios_usuario_id(umtp.getUsuarios_usuario_id());



                    System.out.println(umtp1.getUmtp_id()+"  GGGGGGGGGGG "+usuario.getUsuario_id());
                      interfaz.UmtpActualizado(umtp1);
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
                parametros.put("umtp_id",String.valueOf(idUmtp));
                parametros.put("tipos_relieves_id",String.valueOf(idTipoRelieve));
                parametros.put("geoforma_id",String.valueOf( idGeoforma));
                parametros.put("vegetaciones_id",String.valueOf( idVegetacion));
                parametros.put("dedicaciones_entornos_id",String.valueOf( idDedicacion));
                parametros.put("numero",editTextEditarUmtpNumero.getText().toString());
                parametros.put("largo",editTextEditarUmtpLargo.getText().toString());
                parametros.put("ancho",editTextEditarUmtpAncho.getText().toString());
                parametros.put("area",editTextEditarUmtpArea.getText().toString());
                parametros.put("altura",editTextEditarUmtpAltura.getText().toString());
                parametros.put("utmx",editTextEditarUmtpUtmx.getText().toString());
                parametros.put("utmy",editTextEditarUmtpUtmy.getText().toString());

                parametros.put("latitud",editTextEditarUmtpGradosLatitud.getText().toString().trim()+"°"+
                        editTextEditarUmtpMinutosLatitud.getText().toString().trim()+"'"+editTextEditarUmtpSegundosLatitud.getText().toString().trim()+"\"");
                parametros.put("longitud",editTextEditarUmtpGradosLongitud.getText().toString().trim()+
                        "°"+editTextEditarUmtpMinutosLongitud.getText().toString().trim()+
                        "'"+editTextEditarUmtpSegundosLongitud.getText().toString().trim()+"\"");

                parametros.put("municipio",editTextEditarUmtpMunicipio.getText().toString());
                parametros.put("departamento",editTextEditarUmtpDepartamento.getText().toString());
                parametros.put("vereda",editTextEditarUmtpVereda.getText().toString());
                parametros.put("sector",editTextEditarUmtpSector.getText().toString());
                parametros.put("accesos",editTextEditarUmtpAccesos.getText().toString());
                parametros.put("zona_incluyente",editTextEditarUmtpZonaIncluyente.getText().toString());
                parametros.put("codigo_rotulo",editTextEditarUmtpCodigoRotulo.getText().toString());

                return parametros;
            }
        };
        //request.add(stringRequest);
        VolleySingleton.getIntanciaVolley(contexto1).addToRequestQueue(stringRequest);
    }

}
