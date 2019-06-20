<?PHP
header('Content-type=application/json; charset=utf-8');

$hostname="localhost";
$database="recolectarq";
$username="root";
$password="";
$conexion=mysqli_connect($hostname,$username,$password,$database);
	 
    $tipos_materiales_id=$_POST["tipos_materiales_id"];
    $estratos_perfiles_estrato_perfil_id=$_POST["estratos_perfiles_estrato_perfil_id"];
    $cantidad=$_POST["cantidad"];
    $observacion=$_POST["observacion"];
    $elemento_diagnostico=$_POST["elemento_diagnostico"];
    $observacion_elemento_diagnostico=$_POST["observacion_elemento_diagnostico"];

      //$sql="UPDATE usuarios SET nombre= '{$nombre}', apellido= '{$apellido}' WHERE usuario_id=$documento";

      $sql="INSERT INTO `materiales_estratos_perfiles` (`material_estrato_perfil_id`, `estratos_perfiles_estrato_perfil_id`, `tipos_materiales_id`, `cantidad`, `observacion`, `elemento_diagnostico`, `observacion_elemento_diagnostico`) VALUES (NULL, '{$estratos_perfiles_estrato_perfil_id}', '{$tipos_materiales_id}', '{$cantidad}', '{$observacion}', '{$elemento_diagnostico}', '{$observacion_elemento_diagnostico}')";




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
