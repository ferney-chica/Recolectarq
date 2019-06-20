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

import com.recolectarq.campo.recolectarq.Modelo.RecoleccionesSuperficiales;
import com.recolectarq.campo.recolectarq.R;

import java.util.List;

public class AdapterRecoleccionesSuperficiales extends RecyclerView.Adapter<AdapterRecoleccionesSuperficiales.ViewHolder> {

    private List<RecoleccionesSuperficiales> listRecolecciones;
    private  int layout;
    private OnItemClickListener itemClickListener;
    public AdapterRecoleccionesSuperficiales(List<RecoleccionesSuperficiales> listRecolecciones, int layout, OnItemClickListener itemClickListener)
    {
        this.listRecolecciones=listRecolecciones;
        this.layout=layout;
        this.itemClickListener=itemClickListener;

    }

    @NonNull
    @Override
    //Se ha inflado la vista y se la pasa a la clase ViewHolder para extraer los componentes de la vista
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_recolecciones_superficiales, parent,false);
        ViewHolder vh=new ViewHolder(v);
        return vh;
    }

    @Override
    //Acá es donde se le configuran los datos a los objetos de la vista del layout creado
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int posicion) {
        viewHolder.bind(listRecolecciones.get(posicion), itemClickListener);
        //viewHolder.setOnClickListeners();
    }

    //Número de items que se van a tener
    @Override
    public int getItemCount() {
        return listRecolecciones.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewRecoleccionId;
        public TextView textViewRecoleccionDescripcion;
        public TextView textViewRecoleccionEncargado;
        public TextView textViewRecoleccionCodigoRotulo;
        public ImageView imageViewRecoleccion;
        public ImageButton imageButtonRecoleccionVer;
        public ImageButton imageButtonRecoleccionCodigoRotulo;
        public Context contexto;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            contexto=itemView.getContext();
            this.textViewRecoleccionId=itemView.findViewById(R.id.textViewRecoleccionId);
            this.textViewRecoleccionDescripcion=itemView.findViewById(R.id.textViewRecoleccionDescripcion);
            this.textViewRecoleccionEncargado=itemView.findViewById(R.id.textViewRecoleccionEncargado);
            this.textViewRecoleccionCodigoRotulo=itemView.findViewById(R.id.textViewRecoleccionCodigoRotulo);
            this.imageViewRecoleccion=itemView.findViewById(R.id.imageViewRecoleccion);
            this.imageButtonRecoleccionVer=itemView.findViewById(R.id.imageButtonRecoleccionVer);
            this.imageButtonRecoleccionCodigoRotulo=itemView.findViewById(R.id.imageButtonRecoleccionCodigoRotulo);
        //this.imageButtonVerPozo=itemView.findViewById(R.id.imageButtonVerPozo);
        }

        public void bind(final RecoleccionesSuperficiales listRecolecciones, final OnItemClickListener onItemClickListener){
            this.textViewRecoleccionId.setText("Recolección Id: "+String.valueOf(listRecolecciones.getrecoleccion_superficial_id()));
            this.textViewRecoleccionDescripcion.setText("Descripción: "+String.valueOf(listRecolecciones.getDescripcion()));
            this.textViewRecoleccionEncargado.setText("Encargado: "+String.valueOf(listRecolecciones.getUsuario_encargado()));
            this.textViewRecoleccionCodigoRotulo.setText("Código Rótulo: "+String.valueOf(listRecolecciones.getCodigo_rotulo()));

            if(listRecolecciones.getrecoleccion_superficial_id()==0)
            {
                imageButtonRecoleccionVer.setEnabled(false);
                imageButtonRecoleccionCodigoRotulo.setEnabled(false);
            }


            this.imageViewRecoleccion.setImageResource(R.drawable.add_24dp);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   onItemClickListener.onItemClick(listRecolecciones, getAdapterPosition(), "reciclador");
                }
            });

            /*imageButtonEliminarPozo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(listPozos, getAdapterPosition(), "eliminar");
                }
            });*/

            imageButtonRecoleccionVer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(listRecolecciones, getAdapterPosition(), "Ver");
                }
            });
            imageButtonRecoleccionCodigoRotulo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(listRecolecciones, getAdapterPosition(), "CodigoRotulo");
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
        void onItemClick(RecoleccionesSuperficiales listRecolecciones, int position, String boton);
    }


}
