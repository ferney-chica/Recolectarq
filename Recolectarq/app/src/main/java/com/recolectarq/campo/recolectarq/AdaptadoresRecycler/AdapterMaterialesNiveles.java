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

import com.recolectarq.campo.recolectarq.Modelo.MaterialesNiveles;
import com.recolectarq.campo.recolectarq.R;

import java.util.List;

public class AdapterMaterialesNiveles extends RecyclerView.Adapter<AdapterMaterialesNiveles.ViewHolder> {

    private List<MaterialesNiveles> listMaterialesNiveles;
    private  int layout;
    private OnItemClickListener itemClickListener;
    public AdapterMaterialesNiveles(List<MaterialesNiveles> listMaterialesNiveles, int layout, OnItemClickListener itemClickListener)
    {
        this.listMaterialesNiveles=listMaterialesNiveles;
        this.layout=layout;
        this.itemClickListener=itemClickListener;

    }

    @NonNull
    @Override
    //Se ha inflado la vista y se la pasa a la clase ViewHolder para extraer los componentes de la vista
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_materiales_nivel_pozo, parent,false);
        ViewHolder vh=new ViewHolder(v);
        return vh;
    }

    @Override
    //Acá es donde se le configuran los datos a los objetos de la vista del layout creado
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int posicion) {
        viewHolder.bind(listMaterialesNiveles.get(posicion), itemClickListener);
        //viewHolder.setOnClickListeners();
    }

    //Número de items que se van a tener
    @Override
    public int getItemCount() {
        return listMaterialesNiveles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewMaterialesNivelMaterialId;
        public TextView textViewMaterialesNivelId;
        public TextView textViewMaterialesNivelTipoMaterial;
        public TextView textViewMaterialesNivelCantidad;
        public TextView textViewMaterialesNivelElementoDiagnostico;

        public ImageView imageViewMaterialesNivel;
        public ImageButton imageButtonVerMaterialesNivel;
        public Context contexto;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            contexto=itemView.getContext();
            this.textViewMaterialesNivelMaterialId=itemView.findViewById(R.id.textViewMaterialesEstratoPerfilMaterialId);
            this.textViewMaterialesNivelId=itemView.findViewById(R.id.textViewMaterialesEstratoPerfilId);
            this.textViewMaterialesNivelTipoMaterial=itemView.findViewById(R.id.textViewMaterialesEstratoPerfilTipoMaterial);
            this.textViewMaterialesNivelCantidad=itemView.findViewById(R.id.textViewMaterialesNivelCantidad);
            this.textViewMaterialesNivelElementoDiagnostico=itemView.findViewById(R.id.textViewMaterialesNivelElementoDiagnostico);


            this.imageViewMaterialesNivel=itemView.findViewById(R.id.imageViewMaterialesEstratoPerfil);
            this.imageButtonVerMaterialesNivel=itemView.findViewById(R.id.imageButtonVerMaterialesEstratoPerfil);
            //this.imageButtonVerPozo=itemView.findViewById(R.id.imageButtonVerPozo);
        }

        public void bind(final MaterialesNiveles listMaterialesNiveles, final OnItemClickListener onItemClickListener){

            this.textViewMaterialesNivelMaterialId.setText("Material Id: "+String.valueOf(listMaterialesNiveles.getMaterial_nivel_id()));
            this.textViewMaterialesNivelId.setText("Nivel Pozo Id: "+String.valueOf(listMaterialesNiveles.getNiveles_pozos_nivel_pozo_id()));
            this.textViewMaterialesNivelTipoMaterial.setText("Tipo material: "+String.valueOf(listMaterialesNiveles.getNombre_tipo_material()));
            this.textViewMaterialesNivelCantidad.setText("Cantidad: "+String.valueOf(listMaterialesNiveles.getCantidad()));
            this.textViewMaterialesNivelElementoDiagnostico.setText("Elemento Diagnóstico: "+String.valueOf(listMaterialesNiveles.getElemento_diagnostico()));

            this.imageViewMaterialesNivel.setImageResource(R.drawable.add_24dp);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   onItemClickListener.onItemClick(listMaterialesNiveles, getAdapterPosition(), "reciclador");
                }
            });

            /*imageButtonEliminarPozo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(listPozos, getAdapterPosition(), "eliminar");
                }
            });*/

            imageButtonVerMaterialesNivel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(listMaterialesNiveles, getAdapterPosition(), "Ver");
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
        void onItemClick(MaterialesNiveles listMaterialesNiveles, int position, String boton);
    }


}
