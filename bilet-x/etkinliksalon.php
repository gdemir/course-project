<?php
require 'lib/config.php';

$il_id = g56::get('SESSION.il_id');

$salon = new g56('SALON');
$salonlar = $salon->rows("il_id = '" . $il_id . "'", 'salon_id, ad');

$fields = array();
$fields[''] = '';
foreach ($salonlar['items'] as $indis => $salon)
	$fields[$salon['salon_id']] = $salon['ad'];

g56::set('SESSION.salonlar', $fields);

g56::call('etkinlikadim2.php');
?>
