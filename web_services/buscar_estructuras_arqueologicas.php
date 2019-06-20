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

			$query="SELECT `estructuras_arqueologicas_id`, `tipos_estructuras_id`, (SELECT nombre FROM tipos_estructuras as te WHERE te.id=estructura.tipos_estructuras_id) as tipos_estructuras_nombre, `topologias_estructuras_id`, (SELECT nombre FROM topologias_estructuras as pe WHERE pe.id=estructura.topologias_estructuras_id) as topologias_estructuras_nombre, `perfiles_expuestos_perfil_expuesto_id`, `recolecciones_superficiales_recoleccion_superficial_id`, `pozos_pozo_id`, `descripcion`, `punto_conexo`, `utmx`, `utmy`, `latitud`, `longitud`, `dimension`, `entorno`, `intervencion` FROM `estructuras_arqueologicas` as estructura WHERE estructura.pozos_pozo_id='{$pozos_pozo_id}'";

			//echo $pozos_pozo_id.$query;
		}else{

				if (isset($_GET["recoleccion_id"])){

					$recoleccion_id=$_GET['recoleccion_id'];

					$query="SELECT `estructuras_arqueologicas_id`, `tipos_estructuras_id`, (SELECT nombre FROM tipos_estructuras as te WHERE te.id=estructura.tipos_estructuras_id) as tipos_estructuras_nombre, `topologias_estructuras_id`, (SELECT nombre FROM topologias_estructuras as pe WHERE pe.id=estructura.topologias_estructuras_id) as topologias_estructuras_nombre, `perfiles_expuestos_perfil_expuesto_id`, `recolecciones_superficiales_recoleccion_superficial_id`, `pozos_pozo_id`, `descripcion`, `punto_conexo`, `utmx`, `utmy`, `latitud`, `longitud`, `dimension`, `entorno`, `intervencion` FROM `estructuras_arqueologicas` as estructura WHERE recolecciones_superficiales_recoleccion_superficial_id='{$recoleccion_id}'";

					//echo $recoleccion_id.$query;
				}else{
						if (isset($_GET["perfil_id"])){

							$perfil_id=$_GET['perfil_id'];

							$query="SELECT `estructuras_arqueologicas_id`, `tipos_estructuras_id`, (SELECT nombre FROM tipos_estructuras as te WHERE te.id=estructura.tipos_estructuras_id) as tipos_estructuras_nombre, `topologias_estructuras_id`, (SELECT nombre FROM topologias_estructuras as pe WHERE pe.id=estructura.topologias_estructuras_id) as topologias_estructuras_nombre, `perfiles_expuestos_perfil_expuesto_id`, `recolecciones_superficiales_recoleccion_superficial_id`, `pozos_pozo_id`, `descripcion`, `punto_conexo`, `utmx`, `utmy`, `latitud`, `longitud`, `dimension`, `entorno`, `intervencion` FROM `estructuras_arqueologicas` as estructura WHERE perfiles_expuestos_perfil_expuesto_id='{$perfil_id}'";

							//echo $perfil_id.$query;
						}



					}

			
		    }
		    mysqli_set_charset($con,"utf8");
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
						$json['estructuras_arqueologicas'][]=$fila;

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
					$results["estructuras_arqueologicas_id"]=0;
					$results["tipos_estructuras_id"]=0;
					$results["tipos_estructuras_nombre"]='null';
					$results["topologias_estructuras_id"]=0;
					$results["topologias_estructuras_nombre"]='null';
					$results["perfiles_expuestos_perfil_expuesto_id"]=0;
					$results["recolecciones_superficiales_recoleccion_superficial_id"]=0;
					$results["pozos_pozo_id"]=0;
					$results["descripcion"]='null';
					$results["punto_conexo"]='null';
					$results["utmx"]=0;
					$results["utmy"]=0;
					$results["latitud"]='null';
					$results["longitud"]='null';
					$results["dimension"]='null';
					$results["entorno"]='null';
					$results["intervencion"]='--';

					$json['estructuras_arqueologicas'][]=$results;
					echo json_encode($json,JSON_UNESCAPED_UNICODE);
				}












	}
?>
