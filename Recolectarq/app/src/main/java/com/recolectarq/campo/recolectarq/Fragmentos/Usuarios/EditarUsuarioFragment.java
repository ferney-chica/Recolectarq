package com.recolectarq.campo.recolectarq.Fragmentos.Usuarios;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import com.android.volley.toolbox.Volley;
import com.recolectarq.campo.recolectarq.R;
import com.recolectarq.campo.recolectarq.Modelo.Usuarios;
import com.recolectarq.campo.recolectarq.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EditarUsuarioFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener {
    private EditText cedula;
    private EditText nombre;
    private EditText apellido;
    private TextView usuario;
    private TextView usuarioExistente;
    private TextView textViewEditarUsuario;
    private Button btnEditarUsuario;
    private RequestQueue request;
    private JsonRequest jrq;
    private ArrayList<Usuarios> listaUsuarios;


    private Usuarios usuarioLogueado= null;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments()!=null)
        {
            Toast.makeText(getContext(),"entro",Toast.LENGTH_LONG);
            Toast.makeText(getContext(),"entro",Toast.LENGTH_LONG);
            usuarioLogueado=(Usuarios) getArguments().getSerializable("usuario");

        }else{

            Toast.makeText(getContext(),"NO entroOOOOOOOO",Toast.LENGTH_LONG);
            Toast.makeText(getContext(),"NO entroOOOOOOOO",Toast.LENGTH_LONG);
            Toast.makeText(getContext(),"NO entroOOOOOOOO",Toast.LENGTH_LONG);
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       // return super.onCreateView(inflater, container, savedInstanceState);
        final View v= inflater.inflate(R.layout.fragment_editar_usuario,container, false);

        cedula= v.findViewById(R.id.editTextNombreProyecto);
        nombre= v.findViewById(R.id.editTextNombres);
        apellido=v.findViewById(R.id.editTextApellido);
        usuario= v.findViewById(R.id.textViewApellidos);
        usuarioExistente=v.findViewById(R.id.textViewUsuarioExistente);
        btnEditarUsuario=v.findViewById(R.id.buttonEditarUsuario);
        textViewEditarUsuario=v.findViewById(R.id.imageButtonEditarUmtp);
        cedula.setText(usuarioLogueado.getUsuario_id());
        nombre.setText(usuarioLogueado.getNombre());
        apellido.setText(usuarioLogueado.getApellido());
        System.out.println("  EL apellido es:  "+usuarioLogueado.getApellido());
        cedula.requestFocus();
        cedula.setEnabled(false);
        cedula.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        cedula.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                System.out.println("cambio el foco de cedula");
                String s= cedula.getText().toString();

                if (s.toString().isEmpty())
                {
                    usuarioExistente.setText("");
                    usuarioExistente.setVisibility(View.INVISIBLE);
                }else {
                    //usuario.setText(s);
                    listaUsuarios = new ArrayList<>();
                    request = Volley.newRequestQueue(v.getContext());

                    cargarWebServiceConsultarUsuario(s.toString());
                    System.out.println("entro " + s);
                }
            }
        });

        btnEditarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int cedulaU=Integer.parseInt(cedula.getText().toString());
                String nombreU=nombre.getText().toString();
                String apellidoU=apellido.getText().toString();
                String contrasenaU=cedula.getText().toString();


                //Toast.makeText(LogueoActivity.this, "Mi primer boton", Toast.LENGTH_LONG).show();
                //cargarWebServiceCrearUsuario(Integer.parseInt(cedula.getText().toString()), nombre.getText().toString(), apellido.getText().toString(), cedula.getText().toString());
                //UsuariosFragment usuarios= new UsuariosFragment();
                //FragmentManager manager= getFragmentManager();
                //manager.beginTransaction().replace(R.id.contenidos, usuarios).commit();
                Toast.makeText(getContext(),"clic en boton actualizar", Toast.LENGTH_LONG);
                System.out.println("Clic botoòn actualizar");
                webServiceActualizar();

            }
        });




        return v;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        System.out.println(error.getMessage());
    }

    @Override
    public void onResponse(JSONObject response) {

        Usuarios usuario;
        //Toast.makeText(getContext(),"entro al servicio",Toast.LENGTH_LONG);


        System.out.println("el nombre del response es "+response.names());

        String a=response.names().toString();

        System.out.println("el NOMBRE del response es "+ a);
        System.out.println(a.replaceAll("[^\\dA-Za-z]", ""));
        a=a.replaceAll("[^\\dA-Za-z]", "");
        if(a.equals("datos")) {

            JSONArray json=response.optJSONArray("datos");
            try {
                System.out.println("la longitud de la cadena es: " + json.length());


                for (int i = 0; i < json.length(); i++) {

                    JSONObject jsonObject;
                    jsonObject = json.getJSONObject(i);
                    usuario = new Usuarios(jsonObject.optString("usuario_id"), jsonObject.optString("nombre"), "", jsonObject.optString("contrasena"));
                    usuario.setUsuario_id(jsonObject.optString("usuario_id"));
                    usuario.setNombre(jsonObject.optString("nombre"));

                    listaUsuarios.add(usuario);
                }
                cedula.findViewById(R.id.editTextNombreProyecto);

                System.out.println(listaUsuarios.get(0).getNombre() + "  " + cedula.getText() + " longitud del campo de texto" + cedula.getText().length());
                if(listaUsuarios.get(0).getNombre().equals("null"))
                {
                        usuarioExistente.setText("");
                        usuarioExistente.setVisibility(View.INVISIBLE);
                }else
                {
                    usuarioExistente.setText("El usuario con cédula " + cedula.getText()+" ya esiste" );
                    usuarioExistente.setVisibility(View.VISIBLE);

                }

            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(getContext(), "No entro al servicio" + e.getMessage(), Toast.LENGTH_LONG);
            }

            System.out.println("Entro a datos");
        }else{
            System.out.println("Entro a"+a);
            JSONArray json=response.optJSONArray("usuario_registrado");
        }
    }

    private void cargarWebServiceConsultarUsuario(String s)
    {
        String url =getString(R.string.ip_url)+"web_services/buscar_usuario.php?user="+s;
        System.out.println(url);
        listaUsuarios.clear();
        jrq=new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        request.add(jrq);

    }

    private void cargarWebServiceCrearUsuario(int cedula, String nombre, String apellido, String contrasena)
    {
        String url =getString(R.string.ip_url)+"web_services/insertar_usuario.php?cedula="+ cedula+"&nombre="+nombre.replace(" ","%20")+"&apellido="+apellido.replace(" ","%20")+"&contrasena="+contrasena;

        System.out.println(url);
        jrq=new JsonObjectRequest(Request.Method.GET, url, null, this, this);

        request.add(jrq);

    }





    private void webServiceActualizar() {
        final ProgressDialog pDialog=new ProgressDialog(getContext());
        pDialog.setMessage("Cargando...");
        pDialog.show();

        String ip=getString(R.string.ip_url);

        String url=ip+"/web_services/editar_usuario.php?";

        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pDialog.hide();

                if (response.trim().equalsIgnoreCase("actualiza")){
                    // etiNombre.setText("");
                    //  txtDocumento.setText("");
                    //   etiProfesion.setText("");
                    Toast.makeText(getContext(),"Se ha Actualizado con exito",Toast.LENGTH_SHORT).show();

                    UsuariosFragment usuarios= new UsuariosFragment();
                    FragmentManager manager= getFragmentManager();
                    manager.beginTransaction().replace(R.id.contenidos, usuarios).commit();

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
                pDialog.hide();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String cedulaU=cedula.getText().toString();
                String nombreU=nombre.getText().toString();
                String apellidoU=apellido.getText().toString();

                //String imagen=convertirImgString(bitmap);

                Map<String,String> parametros=new HashMap<>();
                parametros.put("documento",cedulaU);
                parametros.put("nombre",nombreU);
                parametros.put("apellido",apellidoU);
                //parametros.put("imagen",imagen);

                return parametros;
            }
        };
        //request.add(stringRequest);
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(stringRequest);
    }



}
