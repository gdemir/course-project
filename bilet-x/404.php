<?php
// config
require 'lib/config.php';

// head // look at the body directory
$head = array('head.htm', 'usersession.htm', 'menu.htm', 'error.htm', '404.htm');

// body // look at the gui directory
$template = array('');

// footer // look at the body directory
$footer = array('footer.htm');

// page
g56::page($head, $template, $footer);
?>
