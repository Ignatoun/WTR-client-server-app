USE wtrtest;
SET foreign_key_checks=0;

DELETE FROM Location WHERE locationId <> 0;
ALTER TABLE Location AUTO_INCREMENT = 1;
INSERT INTO Location(locationName) VALUES ("epol/brest");
INSERT INTO Location(locationName) VALUES ("epol/minsk");
INSERT INTO Location(locationName) VALUES ("epol/grodno");
INSERT INTO Location(locationName) VALUES ("epol/lodz");
INSERT INTO Location(locationName) VALUES ("epol/praga");



DELETE FROM Factor WHERE factorId <> 0;
ALTER TABLE Factor AUTO_INCREMENT=1;
INSERT INTO Factor(factorName) VALUES ("Standard");
INSERT INTO Factor(factorName) VALUES ("Overtime");
INSERT INTO Factor(factorName) VALUES ("Vacation");
INSERT INTO Factor(factorName) VALUES ("Sick or Care Absence");
INSERT INTO Factor(factorName) VALUES ("Business Trip");
INSERT INTO Factor(factorName) VALUES ("Standard Night");
INSERT INTO Factor(factorName) VALUES ("Excused Absence");
INSERT INTO Factor(factorName) VALUES ("Day Off");
INSERT INTO Factor(factorName) VALUES ("Unexcused Absence");


DELETE FROM Title WHERE titleId <> 0;
ALTER TABLE Title AUTO_INCREMENT = 1;
INSERT INTO Title(titleName) VALUES ("Software Developer");
INSERT INTO Title(titleName) VALUES ("Software Tester");
INSERT INTO Title(titleName) VALUES ("Data Architect");
INSERT INTO Title(titleName) VALUES ("Software Engineer");
INSERT INTO Title(titleName) VALUES ("Network Administrator");

DELETE FROM Department WHERE departmentId <> 0;
ALTER TABLE Department AUTO_INCREMENT = 1;
INSERT INTO Department(departmentName) VALUES ("Department1");
INSERT INTO Department(departmentName) VALUES ("Department2");
INSERT INTO Department(departmentName) VALUES ("Department3");
INSERT INTO Department(departmentName) VALUES ("Department4");
INSERT INTO Department(departmentName) VALUES ("Department5");

DELETE FROM Role WHERE roleId <> 0;
ALTER TABLE Role AUTO_INCREMENT=1;
INSERT INTO Role(roleName) VALUES ("USER");
INSERT INTO Role(roleName) VALUES ("ADMIN");
INSERT INTO Role(roleName) VALUES ("MANAGER");

DELETE FROM User WHERE userId <> 0;
ALTER TABLE User AUTO_INCREMENT = 1;
INSERT INTO User(userName, firstName, lastName, email , password , titleId, departmentId)
VALUES ('123user123', 'Ivan', 'Ivanov', 'vania@tut.by','$2a$04$iuSjuFKUitIy9vTJ8uRl6OkJ4MWaSboeQzpb1slHQQl.J5WCjEara', 1, 1);
INSERT INTO User(userName, firstName, lastName, email , password , titleId, departmentId)
VALUES ('pro-developer', 'Petr', 'Petrov', 'petr@mail.ru','$2a$04$iuSjuFKUitIy9vTJ8uRl6OkJ4MWaSboeQzpb1slHQQl.J5WCjEara', 2, 1);
INSERT INTO User(userName, firstName, lastName, email , password , titleId, departmentId)
VALUES ('bbig-boss', 'Sergey', 'Ivanov', 'boss@gmail.com','$2a$04$iuSjuFKUitIy9vTJ8uRl6OkJ4MWaSboeQzpb1slHQQl.J5WCjEara', 5, 1);
INSERT INTO User(userName, firstName, lastName, email , password , titleId, departmentId)
VALUES ('american-man', 'Mike', 'Djonson', 'mike@outlook.by','$2a$04$iuSjuFKUitIy9vTJ8uRl6OkJ4MWaSboeQzpb1slHQQl.J5WCjEara', 4, 1);
INSERT INTO User(userName, firstName, lastName, email , password , titleId, departmentId)
VALUES ('1-lopata-1', 'Artem', 'Lopata', 'shovel@tut.by','$2a$04$iuSjuFKUitIy9vTJ8uRl6OkJ4MWaSboeQzpb1slHQQl.J5WCjEara', 3, 1);
INSERT INTO User(userName, firstName, lastName, email , password , titleId, departmentId)
VALUES ('the-best', 'Astra', 'Mironova', 'miron@mail.ru','$2a$04$iuSjuFKUitIy9vTJ8uRl6OkJ4MWaSboeQzpb1slHQQl.J5WCjEara', 2, 1);
INSERT INTO User(userName, firstName, lastName, email , password , titleId, departmentId)
VALUES ('gold-girl', 'Zlata', 'Maximochkina', 'zlata_max@gmail.com','$2a$04$iuSjuFKUitIy9vTJ8uRl6OkJ4MWaSboeQzpb1slHQQl.J5WCjEara', 5, 1);
INSERT INTO User(userName, firstName, lastName, email , password , titleId, departmentId)
VALUES ('lihach', 'Daria', 'Lihacheva', 'lihach@tut.by','$2a$04$iuSjuFKUitIy9vTJ8uRl6OkJ4MWaSboeQzpb1slHQQl.J5WCjEara', 1, 1);
INSERT INTO User(userName, firstName, lastName, email , password , titleId, departmentId)
VALUES ('natasha', 'Natalia', 'Romanova', 'nataly@outlook.com','$2a$04$iuSjuFKUitIy9vTJ8uRl6OkJ4MWaSboeQzpb1slHQQl.J5WCjEara', 3, 1);
INSERT INTO User(userName, firstName, lastName, email , password , titleId, departmentId)
VALUES ('rabbit', 'Anastasia', 'Safonova', 'nastya@gmail.com','$2a$04$iuSjuFKUitIy9vTJ8uRl6OkJ4MWaSboeQzpb1slHQQl.J5WCjEara', 4, 1);



