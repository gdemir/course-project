<?php
require 'lib/config.php';

if (g56::get('POST.salon_id')  == "") { // boş mu ?
	g56::set('SESSION.error', "salon bilgisini doldurmanız zorunludur.");
	return g56::call('etkinlikadim2.php');
}

$tarih = g56::get('SESSION.tarih');
$il_id = g56::get('SESSION.il_id');
$etkinlik_id = g56::get('SESSION.etkinlik_id');

$salon = new g56('SALON');

if ($salon->find("salon_id = '" . g56::get('POST.salon_id') . "'")) {
	$salon->load("salon_id = '" . g56::get('POST.salon_id') . "'");

	$yer = new g56('YER');
	$yer->load("etkinlik_id = '" . $etkinlik_id . "' and il_id = '" . $il_id . "'");

	$koltuk = new g56('KOLTUK');
	$koltuklar = $koltuk->rows("salon_id = '" . $salon->salon_id . "'", 'koltuk_id, ad');

	$fields = array();
	$fields[''] = '';
	$rezerve = new g56('REZERVE');
	if ($rezerve->find("tarih = '" . $tarih . "' and etkinlik_id = '" . $etkinlik_id . "' and yer_id = '" . $yer->yer_id . "'")) {
		$rezerveler = $rezerve->rows("tarih = '" . $tarih . "' and etkinlik_id = '" . $etkinlik_id . "' and yer_id = '" . $yer->yer_id . "'", 'koltuk_id');
		foreach ($koltuklar['items'] as $indis => $koltuk) {
			$state = false;
			foreach ($rezerveler['items'] as $indis2 => $rezerve)
				if ($koltuk['koltuk_id'] == $rezerve['koltuk_id']) {
					$state = true;
					break;
				}
			if (!$state)
				$fields[$koltuk['koltuk_id']] = $koltuk['ad'];
		}
		if (count($fields) <= 1) {
			g56::set('SESSION.error', "üzgünüz bu etkinliğin bu salonu dolmuştur.");
			return g56::call('etkinlikadim2.php');
		}
	}else {
		foreach ($koltuklar['items'] as $indis => $koltuk)
			$fields[$koltuk['koltuk_id']] = $koltuk['ad'];
	}

	g56::set('SESSION.koltuklar', $fields);
	g56::set('SESSION.salon_id', $salon->salon_id);
	g56::set('SESSION.salon_ad', $salon->ad);
	g56::set('SESSION.yer_id', $yer->yer_id);

	return g56::call('etkinlikadim3.php');
} else {
	g56::set('SESSION.error', "Etkinliğin böyle bir koltuk bilgisi mevcut değil.");
	return g56::call('etkinlikadim2.php');
}
?>
