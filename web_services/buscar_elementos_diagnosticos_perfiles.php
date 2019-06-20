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


			$query="SELECT p.perfil_expuesto_id as intervencion,(SELECT nombre FROM tipos_materiales WHERE id= me.tipos_materiales_id) as tipoElemento, me.cantidad, me.observacion_elemento_diagnostico as descripcion, 'M' as tipo FROM materiales_estratos_perfiles as me INNER JOIN estratos_perfiles as ep ON (me.estratos_perfiles_estrato_perfil_id=ep.estrato_perfil_id) INNER JOIN `perfiles_expuestos` as p ON (ep.perfiles_expuestos_perfil_expuesto_id=p.perfil_expuesto_id) INNER JOIN umtp as u ON (p.umtp_id=u.umtp_id) WHERE u.umtp_id='{$umtp_id}' AND me.elemento_diagnostico='Sí' UNION SELECT p.perfil_expuesto_id as intervencion,(select nombre from tipos_estructuras where id=ea.tipos_estructuras_id) as tipoElemento, 0 as cantidad, ea.descripcion as descripcion, 'E' as tipo FROM estructuras_arqueologicas as ea INNER JOIN perfiles_expuestos as p ON (ea.perfiles_expuestos_perfil_expuesto_id=p.perfil_expuesto_id) INNER JOIN umtp u ON (p.umtp_id=u.umtp_id) WHERE u.umtp_id='{$umtp_id}'";

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
						$json['elementos_diagnosticos_perfiles'][]=$fila;

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
					$results["intervencion"]=0;
					$results["tipoElemento"]='null';
					$results["cantidad"]=0;
					$results["descripcion"]='null';
					$results["tipo"]='null';
					$json['elementos_diagnosticos_perfiles'][]=$results;
					echo json_encode($json,JSON_UNESCAPED_UNICODE);
				}
		}else{

			$results["intervencion"]=0;
					$results["tipoElemento"]='null';
					$results["cantidad"]=0;
					$results["descripcion"]='null';
					$results["tipo"]='null';
					$json['elementos_diagnosticos_perfiles'][]=$results;
					echo json_encode($json,JSON_UNESCAPED_UNICODE);
		}		
	}
?>