DELETE FROM Project WHERE projectId <> 0;
ALTER TABLE Project AUTO_INCREMENT = 1;
INSERT INTO Project(projectName, startDate, endDate)
VALUES ('wtr lite for all companies and users', '2019-11-02', '2020-05-09');
INSERT INTO Project(projectName, startDate, endDate)
VALUES ('the best game in the world!', '2018-04-20', '2021-12-31');



DELETE FROM ReportDetails WHERE reportDetailsId <> 0;
ALTER TABLE ReportDetails AUTO_INCREMENT=1;
INSERT INTO ReportDetails(date, factorId, locationId)
VALUES ('2019-11-01', 8, 1);
INSERT INTO ReportDetails(date, status, factorId, locationId, hours, workUnits, projectId, featureId, taskId, detailedTaskId, comments)
VALUES ('2018-12-02','REGISTERED', 1, 1, 1, 1, 1, 1, 1, 1, 'comment' );
INSERT INTO ReportDetails(date, status, factorId, locationId, hours, workUnits, projectId, featureId, taskId, detailedTaskId, comments)
VALUES ('2019-12-02','APPROVED', 1, 1, 2, 2, 1, 1, 1, 2, 'I did it' );
INSERT INTO ReportDetails(date, status, factorId, locationId, hours, workUnits, projectId, featureId, taskId, detailedTaskId)
VALUES ('2019-12-02','APPROVED', 1, 1, 5, 5, 1, 1, 2, 3 );
INSERT INTO ReportDetails(date, status, factorId, locationId, hours, workUnits, projectId, featureId, taskId, detailedTaskId, comments)
VALUES ('2019-12-03','REGISTERED', 1, 2, 3, 3, 1, 2, 3, 4, 'Comment');
INSERT INTO ReportDetails(date, status, factorId, locationId, hours, workUnits, projectId, featureId, taskId, detailedTaskId)
VALUES ('2019-12-03','REGISTERED', 1, 2, 5, 5, 1, 2, 3, 5);



DELETE FROM Feature WHERE featureId <> 0;
ALTER TABLE Feature AUTO_INCREMENT = 1;
INSERT INTO Feature(featureName, projectId)
VALUES ("project database development", 1);
INSERT INTO Feature(featureName, projectId)
VALUES ("application composition development", 1);
INSERT INTO Feature(featureName, projectId)
VALUES ("connecting modules to the project", 1);
INSERT INTO Feature(featureName, projectId)
VALUES ("realization of navigation in the project", 2);
INSERT INTO Feature(featureName, projectId)
VALUES ("UI realization", 2);
INSERT INTO Feature(featureName, projectId)
VALUES ("neural network architecture development", 2);
INSERT INTO Feature(featureName, projectId)
VALUES ("neural network architecture implementation", 2);

DELETE FROM UserRole WHERE userId <> 0;
INSERT INTO UserRole(userId, roleId) VALUES (1, 1);
INSERT INTO UserRole(userId, roleId) VALUES (2, 2);
INSERT INTO UserRole(userId, roleId) VALUES (3, 3);
INSERT INTO UserRole(userId, roleId) VALUES (4, 1);
INSERT INTO UserRole(userId, roleId) VALUES (5, 1);
INSERT INTO UserRole(userId, roleId) VALUES (6, 1);
INSERT INTO UserRole(userId, roleId) VALUES (7, 1);
INSERT INTO UserRole(userId, roleId) VALUES (8, 1);
INSERT INTO UserRole(userId, roleId) VALUES (9, 1);
INSERT INTO UserRole(userId, roleId) VALUES (10, 2);


