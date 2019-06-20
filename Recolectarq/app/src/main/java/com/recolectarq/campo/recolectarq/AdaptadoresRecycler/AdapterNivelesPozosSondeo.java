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

import com.recolectarq.campo.recolectarq.Modelo.NivelesPozos;
import com.recolectarq.campo.recolectarq.R;

import java.util.List;

public class AdapterNivelesPozosSondeo extends RecyclerView.Adapter<AdapterNivelesPozosSondeo.ViewHolder> {

    private List<NivelesPozos> listNivelesPozos;
    private  int layout;
    private OnItemClickListener itemClickListener;
    public AdapterNivelesPozosSondeo(List<NivelesPozos> listNivelesPozos, int layout, OnItemClickListener itemClickListener)
    {
        this.listNivelesPozos=listNivelesPozos;
        this.layout=layout;
        this.itemClickListener=itemClickListener;

    }

    @NonNull
    @Override
    //Se ha inflado la vista y se la pasa a la clase ViewHolder para extraer los componentes de la vista
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_niveles_pozos_sondeo, parent,false);
        ViewHolder vh=new ViewHolder(v);
        return vh;
    }

    @Override
    //Acá es donde se le configuran los datos a los objetos de la vista del layout creado
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int posicion) {
        viewHolder.bind(listNivelesPozos.get(posicion), itemClickListener);
        //viewHolder.setOnClickListeners();
    }

    //Número de items que se van a tener
    @Override
    public int getItemCount() {
        return listNivelesPozos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewNivelId;
        public TextView textViewNivelIdPozo;
        public TextView textViewNivelNumero;
        public TextView textViewNivelProfucndidad;
        public TextView textViewNivelCodigoRotuloPozo;

        public ImageView imageViewNivelesPozo;
        public ImageButton imageButtonNivelVer;
        public ImageButton imageButtonNivelMateriales;
        public ImageButton imageButtonNivelImagenes;
        public ImageButton imageButtonNivelCodigoRotulo;

        public Context contexto;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            contexto=itemView.getContext();
            this.textViewNivelId=itemView.findViewById(R.id.textViewNivelId);
            this.textViewNivelIdPozo=itemView.findViewById(R.id.textViewNivelIdPozo);
            this.textViewNivelNumero=itemView.findViewById(R.id.textViewNivelNumero);
            this.textViewNivelProfucndidad=itemView.findViewById(R.id.textViewNivelProfucndidad);
            this.textViewNivelCodigoRotuloPozo=itemView.findViewById(R.id.textViewNivelCodigoRotuloPozo);
            this.imageViewNivelesPozo=itemView.findViewById(R.id.imageViewNivelesPozo);
            this.imageButtonNivelVer=itemView.findViewById(R.id.imageButtonNivelVer);
            this.imageButtonNivelMateriales=itemView.findViewById(R.id.imageButtonNivelMateriales);
            this.imageButtonNivelImagenes=itemView.findViewById(R.id.imageButtonNivelImagenes);
            this.imageButtonNivelCodigoRotulo=itemView.findViewById(R.id.imageButtonNivelCodigoRotulo);
        }

        public void bind(final NivelesPozos listNivelesPozos, final OnItemClickListener onItemClickListener){

            this.textViewNivelId.setText("Id Nivel: "+String.valueOf(listNivelesPozos.getNivel_pozo_id()));
            this.textViewNivelIdPozo.setText("Id Pozo: "+ String.valueOf(listNivelesPozos.getPozos_pozo_id()));
            this.textViewNivelNumero.setText("Número: "+String.valueOf(listNivelesPozos.getNumero()));
            this.textViewNivelProfucndidad.setText("Profundidad: "+String.valueOf(listNivelesPozos.getProfundidad()));
            this.textViewNivelCodigoRotuloPozo.setText("Código Rótulo: "+listNivelesPozos.getCodigo_rotulo());
            this.imageViewNivelesPozo.setImageResource(R.drawable.add_24dp);


            if(listNivelesPozos.getNivel_pozo_id()==0)
            {
                imageButtonNivelVer.setEnabled(false);
                imageButtonNivelMateriales.setEnabled(false);
                imageButtonNivelImagenes.setEnabled(false);
                imageButtonNivelCodigoRotulo.setEnabled(false);
            }


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   onItemClickListener.onItemClick(listNivelesPozos, getAdapterPosition(), "reciclador");
                }
            });

            /*imageButtonEliminarPozo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(listPozos, getAdapterPosition(), "eliminar");
                }
            });*/

            imageButtonNivelVer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(listNivelesPozos, getAdapterPosition(), "Ver");

                }
            });

            imageButtonNivelMateriales.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(listNivelesPozos, getAdapterPosition(), "Materiales");

                }
            });

            imageButtonNivelImagenes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(listNivelesPozos, getAdapterPosition(), "Imagenes");


                }
            });
            imageButtonNivelCodigoRotulo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(listNivelesPozos, getAdapterPosition(), "CodigoRotulo");


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
        void onItemClick(NivelesPozos listNivelesPozos, int position, String boton);
    }


}
