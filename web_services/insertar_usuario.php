<?php
header('Content-type=application/json; charset=utf-8');
	
	$s="localhost";
	$u="root";
	$p="";
	$bd="recolectarq";
$json=array();
	$con =new mysqli($s,$u,$p,$bd);
	mysqli_set_charset($con,"utf8");
	if(mysqli_connect_errno()){
			echo "no conectado";

			

	}else{

		//echo "conectado";

		if (isset($_GET["cedula"]) && isset($_GET["nombre"])&& isset($_GET["apellido"])&&isset($_GET["contrasena"])){

			$cedula=$_GET['cedula'];
			$nombre=utf8_encode($_GET['nombre']);
			$apellido=utf8_encode($_GET['apellido']);
			$contrasena=$_GET['contrasena'];

			
			$query="INSERT INTO usuarios (usuario_id, nombre, apellido, contrasena) VALUES ('{$cedula}', '{$nombre}', '{$apellido}', '{$contrasena}') ";

				$resultado=$con->query($query) or die (mysqli_error($con));

				if ($resultado) {
					
					$consulta="SELECT * FROM usuarios WHERE usuario_id='{$cedula}'";
					$resultado=$con->query($consulta) or die (mysqli_error($con));
					$i=0;
					while ($fila=$resultado->fetch_assoc()) {
						//echo $fila['usuario_id'];
						//echo "<br>";
						//echo $fila['nombre'];
						//echo "<br>";
						//echo "indice". $i;
						//echo "<br>";
						$json['usuario_registrado'][]=$fila;

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
					$results["usuario_id"]='';
					$results["nombre"]='null';
					$results["contrasena"]='';
					$json['usuario_registrado'][]=$results;
					echo json_encode($json,JSON_UNESCAPED_UNICODE);
				}
		}else{

			$results["usuario_id"]='';
			$results["nombre"]='No hay resultados';
			$results["contrasena"]='';
			$json['usuario_registrado'][]=$results;
			echo json_encode($json,JSON_UNESCAPED_UNICODE);
		}		
	}
?>
