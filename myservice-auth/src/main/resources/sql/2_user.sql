
INSERT INTO auth.user (username,name, email, password, activated,id_company)
SELECT * FROM (SELECT 'admin','Administrador', 'admin@admin.com', '$2a$10$r0RFDmpneBVryx.ihHK9gu6FFJQi4nTxQUqzdSTvrPpaKZMxigqpy',true,
  (select id from auth.company where code = '0')
) AS tmp
WHERE NOT EXISTS (
    SELECT username FROM auth.user WHERE username = 'admin'
) LIMIT 1;

INSERT INTO auth.user (username,name, email, password, activated,id_company)
SELECT * FROM (SELECT 'cliente01','Cliente 01', 'cliente01@gmail.com', '$2a$10$r0RFDmpneBVryx.ihHK9gu6FFJQi4nTxQUqzdSTvrPpaKZMxigqpy',true,
    (select id from auth.company where code = 'INTG')
) AS tmp
WHERE NOT EXISTS (
    SELECT username FROM auth.user WHERE username = 'integer'
) LIMIT 1;
