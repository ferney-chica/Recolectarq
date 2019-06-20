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

		if (isset($_GET["perfil_id"])){

			$perfil_id=$_GET['perfil_id'];
			//echo $tipo;
			mysqli_set_charset($con,"utf8");
			$query="SELECT `imagen_perfil_expuesto_id`, `perfiles_expuestos_perfil_expuesto_id`, `tipo`, `nombre` FROM `imagenes_perfil_expuesto` WHERE perfiles_expuestos_perfil_expuesto_id='{$perfil_id}'";
 

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
						$json['imagenes_perfil'][]=$fila;

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
					$results["imagen_perfil_expuesto_id"]=0;
					$results["perfiles_expuestos_perfil_expuesto_id"]=0;
					$results["nombre"]="null";
					$results["tipo"]="null";
					$json['imagenes_perfil'][]=$results;
					echo json_encode($json,JSON_UNESCAPED_UNICODE);
				}
		}else{

			$results["imagen_perfil_expuesto_id"]=0;
					$results["perfiles_expuestos_perfil_expuesto_id"]=0;
					$results["nombre"]="null";
					$results["tipo"]="null";
					$json['imagenes_perfil'][]=$results;
					echo json_encode($json,JSON_UNESCAPED_UNICODE);
		}		
	}
?>
