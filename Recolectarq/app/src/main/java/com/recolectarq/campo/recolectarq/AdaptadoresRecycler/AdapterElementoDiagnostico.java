package com.recolectarq.campo.recolectarq.AdaptadoresRecycler;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.recolectarq.campo.recolectarq.Modelo.ElementoDiagnostico;
import com.recolectarq.campo.recolectarq.R;

import java.util.List;

public class AdapterElementoDiagnostico extends RecyclerView.Adapter<AdapterElementoDiagnostico.ViewHolder> {

    private List<ElementoDiagnostico> listElemento;
    private  int layout;
    private String origen;
    private OnItemClickListener itemClickListener;
    public AdapterElementoDiagnostico(String origen,List<ElementoDiagnostico> listElemento, int layout, OnItemClickListener itemClickListener)
    {
        this.listElemento=listElemento;
        this.layout=layout;
        this.itemClickListener=itemClickListener;
        this.origen=origen;
    }

    @NonNull
    @Override
    //Se ha inflado la vista y se la pasa a la clase ViewHolder para extraer los componentes de la vista
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_elemento_diagnostico, parent,false);
        ViewHolder vh=new ViewHolder(v);
        return vh;
    }

    @Override
    //Acá es donde se le configuran los datos a los objetos de la vista del layout creado
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int posicion) {
        viewHolder.bind(listElemento.get(posicion), itemClickListener);
        //viewHolder.setOnClickListeners();
    }

    //Número de items que se van a tener
    @Override
    public int getItemCount() {
        return listElemento.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewElementoDiagnosticoIntervencion;
        public TextView textViewElementoDiagnosticoTipo;
        public TextView textViewElementoDiagnosticoCantidad;
        public TextView textViewElementoDiagnosticoDescripcion;
        public ImageView imageViewElementoDiagnostico;
        public ConstraintLayout cardViewElementoDiagnostico;
        public Context contexto;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            contexto=itemView.getContext();
            this.textViewElementoDiagnosticoIntervencion=itemView.findViewById(R.id.textViewElementoDiagnosticoIntervencion);
            this.textViewElementoDiagnosticoTipo=itemView.findViewById(R.id.textViewElementoDiagnosticoTipo);
            this.textViewElementoDiagnosticoCantidad=itemView.findViewById(R.id.textViewElementoDiagnosticoCantidad);
            this.textViewElementoDiagnosticoDescripcion=itemView.findViewById(R.id.textViewElementoDiagnosticoDescripcion);
            this.imageViewElementoDiagnostico=itemView.findViewById(R.id.imageViewElementoDiagnostico);
            this.cardViewElementoDiagnostico=itemView.findViewById(R.id.cardViewElementoDiagnostico);
            textViewElementoDiagnosticoCantidad.setVisibility(View.INVISIBLE);
        }

        public void bind(final ElementoDiagnostico listElemento, final OnItemClickListener onItemClickListener){

            if (listElemento.getIdIntervencion()==0){

                switch (origen)
                {
                    case "pozo":
                        textViewElementoDiagnosticoIntervencion.setText("Pozo Id: 0");
                        break;
                    case "recoleccion":
                        textViewElementoDiagnosticoIntervencion.setText("Recolección Id: 0");
                        break;
                    case "perfil":
                        textViewElementoDiagnosticoIntervencion.setText("Perfil Id: 0");
                        break;
                }

                this.textViewElementoDiagnosticoTipo.setText("Tipo: No hay UMTP para mostrar");

            }else{

                switch (origen)
                {
                    case "pozo":
                        textViewElementoDiagnosticoIntervencion.setText("Pozo Id: "+listElemento.getIdIntervencion());
                        System.out.println("Entro a Pozo///////////////////////////////////////////////");
                        break;
                    case "recoleccion":
                        textViewElementoDiagnosticoIntervencion.setText("Recolección Id: "+listElemento.getIdIntervencion());
                        break;
                    case "perfil":
                        textViewElementoDiagnosticoIntervencion.setText("Perfil Id: "+listElemento.getIdIntervencion());
                        break;
                }

                switch (listElemento.getTipo())
                {
                    case "M":
                        textViewElementoDiagnosticoTipo.setText("Tipo Material: "+listElemento.getTipoElementoDiagnostico());
                        textViewElementoDiagnosticoCantidad.setText("Cantidad: "+listElemento.getCantidad());
                        textViewElementoDiagnosticoCantidad.setVisibility(View.VISIBLE);
                        cardViewElementoDiagnostico.setBackgroundColor(Color.rgb(197,185,140));
                        System.out.println("Entro a Material///////////////////////////////////////////////");
                        break;
                    case "E":
                        textViewElementoDiagnosticoTipo.setText("Tipo Estructura: "+listElemento.getTipoElementoDiagnostico());
                        System.out.println("Entro a Estructura///////////////////////////////////////////////");
                        textViewElementoDiagnosticoCantidad.setVisibility(View.INVISIBLE);
                        cardViewElementoDiagnostico.setBackgroundColor(Color.rgb(173,197,140));
                        break;
                }

                this.textViewElementoDiagnosticoDescripcion.setText("Descripción: "+ listElemento.getDescripcion()+"");
            }

            this.imageViewElementoDiagnostico.setImageResource(R.drawable.add_24dp);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   onItemClickListener.onItemClick(listElemento, getAdapterPosition(), "reciclador");
                }
            });

            /*btnEliminarUmtp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(listUmtp, getAdapterPosition(), "eliminar");
                }
            });*/


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
        void onItemClick(ElementoDiagnostico listElemento, int position, String boton);
    }


}
