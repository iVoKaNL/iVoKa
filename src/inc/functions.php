<?php
    function IsNullOrEmptyString($str) { return (strlen($str) < 1 || empty($str)); }
    function IsNotNullOrEmptyString($str) { return (strlen($str) > 0 || !empty($str)); }
?>