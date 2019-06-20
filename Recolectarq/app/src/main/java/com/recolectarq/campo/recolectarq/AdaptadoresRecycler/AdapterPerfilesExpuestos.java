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

import com.recolectarq.campo.recolectarq.Modelo.PerfilesExpuestos;
import com.recolectarq.campo.recolectarq.R;

import java.util.List;

public class AdapterPerfilesExpuestos extends RecyclerView.Adapter<AdapterPerfilesExpuestos.ViewHolder> {

    private List<PerfilesExpuestos> listPerfilesExpuestos;
    private  int layout;
    private OnItemClickListener itemClickListener;
    public AdapterPerfilesExpuestos(List<PerfilesExpuestos> listPerfilesExpuestos, int layout, OnItemClickListener itemClickListener)
    {
        this.listPerfilesExpuestos=listPerfilesExpuestos;
        this.layout=layout;
        this.itemClickListener=itemClickListener;

    }

    @NonNull
    @Override
    //Se ha inflado la vista y se la pasa a la clase ViewHolder para extraer los componentes de la vista
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_perfiles_expuestos, parent,false);
        ViewHolder vh=new ViewHolder(v);
        return vh;
    }

    @Override
    //Acá es donde se le configuran los datos a los objetos de la vista del layout creado
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int posicion) {
        viewHolder.bind(listPerfilesExpuestos.get(posicion), itemClickListener);
        //viewHolder.setOnClickListeners();
    }

    //Número de items que se van a tener
    @Override
    public int getItemCount() {
        return listPerfilesExpuestos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewPerfilId;
        public TextView textViewPerfilDescripcion;
        public TextView textViewPerfilEncargado;
        public TextView textViewPerfilCodigoRotulo;
        public ImageView imageViewPerfil;
        public ImageButton imageButtonPerfilVer;
        public ImageButton imageButtonPerfilImagenes;
        public ImageButton imageButtonPerfilCodigoRotulo;
        public Context contexto;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            contexto=itemView.getContext();
            this.textViewPerfilId=itemView.findViewById(R.id.textViewPerfilId);
            this.textViewPerfilDescripcion=itemView.findViewById(R.id.textViewPerfilDescripcion);
            this.textViewPerfilEncargado=itemView.findViewById(R.id.textViewPerfilEncargado);
            this.textViewPerfilCodigoRotulo=itemView.findViewById(R.id.textViewPerfilCodigoRotulo);
            this.imageViewPerfil=itemView.findViewById(R.id.imageViewPerfil);
            this.imageButtonPerfilVer=itemView.findViewById(R.id.imageButtonPerfilVer);
            this.imageButtonPerfilImagenes=itemView.findViewById(R.id.imageButtonPerfilImagenes);
            this.imageButtonPerfilCodigoRotulo=itemView.findViewById(R.id.imageButtonPerfilCodigoRotulo);
            //this.imageButtonVerPozo=itemView.findViewById(R.id.imageButtonVerPozo);
        }

        public void bind(final PerfilesExpuestos listPerfilesExpuestos, final OnItemClickListener onItemClickListener){
            this.textViewPerfilId.setText("Perfil Id: "+String.valueOf(listPerfilesExpuestos.getPerfil_expuesto_id()));
            this.textViewPerfilDescripcion.setText("Descripción: "+String.valueOf(listPerfilesExpuestos.getDescripcion()));
            this.textViewPerfilEncargado.setText("Encargado: "+String.valueOf(listPerfilesExpuestos.getUsuario_encargado()));
            this.textViewPerfilCodigoRotulo.setText("Código Rótulo: "+String.valueOf(listPerfilesExpuestos.getCodigo_rotulo()));

            if(listPerfilesExpuestos.getPerfil_expuesto_id()==0)
            {
                imageButtonPerfilVer.setEnabled(false);
                imageButtonPerfilImagenes.setEnabled(false);
                imageButtonPerfilCodigoRotulo.setEnabled(false);
            }

            this.imageViewPerfil.setImageResource(R.drawable.add_24dp);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   onItemClickListener.onItemClick(listPerfilesExpuestos, getAdapterPosition(), "reciclador");
                }
            });

            /*imageButtonEliminarPozo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(listPozos, getAdapterPosition(), "eliminar");
                }
            });*/

            imageButtonPerfilVer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(listPerfilesExpuestos, getAdapterPosition(), "Ver");
                }
            });
            imageButtonPerfilImagenes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(listPerfilesExpuestos, getAdapterPosition(), "Imagenes");
                }
            });
            imageButtonPerfilCodigoRotulo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(listPerfilesExpuestos, getAdapterPosition(), "CodigoRotulo");
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
        void onItemClick(PerfilesExpuestos listPerfilesExpuestos, int position, String boton);
    }


}
