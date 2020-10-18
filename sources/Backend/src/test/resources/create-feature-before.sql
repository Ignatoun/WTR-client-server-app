USE wtrtest;
SET foreign_key_checks=0;

DELETE FROM Feature WHERE featureId <> 0;
ALTER TABLE Feature AUTO_INCREMENT = 1;
INSERT INTO Feature(featureName, projectId) VALUES ("feature0", 1);
INSERT INTO Feature(featureName, projectId) VALUES ("feature1", 1);
INSERT INTO Feature(featureName, projectId) VALUES ("feature2", 1);
INSERT INTO Feature(featureName, projectId) VALUES ("feature3", 1);
INSERT INTO Feature(featureName, projectId) VALUES ("feature4", 1);

DELETE FROM Project WHERE projectId <> 0;
ALTER TABLE Project AUTO_INCREMENT = 1;
INSERT INTO Project(projectName, startDate, endDate)
VALUES ('wtr lite for all companies and users', '2019-11-02', '2020-05-09');