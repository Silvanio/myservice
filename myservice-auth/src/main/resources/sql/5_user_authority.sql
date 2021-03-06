INSERT INTO auth.user_authority (id_username, authority)
SELECT * FROM (select (select id from auth.user where username = 'admin'), 'AUTH_ADMIN') AS tmp
WHERE NOT EXISTS (
    SELECT id_username, authority FROM auth.user_authority WHERE id_username = (select id from auth.user where username = 'admin') and authority = 'AUTH_ADMIN'
) LIMIT 1;
