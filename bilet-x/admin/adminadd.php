<?php
require '../lib/config.php';

$fields = array(
				g56::get('POST.password') => "Admin şifresi",
				g56::get('POST.name') => "Admin ismi",
			);

foreach ($fields as $field => $message) {
	if ($field == "") { // boş mu ?
		g56::set('SESSION.error', $message . "ni doldurmanız zorunludur.");
		return g56::call('adminekle.php');
	}
}

$admin = new g56('ADMIN');

if (!$admin->find("name = '" . g56::get('POST.name') . "'")) {
	$admin->get_form($_POST);
	$admin->tarih = date('y-m-d');

	$admin->save();

	$admin_tip = ($admin->super) ? "super" : "admin";

	$ok = array(
		  "ADMIN KAYIT BİLGİLERİ" =>
		    array(
			"Ad" => $admin->name,
			"Şifre" => $admin->password,
			"Tip" => $admin_tip,
			"Kayıt tarihi" => $admin->tarih,
			),
		  );

	g56::set('SESSION.ok', $ok);
	return g56::call('ok.php');
} else {
	g56::set('SESSION.error', "Bu isimde admin zaten var!");
	return g56::call('adminekle.php');
}
?>
