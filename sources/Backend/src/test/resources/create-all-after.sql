USE wtrtest;

DELETE FROM Location WHERE locationId <> 0;
DELETE FROM Factor WHERE factorId <> 0;
DELETE FROM Title WHERE titleId <> 0;
DELETE FROM Department WHERE departmentId <> 0;
DELETE FROM Role WHERE roleId <> 0;
DELETE FROM User WHERE userId <> 0;
DELETE FROM Project WHERE projectId <> 0;
DELETE FROM ReportDetails WHERE reportDetailsId <> 0;
DELETE FROM Feature WHERE featureId <> 0;
DELETE FROM UserRole WHERE userId <> 0;
DELETE FROM Task WHERE taskId <> 0;
DELETE FROM DetailedTask WHERE detailedTaskId <> 0;
DELETE FROM Report WHERE userId <> 0;