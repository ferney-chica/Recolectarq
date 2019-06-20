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

		if (isset($_GET["perfiles_expuestos_perfil_expuesto_id"])){

			$perfiles_expuestos_perfil_expuesto_id=$_GET['perfiles_expuestos_perfil_expuesto_id'];

			mysqli_set_charset($con,"utf8");

			$query="SELECT `estrato_perfil_id`, `texturas_estratos_textura_estrato_id`, (SELECT nombre FROM texturas_estratos as t WHERE t.textura_estrato_id=estratos.texturas_estratos_textura_estrato_id) as textura_estrato_nombre, `estructuras_estratos_estructura_estrato_id`, (SELECT nombre FROM estructuras_estratos as e WHERE e.estructura_estrato_id=estratos.estructuras_estratos_estructura_estrato_id) as estructura_estrato_nombre, `tipos_estratos_tipo_estrato_id`, (SELECT nombre FROM tipos_estratos as t WHERE t.tipo_estrato_id=estratos.tipos_estratos_tipo_estrato_id) as tipo_estrato_nombre,`perfiles_expuestos_perfil_expuesto_id`, `profundidad`, `color`, `observacion`, `codigo_rotulo` FROM `estratos_perfiles` as estratos WHERE estratos.perfiles_expuestos_perfil_expuesto_id='{$perfiles_expuestos_perfil_expuesto_id}'";




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
						$json['estratos_perfiles'][]=$fila;

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
					$results["estrato_perfil_id"]=0;
					$results["texturas_estratos_textura_estrato_id"]=0;
					$results["textura_estrato_nombre"]="null";
					$results["estructuras_estratos_estructura_estrato_id"]=0;
					$results["estructura_estrato_nombre"]="null";
					$results["tipos_estratos_tipo_estrato_id"]=0;
					$results["tipo_estrato_nombre"]="null";
					$results["perfiles_expuestos_perfil_expuesto_id"]=0;
					$results["profundidad"]=0;
					$results["color"]="null";
					$results["observacion"]="null";
					$results["codigo_rotulo"]="null";
					$json['estratos_perfiles'][]=$results;
					echo json_encode($json,JSON_UNESCAPED_UNICODE);
				}
		}else{

			$results["estrato_perfil_id"]=0;
					$results["texturas_estratos_textura_estrato_id"]=0;
					$results["textura_estrato_nombre"]="null";
					$results["estructuras_estratos_estructura_estrato_id"]=0;
					$results["estructura_estrato_nombre"]="null";
					$results["tipos_estratos_tipo_estrato_id"]=0;
					$results["tipo_estrato_nombre"]="null";
					$results["perfiles_expuestos_perfil_expuesto_id"]=0;
					$results["profundidad"]="null";
					$results["color"]="null";
					$results["observacion"]="null";
					$results["codigo_rotulo"]="null";
					$json['estratos_perfiles'][]=$results;
					echo json_encode($json,JSON_UNESCAPED_UNICODE);
		}		
	}
?>
