<?php
require 'lib/config.php';

$date = g56::get('POST.tarih');
$il_id = g56::get('POST.il_id');
$kategori_id = g56::get('POST.kategori_id');

$yer = new g56('YER');
$tarih = new g56('TARIH');
$etkinlik = new g56('ETKINLIK');

// kategorisine uygun etkinlikleri alalım.
$etkinlikler = $etkinlik->rows("kategori_id = '" . $kategori_id . "'", 'ad, etkinlik_id');

// her etkinlik için tarihleri uyuyor mu bakalım.
$fields = array();

foreach ($etkinlikler['items'] as $indis => $etkin)
	if ($yer->find("etkinlik_id = '" . $etkin['etkinlik_id'] . "' and il_id = '" . $il_id  ."'")
		&& $tarih->find("etkinlik_id = '" . $etkin['etkinlik_id'] . "'")) {

		$tarihler = $tarih->rows("etkinlik_id = '" . $etkin['etkinlik_id'] . "' and tarih >= '" . $date . "'", 'tarih');
		if ($tarihler['count'] > 1) {
			$min = $tarihler['items'][0]['tarih'];
			array_shift($tarihler['items']);
			foreach ($tarihler['items'] as $indis => $tarihler)
				if ($min > $tarihler['tarih'])
					$min = $tarihler['tarih'];
			if ($min <= $date)
				array_push($fields, $etkin);
		} else
			 if ($tarihler['items'][0]['tarih'] == $date)
				array_push($fields, $etkin);
	}
if ($fields) { // yine de kontrol yap!
	$ok = array("ETKİNLİK ARAMA BİLGİLERİ" => $fields);

	$il = new g56('IL');
	$il->load("il_id = '". $il_id ."'");

	$kategori = new g56('KATEGORI');
	$kategori->load("kategori_id = '". $kategori_id ."'");

	if (g56::exists('SESSION.error'))
		g56::clear('SESSION.error');

	g56::set('SESSION.ok', $ok);

	g56::set('SESSION.kategori_ad', $kategori->ad);
	g56::set('SESSION.kategori_id', $kategori->kategori_id);
	g56::set('SESSION.il_ad', $il->ad);
	g56::set('SESSION.il_id', $il->il_id);

	g56::call('etkinlikler.php');
} else {
	g56::set('SESSION.error', "üzgünüm aradığınız kriterlere ait bir etkinlik bulunamadı");
	g56::call('etkinlikara.php');
}
?>
