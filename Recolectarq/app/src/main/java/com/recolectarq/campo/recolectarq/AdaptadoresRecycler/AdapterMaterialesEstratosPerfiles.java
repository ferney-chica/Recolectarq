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

import com.recolectarq.campo.recolectarq.Modelo.MaterialesEstratosPerfiles;
import com.recolectarq.campo.recolectarq.R;

import java.util.List;

public class AdapterMaterialesEstratosPerfiles extends RecyclerView.Adapter<AdapterMaterialesEstratosPerfiles.ViewHolder> {

    private List<MaterialesEstratosPerfiles> listMaterialesEstratosPerfiles;
    private  int layout;
    private OnItemClickListener itemClickListener;
    public AdapterMaterialesEstratosPerfiles(List<MaterialesEstratosPerfiles> listMaterialesEstratosPerfiles, int layout, OnItemClickListener itemClickListener)
    {
        this.listMaterialesEstratosPerfiles=listMaterialesEstratosPerfiles;
        this.layout=layout;
        this.itemClickListener=itemClickListener;

    }

    @NonNull
    @Override
    //Se ha inflado la vista y se la pasa a la clase ViewHolder para extraer los componentes de la vista
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_materiales_estrato_perfil, parent,false);
        ViewHolder vh=new ViewHolder(v);
        return vh;
    }

    @Override
    //Acá es donde se le configuran los datos a los objetos de la vista del layout creado
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int posicion) {
        viewHolder.bind(listMaterialesEstratosPerfiles.get(posicion), itemClickListener);
        //viewHolder.setOnClickListeners();
    }

    //Número de items que se van a tener
    @Override
    public int getItemCount() {
        return listMaterialesEstratosPerfiles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewMaterialesEstratoPerfilMaterialId;
        public TextView textViewMaterialesEstratoPerfilId;
        public TextView textViewMaterialesEstratoPerfilTipoMaterial;
        public TextView textViewMaterialesEstratoPerfilCantidad;
        public TextView textViewMaterialesEstratoPerfilElementoDiagnostico;

        public ImageView imageViewMaterialesEstratoPerfil;
        public ImageButton imageButtonVerMaterialesEstratoPerfil;
        public Context contexto;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            contexto=itemView.getContext();
            this.textViewMaterialesEstratoPerfilMaterialId=itemView.findViewById(R.id.textViewMaterialesEstratoPerfilMaterialId);
            this.textViewMaterialesEstratoPerfilId=itemView.findViewById(R.id.textViewMaterialesEstratoPerfilId);
            this.textViewMaterialesEstratoPerfilTipoMaterial=itemView.findViewById(R.id.textViewMaterialesEstratoPerfilTipoMaterial);
            this.textViewMaterialesEstratoPerfilCantidad=itemView.findViewById(R.id.textViewMaterialesEstratoPerfilCantidad);
            this.textViewMaterialesEstratoPerfilElementoDiagnostico=itemView.findViewById(R.id.textViewMaterialesEstratoPerfilElementoDiagnostico);


            this.imageViewMaterialesEstratoPerfil=itemView.findViewById(R.id.imageViewMaterialesEstratoPerfil);
            this.imageButtonVerMaterialesEstratoPerfil=itemView.findViewById(R.id.imageButtonVerMaterialesEstratoPerfil);
            //this.imageButtonVerPozo=itemView.findViewById(R.id.imageButtonVerPozo);
        }

        public void bind(final MaterialesEstratosPerfiles listMaterialesEstratosPerfiles, final OnItemClickListener onItemClickListener){

            this.textViewMaterialesEstratoPerfilMaterialId.setText("Material Id: "+String.valueOf(listMaterialesEstratosPerfiles.getMaterial_estrato_perfil_id()));
            this.textViewMaterialesEstratoPerfilId.setText("Estrato Perfil Id:  "+String.valueOf(listMaterialesEstratosPerfiles.getEstratos_perfiles_estrato_perfil_id()));
            this.textViewMaterialesEstratoPerfilTipoMaterial.setText("Tipo material: "+String.valueOf(listMaterialesEstratosPerfiles.getTipos_materiales_nombre()));
            this.textViewMaterialesEstratoPerfilCantidad.setText("Cantidad: "+String.valueOf(listMaterialesEstratosPerfiles.getCantidad()));
            this.textViewMaterialesEstratoPerfilElementoDiagnostico.setText("Elemento Diagnóstico: "+String.valueOf(listMaterialesEstratosPerfiles.getElemento_diagnostico()));

            if(listMaterialesEstratosPerfiles.getMaterial_estrato_perfil_id()==0)
            {
                imageButtonVerMaterialesEstratoPerfil.setEnabled(false);
            }

            this.imageViewMaterialesEstratoPerfil.setImageResource(R.drawable.add_24dp);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   onItemClickListener.onItemClick(listMaterialesEstratosPerfiles, getAdapterPosition(), "reciclador");
                }
            });

            /*imageButtonEliminarPozo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(listPozos, getAdapterPosition(), "eliminar");
                }
            });*/

            imageButtonVerMaterialesEstratoPerfil.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(listMaterialesEstratosPerfiles, getAdapterPosition(), "Ver");
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
        void onItemClick(MaterialesEstratosPerfiles listMaterialesEstratosPerfiles, int position, String boton);
    }


}
