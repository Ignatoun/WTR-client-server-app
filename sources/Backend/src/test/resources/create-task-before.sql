USE wtrtest;
SET foreign_key_checks=0;

DELETE FROM wtrtest.Task WHERE taskId <> 0;
ALTER TABLE wtrtest.Task AUTO_INCREMENT = 1;
INSERT INTO Task(taskName, featureId) VALUES ("Task1",null);
INSERT INTO Task(taskName, featureId) VALUES ("Task2",null);
INSERT INTO Task(taskName, featureId) VALUES ("Task3",null);
INSERT INTO Task(taskName, featureId) VALUES ("Task4",null);
INSERT INTO Task(taskName, featureId) VALUES ("Task5",null);
