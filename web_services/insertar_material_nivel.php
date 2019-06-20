<?PHP
header('Content-type=application/json; charset=utf-8');

$hostname="localhost";
$database="recolectarq";
$username="root";
$password="";
$conexion=mysqli_connect($hostname,$username,$password,$database);
	 
    $tipos_materiales_id=$_POST["tipos_materiales_id"];
    $niveles_pozos_nivel_pozo_id=$_POST["niveles_pozos_nivel_pozo_id"];
    $cantidad=$_POST["cantidad"];
    $observacion=$_POST["observacion"];
    $elemento_diagnostico=$_POST["elemento_diagnostico"];
    $observacion_elemento_diagnostico=$_POST["observacion_elemento_diagnostico"];

      //$sql="UPDATE usuarios SET nombre= '{$nombre}', apellido= '{$apellido}' WHERE usuario_id=$documento";

      $sql="INSERT INTO `materiales_niveles` (`material_nivel_id`, `tipos_materiales_id`, `niveles_pozos_nivel_pozo_id`, `cantidad`, `observacion`, `elemento_diagnostico`, `observacion_elemento_diagnostico`) VALUES (NULL, '{$tipos_materiales_id}', '{$niveles_pozos_nivel_pozo_id}', '{$cantidad}', '{$observacion}', '{$elemento_diagnostico}', '{$observacion_elemento_diagnostico}')";




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
