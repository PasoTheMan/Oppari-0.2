<?php
include 'db_connect.php';

//Query to select temperature & timestamp & mac
$query = "SELECT humidity, timestamp, mac FROM tbl_testi ORDER BY timestamp DESC LIMIT 1";
$result = array();
$humiArray = array();
$timeArray = array();
$macArray = array();
$response = array();

//Prepare the query
if($stmt = $con->prepare($query)){
	$stmt->execute();
	
	//Bind the fetched data to $temperature
	$stmt->bind_result($humidity, $timestamp, $mac);
	
	//Fetch 1 row at a time					
	while($stmt->fetch()){
		//Populate the arrays
		$humiArray["ilmankosteus"] = $humidity;
		$timeArray["aika"] = $timestamp;
		$macArray["MAC-osoite"] = $mac;
		$result[]=$humiArray;
		$result[]=$timeArray;
		$result[]=$macArray;
	}
	$stmt->close();
	$response["success"] = 1;
	$response["data"] = $result;
	}else{
	//Some error while fetching data
	$response["error"] = 0;
	$response["message"] = mysqli_error($con);}
//Display JSON response
echo json_encode($response);
 
?>