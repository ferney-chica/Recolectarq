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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.recolectarq.campo.recolectarq.Modelo.PozosSondeo;
import com.recolectarq.campo.recolectarq.Modelo.Proyectos;
import com.recolectarq.campo.recolectarq.Modelo.RecoleccionesSuperficiales;
import com.recolectarq.campo.recolectarq.Modelo.Tipos_Proyectos;
import com.recolectarq.campo.recolectarq.Modelo.Usuarios;
import com.recolectarq.campo.recolectarq.R;
import com.recolectarq.campo.recolectarq.VolleySingleton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class CuadroDialogoActualizarRecoleccion {

    private TextView textViewActualizarRecoleccionId;
    private TextView textViewActualizarRecoleccionUmtp;
    private EditText editTextActualizarRecoleccionCodigoRotulo;
    private EditText editTextActualizarRecoleccionDescripcion;
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
    private RecoleccionesSuperficiales recoleccionEnviado;
    private Usuarios usuario= new Usuarios();
    private boolean cambio=false;
    private  Bundle argumentosEnviados;
    private FinalizarCuadroDialogo interfaz;

    public interface FinalizarCuadroDialogo
    {
        void RecoleccionActualizado(RecoleccionesSuperficiales recoleccion);
    }


    public CuadroDialogoActualizarRecoleccion(final Context contexto, final Proyectos proyectoSeleccionado, RecoleccionesSuperficiales recoleccion, final FinalizarCuadroDialogo actividad) {
     interfaz=actividad;
        final Dialog dialogoRecoleccion= new Dialog(contexto);
         //manager1= manager;
         contexto1=contexto;
         proyecto=proyectoSeleccionado;
         recoleccionEnviado=recoleccion;
         //usuario=usuarioLogueado;
        //idTipo=proyectoSeleccionado.getTipo_proyecto_id();
        //idProyecto=proyectoSeleccionado.getProyecto_id();
         //System.out.println(proyecto+"  FFFFFFF "+usuario);
        dialogoRecoleccion.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogoRecoleccion.setCancelable(false);
        dialogoRecoleccion.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogoRecoleccion.setContentView(R.layout.cuadro_dialogo_editar_recoleccion);

        textViewActualizarRecoleccionId=dialogoRecoleccion.findViewById(R.id.textViewActualizarRecoleccionId);
        textViewActualizarRecoleccionUmtp=dialogoRecoleccion.findViewById(R.id.textViewActualizarRecoleccionUmtp);
        editTextActualizarRecoleccionCodigoRotulo=dialogoRecoleccion.findViewById(R.id.editTextActualizarRecoleccionCodigoRotulo);
        editTextActualizarRecoleccionDescripcion=dialogoRecoleccion.findViewById(R.id.editTextActualizarRecoleccionDescripcion);
        cancelar=dialogoRecoleccion.findViewById(R.id.buttonCancelarActualizarRecoleccion);
        actualizar=dialogoRecoleccion.findViewById(R.id.buttonActualizarRecoleccion);

        textViewActualizarRecoleccionId.setText(textViewActualizarRecoleccionId.getText()+" "+recoleccionEnviado.getrecoleccion_superficial_id());
        textViewActualizarRecoleccionUmtp.setText(textViewActualizarRecoleccionUmtp.getText()+" "+recoleccionEnviado.getUmtp_id());
        editTextActualizarRecoleccionCodigoRotulo.setText(recoleccionEnviado.getCodigo_rotulo());
        editTextActualizarRecoleccionDescripcion.setText(recoleccionEnviado.getDescripcion());
        //consultarTiposProyectos();

        /*nombreProyecto.addTextChangedListener(new TextWatcher() {
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
        });*/


        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogoRecoleccion.dismiss();
                System.out.println(proyectoSeleccionado.getNombre()+"     DESDE DIALOGO EDITAR RECOLECCION");
            }
        });

        actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(proyecto+"  EEEEEEEEEEEEE "+usuario);
                if(CamposLlenos()) {
                    webServiceActualizarRecoleccion();
                    dialogoRecoleccion.dismiss();
                }else {
                    Toast.makeText(contexto1,"Hay campos sin diligenciar",Toast.LENGTH_LONG).show();
                }

            }
        });
        dialogoRecoleccion.show();
    }

   private boolean CamposLlenos(){
        boolean campoLleno=true;
        if(editTextActualizarRecoleccionCodigoRotulo.getText().toString().trim().isEmpty()|| editTextActualizarRecoleccionDescripcion.getText().toString().trim().isEmpty() ) {
            campoLleno=false;
        }
        return campoLleno;
    }


    private void webServiceActualizarRecoleccion() {
        /*final ProgressDialog pDialog=new ProgressDialog(contexto1);
        pDialog.setMessage("Cargando...");
        pDialog.show();*/
        System.out.println(proyecto+"  GGGGGGGGGGG "+usuario);
        String ip=contexto1.getString(R.string.ip_url);

        String url=ip+"/web_services/editar_recoleccion.php?";

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
                    recoleccionEnviado.setDescripcion(editTextActualizarRecoleccionDescripcion.getText().toString());
                    recoleccionEnviado.setCodigo_rotulo(editTextActualizarRecoleccionCodigoRotulo.getText().toString());
                      interfaz.RecoleccionActualizado(recoleccionEnviado);
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
                parametros.put("recoleccion_superficial_id",recoleccionEnviado.getrecoleccion_superficial_id()+"");
                parametros.put("descripcion",editTextActualizarRecoleccionDescripcion.getText().toString());
                parametros.put("codigo_rotulo",editTextActualizarRecoleccionCodigoRotulo.getText().toString());

                return parametros;
            }
        };
        //request.add(stringRequest);
        VolleySingleton.getIntanciaVolley(contexto1).addToRequestQueue(stringRequest);
    }

}
