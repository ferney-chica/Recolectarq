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

		if (isset($_GET["estructuras_arqueologicas_id"])){

			$estructuras_arqueologicas_id=$_GET['estructuras_arqueologicas_id'];

			mysqli_set_charset($con,"utf8");
			$query="SELECT `id`,`estructuras_arqueologicas_id`, `tipos_materiales_id`, (SELECT nombre FROM tipos_materiales as t WHERE t.id= materiales.tipos_materiales_id) as nombre_tipo_material FROM `materiales_estructuras_arqueologicas` AS materiales WHERE estructuras_arqueologicas_id='{$estructuras_arqueologicas_id}'";




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
						$json['materiales_estructuras_arqueologicas'][]=$fila;

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
					$results["id"]=0;
					$results["estructuras_arqueologicas_id"]=0;
					$results["tipos_materiales_id"]=0;
					$results["nombre_tipo_material"]='null';
			
					$json['materiales_estructuras_arqueologicas'][]=$results;
					echo json_encode($json,JSON_UNESCAPED_UNICODE);
				}
		}else{

				//echo "Entro acà";
			$results["id"]=0;
			$results["estructuras_arqueologicas_id"]=0;
			$results["tipos_materiales_id"]=0;
			$results["nombre_tipo_material"]='null';
			
			$json['materiales_estructuras_arqueologicas'][]=$results;
			echo json_encode($json,JSON_UNESCAPED_UNICODE);
		}		
	}
?>
