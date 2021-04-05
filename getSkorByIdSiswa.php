<?php
    header('Content-type:application/json;charset=utf-8');
    include "conn.php";
    
    if(isset($_POST['email']) && isset($_POST['periode_nilai'])){
        $email=$_POST['email'];
        $periode_nilai=$_POST['periode_nilai'];

        $q=mysqli_query($connect,"SELECT DISTINCT b.nama, c.kelas, c.subjek, d.skor, d.grade 
        FROM user a, siswa b, kelas_subjek c, skor d, ujian e
        WHERE a.email='$email' AND e.periode_nilai='$periode_nilai' AND a.id_siswa=b.id_siswa 
        AND a.id_siswa=d.id_siswa AND b.id_siswa=d.id_siswa AND c.id_kelas_subjek=d.id_kelas_subjek 
        AND d.periode_nilai=e.periode_nilai");
        $response = array();

        if(mysqli_num_rows($q)>0){
            $response["data"] = array();
            while($r=mysqli_fetch_array($q)){
                $siswa = array();
                $siswa["nama"] = $r["nama"];
                $siswa["kelas"] = $r["kelas"];
                $siswa["subjek"] = $r["subjek"];
                $siswa["skor"] = $r["skor"];
                $siswa["grade"] = $r["grade"];
                array_push($response["data"], $siswa);
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
    } else{
        $response["success"]=-1;
        $response["message"]="Data kosong";
        echo json_encode($response);
    }
?>
