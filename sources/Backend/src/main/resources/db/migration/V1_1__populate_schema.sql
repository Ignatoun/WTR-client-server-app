DROP TABLE IF EXISTS UserRole;
DROP TABLE IF EXISTS Report;
DROP TABLE IF EXISTS ReportDetails;
DROP TABLE IF EXISTS Factor;
DROP TABLE IF EXISTS Location;
DROP TABLE IF EXISTS DetailedTask;
DROP TABLE IF EXISTS Task;
DROP TABLE IF EXISTS Feature;
DROP TABLE IF EXISTS Project;
DROP TABLE IF EXISTS User;
DROP TABLE IF EXISTS Title;
DROP TABLE IF EXISTS Department;
DROP TABLE IF EXISTS Role;
DROP TABLE IF EXISTS Scheduler;

CREATE TABLE Scheduler (
          paramId INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
          paramName VARCHAR(50) NOT NULL,
          value VARCHAR(50) NOT NULL
);

CREATE TABLE User (
                      userId INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
                      userName VARCHAR(50) NOT NULL,
                      firstName VARCHAR(50) NOT NULL,
                      lastName VARCHAR(50) NOT NULL,
                      email VARCHAR(100) NOT NULL,
                      password VARCHAR(60) NOT NULL,
                      titleId INT UNSIGNED,
                      departmentId INT UNSIGNED
);

CREATE TABLE DetailedTask (
                              detailedTaskId INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
                              detailedTaskName VARCHAR(255) NOT NULL,
                              taskId INT UNSIGNED
);

CREATE TABLE UserRole (
                          userId INT UNSIGNED,
                          roleId INT UNSIGNED
);

CREATE TABLE Project (
                         projectId INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
                         projectName VARCHAR(50) NOT NULL,
                         startDate DATE NOT NULL,
                         endDate DATE
);

CREATE TABLE Task (
                      taskId INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
                      taskName VARCHAR(255) NOT NULL,
                      featureId INT UNSIGNED
);

CREATE TABLE Role (
                      roleId INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
                      roleName VARCHAR(50) NOT NULL
);

CREATE TABLE Feature (
                         featureId INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
                         featureName VARCHAR(50) NOT NULL,
                         projectId INT UNSIGNED
);

CREATE TABLE Factor (
                        factorId INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
                        factorName VARCHAR(50) NOT NULL
);

CREATE TABLE Location (
                          locationId INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
                          locationName VARCHAR(50) NOT NULL
);

CREATE TABLE Report (
                        reportDetailsId INT UNSIGNED,
                        userId INT UNSIGNED
);

CREATE TABLE Department (
                            departmentId INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
                            departmentName VARCHAR(255) NOT NULL
);

CREATE TABLE ReportDetails (
                               reportDetailsId INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
                               date DATE NOT NULL,
                               status ENUM('PRIVATE', 'REGISTERED', 'REJECTED','APPROVED'),
                               factorId INT UNSIGNED,
                               locationId INT UNSIGNED,
                               hours DOUBLE UNSIGNED,
                               workUnits DOUBLE UNSIGNED,
                               detailedTaskId INT UNSIGNED ,
                               taskId INT UNSIGNED,
                               featureId INT UNSIGNED,
                               projectId INT UNSIGNED,
                               comments VARCHAR(250)
);

CREATE TABLE  Title (
                        titleId INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
                        titleName VARCHAR(255) NOT NULL
);

ALTER TABLE ReportDetails ADD FOREIGN KEY (factorId) REFERENCES Factor(factorId) on update cascade on delete cascade;
ALTER TABLE ReportDetails ADD FOREIGN KEY (locationId) REFERENCES Location(locationId) on update cascade on delete cascade;
ALTER TABLE ReportDetails ADD FOREIGN KEY (detailedTaskId) REFERENCES DetailedTask(detailedTaskId) on update cascade on delete cascade;
ALTER TABLE ReportDetails ADD FOREIGN KEY (taskId) REFERENCES Task(taskId) on update cascade on delete cascade;
ALTER TABLE ReportDetails ADD FOREIGN KEY (featureId) REFERENCES Feature(featureId) on update cascade on delete cascade;
ALTER TABLE ReportDetails ADD FOREIGN KEY (projectId) REFERENCES Project(projectId) on update cascade on delete cascade;
ALTER TABLE DetailedTask ADD FOREIGN KEY (taskId) REFERENCES Task(taskId) on update cascade on delete cascade;
ALTER TABLE Task ADD FOREIGN KEY (featureId) REFERENCES Feature(featureId) on update cascade on delete cascade;
ALTER TABLE Feature ADD FOREIGN KEY (projectId) REFERENCES Project(projectId) on update cascade on delete cascade;
ALTER TABLE UserRole ADD FOREIGN KEY (userId) REFERENCES User(userId) on update cascade on delete cascade;
ALTER TABLE UserRole ADD FOREIGN KEY (roleId) REFERENCES Role(roleId) on update cascade on delete cascade;
ALTER TABLE User ADD FOREIGN KEY (departmentId) REFERENCES Department(departmentId) on update cascade on delete cascade;
ALTER TABLE User ADD FOREIGN KEY (titleId) REFERENCES Title(titleId) on update cascade on delete cascade;
ALTER TABLE Report ADD FOREIGN KEY (reportDetailsId) REFERENCES ReportDetails(reportDetailsId) on update cascade on delete cascade;
ALTER TABLE Report ADD FOREIGN KEY (userId) REFERENCES User(userId)on update cascade on delete cascade;
