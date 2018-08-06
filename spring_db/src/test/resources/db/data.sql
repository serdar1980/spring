insert into tbl_book (id, name) values (1, 'Java 8. Полное руководство');
insert into tbl_book (id, name) values (2, 'Совершенный код');
insert into tbl_book (id, name) values (3, 'Приёмы объектно-ориентированного проектирования. Паттерны проектирования');


insert into tbl_author (id, fio) values (1, 'Герберт Шилдт');
insert into tbl_author (id, fio) values (2, 'Стив Макконнелл');

insert into tbl_author (id, fio) values (3, 'Эрих Гамма');
insert into tbl_author (id, fio) values (4, 'Ричард Хелм');
insert into tbl_author (id, fio) values (5, 'Ральф Джонсон');
insert into tbl_author (id, fio) values (6, 'Джон Влиссидес.');

insert into book_author (book_id, author_id) values(1, 1);
insert into book_author (book_id, author_id) values(2, 2);
insert into book_author (book_id, author_id) values(3, 3);
insert into book_author (book_id, author_id) values(3, 1);
insert into book_author (book_id, author_id) values(3, 2);
--insert into book_author (book_id, author_id) values(3, 6);


