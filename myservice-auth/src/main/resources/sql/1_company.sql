INSERT INTO auth.company (code, name, fiscal_number, client,status)
SELECT * FROM (SELECT '0','PiramidReadson LTDA','292046488', false,1) AS tmp
WHERE NOT EXISTS (
    SELECT name FROM auth.company WHERE fiscal_number = '292046488'
) LIMIT 1;