package com.recolectarq.campo.recolectarq.Fragmentos;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
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
import com.recolectarq.campo.recolectarq.Fragmentos.Intervenciones.IntervencionesFragment;
import com.recolectarq.campo.recolectarq.Fragmentos.Intervenciones.PerfilesExpuestos.VerPerfilExpuestoFragment;
import com.recolectarq.campo.recolectarq.Fragmentos.Intervenciones.PozosSondeo.VerPozoSondeoFragment;
import com.recolectarq.campo.recolectarq.Fragmentos.Intervenciones.RecoleccionesSuperficiales.VerRecoleccionSuperficialFragment;
import com.recolectarq.campo.recolectarq.Fragmentos.Proyectos.VerProyectoFragment;
import com.recolectarq.campo.recolectarq.Fragmentos.Umtp.VerUmtpFragment;
import com.recolectarq.campo.recolectarq.Modelo.EstratosPerfiles;
import com.recolectarq.campo.recolectarq.Modelo.EstructurasArqueologicas;
import com.recolectarq.campo.recolectarq.Modelo.Imagenes;
import com.recolectarq.campo.recolectarq.Modelo.ImagenesPozos;
import com.recolectarq.campo.recolectarq.Modelo.ImagenesUmtp;
import com.recolectarq.campo.recolectarq.Modelo.MaterialesRecolecciones;
import com.recolectarq.campo.recolectarq.Modelo.NivelesPozos;
import com.recolectarq.campo.recolectarq.Modelo.PerfilesExpuestos;
import com.recolectarq.campo.recolectarq.Modelo.PozosSondeo;
import com.recolectarq.campo.recolectarq.Modelo.Proyectos;
import com.recolectarq.campo.recolectarq.Modelo.RecoleccionesSuperficiales;
import com.recolectarq.campo.recolectarq.Modelo.Umtp;
import com.recolectarq.campo.recolectarq.Modelo.Usuarios;
import com.recolectarq.campo.recolectarq.R;
import com.recolectarq.campo.recolectarq.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.support.v4.provider.FontsContractCompat.FontRequestCallback.RESULT_OK;


public class ImagenesFragment extends Fragment  implements InicioUsuarioActivity.OnBackPressedListener{


    private Usuarios usuarioLogueado;
    private Proyectos proyecto;
    private Proyectos proyectoSeleccionado;
    private String origen;
    private Bundle argumentosEnviados;

    private ArrayList<Imagenes> listImagenes;
    private Umtp umtpSeleccionada;
    private PozosSondeo pozoSeleccionado;
    private RecoleccionesSuperficiales recoleccionSeleccionado;
    private PerfilesExpuestos perfilExpuestoSeleccionado;
    private EstratosPerfiles estratoPerfilSeleccionado;
    private EstructurasArqueologicas estructuraSeleccionada;
    private NivelesPozos nivelSeleccionado;
    private MaterialesRecolecciones materialRecoleccionSeleccionado;
    private Imagenes imagenes=new Imagenes();

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    JsonObjectRequest jsonObjectRequest;
    private TextView textViewImagenesTitulo;
    private ImageView imageViewImagenUmtp;
    FloatingActionButton fabBotonAdicionarImagen;
    FloatingActionButton fabBotonAdicionarCroquis;
    int idUsuario;
    int idProyecto;
    int idUmtp;
    int idPozo;
    int idRecoleccion;
    int idPerfil;
    int idEstratoPerfil;
    int idMaterialRecoleccion;
    int idNivelPozo;
    int idEstructura;
    int numeroImagenes=0;

    String urlEnviada;
    String url;
    String urlPozo;
    String urlRecoleccion;
    String urlPerfil;
    String urlEstratoPerfil;
    String urlEstructura;


