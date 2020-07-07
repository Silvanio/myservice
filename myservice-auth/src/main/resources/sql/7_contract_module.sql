INSERT INTO auth.contract_module (id_contract,app_module)
SELECT * FROM (select (select id from auth.contract  where id = 1),select (select id from auth.app_module  where name = 'myservice-auth'));

