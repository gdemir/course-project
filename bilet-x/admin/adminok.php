<?php
require '../lib/config.php'; 
require '../util.php';

// head // error and session // menu
$head = array('head.htm', 'adminsession.htm', 'menu.htm', 'error.htm');

// body
$template = array('adminok.htm');

// footer
$footer = array('footer.htm');

// page
g56::page($head, $template, $footer);
?>
