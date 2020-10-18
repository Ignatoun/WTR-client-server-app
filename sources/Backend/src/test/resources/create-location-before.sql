USE wtrtest;
SET foreign_key_checks=0;

DELETE FROM Location WHERE locationId <> 0;
ALTER TABLE Location AUTO_INCREMENT = 1;
INSERT INTO Location(locationName) VALUES ("Location1");
INSERT INTO Location(locationName) VALUES ("Location2");
INSERT INTO Location(locationName) VALUES ("Location3");
INSERT INTO Location(locationName) VALUES ("Location4");
INSERT INTO Location(locationName) VALUES ("Location5");
INSERT INTO Location(locationName) VALUES ("Location6");
INSERT INTO Location(locationName) VALUES ("Location7");
INSERT INTO Location(locationName) VALUES ("Location8");