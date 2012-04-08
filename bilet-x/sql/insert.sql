-- ------------------------------------
--    BILET-X_vX-insert
-- ------------------------------------
-- ©  Copyright 2010 designed by gdemir
-- http://gdemir.me
-- Tüm hakkları mahfuzdur
-- ------------------------------------

--
-- tablolara girdi yap
--
insert into KATEGORI (kategori_id, ad)
values
	(1, 'sinema'),
	(2, 'tiyatro'),
	(3, 'opera'),
	(4, 'bale');

insert into ETKINLIK (etkinlik_id, ad, durum, kategori_id)
values
	(1, 'incredible hulk',     1, 1),
	(2, 'ironman 2',           1, 1),
	(3, '7 kocalı hürmüz',     1, 2),
	(4, 'hacıvat ve karagöz',  1, 2),
	(5, 'hızlı ve öfkeli 5',   1, 1);

insert into TARIH (tarih_id, etkinlik_id, tarih, saat)
values
	(1,  1, '2011-10-12', '19:30:00'),
	(2,  1, '2011-10-13', '21:30:00'),
	(3,  1, '2011-10-15', '19:30:00'),
	(4,  1, '2011-10-16', '21:30:00'),

	(5,  2, '2011-10-13', '21:30:00'),
	(6,  2, '2011-10-14', '21:30:00'),
	(7,  2, '2011-10-25', '21:30:00'),
	(8,  2, '2011-10-26', '21:30:00'),

	(9,  3, '2011-10-16', '21:00:00'),
        (10, 3, '2011-10-17', '21:00:00'),

	(11, 4, '2011-10-12', '21:30:00'),
	(12, 4, '2011-10-20', '21:00:00'),

	(13, 5, '2011-10-13', '21:00:00'),
	(14, 5, '2011-10-23', '21:00:00'),
	(15, 5, '2011-10-24', '21:00:00');

insert into YER (yer_id, etkinlik_id, il_id)
values
	(1, 1, 1), -- incredible hulk/adana
	(2, 1, 2), -- incredible hulk/adiyaman
	(3, 2, 1), -- ironman 2/adana
	(4, 3, 1), -- 7 kocalı hürmüz/adanas
	(5, 4, 2), -- hacıvat ve karagöz/adiyaman
	(6, 5, 1), -- hızlı ve öfkeli 5/adana
	(7, 5, 2); -- hızlı ve öfkeli 5/adiyaman

insert into SALON (salon_id, il_id, ad, kapasite)
values
	(1, 1, 'A001', 4), -- adana/a001
	(2, 1, 'B001', 4), -- adana/b001
	(3, 2, 'A',    6), -- adiyaman/A
	(4, 2, 'B',    6), -- adiyaman/B
	(5, 3, 'T-A',  2), -- afyon/T-A
	(6, 3, 'T-B',  2); -- afyon/T-B

insert into KOLTUK (koltuk_id, salon_id, ad)
values
	(1,  1, 'a1'),  -- adana/a001/a1
	(2,  1, 'a2'),  -- adana/a001/a2
	(3,  1, 'a3'),  -- adana/a001/a3
	(4,  1, 'a4'),  -- adana/a001/a4

	(7,  2, 'b1'),  -- adana/b001/b1
	(8,  2, 'b2'),  -- adana/b001/b2
	(9,  2, 'b3'),  -- adana/b001/b3
	(10, 2, 'b4'),  -- adana/b001/b4

	(11, 3, 'a-1'), -- adiyaman/A/a-1
	(12, 3, 'a-2'), -- adiyaman/A/a-2
	(13, 3, 'a-3'), -- adiyaman/A/a-3
	(14, 3, 'a-4'), -- adiyaman/A/a-4
	(15, 3, 'a-5'), -- adiyaman/A/a-5
	(16, 3, 'a-6'), -- adiyaman/A/a-6

	(17, 4, 'b-1'), -- adiyaman/B/b-6
	(18, 4, 'b-2'), -- adiyaman/B/b-6
	(19, 4, 'b-3'), -- adiyaman/B/b-6
	(20, 4, 'b-4'), -- adiyaman/B/b-6
	(21, 4, 'b-5'), -- adiyaman/B/b-6
	(22, 4, 'b-6'), -- adiyaman/B/b-6

	(23, 5, 'aa'),  -- afyon/T-A/aa
	(24, 5, 'ab'),  -- afyon/T-A/ab

	(25, 6, 'ba'),  -- afyon/T-B/ba
	(26, 6, 'bb');  -- afyon/T-B/bb

