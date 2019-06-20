<?PHP
header('Content-type=application/json; charset=utf-8');

$hostname="localhost";
$database="recolectarq";
$username="root";
$password="";
$conexion=mysqli_connect($hostname,$username,$password,$database);
	 
    $estructuras_arqueologicas_id=$_POST["estructuras_arqueologicas_id"];
    $tipos_materiales_id=$_POST["tipos_materiales_id"];
    

      //$sql="UPDATE usuarios SET nombre= '{$nombre}', apellido= '{$apellido}' WHERE usuario_id=$documento";

      $sql="INSERT INTO `materiales_estructuras_arqueologicas` (`id`, `estructuras_arqueologicas_id`, `tipos_materiales_id`) VALUES (NULL, '{$estructuras_arqueologicas_id}', '{$tipos_materiales_id}')";




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
