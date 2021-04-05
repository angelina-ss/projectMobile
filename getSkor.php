<?php
    header('Content-type:application/json;charset=utf-8');
	include "conn.php";
        
    $q=mysqli_query($connect,"SELECT tanggal_event, detail_event FROM notes");
    $response = array();

    if(mysqli_num_rows($q)>0){
        $response["data"] = array();
        while($r=mysqli_fetch_array($q)){
            $notes = array();
            $notes["tanggal_event"] = $r["tanggal_event"];
            $notes["detail_event"] = $r["detail_event"];
            array_push($response["data"], $notes);
        }
        $response["success"] = 1;
        $response["message"] = "Data siswa berhasil dibaca";
        echo json_encode($response);
    }
    else{
        $response["success"] = 0;
        $response["message"] = "Tidak ada data";
        echo json_encode($response);
    }
?>
