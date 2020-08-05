INSERT INTO auth.app_module (name,description,web_url,status)
SELECT * FROM (SELECT 'mymarket','MyMarket - App do Mercado Financeiro', 'http://192.168.1.246:8080/',1) AS tmp
WHERE NOT EXISTS (
    SELECT name FROM auth.app_module WHERE name = 'mymarket'
) LIMIT 1;
