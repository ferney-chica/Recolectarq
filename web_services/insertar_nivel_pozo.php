<?PHP
header('Content-type=application/json; charset=utf-8');

$hostname="localhost";
$database="recolectarq";
$username="root";
$password="";
$conexion=mysqli_connect($hostname,$username,$password,$database);
	 
    $pozos_pozo_id=$_POST["pozos_pozo_id"];
    $numero=$_POST["numero"];
    $profundidad=$_POST["profundidad"];
    $codigo_rotulo=$_POST["codigo_rotulo"];

      //$sql="UPDATE usuarios SET nombre= '{$nombre}', apellido= '{$apellido}' WHERE usuario_id=$documento";

      $sql="INSERT INTO `niveles_pozos` (`nivel_pozo_id`, `pozos_pozo_id`, `numero`, `profundidad`, `codigo_rotulo`) VALUES (NULL, '{$pozos_pozo_id}', '{$numero}', '{$profundidad}', '{$codigo_rotulo}')";

     

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

