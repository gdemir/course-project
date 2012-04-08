<?php
require '../lib/config.php';
require '../util.php';

$etkinlik = new g56('ETKINLIK');
$etkinlik->load("etkinlik_id = '" . g56::get('POST.etkinlik_id') . "'");

if ($etkinlik->find("etkinlik_id = '" . $etkinlik->etkinlik_id . "'")) {
	$etkinlik->load("etkinlik_id = '" . $etkinlik->etkinlik_id . "'");

	if (g56::exists('SESSION.error'))
		g56::clear('SESSION.error');
	
	// etkinligin yer bilgilerini silelim.
	$yer = new g56('YER');
	$yerler = $yer->rows("etkinlik_id = '" . $etkinlik->etkinlik_id . "'", 'yer_id');
	foreach ($yerler['items'] as $indis => $yer) {
		$yer_id = $yer['yer_id'];
		$yer = new g56('YER');
		$yer->load("yer_id = '" . $yer_id . "'");
		$yer->erase();
	}
	
	// etkinligin tarih bilgilerini silelim.
	$tarih = new g56('TARIH');
	$tarihler = $tarih->rows("etkinlik_id = '" . $etkinlik->etkinlik_id . "'", 'tarih_id');
	foreach ($tarihler['items'] as $indis => $tarih) {
		$tarih_id = $tarih['tarih_id'];
		$tarih = new g56('TARIH');
		$tarih->load("tarih_id = '" . $tarih_id . "'");
		$tarih->erase();
	}

	// etkinligin rezerve bilgilerini silelim.
	$rezerve = new g56('REZERVE');
	$rezerveler = $rezerve->rows("etkinlik_id = '" . $etkinlik->etkinlik_id . "'", 'rezerve_id');
	foreach ($rezerveler['items'] as $indis => $rezerve) {
		$rezerve_id = $rezerve['rezerve_id'];
		$rezerve = new g56('REZERVE');
		$rezerve->load("rezerve_id = '" . $rezerve_id . "'");
		$rezerve->erase();
	}

	$etkinlik->erase();

	$ok = array(
		  "ETKİNLİK SİLME BİLGİLERİ" =>
		    array(
			"Ad" => $etkinlik->ad,
			)
		  );

	g56::set('SESSION.ok', $ok);
	g56::call('ok.php');

} else {
	g56::set('SESSION.error', "Böyle bir etkinlik yok!");
	g56::call('etkinliksil.php');
}
?>
