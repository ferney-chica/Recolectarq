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

			if (isset($_GET["proyecto"])){
				$proyecto_id=$_GET["proyecto"];
				//echo "conectado";
				mysqli_set_charset($con,"utf8");
				//$query="SELECT * from usuarios WHERE usuario_id<>0";
				$query="SELECT * from umtp WHERE proyectos_proyecto_id={$proyecto_id} ";
					$resultado=$con->query($query) or die (mysqli_error($con));

						$i=0;
						while ($fila=$resultado->fetch_assoc()) {
							//echo $fila['usuario_id'];
							//echo "<br>";
							//echo $fila['nombre'];
							//echo "<br>";
							//echo "indice". $i;
							//echo "<br>";
							$json['umtp'][]=$fila;

							//echo $json['datos'][$i]['usuario_id'];
							//echo $json['datos'][$i]['usuario_id'];

							$i+=1;

						}

						if($i==0){
							//echo "Entro acá   hay resultados";
						$results["umtp_id"]=-1;
						$results["area"]='';
						$results["altura"]='';
						$results['latitud']='';
						$results['longitud']='';
						$results["municipio"]='';
						$results['vereda']='';
						$results['sector']='';
						$json['umtp'][]=$results;
						echo json_encode($json,JSON_UNESCAPED_UNICODE);

						}else{
						mysqli_close($con);
						//var_dump($json);
						echo json_encode($json,JSON_UNESCAPED_UNICODE);
						}
			}else{

		    			$results["umtp_id"]=-1;
						$results["area"]='';
						$results["altura"]='';
						$results['latitud']='';
						$results['longitud']='';
						$results["municipio"]='';
						$results['vereda']='';
						$results['sector']='';
						$json['umtp'][]=$results;
						echo json_encode($json,JSON_UNESCAPED_UNICODE);
	}
			
	}
?>
