package com.recolectarq.campo.recolectarq.Fragmentos.Usuarios;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.recolectarq.campo.recolectarq.R;
import com.recolectarq.campo.recolectarq.Modelo.Usuarios;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CrearUsuarioFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener {
    private EditText cedula;
    private EditText nombre;
    private EditText apellido;
    private TextView usuario;
    private TextView usuarioExistente;
    private Button btnCrearUsuario;
    private RequestQueue request;
    private JsonRequest jrq;
    private ArrayList<Usuarios> listaUsuarios;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       // return super.onCreateView(inflater, container, savedInstanceState);
        final View v= inflater.inflate(R.layout.fragment_crear_usuario,container, false);

        cedula= v.findViewById(R.id.editTextNombreProyecto);
        nombre= v.findViewById(R.id.editTextNombres);
        apellido=v.findViewById(R.id.editTextApellido);
        usuario= v.findViewById(R.id.textViewApellidos);
        usuarioExistente=v.findViewById(R.id.textViewUsuarioExistente);
        btnCrearUsuario=v.findViewById(R.id.buttonCrearUsuario);
        cedula.requestFocus();
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

        btnCrearUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int cedulaU=Integer.parseInt(cedula.getText().toString());
                String nombreU=nombre.getText().toString();
                String apellidoU=apellido.getText().toString();
                String contrasenaU=cedula.getText().toString();


                //Toast.makeText(LogueoActivity.this, "Mi primer boton", Toast.LENGTH_LONG).show();
                cargarWebServiceCrearUsuario(Integer.parseInt(cedula.getText().toString()), nombre.getText().toString(), apellido.getText().toString(), cedula.getText().toString());
                UsuariosFragment usuarios= new UsuariosFragment();
                FragmentManager manager= getFragmentManager();
                manager.beginTransaction().replace(R.id.contenidos, usuarios).commit();
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
}
