<?php
header('Content-type=application/json; charset=utf-8');
$hostname="localhost";
$database="recolectarq";
$username="root";
$password="";
$json=array();

	if (isset($_GET["user"]) &&  isset($_GET["pwd"])){
		$user=$_GET['user'];
		$pwd=$_GET['pwd'];

		$conexion=mysqli_connect($hostname, $username, $password, $database);
		mysqli_set_charset($conexion,"utf8");
		$consulta="SELECT usuario_id, nombre, apellido, contrasena from recolectarq.usuarios WHERE usuario_id= '{$user}' AND contrasena='{$pwd}'";
		$resultado=mysqli_query($conexion,$consulta);

		if ($consulta) {
			if ($reg=mysqli_fetch_array($resultado)) {
				$json['datos'][]=$reg;
				

			}
			mysqli_close($conexion);
			echo json_encode($json);
		}else{

			$results["usuario_id"]='';
			$results["nombre"]='';
			$results["contrasena"]='';
			$json['datos'][]=$results;
			echo json_encode($json,JSON_UNESCAPED_UNICODE);
		}
	}else{

		    $results["usuario_id"]='';
			$results["nombre"]='';
			$results["contrasena"]='';
			$json['datos'][]=$results;
			echo json_encode($json,JSON_UNESCAPED_UNICODE);
	}
?>
