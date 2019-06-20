<?PHP
header('Content-type=application/json; charset=utf-8');

$hostname="localhost";
$database="recolectarq";
$username="root";
$password="";
$conexion=mysqli_connect($hostname,$username,$password,$database);
	 
	$conjunto_material=$_POST["conjunto_material"];
	$dispercion_material=$_POST["dispercion_material"];
	$otro_material=$_POST["otro_material"];
    $tipo_trabajo=$_POST["tipo_trabajo"];
    $condicion_hallazgo=$_POST["condicion_hallazgo"];
    $elemento_no_arqueologico=$_POST["elemento_no_arqueologico"];
    $yacimientos_conexos=$_POST["yacimientos_conexos"];
    $umtp_id=$_POST["umtp_id"];

      //$sql="UPDATE usuarios SET nombre= '{$nombre}', apellido= '{$apellido}' WHERE usuario_id=$documento";

      $sql="INSERT INTO `sitios_potenciales`(`id`, `umtp_id`, `conjunto_material`, `dispercion_material`, `otro_material`, `tipo_trabajo`, `condicion_hallazgo`, `elemento_no_arqueologico`, `yacimientos_conexos`) VALUES (NULL,'{$umtp_id}','{$conjunto_material}','{$dispercion_material}','{$otro_material}','{$tipo_trabajo}','{$condicion_hallazgo}','{$elemento_no_arqueologico}','{$yacimientos_conexos}')";



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

