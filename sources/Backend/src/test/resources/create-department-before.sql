USE wtrtest;
SET foreign_key_checks=0;

DELETE FROM Department WHERE departmentId <> 0;
ALTER TABLE Department AUTO_INCREMENT = 1;
INSERT INTO Department(departmentName) VALUES ("Department1");
INSERT INTO Department(departmentName) VALUES ("Department2");
INSERT INTO Department(departmentName) VALUES ("Department3");
INSERT INTO Department(departmentName) VALUES ("Department4");
INSERT INTO Department(departmentName) VALUES ("Department5");