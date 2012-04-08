<?php
require 'lib/config.php';

if (g56::get('POST.tarih') == "") { // boş mu ?
	g56::set('SESSION.error', "tarih bilgisini doldurmanız zorunludur.");
	return g56::call('etkinlikadim1.php');
}

$etkinlik_id = g56::get('SESSION.etkinlik_id');
$date = g56::get('POST.tarih');
$tarih = new g56('TARIH');

if ($tarih->find("etkinlik_id = '" . $etkinlik_id . "' and tarih = '" . $date . "'")) {
	$tarih->load("etkinlik_id = '" . $etkinlik_id . "' and tarih = '" . $date . "'");
	if ($date >= date('Y-m-d')) {
		g56::set('SESSION.tarih', $date);
		return g56::call('etkinliksalon.php');
	} else {
		g56::set('SESSION.error', "Üzgünüz bu etkinliğin tarih süresi geçmiştir.");
		return g56::call('etkinlikadim1.php');
	}
} else {
	g56::set('SESSION.error', "Etkinliğin böyle bir tarih bilgisi mevcut değil.");
	return g56::call('etkinlikadim1.php');
}
?>
