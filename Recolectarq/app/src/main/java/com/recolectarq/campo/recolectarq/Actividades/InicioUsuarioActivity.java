package com.recolectarq.campo.recolectarq.Actividades;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.recolectarq.campo.recolectarq.Carpetas;
import com.recolectarq.campo.recolectarq.Fragmentos.DashboardFragment;
import com.recolectarq.campo.recolectarq.Fragmentos.Proyectos.ProyectosFragment;
import com.recolectarq.campo.recolectarq.Fragmentos.Umtp.UmtpFragment;
import com.recolectarq.campo.recolectarq.Fragmentos.Usuarios.UsuariosFragment;
import com.recolectarq.campo.recolectarq.Modelo.Usuarios;
import com.recolectarq.campo.recolectarq.R;

import java.io.File;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class InicioUsuarioActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Bundle usuarioEnviado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        if(validarPermisos())
        {
            System.out.println("LOS PERMISOS ESTAN habilitados");
        }else
        {
            System.out.println("LOS PERMISOS ESTAN deshabilitados");

        }


        setContentView(R.layout.activity_inicio_usuario);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Intent i= getIntent();

        usuarioEnviado=i.getExtras();
        //usuarioEnviado= getIntent().getExtras().getBundle("usuario");
        Usuarios usuarioLogueado= null;
        DashboardFragment dashboard= new DashboardFragment();
        dashboard.setArguments(usuarioEnviado);


        if (usuarioEnviado!=null)
        {

            usuarioLogueado=(Usuarios) usuarioEnviado.getSerializable("usuario");
            getSupportFragmentManager().beginTransaction().replace(R.id.contenidos, dashboard).commit();

            Toast.makeText(InicioUsuarioActivity.this, "Bienvenido "+ usuarioLogueado.getNombre(), Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(InicioUsuarioActivity.this, "no llego", Toast.LENGTH_LONG).show();

        }

        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))   {

            Carpetas carpeta=new Carpetas();
            Toast.makeText(InicioUsuarioActivity.this,"SDCARDINFO\",\"SD Card se encuentra presente.  FERNEY CHICA",Toast.LENGTH_LONG).show();
            //Log.i("SDCARDINFO","SD Card se encuentra presente.");
            /*Toast.makeText(getApplicationContext(), "Carpeta creada : " + crearCarpeta("/Recolectarq"), Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(), "Carpeta creada : " + crearCarpeta("/Recolectarq/Proyectos"), Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(), "Carpeta creada : " + crearCarpeta("/Recolectarq/MemoriasTecnicas"), Toast.LENGTH_SHORT).show();*/
            Toast.makeText(getApplicationContext(), "Carpeta creada : " + carpeta.crearCarpeta("/Recolectarq"), Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(), "Carpeta creada : " + carpeta.crearCarpeta("/Recolectarq/Proyectos"), Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(InicioUsuarioActivity.this,"SDCARDINFO\",\"No se encuentra SD Card.",Toast.LENGTH_LONG).show();
            //Log.i("SDCARDINFO","No se encuentra SD Card.");

        }
    }

    private boolean validarPermisos() {

        if(Build.VERSION.SDK_INT<Build.VERSION_CODES.M)
        {
            return true;

        }

        if ((checkSelfPermission(CAMERA)== PackageManager.PERMISSION_GRANTED)&&
                (checkSelfPermission(WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED)  )
        {
            return true;
        }


        if((shouldShowRequestPermissionRationale(CAMERA))||
                (shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE)))
        {
            cargarDialogoRecomendacion();
        }else{

            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE,CAMERA},100);
        }

        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 100:
                if (grantResults.length ==2 && grantResults[0]==PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    System.out.println("Los permisos fueron aceptados desde OnrequestPermissionresuslt");
                }else{

                    solicitarPermisosManual();
                }

                break;
        }
    }

    private void solicitarPermisosManual() {
        final CharSequence[] opciones={"Si","No"};
        AlertDialog.Builder alertaOpciones =new AlertDialog.Builder(InicioUsuarioActivity.this);
        alertaOpciones.setTitle("¿Desea configurar losPermisos de forma Manual?");

        alertaOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if(opciones[which].equals("Si"))
                {
                    Intent intent= new Intent();
                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri=Uri.fromParts("package",getPackageName(),null);
                    intent.setData(uri);
                    startActivity(intent);
                }else
                {
                    Toast.makeText(getApplicationContext(),"Los permisos no fueron aceptados",Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                }
            }
        });


        alertaOpciones.show();
    }

    private void cargarDialogoRecomendacion()
    {
        AlertDialog.Builder dialogo =new AlertDialog.Builder(InicioUsuarioActivity.this);
        dialogo.setTitle("Permisos desactivados");
        dialogo.setMessage("Debe aceptar los permisos para el correcto funcionamiento de RecolectArq");
        dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {



            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                    //use checkSelfPermission()
                    requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE,CAMERA},100);
                } else {
                    //simply use the required feature
                    //as the user has already granted permission to them during installation
                    requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE,CAMERA},100);
                }

            }
        });
        dialogo.show();
    }


    public String crearCarpeta(String nombre)
    {
        String nombre1=" -----";
        File carpeta = new File(Environment.getExternalStorageDirectory()+nombre);
        if(!carpeta.exists()) {
            //carpeta.mkdir() creará la carpeta en la ruta indicada al inicializar el objeto File
            if(carpeta.mkdir()) {
                nombre1=carpeta.getName();
                //se ha creado la carpeta;

                Toast.makeText(getApplicationContext(), "Carpeta creada : " + carpeta.getAbsolutePath(), Toast.LENGTH_SHORT).show();
            }
        }else
        {
            //la carpeta ya existe
            Toast.makeText(getApplicationContext(), "Carpeta existente : " + carpeta.getAbsolutePath(), Toast.LENGTH_SHORT).show();
        }

        return nombre1;
    }


    /*@Override
    public void onBackPressed() { //Clic en botòn atràs de la tableta
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }*/


    protected OnBackPressedListener onBackPressedListener;

    public interface OnBackPressedListener {
        void doBack();
    }

    public void setOnBackPressedListener(OnBackPressedListener onBackPressedListener) {
        this.onBackPressedListener = onBackPressedListener;
    }

    @Override
    public void onBackPressed() {
        if (onBackPressedListener != null)
            onBackPressedListener.doBack();
        else
            super.onBackPressed();
    }



    @Override
    protected void onDestroy() {
        onBackPressedListener = null;
        super.onDestroy();
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.inicio_usuario, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_Proyectos) {
            // Llamar a ProyectosFragment
            ProyectosFragment fProyecto= new ProyectosFragment();
            fProyecto.setArguments(usuarioEnviado);
            getSupportFragmentManager().beginTransaction().replace(R.id.contenidos, fProyecto).commit();

        } else if (id == R.id.nav_umtp) {
            getSupportFragmentManager().beginTransaction().replace(R.id.contenidos, new UmtpFragment()).commit();
        } else if (id == R.id.nav_usuarios) {
            getSupportFragmentManager().beginTransaction().replace(R.id.contenidos, new UsuariosFragment()).commit();
        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



}
