<?PHP
header('Content-type=application/json; charset=utf-8');

$hostname="localhost";
$database="recolectarq";
$username="root";
$password="";
$conexion=mysqli_connect($hostname,$username,$password,$database);
	 
	$usos_vias_naturales_id=$_POST["usos_vias_naturales_id"];
	$tipos_vias_naturales_id=$_POST["tipos_vias_naturales_id"];
	$fisiografias_elementos_id=$_POST["fisiografias_elementos_id"];
    $hidrografias_elementos_id=$_POST["hidrografias_elementos_id"];
    $afloramientos_elementos_id=$_POST["afloramientos_elementos_id"];
    $umtp_id=$_POST["umtp_id"];
    $elemento_natural_descripcion=$_POST["elemento_natural_descripcion"];
    $vias_naturales_descripcion=$_POST["vias_naturales_descripcion"];

      //$sql="UPDATE usuarios SET nombre= '{$nombre}', apellido= '{$apellido}' WHERE usuario_id=$documento";

      $sql="INSERT INTO `planes_manejos` (`plan_manejo_id`, `intervenciones_recomendadas_id`, `impactos_id`, `tipos_impactos_id`, `protecciones_fisicas_id`, `modos_protecciones_id`, `grados_protecciones_id`, `regimenes_propiedades_id`, `agentes_alteraciones_id`, `causas_alteraciones_id`, `grados_alteraciones_id`, `circulares_visibilidades_id`, `abanicos_visibilidades_id`, `lineales_visibilidades_id`, `puntuales_visibilidades_id`, `usos_vias_naturales_id`, `tipos_vias_naturales_id`, `fisiografias_elementos_id`, `hidrografias_elementos_id`, `afloramientos_elementos_id`, `umtp_id`, `entorno`, `estado_conservacion`, `sintesis_valoracion_evidencia`, `hipotesis`, `valoracion_bien`, `cautela`, `referencia_impacto`, `medida_preventiva`, `actuacion_recomendada`, `ejecucion_medida_preventiva`, `medida_correctora`, `actuacion_minima`, `justificacion`, `ejecucion_medida_correctora`, `revision`, `elemento_natural_descripcion`, `vias_naturales_descripcion`) VALUES (NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{$usos_vias_naturales_id}', '{$tipos_vias_naturales_id}', '{$fisiografias_elementos_id}', '{$hidrografias_elementos_id}', '{$afloramientos_elementos_id}', '{$umtp_id}', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{$elemento_natural_descripcion}', '{$vias_naturales_descripcion}')";




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

