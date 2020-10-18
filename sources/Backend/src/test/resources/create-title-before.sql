USE wtrtest;
SET foreign_key_checks=0;

DELETE FROM Title WHERE titleId <> 0;
ALTER TABLE Title AUTO_INCREMENT = 1;
INSERT INTO Title(titleName) VALUES ("Title1");
INSERT INTO Title(titleName) VALUES ("Title2");
INSERT INTO Title(titleName) VALUES ("Title3");
INSERT INTO Title(titleName) VALUES ("Title4");
INSERT INTO Title(titleName) VALUES ("Title5");