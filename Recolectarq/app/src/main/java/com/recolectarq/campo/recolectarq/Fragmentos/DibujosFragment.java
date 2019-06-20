package com.recolectarq.campo.recolectarq.Fragmentos;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.recolectarq.campo.recolectarq.Actividades.InicioUsuarioActivity;
import com.recolectarq.campo.recolectarq.AdaptadoresRecycler.AdapterImagenes;
import com.recolectarq.campo.recolectarq.GuardarImagenesDirectorioLocal;
import com.recolectarq.campo.recolectarq.Lienzo;
import com.recolectarq.campo.recolectarq.Modelo.ImagenesUmtp;
import com.recolectarq.campo.recolectarq.Modelo.Proyectos;
import com.recolectarq.campo.recolectarq.Modelo.Umtp;
import com.recolectarq.campo.recolectarq.Modelo.Usuarios;
import com.recolectarq.campo.recolectarq.R;
import com.recolectarq.campo.recolectarq.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class DibujosFragment extends Fragment implements View.OnClickListener, InicioUsuarioActivity.OnBackPressedListener{


    private Usuarios usuarioLogueado;
    private Proyectos proyecto;
    private Proyectos proyectoSeleccionado;
    private String origen;
    private int numeroImagenes;
    private String ruta;

    private Bundle argumentosEnviados;

    private ArrayList<ImagenesUmtp> listImagenesUmtp;
    private Umtp umtpSeleccionada;
    private ImagenesUmtp imagenesUmtp = new ImagenesUmtp();
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    JsonObjectRequest jsonObjectRequest;

    private ImageButton imageButtonColorNegro;
    private ImageButton imageButtonColorGris;
    private ImageButton imageButtonColorBlanco;
    private ImageButton imageButtonColorAmarillo;
    private ImageButton imageButtonColorAzul;
    private ImageButton imageButtonColorVerde;
    private ImageButton imageButtonColorRojo;

    private ImageButton imageButtonDibujoNuevo;
    private ImageButton imageButtonDibujoTamano;
    private ImageButton imageButtonDibujoBorrador;
    private ImageButton imageButtonDibujoGuardar;

    private Lienzo lienzoDibujo;

    float pincelPequeno = 1;
    float pincelMediano = 8;
    float pincelGrande = 13;

    Bitmap bitmap;
    String nombre;

    int idUsuario;
    int idProyecto;
    int idUmtp;

    String urlEnviada;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            Toast.makeText(getContext(), "ENTRO DIBUJO ??????????????????????????????????????????", Toast.LENGTH_LONG).show();
            usuarioLogueado = (Usuarios) getArguments().getSerializable("usuario");
            proyectoSeleccionado = (Proyectos) getArguments().getSerializable("proyecto");
            umtpSeleccionada = (Umtp) getArguments().getSerializable("umtp");
            origen = getArguments().getString("origen");
            numeroImagenes=getArguments().getInt("numeroImagenes");
            ruta=getArguments().getString("ruta");
            nombre="croquisUmtp"+umtpSeleccionada.getUmtp_id()+"_"+numeroImagenes;
            System.out.println("LA RUTA QUE ESTA LLEGANDO ES: " +ruta);
            idUsuario = Integer.parseInt(usuarioLogueado.getUsuario_id());
            idProyecto = proyectoSeleccionado.getProyecto_id();
            idUmtp = umtpSeleccionada.getUmtp_id();
            System.out.println("LA UMTP SELECCIONADA DESDE IMAGENES ES :" + idUmtp);
        } else {
            usuarioLogueado = new Usuarios("null", "null", "null", "null");
            Toast.makeText(getContext(), "NO entroOOOOOOOO", Toast.LENGTH_LONG).show();
            usuarioLogueado.setNombre("No hay usuario logueado");

        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // return super.onCreateView(inflater, container, savedInstanceState);
        ((InicioUsuarioActivity) getActivity()).setOnBackPressedListener(this);
        final View v = inflater.inflate(R.layout.fragment_dibujo, container, false);

        imageButtonColorNegro = v.findViewById(R.id.imageButtonColorNegro);
        imageButtonColorGris = v.findViewById(R.id.imageButtonColorGris);
        imageButtonColorBlanco = v.findViewById(R.id.imageButtonColorBlanco);
        imageButtonColorAmarillo = v.findViewById(R.id.imageButtonColorAmarillo);
        imageButtonColorAzul = v.findViewById(R.id.imageButtonColorAzul);
        imageButtonColorRojo = v.findViewById(R.id.imageButtonColorRojo);
        imageButtonColorVerde = v.findViewById(R.id.imageButtonColorVerde);

        imageButtonDibujoNuevo = v.findViewById(R.id.imageButtonDibujoNuevo);
        imageButtonDibujoTamano = v.findViewById(R.id.imageButtonDibujoTamano);
        imageButtonDibujoBorrador = v.findViewById(R.id.imageButtonDibujoBorrador);
        imageButtonDibujoGuardar = v.findViewById(R.id.imageButtonDibujoGuardar);






        lienzoDibujo = v.findViewById(R.id.lienzoDibujo);

        imageButtonColorNegro.setOnClickListener(this);
        imageButtonColorGris.setOnClickListener(this);
        imageButtonColorBlanco.setOnClickListener(this);
        imageButtonColorAmarillo.setOnClickListener(this);
        imageButtonColorAzul.setOnClickListener(this);
        imageButtonColorRojo.setOnClickListener(this);
        imageButtonColorVerde.setOnClickListener(this);

        imageButtonDibujoNuevo.setOnClickListener(this);
        imageButtonDibujoTamano.setOnClickListener(this);
        imageButtonDibujoBorrador.setOnClickListener(this);
        imageButtonDibujoGuardar.setOnClickListener(this);



        return v;
    }











    String color;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageButtonColorNegro:
                color = v.getTag().toString();
                lienzoDibujo.configurarColor(color);
                break;
            case R.id.imageButtonColorGris:
                color = v.getTag().toString();
                lienzoDibujo.configurarColor(color);
                break;
            case R.id.imageButtonColorBlanco:
                color = v.getTag().toString();
                lienzoDibujo.configurarColor(color);
                break;
            case R.id.imageButtonColorAmarillo:
                color = v.getTag().toString();
                lienzoDibujo.configurarColor(color);
                break;
            case R.id.imageButtonColorAzul:
                color = v.getTag().toString();
                lienzoDibujo.configurarColor(color);
                break;
            case R.id.imageButtonColorRojo:
                color = v.getTag().toString();
                lienzoDibujo.configurarColor(color);
                break;
            case R.id.imageButtonColorVerde:
                color = v.getTag().toString();
                lienzoDibujo.configurarColor(color);
                break;
            case R.id.imageButtonDibujoNuevo:

                final AlertDialog.Builder dialogoAdvertencia = new AlertDialog.Builder(getContext());
                dialogoAdvertencia.setTitle("Advertencia");
                dialogoAdvertencia.setMessage("Crear un nuevo dibujo hará que el dibujo actual se borre. ¡Desea continuar?");
                dialogoAdvertencia.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        lienzoDibujo.nuevoDibujo();
                        dialog.dismiss();
                    }
                });

                dialogoAdvertencia.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                dialogoAdvertencia.show();
                break;
            case R.id.imageButtonDibujoTamano:
                final Dialog tamanoPincel = new Dialog(getContext());
                tamanoPincel.setTitle("Tamaño del pincel");
                tamanoPincel.setContentView(R.layout.dialogo_tamano_pincel);


                TextView textViewTamanoPequeno = tamanoPincel.findViewById(R.id.textViewTamanoPequeno);
                textViewTamanoPequeno.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        lienzoDibujo.configurarBorrador(false);
                        lienzoDibujo.configurarTamanoPincel(pincelPequeno);
                        tamanoPincel.dismiss();
                    }
                });

                TextView textViewTamanoMediano = tamanoPincel.findViewById(R.id.textViewTamanoMediano);
                textViewTamanoMediano.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        lienzoDibujo.configurarBorrador(false);
                        lienzoDibujo.configurarTamanoPincel(pincelMediano);
                        tamanoPincel.dismiss();
                    }
                });

                TextView textViewTamanoGrande = tamanoPincel.findViewById(R.id.textViewTamanoGrande);
                textViewTamanoGrande.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        lienzoDibujo.configurarBorrador(false);
                        lienzoDibujo.configurarTamanoPincel(pincelGrande);
                        tamanoPincel.dismiss();
                    }
                });

                tamanoPincel.show();
                break;
            case R.id.imageButtonDibujoBorrador:
                final Dialog tamanoPincelBorrador = new Dialog(getContext());
                tamanoPincelBorrador.setTitle("Tamaño del borrador");
                tamanoPincelBorrador.setContentView(R.layout.dialogo_tamano_pincel);


                TextView textViewTamanoBorradorPequeno = tamanoPincelBorrador.findViewById(R.id.textViewTamanoPequeno);
                textViewTamanoBorradorPequeno.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        lienzoDibujo.configurarBorrador(true);
                        lienzoDibujo.configurarTamanoPincel(pincelPequeno);
                        tamanoPincelBorrador.dismiss();
                    }
                });

                TextView textViewTamanoBorradorMediano = tamanoPincelBorrador.findViewById(R.id.textViewTamanoMediano);
                textViewTamanoBorradorMediano.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        lienzoDibujo.configurarBorrador(true);
                        lienzoDibujo.configurarTamanoPincel(pincelMediano);
                        tamanoPincelBorrador.dismiss();
                    }
                });

                TextView textViewTamanoBorradorGrande = tamanoPincelBorrador.findViewById(R.id.textViewTamanoGrande);
                textViewTamanoBorradorGrande.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        lienzoDibujo.configurarBorrador(true);
                        lienzoDibujo.configurarTamanoPincel(pincelGrande);
                        tamanoPincelBorrador.dismiss();
                    }
                });

                tamanoPincelBorrador.show();
                break;
            case R.id.imageButtonDibujoGuardar:

                final AlertDialog.Builder dialogoGuardar = new AlertDialog.Builder(getContext());
                dialogoGuardar.setTitle("Guardar Dibujo");
                dialogoGuardar.setMessage("El dibujo se guardar en la siguiente ruta de carpetas: . ¿Desea continuar?");
                dialogoGuardar.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //acá la lógica para guardar

                        lienzoDibujo.setDrawingCacheEnabled(true);
                        bitmap=Bitmap.createBitmap(lienzoDibujo.getDrawingCache());

                        webServiceInsertarCroquisUMTP();
                        dialog.dismiss();
                    }
                });

                dialogoGuardar.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                dialogoGuardar.show();

                break;
        }
    }




    private void webServiceInsertarCroquisUMTP() {

        /*final ProgressDialog pDialog=new ProgressDialog(getContext());
        pDialog.setMessage("Cargando...");
        pDialog.show();*/

        String ip=getString(R.string.ip_url);

        String url=ip+"/web_services/insertar_imagen_umtp.php?";

        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //pDialog.hide();

                if (response.trim().equalsIgnoreCase("inserto")){
                    // etiNombre.setText("");
                    //  txtDocumento.setText("");
                    //   etiProfesion.setText("");
                    Toast.makeText(getContext(),"Se ha insertado con exito",Toast.LENGTH_SHORT).show();
                    GuardarImagenesDirectorioLocal guardar= new GuardarImagenesDirectorioLocal();
                    guardar.SaveImage(getContext(),bitmap,ruta,nombre);
                    argumentosEnviados= new Bundle();
                    argumentosEnviados.putSerializable("proyecto",proyectoSeleccionado);
                    argumentosEnviados.putSerializable("usuario",usuarioLogueado);
                    argumentosEnviados.putSerializable("umtp",umtpSeleccionada);
                    argumentosEnviados.putString("origen","verUmtp");
                    ImagenesFragment imagenesUmtp= new ImagenesFragment();
                    FragmentManager manager= getFragmentManager();
                    imagenesUmtp.setArguments(argumentosEnviados);
                    manager.beginTransaction().replace(R.id.contenidos, imagenesUmtp).commit();

                }else{
                    Toast.makeText(getContext(),"No se ha insertado el proyecto ",Toast.LENGTH_SHORT).show();
                    Log.i("RESPUESTA: ",""+response);
                    System.out.println(response);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),"No se ha podido conectar",Toast.LENGTH_SHORT).show();
                //pDialog.hide();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //String cedulaU=cedula.getText().toString();
                //String nombreU=nombre.getText().toString();
                //String apellidoU=apellido.getText().toString();

                String imagen=convertirImgString(bitmap);
                Map<String,String> parametros=new HashMap<>();
                parametros.put("umtp_id",idUmtp+"" );
                parametros.put("tipo","C");
                parametros.put("nombre",nombre+".jpg");
                parametros.put("imagen",imagen);
                parametros.put("proyecto",proyectoSeleccionado.getCodigo_identificacion());

                //parametros.put("imagenR",imagenR);

                return parametros;
            }
        };
        //request.add(stringRequest);
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(stringRequest);
    }

    private String convertirImgString(Bitmap bitmap) {

        ByteArrayOutputStream array=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,array);
        byte[] imageByte=array.toByteArray();
        String imageString=Base64.encodeToString(imageByte,Base64.DEFAULT);


        return imageString;
    }

    @Override
    public void doBack() {
        argumentosEnviados= new Bundle();
        argumentosEnviados.putSerializable("proyecto",proyectoSeleccionado);
        argumentosEnviados.putSerializable("usuario",usuarioLogueado);
        argumentosEnviados.putSerializable("umtp",umtpSeleccionada);
        argumentosEnviados.putString("origen","verUmtp");
        ImagenesFragment imagenesUmtp= new ImagenesFragment();
        imagenesUmtp.setArguments(argumentosEnviados);
        FragmentManager manager= getFragmentManager();
        manager.beginTransaction().replace(R.id.contenidos, imagenesUmtp).commit();
    }
}
