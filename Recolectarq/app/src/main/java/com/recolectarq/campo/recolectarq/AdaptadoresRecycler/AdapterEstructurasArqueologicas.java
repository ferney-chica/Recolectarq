package com.recolectarq.campo.recolectarq.AdaptadoresRecycler;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.recolectarq.campo.recolectarq.Modelo.EstructurasArqueologicas;
import com.recolectarq.campo.recolectarq.Modelo.PozosSondeo;
import com.recolectarq.campo.recolectarq.R;

import java.util.List;

public class AdapterEstructurasArqueologicas extends RecyclerView.Adapter<AdapterEstructurasArqueologicas.ViewHolder> {

    private List<EstructurasArqueologicas> listEstructurasArqueologicas;
    private  int layout;
    private String origen1;

    private OnItemClickListener itemClickListener;
    public AdapterEstructurasArqueologicas(String origen,List<EstructurasArqueologicas> listEstructurasArqueologicas, int layout, OnItemClickListener itemClickListener)
    {
        this.listEstructurasArqueologicas=listEstructurasArqueologicas;
        this.layout=layout;
        this.itemClickListener=itemClickListener;
        origen1=origen;
    }

    @NonNull
    @Override
    //Se ha inflado la vista y se la pasa a la clase ViewHolder para extraer los componentes de la vista
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_estructuras_arqueologicas, parent,false);
        ViewHolder vh=new ViewHolder(v);
        return vh;
    }

    @Override
    //Acá es donde se le configuran los datos a los objetos de la vista del layout creado
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int posicion) {
        viewHolder.bind(listEstructurasArqueologicas.get(posicion), itemClickListener);
        //viewHolder.setOnClickListeners();
    }

    //Número de items que se van a tener
    @Override
    public int getItemCount() {
        return listEstructurasArqueologicas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewEstructuraArqueologicaId;
        public TextView textViewEstructuraArqueologicaIdIntervencion;
        public TextView textViewEstructuraArqueologicaTipoEstructura;
        public TextView textViewEstructuraArqueologicaPuntoConexo;
        public TextView textViewEstructuraArqueologicaEntorno;
        public TextView textViewEstructuraArqueologicaDescripcion;
        public ImageView imageViewEstructuraArqueologica;
        public ImageButton imageButtonVerEstructuraArqueologica;
        public ImageButton imageButtonEliminarEstructuraArqueologica;
        public ImageButton imageButtonEstructuraArqueologicaImagenes;
        public ImageButton imageButtonEstructuraArqueologicaMateriales;
        public Context contexto;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            contexto=itemView.getContext();
            this.textViewEstructuraArqueologicaId=itemView.findViewById(R.id.textViewEstructuraArqueologicaId);
            this.textViewEstructuraArqueologicaIdIntervencion=itemView.findViewById(R.id.textViewEstructuraArqueologicaIdIntervencion);
            this.textViewEstructuraArqueologicaTipoEstructura=itemView.findViewById(R.id.textViewEstructuraArqueologicaTipoEstructura);
            this.textViewEstructuraArqueologicaPuntoConexo=itemView.findViewById(R.id.textViewEstructuraArqueologicaPuntoConexo);
            this.textViewEstructuraArqueologicaEntorno=itemView.findViewById(R.id.textViewEstructuraArqueologicaEntorno);
            this.textViewEstructuraArqueologicaDescripcion=itemView.findViewById(R.id.textViewEstructuraArqueologicaDescripcion);
            this.imageViewEstructuraArqueologica=itemView.findViewById(R.id.imageViewEstructuraArqueologica);
            this.imageButtonVerEstructuraArqueologica=itemView.findViewById(R.id.imageButtonVerEstructuraArqueologica);
            this.imageButtonEliminarEstructuraArqueologica=itemView.findViewById(R.id.imageButtonEliminarEstructuraArqueologica);
            this.imageButtonEstructuraArqueologicaImagenes=itemView.findViewById(R.id.imageButtonEstructuraArqueologicaImagenes);
            this.imageButtonEstructuraArqueologicaMateriales=itemView.findViewById(R.id.imageButtonEstructuraArqueologicaMateriales);
        }

        public void bind(final EstructurasArqueologicas listEstructurasArqueologicas, final OnItemClickListener onItemClickListener){

            switch(origen1)
            {
                case "pozo":
                    if(listEstructurasArqueologicas.getEstructuras_arqueologicas_id()==0)
                    {
                        this.textViewEstructuraArqueologicaIdIntervencion.setText("Id Pozo: "+0);

                        imageButtonVerEstructuraArqueologica.setEnabled(false);
                        imageButtonEliminarEstructuraArqueologica.setEnabled(false);
                        imageButtonEstructuraArqueologicaImagenes.setEnabled(false);
                        imageButtonEstructuraArqueologicaMateriales.setEnabled(false);
                    }else
                    {
                        this.textViewEstructuraArqueologicaIdIntervencion.setText("Id Pozo: "+String.valueOf(listEstructurasArqueologicas.getPozos_pozo_id()));
                    }

                    break;
                case "recoleccion":
                    if(listEstructurasArqueologicas.getEstructuras_arqueologicas_id()==0)
                    {
                        this.textViewEstructuraArqueologicaIdIntervencion.setText("Id Recoleccion: "+0);

                        imageButtonVerEstructuraArqueologica.setEnabled(false);
                        imageButtonEliminarEstructuraArqueologica.setEnabled(false);
                        imageButtonEstructuraArqueologicaImagenes.setEnabled(false);
                        imageButtonEstructuraArqueologicaMateriales.setEnabled(false);
                    }else
                    {
                        this.textViewEstructuraArqueologicaIdIntervencion.setText("Id Recoleccion: "+String.valueOf(listEstructurasArqueologicas.getRecolecciones_superficiales_recoleccion_superficial_id()));
                    }
                    break;

                case "perfil":
                    if(listEstructurasArqueologicas.getEstructuras_arqueologicas_id()==0)
                    {
                        this.textViewEstructuraArqueologicaIdIntervencion.setText("Id Perfil: "+0);

                        imageButtonVerEstructuraArqueologica.setEnabled(false);
                        imageButtonEliminarEstructuraArqueologica.setEnabled(false);
                        imageButtonEstructuraArqueologicaImagenes.setEnabled(false);
                        imageButtonEstructuraArqueologicaMateriales.setEnabled(false);
                    }else
                    {
                        this.textViewEstructuraArqueologicaIdIntervencion.setText("Id Perfil: "+String.valueOf(listEstructurasArqueologicas.getPerfiles_expuestos_perfil_expuesto_id()));
                    }
                    break;
            }

            this.textViewEstructuraArqueologicaId.setText("Id Estructura: "+String.valueOf(listEstructurasArqueologicas.getEstructuras_arqueologicas_id()));
            this.textViewEstructuraArqueologicaTipoEstructura.setText("Tipo Estructura: "+String.valueOf(listEstructurasArqueologicas.getTipos_estructuras_nombre()));
            this.textViewEstructuraArqueologicaPuntoConexo.setText("Punto Conexo: "+String.valueOf(listEstructurasArqueologicas.getPunto_conexo()));
            this.textViewEstructuraArqueologicaEntorno.setText("Entorno: "+String.valueOf(listEstructurasArqueologicas.getEntorno()));
            this.textViewEstructuraArqueologicaDescripcion.setText("Descripción: "+String.valueOf(listEstructurasArqueologicas.getDescripcion()));
            this.imageViewEstructuraArqueologica.setImageResource(R.drawable.add_24dp);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   onItemClickListener.onItemClick(listEstructurasArqueologicas, getAdapterPosition(), "reciclador");
                }
            });

            /*imageButtonEliminarPozo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(listPozos, getAdapterPosition(), "eliminar");
                }
            });*/

            imageButtonVerEstructuraArqueologica.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(listEstructurasArqueologicas, getAdapterPosition(), "Ver");

                }
            });

            imageButtonEstructuraArqueologicaImagenes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(listEstructurasArqueologicas, getAdapterPosition(), "Imagenes");

                }
            });

            imageButtonEstructuraArqueologicaMateriales.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(listEstructurasArqueologicas, getAdapterPosition(), "Materiales");

                }
            });
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
        void onItemClick(EstructurasArqueologicas listEstructurasArqueologicas, int position, String boton);
    }


}
