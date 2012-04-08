<?php
// config
require '../lib/config.php';

// head // error and session // menu
$head = array('head.htm', 'adminsession.htm', 'menu.htm', 'adminmenu.htm', 'error.htm');

// body
$template = array('ok.htm');

// footer
$footer = array('footer.htm');

// page
g56::page($head, $template, $footer);
?>


