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

		if (isset($_GET["umtp_id"])){

			$umtp_id=$_GET['umtp_id'];

			mysqli_set_charset($con,"utf8");
			$query="SELECT * FROM `recolecciones_superficiales` WHERE umtp_id= '{$umtp_id}'";
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
						$json['recolecciones'][]=$fila;

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
					$results["recoleccion_superficial_id"]=0;
					$results["umtp_id"]=0;
					$results["descripcion"]='null';
					$results["usuario_creador"]=0;
					$results["usuario_encargado"]=0;
					$results["codigo_rotulo"]='';
					$json['recolecciones'][]=$results;
					echo json_encode($json,JSON_UNESCAPED_UNICODE);
				}
		}else{

			$results["recoleccion_superficial_id"]=0;
			$results["umtp_id"]=0;
			$results["descripcion"]='null';
			$results["usuario_creador"]=0;
			$results["usuario_encargado"]=0;
			$results["codigo_rotulo"]='';
			$json['recolecciones'][]=$results;
			echo json_encode($json,JSON_UNESCAPED_UNICODE);
		}		
	}
?>
