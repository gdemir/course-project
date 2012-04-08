<?php
require 'lib/config.php';

$etkinlik = new g56('ETKINLIK');
if ($etkinlik->find("etkinlik_id = '" . g56::get('POST.etkinlik_id') . "'")) {
	$etkinlik->load("etkinlik_id = '" . g56::get('POST.etkinlik_id') . "'");

	$tarih = new g56('TARIH');
	$tarihler = $tarih->rows("etkinlik_id = '" . $etkinlik->etkinlik_id . "'", 'tarih');

	$fields = array();
	$fields[''] = '';
	foreach ($tarihler['items'] as $indis => $tarih)
		$fields[$tarih['tarih']] = $tarih['tarih'];

	g56::set('SESSION.tarihler', $fields);
	g56::set('SESSION.etkinlik_id', $etkinlik->etkinlik_id);
	g56::set('SESSION.etkinlik_ad', $etkinlik->ad);

	return g56::call('etkinlikadim1.php');
} else {
	g56::set('SESSION.error', "Böyle bir etkinlik mevcut değil.");
	return g56::call('etkinlikler.php');
}
?>