insert into REZERVE (rezerve_id, tarih, etkinlik_id, yer_id, salon_id, koltuk_id, ucret_id, member_id)
values
	(1,  '2011-10-13', 1, 1, 1, 1,  1, 8060331),
	(2,  '2011-10-13', 1, 1, 1, 2,  1, 8060331),
	(3,  '2011-10-13', 1, 1, 2, 1,  1, 8060332),
	(4,  '2011-10-16', 1, 1, 1, 4,  2, 8060327),
	(5,  '2011-10-15', 1, 2, 2, 7,  1, 8060333),
	(6,  '2011-10-15', 1, 3, 3, 11, 1, 8060320),
	(7,  '2011-10-20', 2, 4, 1, 1,  2, 8060321),
	(8,  '2011-10-20', 4, 6, 2, 1,  2, 8060321),
	(9,  '2011-10-21', 3, 5, 5, 23, 1, 8060331);

insert into IL (il_id, ad)
values
	(1, 'Adana'),
	(2, 'Adıyaman'),
	(3, 'Afyonkarahisar'),
	(4, 'Ağrı'),
	(5, 'Aksaray'),
	(6, 'Amasya'),
	(7, 'Ankara'),
	(8, 'Antalya'),
	(9, 'Ardahan'),
	(10, 'Artvin'),
	(11, 'Aydın'),
	(12, 'Balıkesir'),
	(13, 'Bartın'),
	(14, 'Batman'),
	(15, 'Bayburt'),
	(16, 'Bilecik'),
	(17, 'Bingöl'),
	(18, 'Bitlis'),
	(19, 'Bolu'),
	(20, 'Burdur'),
	(21, 'Bursa'),
	(22, 'Çanakkale'),
	(23, 'Çankırı'),
	(24, 'Çorum'),
	(25, 'Denizli'),
	(26, 'Diyarbakır'),
	(27, 'Düzce'),
	(28, 'Edirne'),
	(29, 'Elazığ'),
	(30, 'Erzincan'),
	(31, 'Erzurum'),
	(32, 'Eskişehir'),
	(33, 'Gaziantep'),
	(34, 'Giresun'),
	(35, 'Gümüşhane'),
	(36, 'Hakkari'),
	(37, 'Hatay'),
	(38, 'Iğdır'),
	(39, 'Isparta'),
	(40, 'İstanbul'),
	(41, 'İzmir'),
	(42, 'Kahramanmaraş'),
	(43, 'Karabük'),
	(44, 'Karaman'),
	(45, 'Kars'),
	(46, 'Kastamonu'),
	(47, 'Kayseri'),
	(48, 'Kırıkkale'),
	(49, 'Kırklareli'),
	(50, 'Kırşehir'),
	(51, 'Kilis'),
	(52, 'Kocaeli'),
	(53, 'Konya'),
	(54, 'Kütahya'),
	(55, 'Malatya'),
	(56, 'Manisa'),
	(57, 'Mardin'),
	(58, 'Mersin'),
	(59, 'Muğla'),
	(60, 'Muş'),
	(61, 'Nevşehir'),
	(62, 'Niğde'),
	(63, 'Ordu'),
	(64, 'Osmaniye'),
	(65, 'Rize'),
	(66, 'Sakarya'),
	(67, 'Samsun'),
	(68, 'Siirt'),
	(69, 'Sinop'),
	(70, 'Sivas'),
	(71, 'Şanlı urfa'),
	(72, 'Şırnak'),
	(73, 'Tekirdağ'),
	(74, 'Tokat'),
	(75, 'Trabzon'),
	(76, 'Tunceli'),
	(77, 'Uşak'),
	(78, 'Van'),
	(79, 'Yalova'),
	(80, 'Yozgat'),
	(81, 'Zonguldak');

insert into MEMBER (member_id, il_id, ad, soyad, telefon, email, username, password, kredikart, tarih)
values
	(8060331, 1, 'gökhan', 'demir',  '535xxxxxxx', 'gdemir@bil.omu.edu.tr',     'gdemir',   '12345', 12345, '2010-10-13'),
	(8060333, 1, 'sefa',   'yıldız', '541xxxxxx',  'sayz@bil.omu.edu.tr',       'sayz',     '123',   12346, '2010-10-14'),
	(8060327, 2, 'yunus',  'ateş',   '542xxxxxx',  'yunus.ates@bil.omu.edu.tr', 'yunusa',   '1234',  12347, '2010-10-15'),
	(8060320, 2, 'hasan',  'ayvaz',  '542xxxxxx',  'hasayvaz@bil.omu.edu.tr',   'hasayvaz', '12',    12348, '2010-10-16'),
	(8060321, 2, 'erol',   'uslu',   '541xxxxxx',  'erol.uslu@bil.omu.edu.tr',  'euslu',    '135',   12349, '2010-10-16');

insert into ADMIN (admin_id, name, password, tarih, super)
values
	(1, 'gdemir', '12345', '2010-10-13', 1),
	(2, 'hop',    'kop',   '2010-10-14', 0);

insert into UCRET (ucret_id, ad, fiyat)
values
	(1, 'İndirimli-gün ücreti', 6),
	(2, 'Öğrenci ücreti',       8),
	(3, 'Tam ücreti',           15);
