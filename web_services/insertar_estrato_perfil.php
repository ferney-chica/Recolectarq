<?PHP
header('Content-type=application/json; charset=utf-8');

$hostname="localhost";
$database="recolectarq";
$username="root";
$password="";
$conexion=mysqli_connect($hostname,$username,$password,$database);
	 
	$texturas_estratos_textura_estrato_id=$_POST["texturas_estratos_textura_estrato_id"];
	$tipos_estratos_tipo_estrato_id=$_POST["tipos_estratos_tipo_estrato_id"];
	$estructuras_estratos_estructura_estrato_id=$_POST["estructuras_estratos_estructura_estrato_id"];
    $perfiles_expuestos_perfil_expuesto_id=$_POST["perfiles_expuestos_perfil_expuesto_id"];
    $profundidad=$_POST["profundidad"];
    $color=$_POST["color"];
    $observacion=$_POST["observacion"];
    $codigo_rotulo=$_POST["codigo_rotulo"];

      //$sql="UPDATE usuarios SET nombre= '{$nombre}', apellido= '{$apellido}' WHERE usuario_id=$documento";

      $sql="INSERT INTO `estratos_perfiles` (`estrato_perfil_id`, `texturas_estratos_textura_estrato_id`, `estructuras_estratos_estructura_estrato_id`, `tipos_estratos_tipo_estrato_id`, `perfiles_expuestos_perfil_expuesto_id`, `profundidad`, `color`, `observacion`, `codigo_rotulo`) VALUES (NULL, '{$texturas_estratos_textura_estrato_id}',  '{$estructuras_estratos_estructura_estrato_id}','{$tipos_estratos_tipo_estrato_id}', '{$perfiles_expuestos_perfil_expuesto_id}', '{$profundidad}', '{$color}', '{$observacion}', '{$codigo_rotulo}')";


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

