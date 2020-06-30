INSERT INTO auth.authority (name,short_description,description,client,id_app_module)
SELECT * FROM (SELECT 'AUTH_CLIENT_ADMIN','AUTH - Administrador', 'Administrador das funcionalidades do Cliente para o modulo AUTH',true,
   (SELECT ID FROM auth.app_module where name = 'myservice-auth')
) AS tmp
WHERE NOT EXISTS (
    SELECT name FROM auth.authority WHERE name = 'AUTH_CLIENT_ADMIN'
) LIMIT 1;

INSERT INTO auth.authority (name,short_description,description,client,id_app_module)
SELECT * FROM (SELECT 'AUTH_ADMIN','AUTH - Administrador', 'Administrador das funcionalidades para o modulo AUTH',false,
   (SELECT ID FROM auth.app_module where name = 'myservice-auth')
) AS tmp
WHERE NOT EXISTS (
    SELECT name FROM auth.authority WHERE name = 'AUTH_ADMIN'
) LIMIT 1;