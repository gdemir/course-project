<?php
require 'lib/config.php';

if (g56::get('POST.il_id') == "") { // boş mu ?
	g56::set('SESSION.error', "il bilgisini doldurmanız zorunludur.");
	return g56::call('etkinlikadim0.php');
}
	
$il = new g56('IL');
if ($il->find("il_id = '" . g56::get('POST.il_id') . "'")) {
	$il->load("il_id = '" . g56::get('POST.il_id') . "'");

	if (g56::exists('SESSION.error'))
		g56::clear('SESSION.error');

	g56::set('SESSION.il_ad', $il->ad);
	g56::set('SESSION.il_id', $il->il_id);

	$tarih = new g56('TARIH');
	$tarihler = $tarih->rows("etkinlik_id = '" . g56::get('SESSION.etkinlik_id') . "'", 'tarih');
	$fields = array();
	$fields[''] = '';
	foreach ($tarihler['items'] as $indis => $tarih)
		$fields[$tarih['tarih']] = $tarih['tarih'];

	g56::set('SESSION.tarihler', $fields);
	g56::call('etkinlikadim1.php');
}else {
	g56::set('SESSION.error', "Etkinliğin böyle bir il bilgisi mevcut değil.");
	g56::call('etkinlikadim0.php');
}
?>
