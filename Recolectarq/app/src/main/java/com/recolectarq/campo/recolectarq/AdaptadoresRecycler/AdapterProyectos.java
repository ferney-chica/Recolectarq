package com.recolectarq.campo.recolectarq.AdaptadoresRecycler;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.recolectarq.campo.recolectarq.Modelo.Proyectos;
import com.recolectarq.campo.recolectarq.R;

import java.util.List;

public class AdapterProyectos extends RecyclerView.Adapter<AdapterProyectos.ViewHolder> {

    private List<Proyectos> listProyectos;
    private  int layout;
    public int idPerfil;
    public ConstraintLayout constraintLayoutProyecto;

    private OnItemClickListener itemClickListener;
    public AdapterProyectos(List<Proyectos> listProyectos, int layout, OnItemClickListener itemClickListener)
    {
        this.listProyectos=listProyectos;
        this.layout=layout;
        this.itemClickListener=itemClickListener;

    }

    @NonNull
    @Override
    //Se ha inflado la vista y se la pasa a la clase ViewHolder para extraer los componentes de la vista
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_proyecto, parent,false);
        ViewHolder vh=new ViewHolder(v);
        return vh;
    }

    @Override
    //Acá es donde se le configuran los datos a los objetos de la vista del layout creado
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int posicion) {
        viewHolder.bind(listProyectos.get(posicion), itemClickListener);
        //viewHolder.setOnClickListeners();
    }

    //Número de items que se van a tener
    @Override
    public int getItemCount() {
        return listProyectos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewProyectoNombreProyecto;
        public TextView textViewProyectoPerfilUsuario;
        public ImageView imageViewProyecto;
        public ImageButton imageButtonProyectoVer;
        public ImageButton imageButtonProyectoEliminar;

        public Context contexto;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            contexto=itemView.getContext();
            this.textViewProyectoNombreProyecto=itemView.findViewById(R.id.textViewProyectoNombreProyecto);
            this.textViewProyectoPerfilUsuario=itemView.findViewById(R.id.textViewProyectoPerfilUsuario);
            this.imageViewProyecto=itemView.findViewById(R.id.imageViewProyecto);
            constraintLayoutProyecto=itemView.findViewById(R.id.constraintLayoutProyecto);

            this.imageButtonProyectoVer=itemView.findViewById(R.id.imageButtonProyectoVer);
            this.imageButtonProyectoEliminar=itemView.findViewById(R.id.imageButtonProyectoEliminar);
        }

        public void bind(final Proyectos listProyectos, final OnItemClickListener onItemClickListener){

            //System.out.println("Nombre Proyecto: "+listProyectos.getNombre());
            this.textViewProyectoNombreProyecto.setText("Nombre proyecto: "+listProyectos.getNombre().toString());
            this.textViewProyectoPerfilUsuario.setText("Perfil usuario: "+listProyectos.getNombre_perfil().toString());
            this.imageViewProyecto.setImageResource(R.drawable.add_24dp);
            idPerfil=listProyectos.getPerfil_id();
            configurarSegunPerfil();


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   onItemClickListener.onItemClick(listProyectos, getAdapterPosition(), "reciclador");
                }
            });

            imageButtonProyectoEliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(listProyectos, getAdapterPosition(), "eliminar");
                }
            });

            imageButtonProyectoVer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(listProyectos, getAdapterPosition(), "Ver");
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

    private void configurarSegunPerfil() {

        switch (idPerfil)
        {
            case 1:
                constraintLayoutProyecto.setBackgroundColor(Color.rgb(91,144,185));
                break;
            case 2:
                constraintLayoutProyecto.setBackgroundColor(Color.rgb(91,185,130));
                break;
            case 3:
                constraintLayoutProyecto.setBackgroundColor(Color.rgb(185,129,91));
                break;
        }
    }

    public  interface OnItemClickListener{
        void onItemClick(Proyectos listProyectos, int position, String boton);
    }


}
