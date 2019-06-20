package com.recolectarq.campo.recolectarq;

import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.recolectarq.campo.recolectarq.Actividades.InicioUsuarioActivity;

import java.io.File;

public class Carpetas {

    public String crearCarpeta(String nombre)
    {
        String nombre1=" -----";
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))   {
        File carpeta = new File(Environment.getExternalStorageDirectory()+nombre);
        if(!carpeta.exists()) {
            //carpeta.mkdir() creará la carpeta en la ruta indicada al inicializar el objeto File
            if(carpeta.mkdir()) {
                nombre1=carpeta.getName();
                //se ha creado la carpeta;

                //Toast.makeText(getApplicationContext(), "Carpeta creada : " + carpeta.getAbsolutePath(), Toast.LENGTH_SHORT).show();
            }
        }else
        {
            nombre1="la carpeta "+ nombre+ "  ya existe";
            //Toast.makeText(getApplicationContext(), "Carpeta existente : " + carpeta.getAbsolutePath(), Toast.LENGTH_SHORT).show();
        }

        } else {
            //Toast.makeText(InicioUsuarioActivity.this,"SDCARDINFO\",\"No se encuentra SD Card.",Toast.LENGTH_LONG).show();
            Log.i("SDCARDINFO","No se encuentra SD Card.");

        }

        return nombre1;
    }
}
