<?php
require 'lib/config.php';

$member = new g56('MEMBER');
if ($member->find("member_id = '" . g56::get('SESSION.member_id') . "'")) {
	$member->load("member_id = '" . g56::get('SESSION.member_id') . "'");
	$il = new g56('IL');
	$il->load("il_id = '" . $member->il_id . "'");

	$ok = array(
			"MÜŞTERİ BİLGİLERİ" => 
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
	g56::call('hesapbilgiler.php');
} else {
	g56::set('SESSION.error', "Anlaşılmayan bir nedenle sistem sizi tanımadı, hesabınız silinmiş olabilir.");
	return g56::call('index.php');
}
?>
