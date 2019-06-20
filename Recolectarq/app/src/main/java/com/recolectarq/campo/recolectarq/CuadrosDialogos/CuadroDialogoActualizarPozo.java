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
import com.recolectarq.campo.recolectarq.Modelo.Tipos_Proyectos;
import com.recolectarq.campo.recolectarq.Modelo.Usuarios;
import com.recolectarq.campo.recolectarq.R;
import com.recolectarq.campo.recolectarq.VolleySingleton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class CuadroDialogoActualizarPozo {

    private TextView textViewIdPozo;
    private TextView textViewIdUmtp;
    private EditText editTextCodigoRotulo;
    private EditText editTextDescripcion;
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
    private PozosSondeo pozoEnviado;
    private Usuarios usuario= new Usuarios();
    private boolean cambio=false;
    private  Bundle argumentosEnviados;
    private FinalizarCuadroDialogo interfaz;

    public interface FinalizarCuadroDialogo
    {
        void PozoActualizado(PozosSondeo pozo);
    }


    public CuadroDialogoActualizarPozo(final Context contexto, final Proyectos proyectoSeleccionado, PozosSondeo pozoSondeo,final FinalizarCuadroDialogo actividad) {
     interfaz=actividad;
        final Dialog dialogoPozo= new Dialog(contexto);
         //manager1= manager;
         contexto1=contexto;
         proyecto=proyectoSeleccionado;
         pozoEnviado=pozoSondeo;
         //usuario=usuarioLogueado;
        idTipo=proyectoSeleccionado.getTipo_proyecto_id();
        idProyecto=proyectoSeleccionado.getProyecto_id();
         System.out.println(proyecto+"  FFFFFFF "+usuario);
        dialogoPozo.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogoPozo.setCancelable(false);
        dialogoPozo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogoPozo.setContentView(R.layout.cuadro_dialogo_editar_pozo);

        textViewIdPozo=dialogoPozo.findViewById(R.id.textViewEditarUmtpLargo);
        textViewIdUmtp=dialogoPozo.findViewById(R.id.textViewEditarUmtpCodigoRotulo);
        editTextCodigoRotulo=dialogoPozo.findViewById(R.id.editTextEditarUmtpCodigoRotulo);
        editTextDescripcion=dialogoPozo.findViewById(R.id.editTextEditarUmtpLargo);
        cancelar=dialogoPozo.findViewById(R.id.buttonCancelarPozo);
        actualizar=dialogoPozo.findViewById(R.id.buttonActualizarPozo);

        textViewIdPozo.setText(textViewIdPozo.getText()+" "+pozoEnviado.getPozo_id());
        textViewIdUmtp.setText(textViewIdUmtp.getText()+" "+pozoEnviado.getUmtp_id());
        editTextCodigoRotulo.setText(pozoEnviado.getCodigo_rotulo());
        editTextDescripcion.setText(pozoEnviado.getDescripcion());
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
                dialogoPozo.dismiss();
                System.out.println(proyectoSeleccionado.getNombre()+"     DESDE DIALOGO EDITAR");
            }
        });

        actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(proyecto+"  EEEEEEEEEEEEE "+usuario);
                if(CamposLlenos()) {
                    webServiceActualizarPozo();
                    dialogoPozo.dismiss();
                }else {
                    Toast.makeText(contexto1,"Hay campos sin diligenciar",Toast.LENGTH_LONG).show();
                }

            }
        });
        dialogoPozo.show();
    }

   private boolean CamposLlenos(){
        boolean campoLleno=true;
        if(editTextCodigoRotulo.getText().toString().trim().isEmpty()|| editTextDescripcion.getText().toString().trim().isEmpty() ) {
            campoLleno=false;
        }
        return campoLleno;
    }


    private void webServiceActualizarPozo() {
        /*final ProgressDialog pDialog=new ProgressDialog(contexto1);
        pDialog.setMessage("Cargando...");
        pDialog.show();*/
        System.out.println(proyecto+"  GGGGGGGGGGG "+usuario);
        String ip=contexto1.getString(R.string.ip_url);

        String url=ip+"/web_services/editar_pozo_sondeo.php?";

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
                    pozoEnviado.setDescripcion(editTextDescripcion.getText().toString());
                    pozoEnviado.setCodigo_rotulo(editTextCodigoRotulo.getText().toString());
                      interfaz.PozoActualizado(pozoEnviado);
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
                parametros.put("pozo_id",pozoEnviado.getPozo_id()+"");
                parametros.put("descripcion",editTextDescripcion.getText().toString());
                parametros.put("codigo_rotulo",editTextCodigoRotulo.getText().toString());

                return parametros;
            }
        };
        //request.add(stringRequest);
        VolleySingleton.getIntanciaVolley(contexto1).addToRequestQueue(stringRequest);
    }

}
