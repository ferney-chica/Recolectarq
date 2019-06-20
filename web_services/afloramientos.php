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
		mysqli_set_charset($con,"utf8");
		//$query="SELECT * from usuarios WHERE usuario_id<>0";
		$query="SELECT * from afloramientos_elementos";
			$resultado=$con->query($query) or die (mysqli_error($con));

			

				$i=0;
				while ($fila=$resultado->fetch_assoc()) {
					//echo $fila['usuario_id'];
					//echo "<br>";
					//echo $fila['nombre'];
					//echo "<br>";
					//echo "indice". $i;
					//echo "<br>";
					$json['afloramientos'][]=$fila;

					//echo $json['datos'][$i]['usuario_id'];
					//echo $json['datos'][$i]['usuario_id'];

					$i+=1;

				}

				if($i==0){
					//echo "Entro acá   hay resultados";
				$results["id"]='';
				$results["nombre"]='No hay resultados';
				$json['afloramientos'][]=$results;
				echo json_encode($json,JSON_UNESCAPED_UNICODE);

				}else{
				mysqli_close($con);
				//var_dump($json);
				echo json_encode($json,JSON_UNESCAPED_UNICODE);
				}

			
	}
	
?>