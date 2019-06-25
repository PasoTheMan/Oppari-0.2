<?php
define('DB_USER', "npftr3kZdD"); // db user
define('DB_PASSWORD', "QkSPUI14JB"); // db password (mention your db password here)
define('DB_DATABASE', "npftr3kZdD"); // database name
define('DB_SERVER', "remotemysql.com"); // db server
 
$con = mysqli_connect(DB_SERVER,DB_USER,DB_PASSWORD,DB_DATABASE);
 
if ($con === false){
	die("ERROR: Could not connect. " . mysqli_connect_error());
}

$sql = "CREATE TABLE persons(
    id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(30) NOT NULL,
    last_name VARCHAR(30) NOT NULL,
    email VARCHAR(70) NOT NULL UNIQUE
)";

if (mysqli_query($con, $sql)){
	echo "Table created succesfully!";
}
else{
	echo "ERROR " . mysqli_error($con);
}

?>