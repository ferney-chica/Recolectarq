<?PHP
header('Content-type=application/json; charset=utf-8');

$hostname="localhost";
$database="recolectarq";
$username="root";
$password="";
$conexion=mysqli_connect($hostname,$username,$password,$database);
	 
    
    $perfil_id=$_POST["perfil_id"];
    $proyectos_proyecto_id=$_POST["proyectos_proyecto_id"];
    $usuarios_usuario_id=$_POST["usuarios_usuario_id"];

        $sql="INSERT INTO `usuarios_proyectos` (`perfiles_perfil_id`, `proyectos_proyecto_id`, `usuarios_usuario_id`) VALUES ('{$perfil_id}', '{$proyectos_proyecto_id}', '{$usuarios_usuario_id}')";

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

