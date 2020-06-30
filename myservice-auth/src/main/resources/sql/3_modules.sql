INSERT INTO auth.app_module (name,description,web_url)
SELECT * FROM (SELECT 'myservice-auth','MyServices - Administração de Acessos', 'http://192.168.1.141:8080/') AS tmp
WHERE NOT EXISTS (
    SELECT name FROM auth.app_module WHERE name = 'myservice-auth'
) LIMIT 1;
