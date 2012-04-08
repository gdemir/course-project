<?php
require 'lib/config.php';

$fields = array(
				g56::get('POST.kredikart') => "Kredikart",
				g56::get('POST.telefon') => "Telefon",
				g56::get('POST.email') => "Email",
				g56::get('POST.soyad') => "Soyad",
				g56::get('POST.ad') => "İsim",
				g56::get('POST.username') => "Kullanıcı ismi",
				g56::get('POST.il_id') => "İl",
			);

foreach ($fields as $field => $message) {
	if ($field == "") { // boş mu ?
		g56::set('SESSION.error', $message . " alanını doldurmanız zorunludur.");
		return g56::call('userekle.php');
	}
}

$user = g56::get('POST.username');

$member = new g56('MEMBER');
if (!$member->find("username = '$user'")) {
	$member->get_form($_POST);
	$member->tarih = date('Y-m-d');
	$member->save();
	
	$il = new g56('IL');
	$il->load("il_id = '" . $member->il_id . "'");
	
	if (g56::exists('SESSION.error'))
		g56::clear('SESSION.error');

	$member->load("username = '$user'");
	
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
	g56::call('ok.php');

} else {
	g56::set('SESSION.error', $user . " ismi kullanılıyor başka bir isim seçin.");
	g56::call('userekle.php');
}
?>
