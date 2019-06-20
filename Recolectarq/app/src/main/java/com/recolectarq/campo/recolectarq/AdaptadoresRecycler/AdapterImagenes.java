package com.recolectarq.campo.recolectarq.AdaptadoresRecycler;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.recolectarq.campo.recolectarq.Modelo.Imagenes;
import com.recolectarq.campo.recolectarq.Modelo.ImagenesUmtp;
import com.recolectarq.campo.recolectarq.Modelo.Umtp;
import com.recolectarq.campo.recolectarq.R;
import com.recolectarq.campo.recolectarq.VolleySingleton;

import java.util.List;

public class AdapterImagenes extends RecyclerView.Adapter<AdapterImagenes.ViewHolder> {

    private List<Imagenes> listImagenes;
    private  int layout;
    Context contextoEnviado;
    String urlEnviada;
    Bitmap imagen2;
    String origen1;

    private OnItemClickListener itemClickListener;
    public AdapterImagenes(List<Imagenes> listImagenes, String origen, Context contexto, String urlImagen, int layout, OnItemClickListener itemClickListener)
    {
        this.listImagenes=listImagenes;
        this.layout=layout;
        this.itemClickListener=itemClickListener;
        contextoEnviado=contexto;
        urlEnviada=urlImagen;
        contextoEnviado=contexto;
        origen1=origen;
    }

