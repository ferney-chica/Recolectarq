<?PHP
header('Content-type=application/json; charset=utf-8');

$hostname="localhost";
$database="recolectarq";
$username="root";
$password="";
$conexion=mysqli_connect($hostname,$username,$password,$database);

	$estructuras_arqueologicas_id = $_POST["estructuras_arqueologicas_id"];
	$tipos_estructuras_id = $_POST["tipos_estructuras_id"];
	$topologias_estructuras_id = $_POST["topologias_estructuras_id"];
	$descripcion = $_POST["descripcion"];
	$punto_conexo = $_POST["punto_conexo"];
	$utmx = $_POST["utmx"];
	$utmy = $_POST["utmy"];
	$latitud = $_POST["latitud"];
	$longitud = $_POST["longitud"];
	$dimension = $_POST["dimension"];
	$entorno = $_POST["entorno"];

	//$documento = 1;
	//$nombre = "nombre";
	//$apellido = "documento";



	//$imagen = $_POST["imagen"];
	//$path = "imagenes/$nombre.jpg";
	//$url = "http://$hostname_localhost/ejemploBDRemota/$path";
	//$url = "imagenes/".$nombre.".jpg";
	//file_put_contents($path,base64_decode($imagen));
	//$bytesArchivo=file_get_contents($path);
	//$sql="UPDATE usuarios SET nombre= ? , profesion= ?, imagen=?, ruta_imagen=? WHERE documento=?";
	//$sql="UPDATE usuarios SET nombre= ?, apellido= ? WHERE usuario_id=?";
      $sql="UPDATE `estructuras_arqueologicas` SET `tipos_estructuras_id`={$tipos_estructuras_id},`topologias_estructuras_id`={$topologias_estructuras_id},`descripcion`='{$descripcion}',`punto_conexo`='{$punto_conexo}',`utmx`='{$utmx}',`utmy`='{$utmy}',`latitud`='{$latitud}',`longitud`='{$longitud}',`dimension`='{$dimension}',`entorno`='{$entorno}' WHERE `estructuras_arqueologicas_id`={$estructuras_arqueologicas_id}";
      	//echo $sql;



	$stm=$conexion->prepare($sql);
	//$stm->bind_param('ssssi',$nombre,$profesion,$bytesArchivo,$url,$documento);
	//$stm->bind_param('ssssi', $nombre, $apellido, $documento);
		
	if($stm->execute()){
		echo "actualiza";
	}else{
		echo "noActualiza";
	}
	mysqli_close($conexion);
?>

