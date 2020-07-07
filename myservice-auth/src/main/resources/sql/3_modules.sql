INSERT INTO auth.app_module (name,description,web_url,status)
SELECT * FROM (SELECT 'myservice-auth','MyServices - Administração de Acessos', 'http://192.168.1.141:8080/',1) AS tmp
WHERE NOT EXISTS (
    SELECT name FROM auth.app_module WHERE name = 'myservice-auth'
) LIMIT 1;
