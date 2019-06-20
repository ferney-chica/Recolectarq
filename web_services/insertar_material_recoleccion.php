<?PHP
header('Content-type=application/json; charset=utf-8');

$hostname="localhost";
$database="recolectarq";
$username="root";
$password="";
$conexion=mysqli_connect($hostname,$username,$password,$database);
	 
    $flancos_geograficos_id=$_POST["flancos_geograficos_id"];
    $tipos_materiales_id=$_POST["tipos_materiales_id"];
    $recolecciones_superficiales_recoleccion_superficial_id=$_POST["recolecciones_superficiales_recoleccion_superficial_id"];
    $cantidad=$_POST["cantidad"];
    $observacion=$_POST["observacion"];
    $codigo_rotulo=$_POST["codigo_rotulo"];
    $elemento_diagnostico=$_POST["elemento_diagnostico"];
    $observacion_elemento_diagnostico=$_POST["observacion_elemento_diagnostico"];

      //$sql="UPDATE usuarios SET nombre= '{$nombre}', apellido= '{$apellido}' WHERE usuario_id=$documento";

      $sql="INSERT INTO `materiales_recolecciones` (`material_recoleccion_id`, `flancos_geograficos_id`, `tipos_materiales_id`, `recolecciones_superficiales_recoleccion_superficial_id`, `cantidad`, `observacion`, `codigo_rotulo`, `elemento_diagnostico`, `observacion_elemento_diagnostico`) VALUES (NULL, '{$flancos_geograficos_id}', '{$tipos_materiales_id}', '{$recolecciones_superficiales_recoleccion_superficial_id}', '{$cantidad}', '{$observacion}', '{$codigo_rotulo}', '{$elemento_diagnostico}', '{$observacion_elemento_diagnostico}')";


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

