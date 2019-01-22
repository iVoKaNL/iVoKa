<?php
    session_start();
    include('functions.php');

    // Database credentials
    define('DBHOST','localhost');
    define('DBNAME','ivoka');
    define('DBUSER','ivoka');
    define('DBPASS','iVoKaPasswoord');

    // Database connection
    $db = new PDO("mysql:host=".DBHOST.";dbname=".DBNAME, DBUSER, DBPASS);
    $db->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

    // Template location
    $template = 'inc/template.php';
?>