
INSERT INTO auth.user (username,name, email, password, status,id_company)
SELECT * FROM (SELECT 'admin','Silvânio Júnior', 'jrsilvanio@gmail.com', '$2a$10$r0RFDmpneBVryx.ihHK9gu6FFJQi4nTxQUqzdSTvrPpaKZMxigqpy',1,
  (select id from auth.company where code = '0')
) AS tmp
WHERE NOT EXISTS (
    SELECT username FROM auth.user WHERE username = 'admin'
) LIMIT 1;
