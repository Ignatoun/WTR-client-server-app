USE wtrtest;
DELETE FROM Feature WHERE featureId <> 0;
DELETE FROM Project WHERE projectId <> 0;
SET foreign_key_checks=1;