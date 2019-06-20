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

		if (isset($_GET["proyecto"])){

			$proyecto=$_GET['proyecto'];

			mysqli_set_charset($con,"utf8");
			$query="SELECT t2.proyecto_id, t2.nombre as proyecto_nombre, t3.usuario_id, t3.nombre as usuario_nombre, t3.apellido AS usuario_apellido, t4.perfil_id, t4.descripcion as perfil_descripcion FROM `usuarios_proyectos` t1 INNER JOIN `proyectos` t2 ON t1.proyectos_proyecto_id= t2.proyecto_id INNER JOIN `usuarios` t3 ON t1.usuarios_usuario_id=t3.usuario_id INNER JOIN `perfiles` t4 ON t1.perfiles_perfil_id=t4.perfil_id where proyectos_proyecto_id='{$proyecto}'";

				//echo $query;
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
						$json['usuarios_proyectos'][]=$fila;

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
					$results["proyecto_id"]=0;
					$results["proyecto_nombre"]='null';
					$results["usuario_id"]=0;
					$results["usuario_nombre"]='';
					$results["usuario_apellido"]='';
					$results["perfil_id"]=0;
					$results["perfil_descripcion"]='';
					$json['usuarios_proyectos'][]=$results;
					echo json_encode($json,JSON_UNESCAPED_UNICODE);
				}
		}else{

			$results["proyecto_id"]=0;
					$results["proyecto_nombre"]='null';
					$results["usuario_id"]=0;
					$results["usuario_nombre"]='';
					$results["usuario_apellido"]='';
					$results["perfil_id"]=0;
					$results["perfil_descripcion"]='';
					$json['usuarios_proyectos'][]=$results;
			echo json_encode($json,JSON_UNESCAPED_UNICODE);
		}		
	}
?>
