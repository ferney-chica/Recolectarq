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

		if (isset($_GET["estratos_perfiles_estrato_perfil_id"])){

			$estratos_perfiles_estrato_perfil_id=$_GET['estratos_perfiles_estrato_perfil_id'];

			mysqli_set_charset($con,"utf8");
			$query="SELECT `material_estrato_perfil_id`, `estratos_perfiles_estrato_perfil_id`, `tipos_materiales_id`, (SELECT nombre FROM tipos_materiales as T WHERE t.id= materiales.tipos_materiales_id) as tipos_materiales_nombre, `cantidad`, `observacion`, `elemento_diagnostico`, `observacion_elemento_diagnostico` FROM `materiales_estratos_perfiles` as materiales WHERE estratos_perfiles_estrato_perfil_id ='{$estratos_perfiles_estrato_perfil_id}'";


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
						$json['materiales_estrato_perfil'][]=$fila;

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
					$results["material_estrato_perfil_id"]=0;
					$results["estratos_perfiles_estrato_perfil_id"]=0;
					$results["tipos_materiales_id"]=0;
					$results["tipos_materiales_nombre"]='null';
					$results["cantidad"]=0;
					$results["observacion"]='';
					$results["elemento_diagnostico"]='';
					$results["observacion_elemento_diagnostico"]='';
					$json['materiales_estrato_perfil'][]=$results;
					echo json_encode($json,JSON_UNESCAPED_UNICODE);
				}
		}else{

				//echo "Entro acà";
			$results["material_estrato_perfil_id"]=0;
					$results["estratos_perfiles_estrato_perfil_id"]=0;
					$results["tipos_materiales_id"]=0;
					$results["tipos_materiales_nombre"]='null';
					$results["cantidad"]=0;
					$results["observacion"]='';
					$results["elemento_diagnostico"]='';
					$results["observacion_elemento_diagnostico"]='';
					$json['materiales_estrato_perfil'][]=$results;
					echo json_encode($json,JSON_UNESCAPED_UNICODE);
		}		
	}
?>
