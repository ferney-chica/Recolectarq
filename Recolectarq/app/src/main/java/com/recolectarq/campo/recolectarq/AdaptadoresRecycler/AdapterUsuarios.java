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

import com.recolectarq.campo.recolectarq.R;
import com.recolectarq.campo.recolectarq.Modelo.Usuarios;

import java.util.List;

public class AdapterUsuarios extends RecyclerView.Adapter<AdapterUsuarios.ViewHolder> {

    private List<Usuarios> listUsuarios;
    private  int layout;
    private OnItemClickListener itemClickListener;
    public AdapterUsuarios(List<Usuarios> listUsuarios, int layout, OnItemClickListener itemClickListener)
    {
        this.listUsuarios=listUsuarios;
        this.layout=layout;
        this.itemClickListener=itemClickListener;

    }

    @NonNull
    @Override
    //Se ha inflado la vista y se la pasa a la clase ViewHolder para extraer los componentes de la vista
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_usuario, parent,false);
        ViewHolder vh=new ViewHolder(v);
        return vh;
    }

    @Override
    //Acá es donde se le configuran los datos a los objetos de la vista del layout creado
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int posicion) {
        viewHolder.bind(listUsuarios.get(posicion), itemClickListener);
        //viewHolder.setOnClickListeners();
    }

    //Número de items que se van a tener
    @Override
    public int getItemCount() {
        return listUsuarios.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewName;
        public ImageView imageViewUsusario;
        public ImageButton btnEliminarUsuario;
        public ImageButton btnEditarUsuario;
        public Context contexto;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            contexto=itemView.getContext();
            this.textViewName=itemView.findViewById(R.id.textViewName);
            this.imageViewUsusario=itemView.findViewById(R.id.imageViewUmtp);
            this.btnEliminarUsuario=itemView.findViewById(R.id.imageButtonEliminarUsuario);
            this.btnEditarUsuario=itemView.findViewById(R.id.imageButtonEditarUmtp);
        }

        public void bind(final Usuarios listUsuarios, final OnItemClickListener onItemClickListener){
            this.textViewName.setText(listUsuarios.getNombre().toString());
            this.imageViewUsusario.setImageResource(R.drawable.add_24dp);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   onItemClickListener.onItemClick(listUsuarios, getAdapterPosition(), "reciclador");
                }
            });

            btnEliminarUsuario.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(listUsuarios, getAdapterPosition(), "eliminar");
                }
            });

            btnEditarUsuario.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(listUsuarios, getAdapterPosition(), "editar");
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
        void onItemClick(Usuarios listUsuarios, int position, String boton);
    }


}
