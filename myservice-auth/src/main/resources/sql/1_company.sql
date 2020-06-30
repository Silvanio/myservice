INSERT INTO auth.company (code, name, fiscal_number, client)
SELECT * FROM (SELECT '0','PiramidReadson LTDA','292046488', false) AS tmp
WHERE NOT EXISTS (
    SELECT name FROM auth.company WHERE fiscal_number = '292046488'
) LIMIT 1;

INSERT INTO auth.company (code, name, fiscal_number, client)
SELECT * FROM (SELECT 'INTG','Integer Consulting','292046444', true) AS tmp
WHERE NOT EXISTS (
    SELECT name FROM auth.company WHERE fiscal_number = '292046444'
) LIMIT 1;
