<?php
	header('Content-type:application/json;charset=utf-8');
	include "conn.php";

    if(isset($_POST['id_siswa']) && isset($_POST['email']) && isset($_POST['password'])){
        $id_siswa = $_POST['id_siswa'];
        $email = $_POST['email'];
        $password = $_POST['password'];
        
        $q=mysqli_query($connect,"INSERT INTO user(id_siswa, email, password) VALUES('$id_siswa','$email', '$password')");
        $response = array();
        
        if($q){
            $response["success"] = 1;
            $response["message"] = "Data berhasil ditambah";
            echo json_encode($response);
        }
        else{
            $response["success"] = 0;
            $response["message"] = "Data gagal ditambah";
            echo json_encode($response);
        }
    }
    else{
        $response["success"] = -1;
        $response["message"] = "Data kosong";
        echo json_encode($response);
    }
?>
