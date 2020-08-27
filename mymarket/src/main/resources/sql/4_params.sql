INSERT INTO market.tb_market_param(CODE,NAME,VALUE) VALUES ('URL_HISTORICAL_DATA_STOCK','URL para importar o histórico de ações','https://api.marketstack.com/v1/eod?access_key={0}&symbols={1}&date_from={2}&date_to={3}');
INSERT INTO market.tb_market_param(CODE,NAME,VALUE) VALUES ('TOKEN_HISTORICAL_DATA_STOCK','Token para importar o histórico de ações','7de3cdddfb12928c2c612b2b9d0b34');

INSERT INTO market.tb_market_param(CODE,NAME,VALUE) VALUES ('URL_LOGIN_CEDRO_API','URL para logar na api cedro','http://webfeeder.cedrotech.com/SignIn?login={0}&password={1}');
INSERT INTO market.tb_market_param(CODE,NAME,VALUE) VALUES ('USER_CEDRO_API','User para API Cedro','jrsilvanio');
INSERT INTO market.tb_market_param(CODE,NAME,VALUE) VALUES ('PASSWORD_CEDRO_API','Senha para API Cedro','102030');
INSERT INTO market.tb_market_param(CODE,NAME,VALUE) VALUES ('URL_GET_PRICE_CEDRO_API','URL obter cotação na API Cedro','http://webfeeder.cedrotech.com/services/quotes/quote/{0}');

INSERT INTO market.tb_market_param(CODE,NAME,VALUE) VALUES ('URL_GET_PRICE_CRYPTO_MERCADO_BTC','URL cotação Criptomoedas na API Mercado BTC','https://www.mercadobitcoin.net/api/{0}/ticker');


INSERT INTO market.tb_market_param(CODE,NAME,VALUE) VALUES ('B3_OPEN_MARKET','ABERTURA MERCADO B3','14');
INSERT INTO market.tb_market_param(CODE,NAME,VALUE) VALUES ('B3_CLOSE_MARKET','FECHAMENTO MERCADO B3','21');
