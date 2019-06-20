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

import com.recolectarq.campo.recolectarq.Modelo.EstratosPerfiles;
import com.recolectarq.campo.recolectarq.R;

import java.util.List;

public class AdapterEstratosPerfilesExpuestos extends RecyclerView.Adapter<AdapterEstratosPerfilesExpuestos.ViewHolder> {

    private List<EstratosPerfiles> listEstratosPerfiles;
    private  int layout;
    private OnItemClickListener itemClickListener;
    public AdapterEstratosPerfilesExpuestos(List<EstratosPerfiles> listEstratosPerfiles, int layout, OnItemClickListener itemClickListener)
    {
        this.listEstratosPerfiles=listEstratosPerfiles;
        this.layout=layout;
        this.itemClickListener=itemClickListener;

    }

    @NonNull
    @Override
    //Se ha inflado la vista y se la pasa a la clase ViewHolder para extraer los componentes de la vista
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_estratos_perfiles_expuestos, parent,false);
        ViewHolder vh=new ViewHolder(v);
        return vh;
    }

    @Override
    //Acá es donde se le configuran los datos a los objetos de la vista del layout creado
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int posicion) {
        viewHolder.bind(listEstratosPerfiles.get(posicion), itemClickListener);
        //viewHolder.setOnClickListeners();
    }

    //Número de items que se van a tener
    @Override
    public int getItemCount() {
        return listEstratosPerfiles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewEstratoPerfilIdEstrato;
        public TextView textViewEstratoPerfilIdPerfil;
        public TextView textViewEstratoPerfilTipoEstrato;
        public TextView textViewEstratoPerfilProfundidad;
        public TextView textViewEstratoPerfilColor;
        public TextView textViewEstratoPerfilTextura;
        public TextView textViewEstratoPerfilEstructura;
        public TextView textViewEstratoPerfilCodigoRotulo;
        public TextView textViewEstratoPerfilObservacion;

        public ImageView imageViewEstratoPerfil;
        public ImageButton imageButtonVerEstratoPerfil;
        public ImageButton imageButtonMaterialesEstratoPerfil;
        public ImageButton imageButtonEstratoPerfilImagenes;
        public ImageButton imageButtonEstratoPerfilCodigoRotulo;
        public ImageButton btnVerProyecto;
        public Context contexto;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            contexto=itemView.getContext();
            this.textViewEstratoPerfilIdEstrato=itemView.findViewById(R.id.textViewEstratoPerfilIdEstrato);
            this.textViewEstratoPerfilIdPerfil=itemView.findViewById(R.id.textViewEstratoPerfilIdPerfil);
            this.textViewEstratoPerfilTipoEstrato=itemView.findViewById(R.id.textViewEstratoPerfilTipoEstrato);
            this.textViewEstratoPerfilProfundidad=itemView.findViewById(R.id.textViewEstratoPerfilProfundidad);
            this.textViewEstratoPerfilColor=itemView.findViewById(R.id.textViewEstratoPerfilColor);
            this.textViewEstratoPerfilTextura=itemView.findViewById(R.id.textViewEstratoPerfilTextura);
            this.textViewEstratoPerfilEstructura=itemView.findViewById(R.id.textViewEstratoPerfilEstructura);
            this.textViewEstratoPerfilCodigoRotulo=itemView.findViewById(R.id.textViewEstratoPerfilCodigoRotulo);
            this.textViewEstratoPerfilObservacion=itemView.findViewById(R.id.textViewEstratoPerfilObservacion);

            this.imageViewEstratoPerfil=itemView.findViewById(R.id.imageViewEstratoPerfil);
            this.imageButtonVerEstratoPerfil=itemView.findViewById(R.id.imageButtonVerEstratoPerfil);
            this.imageButtonMaterialesEstratoPerfil=itemView.findViewById(R.id.imageButtonMaterialesEstratoPerfil);
            this.imageButtonEstratoPerfilImagenes=itemView.findViewById(R.id.imageButtonEstratoPerfilImagenes);
            this.imageButtonEstratoPerfilCodigoRotulo=itemView.findViewById(R.id.imageButtonEstratoPerfilCodigoRotulo);
        }

        public void bind(final EstratosPerfiles listEstratosPerfiles, final OnItemClickListener onItemClickListener){
            this.textViewEstratoPerfilIdEstrato.setText("Id Estrato Perfil: "+ String.valueOf(listEstratosPerfiles.getEstrato_perfil_id()));
            this.textViewEstratoPerfilIdPerfil.setText("Id Perfil Expuesto: "+String.valueOf(listEstratosPerfiles.getPerfiles_expuestos_perfil_expuesto_id()));
            this.textViewEstratoPerfilTipoEstrato.setText("Tipo Estrato: "+String.valueOf(listEstratosPerfiles.getTipo_estrato_nombre()));
            this.textViewEstratoPerfilProfundidad.setText("Profundidad: "+String.valueOf(listEstratosPerfiles.getProfundidad()));
            this.textViewEstratoPerfilColor.setText("Color:  "+listEstratosPerfiles.getColor());
            this.textViewEstratoPerfilTextura.setText("Textura: "+listEstratosPerfiles.getTextura_estrato_nombre());
            this.textViewEstratoPerfilEstructura.setText("Estructura "+String.valueOf(listEstratosPerfiles.getEstructura_estrato_nombre()));
            this.textViewEstratoPerfilCodigoRotulo.setText("Código Rótulo: "+String.valueOf(listEstratosPerfiles.getCodigo_rotulo()));
            this.textViewEstratoPerfilObservacion.setText("Observaciones: "+listEstratosPerfiles.getObservacion());

            if(listEstratosPerfiles.getEstrato_perfil_id()==0)
            {
                imageButtonVerEstratoPerfil.setEnabled(false);
                imageButtonMaterialesEstratoPerfil.setEnabled(false);
                imageButtonEstratoPerfilImagenes.setEnabled(false);
                imageButtonEstratoPerfilCodigoRotulo.setEnabled(false);
            }

            this.imageViewEstratoPerfil.setImageResource(R.drawable.add_24dp);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   onItemClickListener.onItemClick(listEstratosPerfiles, getAdapterPosition(), "reciclador");
                }
            });

            /*imageButtonEliminarPozo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(listPozos, getAdapterPosition(), "eliminar");
                }
            });*/

            imageButtonVerEstratoPerfil.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(listEstratosPerfiles, getAdapterPosition(), "Ver");

                }
            });

            imageButtonMaterialesEstratoPerfil.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(listEstratosPerfiles, getAdapterPosition(), "Materiales");
                }
            });

            imageButtonEstratoPerfilImagenes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(listEstratosPerfiles, getAdapterPosition(), "Imagenes");
                }
            });
            imageButtonEstratoPerfilCodigoRotulo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(listEstratosPerfiles, getAdapterPosition(), "CodigoRotulo");
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
        void onItemClick(EstratosPerfiles listEstratosPerfiles, int position, String boton);
    }


}
