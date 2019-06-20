<?PHP
header('Content-type=application/json; charset=utf-8');

$hostname="localhost";
$database="recolectarq";
$username="root";
$password="";
$conexion=mysqli_connect($hostname,$username,$password,$database);
	 
    
    $tipoRelieve_id=$_POST["tipoRelieve_id"];
    $largo=$_POST["largo"];
    $ancho=$_POST["ancho"];
    $area=$_POST["area"];
    $altura=$_POST["altura"];
    $utmx=$_POST["utmx"];
    $utmy=$_POST["utmy"];
    $latitud=$_POST["latitud"];
    $longitud=$_POST["longitud"];
    $municipio=$_POST["municipio"];
    $departamento=$_POST["departamento"];
    $vereda= $_POST["vereda"];
    $sector=$_POST["sector"];
    $accesos=$_POST["accesos"];
    $zonasIncluyentes=$_POST["zonasIncluyente"];
    $geoforma=$_POST["geoforma"];
    $vegetaciones=$_POST["vegetaciones"];
    $dedicacion= $_POST["dedicacion"];
    $proyecto_id=$_POST["proyecto_id"];
    $usuario_id=$_POST["usuario_id"];

      $sql="INSERT INTO `umtp` (`umtp_id`, `tipos_relieves_id`, `geoforma_id`, `vegetaciones_id`, `dedicaciones_entornos_id`, `proyectos_proyecto_id`, `numero`, `largo`, `ancho`, `area`, `altura`, `utmx`, `utmy`, `latitud`, `longitud`, `municipio`, `departamento`, `vereda`, `sector`, `accesos`, `zona_incluyente`, `codigo_rotulo`, `yacimiento`, `sitio_potencial`, `usuarios_usuario_id`) VALUES (NULL, '{$tipoRelieve_id}', '{$geoforma}', '{$vegetaciones}', '{$dedicacion}', '{$proyecto_id}', '---', '{$largo}', '{$ancho}', '{$area}', '{$altura}', '{$utmx}', '{$utmy}', \"{$latitud}\"\", \"{$longitud}\"\", '{$municipio}', '{$departamento}', '{$vereda}', '{$sector}', '{$accesos}', '{$zonasIncluyentes}', '---', '0', '0', '{$usuario_id}')";

                                                               



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

