<?PHP
header('Content-type=application/json; charset=utf-8');

$hostname="localhost";
$database="recolectarq";
$username="root";
$password="";
$conexion=mysqli_connect($hostname,$username,$password,$database);
	 
    $tipos_muestras_id=$_POST["tipos_muestras_id"];
    $tipos_materiales_id=$_POST["tipos_materiales_id"];
    $perfiles_expuestos_perfil_expuesto_id=$_POST["perfiles_expuestos_perfil_expuesto_id"];
    $recolecciones_superficiales_recoleccion_superficial_id=$_POST["recolecciones_superficiales_recoleccion_superficial_id"];
    $pozos_pozo_id=$_POST["pozos_pozo_id"];
    $argumentacion=$_POST["argumentacion"];
    $destino=$_POST["destino"];
    $contexto=$_POST["contexto"];
    $intervencion=$_POST["intervencion"];

      //$sql="UPDATE usuarios SET nombre= '{$nombre}', apellido= '{$apellido}' WHERE usuario_id=$documento";



switch ($intervencion) {
    case "PS":
        $sql="INSERT INTO `muestras` (`muestra_id`, `tipos_muestras_id`, `tipos_materiales_id`, `perfiles_expuestos_perfil_expuesto_id`, `recolecciones_superficiales_recoleccion_superficial_id`, `pozos_pozo_id`, `argumentacion`, `destino`, `contexto`, `intervencion`) VALUES (NULL, '{$tipos_muestras_id}', '{$tipos_materiales_id}', NULL, NULL, '{$pozos_pozo_id}', '{$argumentacion}', '{$destino}', '{$contexto}', '{$intervencion}')";
        break;
    case "RS":
    $sql="INSERT INTO `muestras` (`muestra_id`, `tipos_muestras_id`, `tipos_materiales_id`, `perfiles_expuestos_perfil_expuesto_id`, `recolecciones_superficiales_recoleccion_superficial_id`, `pozos_pozo_id`, `argumentacion`, `destino`, `contexto`, `intervencion`) VALUES (NULL, '{$tipos_muestras_id}', '{$tipos_materiales_id}', NULL,'{$recolecciones_superficiales_recoleccion_superficial_id}', NULL,  '{$argumentacion}', '{$destino}', '{$contexto}', '{$intervencion}')";
        break;
    case "PE":
    $sql="INSERT INTO `muestras` (`muestra_id`, `tipos_muestras_id`, `tipos_materiales_id`, `perfiles_expuestos_perfil_expuesto_id`, `recolecciones_superficiales_recoleccion_superficial_id`, `pozos_pozo_id`, `argumentacion`, `destino`, `contexto`, `intervencion`) VALUES (NULL, '{$tipos_muestras_id}', '{$tipos_materiales_id}','{$perfiles_expuestos_perfil_expuesto_id}', NULL, NULL,  '{$argumentacion}', '{$destino}', '{$contexto}', '{$intervencion}')";
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

