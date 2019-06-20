<?PHP
header('Content-type=application/json; charset=utf-8');

$hostname="localhost";
$database="recolectarq";
$username="root";
$password="";
$conexion=mysqli_connect($hostname,$username,$password,$database);
	$estrato_pozo_id = $_POST["estrato_pozo_id"];
	$texturas_estratos_textura_estrato_id = $_POST["texturas_estratos_textura_estrato_id"];
	$tipos_estratos_tipo_estrato_id = $_POST["tipos_estratos_tipo_estrato_id"];
	$estructuras_estratos_estructura_estrato_id = $_POST["estructuras_estratos_estructura_estrato_id"];
	$profundidad = $_POST["profundidad"];
	$color = $_POST["color"];
	$observacion = $_POST["observacion"];

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
      $sql="UPDATE `estratos_pozos` SET `texturas_estratos_textura_estrato_id`='{$texturas_estratos_textura_estrato_id}',`tipos_estratos_tipo_estrato_id`='{$tipos_estratos_tipo_estrato_id}',`estructuras_estratos_estructura_estrato_id`='{$estructuras_estratos_estructura_estrato_id}',`profundidad`='{$profundidad}',`color`='{$color}',`observacion`='{$observacion}' WHERE estrato_pozo_id='{$estrato_pozo_id}'";
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

