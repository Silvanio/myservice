INSERT INTO market.tb_sector(status, name) VALUES (1, 'Criptomoedas');

INSERT INTO market.tb_sub_sector(status, name, id_sector)
	VALUES (1, 'Criptomoedas', (select sec.id from market.tb_sector sec where sec.name = 'Criptomoedas'));


INSERT INTO market.tb_segment( status, name, id_sub_sector)
    	VALUES (1, 'Criptomoedas', (select subs.id from market.tb_sub_sector subs where subs.name = 'Criptomoedas'));


INSERT INTO market.tb_stock(status, code, name,  id_product, id_segment, exchange)
	VALUES (1, 'BTC', 'Bitcoin', (select prod.id from market.tb_product prod where prod.code = 'CRIPTO'),
			(select seg.id from market.tb_segment seg where seg.name = 'Criptomoedas' ), 'Mercado Bitcoin');


INSERT INTO market.tb_stock(status, code, name,  id_product, id_segment, exchange)
	VALUES (1, 'ETH', 'Ethereum', (select prod.id from market.tb_product prod where prod.code = 'CRIPTO'),
			(select seg.id from market.tb_segment seg where seg.name = 'Criptomoedas' ), 'Mercado Bitcoin');

INSERT INTO market.tb_stock(status, code, name,  id_product, id_segment, exchange)
	VALUES (1, 'LTC', 'Litecoin', (select prod.id from market.tb_product prod where prod.code = 'CRIPTO'),
			(select seg.id from market.tb_segment seg where seg.name = 'Criptomoedas' ), 'Mercado Bitcoin');

INSERT INTO market.tb_stock(status, code, name,  id_product, id_segment, exchange)
	VALUES (1, 'BCH', 'Bitcoin Cash', (select prod.id from market.tb_product prod where prod.code = 'CRIPTO'),
			(select seg.id from market.tb_segment seg where seg.name = 'Criptomoedas' ), 'Mercado Bitcoin');

INSERT INTO market.tb_stock(status, code, name,  id_product, id_segment, exchange)
	VALUES (1, 'XRP', 'XRP', (select prod.id from market.tb_product prod where prod.code = 'CRIPTO'),
			(select seg.id from market.tb_segment seg where seg.name = 'Criptomoedas' ), 'Mercado Bitcoin');
