<?php
require 'lib/config.php';

if (g56::get('POST.koltuk_id')  == "") { // boş mu ?
	g56::set('SESSION.error', "koltuk bilgisini doldurmanız zorunludur.");
	return g56::call('etkinlikadim3.php');
}

$koltuk = new g56('KOLTUK');

if ($koltuk->find("koltuk_id = '" . g56::get('POST.koltuk_id') . "'")) {
	$koltuk->load("koltuk_id = '" . g56::get('POST.koltuk_id') . "'");

	$fields = array();
	$fields[''] = '';
	$ucret = new g56('UCRET');
	$ucretler = $ucret->rows();
	foreach ($ucretler['items'] as $indis => $ucret)
		$fields[$ucret['ucret_id']] = $ucret['ad'] . "-" . $ucret['fiyat'];

	g56::set('SESSION.ucretler', $fields);
	g56::set('SESSION.koltuk_id', $koltuk->koltuk_id);
	g56::set('SESSION.koltuk_ad', $koltuk->ad);

	return g56::call('etkinlikadim4.php');
} else
	g56::set('SESSION.error', "Etkinliğin böyle bir koltuk bilgisi mevcut değil.");
	return g56::call('etkinlikadim3.php');
?>
