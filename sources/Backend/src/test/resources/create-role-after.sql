USE wtrtest;
DELETE FROM Role WHERE roleId <> 0;
DELETE FROM User WHERE userId <> 0;
DELETE FROM UserRole WHERE userId <> 0;

SET foreign_key_checks=1;