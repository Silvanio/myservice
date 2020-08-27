INSERT INTO auth.contract (id_company,count_user,dt_initial, dt_final)
SELECT * FROM (select (select id from auth.company  where code = '0'),1, timestamp '2020-07-06T00:00:00',timestamp '2030-07-28T00:00:00') as tmp
