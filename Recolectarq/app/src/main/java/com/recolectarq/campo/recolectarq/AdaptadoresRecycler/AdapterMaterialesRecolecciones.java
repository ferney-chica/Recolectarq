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

import com.recolectarq.campo.recolectarq.Modelo.MaterialesRecolecciones;
import com.recolectarq.campo.recolectarq.Modelo.RecoleccionesSuperficiales;
import com.recolectarq.campo.recolectarq.R;

import java.util.List;

public class AdapterMaterialesRecolecciones extends RecyclerView.Adapter<AdapterMaterialesRecolecciones.ViewHolder> {

    private List<MaterialesRecolecciones> listMaterialesRecolecciones;
    private  int layout;
    private OnItemClickListener itemClickListener;
    public AdapterMaterialesRecolecciones(List<MaterialesRecolecciones> listMaterialesRecolecciones, int layout, OnItemClickListener itemClickListener)
    {
        this.listMaterialesRecolecciones=listMaterialesRecolecciones;
        this.layout=layout;
        this.itemClickListener=itemClickListener;

    }

    @NonNull
    @Override
    //Se ha inflado la vista y se la pasa a la clase ViewHolder para extraer los componentes de la vista
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_materiales_recolecciones_superficiales, parent,false);
        ViewHolder vh=new ViewHolder(v);
        return vh;
    }

    @Override
    //Acá es donde se le configuran los datos a los objetos de la vista del layout creado
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int posicion) {
        viewHolder.bind(listMaterialesRecolecciones.get(posicion), itemClickListener);
        //viewHolder.setOnClickListeners();
    }

    //Número de items que se van a tener
    @Override
    public int getItemCount() {
        return listMaterialesRecolecciones.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewMatRecolId;
        public TextView textViewMatRecolRecoleccionId;
        public TextView textViewMatRecolTipoMaterial;
        public TextView textViewMatRecolCantidad;
        public TextView textViewMatRecolNombreFlanco;
        public TextView textViewMatElementoDiagnostico;
        public TextView textViewMatRecolCodigoRotulo;


        public ImageView imageViewMatRecol;
        public ImageButton imageButtonVerMatRecol;
        public ImageButton imageButtonMatRecolImagenes;
        public ImageButton imageButtonMatRecolCodigoRotulo;
        public Context contexto;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            contexto=itemView.getContext();
            this.textViewMatRecolId=itemView.findViewById(R.id.textViewMatRecolId);
            this.textViewMatRecolRecoleccionId=itemView.findViewById(R.id.textViewMatRecolRecoleccionId);
            this.textViewMatRecolTipoMaterial=itemView.findViewById(R.id.textViewMatRecolTipoMaterial);
            this.textViewMatRecolCantidad=itemView.findViewById(R.id.textViewMatRecolCantidad);
            this.textViewMatRecolNombreFlanco=itemView.findViewById(R.id.textViewMatRecolNombreFlanco);
            this.textViewMatElementoDiagnostico=itemView.findViewById(R.id.textViewMatElementoDiagnostico);
            this.textViewMatRecolCodigoRotulo=itemView.findViewById(R.id.textViewMatRecolCodigoRotulo);

            this.imageViewMatRecol=itemView.findViewById(R.id.imageViewMatRecol);
            this.imageButtonVerMatRecol=itemView.findViewById(R.id.imageButtonVerMatRecol);
            this.imageButtonMatRecolImagenes=itemView.findViewById(R.id.imageButtonMatRecolImagenes);
            this.imageButtonMatRecolCodigoRotulo=itemView.findViewById(R.id.imageButtonMatRecolCodigoRotulo);
            //this.imageButtonVerPozo=itemView.findViewById(R.id.imageButtonVerPozo);
        }

        public void bind(final MaterialesRecolecciones listMaterialesRecolecciones, final OnItemClickListener onItemClickListener){

            this.textViewMatRecolId.setText("Material Id: "+String.valueOf(listMaterialesRecolecciones.getMaterial_recoleccion_id()));
            this.textViewMatRecolRecoleccionId.setText("Recolección Id: "+String.valueOf(listMaterialesRecolecciones.getRecolecciones_superficiales_recoleccion_superficial_id()));
            this.textViewMatRecolTipoMaterial.setText("Tipo material: "+String.valueOf(listMaterialesRecolecciones.getNombre_tipo_material()));
            this.textViewMatRecolCantidad.setText("Cantidad: "+String.valueOf(listMaterialesRecolecciones.getCantidad()));
            this.textViewMatRecolNombreFlanco.setText("Flanco Geográfico: "+String.valueOf(listMaterialesRecolecciones.getNombre_flanco_geografico()));
            this.textViewMatElementoDiagnostico.setText("Elemento Diagnóstico: "+String.valueOf(listMaterialesRecolecciones.getElemento_diagnostico()));
            this.textViewMatRecolCodigoRotulo.setText("Código Rótulo: "+String.valueOf(listMaterialesRecolecciones.getCodigo_rotulo()));

            this.imageViewMatRecol.setImageResource(R.drawable.add_24dp);

            if(listMaterialesRecolecciones.getMaterial_recoleccion_id()==0)
            {
                imageButtonVerMatRecol.setEnabled(false);
                imageButtonMatRecolImagenes.setEnabled(false);
                imageButtonMatRecolCodigoRotulo.setEnabled(false);

            }


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   onItemClickListener.onItemClick(listMaterialesRecolecciones, getAdapterPosition(), "reciclador");
                }
            });

            /*imageButtonEliminarPozo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(listPozos, getAdapterPosition(), "eliminar");
                }
            });*/

            imageButtonVerMatRecol.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(listMaterialesRecolecciones, getAdapterPosition(), "Ver");
                }
            });

            imageButtonMatRecolImagenes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(listMaterialesRecolecciones, getAdapterPosition(), "Imagenes");
                }
            });
            imageButtonMatRecolCodigoRotulo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(listMaterialesRecolecciones, getAdapterPosition(), "CodigoRotulo");
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
        void onItemClick(MaterialesRecolecciones listMaterialesRecolecciones, int position, String boton);
    }


}
