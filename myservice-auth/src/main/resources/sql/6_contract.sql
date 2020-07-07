INSERT INTO auth.contract (id_company,count_user,dt_initial, dt_final)
SELECT * FROM (select (select id from auth.company  where code = '0'),1, '2020-07-06 00:00:00','2030-07-28 00:00:00');

