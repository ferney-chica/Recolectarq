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

import com.recolectarq.campo.recolectarq.Modelo.Umtp;
import com.recolectarq.campo.recolectarq.R;

import java.util.List;

public class AdapterUmtp extends RecyclerView.Adapter<AdapterUmtp.ViewHolder> {

    private List<Umtp> listUmtp;
    private  int layout;
    private OnItemClickListener itemClickListener;
    public AdapterUmtp(List<Umtp> listUmtp, int layout, OnItemClickListener itemClickListener)
    {
        this.listUmtp=listUmtp;
        this.layout=layout;
        this.itemClickListener=itemClickListener;

    }

    @NonNull
    @Override
    //Se ha inflado la vista y se la pasa a la clase ViewHolder para extraer los componentes de la vista
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_umtp, parent,false);
        ViewHolder vh=new ViewHolder(v);
        return vh;
    }

    @Override
    //Acá es donde se le configuran los datos a los objetos de la vista del layout creado
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int posicion) {
        viewHolder.bind(listUmtp.get(posicion), itemClickListener);
        //viewHolder.setOnClickListeners();
    }

    //Número de items que se van a tener
    @Override
    public int getItemCount() {
        return listUmtp.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewUmtpId;
        public TextView textViewUmtpGeoforma;
        public TextView textViewUmtpDedicacionEntorno;
        public TextView textViewUmtpTipoRelieve;
        public TextView textViewUmtpVegetacion;
        public TextView textViewUmtpMunicipio;
        public TextView textViewUmtpVereda;
        public TextView textViewUmtpSector;
        public ImageButton btnEliminarUmtp;
        public ImageButton btnEditarUmtp;
        public ImageButton imageButtonUmtpElemetosDiagnosticos;
        public ImageView imageViewUmtp;
        public Context contexto;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            contexto=itemView.getContext();
            this.textViewUmtpId=itemView.findViewById(R.id.textViewUmtpId);
            this.textViewUmtpGeoforma=itemView.findViewById(R.id.textViewUmtpGeoforma);
            this.textViewUmtpDedicacionEntorno=itemView.findViewById(R.id.textViewUmtpDedicacionEntorno);
            this.textViewUmtpTipoRelieve=itemView.findViewById(R.id.textViewUmtpTipoRelieve);
            this.textViewUmtpVegetacion=itemView.findViewById(R.id.textViewUmtpVegetacion);
            this.textViewUmtpMunicipio=itemView.findViewById(R.id.textViewUmtpMunicipio);
            this.textViewUmtpVereda=itemView.findViewById(R.id.textViewUmtpVereda);
            this.textViewUmtpSector=itemView.findViewById(R.id.textViewUmtpSector);
            this.imageViewUmtp=itemView.findViewById(R.id.imageViewUmtp);
            this.btnEliminarUmtp=itemView.findViewById(R.id.imageButtonEliminarUmtp);
            this.btnEditarUmtp=itemView.findViewById(R.id.imageButtonEditarUmtp);
            this.imageButtonUmtpElemetosDiagnosticos=itemView.findViewById(R.id.imageButtonUmtpElemetosDiagnosticos);
        }

        public void bind(final Umtp listUmtp, final OnItemClickListener onItemClickListener){

            if (listUmtp.getUmtp_id()==0){
                this.textViewUmtpId.setText("No hay UMTP para mostrar");
                this.btnEditarUmtp.setEnabled(false);
                this.btnEliminarUmtp.setEnabled(false);
                this.imageButtonUmtpElemetosDiagnosticos.setEnabled(false);
            }else{
                this.textViewUmtpId.setText("Umtp Id: "+ listUmtp.getUmtp_id()+"");
                this.textViewUmtpGeoforma.setText("Geoforma: "+ listUmtp.getGeoforma_id()+"");
                this.textViewUmtpDedicacionEntorno.setText("Dedicacion Entorno: "+ listUmtp.getDedicaciones_entornos_id()+"");
                this.textViewUmtpTipoRelieve.setText("Tipo Relieve: "+ listUmtp.getTipos_relieves_id()+"");
                this.textViewUmtpVegetacion.setText("Vegetación: "+ listUmtp.getVegetaciones_id()+"");
                this.textViewUmtpMunicipio.setText("Municipio: "+ listUmtp.getMunicipio()+"");
                this.textViewUmtpVereda.setText("Vereda: "+ listUmtp.getVereda()+"");
                this.textViewUmtpSector.setText( "Sector: "+listUmtp.getSector()+"");
            }

            this.imageViewUmtp.setImageResource(R.drawable.add_24dp);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   onItemClickListener.onItemClick(listUmtp, getAdapterPosition(), "reciclador");
                }
            });

            btnEliminarUmtp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(listUmtp, getAdapterPosition(), "eliminar");
                }
            });

            btnEditarUmtp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(listUmtp, getAdapterPosition(), "editar");
                }
            });
            imageButtonUmtpElemetosDiagnosticos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(listUmtp, getAdapterPosition(), "ElementosDiagnosticos");
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
        void onItemClick(Umtp listUmtp, int position, String boton);
    }


}
