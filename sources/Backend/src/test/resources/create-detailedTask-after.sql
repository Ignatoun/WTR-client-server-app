USE wtrtest;
DELETE FROM DetailedTask WHERE detailedTaskId <> 0;
DELETE FROM wtrtest.Task WHERE taskId <> 0;
DELETE FROM Feature WHERE featureId <> 0;
DELETE FROM Project WHERE projectId <> 0;
SET foreign_key_checks=1;