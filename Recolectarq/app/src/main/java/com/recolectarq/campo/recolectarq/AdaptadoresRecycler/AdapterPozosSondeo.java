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

import com.recolectarq.campo.recolectarq.Modelo.PozosSondeo;
import com.recolectarq.campo.recolectarq.R;


import java.util.List;

public class AdapterPozosSondeo extends RecyclerView.Adapter<AdapterPozosSondeo.ViewHolder> {

    private List<PozosSondeo> listPozos;
    private  int layout;
    private OnItemClickListener itemClickListener;
    public AdapterPozosSondeo(List<PozosSondeo> listPozos, int layout, OnItemClickListener itemClickListener)
    {
        this.listPozos=listPozos;
        this.layout=layout;
        this.itemClickListener=itemClickListener;

    }

    @NonNull
    @Override
    //Se ha inflado la vista y se la pasa a la clase ViewHolder para extraer los componentes de la vista
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_pozos_sondeo, parent,false);
        ViewHolder vh=new ViewHolder(v);
        return vh;
    }

    @Override
    //Acá es donde se le configuran los datos a los objetos de la vista del layout creado
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int posicion) {
        viewHolder.bind(listPozos.get(posicion), itemClickListener);
        //viewHolder.setOnClickListeners();
    }

    //Número de items que se van a tener
    @Override
    public int getItemCount() {
        return listPozos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewPozoId;
        public TextView textViewPozoDescripcion;
        public TextView textViewPozoCodigoRotulo;
        public TextView textViewPozoEncargado;
        public ImageView imageViewPozoProyecto;
        public ImageButton imageButtonPozoEditar;
        public ImageButton imageButtonPozoImagenes;
        public ImageButton imageButtonPozoEliminar;
        public ImageButton imageButtonPozoCodigoRotulo;

        public Context contexto;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            contexto=itemView.getContext();
            this.textViewPozoId=itemView.findViewById(R.id.textViewPozoId);
            this.textViewPozoDescripcion=itemView.findViewById(R.id.textViewPozoDescripcion);
            this.textViewPozoCodigoRotulo=itemView.findViewById(R.id.textViewPozoCodigoRotulo);
            this.textViewPozoEncargado=itemView.findViewById(R.id.textViewPozoEncargado);
            this.imageViewPozoProyecto=itemView.findViewById(R.id.imageViewPozoProyecto);
            this.imageButtonPozoEditar=itemView.findViewById(R.id.imageButtonPozoEditar);
            this.imageButtonPozoImagenes=itemView.findViewById(R.id.imageButtonPozoImagenes);
            this.imageButtonPozoEliminar=itemView.findViewById(R.id.imageButtonPozoEliminar);
            this.imageButtonPozoCodigoRotulo=itemView.findViewById(R.id.imageButtonPozoCodigoRotulo);
        }

        public void bind(final PozosSondeo listPozos, final OnItemClickListener onItemClickListener){
            this.textViewPozoId.setText("Pozo Id: "+String.valueOf(listPozos.getPozo_id()));
            this.textViewPozoDescripcion.setText("Descripción: "+String.valueOf(listPozos.getDescripcion()));
            this.textViewPozoCodigoRotulo.setText("Código Rótulo: "+String.valueOf(listPozos.getCodigo_rotulo()));
            this.textViewPozoEncargado.setText("Usuario Encargado: "+String.valueOf(listPozos.getUsuario_encargado()));
            this.imageViewPozoProyecto.setImageResource(R.drawable.add_24dp);


            if(listPozos.getPozo_id()==0)
            {
                imageButtonPozoEditar.setEnabled(false);
                imageButtonPozoImagenes.setEnabled(false);
                imageButtonPozoEliminar.setEnabled(false);
                imageButtonPozoCodigoRotulo.setEnabled(false);
            }


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   onItemClickListener.onItemClick(listPozos, getAdapterPosition(), "reciclador");
                }
            });

            /*imageButtonEliminarPozo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(listPozos, getAdapterPosition(), "eliminar");
                }
            });*/

            imageButtonPozoEditar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(listPozos, getAdapterPosition(), "Ver");

                }
            });

            imageButtonPozoImagenes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(listPozos, getAdapterPosition(), "Imagenes");

                }
            });
            imageButtonPozoCodigoRotulo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(listPozos, getAdapterPosition(), "CodigoRotulo");

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
        void onItemClick(PozosSondeo listPozos, int position, String boton);
    }


}
