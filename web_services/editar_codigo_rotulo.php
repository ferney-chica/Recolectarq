<?PHP
header('Content-type=application/json; charset=utf-8');

$hostname="localhost";
$database="recolectarq";
$username="root";
$password="";
$conexion=mysqli_connect($hostname,$username,$password,$database);
	
	$codigo_rotulo = $_POST["codigo_rotulo"];
	$origen = $_POST["origen"];

	switch ($origen) {
    	case "umtp":
        //echo "i es igual a 0";
        $umtp_id = $_POST["umtp_id"];
        	$sql="UPDATE `umtp` SET `codigo_rotulo` = '{$codigo_rotulo}' WHERE `umtp`.`umtp_id` = '{$umtp_id}'";
       	 break;
   	 	case "pozo":
        //echo "i es igual a 0";
        $pozo_id = $_POST["pozo_id"];
        	$sql="UPDATE `pozos` SET `codigo_rotulo` = '{$codigo_rotulo}' WHERE `pozo_id` = '{$pozo_id}'";
        	break;
        case "recoleccion":
        //echo "i es igual a 0";
        $recoleccion_id = $_POST["recoleccion_id"];
        	$sql="UPDATE `recolecciones_superficiales` SET `codigo_rotulo` = '{$codigo_rotulo}' WHERE `recoleccion_superficial_id` = '{$recoleccion_id}'";
        	break;
        case "perfil":
        //echo "i es igual a 0";
        $perfil_id = $_POST["perfil_id"];
        	$sql="UPDATE `perfiles_expuestos` SET `codigo_rotulo` = '{$codigo_rotulo}' WHERE `perfil_expuesto_id` = '{$perfil_id}'";
        	break;
        case "nivelPozo":
        //echo "i es igual a 0";
        $nivel_pozo_id = $_POST["nivel_pozo_id"];
        	$sql="UPDATE `niveles_pozos` SET `codigo_rotulo` = '{$codigo_rotulo}' WHERE `nivel_pozo_id` = '{$nivel_pozo_id}'";
        	break;
        case "estratoPerfil":
        //echo "i es igual a 0";
        $estrato_perfil_id = $_POST["estrato_perfil_id"];
        	$sql="UPDATE `estratos_perfiles` SET `codigo_rotulo` = '{$codigo_rotulo}' WHERE `estrato_perfil_id` = '{$estrato_perfil_id}'";
        	break;
        case "materialRecoleccion":
        //echo "i es igual a 0";
        $material_recoleccion_id = $_POST["material_recoleccion_id"];
        	$sql="UPDATE `materiales_recolecciones` SET `codigo_rotulo` = '{$codigo_rotulo}' WHERE `material_recoleccion_id` = '{$material_recoleccion_id}'";
        	break;
	}

	
      
      	//echo $sql;
	$stm=$conexion->prepare($sql);
	//$stm->bind_param('ssssi',$nombre,$profesion,$bytesArchivo,$url,$documento);
	//$stm->bind_param('ssssi', $nombre, $apellido, $documento);
		
	if($stm->execute()){
		echo "actualiza";
	}else{
		echo "noActualiza";
	}
	mysqli_close($conexion);
?>

