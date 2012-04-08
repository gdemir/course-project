<?php 

// config
require 'lib/config.php';
require 'util.php';

g56::clear('SESSION.etkinlik_id');
g56::clear('SESSION.yer_id');
g56::clear('SESSION.ucret_id');
g56::clear('SESSION.tarih');
g56::clear('SESSION.salon_id');
g56::clear('SESSION.koltuk_id');

// head // look at the body directory
$head = array('head2.htm', 'usersession.htm', 'menu.htm', 'error.htm');

// body // look at the gui directory
$template = array('slide.htm', 'hotticket.htm', 'index.htm');

// footer // look at the body directory
$footer = array('footer.htm');

// page
g56::page($head, $template, $footer);
?>





