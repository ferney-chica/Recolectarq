<?php
header('Content-type=application/json; charset=utf-8');
	
	$s="localhost";
	$u="root";
	$p="";
	$bd="recolectarq";
$json=array();
	$con =new mysqli($s,$u,$p,$bd);


	if (isset($_GET["documento"]))
	{
		$documento=$_GET['documento'];
			if(mysqli_connect_errno()){
					echo "no conectado";

					

			}else{

				//echo "conectado";
				mysqli_set_charset($con,"utf8");
				//$query="SELECT * from usuarios WHERE usuario_id<>0";
				$query="SELECT p.proyecto_id, p.usuarios_usuario_id as usuario_creador, up.usuarios_usuario_id, (SELECT per.descripcion from perfiles per WHERE per.perfil_id= up.perfiles_perfil_id) as nombre_perfil, up.perfiles_perfil_id as perfil_id, (SELECT tp.nombre FROM tipos_proyectos as tp WHERE tp.tipo_proyecto_id =p.tipos_proyectos_tipo_proyecto_id) as nombre_tipo_proyecto, p.tipos_proyectos_tipo_proyecto_id,(SELECT fp.nombre FROM fases_proyectos as fp WHERE fp.fase_proyecto_id=p.fases_proyectos_fase_proyecto_id) as nombre_fases_proyectos, p.fases_proyectos_fase_proyecto_id, p.nombre, p.ubicacion, p.fecha_inicio, p.fecha_fin, p.referencias_administrativas, p.aval_cientifico, p.codigo_identificacion FROM proyectos as p INNER JOIN usuarios_proyectos as up ON (p.proyecto_id=up.proyectos_proyecto_id) WHERE up.usuarios_usuario_id={$documento}";


				
				//$query="SELECT * from proyectos WHERE usuarios_usuario_id=1";
					$resultado=$con->query($query) or die (mysqli_error($con));

					

						$i=0;
						while ($fila=$resultado->fetch_assoc()) {
							//echo $fila['usuario_id'];
							//echo "<br>";
							//echo $fila['nombre'];
							//echo "<br>";
							//echo "indice". $i;
							//echo "<br>";
							$json['proyectos'][]=$fila;

							//echo $json['datos'][$i]['usuario_id'];
							//echo $json['datos'][$i]['usuario_id'];

							$i+=1;

						}

						if($i==0){
							//echo "Entro acá   hay resultados";
						$results["proyecto_id"]=0;
						$results["usuario_creador"]=0;
						$results["usuarios_usuario_id"]=0;
						$results["nombre_perfil"]=null;
						$results["perfil_id"]=0;
						$results["nombre_tipo_proyecto"]=null;
						$results["tipos_proyectos_tipo_proyecto_id"]=0;
						$results["nombre_fases_proyectos"]=null;
						$results["fases_proyectos_fase_proyecto_id"]=0;
						$results["nombre"]='No hay proyectos para mostrar';
						$results["ubicacion"]='';
						$results["fecha_inicio"]='0000-00-00';
						$results["fecha_fin"]='0000-00-00';
						$results["referencias_administrativas"]='';
						$results["aval_cientifico"]='';
						$results["codigo_identificacion"]='';
						$json['proyectos'][]=$results;
						echo json_encode($json,JSON_UNESCAPED_UNICODE);

						}else{
						mysqli_close($con);
						//var_dump($json);
						echo json_encode($json,JSON_UNESCAPED_UNICODE);
						}

					
			}
	}
?>
