<?php
require '../lib/config.php';

$saat = g56::get('POST.saat') . ':' . g56::get('POST.dakika') . ':00';
$tarih = g56::get('POST.yil') . '-' . g56::get('POST.ay') . '-' . g56::get('POST.gun');

$fields = array(
				g56::get('POST.dakika') => "Dakika",
				g56::get('POST.saat') => "Saat",
				g56::get('POST.gun') => "Gün",
				g56::get('POST.ay') => "Ay",
				g56::get('POST.yil') => "Yıl",
				g56::get('POST.etkinlik_id') => "Etkinlik",
				);

foreach ($fields as $field => $message) {
	if ($field == "") { // boş mu ?
		g56::set('SESSION.error', $message . " alanını doldurmanız zorunludur.");
		return g56::call('tarihekle.php');
	}
}

$etkinlik = new g56('ETKINLIK');
$etkinlik->load("etkinlik_id = '" . g56::get('POST.etkinlik_id') . "'");

$date = new g56('TARIH');

if (!$date->find("etkinlik_id = '" . $etkinlik->etkinlik_id . "' and tarih = '" . $tarih. "'")) {
	if (g56::exists('SESSION.error'))
		g56::clear('SESSION.error');

	$date->etkinlik_id = $etkinlik->etkinlik_id;
	$date->tarih = $tarih;
	$date->saat = $saat;
	$date->save();

	$ok = array(
		  "ETKİNLİK TARİH KAYIT BİLGİLERİ" =>
		    array(
			"Ad" => $etkinlik->ad,
			"Tarih" => $date->tarih,
			"Saat" => $date->saat,
			)
		  );

	g56::set('SESSION.ok', $ok);
	return g56::call('ok.php');

} else {
	g56::set('SESSION.error', "Bu etkinliğin, bu tarih bilgisi zaten var!");
	return g56::call('tarihekle.php');
}
?>