    Dialog dialogo;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments()!=null)
        {
            Toast.makeText(getContext(),"entro",Toast.LENGTH_LONG).show();
            Toast.makeText(getContext(),"entro",Toast.LENGTH_LONG);
            usuarioLogueado=(Usuarios) getArguments().getSerializable("usuario");
            proyectoSeleccionado=(Proyectos)getArguments().getSerializable("proyecto");
            umtpSeleccionada=(Umtp) getArguments().getSerializable("umtp");
            origen=getArguments().getString("origen");
            System.out.println("EL ORIGEN DESDE POZO ES++++++++++++: "+origen);

            configurarObjetosSegunOrigen(origen);
            System.out.println(proyectoSeleccionado.getNombre());
            idUsuario = Integer.parseInt(usuarioLogueado.getUsuario_id());
            idProyecto=proyectoSeleccionado.getProyecto_id();
            idUmtp=umtpSeleccionada.getUmtp_id();
            System.out.println("LA UMTP SELECCIONADA DESDE IMAGENES ES :" +idUmtp);
        }else{
            usuarioLogueado=new Usuarios("null","null", "null","null");
            Toast.makeText(getContext(),"NO entroOOOOOOOO",Toast.LENGTH_LONG).show();
            usuarioLogueado.setNombre("No hay usuario logueado");

        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       // return super.onCreateView(inflater, container, savedInstanceState);
        ((InicioUsuarioActivity) getActivity()).setOnBackPressedListener(this);
        final View v=inflater.inflate(R.layout.fragment_imagenes,container, false);

        dialogo=new Dialog(getContext());
        dialogo.setTitle("Guardando Imagen...");
        dialogo.setCancelable(false);
        dialogo.setContentView(R.layout.dialogo_insertar_imagen);

        textViewImagenesTitulo=v.findViewById(R.id.textViewImagenesTitulo);


        //imageViewImagenUmtp=v.findViewById(R.id.imageViewImagenUmtp);
        listImagenes = new ArrayList<>();
        mRecyclerView=v.findViewById(R.id.recyclerViewImagenes);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
        fabBotonAdicionarImagen= v.findViewById(R.id.fabBotonAdicionarImagen);
        fabBotonAdicionarCroquis= v.findViewById(R.id.fabBotonAdicionarCroquis);



        configurarInterfazSegunOrigen(origen);


        fabBotonAdicionarImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Toast.makeText(getContext(),proyectoSeleccionado.getNombre()+" "+ proyectoSeleccionado.getProyecto_id()+" "+proyectoSeleccionado.getProyecto_id(),Toast.LENGTH_LONG).show();
                System.out.println("CLICBOTONCREARUMTP");
                argumentosEnviados= new Bundle();
                argumentosEnviados.putSerializable("proyecto",proyectoSeleccionado);
                argumentosEnviados.putSerializable("usuario",usuarioLogueado);
                System.out.println(proyectoSeleccionado.getNombre()+" "+ proyectoSeleccionado.getProyecto_id()+" "+usuarioLogueado.getNombre());
                CrearUmtpFragment umtp= new CrearUmtpFragment();
                FragmentManager manager= getFragmentManager();
                umtp.setArguments(argumentosEnviados);
                manager.beginTransaction().replace(R.id.contenidos, umtp).commit();*/
                tomarFotografia();



            }
        });

        fabBotonAdicionarCroquis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                argumentosEnviados= new Bundle();
                argumentosEnviados.putSerializable("proyecto",proyectoSeleccionado);
                argumentosEnviados.putSerializable("usuario",usuarioLogueado);
                argumentosEnviados.putSerializable("umtp",umtpSeleccionada);
                argumentosEnviados.putString("origen","umtp");
                argumentosEnviados.putInt("numeroImagenes",numeroImagenes);
                argumentosEnviados.putString("ruta","/Recolectarq/Proyectos/"+proyectoSeleccionado.getCodigo_identificacion()+"/UMTP/UMTP"+umtpSeleccionada.getUmtp_id()+"/ImgUMTP" );
                System.out.println(proyectoSeleccionado.getNombre()+" "+ proyectoSeleccionado.getProyecto_id()+" "+usuarioLogueado.getNombre());
                DibujosFragment dibujoUmtp= new DibujosFragment();
                FragmentManager manager= getFragmentManager();
                dibujoUmtp.setArguments(argumentosEnviados);
                manager.beginTransaction().replace(R.id.contenidos, dibujoUmtp).commit();
            }
        });

        return v;
    }



    private void configurarObjetosSegunOrigen(String origen)
    {
        switch(origen)
        {
            case "verUmtp":
                break;
            case "pozo":
                pozoSeleccionado=(PozosSondeo)getArguments().getSerializable("pozo");
                idPozo=pozoSeleccionado.getPozo_id();

                break;
            case "estratoPozo":
                pozoSeleccionado=(PozosSondeo)getArguments().getSerializable("pozo");
                idPozo=pozoSeleccionado.getPozo_id();
                //fabBotonAdicionarCroquis.setEnabled(false);
                break;
            case "nivelPozo":
                pozoSeleccionado=(PozosSondeo)getArguments().getSerializable("pozo");
                idPozo=pozoSeleccionado.getPozo_id();
                nivelSeleccionado=(NivelesPozos)getArguments().getSerializable("nivel");
                idNivelPozo=nivelSeleccionado.getNivel_pozo_id();
                //fabBotonAdicionarCroquis.setEnabled(false);
                break;
            case "materialRecoleccion":
                recoleccionSeleccionado=(RecoleccionesSuperficiales)getArguments().getSerializable("recoleccion");
                idRecoleccion=recoleccionSeleccionado.getrecoleccion_superficial_id();
                materialRecoleccionSeleccionado=(MaterialesRecolecciones)getArguments().getSerializable("materialRecoleccion");
                idMaterialRecoleccion=materialRecoleccionSeleccionado.getMaterial_recoleccion_id();
                //fabBotonAdicionarCroquis.setEnabled(false);
                break;
            case "perfilExpuesto":
                perfilExpuestoSeleccionado=(PerfilesExpuestos)getArguments().getSerializable("perfil");
                idPerfil=perfilExpuestoSeleccionado.getPerfil_expuesto_id();
                break;
            case "estratoPerfil":
                perfilExpuestoSeleccionado=(PerfilesExpuestos)getArguments().getSerializable("perfil");
                idPerfil=perfilExpuestoSeleccionado.getPerfil_expuesto_id();
                estratoPerfilSeleccionado=(EstratosPerfiles)getArguments().getSerializable("estratoPerfil");
                idEstratoPerfil=estratoPerfilSeleccionado.getEstrato_perfil_id();
                break;
            case "pozoEstructura":
                pozoSeleccionado=(PozosSondeo)getArguments().getSerializable("pozo");
                idPozo=pozoSeleccionado.getPozo_id();
                estructuraSeleccionada=(EstructurasArqueologicas)getArguments().getSerializable("estructura");
                idEstructura=estructuraSeleccionada.getEstructuras_arqueologicas_id();
                break;
            case "recoleccionEstructura":
                recoleccionSeleccionado=(RecoleccionesSuperficiales)getArguments().getSerializable("recoleccion");
                idRecoleccion=recoleccionSeleccionado.getrecoleccion_superficial_id();
                estructuraSeleccionada=(EstructurasArqueologicas)getArguments().getSerializable("estructura");
                idEstructura=estructuraSeleccionada.getEstructuras_arqueologicas_id();
                break;
            case "perfilEstructura":
                perfilExpuestoSeleccionado=(PerfilesExpuestos)getArguments().getSerializable("perfil");
                idPerfil=perfilExpuestoSeleccionado.getPerfil_expuesto_id();
                estructuraSeleccionada=(EstructurasArqueologicas)getArguments().getSerializable("estructura");
                idEstructura=estructuraSeleccionada.getEstructuras_arqueologicas_id();
                break;

        }
    }

    private void configurarInterfazSegunOrigen(String origen)
    {
        switch(origen)
        {
            case "verUmtp":
                textViewImagenesTitulo.setText("Imágenes UMTP");
                consultarImagenesUmtp();
                break;
            case "pozo":
                textViewImagenesTitulo.setText("Imágenes Pozo");
                urlPozo=getString(R.string.ip_url)+"web_services/buscar_imagenes_pozo.php?pozo_id="+idPozo+"&tipo=M";
                System.out.println("EL ID DEL POZO ES :        "+ pozoSeleccionado.getPozo_id());
                fabBotonAdicionarCroquis.setEnabled(false);
                consultarImagenesPozo();
                break;
            case "estratoPozo":
                textViewImagenesTitulo.setText("Imágenes Estrato Pozo");
                urlPozo=getString(R.string.ip_url)+"web_services/buscar_imagenes_pozo.php?pozo_id="+idPozo+"&tipo=E";
                System.out.println("EL ID DEL POZO ES :        "+ pozoSeleccionado.getPozo_id()+"  "+urlPozo);
                fabBotonAdicionarCroquis.setEnabled(false);
                consultarImagenesPozo();
                break;

            case "nivelPozo":
                textViewImagenesTitulo.setText("Imágenes Nivel Pozo");
                urlPozo=getString(R.string.ip_url)+"web_services/buscar_imagenes_nivel_pozo.php?nivel_id="+idNivelPozo;
                System.out.println("EL ID DEL POZO ES:        "+ pozoSeleccionado.getPozo_id()+ "  id  "+nivelSeleccionado.getNivel_pozo_id()+"  "+urlPozo);
                fabBotonAdicionarCroquis.setEnabled(false);
                consultarImagenesNivelPozo();
                break;
            case "materialRecoleccion":
                textViewImagenesTitulo.setText("Imágenes Material Recolección");
                urlRecoleccion=getString(R.string.ip_url)+"web_services/buscar_imagenes_material_recoleccion.php?material_recoleccion_id="+idMaterialRecoleccion;
                System.out.println("EL ID DE LA RECOLECCION ES:        "+ recoleccionSeleccionado.getrecoleccion_superficial_id()+ "  id  "+materialRecoleccionSeleccionado.getMaterial_recoleccion_id()+"  "+urlRecoleccion);
                fabBotonAdicionarCroquis.setEnabled(false);
                consultarImagenesMaterialesRecoleccion();
                break;
            case "perfilExpuesto":
                textViewImagenesTitulo.setText("Imágenes Perfil Expuesto");
                urlPerfil=getString(R.string.ip_url)+"web_services/buscar_imagenes_perfil.php?perfil_id="+idPerfil;
                System.out.println("EL ID DEL PERFIL ES:        "+ perfilExpuestoSeleccionado.getPerfil_expuesto_id()+"  "+urlPerfil);
                fabBotonAdicionarCroquis.setEnabled(false);
                consultarImagenesPerfil();
                break;
            case "estratoPerfil":
                textViewImagenesTitulo.setText("Imágenes Estrato Perfil Expuesto");
                urlEstratoPerfil=getString(R.string.ip_url)+"web_services/buscar_imagenes_estrato_perfil.php?estrato_perfil_id="+idEstratoPerfil;
                System.out.println("EL ID DEL ESTRATO DEL PERFIL ES:        "+ perfilExpuestoSeleccionado.getPerfil_expuesto_id()+"  "+urlEstratoPerfil);
                fabBotonAdicionarCroquis.setEnabled(false);
                consultarImagenesEstratoPerfil();
                break;

            case "pozoEstructura":
                textViewImagenesTitulo.setText("Imágenes Estructura Pozo");
                urlEstructura=getString(R.string.ip_url)+"web_services/buscar_imagenes_estructura.php?estructura_id="+idEstructura+"&tipo=F";
                System.out.println("EL ID DEL POZO ES :        "+ pozoSeleccionado.getPozo_id());
                fabBotonAdicionarCroquis.setEnabled(false);
                consultarImagenesEstructura();
                break;
            case "recoleccionEstructura":
                textViewImagenesTitulo.setText("Imágenes Estructura Recolección");
                urlEstructura=getString(R.string.ip_url)+"web_services/buscar_imagenes_estructura.php?estructura_id="+idEstructura+"&tipo=F";
                System.out.println("EL ID DE LA RECOLECCION ES :        "+ recoleccionSeleccionado.getrecoleccion_superficial_id());
                fabBotonAdicionarCroquis.setEnabled(false);
                consultarImagenesEstructura();
                break;
            case "perfilEstructura":
                textViewImagenesTitulo.setText("Imágenes Estructura Perfil");
                urlEstructura=getString(R.string.ip_url)+"web_services/buscar_imagenes_estructura.php?estructura_id="+idEstructura+"&tipo=F";
                System.out.println("EL ID DEL PERFIL ES :        "+ perfilExpuestoSeleccionado.getPerfil_expuesto_id());
                fabBotonAdicionarCroquis.setEnabled(false);
                consultarImagenesEstructura();
                break;
        }
    }

    private static  String CARPETA_PRINCIPAL;
    private  static  String CARPETA_IMAGEN;
    private static String DIRECTORIO_IMAGEN;
    private String path;
    File fileImagen;
    Bitmap bitmap;
    Bitmap bitmapRedim;
    String nombre;

    private static final int COD_FOTO=20;

    private void tomarFotografia() {

        crearPath();
        File miFile= new File(Environment.getExternalStorageDirectory(),DIRECTORIO_IMAGEN);
        boolean isCreada=miFile.exists();

        if(isCreada==false)
        {
            isCreada=miFile.mkdirs();
        }

        if(isCreada)
        {
            //nombre="umtp"+umtpSeleccionada.getUmtp_id()+"_"+numeroImagenes+".jpg";
            path=Environment.getExternalStorageDirectory()+File.separator+DIRECTORIO_IMAGEN
                    +File.separator+nombre;
            System.out.println("LA RUTA QUE LLEGA AL SERVIDOR ES:    "   +path);

            fileImagen=new File(path);
            Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N)
            {
                String authorities=getContext().getPackageName()+".provider";
                Uri imageUri=FileProvider.getUriForFile(getContext(),authorities,fileImagen);
                intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
            }else
            {
                intent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(fileImagen));
            }


            startActivityForResult(intent,COD_FOTO);
        }

    }

    private void crearPath() {

        CARPETA_PRINCIPAL="Recolectarq/Proyectos/"+proyectoSeleccionado.getCodigo_identificacion()+"/UMTP/"+"UMTP"+idUmtp+"/";

        //System.out.println("El oriegen al crear el path es: "+ origen);
        switch (origen)
        {
            case "verUmtp":

                CARPETA_IMAGEN="ImgUMTP";
                DIRECTORIO_IMAGEN=CARPETA_PRINCIPAL+CARPETA_IMAGEN;
                nombre="umtp"+umtpSeleccionada.getUmtp_id()+"_"+numeroImagenes+".jpg";
                break;

            case "pozo":
                    System.out.println("ESTE ES EL POZO SELECCIONADO PARA GUARADR:XXXXXXXXXXXXXXXXXX  " +pozoSeleccionado.getPozo_id() );
                CARPETA_IMAGEN="PS/PS"+pozoSeleccionado.getPozo_id()+"/ImgPS";
                DIRECTORIO_IMAGEN=CARPETA_PRINCIPAL+CARPETA_IMAGEN;
                nombre="pozoM"+pozoSeleccionado.getPozo_id()+"_"+numeroImagenes+".jpg";
                break;
            case "estratoPozo":
                System.out.println("ESTE ES EL POZO SELECCIONADO PARA GUARADR:XXXXXXXXXXXXXXXXXX  " +pozoSeleccionado.getPozo_id() );
                CARPETA_IMAGEN="PS/PS"+pozoSeleccionado.getPozo_id()+"/ImgPS";
                DIRECTORIO_IMAGEN=CARPETA_PRINCIPAL+CARPETA_IMAGEN;
                nombre="pozoE"+pozoSeleccionado.getPozo_id()+"_"+numeroImagenes+".jpg";
                break;

            case "nivelPozo":
                System.out.println("ESTE ES EL POZO SELECCIONADO PARA GUARADR:XXXXXXXXXXXXXXXXXX  " +pozoSeleccionado.getPozo_id() );
                CARPETA_IMAGEN="PS/PS"+pozoSeleccionado.getPozo_id()+"/ImgPS";
                DIRECTORIO_IMAGEN=CARPETA_PRINCIPAL+CARPETA_IMAGEN;
                nombre="pozoMN"+nivelSeleccionado.getNivel_pozo_id()+"_"+numeroImagenes+".jpg";
                break;

            case "materialRecoleccion":
                System.out.println("ESTE ES EL POZO SELECCIONADO PARA GUARADR:XXXXXXXXXXXXXXXXXX  " +recoleccionSeleccionado.getrecoleccion_superficial_id() );
                CARPETA_IMAGEN="RS/RS"+recoleccionSeleccionado.getrecoleccion_superficial_id()+"/ImgRS";
                DIRECTORIO_IMAGEN=CARPETA_PRINCIPAL+CARPETA_IMAGEN;
                nombre="recoleccionM"+materialRecoleccionSeleccionado.getMaterial_recoleccion_id()+"_"+numeroImagenes+".jpg";
                break;

            case "perfilExpuesto":
                System.out.println("ESTE ES EL PERFIL SELECCIONADO PARA GUARADR:XXXXXXXXXXXXXXXXXX  " +perfilExpuestoSeleccionado.getPerfil_expuesto_id() );
                CARPETA_IMAGEN="PE/PE"+perfilExpuestoSeleccionado.getPerfil_expuesto_id()+"/ImgPE";
                DIRECTORIO_IMAGEN=CARPETA_PRINCIPAL+CARPETA_IMAGEN;
                nombre="perfilP"+perfilExpuestoSeleccionado.getPerfil_expuesto_id()+"_"+numeroImagenes+".jpg";
                break;

            case "estratoPerfil":
                System.out.println("ESTE ES EL ESTRATO PERFIL SELECCIONADO PARA GUARADR:XXXXXXXXXXXXXXXXXX  " +perfilExpuestoSeleccionado.getPerfil_expuesto_id() );
                CARPETA_IMAGEN="PE/PE"+perfilExpuestoSeleccionado.getPerfil_expuesto_id()+"/ImgPE";
                DIRECTORIO_IMAGEN=CARPETA_PRINCIPAL+CARPETA_IMAGEN;
                nombre="perfilE"+estratoPerfilSeleccionado.getEstrato_perfil_id()+"_"+numeroImagenes+".jpg";
                break;

            case "pozoEstructura":
                System.out.println("ESTE ES la estructura de pozo SELECCIONADO PARA GUARADR:XXXXXXXXXXXXXXXXXX  " +pozoSeleccionado.getPozo_id() );
                CARPETA_IMAGEN="PS/PS"+pozoSeleccionado.getPozo_id()+"/ImgPS";
                DIRECTORIO_IMAGEN=CARPETA_PRINCIPAL+CARPETA_IMAGEN;
                nombre="pozoEsF"+estructuraSeleccionada.getEstructuras_arqueologicas_id()+"_"+numeroImagenes+".jpg";
                break;

            case "recoleccionEstructura":
                System.out.println("ESTE ES la estructura de recolección SELECCIONADO PARA GUARADR:XXXXXXXXXXXXXXXXXX  " +recoleccionSeleccionado.getrecoleccion_superficial_id() );
                CARPETA_IMAGEN="RS/RS"+recoleccionSeleccionado.getrecoleccion_superficial_id()+"/ImgRS";
                DIRECTORIO_IMAGEN=CARPETA_PRINCIPAL+CARPETA_IMAGEN;
                nombre="recoleccionEsF"+estructuraSeleccionada.getEstructuras_arqueologicas_id()+"_"+numeroImagenes+".jpg";
                break;

            case "perfilEstructura":
                System.out.println("ESTE ES la estructura de perfil SELECCIONADO PARA GUARADR:XXXXXXXXXXXXXXXXXX  " +perfilExpuestoSeleccionado.getPerfil_expuesto_id() );
                CARPETA_IMAGEN="PE/PE"+perfilExpuestoSeleccionado.getPerfil_expuesto_id()+"/ImgPE";
                DIRECTORIO_IMAGEN=CARPETA_PRINCIPAL+CARPETA_IMAGEN;
                nombre="perfilEsF"+estructuraSeleccionada.getEstructuras_arqueologicas_id()+"_"+numeroImagenes+".jpg";
                break;
        }


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //System.out.println("RequestCode: "+ requestCode+" ResultCode: "+ resultCode+" data: "+ data);

        switch (requestCode)
        {
            case COD_FOTO:
                MediaScannerConnection.scanFile(getContext(), new String[]{path}, null, new MediaScannerConnection.OnScanCompletedListener() {
                    @Override
                    public void onScanCompleted(String path, Uri uri) {
                        System.out.println("Path: "+path);
                    }
                });

                bitmap=BitmapFactory.decodeFile(path);
                //imageViewImagenUmtp.setImageBitmap(bitmap);

                redimensionarImagen(bitmap,10,20);

                switch (origen)
                {
                    case "verUmtp":
                        if(bitmap!=null){
                            webServiceInsertarImagenUMTP();
                        }

                        break;

                    case "pozo":
                        if(bitmap!=null) {
                            webServiceInsertarImagenPozo();
                        }
                        break;
                    case "estratoPozo":
                        if(bitmap!=null) {
                            webServiceInsertarImagenPozo();
                        }
                        break;
                    case "nivelPozo":
                        if(bitmap!=null) {
                            webServiceInsertarImagenNivelPozo();
                        }
                        break;
                    case "materialRecoleccion":
                        if(bitmap!=null) {
                            webServiceInsertarImagenMaterialRecoleccion();
                        }
                        break;
                    case "perfilExpuesto":
                        if(bitmap!=null) {
                            webServiceInsertarImagenPerfilExpuesto();
                        }
                        break;
                    case "estratoPerfil":
                        if(bitmap!=null) {
                            webServiceInsertarImagenEstratoPerfil();
                        }
                        break;
                    case "pozoEstructura":
                        if(bitmap!=null) {
                            webServiceInsertarEstructuraPozo();
                        }
                        break;
                    case "recoleccionEstructura":
                        if(bitmap!=null) {
                            webServiceInsertarEstructuraRecoleccion();
                        }
                        break;
                    case "perfilEstructura":
                        if(bitmap!=null) {
                            webServiceInsertarEstructuraPerfil();
                        }
                        break;
                }



                break;
        }
    }

    private void redimensionarImagen(Bitmap bitmap, float anchoNuevo, float altoNuevo) {

        if(bitmap==null)
        {
            //System.out.println("Dio clic atras????????????????????????????????");

        }else {

            int ancho = bitmap.getWidth();
            int alto = bitmap.getHeight();

            if (ancho > anchoNuevo || alto > altoNuevo) {
                float escalarAncho = anchoNuevo / ancho;
                float escalarAlto = altoNuevo / alto;

                Matrix matrix = new Matrix();
                matrix.postScale(escalarAncho, escalarAlto);
                bitmapRedim = Bitmap.createBitmap(bitmap, 0, 0, ancho, alto, matrix, false);
            } else {
                bitmapRedim = bitmap;
            }
        }

    }


    private void webServiceInsertarImagenEstratoPerfil() {

        /*final ProgressDialog pDialog=new ProgressDialog(getContext());
        pDialog.setMessage("Cargando...");
        pDialog.show();*/
        dialogo.show();

        String ip=getString(R.string.ip_url);

        String url=ip+"/web_services/insertar_imagen_estrato_perfil.php";

        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //pDialog.hide();
                dialogo.dismiss();

                if (response.trim().equalsIgnoreCase("inserto")){
                    // etiNombre.setText("");
                    //  txtDocumento.setText("");
                    //   etiProfesion.setText("");
                    Toast.makeText(getContext(),"Se ha insertado con exito",Toast.LENGTH_SHORT).show();
                    /*VerProyectoFragment proyecto= new VerProyectoFragment();
                    FragmentManager manager= getFragmentManager();
                    argumentosEnviados= new Bundle();
                    argumentosEnviados.putSerializable("usuario",usuarioLogueado);
                    argumentosEnviados.putSerializable("proyecto",proyectoSeleccionado);
                    proyecto.setArguments(argumentosEnviados);
                    manager.beginTransaction().replace(R.id.contenidos, proyecto).commit();*/
                    listImagenes.clear();
                    mAdapter.notifyDataSetChanged();
                    consultarImagenesEstratoPerfil();

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
                dialogo.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //String cedulaU=cedula.getText().toString();
                //String nombreU=nombre.getText().toString();
                //String apellidoU=apellido.getText().toString();

                String imagen=convertirImgString(bitmap);
                String imagenR=convertirImgString(bitmapRedim);

                Map<String,String> parametros=new HashMap<>();


                parametros.put("umtp_id",idUmtp+"" );
                parametros.put("perfil_id",idPerfil+"" );
                parametros.put("estrato_perfil_id",idEstratoPerfil+"" );
                parametros.put("nombre",nombre);
                parametros.put("tipo","E");
                parametros.put("imagen",imagen);
                parametros.put("proyecto",proyectoSeleccionado.getCodigo_identificacion());

                //parametros.put("imagenR",imagenR);

                return parametros;
            }
        };
        //request.add(stringRequest);
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(stringRequest);
    }

    private void webServiceInsertarImagenPerfilExpuesto() {

        /*final ProgressDialog pDialog=new ProgressDialog(getContext());
        pDialog.setMessage("Cargando...");
        pDialog.show();*/
        dialogo.show();

        String ip=getString(R.string.ip_url);

        String url=ip+"/web_services/insertar_imagen_perfil_expuesto.php";

        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //pDialog.hide();
                dialogo.dismiss();

                if (response.trim().equalsIgnoreCase("inserto")){
                    // etiNombre.setText("");
                    //  txtDocumento.setText("");
                    //   etiProfesion.setText("");
                    Toast.makeText(getContext(),"Se ha insertado con exito",Toast.LENGTH_SHORT).show();
                    /*VerProyectoFragment proyecto= new VerProyectoFragment();
                    FragmentManager manager= getFragmentManager();
                    argumentosEnviados= new Bundle();
                    argumentosEnviados.putSerializable("usuario",usuarioLogueado);
                    argumentosEnviados.putSerializable("proyecto",proyectoSeleccionado);
                    proyecto.setArguments(argumentosEnviados);
                    manager.beginTransaction().replace(R.id.contenidos, proyecto).commit();*/
                    listImagenes.clear();
                    mAdapter.notifyDataSetChanged();
                    consultarImagenesPerfil();

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
                dialogo.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //String cedulaU=cedula.getText().toString();
                //String nombreU=nombre.getText().toString();
                //String apellidoU=apellido.getText().toString();

                String imagen=convertirImgString(bitmap);
                String imagenR=convertirImgString(bitmapRedim);

                Map<String,String> parametros=new HashMap<>();


                parametros.put("umtp_id",idUmtp+"" );
                parametros.put("perfil_id",idPerfil+"" );
                parametros.put("nombre",nombre);
                parametros.put("tipo","P");
                parametros.put("imagen",imagen);
                parametros.put("proyecto",proyectoSeleccionado.getCodigo_identificacion());

                //parametros.put("imagenR",imagenR);

                return parametros;
            }
        };
        //request.add(stringRequest);
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(stringRequest);
    }

    private void webServiceInsertarImagenMaterialRecoleccion() {

        /*final ProgressDialog pDialog=new ProgressDialog(getContext());
        pDialog.setMessage("Cargando...");
        pDialog.show();*/
        dialogo.show();
        String ip=getString(R.string.ip_url);

        String url=ip+"/web_services/insertar_imagen_material_recoleccion.php";

        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //pDialog.hide();
                dialogo.dismiss();
                if (response.trim().equalsIgnoreCase("inserto")){
                    // etiNombre.setText("");
                    //  txtDocumento.setText("");
                    //   etiProfesion.setText("");
                    Toast.makeText(getContext(),"Se ha insertado con exito",Toast.LENGTH_SHORT).show();
                    /*VerProyectoFragment proyecto= new VerProyectoFragment();
                    FragmentManager manager= getFragmentManager();
                    argumentosEnviados= new Bundle();
                    argumentosEnviados.putSerializable("usuario",usuarioLogueado);
                    argumentosEnviados.putSerializable("proyecto",proyectoSeleccionado);
                    proyecto.setArguments(argumentosEnviados);
                    manager.beginTransaction().replace(R.id.contenidos, proyecto).commit();*/
                    listImagenes.clear();
                    mAdapter.notifyDataSetChanged();
                    consultarImagenesMaterialesRecoleccion();

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
                dialogo.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //String cedulaU=cedula.getText().toString();
                //String nombreU=nombre.getText().toString();
                //String apellidoU=apellido.getText().toString();

                String imagen=convertirImgString(bitmap);
                String imagenR=convertirImgString(bitmapRedim);

                Map<String,String> parametros=new HashMap<>();


                parametros.put("umtp_id",idUmtp+"" );
                parametros.put("recoleccion_id",idRecoleccion+"" );
                parametros.put("material_recoleccion_id",idMaterialRecoleccion+"" );
                parametros.put("nombre",nombre);
                parametros.put("imagen",imagen);
                parametros.put("proyecto",proyectoSeleccionado.getCodigo_identificacion());

                //parametros.put("imagenR",imagenR);

                return parametros;
            }
        };
        //request.add(stringRequest);
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(stringRequest);
    }

    private void webServiceInsertarImagenNivelPozo() {

        /*final ProgressDialog pDialog=new ProgressDialog(getContext());
        pDialog.setMessage("Cargando...");
        pDialog.show();*/
        dialogo.show();
        String ip=getString(R.string.ip_url);

        String url=ip+"/web_services/insertar_imagen_nivel_pozo.php";

        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //pDialog.hide();
                dialogo.dismiss();
                if (response.trim().equalsIgnoreCase("inserto")){
                    // etiNombre.setText("");
                    //  txtDocumento.setText("");
                    //   etiProfesion.setText("");
                    Toast.makeText(getContext(),"Se ha insertado con exito",Toast.LENGTH_SHORT).show();
                    /*VerProyectoFragment proyecto= new VerProyectoFragment();
                    FragmentManager manager= getFragmentManager();
                    argumentosEnviados= new Bundle();
                    argumentosEnviados.putSerializable("usuario",usuarioLogueado);
                    argumentosEnviados.putSerializable("proyecto",proyectoSeleccionado);
                    proyecto.setArguments(argumentosEnviados);
                    manager.beginTransaction().replace(R.id.contenidos, proyecto).commit();*/
                    listImagenes.clear();
                    mAdapter.notifyDataSetChanged();
                    consultarImagenesNivelPozo();

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
                dialogo.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //String cedulaU=cedula.getText().toString();
                //String nombreU=nombre.getText().toString();
                //String apellidoU=apellido.getText().toString();

                String imagen=convertirImgString(bitmap);
                String imagenR=convertirImgString(bitmapRedim);

                Map<String,String> parametros=new HashMap<>();


                parametros.put("umtp_id",idUmtp+"" );
                parametros.put("pozo_id",idPozo+"" );
                parametros.put("nivel_id",idNivelPozo+"" );
                parametros.put("nombre",nombre);
                parametros.put("imagen",imagen);
                parametros.put("proyecto",proyectoSeleccionado.getCodigo_identificacion());

                //parametros.put("imagenR",imagenR);

                return parametros;
            }
        };
        //request.add(stringRequest);
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(stringRequest);
    }

    private void webServiceInsertarEstructuraPozo() {

        /*final ProgressDialog pDialog=new ProgressDialog(getContext());
        pDialog.setMessage("Cargando...");
        pDialog.show();*/
        dialogo.show();
        String ip=getString(R.string.ip_url);

        String url=ip+"/web_services/insertar_imagen_estructura_pozo.php";

        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //pDialog.hide();
                dialogo.dismiss();
                if (response.trim().equalsIgnoreCase("inserto")){
                    // etiNombre.setText("");
                    //  txtDocumento.setText("");
                    //   etiProfesion.setText("");
                    Toast.makeText(getContext(),"Se ha insertado con exito",Toast.LENGTH_SHORT).show();
                    /*VerProyectoFragment proyecto= new VerProyectoFragment();
                    FragmentManager manager= getFragmentManager();
                    argumentosEnviados= new Bundle();
                    argumentosEnviados.putSerializable("usuario",usuarioLogueado);
                    argumentosEnviados.putSerializable("proyecto",proyectoSeleccionado);
                    proyecto.setArguments(argumentosEnviados);
                    manager.beginTransaction().replace(R.id.contenidos, proyecto).commit();*/
                    listImagenes.clear();
                    mAdapter.notifyDataSetChanged();
                    consultarImagenesEstructura();

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
                dialogo.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //String cedulaU=cedula.getText().toString();
                //String nombreU=nombre.getText().toString();
                //String apellidoU=apellido.getText().toString();

                String imagen=convertirImgString(bitmap);
                String imagenR=convertirImgString(bitmapRedim);

                Map<String,String> parametros=new HashMap<>();

                parametros.put("pozo_id",idPozo+"" );
                parametros.put("umtp_id",idUmtp+"" );
                parametros.put("estructura_id",idEstructura+"" );
                parametros.put("tipo","F");
                parametros.put("nombre",nombre);
                parametros.put("imagen",imagen);
                parametros.put("proyecto",proyectoSeleccionado.getCodigo_identificacion());

                //parametros.put("imagenR",imagenR);

                return parametros;
            }
        };
        //request.add(stringRequest);
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(stringRequest);
    }
    private void webServiceInsertarEstructuraPerfil() {

        /*final ProgressDialog pDialog=new ProgressDialog(getContext());
        pDialog.setMessage("Cargando...");
        pDialog.show();*/
        dialogo.show();
        String ip=getString(R.string.ip_url);

        String url=ip+"/web_services/insertar_imagen_estructura_perfil.php";

        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //pDialog.hide();
                dialogo.dismiss();
                if (response.trim().equalsIgnoreCase("inserto")){
                    // etiNombre.setText("");
                    //  txtDocumento.setText("");
                    //   etiProfesion.setText("");
                    Toast.makeText(getContext(),"Se ha insertado con exito",Toast.LENGTH_SHORT).show();
                    /*VerProyectoFragment proyecto= new VerProyectoFragment();
                    FragmentManager manager= getFragmentManager();
                    argumentosEnviados= new Bundle();
                    argumentosEnviados.putSerializable("usuario",usuarioLogueado);
                    argumentosEnviados.putSerializable("proyecto",proyectoSeleccionado);
                    proyecto.setArguments(argumentosEnviados);
                    manager.beginTransaction().replace(R.id.contenidos, proyecto).commit();*/
                    listImagenes.clear();
                    mAdapter.notifyDataSetChanged();
                    consultarImagenesEstructura();

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
                dialogo.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //String cedulaU=cedula.getText().toString();
                //String nombreU=nombre.getText().toString();
                //String apellidoU=apellido.getText().toString();

                String imagen=convertirImgString(bitmap);
                String imagenR=convertirImgString(bitmapRedim);

                Map<String,String> parametros=new HashMap<>();

                parametros.put("perfil_id",idPerfil+"" );
                parametros.put("umtp_id",idUmtp+"" );
                parametros.put("estructura_id",idEstructura+"" );
                parametros.put("tipo","F");
                parametros.put("nombre",nombre);
                parametros.put("imagen",imagen);
                parametros.put("proyecto",proyectoSeleccionado.getCodigo_identificacion());

                //parametros.put("imagenR",imagenR);

                return parametros;
            }
        };
        //request.add(stringRequest);
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(stringRequest);
    }

    private void webServiceInsertarEstructuraRecoleccion() {

        /*final ProgressDialog pDialog=new ProgressDialog(getContext());
        pDialog.setMessage("Cargando...");
        pDialog.show();*/
        dialogo.show();
        String ip=getString(R.string.ip_url);

        String url=ip+"/web_services/insertar_imagen_estructura_recoleccion.php";

        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //pDialog.hide();
                dialogo.dismiss();
                if (response.trim().equalsIgnoreCase("inserto")){
                    // etiNombre.setText("");
                    //  txtDocumento.setText("");
                    //   etiProfesion.setText("");
                    Toast.makeText(getContext(),"Se ha insertado con exito",Toast.LENGTH_SHORT).show();
                    /*VerProyectoFragment proyecto= new VerProyectoFragment();
                    FragmentManager manager= getFragmentManager();
                    argumentosEnviados= new Bundle();
                    argumentosEnviados.putSerializable("usuario",usuarioLogueado);
                    argumentosEnviados.putSerializable("proyecto",proyectoSeleccionado);
                    proyecto.setArguments(argumentosEnviados);
                    manager.beginTransaction().replace(R.id.contenidos, proyecto).commit();*/
                    listImagenes.clear();
                    mAdapter.notifyDataSetChanged();
                    consultarImagenesEstructura();

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
                dialogo.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //String cedulaU=cedula.getText().toString();
                //String nombreU=nombre.getText().toString();
                //String apellidoU=apellido.getText().toString();

                String imagen=convertirImgString(bitmap);
                String imagenR=convertirImgString(bitmapRedim);

                Map<String,String> parametros=new HashMap<>();

                parametros.put("recoleccion_id",idRecoleccion+"" );
                parametros.put("umtp_id",idUmtp+"" );
                parametros.put("estructura_id",idEstructura+"" );
                parametros.put("tipo","F");
                parametros.put("nombre",nombre);
                parametros.put("imagen",imagen);
                parametros.put("proyecto",proyectoSeleccionado.getCodigo_identificacion());

                //parametros.put("imagenR",imagenR);

                return parametros;
            }
        };
        //request.add(stringRequest);
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(stringRequest);
    }
    private void webServiceInsertarImagenPozo() {

        /*final ProgressDialog pDialog=new ProgressDialog(getContext());
        pDialog.setMessage("Cargando...");
        pDialog.show();*/
        dialogo.show();

        String ip=getString(R.string.ip_url);

        String url=ip+"/web_services/insertar_imagen_pozo.php";

        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //pDialog.hide();
                dialogo.dismiss();
                if (response.trim().equalsIgnoreCase("inserto")){
                    // etiNombre.setText("");
                    //  txtDocumento.setText("");
                    //   etiProfesion.setText("");
                    Toast.makeText(getContext(),"Se ha insertado con exito",Toast.LENGTH_SHORT).show();
                    /*VerProyectoFragment proyecto= new VerProyectoFragment();
                    FragmentManager manager= getFragmentManager();
                    argumentosEnviados= new Bundle();
                    argumentosEnviados.putSerializable("usuario",usuarioLogueado);
                    argumentosEnviados.putSerializable("proyecto",proyectoSeleccionado);
                    proyecto.setArguments(argumentosEnviados);
                    manager.beginTransaction().replace(R.id.contenidos, proyecto).commit();*/
                    listImagenes.clear();
                    mAdapter.notifyDataSetChanged();
                    consultarImagenesPozo();

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
                dialogo.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //String cedulaU=cedula.getText().toString();
                //String nombreU=nombre.getText().toString();
                //String apellidoU=apellido.getText().toString();

                String imagen=convertirImgString(bitmap);
                String imagenR=convertirImgString(bitmapRedim);

                Map<String,String> parametros=new HashMap<>();
                switch (origen)
                {
                    case "pozo":
                        parametros.put("tipo","M");
                        break;
                    case "estratoPozo":
                        parametros.put("tipo","E");
                        break;

                }



                parametros.put("pozo_id",idPozo+"" );
                parametros.put("umtp_id",idUmtp+"" );

                parametros.put("nombre",nombre);
                parametros.put("imagen",imagen);
                parametros.put("proyecto",proyectoSeleccionado.getCodigo_identificacion());

                //parametros.put("imagenR",imagenR);

                return parametros;
            }
        };
        //request.add(stringRequest);
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(stringRequest);
    }

    private void webServiceInsertarImagenUMTP() {

        /*final ProgressDialog pDialog=new ProgressDialog(getContext());
        pDialog.setMessage("Cargando...");
        pDialog.show();*/

        dialogo.show();

        String ip=getString(R.string.ip_url);

        String url=ip+"/web_services/insertar_imagen_umtp.php";

        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //pDialog.hide();
                dialogo.dismiss();

                if (response.trim().equalsIgnoreCase("inserto")){
                    // etiNombre.setText("");
                    //  txtDocumento.setText("");
                    //   etiProfesion.setText("");
                    Toast.makeText(getContext(),"Se ha insertado con exito",Toast.LENGTH_SHORT).show();
                    /*VerProyectoFragment proyecto= new VerProyectoFragment();
                    FragmentManager manager= getFragmentManager();
                    argumentosEnviados= new Bundle();
                    argumentosEnviados.putSerializable("usuario",usuarioLogueado);
                    argumentosEnviados.putSerializable("proyecto",proyectoSeleccionado);
                    proyecto.setArguments(argumentosEnviados);
                    manager.beginTransaction().replace(R.id.contenidos, proyecto).commit();*/
                    listImagenes.clear();
                    mAdapter.notifyDataSetChanged();
                    consultarImagenesUmtp();

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
                dialogo.dismiss();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //String cedulaU=cedula.getText().toString();
                //String nombreU=nombre.getText().toString();
                //String apellidoU=apellido.getText().toString();

                String imagen=convertirImgString(bitmap);
                String imagenR=convertirImgString(bitmapRedim);
                Map<String,String> parametros=new HashMap<>();
                parametros.put("umtp_id",idUmtp+"" );
                parametros.put("tipo","I");
                parametros.put("nombre",nombre);
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


    private void consultarImagenesUmtp() {
        /*final ProgressDialog pDialog;
        pDialog=new ProgressDialog(getContext());
        pDialog.setMessage("Cargando...");
        pDialog.show();*/

        final String ip=getString(R.string.ip_url);

        String url=ip+"/web_services/buscar_imagenes_umtp.php?umtp_id="+idUmtp;

        System.out.println(url);

        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //pDialog.hide();

                JSONArray json=response.optJSONArray("imagenes_umtp");
                JSONObject jsonObject;

                try {
                    jsonObject = json.getJSONObject(0);
                    if(jsonObject.getInt("umtp_id")==0){
                        numeroImagenes=1;
                        imagenes = new Imagenes(0,0,"null","null");
                        listImagenes.add(imagenes);
                    }else {
                        numeroImagenes=json.length()+1;
                        for (int i = 0; i < json.length(); i++) {
                            jsonObject = json.getJSONObject(i);

                            imagenes =new Imagenes(jsonObject.getInt("id"), jsonObject.getInt("umtp_id"),
                                    jsonObject.getString("nombre"),jsonObject.getString("tipo"));
                            System.out.println("La imagene es la ???????????????????? : "+ imagenes.getId());
                            //proyecto.setNombre(jsonObject.optString("nombre"));
                            //miUsuario.setProfesion(jsonObject.optString("profesion"));
                            //miUsuario.setRutaImagen(jsonObject.optString("ruta_imagen"));
                            //System.out.println(jsonObject.getString("nombre"));
                            listImagenes.add(imagenes);

                        }
                    }
                    urlEnviada=getString(R.string.ip_url)+"recolectarq/proyecto/"+proyectoSeleccionado.getCodigo_identificacion()
                            +"/UMTP/UMTP"+umtpSeleccionada.getUmtp_id()+"/ImgUMTP/";
                    mAdapter=new AdapterImagenes(listImagenes, "imagenesUmtp",getContext(),urlEnviada,R.layout.recycler_view_imagenes, new AdapterImagenes.OnItemClickListener() {
                        @Override
                        public void onItemClick(Imagenes listImagenes, int position, String boton) {
                            System.out.println("DIOOOOO clic boton: " + boton+ " DESDE VER PROYECTO");
                            System.out.println("El USUARIO" + usuarioLogueado.getNombre());
                            /*if(boton.equals("editar")){

                                System.out.println("VEEEEERRR");

                                System.out.println("El USUARIO" + usuarioLogueado.getNombre());
                                umtp=listUmtp;
                                System.out.println("LA UTMx AL LLEVARLA A VER  : "+ umtp.getUtmx());

                                argumentosEnviados= new Bundle();
                                argumentosEnviados.putSerializable("proyecto",proyectoSeleccionado);
                                argumentosEnviados.putSerializable("usuario",usuarioLogueado);
                                argumentosEnviados.putSerializable("umtp",umtp);
                                VerUmtpFragment verUmtp= new VerUmtpFragment();
                                verUmtp.setArguments(argumentosEnviados);
                                FragmentManager manager= getFragmentManager();
                                manager.beginTransaction().replace(R.id.contenidos, verUmtp).commit();
                            }*/
                        }
                    });
                  //webServiceCargarImagenes();
                }  catch (JSONException e) {
                    e.printStackTrace();
                }

                //etiNombre.setText(miUsuario.getNombre());//SE MODIFICA
                //etiProfesion.setText(miUsuario.getProfesion());//SE MODIFICA

                //String urlImagen=ip+"/ejemploBDRemota/"+miUsuario.getRutaImagen();
                //Toast.makeText(getContext(), "url "+urlImagen, Toast.LENGTH_LONG).show();
                //cargarWebServiceImagen(urlImagen);

                mRecyclerView.setAdapter(mAdapter);
                json=null;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "No se puede conectar "+error.toString(), Toast.LENGTH_LONG).show();
                System.out.println();
                //pDialog.hide();
                Log.d("ERROR: ", error.toString());
            }
        });

        // request.add(jsonObjectRequest);
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(jsonObjectRequest);
    }

    private void consultarImagenesEstructura() {
        /*final ProgressDialog pDialog;
        pDialog=new ProgressDialog(getContext());
        pDialog.setMessage("Cargando...");
        pDialog.show();*/

        //final String ip=getString(R.string.ip_url);

        //String url=ip+"web_services/buscar_imagenes_pozo.php?pozo_id="+idPozo;

        System.out.println("La URL DE ESTRUCTURA ES :"+urlEstructura);

        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, urlEstructura, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //pDialog.hide();

                JSONArray json=response.optJSONArray("imagenes_estructura");
                JSONObject jsonObject;

                try {
                    jsonObject = json.getJSONObject(0);
                    if(jsonObject.getInt("imagen_estructura_id")==0){
                        numeroImagenes=1;
                        imagenes = new Imagenes(0,0,"null","null");
                        listImagenes.add(imagenes);
                    }else {
                        numeroImagenes=json.length()+1;
                        for (int i = 0; i < json.length(); i++) {
                            jsonObject = json.getJSONObject(i);

                            imagenes =new Imagenes(jsonObject.getInt("imagen_estructura_id"), jsonObject.getInt("estructuras_arqueologicas_id"),
                                    jsonObject.getString("nombre"),jsonObject.getString("tipo"));
                            System.out.println("La imagene del pozo es la  ???????????????????? : "+ imagenes.getId());
                            //proyecto.setNombre(jsonObject.optString("nombre"));
                            //miUsuario.setProfesion(jsonObject.optString("profesion"));
                            //miUsuario.setRutaImagen(jsonObject.optString("ruta_imagen"));
                            //System.out.println(jsonObject.getString("nombre"));
                            listImagenes.add(imagenes);

                        }
                    }

                    System.out.println("El ORIGEN DE ESTRUCTURA ES :"+origen);
                    switch(origen)
                    {
                        case "pozoEstructura":
                            urlEnviada=getString(R.string.ip_url)+"recolectarq/proyecto/"+proyectoSeleccionado.getCodigo_identificacion()
                                    +"/UMTP/UMTP"+umtpSeleccionada.getUmtp_id()+"/PS/PS"+pozoSeleccionado.getPozo_id()+"/ImgPS/";
                            break;
                        case "recoleccionEstructura":
                            urlEnviada=getString(R.string.ip_url)+"recolectarq/proyecto/"+proyectoSeleccionado.getCodigo_identificacion()
                                    +"/UMTP/UMTP"+umtpSeleccionada.getUmtp_id()+"/RS/RS"+recoleccionSeleccionado.getrecoleccion_superficial_id()+"/ImgRS/";
                            break;

                        case "perfilEstructura":
                            urlEnviada=getString(R.string.ip_url)+"recolectarq/proyecto/"+proyectoSeleccionado.getCodigo_identificacion()
                                    +"/UMTP/UMTP"+umtpSeleccionada.getUmtp_id()+"/PE/PE"+perfilExpuestoSeleccionado.getPerfil_expuesto_id()+"/ImgPE/";
                            break;
                    }


                    mAdapter=new AdapterImagenes(listImagenes, "imagenesEstructura",getContext(),urlEnviada,R.layout.recycler_view_imagenes, new AdapterImagenes.OnItemClickListener() {
                        @Override
                        public void onItemClick(Imagenes listImagenes, int position, String boton) {
                            System.out.println("DIOOOOO clic boton: " + boton+ " DESDE VER PROYECTO");
                            System.out.println("El USUARIO" + usuarioLogueado.getNombre());
                            /*if(boton.equals("editar")){

                                System.out.println("VEEEEERRR");

                                System.out.println("El USUARIO" + usuarioLogueado.getNombre());
                                umtp=listUmtp;
                                System.out.println("LA UTMx AL LLEVARLA A VER  : "+ umtp.getUtmx());

                                argumentosEnviados= new Bundle();
                                argumentosEnviados.putSerializable("proyecto",proyectoSeleccionado);
                                argumentosEnviados.putSerializable("usuario",usuarioLogueado);
                                argumentosEnviados.putSerializable("umtp",umtp);
                                VerUmtpFragment verUmtp= new VerUmtpFragment();
                                verUmtp.setArguments(argumentosEnviados);
                                FragmentManager manager= getFragmentManager();
                                manager.beginTransaction().replace(R.id.contenidos, verUmtp).commit();
                            }*/
                        }
                    });
                    //webServiceCargarImagenes();
                }  catch (JSONException e) {
                    e.printStackTrace();
                }

                //etiNombre.setText(miUsuario.getNombre());//SE MODIFICA
                //etiProfesion.setText(miUsuario.getProfesion());//SE MODIFICA

                //String urlImagen=ip+"/ejemploBDRemota/"+miUsuario.getRutaImagen();
                //Toast.makeText(getContext(), "url "+urlImagen, Toast.LENGTH_LONG).show();
                //cargarWebServiceImagen(urlImagen);

                mRecyclerView.setAdapter(mAdapter);
                json=null;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "No se puede conectar "+error.toString(), Toast.LENGTH_LONG).show();
                System.out.println();
                //pDialog.hide();
                Log.d("ERROR: ", error.toString());
            }
        });

        // request.add(jsonObjectRequest);
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(jsonObjectRequest);
    }
    private void consultarImagenesPozo() {
        final ProgressDialog pDialog;
        /*pDialog=new ProgressDialog(getContext());
        pDialog.setMessage("Cargando...");
        pDialog.show();*/

        //final String ip=getString(R.string.ip_url);

        //String url=ip+"web_services/buscar_imagenes_pozo.php?pozo_id="+idPozo;

        System.out.println(url);

        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, urlPozo, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //pDialog.hide();

                JSONArray json=response.optJSONArray("imagenes_pozos");
                JSONObject jsonObject;

                try {
                    jsonObject = json.getJSONObject(0);
                    if(jsonObject.getInt("pozos_pozo_id")==0){
                        numeroImagenes=1;
                        imagenes = new Imagenes(0,0,"null","null");
                        listImagenes.add(imagenes);
                    }else {
                        numeroImagenes=json.length()+1;
                        for (int i = 0; i < json.length(); i++) {
                            jsonObject = json.getJSONObject(i);

                            imagenes =new Imagenes(jsonObject.getInt("imagen_pozo_id"), jsonObject.getInt("pozos_pozo_id"),
                                    jsonObject.getString("nombre"),jsonObject.getString("tipo"));
                            System.out.println("La imagene del pozo es la  ???????????????????? : "+ imagenes.getId());
                            //proyecto.setNombre(jsonObject.optString("nombre"));
                            //miUsuario.setProfesion(jsonObject.optString("profesion"));
                            //miUsuario.setRutaImagen(jsonObject.optString("ruta_imagen"));
                            //System.out.println(jsonObject.getString("nombre"));
                            listImagenes.add(imagenes);

                        }
                    }
                    urlEnviada=getString(R.string.ip_url)+"recolectarq/proyecto/"+proyectoSeleccionado.getCodigo_identificacion()
                            +"/UMTP/UMTP"+umtpSeleccionada.getUmtp_id()+"/PS/PS"+pozoSeleccionado.getPozo_id()+"/ImgPS/";
                    mAdapter=new AdapterImagenes(listImagenes, "imagenesPozo",getContext(),urlEnviada,R.layout.recycler_view_imagenes, new AdapterImagenes.OnItemClickListener() {
                        @Override
                        public void onItemClick(Imagenes listImagenes, int position, String boton) {
                            System.out.println("DIOOOOO clic boton: " + boton+ " DESDE VER PROYECTO");
                            System.out.println("El USUARIO" + usuarioLogueado.getNombre());
                            /*if(boton.equals("editar")){

                                System.out.println("VEEEEERRR");

                                System.out.println("El USUARIO" + usuarioLogueado.getNombre());
                                umtp=listUmtp;
                                System.out.println("LA UTMx AL LLEVARLA A VER  : "+ umtp.getUtmx());

                                argumentosEnviados= new Bundle();
                                argumentosEnviados.putSerializable("proyecto",proyectoSeleccionado);
                                argumentosEnviados.putSerializable("usuario",usuarioLogueado);
                                argumentosEnviados.putSerializable("umtp",umtp);
                                VerUmtpFragment verUmtp= new VerUmtpFragment();
                                verUmtp.setArguments(argumentosEnviados);
                                FragmentManager manager= getFragmentManager();
                                manager.beginTransaction().replace(R.id.contenidos, verUmtp).commit();
                            }*/
                        }
                    });
                    //webServiceCargarImagenes();
                }  catch (JSONException e) {
                    e.printStackTrace();
                }

                //etiNombre.setText(miUsuario.getNombre());//SE MODIFICA
                //etiProfesion.setText(miUsuario.getProfesion());//SE MODIFICA

                //String urlImagen=ip+"/ejemploBDRemota/"+miUsuario.getRutaImagen();
                //Toast.makeText(getContext(), "url "+urlImagen, Toast.LENGTH_LONG).show();
                //cargarWebServiceImagen(urlImagen);

                mRecyclerView.setAdapter(mAdapter);
                json=null;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "No se puede conectar "+error.toString(), Toast.LENGTH_LONG).show();
                System.out.println();
                //pDialog.hide();
                Log.d("ERROR: ", error.toString());
            }
        });

        // request.add(jsonObjectRequest);
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(jsonObjectRequest);
    }

    private void consultarImagenesNivelPozo() {
        final ProgressDialog pDialog;
        /*pDialog=new ProgressDialog(getContext());
        pDialog.setMessage("Cargando...");
        pDialog.show();*/

        //final String ip=getString(R.string.ip_url);

        //String url=ip+"web_services/buscar_imagenes_nivel_pozo.php?pozo_id="+idPozo;

        System.out.println(url);

        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, urlPozo, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
               // pDialog.hide();

                JSONArray json=response.optJSONArray("imagenes_nivel_pozo");
                JSONObject jsonObject;

                try {
                    jsonObject = json.getJSONObject(0);
                    if(jsonObject.getInt("imagen_nivel_pozo_id")==0){
                        numeroImagenes=1;
                        imagenes = new Imagenes(0,0,"null","null");
                        listImagenes.add(imagenes);
                    }else {
                        numeroImagenes=json.length()+1;
                        for (int i = 0; i < json.length(); i++) {
                            jsonObject = json.getJSONObject(i);

                            imagenes =new Imagenes(jsonObject.getInt("imagen_nivel_pozo_id"), jsonObject.getInt("niveles_pozos_nivel_pozo_id"),
                                    jsonObject.getString("nombre"),"");
                            System.out.println("La imagene del pozo es la  ???????????????????? : "+ imagenes.getId());
                            //proyecto.setNombre(jsonObject.optString("nombre"));
                            //miUsuario.setProfesion(jsonObject.optString("profesion"));
                            //miUsuario.setRutaImagen(jsonObject.optString("ruta_imagen"));
                            //System.out.println(jsonObject.getString("nombre"));
                            listImagenes.add(imagenes);

                        }
                    }
                    urlEnviada=getString(R.string.ip_url)+"recolectarq/proyecto/"+proyectoSeleccionado.getCodigo_identificacion()
                            +"/UMTP/UMTP"+umtpSeleccionada.getUmtp_id()+"/PS/PS"+pozoSeleccionado.getPozo_id()+"/ImgPS/";
                    mAdapter=new AdapterImagenes(listImagenes, "imagenesNivelPozo",getContext(),urlEnviada,R.layout.recycler_view_imagenes, new AdapterImagenes.OnItemClickListener() {
                        @Override
                        public void onItemClick(Imagenes listImagenes, int position, String boton) {
                            System.out.println("DIOOOOO clic boton: " + boton+ " DESDE VER PROYECTO");
                            System.out.println("El USUARIO" + usuarioLogueado.getNombre());
                            /*if(boton.equals("editar")){

                                System.out.println("VEEEEERRR");

                                System.out.println("El USUARIO" + usuarioLogueado.getNombre());
                                umtp=listUmtp;
                                System.out.println("LA UTMx AL LLEVARLA A VER  : "+ umtp.getUtmx());

                                argumentosEnviados= new Bundle();
                                argumentosEnviados.putSerializable("proyecto",proyectoSeleccionado);
                                argumentosEnviados.putSerializable("usuario",usuarioLogueado);
                                argumentosEnviados.putSerializable("umtp",umtp);
                                VerUmtpFragment verUmtp= new VerUmtpFragment();
                                verUmtp.setArguments(argumentosEnviados);
                                FragmentManager manager= getFragmentManager();
                                manager.beginTransaction().replace(R.id.contenidos, verUmtp).commit();
                            }*/
                        }
                    });
                    //webServiceCargarImagenes();
                }  catch (JSONException e) {
                    e.printStackTrace();
                }

                //etiNombre.setText(miUsuario.getNombre());//SE MODIFICA
                //etiProfesion.setText(miUsuario.getProfesion());//SE MODIFICA

                //String urlImagen=ip+"/ejemploBDRemota/"+miUsuario.getRutaImagen();
                //Toast.makeText(getContext(), "url "+urlImagen, Toast.LENGTH_LONG).show();
                //cargarWebServiceImagen(urlImagen);

                mRecyclerView.setAdapter(mAdapter);
                json=null;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "No se puede conectar "+error.toString(), Toast.LENGTH_LONG).show();
                System.out.println();
                //pDialog.hide();
                Log.d("ERROR: ", error.toString());
            }
        });

        // request.add(jsonObjectRequest);
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(jsonObjectRequest);
    }


    private void consultarImagenesMaterialesRecoleccion() {
        /*final ProgressDialog pDialog;
        pDialog=new ProgressDialog(getContext());
        pDialog.setMessage("Cargando...");
        pDialog.show();*/

        //final String ip=getString(R.string.ip_url);

        //String url=ip+"web_services/buscar_imagenes_nivel_pozo.php?pozo_id="+idPozo;

        System.out.println(urlRecoleccion);

        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, urlRecoleccion, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //pDialog.hide();

                JSONArray json=response.optJSONArray("imagenes_materiales_recoleccion");
                JSONObject jsonObject;

                try {
                    jsonObject = json.getJSONObject(0);
                    if(jsonObject.getInt("imagen_material_recoleccion_id")==0){
                        numeroImagenes=1;
                        imagenes = new Imagenes(0,0,"null","null");
                        listImagenes.add(imagenes);
                    }else {
                        numeroImagenes=json.length()+1;
                        for (int i = 0; i < json.length(); i++) {
                            jsonObject = json.getJSONObject(i);

                            imagenes =new Imagenes(jsonObject.getInt("imagen_material_recoleccion_id"), jsonObject.getInt("materiales_recolecciones_material_recoleccion_id"),
                                    jsonObject.getString("nombre"),"");
                            System.out.println("La imagene del material de la recoleccion es la  ???????????????????? : "+ imagenes.getId());
                            //proyecto.setNombre(jsonObject.optString("nombre"));
                            //miUsuario.setProfesion(jsonObject.optString("profesion"));
                            //miUsuario.setRutaImagen(jsonObject.optString("ruta_imagen"));
                            //System.out.println(jsonObject.getString("nombre"));
                            listImagenes.add(imagenes);

                        }
                    }
                    urlEnviada=getString(R.string.ip_url)+"recolectarq/proyecto/"+proyectoSeleccionado.getCodigo_identificacion()
                            +"/UMTP/UMTP"+umtpSeleccionada.getUmtp_id()+"/RS/RS"+recoleccionSeleccionado.getrecoleccion_superficial_id()+"/ImgRS/";
                    mAdapter=new AdapterImagenes(listImagenes, "imagenesMaterialRecoleccion",getContext(),urlEnviada,R.layout.recycler_view_imagenes, new AdapterImagenes.OnItemClickListener() {
                        @Override
                        public void onItemClick(Imagenes listImagenes, int position, String boton) {
                            System.out.println("DIOOOOO clic boton: " + boton+ " DESDE VER PROYECTO");
                            System.out.println("El USUARIO" + usuarioLogueado.getNombre());
                            /*if(boton.equals("editar")){

                                System.out.println("VEEEEERRR");

                                System.out.println("El USUARIO" + usuarioLogueado.getNombre());
                                umtp=listUmtp;
                                System.out.println("LA UTMx AL LLEVARLA A VER  : "+ umtp.getUtmx());

                                argumentosEnviados= new Bundle();
                                argumentosEnviados.putSerializable("proyecto",proyectoSeleccionado);
                                argumentosEnviados.putSerializable("usuario",usuarioLogueado);
                                argumentosEnviados.putSerializable("umtp",umtp);
                                VerUmtpFragment verUmtp= new VerUmtpFragment();
                                verUmtp.setArguments(argumentosEnviados);
                                FragmentManager manager= getFragmentManager();
                                manager.beginTransaction().replace(R.id.contenidos, verUmtp).commit();
                            }*/
                        }
                    });
                    //webServiceCargarImagenes();
                }  catch (JSONException e) {
                    e.printStackTrace();
                }

                //etiNombre.setText(miUsuario.getNombre());//SE MODIFICA
                //etiProfesion.setText(miUsuario.getProfesion());//SE MODIFICA

                //String urlImagen=ip+"/ejemploBDRemota/"+miUsuario.getRutaImagen();
                //Toast.makeText(getContext(), "url "+urlImagen, Toast.LENGTH_LONG).show();
                //cargarWebServiceImagen(urlImagen);

                mRecyclerView.setAdapter(mAdapter);
                json=null;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "No se puede conectar "+error.toString(), Toast.LENGTH_LONG).show();
                System.out.println();
                //pDialog.hide();
                Log.d("ERROR: ", error.toString());
            }
        });

        // request.add(jsonObjectRequest);
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(jsonObjectRequest);
    }

    private void consultarImagenesPerfil() {
        /*final ProgressDialog pDialog;
        pDialog=new ProgressDialog(getContext());
        pDialog.setMessage("Cargando...");
        pDialog.show();*/

        //final String ip=getString(R.string.ip_url);

        //String url=ip+"web_services/buscar_imagenes_nivel_pozo.php?pozo_id="+idPozo;

        System.out.println(urlPerfil);

        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, urlPerfil, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //pDialog.hide();

                JSONArray json=response.optJSONArray("imagenes_perfil");
                JSONObject jsonObject;

                try {
                    jsonObject = json.getJSONObject(0);
                    if(jsonObject.getInt("imagen_perfil_expuesto_id")==0){
                        numeroImagenes=1;
                        imagenes = new Imagenes(0,0,"null","null");
                        listImagenes.add(imagenes);
                    }else {
                        numeroImagenes=json.length()+1;
                        for (int i = 0; i < json.length(); i++) {
                            jsonObject = json.getJSONObject(i);

                            imagenes =new Imagenes(jsonObject.getInt("imagen_perfil_expuesto_id"), jsonObject.getInt("perfiles_expuestos_perfil_expuesto_id"),
                                    jsonObject.getString("nombre"),jsonObject.getString("tipo"));
                            System.out.println("La imagene del material de la recoleccion es la  ???????????????????? : "+ imagenes.getId());
                            //proyecto.setNombre(jsonObject.optString("nombre"));
                            //miUsuario.setProfesion(jsonObject.optString("profesion"));
                            //miUsuario.setRutaImagen(jsonObject.optString("ruta_imagen"));
                            //System.out.println(jsonObject.getString("nombre"));
                            listImagenes.add(imagenes);

                        }
                    }
                    urlEnviada=getString(R.string.ip_url)+"recolectarq/proyecto/"+proyectoSeleccionado.getCodigo_identificacion()
                            +"/UMTP/UMTP"+umtpSeleccionada.getUmtp_id()+"/PE/PE"+perfilExpuestoSeleccionado.getPerfil_expuesto_id()+"/ImgPE/";
                    mAdapter=new AdapterImagenes(listImagenes, "imagenesPerfil",getContext(),urlEnviada,R.layout.recycler_view_imagenes, new AdapterImagenes.OnItemClickListener() {
                        @Override
                        public void onItemClick(Imagenes listImagenes, int position, String boton) {
                            System.out.println("DIOOOOO clic boton: " + boton+ " DESDE VER PROYECTO");
                            System.out.println("El USUARIO" + usuarioLogueado.getNombre());
                            /*if(boton.equals("editar")){

                                System.out.println("VEEEEERRR");

                                System.out.println("El USUARIO" + usuarioLogueado.getNombre());
                                umtp=listUmtp;
                                System.out.println("LA UTMx AL LLEVARLA A VER  : "+ umtp.getUtmx());

                                argumentosEnviados= new Bundle();
                                argumentosEnviados.putSerializable("proyecto",proyectoSeleccionado);
                                argumentosEnviados.putSerializable("usuario",usuarioLogueado);
                                argumentosEnviados.putSerializable("umtp",umtp);
                                VerUmtpFragment verUmtp= new VerUmtpFragment();
                                verUmtp.setArguments(argumentosEnviados);
                                FragmentManager manager= getFragmentManager();
                                manager.beginTransaction().replace(R.id.contenidos, verUmtp).commit();
                            }*/
                        }
                    });
                    //webServiceCargarImagenes();
                }  catch (JSONException e) {
                    e.printStackTrace();
                }

                //etiNombre.setText(miUsuario.getNombre());//SE MODIFICA
                //etiProfesion.setText(miUsuario.getProfesion());//SE MODIFICA

                //String urlImagen=ip+"/ejemploBDRemota/"+miUsuario.getRutaImagen();
                //Toast.makeText(getContext(), "url "+urlImagen, Toast.LENGTH_LONG).show();
                //cargarWebServiceImagen(urlImagen);

                mRecyclerView.setAdapter(mAdapter);
                json=null;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "No se puede conectar "+error.toString(), Toast.LENGTH_LONG).show();
                System.out.println();
                //pDialog.hide();
                Log.d("ERROR: ", error.toString());
            }
        });

        // request.add(jsonObjectRequest);
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(jsonObjectRequest);
    }


    private void consultarImagenesEstratoPerfil() {
        /*final ProgressDialog pDialog;
        pDialog=new ProgressDialog(getContext());
        pDialog.setMessage("Cargando...");
        pDialog.show();*/

        //final String ip=getString(R.string.ip_url);

        //String url=ip+"web_services/buscar_imagenes_nivel_pozo.php?pozo_id="+idPozo;

        System.out.println(urlEstratoPerfil);

        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, urlEstratoPerfil, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //pDialog.hide();

                JSONArray json=response.optJSONArray("imagenes_estrato_perfil");
                JSONObject jsonObject;

                try {
                    jsonObject = json.getJSONObject(0);
                    if(jsonObject.getInt("imagen_perfil_expuesto_id")==0){
                        numeroImagenes=1;
                        imagenes = new Imagenes(0,0,"null","null");
                        listImagenes.add(imagenes);
                    }else {
                        numeroImagenes=json.length()+1;
                        for (int i = 0; i < json.length(); i++) {
                            jsonObject = json.getJSONObject(i);

                            imagenes =new Imagenes(jsonObject.getInt("imagen_perfil_expuesto_id"), jsonObject.getInt("estratos_perfiles_estrato_perfil_id"),
                                    jsonObject.getString("nombre"),jsonObject.getString("tipo"));
                            System.out.println("La imagene del material de la recoleccion es la  ???????????????????? : "+ imagenes.getId());
                            //proyecto.setNombre(jsonObject.optString("nombre"));
                            //miUsuario.setProfesion(jsonObject.optString("profesion"));
                            //miUsuario.setRutaImagen(jsonObject.optString("ruta_imagen"));
                            //System.out.println(jsonObject.getString("nombre"));
                            listImagenes.add(imagenes);

                        }

                        System.out.println("LA LONGITUD DE LAS IMAGENES DE LOS ESTRATOS ES: " +json.length());
                    }
                    urlEnviada=getString(R.string.ip_url)+"recolectarq/proyecto/"+proyectoSeleccionado.getCodigo_identificacion()
                            +"/UMTP/UMTP"+umtpSeleccionada.getUmtp_id()+"/PE/PE"+perfilExpuestoSeleccionado.getPerfil_expuesto_id()+"/ImgPE/";
                    mAdapter=new AdapterImagenes(listImagenes, "imagenesEstratoPerfil",getContext(),urlEnviada,R.layout.recycler_view_imagenes, new AdapterImagenes.OnItemClickListener() {
                        @Override
                        public void onItemClick(Imagenes listImagenes, int position, String boton) {
                            System.out.println("DIOOOOO clic boton: " + boton+ " DESDE VER PROYECTO");
                            System.out.println("El USUARIO" + usuarioLogueado.getNombre());
                            /*if(boton.equals("editar")){

                                System.out.println("VEEEEERRR");

                                System.out.println("El USUARIO" + usuarioLogueado.getNombre());
                                umtp=listUmtp;
                                System.out.println("LA UTMx AL LLEVARLA A VER  : "+ umtp.getUtmx());

                                argumentosEnviados= new Bundle();
                                argumentosEnviados.putSerializable("proyecto",proyectoSeleccionado);
                                argumentosEnviados.putSerializable("usuario",usuarioLogueado);
                                argumentosEnviados.putSerializable("umtp",umtp);
                                VerUmtpFragment verUmtp= new VerUmtpFragment();
                                verUmtp.setArguments(argumentosEnviados);
                                FragmentManager manager= getFragmentManager();
                                manager.beginTransaction().replace(R.id.contenidos, verUmtp).commit();
                            }*/
                        }
                    });
                    //webServiceCargarImagenes();
                }  catch (JSONException e) {
                    e.printStackTrace();
                }

                //etiNombre.setText(miUsuario.getNombre());//SE MODIFICA
                //etiProfesion.setText(miUsuario.getProfesion());//SE MODIFICA

                //String urlImagen=ip+"/ejemploBDRemota/"+miUsuario.getRutaImagen();
                //Toast.makeText(getContext(), "url "+urlImagen, Toast.LENGTH_LONG).show();
                //cargarWebServiceImagen(urlImagen);

                mRecyclerView.setAdapter(mAdapter);
                json=null;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "No se puede conectar "+error.toString(), Toast.LENGTH_LONG).show();
                System.out.println();
               // pDialog.hide();
                Log.d("ERROR: ", error.toString());
            }
        });

        // request.add(jsonObjectRequest);
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(jsonObjectRequest);
    }
    private void webServiceCargarImagenes() {
        String urlImagen=getString(R.string.ip_url)+"recolectarq/proyecto/"+proyectoSeleccionado.getCodigo_identificacion()
                +"/UMTP/UMTP"+umtpSeleccionada.getUmtp_id()+"/"+listImagenes.get(0).getNombre();
        System.out.println("LA URL QUE SE CARAGARA ES LA: " +urlImagen);

        ImageRequest imageRequest=new ImageRequest(urlImagen, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {

                redimensionarImagen(response,300,200);

                imageViewImagenUmtp.setImageBitmap(bitmapRedim);
                response.recycle();
            }
        }, 0, 0, ImageView.ScaleType.CENTER, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),"Error al cargar la imagen",Toast.LENGTH_LONG).show();
            }
        });

        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(imageRequest);
    }


    @Override
    public void doBack() {

        FragmentManager manager= getFragmentManager();
        Bundle argumentosEnviados;
        switch(origen) {
            case "verUmtp":


                argumentosEnviados= new Bundle();
                argumentosEnviados.putSerializable("proyecto",proyectoSeleccionado);
                argumentosEnviados.putSerializable("usuario",usuarioLogueado);
                argumentosEnviados.putSerializable("umtp",umtpSeleccionada);
                VerUmtpFragment verUmtp= new VerUmtpFragment();
                verUmtp.setArguments(argumentosEnviados);
                manager.beginTransaction().replace(R.id.contenidos, verUmtp).commit();
                break;
            case "pozo":
                argumentosEnviados= new Bundle();
                argumentosEnviados.putSerializable("proyecto",proyectoSeleccionado);
                argumentosEnviados.putSerializable("usuario",usuarioLogueado);
                argumentosEnviados.putSerializable("umtp",umtpSeleccionada);
                IntervencionesFragment intervenciones= new IntervencionesFragment();
                intervenciones.setArguments(argumentosEnviados);
                manager.beginTransaction().replace(R.id.contenidos, intervenciones).commit();

                break;
            case "estratoPozo":
                argumentosEnviados= new Bundle();
                argumentosEnviados.putSerializable("proyecto",proyectoSeleccionado);
                argumentosEnviados.putSerializable("usuario",usuarioLogueado);
                argumentosEnviados.putSerializable("umtp",umtpSeleccionada);
                argumentosEnviados.putSerializable("pozo",pozoSeleccionado);

                VerPozoSondeoFragment verPozo= new VerPozoSondeoFragment();
                verPozo.setArguments(argumentosEnviados);
                //FragmentManager manager= getFragmentManager();
                manager.beginTransaction().replace(R.id.contenidos, verPozo).commit();
                break;
            case "nivelPozo":
                argumentosEnviados= new Bundle();
                argumentosEnviados.putSerializable("proyecto",proyectoSeleccionado);
                argumentosEnviados.putSerializable("usuario",usuarioLogueado);
                argumentosEnviados.putSerializable("umtp",umtpSeleccionada);
                argumentosEnviados.putSerializable("pozo",pozoSeleccionado);

                verPozo= new VerPozoSondeoFragment();
                verPozo.setArguments(argumentosEnviados);

                manager.beginTransaction().replace(R.id.contenidos, verPozo).commit();
                break;
            case "materialRecoleccion":

                argumentosEnviados= new Bundle();
                argumentosEnviados.putSerializable("proyecto",proyectoSeleccionado);
                argumentosEnviados.putSerializable("usuario",usuarioLogueado);
                argumentosEnviados.putSerializable("umtp",umtpSeleccionada);
                argumentosEnviados.putSerializable("recoleccion",recoleccionSeleccionado);

                VerRecoleccionSuperficialFragment verRecoleccion= new VerRecoleccionSuperficialFragment();
                verRecoleccion.setArguments(argumentosEnviados);

                manager.beginTransaction().replace(R.id.contenidos, verRecoleccion).commit();

                break;
            case "perfilExpuesto":
                argumentosEnviados= new Bundle();
                argumentosEnviados.putSerializable("proyecto",proyectoSeleccionado);
                argumentosEnviados.putSerializable("usuario",usuarioLogueado);
                argumentosEnviados.putSerializable("umtp",umtpSeleccionada);
                intervenciones= new IntervencionesFragment();
                intervenciones.setArguments(argumentosEnviados);
                manager.beginTransaction().replace(R.id.contenidos, intervenciones).commit();
                break;
            case "estratoPerfil":
                argumentosEnviados= new Bundle();
                argumentosEnviados.putSerializable("proyecto",proyectoSeleccionado);
                argumentosEnviados.putSerializable("usuario",usuarioLogueado);
                argumentosEnviados.putSerializable("umtp",umtpSeleccionada);
                argumentosEnviados.putSerializable("perfil",perfilExpuestoSeleccionado);

                VerPerfilExpuestoFragment verPerfil= new VerPerfilExpuestoFragment();
                verPerfil.setArguments(argumentosEnviados);

                manager.beginTransaction().replace(R.id.contenidos, verPerfil).commit();
                break;
            case "pozoEstructura":

                argumentosEnviados= new Bundle();
                argumentosEnviados.putSerializable("proyecto",proyectoSeleccionado);
                argumentosEnviados.putSerializable("usuario",usuarioLogueado);
                argumentosEnviados.putSerializable("umtp",umtpSeleccionada);
                argumentosEnviados.putSerializable("pozo",pozoSeleccionado);

                verPozo= new VerPozoSondeoFragment();
                verPozo.setArguments(argumentosEnviados);
                //FragmentManager manager= getFragmentManager();
                manager.beginTransaction().replace(R.id.contenidos, verPozo).commit();
                break;
            case "recoleccionEstructura":

                argumentosEnviados= new Bundle();
                argumentosEnviados.putSerializable("proyecto",proyectoSeleccionado);
                argumentosEnviados.putSerializable("usuario",usuarioLogueado);
                argumentosEnviados.putSerializable("umtp",umtpSeleccionada);
                argumentosEnviados.putSerializable("recoleccion",recoleccionSeleccionado);

                verRecoleccion= new VerRecoleccionSuperficialFragment();
                verRecoleccion.setArguments(argumentosEnviados);

                manager.beginTransaction().replace(R.id.contenidos, verRecoleccion).commit();
                break;
            case "perfilEstructura":

                argumentosEnviados= new Bundle();
                argumentosEnviados.putSerializable("proyecto",proyectoSeleccionado);
                argumentosEnviados.putSerializable("usuario",usuarioLogueado);
                argumentosEnviados.putSerializable("umtp",umtpSeleccionada);
                argumentosEnviados.putSerializable("perfil",perfilExpuestoSeleccionado);

                verPerfil= new VerPerfilExpuestoFragment();
                verPerfil.setArguments(argumentosEnviados);

                manager.beginTransaction().replace(R.id.contenidos, verPerfil).commit();

                break;
        }
    }
}
