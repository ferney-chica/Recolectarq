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
			$query="SELECT `muestra_id`, `tipos_muestras_id`, (SELECT nombre FROM tipos_muestras as tm WHERE tm.id=m.tipos_muestras_id) as tipos_muestras_nombre, `tipos_materiales_id`, (SELECT nombre FROM tipos_materiales as tma WHERE tma.id=m.tipos_materiales_id) as tipos_materiales_nombre, `perfiles_expuestos_perfil_expuesto_id`, `recolecciones_superficiales_recoleccion_superficial_id`, `pozos_pozo_id`, `argumentacion`, `destino`, `contexto`, `intervencion` FROM `muestras` as m WHERE m.pozos_pozo_id='{$pozos_pozo_id}'";


		}else
		{
			if (isset($_GET["recoleccion_id"])){
				$recoleccion_id=$_GET['recoleccion_id'];

				mysqli_set_charset($con,"utf8");
				$query="SELECT `muestra_id`, `tipos_muestras_id`, (SELECT nombre FROM tipos_muestras as tm WHERE tm.id=m.tipos_muestras_id) as tipos_muestras_nombre, `tipos_materiales_id`, (SELECT nombre FROM tipos_materiales as tma WHERE tma.id=m.tipos_materiales_id) as tipos_materiales_nombre, `perfiles_expuestos_perfil_expuesto_id`, `recolecciones_superficiales_recoleccion_superficial_id`, `pozos_pozo_id`, `argumentacion`, `destino`, `contexto`, `intervencion` FROM `muestras` as m WHERE m.recolecciones_superficiales_recoleccion_superficial_id='{$recoleccion_id}'";

			}else
			{
				if (isset($_GET["perfil_id"])){
					$perfil_id=$_GET['perfil_id'];
					$query="SELECT `muestra_id`, `tipos_muestras_id`, (SELECT nombre FROM tipos_muestras as tm WHERE tm.id=m.tipos_muestras_id) as tipos_muestras_nombre, `tipos_materiales_id`, (SELECT nombre FROM tipos_materiales as tma WHERE tma.id=m.tipos_materiales_id) as tipos_materiales_nombre, `perfiles_expuestos_perfil_expuesto_id`, `recolecciones_superficiales_recoleccion_superficial_id`, `pozos_pozo_id`, `argumentacion`, `destino`, `contexto`, `intervencion` FROM `muestras` as m WHERE m.perfiles_expuestos_perfil_expuesto_id='{$perfil_id}'";


				}
		    }		
	   }

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
						$json['muestras'][]=$fila;

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
					$results["muestra_id"]=0;
					$results["tipos_muestras_id"]=0;
					$results["tipos_muestras_nombre"]='null';
					$results["tipos_materiales_id"]=0;
					$results["tipos_materiales_nombre"]='null';
					$results["perfiles_expuestos_perfil_expuesto_id"]=0;
					$results["recolecciones_superficiales_recoleccion_superficial_id"]=0;
					$results["pozos_pozo_id"]=0;
					$results["argumentacion"]='null';
					$results["destino"]='null';
					$results["contexto"]='null';
					$results["intervencion"]='null';
					$json['muestras'][]=$results;
					echo json_encode($json,JSON_UNESCAPED_UNICODE);
				}




	   
    }
?>
