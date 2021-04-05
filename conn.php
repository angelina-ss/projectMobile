<?php

$server = "localhost";
$user = "root";
$password = "";
$database = "sekolah";

$connect = mysqli_connect($server, $user, $password) or die ("Koneksi gagal!");
mysqli_select_db($connect, $database) or die ("Database belum siap!");
?>