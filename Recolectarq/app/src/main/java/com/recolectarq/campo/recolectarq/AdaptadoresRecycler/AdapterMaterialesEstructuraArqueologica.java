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

import com.recolectarq.campo.recolectarq.Modelo.MaterialesEstructurasArqueologicas;
import com.recolectarq.campo.recolectarq.Modelo.MaterialesNiveles;
import com.recolectarq.campo.recolectarq.R;

import java.util.List;

public class AdapterMaterialesEstructuraArqueologica extends RecyclerView.Adapter<AdapterMaterialesEstructuraArqueologica.ViewHolder> {

    private List<MaterialesEstructurasArqueologicas> listMaterialesEstructura;
    private  int layout;
    private OnItemClickListener itemClickListener;
    public AdapterMaterialesEstructuraArqueologica(List<MaterialesEstructurasArqueologicas> listMaterialesEstructura, int layout, OnItemClickListener itemClickListener)
    {
        this.listMaterialesEstructura=listMaterialesEstructura;
        this.layout=layout;
        this.itemClickListener=itemClickListener;

    }

    @NonNull
    @Override
    //Se ha inflado la vista y se la pasa a la clase ViewHolder para extraer los componentes de la vista
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_materiales_estructura_arqueologica, parent,false);
        ViewHolder vh=new ViewHolder(v);
        return vh;
    }

    @Override
    //Acá es donde se le configuran los datos a los objetos de la vista del layout creado
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int posicion) {
        viewHolder.bind(listMaterialesEstructura.get(posicion), itemClickListener);
        //viewHolder.setOnClickListeners();
    }

    //Número de items que se van a tener
    @Override
    public int getItemCount() {
        return listMaterialesEstructura.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewMaterialesEstructuraMaterialId;
        public TextView textViewMaterialesEstructuraId;
        public TextView textViewMaterialesEstructuraTipoMaterial;

        public ImageView imageViewMaterialesEstructura;
        public ImageButton imageButtonVerMaterialesEstructura;
        public Context contexto;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            contexto=itemView.getContext();
            this.textViewMaterialesEstructuraMaterialId=itemView.findViewById(R.id.textViewMaterialesEstructuraMaterialId);
            this.textViewMaterialesEstructuraId=itemView.findViewById(R.id.textViewMaterialesEstructuraId);
            this.textViewMaterialesEstructuraTipoMaterial=itemView.findViewById(R.id.textViewMaterialesEstructuraTipoMaterial);


            this.imageViewMaterialesEstructura=itemView.findViewById(R.id.imageViewMaterialesEstructura);
            this.imageButtonVerMaterialesEstructura=itemView.findViewById(R.id.imageButtonVerMaterialesEstructura);
            //this.imageButtonVerPozo=itemView.findViewById(R.id.imageButtonVerPozo);
        }

        public void bind(final MaterialesEstructurasArqueologicas listMaterialesEstructura, final OnItemClickListener onItemClickListener){

            this.textViewMaterialesEstructuraMaterialId.setText("Material Id: "+String.valueOf(listMaterialesEstructura.getId()));
            this.textViewMaterialesEstructuraId.setText("Estructura Id: "+String.valueOf(listMaterialesEstructura.getEstructurasArqueologicasId()));
            this.textViewMaterialesEstructuraTipoMaterial.setText("Tipo material: "+listMaterialesEstructura.getNombreTipoMaterial());

            this.imageViewMaterialesEstructura.setImageResource(R.drawable.add_24dp);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   onItemClickListener.onItemClick(listMaterialesEstructura, getAdapterPosition(), "reciclador");
                }
            });

            /*imageButtonEliminarPozo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(listPozos, getAdapterPosition(), "eliminar");
                }
            });*/

            imageButtonVerMaterialesEstructura.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(listMaterialesEstructura, getAdapterPosition(), "Ver");
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
        void onItemClick(MaterialesEstructurasArqueologicas listMaterialesEstructura, int position, String boton);
    }


}
