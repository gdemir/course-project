<?php
require 'lib/config.php';

$rezerve = new g56('REZERVE');
if ($rezerve->find("member_id = '" . g56::get('SESSION.member_id') . "'")) {
	$rezerveler = $rezerve->rows("member_id = '" . g56::get('SESSION.member_id') . "'", 'etkinlik_id, tarih, salon_id, koltuk_id, ucret_id, rezerve_id');

	$fields = array();
	$field = array(
			"tarih",
			"kategori",
			"il",
			"etkinlik",
			"salon",
			"koltuk",
			"ücret",
			"rezerve_id",
		);
	$null = array(
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
		);
	array_push($fields, array_combine($field, $null));

	foreach ($rezerveler['items'] as $indis => $rezerve) {

		$etkinlik = new g56('ETKINLIK');
		$etkinlik->load("etkinlik_id = '" . $rezerve['etkinlik_id'] . "'");

		$kategori = new g56('KATEGORI');
		$kategori->load("kategori_id = '" . $etkinlik->kategori_id . "'");

		$salon = new g56('SALON');
		$salon->load("salon_id = '" . $rezerve['salon_id'] . "'");
		
		$il = new g56('IL');
		$il->load("il_id = '" . $salon->il_id . "'");

		$koltuk = new g56('KOLTUK');
		$koltuk->load("koltuk_id = '" . $rezerve['koltuk_id'] . "'");

		$ucret = new g56('UCRET');
		$ucret->load("ucret_id = '" . $rezerve['ucret_id'] . "'");
		
		array_push($fields, 
			array_combine($field,
				array(
				$rezerve['tarih'],
				$kategori->ad,
				$il->ad,
				$etkinlik->ad,
				$salon->ad,
				$koltuk->ad,
				$ucret->ad,
				$rezerve['rezerve_id'],
				)
			)
		);
	}
	
	$ok = array("REZERVE BİLGİLERİ" => $fields);
	g56::set('SESSION.ok', $ok);
	g56::call('rezervebilgiler.php');
} else {
	g56::set('SESSION.error', "üzgünüz hiç rezerve edilmiş etkinliğiniz yok.");
	return g56::call('index.php');
}

?>
