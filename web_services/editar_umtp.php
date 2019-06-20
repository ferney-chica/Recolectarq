<?PHP
header('Content-type=application/json; charset=utf-8');

$hostname="localhost";
$database="recolectarq";
$username="root";
$password="";
$conexion=mysqli_connect($hostname,$username,$password,$database);
	$umtp_id = $_POST["umtp_id"];
	$tipos_relieves_id = $_POST["tipos_relieves_id"];
	$geoforma_id = $_POST["geoforma_id"];
	$vegetaciones_id = $_POST["vegetaciones_id"];
	$dedicaciones_entornos_id = $_POST["dedicaciones_entornos_id"];
	$numero = $_POST["numero"];
	$largo = $_POST["largo"];
	$ancho = $_POST["ancho"];
	$area = $_POST["area"];
	$altura = $_POST["altura"];
	$utmx = $_POST["utmx"];
	$utmy = $_POST["utmy"];
	$latitud = $_POST["latitud"];
	$longitud = $_POST["longitud"];
	$municipio = $_POST["municipio"];
	$departamento = $_POST["departamento"];
	$vereda = $_POST["vereda"];
	$sector = $_POST["sector"];
	$accesos = $_POST["accesos"];
	$zona_incluyente = $_POST["zona_incluyente"];
	$codigo_rotulo = $_POST["codigo_rotulo"];
	//$documento = 1;
	//$nombre = "nombre";
	//$apellido = "documento";



	//$imagen = $_POST["imagen"];
	//$path = "imagenes/$nombre.jpg";
	//$url = "http://$hostname_localhost/ejemploBDRemota/$path";
	//$url = "imagenes/".$nombre.".jpg";
	//file_put_contents($path,base64_decode($imagen));
	//$bytesArchivo=file_get_contents($path);
	//$sql="UPDATE usuarios SET nombre= ? , profesion= ?, imagen=?, ruta_imagen=? WHERE documento=?";
	//$sql="UPDATE usuarios SET nombre= ?, apellido= ? WHERE usuario_id=?";
      $sql="UPDATE `umtp` SET `tipos_relieves_id` = '{$tipos_relieves_id}', `geoforma_id`='{$geoforma_id}', `vegetaciones_id` = '{$vegetaciones_id}', `dedicaciones_entornos_id` = '{$dedicaciones_entornos_id}', `numero` = '{$numero}', `largo` = '{$largo}', `ancho` = '{$ancho}', `area` = '{$area}', `altura` = '{$altura}', `utmx` = '{$utmx}', `utmy` = '{$utmy}', `latitud` = \"{$latitud}\"\", `longitud` = \"{$longitud}\"\", `municipio` = '{$municipio}', `departamento` = '{$departamento}', `vereda` = '{$vereda}', `sector` = '{$sector}', `accesos` = '{$accesos}', `zona_incluyente` = '{$zona_incluyente}', `codigo_rotulo` = '{$codigo_rotulo}' WHERE `umtp`.`umtp_id` = '{$umtp_id}'";
      	//echo $sql;
	$stm=$conexion->prepare($sql);
	//$stm->bind_param('ssssi',$nombre,$profesion,$bytesArchivo,$url,$documento);
	//$stm->bind_param('ssssi', $nombre, $apellido, $documento);
		
	if($stm->execute()){
		echo "actualiza";
	}else{
		echo "noActualiza";
	}
	mysqli_close($conexion);
?>