    @NonNull
    @Override
    //Se ha inflado la vista y se la pasa a la clase ViewHolder para extraer los componentes de la vista
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_imagenes, parent,false);
        ViewHolder vh=new ViewHolder(v);
        return vh;
    }

    @Override
    //Acá es donde se le configuran los datos a los objetos de la vista del layout creado
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int posicion) {
        viewHolder.bind(listImagenes.get(posicion), itemClickListener, viewHolder);
        //viewHolder.setOnClickListeners();
    }

    //Número de items que se van a tener
    @Override
    public int getItemCount() {
        return listImagenes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewImagenId;
        public TextView textViewImagenIdIntervencion;
        public TextView textViewImagenNombre;
        public ImageButton btnEliminarUmtp;
        public ImageButton btnEditarUmtp;
        public ImageView imageViewImagenes;
        public Context contexto;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            contexto=itemView.getContext();
            this.textViewImagenId=itemView.findViewById(R.id.textViewImagenId);
            this.textViewImagenIdIntervencion=itemView.findViewById(R.id.textViewImagenIdIntervencion);
            this.textViewImagenNombre=itemView.findViewById(R.id.textViewImagenNombre);

            this.imageViewImagenes=itemView.findViewById(R.id.imageViewImagenes);

            /*this.btnEliminarUmtp=itemView.findViewById(R.id.imageButtonEliminarUmtp);
            this.btnEditarUmtp=itemView.findViewById(R.id.imageButtonEditarUmtp);*/
        }

        public void bind(final Imagenes listImagenes, final OnItemClickListener onItemClickListener, ViewHolder viewHolder){


            System.out.print("VOY POR ACA ÑÑÑÑÑÑÑÑÑÑÑÑÑÑÑÑÑÑÑÑÑ: " + listImagenes.getId());
            if (listImagenes.getId()==0){
                switch (origen1)
                {
                    case "imagenesUmtp":
                        this.textViewImagenId.setText("No hay imágenes de UMTP para mostrar");
                        this.imageViewImagenes.setImageResource(R.drawable.common_full_open_on_phone);
                        break;
                    case "imagenesPozo":
                        this.textViewImagenId.setText("No hay imágenes del Pozo para mostrar");
                        this.imageViewImagenes.setImageResource(R.drawable.common_full_open_on_phone);
                        break;
                    case "imagenesNivelPozo":
                        this.textViewImagenId.setText("No hay imágenes del nivel del Pozo para mostrar");
                        this.imageViewImagenes.setImageResource(R.drawable.common_full_open_on_phone);
                        break;
                    case "imagenesMaterialRecoleccion":
                        this.textViewImagenId.setText("No hay imágenes del material de la recolección para mostrar");
                        this.imageViewImagenes.setImageResource(R.drawable.common_full_open_on_phone);
                        break;
                    case "imagenesPerfil":
                        this.textViewImagenId.setText("No hay imágenes del perfil para mostrar");
                        this.imageViewImagenes.setImageResource(R.drawable.common_full_open_on_phone);
                        break;
                    case "imagenesEstratoPerfil":
                        this.textViewImagenId.setText("No hay imágenes del estrato del perfil para mostrar");
                        this.imageViewImagenes.setImageResource(R.drawable.common_full_open_on_phone);
                        break;
                    case "imagenesEstructura":
                        this.textViewImagenId.setText("No hay imágenes de la estructura para mostrar");
                        this.imageViewImagenes.setImageResource(R.drawable.common_full_open_on_phone);
                        break;
                }


            }else{

                switch (origen1)
                {
                    case "imagenesUmtp":
                        this.textViewImagenIdIntervencion.setText("Id UMTP: "+ listImagenes.getIntervencion_id()+"");
                        break;
                    case "imagenesPozo":
                        this.textViewImagenIdIntervencion.setText("Id Pozo: "+ listImagenes.getIntervencion_id()+"");
                        break;
                    case "imagenesNivelPozo":
                        this.textViewImagenIdIntervencion.setText("Id Nivel Pozo: "+ listImagenes.getIntervencion_id()+"");
                        break;
                    case "imagenesMaterialRecoleccion":
                        this.textViewImagenIdIntervencion.setText("Id Material Recolección: "+ listImagenes.getIntervencion_id()+"");
                        break;

                    case "imagenesPerfil":
                        this.textViewImagenIdIntervencion.setText("Id Perfil Expuesto: "+ listImagenes.getIntervencion_id()+"");
                        break;

                    case "imagenesEstratoPerfil":
                        this.textViewImagenIdIntervencion.setText("Id Estrato Perfil Expuesto: "+ listImagenes.getIntervencion_id()+"");
                        break;
                    case "imagenesEstructura":
                        this.textViewImagenIdIntervencion.setText("Id Estructura: "+ listImagenes.getIntervencion_id()+"");
                        break;
                }
                System.out.print("LA IMAGEN CON ID: " +listImagenes.getId());
                this.textViewImagenId.setText("Imagen Id: "+ listImagenes.getId()+"");
                //this.textViewImagenIdIntervencion.setText("Id UMTP: "+ listImagenes.getIntervencion_id()+"");
                this.textViewImagenNombre.setText("Nombre Imagen: "+ listImagenes.getNombre()+"");
                //this.imageViewImagenes.setImageBitmap(listImagenesUmtp.getImagen());
                webServiceCargarImagenes(viewHolder, urlEnviada+listImagenes.getNombre());
                //imageViewImagenes.setImageBitmap(imagen2);
                //this.imageViewImagenes.setImageResource(R.drawable.common_full_open_on_phone);

            }


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   onItemClickListener.onItemClick(listImagenes, getAdapterPosition(), "reciclador");
                }
            });

            /*btnEliminarUmtp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(listUmtp, getAdapterPosition(), "eliminar");
                }
            });

            btnEditarUmtp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(listUmtp, getAdapterPosition(), "editar");
                }
            });*/
        }

        /*void setOnClickListeners(){
          //btnEliminarUsuario.setOnClickListener(this);
            btnEditarUsuario.setOnClickListener(this);
        }*/

        /*
        @Override
        public void onClick(View v) {
            String a=textViewName.getText().toString();
            switch (v.getId())
            {
                /*case R.id.imageButtonEliminarUsuario:


                    System.out.println("Clic boton eliminar a: "+a);
                    break;*/
               /* case R.id.imageButtonEditarUsuario:
                    System.out.println("Clic boton editar a: "+a);
                    EditarUsuarioFragment editarUsuarioFragment= new EditarUsuarioFragment();

                    manager.beginTransaction().replace(R.id.contenidos, editarUsuarioFragment).commit();
                    break;


            }
        }*/
    }

    public  interface OnItemClickListener{
        void onItemClick(Imagenes listImagenes, int position, String boton);
    }


    private void webServiceCargarImagenes(final ViewHolder viewHolder, String url) {
        final String urlCargar=url;
        System.out.println("LA URL QUE SE CARAGARA ES LA: " +urlCargar);

        ImageRequest imageRequest=new ImageRequest(urlCargar, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {

                //redimensionarImagen(response,300,200);
                viewHolder.imageViewImagenes.setImageBitmap(response);
                //imageView2Ferney.setImageBitmap(bitmapRedim);
                //response.recycle();
            }
        }, 0, 0, ImageView.ScaleType.CENTER, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(contextoEnviado,"Error al cargar la imagen",Toast.LENGTH_LONG).show();
            }
        });

        VolleySingleton.getIntanciaVolley(contextoEnviado).addToRequestQueue(imageRequest);
    }

}
