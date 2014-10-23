<?php
$con = mysqli_connect("127.0.0.1:3306/","root","Gl@ss2014","glass_data");

// Check connection
if (mysqli_connect_errno()) {
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
}
$response = array();
$data = array();
$result = mysqli_query($con,"SELECT * FROM contextual_data");

while($row = mysqli_fetch_array($result)) {
	$username = $row['username'];
	$lineNumber = $row['line_number'];
	$fileName = $row['file_name'];
	$projectName = $row['project_name'];
	$users[] = array('username' => $username,
					'line_number' => $lineNumber,
					'file_name' => $fileName,
					'project_name' => $projectName);
	
}
$data[] = array('users' => $users);
$response['data'] = $data;

echo json_encode($response);

mysqli_close($con);
?>