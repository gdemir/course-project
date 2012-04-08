### HAZIRLAYANLAR

- Gökhan Demir http://gdemir.me
- Erol Uslu
- Merve Ceylan
- Yunus Ateþ

### AÇIKLAMA

arkaplan:
	video: 002.mpg
	ortalama: mn.jpg
	standart_sapma: st.jpg
onplan:
	video: 006.mpg
betik:
	bg_model: arkaplan ortalamasý ve standart sapmasý döner.
	bg_remove: bg_model'in çýktýlarýný kullanarak 006.mpgden
		insanlarýn belli olduðu siyah beyaz frameleri
		"frames" dizinine çýkartýr.
	motion_detection: bg_remove'in çýkardýðý "frames" dizinindeki
		framlerden belli bir kesiti alýp buna yorum yapar.