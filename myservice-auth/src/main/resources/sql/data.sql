INSERT INTO auth.company (code, name, fiscal_number, client)
SELECT * FROM (SELECT '0','PiramidReadson LTDA','292046488', false) AS tmp
WHERE NOT EXISTS (
    SELECT name FROM auth.company WHERE fiscal_number = '292046488'
) LIMIT 1;

INSERT INTO auth.company (code, name, fiscal_number, client)
SELECT * FROM (SELECT 'code','Integer Consulting','292046444', true) AS tmp
WHERE NOT EXISTS (
    SELECT name FROM auth.company WHERE fiscal_number = '292046444'
) LIMIT 1;

INSERT INTO auth.user (username,name, email, password, activated,id_company)
SELECT * FROM (SELECT 'admin','Administrador', 'admin@admin.com', '$2a$10$r0RFDmpneBVryx.ihHK9gu6FFJQi4nTxQUqzdSTvrPpaKZMxigqpy',true, 3) AS tmp
WHERE NOT EXISTS (
    SELECT username FROM auth.user WHERE username = 'admin'
) LIMIT 1;

INSERT INTO auth.user (username,name, email, password, activated,id_company)
SELECT * FROM (SELECT 'integer','Integer Cliente', 'integer@gmail.com', '$2a$10$r0RFDmpneBVryx.ihHK9gu6FFJQi4nTxQUqzdSTvrPpaKZMxigqpy',true, 4) AS tmp
WHERE NOT EXISTS (
    SELECT username FROM auth.user WHERE username = 'integer'
) LIMIT 1;

INSERT INTO auth.authority (name,short_description,description,client,module_name)
SELECT * FROM (SELECT 'AUTH_CLIENT_ADMIN','Administrador', 'Administrador das funcionalidades do Cliente para o modulo AUTH',true,'myservice-auth') AS tmp
WHERE NOT EXISTS (
    SELECT name FROM auth.authority WHERE name = 'AUTH_CLIENT_ADMIN'
) LIMIT 1;

INSERT INTO auth.authority (name,short_description,description,client,module_name)
SELECT * FROM (SELECT 'AUTH_ADMIN','Administrador','Administrador AUTH',false,'myservice-auth') AS tmp
WHERE NOT EXISTS (
    SELECT name FROM auth.authority WHERE name = 'AUTH_ADMIN'
) LIMIT 1;

INSERT INTO auth.user_authority (id_username, authority)
SELECT * FROM (SELECT 4, 'AUTH_CLIENT_ADMIN') AS tmp
WHERE NOT EXISTS (
    SELECT id_username, authority FROM auth.user_authority WHERE id_username = 4 and authority = 'AUTH_CLIENT_ADMIN'
) LIMIT 1;

INSERT INTO auth.user_authority (id_username, authority)
SELECT * FROM (SELECT 3, 'AUTH_ADMIN') AS tmp
WHERE NOT EXISTS (
    SELECT id_username, authority FROM auth.user_authority WHERE id_username = 3 and authority = 'AUTH_ADMIN'
) LIMIT 1;