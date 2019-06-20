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
import com.recolectarq.campo.recolectarq.Modelo.NivelesPozos;
import com.recolectarq.campo.recolectarq.Modelo.PozosSondeo;
import com.recolectarq.campo.recolectarq.Modelo.Proyectos;
import com.recolectarq.campo.recolectarq.Modelo.Tipos_Proyectos;
import com.recolectarq.campo.recolectarq.Modelo.Usuarios;
import com.recolectarq.campo.recolectarq.R;
import com.recolectarq.campo.recolectarq.VolleySingleton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class CuadroDialogoActualizarNivelPozo {

    private TextView textViewActualizarNivelIdNivel;
    private TextView textViewActualizarNivelIdPozo;
    private EditText editTextActualizarNivelNumero;
    private EditText editTextActualizarNivelProfundidad;
    private EditText editTextActualizarNivelCodigoRotulo;
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
    private NivelesPozos nivelPozoEnviado;
    private Usuarios usuario= new Usuarios();
    private boolean cambio=false;
    private  Bundle argumentosEnviados;
    private FinalizarCuadroDialogo interfaz;
    final Dialog dialogoNivelPozo;

    public interface FinalizarCuadroDialogo
    {
        void NivelPozoActualizado(NivelesPozos nivelPozo);
    }


    public CuadroDialogoActualizarNivelPozo(final Context contexto, final Proyectos proyectoSeleccionado, NivelesPozos nivelPozoSondeo, final FinalizarCuadroDialogo actividad) {
     interfaz=actividad;
     dialogoNivelPozo = new Dialog(contexto);

         //manager1= manager;
         contexto1=contexto;
         proyecto=proyectoSeleccionado;
        nivelPozoEnviado=nivelPozoSondeo;
         //usuario=usuarioLogueado;
        idTipo=proyectoSeleccionado.getTipo_proyecto_id();
        idProyecto=proyectoSeleccionado.getProyecto_id();
         System.out.println(proyecto+"  FFFFFFF "+usuario);
        //dialogoNivelPozo.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogoNivelPozo.setCancelable(false);
        dialogoNivelPozo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogoNivelPozo.setContentView(R.layout.cuadro_dialogo_editar_nivel_pozo);

        textViewActualizarNivelIdNivel=dialogoNivelPozo.findViewById(R.id.textViewActualizarNivelIdNivel);
        textViewActualizarNivelIdPozo=dialogoNivelPozo.findViewById(R.id.textViewActualizarNivelIdPozo);
        editTextActualizarNivelNumero=dialogoNivelPozo.findViewById(R.id.editTextActualizarNivelNumero);
        editTextActualizarNivelProfundidad=dialogoNivelPozo.findViewById(R.id.editTextActualizarNivelProfundidad);
        editTextActualizarNivelCodigoRotulo=dialogoNivelPozo.findViewById(R.id.editTextActualizarNivelCodigoRotulo);
        cancelar=dialogoNivelPozo.findViewById(R.id.buttonCancelarActualizarNivelPozo);
        actualizar=dialogoNivelPozo.findViewById(R.id.buttonActualizarNivelPozo);

        textViewActualizarNivelIdNivel.setText(textViewActualizarNivelIdNivel.getText()+" "+nivelPozoSondeo.getNivel_pozo_id());
        textViewActualizarNivelIdPozo.setText(textViewActualizarNivelIdPozo.getText()+" "+nivelPozoSondeo.getPozos_pozo_id());
        editTextActualizarNivelNumero.setText(nivelPozoSondeo.getNumero()+"");
        editTextActualizarNivelProfundidad.setText(nivelPozoSondeo.getProfundidad()+"");
        editTextActualizarNivelCodigoRotulo.setText(nivelPozoSondeo.getCodigo_rotulo());



        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogoNivelPozo.dismiss();
                System.out.println(proyectoSeleccionado.getNombre()+"     DESDE DIALOGO EDITAR");
            }
        });

        actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(proyecto+"  EEEEEEEEEEEEE "+usuario);
                CamposLlenos();
                dialogoNivelPozo.dismiss();
            }
        });
        dialogoNivelPozo.show();
    }

   private void CamposLlenos(){

        if(editTextActualizarNivelNumero.getText().toString().trim().isEmpty()|| editTextActualizarNivelProfundidad.getText().toString().trim().isEmpty() ) {
            Toast.makeText(contexto1,"Hay campos sin diligenciar",Toast.LENGTH_LONG).show();
        }else
            {

                if (editTextActualizarNivelCodigoRotulo.getText().toString().trim().isEmpty())
                {
                    editTextActualizarNivelCodigoRotulo.setText("---");
                }
                webServiceActualizarNivelPozo();
            }


    }


    private void webServiceActualizarNivelPozo() {

        /*final ProgressDialog pDialog=new ProgressDialog(contexto1);
        pDialog.setMessage("Cargando...");
        pDialog.show();*/
        String ip=contexto1.getString(R.string.ip_url);

        String url=ip+"/web_services/editar_nivel_pozo_sondeo.php?";

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
                    nivelPozoEnviado.setNumero(Integer.parseInt(editTextActualizarNivelNumero.getText().toString().trim()));
                    nivelPozoEnviado.setProfundidad(Integer.parseInt(editTextActualizarNivelProfundidad.getText().toString().trim()));
                    nivelPozoEnviado.setCodigo_rotulo(editTextActualizarNivelCodigoRotulo.getText().toString());
                    interfaz.NivelPozoActualizado(nivelPozoEnviado);
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
                parametros.put("nivel_pozo_id",nivelPozoEnviado.getNivel_pozo_id()+"");
                parametros.put("numero",editTextActualizarNivelNumero.getText().toString().trim());
                parametros.put("profundidad",editTextActualizarNivelProfundidad.getText().toString().trim());
                parametros.put("codigo_rotulo",editTextActualizarNivelCodigoRotulo.getText().toString());

                return parametros;
            }
        };
        //request.add(stringRequest);
        VolleySingleton.getIntanciaVolley(contexto1).addToRequestQueue(stringRequest);
    }

}
