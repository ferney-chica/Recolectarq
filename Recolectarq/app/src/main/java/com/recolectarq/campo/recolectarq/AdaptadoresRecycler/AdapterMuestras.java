package com.recolectarq.campo.recolectarq.AdaptadoresRecycler;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.recolectarq.campo.recolectarq.Modelo.EstructurasArqueologicas;
import com.recolectarq.campo.recolectarq.Modelo.Muestras;
import com.recolectarq.campo.recolectarq.R;

import java.util.List;

public class AdapterMuestras extends RecyclerView.Adapter<AdapterMuestras.ViewHolder> {

    private List<Muestras> listMuestras;
    private  int layout;
    private OnItemClickListener itemClickListener;
    private String origen1;

    public AdapterMuestras(String origen, List<Muestras> listMuestras, int layout, OnItemClickListener itemClickListener)
    {
        this.listMuestras=listMuestras;
        this.layout=layout;
        this.itemClickListener=itemClickListener;
        origen1=origen;

    }

    @NonNull
    @Override
    //Se ha inflado la vista y se la pasa a la clase ViewHolder para extraer los componentes de la vista
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_muestras, parent,false);
        ViewHolder vh=new ViewHolder(v);
        return vh;
    }

    @Override
    //Acá es donde se le configuran los datos a los objetos de la vista del layout creado
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int posicion) {
        viewHolder.bind(listMuestras.get(posicion), itemClickListener);
        //viewHolder.setOnClickListeners();
    }

    //Número de items que se van a tener
    @Override
    public int getItemCount() {
        return listMuestras.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewMuestraId;
        public TextView textViewMuestraIntervencionId;
        public TextView textViewMuestraTipoMuestra;
        public TextView textViewMuestraTipoMaterial;
        public TextView textViewMuestraArgumentacion;
        public TextView textViewMuestraContexto;
        public ImageView imageViewMuestras;
        public ImageButton imageButtonVerMuestra;
        public ImageButton imageButtonEliminarMuestra;

        public Context contexto;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            contexto=itemView.getContext();
            this.textViewMuestraId=itemView.findViewById(R.id.textViewMuestraId);
            this.textViewMuestraIntervencionId=itemView.findViewById(R.id.textViewMuestraIntervencionId);
            this.textViewMuestraTipoMuestra=itemView.findViewById(R.id.textViewMuestraTipoMuestra);
            this.textViewMuestraTipoMaterial=itemView.findViewById(R.id.textViewMuestraTipoMaterial);
            this.textViewMuestraArgumentacion=itemView.findViewById(R.id.textViewMuestraArgumentacion);
            this.textViewMuestraContexto=itemView.findViewById(R.id.textViewMuestraContexto);
            this.imageViewMuestras=itemView.findViewById(R.id.imageViewMuestras);
            this.imageButtonVerMuestra=itemView.findViewById(R.id.imageButtonVerMuestra);
        }

        public void bind(final Muestras listMuestras, final OnItemClickListener onItemClickListener){

            switch(origen1)
            {
                case "pozo":
                    if(listMuestras.getMuestra_id()==0)
                    {
                        this.textViewMuestraIntervencionId.setText("Id Pozo: "+0);

                        //imageButtonEliminarMuestra.setEnabled(false);
                        imageButtonVerMuestra.setEnabled(false);
                    }else
                    {
                        this.textViewMuestraIntervencionId.setText("Id Pozo: "+String.valueOf(listMuestras.getPozos_pozo_id()));
                    }

                    break;
                case "recoleccion":
                    if(listMuestras.getMuestra_id()==0)
                    {
                        this.textViewMuestraIntervencionId.setText("Id Recoleccion: "+0);

                        //imageButtonEliminarMuestra.setEnabled(false);
                        imageButtonVerMuestra.setEnabled(false);
                    }else
                    {
                        this.textViewMuestraIntervencionId.setText("Id Recolección: "+String.valueOf(listMuestras.getRecolecciones_superficiales_recoleccion_superficial_id()));
                    }
                    break;

                case "perfil":
                    if(listMuestras.getMuestra_id()==0)
                    {
                        this.textViewMuestraIntervencionId.setText("Id Perfil: "+0);

                        //imageButtonEliminarMuestra.setEnabled(false);
                        imageButtonVerMuestra.setEnabled(false);
                    }else
                    {
                        this.textViewMuestraIntervencionId.setText("Id Perfil: "+String.valueOf(listMuestras.getPerfiles_expuestos_perfil_expuesto_id()));
                    }
                    break;
            }



            this.textViewMuestraId.setText("Id Muestra: "+String.valueOf(listMuestras.getMuestra_id()));

            this.textViewMuestraTipoMuestra.setText("Tipo Muestra: "+String.valueOf(listMuestras.getTipos_muestras_nombre()));
            this.textViewMuestraTipoMaterial.setText("Tipo Material: "+String.valueOf(listMuestras.getTipos_materiales_nombre()));
            this.textViewMuestraArgumentacion.setText("Argumentación: "+String.valueOf(listMuestras.getArgumentacion()));
            this.textViewMuestraContexto.setText("Contexto: "+String.valueOf(listMuestras.getContexto()));
            this.imageViewMuestras.setImageResource(R.drawable.add_24dp);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   onItemClickListener.onItemClick(listMuestras, getAdapterPosition(), "reciclador");
                }
            });

            /*imageButtonEliminarPozo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(listPozos, getAdapterPosition(), "eliminar");
                }
            });*/

            imageButtonVerMuestra.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(listMuestras, getAdapterPosition(), "Ver");

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
        void onItemClick(Muestras listMuestras, int position, String boton);
    }


}
