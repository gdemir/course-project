<?php
require '../lib/config.php';

$member = new g56('MEMBER');
if ($member->find("username = '" . g56::get('POST.username') . "'")) {
	$member->load("username = '" . g56::get('POST.username') . "'");

	$rezerve = new g56('REZERVE');
	$rezerveler = $rezerve->rows("member_id = '" . $member->member_id . "'", 'rezerve_id');

	// kullanıcının rezervelerini silelim.
	foreach ($rezerveler['items'] as $indis => $rezerve) {
		$rezerve_id = $rezerve['rezerve_id'];
		$rezerve = new g56('REZERVE');
		$rezerve->load("rezerve_id = '" . $rezerve_id . "'");
		$rezerve->erase();
	}

	$member->erase();
	if (g56::exists('SESSION.error'))
		g56::clear('SESSION.error');

	$il = new g56('IL');
	$il->load("il_id = '" . $member->il_id . "'");

	$ok = array(
			"SİLİNEN KULLANICI BİLGİLERİ" => 
			array(
			"Müşteri no" => $member->member_id,
			"Kullanıcı adı" => $member->username,
			"Şifre" => $member->password,
			"Ad" => $member->ad,
			"Soyad" => $member->soyad,
			"Email" => $member->email,
			"İl" => $il->ad,
			"Telefon" => $member->telefon,
			"Kredi kart no" => $member->kredikart,
			"Kayıt tarih" => $member->tarih,
			));

	g56::set('SESSION.ok', $ok);
	g56::call('ok.php');

} else {
	g56::set('SESSION.error', "Bu isimde kullanıcı yok!");
	g56::call('usersil.php');
}
?>
