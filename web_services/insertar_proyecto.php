<?PHP
header('Content-type=application/json; charset=utf-8');

$hostname="localhost";
$database="recolectarq";
$username="root";
$password="";
$conexion=mysqli_connect($hostname,$username,$password,$database);
	 
    $usuario_id=$_POST["usuario"];
    $tipo_proyecto_id=$_POST["tipo"];
    $fase_proyecto_id=$_POST["fase"];
    $nombre=$_POST["nombre"];
    $ubicacion=$_POST["ubicacion"];
    $fecha_inicio=$_POST["inicio"];
    $fecha_fin=$_POST["final"];
    $referencias_administrativas=$_POST["referencias"];
    $aval_cientifico=$_POST["aval"];
    $codigo_identificacion= $_POST["codigo"];

      //$sql="UPDATE usuarios SET nombre= '{$nombre}', apellido= '{$apellido}' WHERE usuario_id=$documento";
      $sql="INSERT INTO `proyectos`(`usuarios_usuario_id`, `tipos_proyectos_tipo_proyecto_id`, `fases_proyectos_fase_proyecto_id`, `nombre`, `ubicacion`, `fecha_inicio`, `fecha_fin`, `referencias_administrativas`, `aval_cientifico`, `codigo_identificacion`) VALUES ({$usuario_id},{$tipo_proyecto_id},{$fase_proyecto_id},'{$nombre}','{$ubicacion}', '{$fecha_inicio}', '{$fecha_fin}','{$referencias_administrativas}','{$aval_cientifico}', '{$codigo_identificacion}')";

     

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

