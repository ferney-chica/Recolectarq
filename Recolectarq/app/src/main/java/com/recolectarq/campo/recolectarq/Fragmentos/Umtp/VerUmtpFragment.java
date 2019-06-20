package com.recolectarq.campo.recolectarq.Fragmentos.Umtp;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.recolectarq.campo.recolectarq.Actividades.InicioUsuarioActivity;
import com.recolectarq.campo.recolectarq.Carpetas;
import com.recolectarq.campo.recolectarq.CuadrosDialogos.CuadroDialogoActualizarUmtp;
import com.recolectarq.campo.recolectarq.Fragmentos.ImagenesFragment;
import com.recolectarq.campo.recolectarq.Fragmentos.Intervenciones.IntervencionesFragment;
import com.recolectarq.campo.recolectarq.Fragmentos.Proyectos.VerProyectoFragment;
import com.recolectarq.campo.recolectarq.Modelo.Proyectos;
import com.recolectarq.campo.recolectarq.Modelo.Tipos_Proyectos;
import com.recolectarq.campo.recolectarq.Modelo.Umtp;
import com.recolectarq.campo.recolectarq.Modelo.Usuarios;
import com.recolectarq.campo.recolectarq.R;
import com.recolectarq.campo.recolectarq.VolleySingleton;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class VerUmtpFragment extends Fragment  implements CuadroDialogoActualizarUmtp.FinalizarCuadroDialogo, InicioUsuarioActivity.OnBackPressedListener  {

    TextView textViewVerUmtpIdUmtp;
    TextView textViewVerUmtpLargo;
    TextView textViewVerUmtpAncho;
    TextView textViewVerUmtpArea;
    TextView textViewVerUmtpAltura;
    TextView textViewVerUmtpUTMx;
    TextView textViewVerUmtpUTMy;
    TextView textViewVerUmtpLatitud;
    TextView textViewVerUmtpLongitud;
    TextView textViewVerUmtpMunicipio;
    TextView textViewVerUmtpDepartamento;
    TextView textViewVerUmtpVereda;
    TextView textViewVerUmtpSector;
    TextView textViewVerUmtpAccesos;
    TextView textViewVerUmtpTipoRelieve;
    TextView textViewVerUmtpGeoforma;
    TextView textViewVerUmtpVegetacion;
    TextView textViewVerUmtpDedicacionEntorno;
    TextView textViewVerUmtpZonasIncluyentes;
    TextView textViewVerUmtpNumero;
    TextView textViewVerUmtpCodigoRotulo;

    FloatingActionButton fabBotonVerUmtpIntervenciones;
    FloatingActionButton fabBotonVerUmtpEditar;
    FloatingActionButton fabBotonVerUmtpImagenes;
    ImageButton imageButtonVerUmtpEscanearCodigo;


    private int idUsuario;
    private int idProyecto;
    private int idUmtp;
    private int idPerfil;

    private Context contexto;

    private RequestQueue request;
    private JsonRequest jrq;
    private ArrayList<Tipos_Proyectos> listaTiposProyectos;
    private Spinner spinnerTipoProyecto;
    private JsonObjectRequest jsonObjectRequest;


    private Usuarios usuarioLogueado;
    private Proyectos proyectoSeleccionado;
    private Umtp umtpSeleccionada;
    private Bundle argumentosEnviados;

    Carpetas carpeta=new Carpetas();













    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments()!=null)
        {

            usuarioLogueado=(Usuarios) getArguments().getSerializable("usuario");
            proyectoSeleccionado=(Proyectos)getArguments().getSerializable("proyecto");
            umtpSeleccionada=(Umtp)getArguments().getSerializable("umtp");

            idUsuario = Integer.parseInt(usuarioLogueado.getUsuario_id());
            idProyecto=proyectoSeleccionado.getProyecto_id();
            idUmtp=umtpSeleccionada.getUmtp_id();
            idPerfil=proyectoSeleccionado.getPerfil_id();
            System.out.println("entro a EDITAR UMTP"+ "Usuario: "+ idUsuario+ "Proyecto: "+ idProyecto + "Umtp: "+idUmtp);
            carpeta.crearCarpeta("/Recolectarq/Proyectos/"+proyectoSeleccionado.getCodigo_identificacion()+"/UMTP/UMTP"+idUmtp);

        }else{
            usuarioLogueado=new Usuarios("null","null", "null","null");
            Toast.makeText(getContext(),"NO entroOOOOOOOO",Toast.LENGTH_LONG);
            Toast.makeText(getContext(),"NO entroOOOOOOOO",Toast.LENGTH_LONG);

            usuarioLogueado.setNombre("No hay usuario logueado");

        }

    }




    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
       // return super.onCreateView(inflater, container, savedInstanceState);
        ((InicioUsuarioActivity) getActivity()).setOnBackPressedListener(this);
        final View v= inflater.inflate(R.layout.fragment_ver_umtp,container, false);


        textViewVerUmtpIdUmtp=v.findViewById(R.id.textViewVerUmtpIdUmtp);
        textViewVerUmtpLargo=v.findViewById(R.id.textViewVerUmtpLargo);
        textViewVerUmtpAncho=v.findViewById(R.id.textViewVerUmtpAncho);
        textViewVerUmtpArea=v.findViewById(R.id.textViewVerUmtpArea);
        textViewVerUmtpAltura=v.findViewById(R.id.textViewVerUmtpAltura);
        textViewVerUmtpUTMx=v.findViewById(R.id.textViewVerUmtpUTMx);
        textViewVerUmtpUTMy=v.findViewById(R.id.textViewVerUmtpUTMy);
        textViewVerUmtpLatitud=v.findViewById(R.id.textViewVerUmtpLatitud);
        textViewVerUmtpLongitud=v.findViewById(R.id.textViewVerUmtpLongitud);
        textViewVerUmtpMunicipio=v.findViewById(R.id.textViewVerUmtpMunicipio);
        textViewVerUmtpDepartamento=v.findViewById(R.id.textViewVerUmtpDepartamento);
        textViewVerUmtpVereda=v.findViewById(R.id.textViewVerUmtpVereda);
        textViewVerUmtpSector=v.findViewById(R.id.textViewVerUmtpSector);
        textViewVerUmtpAccesos=v.findViewById(R.id.textViewVerUmtpAccesos);
        textViewVerUmtpTipoRelieve=v.findViewById(R.id.textViewVerUmtpTipoRelieve);
        textViewVerUmtpGeoforma=v.findViewById(R.id.textViewVerUmtpGeoforma);
        textViewVerUmtpVegetacion=v.findViewById(R.id.textViewVerUmtpVegetacion);
        textViewVerUmtpDedicacionEntorno=v.findViewById(R.id.textViewVerUmtpDedicacionEntorno);
        textViewVerUmtpZonasIncluyentes=v.findViewById(R.id.textViewVerUmtpZonasIncluyentes);
        textViewVerUmtpNumero=v.findViewById(R.id.textViewVerUmtpNumero);
        textViewVerUmtpCodigoRotulo=v.findViewById(R.id.textViewVerUmtpCodigoRotulo);

        fabBotonVerUmtpIntervenciones=v.findViewById(R.id.fabBotonVerUmtpIntervenciones);
        fabBotonVerUmtpEditar=v.findViewById(R.id.fabBotonVerUmtpEditar);
        fabBotonVerUmtpImagenes=v.findViewById(R.id.fabBotonVerUmtpImagenes);
        imageButtonVerUmtpEscanearCodigo=v.findViewById(R.id.imageButtonVerUmtpEscanearCodigo);



        System.out.println("ESTA ES:"+umtpSeleccionada.getUtmx());

        textViewVerUmtpIdUmtp.setText(textViewVerUmtpIdUmtp.getText().toString() +" "+  umtpSeleccionada.getUmtp_id()+"");
        textViewVerUmtpLargo.setText(textViewVerUmtpLargo.getText().toString() +" "+  umtpSeleccionada.getLargo()+"");
        textViewVerUmtpAncho.setText(textViewVerUmtpAncho.getText().toString() +" "+ umtpSeleccionada.getAncho()+"");
        textViewVerUmtpArea.setText(textViewVerUmtpArea.getText().toString() +" "+  umtpSeleccionada.getArea()+"");
        textViewVerUmtpAltura.setText(textViewVerUmtpAltura.getText().toString() +" "+  umtpSeleccionada.getAltura()+"");
        textViewVerUmtpUTMx.setText(textViewVerUmtpUTMx.getText().toString() +" "+  umtpSeleccionada.getUtmx()+"");
        textViewVerUmtpUTMy.setText(textViewVerUmtpUTMy.getText().toString() +" "+  umtpSeleccionada.getUtmy()+"");
        textViewVerUmtpLatitud.setText(textViewVerUmtpLatitud.getText().toString() +" "+  umtpSeleccionada.getLatitud()+"");
        textViewVerUmtpLongitud.setText(textViewVerUmtpLongitud.getText().toString() +" "+  umtpSeleccionada.getLongitud()+"");
        textViewVerUmtpMunicipio.setText(textViewVerUmtpMunicipio.getText().toString() +" "+  umtpSeleccionada.getMunicipio()+"");
        textViewVerUmtpDepartamento.setText(textViewVerUmtpDepartamento.getText().toString() +" "+  umtpSeleccionada.getDepartamento()+"");
        textViewVerUmtpVereda.setText(textViewVerUmtpVereda.getText().toString() +" "+  umtpSeleccionada.getVereda()+"");
        textViewVerUmtpSector.setText(textViewVerUmtpSector.getText().toString() +" "+  umtpSeleccionada.getSector()+"");
        textViewVerUmtpAccesos.setText(textViewVerUmtpAccesos.getText().toString() +" "+  umtpSeleccionada.getAccesos()+"");
        textViewVerUmtpTipoRelieve.setText(textViewVerUmtpTipoRelieve.getText().toString() +" "+  umtpSeleccionada.getTipos_relieves_id()+"");
        textViewVerUmtpGeoforma.setText(textViewVerUmtpGeoforma.getText().toString() +" "+  umtpSeleccionada.getGeoforma_id()+"");
        textViewVerUmtpVegetacion.setText(textViewVerUmtpVegetacion.getText().toString() +" "+  umtpSeleccionada.getVegetaciones_id()+"");
        textViewVerUmtpDedicacionEntorno.setText(textViewVerUmtpDedicacionEntorno.getText().toString() +" "+  umtpSeleccionada.getDedicaciones_entornos_id()+"");
        textViewVerUmtpZonasIncluyentes.setText(textViewVerUmtpZonasIncluyentes.getText().toString() +" "+  umtpSeleccionada.getZona_incluyente()+"");
        textViewVerUmtpNumero.setText(textViewVerUmtpNumero.getText().toString() +" "+  umtpSeleccionada.getNumero()+"");
        textViewVerUmtpCodigoRotulo.setText(textViewVerUmtpCodigoRotulo.getText().toString() +" "+  umtpSeleccionada.getCodigo_rotulo()+"");
        textViewVerUmtpCodigoRotulo.setText("Código Rótulo:" +umtpSeleccionada.getCodigo_rotulo()+"");

        configurarSegunPerfil();

        System.out.println(umtpSeleccionada.getArea()+ "DESDE VER UMTP " + textViewVerUmtpArea.getText() );
        fabBotonVerUmtpEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contexto=getContext();
                new CuadroDialogoActualizarUmtp(contexto, umtpSeleccionada, getActivity(),VerUmtpFragment.this);
            }
        });

        fabBotonVerUmtpIntervenciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                argumentosEnviados= new Bundle();
                argumentosEnviados.putSerializable("proyecto",proyectoSeleccionado);
                argumentosEnviados.putSerializable("usuario",usuarioLogueado);
                argumentosEnviados.putSerializable("umtp",umtpSeleccionada);
                IntervencionesFragment intervenciones= new IntervencionesFragment();
                intervenciones.setArguments(argumentosEnviados);
                FragmentManager manager= getFragmentManager();
                manager.beginTransaction().replace(R.id.contenidos, intervenciones).commit();
            }
        });
        
        fabBotonVerUmtpImagenes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("CLIC BOTON IMAGENES UMTP");
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
        });


        imageButtonVerUmtpEscanearCodigo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder dialogoAdvertencia = new AlertDialog.Builder(getContext());
                dialogoAdvertencia.setTitle("Advertencia");
                dialogoAdvertencia.setMessage("Va realizar un escaneo de código QR, esto podría traer pérdida de información. ¿Desea continuar?");
                dialogoAdvertencia.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        IntentIntegrator integrator = new IntentIntegrator(getActivity());
                        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                        integrator.setPrompt("Scan a barcode");
                        integrator.setCameraId(0);  // Use a specific camera of the device
                        integrator.setBeepEnabled(false);
                        integrator.setBarcodeImageEnabled(true);
                        IntentIntegrator.forSupportFragment(VerUmtpFragment.this).initiateScan();
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






            }
        });


        return v;
    }

    private void configurarSegunPerfil() {

        switch (idPerfil)
        {
            case 3:
                imageButtonVerUmtpEscanearCodigo.setEnabled(false);
                fabBotonVerUmtpEditar.setEnabled(false);
                fabBotonVerUmtpImagenes.setEnabled(false);
                break;
        }
    }


    String scanContent;
    String scanFormat;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        System.out.println("El resultado del escaneo es: "+ scanningResult);

        if (scanningResult.getContents() != null) {
            System.out.println("El resultado del escaneo es DIFERENTE de NULL: "+ scanningResult);
            scanContent = scanningResult.getContents().toString();
            scanFormat = scanningResult.getFormatName().toString();
            actualizarCodigoRotulo();
        }else
        {
            System.out.println("El resultado del escaneo es NULL: "+ scanningResult);
            Toast.makeText(getActivity(), "No se ha escaneado nada, o se ha cancelado el escaneo", Toast.LENGTH_SHORT).show();

        }




    }

    private void actualizarCodigoRotulo() {
        /*final ProgressDialog pDialog=new ProgressDialog(getContext());
        pDialog.setMessage("Cargando...");
        pDialog.show();*/
        String ip=getString(R.string.ip_url);
        String url;


            url=ip+"/web_services/editar_codigo_rotulo.php?";



        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //pDialog.hide();

                if (response.trim().equalsIgnoreCase("actualiza")){
                    // etiNombre.setText("");
                    //  txtDocumento.setText("");
                    //   etiProfesion.setText("");
                    textViewVerUmtpCodigoRotulo.setText("Código Rótulo: "+scanContent);
                    Toast.makeText(getContext(),"Se ha Actualizado con exito",Toast.LENGTH_SHORT).show();


                }else{
                    Toast.makeText(getContext(),"No se ha Actualizado ",Toast.LENGTH_SHORT).show();
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


                Map<String,String> parametros=new HashMap<>();


                    parametros.put("umtp_id",umtpSeleccionada.getUmtp_id()+"");
                    parametros.put("codigo_rotulo",scanContent);
                    parametros.put("origen","umtp");
                return parametros;
            }
        };
        //request.add(stringRequest);
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(stringRequest);
    }


    @Override
    public void UmtpActualizado(Umtp umtp) {
        textViewVerUmtpIdUmtp.setText("Id UMTP: "+  umtp.getUmtp_id()+"");
        textViewVerUmtpLargo.setText("Largo: "+  umtp.getLargo()+"");
        textViewVerUmtpAncho.setText("Ancho: "+ umtp.getAncho()+"");
        textViewVerUmtpArea.setText("Àrea: "+  umtp.getArea()+"");
        textViewVerUmtpAltura.setText("Altura "+  umtp.getAltura()+"");
        textViewVerUmtpUTMx.setText("UTMx: "+  umtp.getUtmx()+"");
        textViewVerUmtpUTMy.setText("UTMy: "+  umtp.getUtmy()+"");
        textViewVerUmtpLatitud.setText("Latitud: "+  umtp.getLatitud()+"");
        textViewVerUmtpLongitud.setText("Longitud: "+  umtp.getLongitud()+"");
        textViewVerUmtpMunicipio.setText("Municipio: "+  umtp.getMunicipio()+"");
        textViewVerUmtpDepartamento.setText("Departamento: "+  umtp.getDepartamento()+"");
        textViewVerUmtpVereda.setText("Vereda: "+  umtp.getVereda()+"");
        textViewVerUmtpSector.setText("Sector: "+  umtp.getSector()+"");
        textViewVerUmtpAccesos.setText("Accesos: "+  umtp.getAccesos()+"");
        textViewVerUmtpTipoRelieve.setText("Tipo Relieve "+  umtp.getTipos_relieves_id()+"");
        textViewVerUmtpGeoforma.setText("Geoforma: "+  umtp.getGeoforma_id()+"");
        textViewVerUmtpVegetacion.setText("Vegetación: "+  umtp.getVegetaciones_id()+"");
        textViewVerUmtpDedicacionEntorno.setText("Dedicación entorno: "+  umtp.getDedicaciones_entornos_id()+"");
        textViewVerUmtpZonasIncluyentes.setText("Zonas incluyentes: "+  umtp.getZona_incluyente()+"");
        textViewVerUmtpNumero.setText("Número: "+  umtp.getNumero()+"");
        textViewVerUmtpCodigoRotulo.setText("Código Rótulo: "+  umtp.getCodigo_rotulo()+"");
        umtpSeleccionada=umtp;
    }


    @Override
    public void doBack() {
        argumentosEnviados= new Bundle();
        argumentosEnviados.putSerializable("proyecto",proyectoSeleccionado);
        argumentosEnviados.putSerializable("usuario",usuarioLogueado);
        VerProyectoFragment verProyecto= new VerProyectoFragment();
        verProyecto.setArguments(argumentosEnviados);
        FragmentManager manager= getFragmentManager();
        manager.beginTransaction().replace(R.id.contenidos, verProyecto).commit();
    }
}
