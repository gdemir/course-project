<?php
require 'lib/config.php';

$etkinlik_id = g56::get('POST.etkinlik_id');

$yer = new g56('YER');
$tarih = new g56('TARIH');
$etkinlik = new g56('ETKINLIK');

// kategorisine uygun etkinlikleri alalım.
$etkinlik->load("etkinlik_id = '" . $etkinlik_id . "'");


$kategori = new g56('KATEGORI');
$kategori->load("kategori_id = '" . $etkinlik->kategori_id . "'");

// her etkinlik için tarihleri uyuyor mu bakalım.

if ($yer->find("etkinlik_id = '" . $etkinlik->etkinlik_id . "'")) { // yine de kontrol yap!
	if ($tarih->find("etkinlik_id = '" . $etkinlik->etkinlik_id . "'")) { // yine de kontrol yap!
	
		$yerler = $yer->rows("etkinlik_id = '" . $etkinlik->etkinlik_id . "'", 'il_id');
		
		$il = new g56('IL');
		$iller = $il->rows();
		
		$fields = array();
		$fields[''] = '';
		foreach ($yerler['items'] as $indis => $yer)
			foreach ($iller['items'] as $indis => $il)
				if ($yer['il_id'] == $il['il_id']) {
					$fields[$il['il_id']] = $il['ad'];
					break;
				}
		
		if (g56::exists('SESSION.error'))
			g56::clear('SESSION.error');
		
		g56::set('SESSION.iller', $fields);

		g56::set('SESSION.kategori_ad', $kategori->ad);
		g56::set('SESSION.kategori_id', $kategori->kategori_id);
		g56::set('SESSION.etkinlik_ad', $etkinlik->ad);
		g56::set('SESSION.etkinlik_id', $etkinlik->etkinlik_id);

		g56::call('etkinlikadim0.php');
	} else {
		g56::set('SESSION.error', "Üzgünüz bu etkinliğin henüz hangi tarihlerde olduğu belirlenmedi.");
		g56::call('etkinlikara.php');
	}
} else {
	g56::set('SESSION.error', "Üzgünüz bu etkinliğin henüz hangi illerde olduğu belirlenmedi.");
	g56::call('etkinlikara.php');
}
?>
