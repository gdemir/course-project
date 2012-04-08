<?php
function listbox_kv($hash, $name) {
	echo "<select name = '$name'>\n";
	foreach ($hash as $key => $value)
		echo "<option value = '$key'> $value </option>\n";
	echo "</select>\n";
}
function listbox($hash, $name) {
	echo "<select name = '$name'>\n";
	foreach ($hash as $key => $value)
		echo "<option value = '$value'> $value </option>\n";
	echo "</select>\n";
}
function adminler() {
	$ret = array();
	$ret[''] = '';
	$admin = new g56('ADMIN');
	$adminler = $admin->rows();
	foreach ($adminler['items'] as $row)
		if (!array_key_exists($row['name'], $ret))
			$ret[$row['admin_id']] = $row['name'];
	return $ret;
}
function musteriler() {
	$ret = array();
	$ret[''] = '';
	$member = new g56('MEMBER');
	$memberler = $member->rows();
	foreach ($memberler['items'] as $row)
		if (!array_key_exists($row['username'], $ret))
			$ret[$row['member_id']] = $row['username'];
	return $ret;
}
function iller() {
	$ret = array();
	$ret[''] = '';
	$il = new g56('IL');
	$iller = $il->rows();
	foreach ($iller['items'] as $row)
		if (!array_key_exists($row['ad'], $ret))
			$ret[$row['il_id']] = $row['ad'];
	return $ret;
}
function tarihler($etkinlik_id) {
	$ret = array();
	$tarih = new g56('TARIH');
	$tarihler = $tarih->rows("etkinlik_id='" . $etkinlik_id ."'");  
	foreach ($tarihler['items'] as $row)
		if (!array_key_exists($row['tarih'], $ret))
			array_push($ret, $row['tarih']);
	return $ret;
}
function kategoriler() {
	$ret = array();
	$ret[''] = '';
	$kategori = new g56('KATEGORI');
	$kategoriler = $kategori->rows();
	foreach ($kategoriler['items'] as $row)	
		if (!array_key_exists($row['kategori_id'], $ret))
			$ret[$row['kategori_id']] = $row['ad'];
	return $ret;
}
function etkinlikler() {
	$ret = array();
	$ret[''] = '';
	$kategori = new g56('ETKINLIK');
	$kategoriler = $kategori->rows();
	foreach ($kategoriler['items'] as $row)
		if (!array_key_exists($row['etkinlik_id'], $ret))
			$ret[$row['etkinlik_id']] = $row['ad'];
	return $ret;
}
function gunler($ilk_gun = 1, $son_gun = 31) {
	$ret = range($ilk_gun, $son_gun); array_unshift($ret, '');
	return $ret;
}
function aylar($ilk_ay = 1, $son_ay = 12) {
	$ret = range($ilk_ay, $son_ay); array_unshift($ret, '');
	return $ret;
}
function yillar($enfazla = 70) {
	$busene = date('Y');
	// sorarım size insan kaç sene yaşar?
	$ret = range($busene, $busene + $enfazla); array_unshift($ret, '');
	return $ret;
}
function saatler() {
	$ret = range(1, 24); array_unshift($ret, '');
	return $ret;
}
function dakikalar() {
	$ret = range(0, 59); array_unshift($ret, '');
	return $ret;
}
?>
