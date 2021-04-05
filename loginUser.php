<?php
header('Content-type:application/json;charset=utf-8');
include "conn.php";
$con = mysqli_connect('localhost', 'root', '', 'sekolah');

if(isset($_POST['email']) && isset($_POST['password'])) {
    $email = $_POST['email'];
    $password = $_POST['password'];

    $sql="SELECT * FROM user WHERE email='$email' AND password='$password'";
    $check = mysqli_fetch_array(mysqli_query($con, $sql));
    if (isset($check)) {
        echo 'Anda berhasil login';

    } else {
        echo 'Silahkan coba lagi';
    }
}

?>