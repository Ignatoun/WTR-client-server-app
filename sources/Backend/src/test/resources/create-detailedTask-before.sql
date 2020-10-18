USE wtrtest;
SET foreign_key_checks=0;

DELETE FROM DetailedTask WHERE detailedTaskId <> 0;
ALTER TABLE DetailedTask AUTO_INCREMENT = 1;
INSERT INTO DetailedTask(detailedTaskName, taskId) VALUES('detailedTask0', 1);
INSERT INTO DetailedTask(detailedTaskName, taskId) VALUES('detailedTask1', 1);
INSERT INTO DetailedTask(detailedTaskName, taskId) VALUES('detailedTask2', 1);
INSERT INTO DetailedTask(detailedTaskName, taskId) VALUES('detailedTask3', 1);
INSERT INTO DetailedTask(detailedTaskName, taskId) VALUES('detailedTask4', 1);

DELETE FROM Task WHERE taskId <> 0;
ALTER TABLE Task AUTO_INCREMENT = 1;
INSERT INTO Task(taskName, featureId) VALUES ("Task1",1);

DELETE FROM Feature WHERE featureId <> 0;
ALTER TABLE Feature AUTO_INCREMENT = 1;
INSERT INTO Feature(featureName, projectId) VALUES ("feature0", 1);

DELETE FROM Project WHERE projectId <> 0;
ALTER TABLE Project AUTO_INCREMENT = 1;
INSERT INTO Project(projectName, startDate, endDate)
VALUES ('wtr lite for all companies and users', '2019-11-02', '2020-05-09');