<?PHP
header('Content-type=application/json; charset=utf-8');

$hostname="localhost";
$database="recolectarq";
$username="root";
$password="";
$conexion=mysqli_connect($hostname,$username,$password,$database);

	$origen = $_POST["origen"];
	$umtp_id = $_POST["umtp_id"];
	

	switch ($origen) {
	    case 2:

			$ambito_visibilidad_descripcion = $_POST["ambito_visibilidad_descripcion"];
			$puntuales_visibilidades_id = $_POST["puntuales_visibilidades_id"];
			$lineales_visibilidades_id = $_POST["lineales_visibilidades_id"];
			$abanicos_visibilidades_id = $_POST["abanicos_visibilidades_id"];
			$circulares_visibilidades_id = $_POST["circulares_visibilidades_id"];
			$condicion_edafologica = $_POST["condicion_edafologica"];
			$entorno_arqueologico = $_POST["entorno_arqueologico"];

	        $sql="UPDATE `planes_manejos` SET `ambito_visibilidad_descripcion`='{$ambito_visibilidad_descripcion}',`puntuales_visibilidades_id`='{$puntuales_visibilidades_id}',`lineales_visibilidades_id`='{$lineales_visibilidades_id}',`abanicos_visibilidades_id`='{$abanicos_visibilidades_id}',`circulares_visibilidades_id`='{$circulares_visibilidades_id}', `condicion_edafologica`='{$condicion_edafologica}', `entorno_arqueologico`='{$entorno_arqueologico}' WHERE `umtp_id`='{$umtp_id}'";
	        break;
	    case 3:

	    	$estado_conservacion = $_POST["estado_conservacion"];
			$grados_alteraciones_id = $_POST["grados_alteraciones_id"];
			$causas_alteraciones_id = $_POST["causas_alteraciones_id"];
			$agentes_alteraciones_id = $_POST["agentes_alteraciones_id"];
			$regimenes_propiedades_id = $_POST["regimenes_propiedades_id"];
			$grados_protecciones_id = $_POST["grados_protecciones_id"];
			$modos_protecciones_id = $_POST["modos_protecciones_id"];
			$protecciones_fisicas_id = $_POST["protecciones_fisicas_id"];


	        $sql="UPDATE `planes_manejos` SET `estado_conservacion`='{$estado_conservacion}',`grados_alteraciones_id`='{$grados_alteraciones_id}',`causas_alteraciones_id`='{$causas_alteraciones_id}',`agentes_alteraciones_id`='{$agentes_alteraciones_id}',`regimenes_propiedades_id`='{$regimenes_propiedades_id}', `grados_protecciones_id`='{$grados_protecciones_id}', `modos_protecciones_id`='{$modos_protecciones_id}', `protecciones_fisicas_id`='{$protecciones_fisicas_id}' WHERE `umtp_id`='{$umtp_id}'";
	        break;
	    case 4:
	        $sintesis_valoracion_evidencia = $_POST["sintesis_valoracion_evidencia"];
			$hipotesis = $_POST["hipotesis"];
			$valoracion_bien = $_POST["valoracion_bien"];
			$cautela = $_POST["cautela"];
			$referencia_impacto = $_POST["referencia_impacto"];
			$tipos_impactos_id = $_POST["tipos_impactos_id"];
			$impactos_id = $_POST["impactos_id"];


	        $sql="UPDATE `planes_manejos` SET `sintesis_valoracion_evidencia`='{$sintesis_valoracion_evidencia}',`hipotesis`='{$hipotesis}',`valoracion_bien`='{$valoracion_bien}',`cautela`='{$cautela}',`referencia_impacto`='{$referencia_impacto}', `tipos_impactos_id`='{$tipos_impactos_id}', `impactos_id`='{$impactos_id}' WHERE `umtp_id`='{$umtp_id}'";
	        
	        break;
	    case 5:
	        $medida_preventiva = $_POST["medida_preventiva"];
			$actuacion_recomendada = $_POST["actuacion_recomendada"];
			$ejecucion_medida_preventiva = $_POST["ejecucion_medida_preventiva"];
			$medida_correctora = $_POST["medida_correctora"];
			$actuacion_minima = $_POST["actuacion_minima"];
			$justificacion = $_POST["justificacion"];
			$ejecucion_medida_correctora = $_POST["ejecucion_medida_correctora"];
			$revision = $_POST["revision"];
			$intervenciones_recomendadas_id = $_POST["intervenciones_recomendadas_id"];


	        $sql="UPDATE `planes_manejos` SET `medida_preventiva`='{$medida_preventiva}',`actuacion_recomendada`='{$actuacion_recomendada}',`ejecucion_medida_preventiva`='{$ejecucion_medida_preventiva}',`medida_correctora`='{$medida_correctora}',`actuacion_minima`='{$actuacion_minima}', `justificacion`='{$justificacion}', `ejecucion_medida_correctora`='{$ejecucion_medida_correctora}', `revision`='{$revision}', `intervenciones_recomendadas_id`='{$intervenciones_recomendadas_id}' WHERE `umtp_id`='{$umtp_id}'";
	        
	        break;
	}
      
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

