INSERT INTO TEST ( name)
VALUES ( 'TETSTYATY');

INSERT INTO USERS (name, email, password)
VALUES ('User', 'user@yandex.ru', '{noop}password'),
       ('Operator', 'operator@yandex.ru', '{noop}password'),
       ('Admin', 'admin@gmail.com', '{noop}admin'),
       ('User2', 'u2@gmail.com', '{noop}password');

INSERT INTO USER_ROLE (role, user_id)
VALUES ('USER', 1),
       ('ADMIN', 3),
       ('USER', 3),
       ('OPERATOR', 2),
       ('USER', 4);

INSERT INTO application( message, status, user_id, application_date_time)
VALUES ('Перезвоните срочно! Как можно не работать в выходные!', 'DRAFT',1, '2020-01-30 10:00:00'),
 ('На мониторе написано LG, что делать?', 'DRAFT',1, '2023-01-30 11:00:00'),
 ('Я ничего не делал и компьютер сам выключился. Что делать?', 'DRAFT',2, '2023-02-28 10:00:00'),
 ('У меня монитор не сенсорный, можно заменить?', 'DRAFT',4, '2023-02-28 10:00:00'),
 ('5 элемент', 'DRAFT',4, '2023-02-28 10:00:00'),
 ('Уже отправленная заявка', 'SEND',1, '2023-02-28 10:00:00'),
 ('6 элемент', 'DRAFT',4, '2023-02-28 12:00:00');