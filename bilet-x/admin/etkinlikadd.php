<?php
require '../lib/config.php';
require '../util.php';

$fields = array(
				g56::get('POST.kategori_id') => "Etkinlik kategorisi",
				g56::get('POST.ad') => "Etkinlik ismi",
				);

foreach ($fields as $field => $message) {
	if ($field == "") { // boş mu ?
		g56::set('SESSION.error', $message . "ni doldurmanız zorunludur.");
		return g56::call('etkinlikekle.php');
	}
}

$kategori = new g56('KATEGORI');
$kategori->load("kategori_id = '" . g56::get('POST.kategori_id') . "'");

$etkinlik = new g56('ETKINLIK');
$etkinlik->ad = g56::get('POST.ad');
$etkinlik->durum       = 1;
$etkinlik->kategori_id = g56::get('POST.kategori_id');

if (!$etkinlik->find("ad = '" . $etkinlik->ad . "' and kategori_id = '" . $etkinlik->kategori_id . "'")) {

	if (g56::exists('SESSION.error'))
		g56::clear('SESSION.error');

	$etkinlik->save();
	$etkinlik->load("ad = '" . $etkinlik->ad . "' and kategori_id = '" . $etkinlik->kategori_id . "'");
	$image_name = $etkinlik->etkinlik_id . ".jpg";

	$file = g56::get('FILES.file');
	if (($error = g56::img_upload("resim", $file, $image_name)) != null) {
		g56::set('SESSION.error', $error);
		$etkinlik->erase();
		return g56::call('etkinlikekle.php');
	}

	$image_path = g56::path() . "resim/" . $image_name;
	g56::img_small($image_path,  $image_path, 250);	
/*
	$image_path = g56::path() . "resim/" . $image_name;
	$size = g56::img_wh($image_path);
	if ($size['withd'] > 300)
		g56::img_small($image_path,  $image_path, 300);
*/

	$ok = array(
		  "ETKİNLİK KAYIT BİLGİLERİ" =>
		    array(
			"Ad" => $etkinlik->ad,
			"Kategori" => $kategori->ad,
			)
		  );

	g56::set('SESSION.ok', $ok);
	g56::call('ok.php');

} else {
	g56::set('SESSION.error', "Bu etkinlik adında ve bu kategoride, bir etkinlik mevcut!");
	g56::call('etkinlikekle.php');
}
?>
