<?php
require 'lib/config.php';

if (g56::get('POST.ucret_id')  == "") { // boş mu ?
	g56::set('SESSION.error', "ucret bilgisini doldurmanız zorunludur.");
	return g56::call('etkinlikadim4.php');
}

$ucret = new g56('UCRET');
$ucret->load("ucret_id = '" . g56::get('POST.ucret_id') . "'");

g56::set('SESSION.ucret_id', $ucret->ucret_id);
g56::set('SESSION.ucret_ad', $ucret->ad);

// şu an ki etkinlik alma sayfasından gideceğimizi oturuma gömelim

if (g56::exists('SESSION.member_id')) {
	g56::call('rezerveadd.php');
} else {
	g56::set('SESSION.onceki', 'true'); 
	g56::call('userlogin.php');
}
?>
