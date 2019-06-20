<?PHP
header('Content-type=application/json; charset=utf-8');

$hostname="localhost";
$database="recolectarq";
$username="root";
$password="";
$conexion=mysqli_connect($hostname,$username,$password,$database);

	$muestra_id = $_POST["muestra_id"];
	$tipos_muestras_id = $_POST["tipos_muestras_id"];
	$tipos_materiales_id = $_POST["tipos_materiales_id"];
	$argumentacion = $_POST["argumentacion"];
	$destino = $_POST["destino"];
	$contexto = $_POST["contexto"];
	$intervencion = $_POST["intervencion"];

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
      $sql="UPDATE `muestras` SET `tipos_muestras_id`='{$tipos_muestras_id}',`tipos_materiales_id`='{$tipos_materiales_id}',`argumentacion`='{$argumentacion}',`destino`='{$destino}',`contexto`='{$contexto}' WHERE `muestra_id`='{$muestra_id}'";
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

