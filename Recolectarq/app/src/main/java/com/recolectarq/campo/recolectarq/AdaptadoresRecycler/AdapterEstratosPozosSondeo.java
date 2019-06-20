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

import com.recolectarq.campo.recolectarq.Modelo.EstratosPozos;
import com.recolectarq.campo.recolectarq.R;

import java.util.List;

public class AdapterEstratosPozosSondeo extends RecyclerView.Adapter<AdapterEstratosPozosSondeo.ViewHolder> {

    private List<EstratosPozos> listEstratosPozos;
    private  int layout;
    private OnItemClickListener itemClickListener;
    public AdapterEstratosPozosSondeo(List<EstratosPozos> listEstratosPozos, int layout, OnItemClickListener itemClickListener)
    {
        this.listEstratosPozos=listEstratosPozos;
        this.layout=layout;
        this.itemClickListener=itemClickListener;

    }

    @NonNull
    @Override
    //Se ha inflado la vista y se la pasa a la clase ViewHolder para extraer los componentes de la vista
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_estratos_pozos_sondeo, parent,false);
        ViewHolder vh=new ViewHolder(v);
        return vh;
    }

    @Override
    //Acá es donde se le configuran los datos a los objetos de la vista del layout creado
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int posicion) {
        viewHolder.bind(listEstratosPozos.get(posicion), itemClickListener);
        //viewHolder.setOnClickListeners();
    }

    //Número de items que se van a tener
    @Override
    public int getItemCount() {
        return listEstratosPozos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewEstratoPozoIdEstrato;
        public TextView textViewEstratoPozoIdPozo;
        public TextView textViewEstratoPozoTipoEstrato;
        public TextView textViewEstratoPozoProfundidad;
        public TextView textViewEstratoPozoColor;
        public TextView textViewEstratoPozoTextura;
        public TextView textViewEstratoPozoEstructura;
        public TextView textViewEstratoPozoObservacion;

        public ImageView imageViewEstrato;
        public ImageButton imageButtonVerEstrato;
        public ImageButton btnVerProyecto;
        public Context contexto;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            contexto=itemView.getContext();
            this.textViewEstratoPozoIdEstrato=itemView.findViewById(R.id.textViewEstratoPerfilIdEstrato);
            this.textViewEstratoPozoIdPozo=itemView.findViewById(R.id.textViewEstratoPerfilIdPerfil);
            this.textViewEstratoPozoTipoEstrato=itemView.findViewById(R.id.textViewEstratoPerfilTipoEstrato);
            this.textViewEstratoPozoProfundidad=itemView.findViewById(R.id.textViewEstratoPerfilProfundidad);
            this.textViewEstratoPozoColor=itemView.findViewById(R.id.textViewEstratoPerfilColor);
            this.textViewEstratoPozoTextura=itemView.findViewById(R.id.textViewEstratoPozoTextura);
            this.textViewEstratoPozoEstructura=itemView.findViewById(R.id.textViewEstratoPerfilEstructura);
            this.textViewEstratoPozoObservacion=itemView.findViewById(R.id.textViewEstratoPozoObservacion);

            this.imageViewEstrato=itemView.findViewById(R.id.imageViewEstrato);
            this.imageButtonVerEstrato=itemView.findViewById(R.id.imageButtonVerEstratoPerfil);
        }

        public void bind(final EstratosPozos listEstratosPozos, final OnItemClickListener onItemClickListener){
            this.textViewEstratoPozoIdEstrato.setText(textViewEstratoPozoIdEstrato.getText()+" "+ String.valueOf(listEstratosPozos.getEstrato_pozo_id()));
            this.textViewEstratoPozoIdPozo.setText(textViewEstratoPozoIdPozo.getText()+" "+String.valueOf(listEstratosPozos.getPozos_pozo_id()));
            this.textViewEstratoPozoTipoEstrato.setText(textViewEstratoPozoTipoEstrato.getText()+" "+String.valueOf(listEstratosPozos.getTipos_estratos_tipo_estrato_id()));
            this.textViewEstratoPozoProfundidad.setText(textViewEstratoPozoProfundidad.getText()+" "+String.valueOf(listEstratosPozos.getProfundidad()));
            this.textViewEstratoPozoColor.setText(textViewEstratoPozoColor.getText()+" "+listEstratosPozos.getColor());
            this.textViewEstratoPozoTextura.setText(textViewEstratoPozoTextura.getText()+" "+String.valueOf(listEstratosPozos.getTexturas_estratos_textura_estrato_id()));
            this.textViewEstratoPozoEstructura.setText(textViewEstratoPozoEstructura.getText()+" "+String.valueOf(listEstratosPozos.getEstructuras_estratos_estructura_estrato_id()));
            this.textViewEstratoPozoObservacion.setText(textViewEstratoPozoObservacion.getText()+" "+listEstratosPozos.getObservacion());

            if(listEstratosPozos.getEstrato_pozo_id()==0)
            {
                imageButtonVerEstrato.setEnabled(false);
            }

            this.imageViewEstrato.setImageResource(R.drawable.add_24dp);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   onItemClickListener.onItemClick(listEstratosPozos, getAdapterPosition(), "reciclador");
                }
            });

            /*imageButtonEliminarPozo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(listPozos, getAdapterPosition(), "eliminar");
                }
            });*/

            imageButtonVerEstrato.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(listEstratosPozos, getAdapterPosition(), "Ver");

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
        void onItemClick(EstratosPozos listEstratosPozos, int position, String boton);
    }


}
