<?PHP
header('Content-type=application/json; charset=utf-8');

$hostname="localhost";
$database="recolectarq";
$username="root";
$password="";
$conexion=mysqli_connect($hostname,$username,$password,$database);
	 
    $tipos_estructuras_id=$_POST["tipos_estructuras_id"];
    $topologias_estructuras_id=$_POST["topologias_estructuras_id"];
    $perfiles_expuestos_perfil_expuesto_id=$_POST["perfiles_expuestos_perfil_expuesto_id"];
    $recolecciones_superficiales_recoleccion_superficial_id=$_POST["recolecciones_superficiales_recoleccion_superficial_id"];
    $pozos_pozo_id=$_POST["pozos_pozo_id"];
    $descripcion=$_POST["descripcion"];
    $punto_conexo=$_POST["punto_conexo"];
    $utmx=$_POST["utmx"];
    $utmy=$_POST["utmy"];
    $latitud=$_POST["latitud"];
    $longitud=$_POST["longitud"];
    $dimension=$_POST["dimension"];
    $entorno=$_POST["entorno"];
    $intervencion=$_POST["intervencion"];

      //$sql="UPDATE usuarios SET nombre= '{$nombre}', apellido= '{$apellido}' WHERE usuario_id=$documento";



switch ($intervencion) {
    case "PS":
        $sql="INSERT INTO `estructuras_arqueologicas` (`estructuras_arqueologicas_id`, `tipos_estructuras_id`, `topologias_estructuras_id`, `perfiles_expuestos_perfil_expuesto_id`, `recolecciones_superficiales_recoleccion_superficial_id`, `pozos_pozo_id`, `descripcion`, `punto_conexo`, `utmx`, `utmy`, `latitud`, `longitud`, `dimension`, `entorno`, `intervencion`) VALUES (NULL, '{$tipos_estructuras_id}', '{$topologias_estructuras_id}', NULL, NULL, '{$pozos_pozo_id}', '{$descripcion}', '{$punto_conexo}', '{$utmx}', '{$utmy}', '{$latitud}', '{$longitud}', '{$dimension}', '{$entorno}', '{$intervencion}')";
        break;
    case "RS":
    $sql="INSERT INTO `estructuras_arqueologicas` (`estructuras_arqueologicas_id`, `tipos_estructuras_id`, `topologias_estructuras_id`, `perfiles_expuestos_perfil_expuesto_id`, `recolecciones_superficiales_recoleccion_superficial_id`, `pozos_pozo_id`, `descripcion`, `punto_conexo`, `utmx`, `utmy`, `latitud`, `longitud`, `dimension`, `entorno`, `intervencion`) VALUES (NULL, '{$tipos_estructuras_id}', '{$topologias_estructuras_id}', NULL, '{$recolecciones_superficiales_recoleccion_superficial_id}',NULL, '{$descripcion}', '{$punto_conexo}', '{$utmx}', '{$utmy}', '{$latitud}', '{$longitud}', '{$dimension}', '{$entorno}', '{$intervencion}')";
        break;

    case "PE":
    $sql="INSERT INTO `estructuras_arqueologicas` (`estructuras_arqueologicas_id`, `tipos_estructuras_id`, `topologias_estructuras_id`, `perfiles_expuestos_perfil_expuesto_id`, `recolecciones_superficiales_recoleccion_superficial_id`, `pozos_pozo_id`, `descripcion`, `punto_conexo`, `utmx`, `utmy`, `latitud`, `longitud`, `dimension`, `entorno`, `intervencion`) VALUES (NULL, '{$tipos_estructuras_id}', '{$topologias_estructuras_id}', '{$perfiles_expuestos_perfil_expuesto_id}',NULL ,NULL, '{$descripcion}', '{$punto_conexo}', '{$utmx}', '{$utmy}', '{$latitud}', '{$longitud}', '{$dimension}', '{$entorno}', '{$intervencion}')";
        break;
}


      
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

