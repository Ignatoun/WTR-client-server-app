USE wtrtest;
SET foreign_key_checks=0;

DELETE FROM Role WHERE roleId <> 0;
ALTER TABLE Role AUTO_INCREMENT=1;
INSERT INTO Role(roleName) VALUES ("USER");
INSERT INTO Role(roleName) VALUES ("ADMIN");
INSERT INTO Role(roleName) VALUES ("MANAGER");
