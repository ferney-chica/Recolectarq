<?PHP
header('Content-type=application/json; charset=utf-8');

$hostname="localhost";
$database="recolectarq";
$username="root";
$password="";
$conexion=mysqli_connect($hostname,$username,$password,$database);
	 
    $umtp_id=$_POST["umtp_id"];
    $descripcion=$_POST["descripcion"];
    $usuario_creador=$_POST["usuario_creador"];
    $usuario_encargado=$_POST["usuario_encargado"];
    $codigo_rotulo=$_POST["codigo_rotulo"];

      //$sql="UPDATE usuarios SET nombre= '{$nombre}', apellido= '{$apellido}' WHERE usuario_id=$documento";

      $sql="INSERT INTO `perfiles_expuestos` (`perfil_expuesto_id`, `umtp_id`, `descripcion`, `usuario_creador`, `usuario_encargado`, `codigo_rotulo`) VALUES (NULL, '{$umtp_id}', '{$descripcion}', '{$usuario_creador}', '{$usuario_encargado}', '{$codigo_rotulo}')";
     

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

