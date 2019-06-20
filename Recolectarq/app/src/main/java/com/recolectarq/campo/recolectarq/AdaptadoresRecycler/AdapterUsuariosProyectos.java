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

import com.recolectarq.campo.recolectarq.Modelo.Usuarios;
import com.recolectarq.campo.recolectarq.Modelo.UsuariosProyectos;
import com.recolectarq.campo.recolectarq.R;

import java.util.List;

public class AdapterUsuariosProyectos extends RecyclerView.Adapter<AdapterUsuariosProyectos.ViewHolder> {

    private List<UsuariosProyectos> listUsuariosProyectos;
    private  int layout;
    private OnItemClickListener itemClickListener;
    public AdapterUsuariosProyectos(List<UsuariosProyectos> listUsuariosProyectos, int layout, OnItemClickListener itemClickListener)
    {
        this.listUsuariosProyectos=listUsuariosProyectos;
        this.layout=layout;
        this.itemClickListener=itemClickListener;

    }

    @NonNull
    @Override
    //Se ha inflado la vista y se la pasa a la clase ViewHolder para extraer los componentes de la vista
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_usuario_proyecto, parent,false);
        ViewHolder vh=new ViewHolder(v);
        return vh;
    }

    @Override
    //Acá es donde se le configuran los datos a los objetos de la vista del layout creado
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int posicion) {
        viewHolder.bind(listUsuariosProyectos.get(posicion), itemClickListener);
        //viewHolder.setOnClickListeners();
    }

    //Número de items que se van a tener
    @Override
    public int getItemCount() {
        return listUsuariosProyectos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewNombreUsuarioProyecto;
        public TextView textViewApellidoUsuarioProyecto;
        public TextView textViewPerfilUsuarioProyecto;
        public ImageView imageViewUsusarioProyecto;
        public ImageButton btnEliminarUsuarioProyecto;
        public ImageButton btnEditarUsuarioProyecto;
        public Context contexto;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            contexto=itemView.getContext();
            this.textViewNombreUsuarioProyecto=itemView.findViewById(R.id.textViewNombreUsuarioProyecto);
            this.textViewApellidoUsuarioProyecto=itemView.findViewById(R.id.textViewApellidoUsuarioProyecto);
            this.textViewPerfilUsuarioProyecto=itemView.findViewById(R.id.textViewPerfilUsuarioProyecto);
            this.imageViewUsusarioProyecto=itemView.findViewById(R.id.imageViewUsuarioProyecto);
            this.btnEliminarUsuarioProyecto=itemView.findViewById(R.id.imageButtonEliminarUsuarioProyecto);
            this.btnEditarUsuarioProyecto=itemView.findViewById(R.id.imageButtonEditarUsuarioProyecto);
        }

        public void bind(final UsuariosProyectos listUsuariosProyectos, final OnItemClickListener onItemClickListener){
            this.textViewNombreUsuarioProyecto.setText(String.valueOf( listUsuariosProyectos.getUsuario_nombre()));
            this.textViewApellidoUsuarioProyecto.setText(String.valueOf( listUsuariosProyectos.getUsuario_apellido()));
            this.textViewPerfilUsuarioProyecto.setText(String.valueOf( listUsuariosProyectos.getPerfil_descripcion()));
            this.imageViewUsusarioProyecto.setImageResource(R.drawable.add_24dp);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   onItemClickListener.onItemClick(listUsuariosProyectos, getAdapterPosition(), "reciclador");
                }
            });

            btnEliminarUsuarioProyecto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(listUsuariosProyectos, getAdapterPosition(), "eliminar");
                }
            });

            btnEditarUsuarioProyecto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(listUsuariosProyectos, getAdapterPosition(), "editar");
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
        void onItemClick(UsuariosProyectos listUsuariosProyectos, int position, String boton);
    }


}
