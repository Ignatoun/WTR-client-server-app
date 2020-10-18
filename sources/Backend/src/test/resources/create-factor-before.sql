USE wtrtest;
SET foreign_key_checks=0;

DELETE FROM Factor WHERE factorId <> 0;
ALTER TABLE Factor AUTO_INCREMENT=1;
INSERT INTO Factor(factorName) VALUES ("Standard");
INSERT INTO Factor(factorName) VALUES ("Overtime");
INSERT INTO Factor(factorName) VALUES ("Vacation");
INSERT INTO Factor(factorName) VALUES ("Sick or Care Absence");
INSERT INTO Factor(factorName) VALUES ("Business Trip");
INSERT INTO Factor(factorName) VALUES ("Standard Night");
INSERT INTO Factor(factorName) VALUES ("Excused Absence");
INSERT INTO Factor(factorName) VALUES ("Day Off");
INSERT INTO Factor(factorName) VALUES ("Unexcused Absence");