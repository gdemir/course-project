<?php
require '../lib/config.php';

$admin = new g56('ADMIN');

if ($admin->find("name = '" . g56::get('POST.name') . "'")) {
	$admin->load("name = '" . g56::get('POST.name') . "'");

	$admin->erase();

	$admin_tip = ($admin->super) ? "super" : "admin";

	$ok = array(
		  "ADMIN SİLME BİLGİLERİ" =>
		    array(
			"Ad" => $admin->name,
			"şifre" => $admin->password,
			"Tip" => $admin_tip,
			"Kayıt tarihi" => $admin->tarih,
			),
		  );

	g56::set('SESSION.ok', $ok);
	g56::call('ok.php');
} else {

	g56::set('SESSION.error', "Bu isimde bir admin yok!");
	g56::call('adminsil.php');
}
?>
