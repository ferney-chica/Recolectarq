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

		if (isset($_GET["pozos_pozo_id"])){

			$pozos_pozo_id=$_GET['pozos_pozo_id'];

			mysqli_set_charset($con,"utf8");
			$query="SELECT * FROM `niveles_pozos` WHERE pozos_pozo_id ='{$pozos_pozo_id}'";
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
						$json['niveles_pozos'][]=$fila;

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
					$results["nivel_pozo_id"]=0;
					$results["pozos_pozo_id"]=0;
					$results["numero"]='0';
					$results["profundidad"]=0;
					$results["codigo_rotulo"]='';
					$json['niveles_pozos'][]=$results;
					echo json_encode($json,JSON_UNESCAPED_UNICODE);
				}
		}else{

			$results["nivel_pozo_id"]=0;
			$results["pozos_pozo_id"]=0;
			$results["numero"]='0';
			$results["profundidad"]=0;
			$results["codigo_rotulo"]='';
			$json['niveles_pozos'][]=$results;
			echo json_encode($json,JSON_UNESCAPED_UNICODE);
		}		
	}
?>
