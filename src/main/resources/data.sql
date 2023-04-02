INSERT INTO TEST ( name)
VALUES ( 'TETSTYATY');

INSERT INTO USERS (name, email, password)
VALUES ('User', 'user@yandex.ru', '{noop}password'),
       ('Admin', 'admin@gmail.com', '{noop}admin');

INSERT INTO USER_ROLE (role, user_id)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2);

INSERT INTO application( message, status, user_id, application_date_time)
VALUES ('Перезвоните срочно! Как можно не работать в выходные!', 'DRAFT',1, '2020-01-30 10:00:00'),
 ('На мониторе написано LG, что делать?', 'DRAFT',1, '2023-01-30 10:00:00');