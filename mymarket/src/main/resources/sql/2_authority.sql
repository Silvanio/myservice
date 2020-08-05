INSERT INTO auth.authority (name,short_description,description,client,id_app_module)
SELECT * FROM (SELECT 'MY_MARKET_ADMIN','MyMarket - Administrador', 'Administrador das funcionalidades para o modulo Market',true,
   (SELECT ID FROM auth.app_module where name = 'mymarket')
) AS tmp
WHERE NOT EXISTS (
    SELECT name FROM auth.authority WHERE name = 'MY_MARKET_ADMIN'
) LIMIT 1;


INSERT INTO auth.authority (name,short_description,description,client,id_app_module)
SELECT * FROM (SELECT 'MY_MARKET_USER','MyMarket - User', 'Utilizador',true,
   (SELECT ID FROM auth.app_module where name = 'mymarket')
) AS tmp
WHERE NOT EXISTS (
    SELECT name FROM auth.authority WHERE name = 'MY_MARKET_USER'
) LIMIT 1;