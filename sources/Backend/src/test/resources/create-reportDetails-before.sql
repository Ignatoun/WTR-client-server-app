USE wtrtest;
SET foreign_key_checks=0;

DELETE FROM wtrtest.ReportDetails WHERE reportDetailsId <> 0;
ALTER TABLE ReportDetails AUTO_INCREMENT=1;
INSERT INTO ReportDetails(date, factorId, locationId)
VALUES ('2019-12-01', 2, 2);
INSERT INTO ReportDetails(date, status, factorId, locationId, hours, workUnits, projectId, featureId, taskId, detailedTaskId, comments)
VALUES ('2019-12-02','REGISTERED', 2, 1, null, null, null, null, null, null, 'comment' );
INSERT INTO ReportDetails(date, status, factorId, locationId, hours, workUnits, projectId, featureId, taskId, detailedTaskId, comments)
VALUES ('2019-12-02','PRIVATE', 2, 1, null, null, null, null, null, null, 'I did it' );
INSERT INTO ReportDetails(date, status, factorId, locationId, hours, workUnits, projectId, featureId, taskId, detailedTaskId)
VALUES ('2019-12-02','REJECTED', 2, 1, null, null, null, null, null, null );
INSERT INTO ReportDetails(date, status, factorId, locationId, hours, workUnits, projectId, featureId, taskId, detailedTaskId, comments)
VALUES ('2019-12-03','REGISTERED', 1, 1, null, null, null, null, null, null, 'Comment');
INSERT INTO ReportDetails(date, status, factorId, locationId, hours, workUnits, projectId, featureId, taskId, detailedTaskId)
VALUES ('2019-12-03','PRIVATE', 1, 1, null, null, null, null, null, null);
INSERT INTO ReportDetails (date, status, factorId, locationId, hours, workUnits, projectId, featureId, taskId, detailedTaskId)
VALUES ('2019-12-03','REJECTED', 1, 2, null, null, null, null, null, null);
INSERT INTO ReportDetails(date, status, factorId, locationId, hours, workUnits, projectId, featureId, taskId, comments)
VALUES ('2019-12-04','REGISTERED', 2, 1, null, null, null, null, null, 'Comment');
INSERT INTO ReportDetails(date, status, factorId, locationId, hours, workUnits, projectId, featureId, taskId, detailedTaskId, comments)
VALUES ('2019-12-04','PRIVATE', 1, 1, null, null, null, null, null,null, 'Comment');
INSERT INTO ReportDetails(date, status, factorId, locationId, hours, workUnits, projectId, featureId, taskId, detailedTaskId)
VALUES ('2019-12-04','REJECTED', 1, 2, null, null, null, null, null, null);

DELETE FROM Location WHERE locationId <> 0;
ALTER TABLE Location AUTO_INCREMENT = 1;
INSERT INTO Location(locationName) VALUES ("Location1");
INSERT INTO Location(locationName) VALUES ("Location2");

DELETE FROM Factor WHERE factorId <> 0;
ALTER TABLE Factor AUTO_INCREMENT=1;
INSERT INTO Factor(factorName) VALUES ("Standard");
INSERT INTO Factor(factorName) VALUES ("Overtime");


DELETE FROM Report WHERE userId <> 0;
ALTER TABLE Report AUTO_INCREMENT = 1;
INSERT INTO Report (userId, reportDetailsId) VALUES (1, 1);
INSERT INTO Report (userId, reportDetailsId) VALUES (1, 2);
INSERT INTO Report (userId, reportDetailsId) VALUES (1, 3);
INSERT INTO Report (userId, reportDetailsId) VALUES (1, 4);
INSERT INTO Report (userId, reportDetailsId) VALUES (1, 5);
INSERT INTO Report (userId, reportDetailsId) VALUES (1, 6);
INSERT INTO Report (userId, reportDetailsId) VALUES (1, 7);
INSERT INTO Report (userId, reportDetailsId) VALUES (1, 8);
INSERT INTO Report (userId, reportDetailsId) VALUES (1, 9);
INSERT INTO Report (userId, reportDetailsId) VALUES (1, 10);