DELETE FROM Task WHERE taskId <> 0;
ALTER TABLE Task AUTO_INCREMENT = 1;
INSERT INTO Task(taskName, featureId) VALUES ("development of database tables",1);
INSERT INTO Task(taskName, featureId) VALUES ("database table implementation",1);
INSERT INTO Task(taskName, featureId) VALUES ("Creating test data for the database",1);
INSERT INTO Task(taskName, featureId) VALUES ("frontend",2);
INSERT INTO Task(taskName, featureId) VALUES ("backend",2);
INSERT INTO Task(taskName, featureId) VALUES ("database",2);
INSERT INTO Task(taskName, featureId) VALUES ("to frontend",3);
INSERT INTO Task(taskName, featureId) VALUES ("to backend",3);
INSERT INTO Task(taskName, featureId) VALUES ("to database",3);
INSERT INTO Task(taskName, featureId) VALUES ("main menu",4);
INSERT INTO Task(taskName, featureId) VALUES ("addition menu",4);
INSERT INTO Task(taskName, featureId) VALUES ("prototyping",5);
INSERT INTO Task(taskName, featureId) VALUES ("construction",5);
INSERT INTO Task(taskName, featureId) VALUES ("used modules",6);
INSERT INTO Task(taskName, featureId) VALUES ("advantages",6);
INSERT INTO Task(taskName, featureId) VALUES ("innovations",6);
INSERT INTO Task(taskName, featureId) VALUES ("borrowing architecture",6);
INSERT INTO Task(taskName, featureId) VALUES ("main application",7);
INSERT INTO Task(taskName, featureId) VALUES ("sampling",7);
INSERT INTO Task(taskName, featureId) VALUES ("training",7);
INSERT INTO Task(taskName, featureId) VALUES ("comparison of results",7);

DELETE FROM DetailedTask WHERE detailedTaskId <> 0;
ALTER TABLE DetailedTask AUTO_INCREMENT = 1;
INSERT INTO DetailedTask(detailedTaskName, taskId) VALUES('Creating database schema (fields).', 1);
INSERT INTO DetailedTask(detailedTaskName, taskId) VALUES('Creating database schema (relations).', 1);
INSERT INTO DetailedTask(detailedTaskName, taskId) VALUES('Creating SQL script for database.', 2);
INSERT INTO DetailedTask(detailedTaskName, taskId) VALUES('Disabling foreign key checking.', 3);
INSERT INTO DetailedTask(detailedTaskName, taskId) VALUES('Creating SQL script for tests.', 3);
INSERT INTO DetailedTask(detailedTaskName, taskId) VALUES('Creating Node software with React.', 4);
INSERT INTO DetailedTask(detailedTaskName, taskId) VALUES('Choosing SQL language for database.', 6);
INSERT INTO DetailedTask(detailedTaskName, taskId) VALUES('Creating database schema.', 6);
INSERT INTO DetailedTask(detailedTaskName, taskId) VALUES('Connecting Redux.', 7);
INSERT INTO DetailedTask(detailedTaskName, taskId) VALUES('Creating structure of Spring project.', 8);
INSERT INTO DetailedTask(detailedTaskName, taskId) VALUES('Fixing errors.', 8);
INSERT INTO DetailedTask(detailedTaskName, taskId) VALUES('Creating ui form to Main menu.', 10);
INSERT INTO DetailedTask(detailedTaskName, taskId) VALUES('Connection of actions with the Additional menu.', 11);
INSERT INTO DetailedTask(detailedTaskName, taskId) VALUES('Lets skip this task.', 12);
INSERT INTO DetailedTask(detailedTaskName, taskId) VALUES('Writing code for main window.', 13);
INSERT INTO DetailedTask(detailedTaskName, taskId) VALUES('Connection libraries.', 14);
INSERT INTO DetailedTask(detailedTaskName, taskId) VALUES('Testing of innovations.', 16);
INSERT INTO DetailedTask(detailedTaskName, taskId) VALUES('Fix errors.', 16);
INSERT INTO DetailedTask(detailedTaskName, taskId) VALUES('Without comments.', 17);
INSERT INTO DetailedTask(detailedTaskName, taskId) VALUES('Choosing max number of smth.', 19);

DELETE FROM Report WHERE userId <> 0;
ALTER TABLE Report AUTO_INCREMENT = 1;
INSERT INTO Report (userId, reportDetailsId) VALUES (1, 1);
INSERT INTO Report (userId, reportDetailsId) VALUES (1, 2);
INSERT INTO Report (userId, reportDetailsId) VALUES (1, 3);
INSERT INTO Report (userId, reportDetailsId) VALUES (1, 4);
INSERT INTO Report (userId, reportDetailsId) VALUES (1, 5);
INSERT INTO Report (userId, reportDetailsId) VALUES (1, 6);

SET foreign_key_checks=1;