package com.recolectarq.campo.recolectarq.Actividades;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class Permisos {

    public Activity actividad=null;
    public Context contexto=null;

    @RequiresApi(api = Build.VERSION_CODES.M)
    protected Boolean validarPermisos(Context contexto1, Activity actividad1)
    {  Boolean validar=false;
        actividad=actividad1;
        contexto=contexto1;
        if(Build.VERSION.SDK_INT<Build.VERSION_CODES.M)
        {
            validar=true;

        }

        if ((ContextCompat.checkSelfPermission(contexto, Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED)&&
                (ContextCompat.checkSelfPermission(contexto, Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED)  )
        {
            validar=true;
        }

        if((ActivityCompat.shouldShowRequestPermissionRationale(actividad,Manifest.permission.CAMERA))||
                (ActivityCompat.shouldShowRequestPermissionRationale(actividad,Manifest.permission.WRITE_EXTERNAL_STORAGE)))
        {
            cargarDialogoRecomendacion();
        }else{

            actividad.requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE,CAMERA},100);
        }

        return false;

    }

    private void cargarDialogoRecomendacion() {
        AlertDialog.Builder dialogo =new AlertDialog.Builder(actividad);
        dialogo.setTitle("Permisos desactivados");
        dialogo.setMessage("Debe aceptar los permisos para el correcto funcionamiento de RecolectArq");
        dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(DialogInterface dialog, int which) {
                actividad.requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE,CAMERA},100);
            }
        });
        dialogo.show();
    }


    public void onRequestPermissionsResult(
            int requestCode,
            @NonNull String permissions[],
            @NonNull int[] grantResults) {
        switch (requestCode) {
            case 100:
                if (grantResults.length ==2 && grantResults[0]==PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                }else{

                    solicitarPermisosManual();
                }

                break;
                }
        }

    private void solicitarPermisosManual() {
        final CharSequence[] opciones={"Si","No"};
        AlertDialog.Builder alertaOpciones =new AlertDialog.Builder(actividad);
        alertaOpciones.setTitle("¿Desea configurar losPermisos de forma Manual?");

        alertaOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if(opciones[which].equals("Si"))
                {
                    Intent intent= new Intent();
                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri=Uri.fromParts("package",contexto.getPackageName(),null);
                    intent.setData(uri);
                    contexto.startActivity(intent);
                }else
                {
                    Toast.makeText(contexto,"Los permisos no fueron aceptados",Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                }
            }
        });


        alertaOpciones.show();
    }
}


