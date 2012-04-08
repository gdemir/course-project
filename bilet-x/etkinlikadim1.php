<?php
// config
require 'lib/config.php';
require 'util.php';

// head // look at the body directory
$head = array('head.htm', 'usersession.htm', 'menu.htm', 'error.htm');

// body // look at the gui directory
$template = array('etkinlikadim1.htm');

// footer // look at the body directory
$footer = array('footer.htm');

// page
g56::page($head, $template, $footer);
?>
