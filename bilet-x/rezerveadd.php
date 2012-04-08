<?php
require 'lib/config.php';

$etkinlik_id = g56::get('SESSION.etkinlik_id');
$yer_id = g56::get('SESSION.yer_id');
$ucret_id = g56::get('SESSION.ucret_id');
$tarih = g56::get('SESSION.tarih');
$salon_id = g56::get('SESSION.salon_id');
$koltuk_id = g56::get('SESSION.koltuk_id');

$rezerve = new g56('REZERVE');
if (!$rezerve->find(
		"etkinlik_id = '" . $etkinlik_id .
		"' and yer_id = '" . $yer_id .
		"' and ucret_id = '" . $ucret_id .
		"' and tarih = '" . $tarih .
		"' and salon_id = '" . $salon_id .
		"' and koltuk_id = '" . $koltuk_id . "'"
	)) {
	$rezerve->etkinlik_id = $etkinlik_id;
	$rezerve->yer_id = $yer_id;
	$rezerve->ucret_id = $ucret_id;
	$rezerve->tarih = $tarih;
	$rezerve->salon_id = $salon_id;
	$rezerve->koltuk_id = $koltuk_id;
	$rezerve->member_id = g56::get('SESSION.member_id');
	$rezerve->save();
	

	$ok = array(
		  "REZERVE KAYIT BİLGİLERİ" => 
		    array(
			"Kategori" => g56::get('SESSION.kategori_ad'),
			"Il" => g56::get('SESSION.il_ad'),
			"Etkinlik" => g56::get('SESSION.etkinlik_ad'),
			"Tarih" => g56::get('SESSION.tarih'),
			"Salon" => g56::get('SESSION.salon_ad'),
			"Koltuk" => g56::get('SESSION.koltuk_ad'),
			"Ucret" => g56::get('SESSION.ucret_ad'),
			),
		  );

	// tüm oturum bilgilerini sil
	g56::clear('SESSION.etkinlik_id');
	g56::clear('SESSION.yer_id');
	g56::clear('SESSION.ucret_id');
	g56::clear('SESSION.tarih');
	g56::clear('SESSION.salon_id');
	g56::clear('SESSION.koltuk_id');

	g56::set('SESSION.ok', $ok);
	g56::call('ok.php');
}else {
	g56::set('SESSION.error', "üzgünüz rezerve edilmiş bir yeri  tekrardan rezerve edemezsiniz.");
	return g56::call('etkinlikadim4.php');
}

?>
