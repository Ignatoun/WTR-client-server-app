USE wtrtest;
SET foreign_key_checks=0;

DELETE FROM User WHERE userId <> 0;
ALTER TABLE User AUTO_INCREMENT = 1;
INSERT INTO User(userName, firstName, lastName, email , password , titleId, departmentId)
VALUES ('123user123', 'Ivan', 'Ivanov', 'vania@tut.by','$2a$04$iuSjuFKUitIy9vTJ8uRl6OkJ4MWaSboeQzpb1slHQQl.J5WCjEara', null, null);
INSERT INTO User(userName, firstName, lastName, email , password , titleId, departmentId)
VALUES ('pro-developer', 'Petr', 'Petrov', 'petr@mail.ru','$2a$04$iuSjuFKUitIy9vTJ8uRl6OkJ4MWaSboeQzpb1slHQQl.J5WCjEara', null, null);
INSERT INTO User(userName, firstName, lastName, email , password , titleId, departmentId)
VALUES ('bbig-boss', 'Sergey', 'Ivanov', 'boss@gmail.com','$2a$04$iuSjuFKUitIy9vTJ8uRl6OkJ4MWaSboeQzpb1slHQQl.J5WCjEara', null, null);
INSERT INTO User(userName, firstName, lastName, email , password , titleId, departmentId)
VALUES ('american-man', 'Mike', 'Djonson', 'mike@outlook.by','$2a$04$iuSjuFKUitIy9vTJ8uRl6OkJ4MWaSboeQzpb1slHQQl.J5WCjEara', null, null);
INSERT INTO User(userName, firstName, lastName, email , password , titleId, departmentId)
VALUES ('1-lopata-1', 'Artem', 'Lopata', 'shovel@tut.by','$2a$04$iuSjuFKUitIy9vTJ8uRl6OkJ4MWaSboeQzpb1slHQQl.J5WCjEara', null, null);
INSERT INTO User(userName, firstName, lastName, email , password , titleId, departmentId)
VALUES ('the-best', 'Astra', 'Mironova', 'miron@mail.ru','$2a$04$iuSjuFKUitIy9vTJ8uRl6OkJ4MWaSboeQzpb1slHQQl.J5WCjEara', null, null);
INSERT INTO User(userName, firstName, lastName, email , password , titleId, departmentId)
VALUES ('gold-girl', 'Zlata', 'Maximochkina', 'zlata_max@gmail.com','$2a$04$iuSjuFKUitIy9vTJ8uRl6OkJ4MWaSboeQzpb1slHQQl.J5WCjEara', null, null);
INSERT INTO User(userName, firstName, lastName, email , password , titleId, departmentId)
VALUES ('lihach', 'Daria', 'Lihacheva', 'lihach@tut.by','$2a$04$iuSjuFKUitIy9vTJ8uRl6OkJ4MWaSboeQzpb1slHQQl.J5WCjEara', null, null);
INSERT INTO User(userName, firstName, lastName, email , password , titleId, departmentId)
VALUES ('natasha', 'Natalia', 'Romanova', 'nataly@outlook.com','$2a$04$iuSjuFKUitIy9vTJ8uRl6OkJ4MWaSboeQzpb1slHQQl.J5WCjEara', null, null);
INSERT INTO User(userName, firstName, lastName, email , password , titleId, departmentId)
VALUES ('rabbit', 'Anastasia', 'Safonova', 'nastya@gmail.com','$2a$04$iuSjuFKUitIy9vTJ8uRl6OkJ4MWaSboeQzpb1slHQQl.J5WCjEara', null, null);
