<?php
require 'lib/config.php';

$rezerve  = new g56('REZERVE');
if ($rezerve->find("rezerve_id = '" . g56::get('POST.rezerve_id') . "'")) {
	$rezerve->load("rezerve_id = '" . g56::get('POST.rezerve_id') . "'");

	$etkinlik = new g56('ETKINLIK');
	$etkinlik->load("etkinlik_id = '". $rezerve->etkinlik_id . "'");

	$salon = new g56('SALON');
	$salon->load("salon_id = '". $rezerve->salon_id . "'");

	$koltuk = new g56('KOLTUK');
	$koltuk->load("koltuk_id = '". $rezerve->koltuk_id . "'");

	$ucret = new g56('UCRET');
	$ucret->load("ucret_id = '". $rezerve->ucret_id . "'");

	$yer = new g56('YER');
	$yer->load("yer_id = '". $rezerve->yer_id . "'");

	$il = new g56('IL');
	$il->load("il_id = '". $yer->il_id . "'");
	
	$kategori = new g56('KATEGORI');
	$kategori->load("kategori_id = '". $etkinlik->kategori_id . "'");

	$rezerve->erase();
	
	$ok = array(
		  "REZERVE SİLME BİLGİLERİ" => 
		    array(
			"Kategori" => $kategori->ad,
			"Il" => $il->ad,
			"Etkinlik" => $etkinlik->ad,
			"Tarih" => 	$rezerve->tarih,
			"Salon" => $salon->ad,
			"Koltuk" => $koltuk->ad,
			"Ucret" => $ucret->ad,
			),
		  );
	g56::set('SESSION.ok', $ok);
	g56::call('ok.php');
} else {
	g56::set('SESSION.error', "üzgünüz böyle rezerve edilmiş bir yeriniz bulunmamaktadır.");
	return g56::call('rezervegoster.php');
}
