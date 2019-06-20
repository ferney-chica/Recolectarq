<?PHP
$hostname="localhost";
$database="recolectarq";
$username="root";
$password="";

	if(isset($_GET["cedula"])){
		$documento=$_GET["cedula"];
				
		$conexion = mysqli_connect($hostname,$username,$password,$database);
		$sql="DELETE FROM usuarios WHERE usuario_id= ?";
		$stm=$conexion->prepare($sql);
		$stm->bind_param('i',$documento);
			
		if($stm->execute()){
			echo "elimina";
		}else{
			echo "noElimina";
		}
		
		mysqli_close($conexion);
	}
	else{
		echo "noExiste";
	}
?>
