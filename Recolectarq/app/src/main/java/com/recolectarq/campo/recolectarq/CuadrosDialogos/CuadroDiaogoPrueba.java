package com.recolectarq.campo.recolectarq.CuadrosDialogos;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.recolectarq.campo.recolectarq.R;

public class CuadroDiaogoPrueba {

    private Dialog dialogoProyecto;
    private TextView textViewPruebaTitulo;
    private Button buttonCuadroDialogoProyectoCancelar;
    private Button buttonCuadroDialogoProyectoActualizar;

    public CuadroDiaogoPrueba(Context contextoE) {

        dialogoProyecto=new Dialog(contextoE);
        dialogoProyecto.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogoProyecto.setCancelable(false);
        dialogoProyecto.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogoProyecto.setContentView(R.layout.cuadro_dialogo_prueba);

        textViewPruebaTitulo=dialogoProyecto.findViewById(R.id.textViewPruebaTitulo);
        buttonCuadroDialogoProyectoCancelar=dialogoProyecto.findViewById(R.id.buttonCuadroDialogoProyectoCancelar);
        buttonCuadroDialogoProyectoActualizar=dialogoProyecto.findViewById(R.id.buttonCuadroDialogoProyectoActualizar);

        buttonCuadroDialogoProyectoCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogoProyecto.dismiss();
            }
        });

        buttonCuadroDialogoProyectoActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogoProyecto.dismiss();
            }
        });

        dialogoProyecto.show();

    }
}
