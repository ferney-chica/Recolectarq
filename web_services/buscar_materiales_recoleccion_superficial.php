<?php
header('Content-type=application/json; charset=utf-8');
	
	$s="localhost";
	$u="root";
	$p="";
	$bd="recolectarq";
$json=array();
	$con =new mysqli($s,$u,$p,$bd);
	if(mysqli_connect_errno()){
			echo "no conectado";

			

	}else{

		//echo "conectado";

		if (isset($_GET["recolecciones_superficiales_recoleccion_superficial_id"])){

			$recolecciones_superficiales_recoleccion_superficial_id=$_GET['recolecciones_superficiales_recoleccion_superficial_id'];

			mysqli_set_charset($con,"utf8");
			$query="SELECT `material_recoleccion_id`, `flancos_geograficos_id`, (SELECT nombre FROM flancos_geograficos as F WHERE F.id= materiales.flancos_geograficos_id) as nombre_flanco_geografico, `tipos_materiales_id`, (SELECT nombre FROM tipos_materiales as T WHERE t.id= materiales.tipos_materiales_id) as nombre_tipo_material, `recolecciones_superficiales_recoleccion_superficial_id`, `cantidad`, `observacion`, `codigo_rotulo`, `elemento_diagnostico`, `observacion_elemento_diagnostico` FROM `materiales_recolecciones` as materiales WHERE recolecciones_superficiales_recoleccion_superficial_id='{$recolecciones_superficiales_recoleccion_superficial_id}'";


				$resultado=$con->query($query) or die (mysqli_error($con));

				if ($resultado->num_rows>0) {
					
				
					$i=0;
					while ($fila=$resultado->fetch_assoc()) {
						//echo $fila['usuario_id'];
						//echo "<br>";
						//echo $fila['nombre'];
						//echo "<br>";
						//echo "indice". $i;
						//echo "<br>";
						$json['materiales_recoleccion'][]=$fila;

						//echo $json['datos'][$i]['usuario_id'];
						//echo $json['datos'][$i]['usuario_id'];

						$i+=1;

					}
					mysqli_close($con);
					//var_dump($json);
					echo json_encode($json,JSON_UNESCAPED_UNICODE);

					//echo "El numero de registros es: " . $resultado->num_rows;
				}else
				{
					$results["material_recoleccion_id"]=0;
					$results["flancos_geograficos_id"]=0;
					$results["nombre_flanco_geografico"]='';
					$results["tipos_materiales_id"]=0;
					$results["nombre_tipo_material"]='null';
					$results["recolecciones_superficiales_recoleccion_superficial_id"]=0;
					$results["cantidad"]=0;
					$results["observacion"]='';
					$results["codigo_rotulo"]='';
					$results["elemento_diagnostico"]='';
					$results["observacion_elemento_diagnostico"]='';
					$json['materiales_recoleccion'][]=$results;
					echo json_encode($json,JSON_UNESCAPED_UNICODE);
				}
		}else{

				//echo "Entro acà";
			$results["material_recoleccion_id"]=0;
			$results["flancos_geograficos_id"]=0;
			$results["nombre_flanco_geografico"]='';
			$results["tipos_materiales_id"]=0;
			$results["nombre_tipo_material"]='null';
			$results["recolecciones_superficiales_recoleccion_superficial_id"]=0;
			$results["cantidad"]=0;
			$results["observacion"]='';
			$results["codigo_rotulo"]='';
			$results["elemento_diagnostico"]='';
			$results["observacion_elemento_diagnostico"]='';
			$json['materiales_recoleccion'][]=$results;
			echo json_encode($json,JSON_UNESCAPED_UNICODE);
		}		
	}
?>
