USE wtrtest;
SET foreign_key_checks=0;

DELETE FROM Project WHERE projectId <> 0;
ALTER TABLE Project AUTO_INCREMENT = 1;
INSERT INTO Project(projectName, startDate, endDate)
VALUES ('wtr lite for all companies and users', '2019-11-02', '2020-05-09');
INSERT INTO Project(projectName, startDate, endDate)
VALUES ('the best game in the world!', '2018-04-20', '2021-12-31');