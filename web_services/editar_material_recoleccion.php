<?PHP
header('Content-type=application/json; charset=utf-8');

$hostname="localhost";
$database="recolectarq";
$username="root";
$password="";
$conexion=mysqli_connect($hostname,$username,$password,$database);
	$material_recoleccion_id = $_POST["material_recoleccion_id"];
	$flancos_geograficos_id = $_POST["flancos_geograficos_id"];
	$tipos_materiales_id = $_POST["tipos_materiales_id"];
	$cantidad = $_POST["cantidad"];
	$observacion = $_POST["observacion"];
	$codigo_rotulo = $_POST["codigo_rotulo"];
	$elemento_diagnostico = $_POST["elemento_diagnostico"];
	$observacion_elemento_diagnostico = $_POST["observacion_elemento_diagnostico"];

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
      $sql="UPDATE `materiales_recolecciones` SET `flancos_geograficos_id`='{$flancos_geograficos_id}',`tipos_materiales_id`='{$tipos_materiales_id}',`cantidad`='{$cantidad}',`observacion`='{$observacion}',`codigo_rotulo`='{$codigo_rotulo}',`elemento_diagnostico`='{$elemento_diagnostico}',`observacion_elemento_diagnostico`='{$observacion_elemento_diagnostico}' WHERE material_recoleccion_id='{$material_recoleccion_id}'";
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

