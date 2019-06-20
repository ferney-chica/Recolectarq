<?PHP
header('Content-type=application/json; charset=utf-8');

$hostname="localhost";
$database="recolectarq";
$username="root";
$password="";
$conexion=mysqli_connect($hostname,$username,$password,$database);

   $pozo_id=$_POST["pozo_id"]; 
   $umtp_id=$_POST["umtp_id"];
   $estructura_id=$_POST["estructura_id"];
   $tipo=$_POST["tipo"];
   $nombre=$_POST["nombre"];
   $imagen=$_POST["imagen"];
   $proyecto=$_POST["proyecto"];
   
//echo "Esta es la ruta que llega del pc ".$nombre ;
      

//$ruta=$_SERVER['DOCUMENT_ROOT'].'/web_services/imagenes/proyecto/UMTP/'.$umtp;
//$ruta=$_SERVER['DOCUMENT_ROOT'].'/recolectarq/proyecto/'.$proyecto.'/UMTP/UMTP'.$umtp_id;
$ruta=$_SERVER['DOCUMENT_ROOT'].'/recolectarq/proyecto/'.$proyecto;
//echo "esta es la ruta". $ruta. "   uuuuuuuu";


//Crea el proyecto
if (!file_exists($ruta)) 
{
	mkdir($ruta);
	$mensaje="se ha creado la ruta".$ruta;
}else
{
	$mensaje="la ruta ya existe: ".$ruta;

}

//Crea la carpeta UMTP para todas las UMTP
$ruta=$ruta."/UMTP";
if (!file_exists($ruta)) 
{
	mkdir($ruta);
	$mensaje="se ha creado la ruta".$ruta;
}else
{
	$mensaje="la ruta ya existe: ".$ruta;

}

//Crea la UMTP correspondiente
$ruta=$ruta."/UMTP$umtp_id";
if (!file_exists($ruta)) 
{
	mkdir($ruta);
	$mensaje="se ha creado la ruta".$ruta;
}else
{
	$mensaje="la ruta ya existe: ".$ruta;

}

$ruta=$ruta."/PS";
if (!file_exists($ruta)) 
{
	mkdir($ruta);
	$mensaje="se ha creado la ruta".$ruta;
}else
{
	$mensaje="la ruta ya existe: ".$ruta;

}

$ruta=$ruta."/PS$pozo_id";
if (!file_exists($ruta)) 
{
	mkdir($ruta);
	$mensaje="se ha creado la ruta".$ruta;
}else
{
	$mensaje="la ruta ya existe: ".$ruta;

}

$ruta=$ruta."/ImgPS";
if (!file_exists($ruta)) 
{
	mkdir($ruta);
	$mensaje="se ha creado la ruta".$ruta;
}else
{
	$mensaje="la ruta ya existe: ".$ruta;

}

//echo $mensaje. $ruta;
//$path="imagenes/proyecto/UMTP/$umtp/prueba.jpg";
//$path="proyecto/$proyecto/UMTP/UMTP$umtp_id/$nombre";
$path="$ruta/$nombre";


//$url="$hostname/recolectarq/$path";

//echo "esta es el PATH: $path";

//file_put_contents($_SERVER['DOCUMENT_ROOT']."/recolectarq/".$path, base64_decode($imagen));
file_put_contents($path, base64_decode($imagen));

//$bytesArchivo=file_get_contents($_SERVER['DOCUMENT_ROOT']."/recolectarq/".$path);

//echo "esta es la imagen ". $bytesArchivo;
//$sql="UPDATE usuarios SET nombre= '{$nombre}', apellido= '{$apellido}' WHERE usuario_id=$documento";
$sql="INSERT INTO `imagenes_estructuras`(`imagen_estructura_id`, `estructuras_arqueologicas_id`, `nombre`, `tipo`) VALUES (NULL, '{$estructura_id}', '{$nombre}','{$tipo}')";

    //echo $sql;
	$stm=$conexion->prepare($sql);
	//$stm->bind_param('ssssi',$nombre,$profesion,$bytesArchivo,$url,$documento);
	//$stm->bind_param('ssssi', $nombre, $apellido, $documento);
		
	if($stm->execute()){
	echo "inserto";
	}else{
	echo "noInserto";
	}
	mysqli_close($conexion);



?>
