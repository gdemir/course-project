<?php
require '../lib/config.php';

$yer = new g56('YER');

$yer->etkinlik_id = g56::get('POST.etkinlik_id');
$yer->il_id = g56::get('POST.il_id');


if (!$yer->find("etkinlik_id = '" . $yer->etkinlik_id . "' and il_id = '" . $yer->il_id. "'")) {
	if (g56::exists('SESSION.error'))
		g56::clear('SESSION.error');

	$yer->save();
	
	$etkinlik = new g56('ETKINLIK');
	$etkinlik->load("etkinlik_id = '" .$yer->etkinlik_id . "'");
	
	$il = new g56('IL');
	$il->load("il_id = '" .$yer->il_id . "'");

	$ok = array(
		  "YER KAYIT BİLGİLERİ" =>
		    array(
			"Ad" => $etkinlik->ad,
			"İl" => $il->ad,
			)
		  );

	g56::set('SESSION.ok', $ok);
	g56::call('ok.php');

} else {
	g56::set('SESSION.error', "Bu etkinliğin, bu ilde yer bilgisi zaten var!");
	g56::call('yerekle.php');
}
?>
